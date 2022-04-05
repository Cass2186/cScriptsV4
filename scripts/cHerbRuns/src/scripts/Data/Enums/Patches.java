package scripts.Data.Enums;

import lombok.Getter;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.types.Area;
import scripts.Data.Areas;
import scripts.Data.Const;
import scripts.Utils;
import scripts.Varbits;

import java.util.List;
import java.util.stream.Collectors;

public enum Patches {

    FALADOR_HERB_PATCH(List.of(Const.FALADOR_HERB_PATCH_ID), Areas.FALADOR_AREA),
    CATHERBY_HERB_PATCH(List.of(Const.CATHERBY_HERB_PATCH_ID), Areas.CATHERBY_AREA),
    ARDOUGNE_HERB_PATCH(List.of(Const.ARDOUGNE_HERB_PATCH_ID), Areas.ARDOUGNE_AREA),
    HOSIDIUS_HERB_PATCH(List.of(Const.HOISIDIUS_HERB_PATCH_ID), Areas.HOSIDIUS_AREA),
    MORYTANIA_HERB_PATCH(List.of(Const.MORYTANIA_HERB_PATCH_ID), Areas.MORYTANIA_AREA,
            Quest.PRIEST_IN_PERIL.getState().equals(Quest.State.COMPLETE)),
    FARMING_GUILD_HERB_PATCH(List.of(Const.FARMING_GUILD_HERB_PATCH_ID),
            Areas.FARMING_GUILD_ALLOTMENT_AREA,
            Utils.getVarBitValue(Varbits.KOUREND_FAVOR_HOSIDIUS.getId()) >= 600),

    FALADOR_ALLOTMENT_PATCH(List.of(Const.FALADOR_NW_ALLOTMENT_ID, Const.FALADOR_SE_ALLOTMENT_ID), Areas.FALADOR_AREA),
    CATHERBY_ALLOTMENT_PATCH(List.of(Const.CATHERBY_N_ALLOTMENT_ID, Const.CATHERBY_S_ALLOTMENT_ID), Areas.CATHERBY_AREA),
    ARDOUGNE_ALLOTMENT_PATCH(List.of(Const.ARDOUGNE_S_ALLOTMENT_ID, Const.ARDOUGNE_N_ALLOTMENT_ID), Areas.ARDOUGNE_AREA),
    HOSIDIUS_ALLOTMENT_PATCH(List.of(Const.HOISIDIUS_SW_ALLOTMENT_ID, Const.HOISIDIUS_SW_ALLOTMENT_ID), Areas.HOSIDIUS_AREA),
    MORYTANIA_ALLOTMENT_PATCH(List.of(Const.MORYTANIA_N_ALLOTMENT_ID, Const.MORYTANIA_S_ALLOTMENT_ID), Areas.MORYTANIA_AREA),
    //TODO ADD this
    //   FARMING_GUILD_ALLOTMENT_PATCH(List.of(Const.FARMING_GUILD_HERB_PATCH_ID), Const.FARMING_GUILD_ALLOTMENT_AREA),

    VARROCK_TREE_PATCH(List.of(Const.VAROCK_TREE_PATCH_ID), Areas.VARROCK_TREE_AREA),
    LUMBRIDGE_TREE_PATCH(List.of(Const.LUMBRIDGE_TREE_PATCH_ID), Areas.LUMBRIDGE_TREE_AREA),
    TAVERLY_TREE_PATCH(List.of(Const.TAVERLY_TREE_PATCH_ID), Areas.TAVERLY_TREE_AREA),
    FALADOR_TREE_PATCH(List.of(Const.GNOME_STRONGHOLD_TREE_PATCH_ID), Areas.FALADOR_TREE_AREA),
    STRONGHOLD_TREE_PATCH(List.of(Const.GNOME_STRONGHOLD_TREE_PATCH_ID), Areas.TREE_GNOME_TREE_AREA),
    FARMING_GUILD_TREE_PATCH(List.of(Const.FARMING_GUILD_TREE_PATCH_ID), Areas.FARMING_GUILD_TREE_AREA),

    STRONGHOLD_FRUIT_TREE_PATCH(List.of(Const.STRONGHOLD_FRUIT_TREE_ID), Areas.STRONGHOLD_FRUIT_TREE_AREA),
    TGV_FRUIT_TREE_PATCH(List.of(Const.TGV_FRUIT_TREE_ID), Areas.TGV_FRUIT_TREE_AREA),
    BRIMHAVEN_FRUIT_TREE_PATCH(List.of(Const.BRIMHAVEN_FRUIT_TREE_ID), Areas.BRIMHAVEN_FRUIT_TREE_AREA),
    FARMING_GUILD_FRUIT_TREE_PATCH(List.of(Const.GUILD_TREE_PATCH_ID), Areas.FARMING_GUILD_TREE_AREA),//TODO CHECK
    CATHERBY_FRUIT_TREE_PATCH(List.of(Const.CATHERBY_FRUIT_TREE_ID), Areas.CATHERBY_FRUIT_TREE_AREA);

    @Getter
    private List<Integer> patchIds;

    @Getter
    private final Area patchArea;
    @Getter
    private boolean meetsRequirement;

    Patches(List<Integer> id, Area area) {
        this.patchIds = id;
        this.patchArea= area;
        if (this.patchArea == null){
            Log.error("Patch area is null");
        }
        meetsRequirement = true;
    }

    Patches(List<Integer> id, Area area, boolean requirement) {
        this.patchIds = id;
        this.patchArea= area;
        if (this.patchArea == null){
            Log.error("Patch area is null");
        }
        meetsRequirement = requirement;
    }

    public static List<Patches> getAllHerbPatches(){
        List<Patches> all =  List.of(
                FALADOR_HERB_PATCH,
                CATHERBY_HERB_PATCH,
                ARDOUGNE_HERB_PATCH,
                MORYTANIA_HERB_PATCH,
                HOSIDIUS_HERB_PATCH,
                FARMING_GUILD_HERB_PATCH
        );
      return  all.stream().filter(p-> p.meetsRequirement).collect(Collectors.toList());
    }

}

