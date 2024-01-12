package fun.ruafafa.ityut.server.impl;

import com.alibaba.fastjson.JSON;
import fun.ruafafa.ityut.client.TmspItergratedClient;
import fun.ruafafa.ityut.constant.TyutCampus;
import fun.ruafafa.ityut.dto.TeachBuilding;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;

@Component
public class ITyutItergratedServiceImpl implements fun.ruafafa.ityut.server.ITyutItergratedService {

    @Resource
    private TmspItergratedClient tmspItergratedClient;

    @Override
    public String getTeachBuildingJson(String account, @NotNull TyutCampus campus) {
        String result;
        if (campus.equals(TyutCampus.ALL)) {
            result = tmspItergratedClient.getTeachBuildings();
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put("xqh", campus.getId());
            String json = JSON.toJSONString(map);
            result = tmspItergratedClient.getTeachBuildingByCampus(account, json);
        }
        return result;
    }

    @Override
    public List<TeachBuilding> getTeachBuilding(String account, @NotNull TyutCampus campus) {
        String json = getTeachBuildingJson(account, campus);
        return JSON.parseArray(json, TeachBuilding.class);
    }
}
