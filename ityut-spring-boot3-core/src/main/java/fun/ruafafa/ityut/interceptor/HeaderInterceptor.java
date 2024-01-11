package fun.ruafafa.ityut.interceptor;

import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.interceptor.Interceptor;
import fun.ruafafa.ityut.constant.RequestConstant;

public class HeaderInterceptor implements Interceptor {

    @Override
    public boolean beforeExecute(ForestRequest req) {
        req.addHeader(RequestConstant.DEFUALT_HEADERS);
        return true;
    }
}
