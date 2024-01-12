package fun.ruafafa.ityut.client;

import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.annotation.Post;
import fun.ruafafa.ityut.annotation.TmspUserApi;

@TmspUserApi
public interface TmspItergratedClient {

    @Post(
            url = "/Tschedule/Zhcx/GetJxlhByXqh",
            readTimeout = 10000,
            contentType = "application/x-www-form-urlencoded"
    )
    String getTeachBuildingByCampus(String account, @Body("queryJson") String json);

    @Post (
            url = "/Tschedule/Zhcx/GetJxlhByXqh",
            readTimeout = 10000,
            contentType = "application/x-www-form-urlencoded"
    )
    String getTeachBuildings();
}
