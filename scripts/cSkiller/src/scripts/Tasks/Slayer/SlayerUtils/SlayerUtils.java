package scripts.Tasks.Slayer.SlayerUtils;

import org.tribot.api.General;
import org.tribot.script.sdk.Equipment;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Quest;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.cache.BankCache;
import org.tribot.script.sdk.query.Query;
import scripts.BankManager;
import scripts.GEManager.GEItem;
import scripts.ItemID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlayerUtils {


    public static ArrayList<GEItem> getPurchaseList() {
        if (!BankCache.isInitialized()) {
            Log.info("Initializing BankCache");
            BankManager.open(true);
            BankCache.update();
        }
        ArrayList<GEItem> list = new ArrayList<>(Arrays.asList(
                new GEItem(ItemID.PRAYER_POTION[0], SlayerVars.get().restockNumber + 5, 15),
                new GEItem(ItemID.EXPEDITIOUS_BRACELET, 10, 50),
                new GEItem(ItemID.FALADOR_TELEPORT, SlayerVars.get().restockNumber * 2, 50),
                new GEItem(ItemID.VARROCK_TELEPORT, SlayerVars.get().restockNumber * 5, 50),
                new GEItem(ItemID.CAMELOT_TELEPORT, SlayerVars.get().restockNumber * 2, 50),
                new GEItem(ItemID.ARDOUGNE_TELEPORT, SlayerVars.get().restockNumber * 2, 50),
                new GEItem(ItemID.LUMBRIDGE_TELEPORT, SlayerVars.get().restockNumber * 2, 50),
                new GEItem(ItemID.SHANTAY_PASS, 20, 550),
                new GEItem(ItemID.BRASS_KEY, 1, 250),
                new GEItem(ItemID.ROPE, 1, 250),
                new GEItem(ItemID.MIRROR_SHIELD, 1, 250),
                new GEItem(ItemID.MONKFISH, SlayerVars.get().restockNumber * 15, 25),
                new GEItem(ItemID.SUPER_COMBAT_POTION[0], SlayerVars.get().restockNumber / 2, 15),
                new GEItem(ItemID.ANTIDOTE_PLUS_PLUS[0], SlayerVars.get().restockNumber / 4, 50),
                // new GEItem(ItemID.MONKFISH, SlayerVars.get().restockNumber * General.random(30, 40), 50),
                new GEItem(ItemID.AMULET_OF_GLORY[2], SlayerVars.get().restockNumber / 3, 20),
                new GEItem(ItemID.STAMINA_POTION[0], SlayerVars.get().restockNumber / 2, 15),
                new GEItem(ItemID.RING_OF_DUELING[0], SlayerVars.get().restockNumber / 3, 25),
                new GEItem(ItemID.GAMES_NECKLACE[0], SlayerVars.get().restockNumber / 3, 25),
                new GEItem(ItemID.SKILLS_NECKLACE[0], SlayerVars.get().restockNumber / 4, 25),
                new GEItem(ItemID.RING_OF_WEALTH[0], 1, 25),
                new GEItem(ItemID.IORWERTH_CAMP_TELEPORT, 5, 30),
                new GEItem(ItemID.TINDERBOX, 1, 500),
                new GEItem(ItemID.BAG_OF_SALT, 250, 200)
        ));
        if (BankCache.getStack(ItemID.LIT_CANDLE) == 0) {
            Log.info("Adding Candle to buy list");
            list.add(new GEItem(ItemID.CANDLE, 1, 500));
        }
        if (Quest.LUNAR_DIPLOMACY.getState().equals(Quest.State.COMPLETE)) {
            Log.info("Adding Lunar Isle Teleport to buy list");
            list.add(new GEItem(ItemID.LUNAR_ISLE_TELEPORT, 5, 30));
        }
        if (Quest.PRIEST_IN_PERIL.getState().equals(Quest.State.COMPLETE)) {
            Log.info("Adding Salve Graveyard Teleport to buy list");
            list.add(new GEItem(ItemID.SALVE_GRAVEYARD_TELEPORT, SlayerVars.get().restockNumber * 2, 50));
        }
        if (Skill.SLAYER.getActualLevel() >= 65) {
            Log.info("Adding Leaf bladed battleaxe to buy list");
            list.add(new GEItem(ItemID.LEAFBLADED_BATTLEAXE, 1, 50));
        } else if (Skill.SLAYER.getActualLevel() >= 55) {
            Log.info("Adding Leaf bladed sword to buy list");
            list.add(new GEItem(ItemID.LEAFBLADED_SWORD, 1, 50));
        }
        if (Skill.SLAYER.getActualLevel() >= 60) {
            Log.info("Adding Boots of Stone to buy list");
            list.add(new GEItem(ItemID.BOOTS_OF_STONE, 1, 250));
        }
        if (Skill.SLAYER.getActualLevel() >= 75) {
            Log.info("Adding Rock Hammer to buy list");
            list.add(new GEItem(ItemID.ROCK_HAMMER, 1, 450));
        }
        if (!Query.equipment().nameContains("Slayer helm").isAny()){
            list.add(new GEItem(ItemID.EARMUFFS, 1, 300));
            list.add(new GEItem(ItemID.SPINY_HELMET, 1, 300));
            list.add(new GEItem(ItemID.FACEMASK, 1, 300));
            list.add(new GEItem(ItemID.NOSE_PEG, 1, 300));
        }
        return list;
    }
}
