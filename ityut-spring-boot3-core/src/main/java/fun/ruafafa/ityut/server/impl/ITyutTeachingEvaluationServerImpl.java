package fun.ruafafa.ityut.server.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import fun.ruafafa.ityut.client.TmspTeachingEvaluationClient;
import fun.ruafafa.ityut.dto.PjrsPagination;
import fun.ruafafa.ityut.dto.TeachEvaluationPaper;
import fun.ruafafa.ityut.dto.TeachEvaluationQuestion;
import jakarta.annotation.Resource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
@Component
public class ITyutTeachingEvaluationServerImpl implements fun.ruafafa.ityut.server.ITyutTeachingEvaluationServer {

    @Resource
    private TmspTeachingEvaluationClient tteClient;

    @Override
    public String oneClickEvaluation(String account) {
        // 获取评教列表
        tteClient.getPjToken(account);
        String pjrsjPageListJson = tteClient.getPjrsjPageListJson(account, new PjrsPagination());
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
                String s = doPages(account, info);
                System.out.println(s);
                if (!"是".equals(info.getIsEvaluated()))
                    return doPages(account, info);
                return finalI + ". 已评教";
            });
        }
        // 执行任务
        List<Future<String>> futures = null;
        try {
            futures = executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("评教执行失败");
        }
        // 处理任务的结果
        for (Future<String> future : futures) {
            try {
                String result = future.get();
                System.out.println(result);
                // 处理结果...
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                throw new RuntimeException("评教执行失败");
            }
        }
        // 关闭线程池
        executorService.shutdown();
        return null;
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

    /**
     * 完成卷子
     *
     * @param info
     * @return 结果
     * @throws Exception
     */
    String doPages(String account, TeachEvaluationPaper info) throws InterruptedException {
//        System.out.println("-------------------");
//        System.out.println(info.getPaperCode());
//        System.out.println("-------------------");
        List<TeachEvaluationQuestion> questions = tteClient.getTreeJson(account, info.getPaperCode());
        for (TeachEvaluationQuestion question : questions) {
            BeanUtil.copyProperties(info, question);
            autoWriteAnwser(question);
        }
        HashMap<String, String> resultMap = new HashMap<>();
        // 提交答案
        for (int j = 0; j < questions.size(); j++) {
            TeachEvaluationQuestion question = questions.get(j);
            resultMap.put("list[" + j + "][Sjdm]", question.getPaperCode());
            resultMap.put("list[" + j + "][Stdm]", question.getQuestionCode());
            resultMap.put("list[" + j + "][Stmc]", question.getQuestionTitle());
            resultMap.put("list[" + j + "][Stlbdm]", question.getQuestionType());
            resultMap.put("list[" + j + "][Dadm]", question.getAnswerCode());
            resultMap.put("list[" + j + "][Kch]", question.getCourseId());
            resultMap.put("list[" + j + "][Kxh]", question.getCourseNumber());
            resultMap.put("list[" + j + "][Bpr]", question.getTeacherId());
        }
        // 访问评教卷地址设置cookie
        String htmlStr = tteClient.getPjSubmitToken(account,info.getCourseId(), info.getCourseNumber(), info.getPaperCode(), info.getTeacherId());
        String csrfToken = getCSRFToken(htmlStr);
        // 时间限制
        TimeUnit.SECONDS.sleep(30);
        return tteClient.submitAnwser(account, resultMap, csrfToken);
    }

    String getCSRFToken(String htmlStr) {
        // 使用Jsoup解析HTML
        Document document = Jsoup.parse(htmlStr);
        // 提取__RequestVerificationToken的值
        Element csrfTokenElement = document.select("input[name=__RequestVerificationToken]").first();
        if (csrfTokenElement != null) {
            String csrfTokenValue = csrfTokenElement.val();
            return csrfTokenValue;
        } else {
            throw new RuntimeException("获取__RequestVerificationToken失败");
        }
    }

}
