package fun.ruafafa.ityut.interceptor;

import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.interceptor.Interceptor;
import com.dtflys.forest.reflection.ForestMethod;
import fun.ruafafa.ityut.manager.ITyutUserManager;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ITyutUserInterceptor implements Interceptor {

    @Override
    public void onInvokeMethod(ForestRequest request, ForestMethod method, Object[] args) {
        request.addAttachment("account" ,args[0]);
    }
}
