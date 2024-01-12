package fun.ruafafa.ityut.client;

import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Redirection;
import com.dtflys.forest.annotation.Var;
import com.dtflys.forest.http.ForestResponse;

public interface AddressClient {

    @Get(
            url = "http://{address}/",
            readTimeout = 10000
    )
    ForestResponse<?> checkAddress(@Var("address") String address);
}
