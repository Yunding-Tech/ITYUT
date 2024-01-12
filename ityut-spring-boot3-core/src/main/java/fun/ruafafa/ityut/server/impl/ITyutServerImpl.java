package fun.ruafafa.ityut.server.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import fun.ruafafa.ityut.annotation.TmspConvert;
import fun.ruafafa.ityut.dto.*;
import fun.ruafafa.ityut.client.TmspClient;
import fun.ruafafa.ityut.server.ITyutService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;

/**
 * ITyut 服务调用
 */
@Component
@RequiredArgsConstructor
public class ITyutServerImpl implements ITyutService {

    private final TmspClient tmspClient;

    @Override
    public String getAcademicYearTerm() {
        return null;
    }

    @Override
    public String getPjrsjPageListJson() throws Exception {
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
            TeachEvaluationPaper info = courseObject.toJavaObject(TeachEvaluationPaper.class);
            // 处理评教的课程
            int finalI = i;
            tasks.add(() -> {
                // 处理已评教的课程
                String s = doPages(info);
                System.out.println(s);
                if (!"是".equals(info.getIsEvaluated()))
                    return doPages(info);
                return finalI + ". 已评教";
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
    public StudentInfo getStudentInfo(String account) throws Throwable {
        String htmlStr = tmspClient.getStudentInfo(account);
        StudentInfo studentInfo = new StudentInfo();
        // TODO: info?
        return grabHtmlInfoToBean(htmlStr, studentInfo, ".profile-info-row:contains({}) .profile-info-value span");
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


    /**
     * 完成卷子
     * @param info
     * @throws Exception
     * @return 结果
     */
    private String doPages(TeachEvaluationPaper info) throws Exception {
        System.out.println("-------------------");
        System.out.println(info.getPaperCode());
        System.out.println("-------------------");
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
        String csrfToken = getCSRFToken(htmlStr);
        // 时间限制
        TimeUnit.SECONDS.sleep(30);
        return tmspClient.submitAnwser(resultMap, csrfToken);
    }

    @NotNull
    private String getCSRFToken(String htmlStr) throws Exception {
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
