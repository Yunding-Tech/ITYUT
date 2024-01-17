package fun.ruafafa.ityut.interceptor;

import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.interceptor.Interceptor;
import com.dtflys.forest.reflection.ForestMethod;

public class ITyutUserInterceptor<T> implements Interceptor<T> {

    @Override
    public void onInvokeMethod(ForestRequest request, ForestMethod method, Object[] args) {
        request.addAttachment("account" ,args[0]);
    }
}
