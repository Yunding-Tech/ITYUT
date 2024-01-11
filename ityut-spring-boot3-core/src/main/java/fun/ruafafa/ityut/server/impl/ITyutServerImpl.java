package fun.ruafafa.ityut.server.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dtflys.forest.http.ForestResponse;
import fun.ruafafa.ityut.annotation.TmspConvert;
import fun.ruafafa.ityut.constant.RequestConstant;
import fun.ruafafa.ityut.constant.TyutCampus;
import fun.ruafafa.ityut.dto.*;
import fun.ruafafa.ityut.dto.ITyutLoginUser;
import fun.ruafafa.ityut.client.TmspClient;
import fun.ruafafa.ityut.constant.LoginConstant;
import fun.ruafafa.ityut.server.ITyutService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;

/**
 * ITyut 服务调用
 */
@Service
@RequiredArgsConstructor
public class ITyutServerImpl implements ITyutService {

    private final TmspClient tmspClient;

    private final ITyutLoginUser iTyutLoginUser;

    /**
     * 用户登录
     * @param account
     * @param password
     * @return
     */
    @Override
    public boolean login(String account, String password) {
        // 重复登录会下号，同时重置不同用户登录状态
        iTyutLoginUser.setIsLogin(false);
        // 使用 公钥 RSA 加密 表单数据
        RSA rsa = SecureUtil.rsa(null, LoginConstant.LOGIN_PUB_KEY);
        String username = rsa.encryptBase64(account, KeyType.PublicKey);
        iTyutLoginUser.setUsername(username);
        iTyutLoginUser.setPassword(password);
        // 接口调用
        Map<String,String> jsonMap = tmspClient.login(iTyutLoginUser);
        // TODO: log
        String msg = jsonMap.get("message");
        System.out.println(msg);
        iTyutLoginUser.setIsLogin(true);
        return true;
    }

    /**
     * 获取考试安排
     * @return
     */
    @Override
    public String getExamSchedule() {
        ForestResponse<String> response = tmspClient.getExamSchedule(RequestConstant.PAGINATION);
        System.out.println(response.getContent());
        return null;
    }

    @Override
    public String getAcademicYearTerm() {
        return null;
    }

