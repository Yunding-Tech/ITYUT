package fun.ruafafa.ityut.server;

import fun.ruafafa.ityut.constant.TyutCampus;
import fun.ruafafa.ityut.dto.TeachBuilding;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ITyutItergratedService {

    String getTeachBuildingJson(@NotNull String account, @NotNull TyutCampus campus);

    List<TeachBuilding> getTeachBuilding(@NotNull String account, @NotNull TyutCampus campus);
}
