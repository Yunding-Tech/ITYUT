package fun.ruafafa.ityut.interceptor;

import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.interceptor.Interceptor;

public class ITyutUserLoginInterceptor implements Interceptor {

    @Override
    public void onSuccess(Object data, ForestRequest request, ForestResponse response) {
        // 是否成功登录？
        String content = response.getContent();
        if (!content.contains("登录成功")) {
            response.setStatusCode(500);
        }
    }


}