    @Override
    public String getPjrsjPageListJson() throws Exception {
        // 获取默认请求头，并添加自定义请求
        Map<String, Object> headers = new HashMap<>(Map.copyOf(RequestConstant.DEFUALT_HEADERS));
        // 获取评教列表
        tmspClient.getPjToken();
        String pjrsjPageListJson = tmspClient.getPjrsjPageListJson(new PjrsPagination());
        JSONArray rows = JSON.parseObject(pjrsjPageListJson).getJSONArray("rows");
        // 多线程处理
        int taskNum = rows.size();
        ExecutorService executorService = Executors.newFixedThreadPool(taskNum);
        List<Callable<String>> tasks = new ArrayList<>();
        // 评教的课程依次添加
        for (int i = 0; i < taskNum; i++) {
            JSONObject courseObject = rows.getJSONObject(i);
            int finalI = i;
            tasks.add(() -> {
                TeachEvaluationPaper info = courseObject.toJavaObject(TeachEvaluationPaper.class);
                // 处理已评教的课程
                if (!"是".equals(info.getIsEvaluated()))
                    return doPages(headers, info);
                return  finalI + ". 已评教";
            });
        }
        // 执行任务
        List<Future<String>> futures = executorService.invokeAll(tasks);
        // 处理任务的结果
        for (Future<String> future : futures) {
            try {
                String result = future.get();
                System.out.println(result);
                // 处理结果...
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        // 关闭线程池
        executorService.shutdown();
        return null;
    }
    @Override
    public StudentInfo getStudentInfo() throws Throwable {
        String htmlStr = tmspClient.getStudentInfo();
        StudentInfo studentInfo = new StudentInfo();
        // TODO: info?
        return grabHtmlInfoToBean(htmlStr, studentInfo, ".profile-info-row:contains({}) .profile-info-value span");
    }
    @Override
    public String getTeachBuildingJson(@NotNull TyutCampus campus) {
        String result;
        if (campus.equals(TyutCampus.ALL)) {
            result = tmspClient.getTeachBuildings();
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put("xqh", campus.getId());
            String json = JSON.toJSONString(map);
            result = tmspClient.getTeachBuildingByCampus(json);
        }
        return result;
    }

    @Override
    public List<TeachBuilding> getTeachBuilding(@NotNull TyutCampus campus) {
        String json = getTeachBuildingJson(campus);
        return JSON.parseArray(json, TeachBuilding.class);
    }

    /**
     * 从HTML中提取信息并封装到Bean中
     * @apiNote bean 必须有符合规范的 setter 方法
     * @param htmlStr
     * @param bean
     * @return
     * @param <T>
     * @throws Throwable
     */
    @NotNull
    private  static <T> T grabHtmlInfoToBean (String htmlStr, T bean, String template) throws Throwable {
        // 使用JSoup解析HTML
        Document doc = Jsoup.parse(htmlStr);
        // 使用 MethodHandles 获取 MethodHandle
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType setterMethod = MethodType.methodType(void.class, String.class);
        // 使用传统反射获取对象的所有属性并处理
        Field[] fields = StudentInfo.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String methodName = StrUtil.toCamelCase("set" + StrUtil.upperFirst(field.getName()));
            TmspConvert annotation = field.getAnnotation(TmspConvert.class);
            Element element;
            if (annotation != null) {
                String value = annotation.value();
                element = doc.select(StrUtil.format(template, value)).first();
            } else {
                element = doc.select(template).first();
            }
            MethodHandle setterHandle = lookup.findVirtual(bean.getClass(), methodName, setterMethod);
            if (element != null) setterHandle.invoke(bean, element.text());
        }
        return bean;
    }

    @Override
    public GradeReport getGradeReport() {
        String htmlStr = tmspClient.getGrade();
        // 使用JSoup解析HTML
        Document document = Jsoup.parse(htmlStr);
        // 从HTML结构中选择学生信息的元素
        Elements profileInfoRows = document.select(".profile-info-row");
        // 遍历每一行学生信息
        HashMap<String, String> map = new HashMap<>();
        for (Element profileInfoRow : profileInfoRows) {
            // 获取信息名称和值
            String infoKey = profileInfoRow.select(".profile-info-name").text().trim();
            String infoValue = profileInfoRow.select(".profile-info-value span").text().trim();
            map.put(infoKey, infoValue);
        }
        String jsonString = JSON.toJSONString(map);
        return JSON.parseObject(jsonString, GradeReport.class);
    }

    /**
     * 完成卷子
     * @param headers
     * @param info
     * @throws Exception
     * @return 结果
     */
    private String doPages(Map<String, Object> headers, TeachEvaluationPaper info) throws Exception {
        List<TeachEvaluationQuestion> questions = tmspClient.getTreeJson(info.getPaperCode());
        for (TeachEvaluationQuestion question : questions) {
            BeanUtil.copyProperties(info, question);
            autoWriteAnwser(question);
        }
        HashMap<String, String> resultMap = new HashMap<>();
        // 提交答案
        for (int j = 0; j < questions.size(); j++) {
            TeachEvaluationQuestion question = questions.get(j);
            resultMap.put("list["+ j +"][Sjdm]", question.getPaperCode());
            resultMap.put("list["+ j +"][Stdm]", question.getQuestionCode());
            resultMap.put("list["+ j +"][Stmc]", question.getQuestionTitle());
            resultMap.put("list["+ j +"][Stlbdm]", question.getQuestionType());
            resultMap.put("list["+ j +"][Dadm]", question.getAnswerCode());
            resultMap.put("list["+ j +"][Kch]", question.getCourseId());
            resultMap.put("list["+ j +"][Kxh]", question.getCourseNumber());
            resultMap.put("list["+ j +"][Bpr]", question.getTeacherId());
        }
        // 访问评教卷地址设置cookie
        String htmlStr = tmspClient.getPjSubmitToken(info.getCourseId(), info.getCourseNumber(), info.getPaperCode(), info.getTeacherId());
        String csrfToken = getCSRFToken(htmlStr, headers, resultMap, info);
        // 时间限制
        TimeUnit.SECONDS.sleep(30);
        return tmspClient.submitAnwser(resultMap, csrfToken);
    }

    @NotNull
    private String getCSRFToken(String htmlStr, Map<String, Object> headers, HashMap<String, String> resultMap, TeachEvaluationPaper info) throws Exception {
        // 使用Jsoup解析HTML
        Document document = Jsoup.parse(htmlStr);
        // 提取__RequestVerificationToken的值
        Element csrfTokenElement = document.select("input[name=__RequestVerificationToken]").first();
        if (csrfTokenElement != null) {
            String csrfTokenValue = csrfTokenElement.val();
            return csrfTokenValue;
        } else {
            throw new Exception("获取__RequestVerificationToken失败");
        }
    }

    public void autoWriteAnwser(TeachEvaluationQuestion question) {
        String questionType = question.getQuestionType();
        if ("01".equals(questionType)) {
            question.setAnswerCode("03");
        } else if ("02".equals(questionType)) {
            question.setAnswerCode("良好");
        } else if ("03".equals(questionType)) {
            question.setAnswerCode("100");
        }
    }
}
