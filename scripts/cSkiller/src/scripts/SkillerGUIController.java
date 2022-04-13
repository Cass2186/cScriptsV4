package scripts;

import com.allatori.annotations.DoNotRename;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.tribot.api.General;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Skill;
import org.tribot.script.sdk.util.ScriptSettings;
import scripts.Data.*;
import scripts.Data.Enums.Crafting.CraftMethods;
import scripts.Data.Enums.Methods;
import scripts.Tasks.Fishing.Locations.RodLocations;
import scripts.Tasks.Fishing.Locations.ShrimpLocations;
import scripts.Tasks.Magic.Alch;
import scripts.Tasks.Slayer.SlayerConst.CombatPotions;
import scripts.Tasks.Slayer.SlayerUtils.SlayerVars;
import scripts.Tasks.Woodcutting.WoodcuttingData.WcLocations;
import scripts.skillergui.SkillerAbstractGUIController;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

@DoNotRename
public class SkillerGUIController extends SkillerAbstractGUIController {



    @FXML
    @DoNotRename
    private Button atkAddButton, defAddButton, rangedAddButton, strAddButton, resetSkillsButton;

    @FXML
    @DoNotRename
    private VBox combatProgressionBox;

    @FXML
    @DoNotRename
    private ImageView craftingGoalLevel;

    @FXML
    @DoNotRename
    private TextField agilityGoalLevelBox, herbloreGoalLevelBox,
            craftingGoalLevelBox, atkLvlGoalBox, defLvlGoalBox, rangedLvlGoalBox, strLvlGoalBox,
            miningGoalLevelBox, chancetoClickJewleryBox, fletchingGoalLevelBox, firemakingGoalLevelBox,
            lootOverGpText, restockMultiplierBox;

    @FXML
    @DoNotRename
    private TextField woodcuttingGoalLevelBox;


    @FXML
    @DoNotRename
    private TextField cookingGoalLevelBox;

    @FXML
    @DoNotRename
    private TextField fishingGoalLevel;

    @FXML
    @DoNotRename
    private TextField prayerGoalLevelBox;

    @FXML
    @DoNotRename
    private TextField magicGoalLevelBox;

    @FXML
    @DoNotRename
    private TextField thievingGoalLevelBox;

    @FXML
    @DoNotRename
    private TextField smithingGoalLevelBox;

    @FXML
    @DoNotRename
    private TextField runecraftingGoalLevelBox;

    @FXML
    @DoNotRename
    private TextField constructionGoalLevelBox;

    @FXML
    @DoNotRename
    private TextField hunterGoalLevelBox;

    @FXML
    @DoNotRename
    private TextField slayerGoalLevelBox;


    @FXML
    @DoNotRename
    private TextField switchSkillMinTime;

    @FXML
    @DoNotRename
    private TextField switchSkillMaxTime;

    @FXML
    @DoNotRename
    private TextField afkEveryAverage;

    @FXML
    @DoNotRename
    private TextField afkEverySD;

    @FXML
    @DoNotRename
    private TextField afkForAverage;

    @FXML
    @DoNotRename
    private TextField afkForSD;

    @FXML
    @DoNotRename
    private TextField mouseSpeedBox;

    @FXML
    @DoNotRename
    private Slider abc2Modifier;

    @FXML
    @DoNotRename
    private ComboBox<String> startingSkillDropDown;


    @FXML
    @DoNotRename
    private ComboBox<String> logActionBox;

    @FXML
    @DoNotRename
    private ComboBox<String> magicAlchItemBox, craftingMethodsDropDown, slayerCombatPotionBox,
            shrimpLocationsDropDown, troutLocationsDropDown, regularTreeAreaBox, oakTreeAreaBox,
            mapleTreeAreaBox, willowTreeAreaBox;

    @FXML
    @DoNotRename
    private CheckBox useMlmBox1, useFruitStallButton, pointBoosingBox, getBarbarianRodCheckBox;

    @FXML
    @DoNotRename
    private CheckBox useBlastFurnaceBox, preferJewleryOverTeleportsCheckBox;

    @FXML
    @DoNotRename
    public ImageView skillsImage;


    @FXML
    @DoNotRename
    private Slider slayerAbc2ChanceSlider;


    @FXML
    @DoNotRename
    void startButtonPressed(ActionEvent event) {
        General.println("[GUIController]: Start button pressed");

        updateSkillEndLevel(event);
        updateSkillSwitchTimer(event);
        updateAfkDuration(event);
        updateAFKFrequency(event);
        //updateABC2Modifier(event);

        handleCheckBoxes();
        updateFishingLocations();

        SlayerVars.get().minLootValue = lootOverGpText.getText() != null ?
                Integer.parseInt(lootOverGpText.getText()) : 1500;
        SlayerVars.get().restockNumber = restockMultiplierBox.getText() != null ?
                Integer.parseInt(restockMultiplierBox.getText()) : 6;
        // SlayerVars.get().potionToUse =
        SlayerVars.get().pointBoosting = pointBoosingBox.isSelected();
        updateSlayerPotion();
        SlayerVars.get().abc2Chance = (int) slayerAbc2ChanceSlider.getValue() + 1; // adding 1 in case it's set to 0
        //  setUseCBalls(event);
        ScriptSettings settingsHandler = ScriptSettings.getDefault();
        //update slayer vars object in vars
        Vars.get().slayVars = SlayerVars.get();
        val settings = new SkillerSettings();
        settings.setTest("wee");

        Log.info("Settings dir: " + settingsHandler.getDirectory());
        if(settingsHandler.save("lastTes", settings)){
            Log.info("Saved settings as last");
        } else {
            Log.error("FAILED to Save settings as last");
        }
        this.getGUI().close();
    }

    private void handleCheckBoxes() {
        String s = chancetoClickJewleryBox.getText();
        Vars.get().clickAllJeweleryChance = !s.equals("") ?
                Integer.parseInt(chancetoClickJewleryBox.getText()) : Utils.random(25, 45);
        Vars.get().useFruitStalls = useFruitStallButton.isSelected();
        Vars.get().preferJeweleryOverTeleports = preferJewleryOverTeleportsCheckBox.isSelected();
        Vars.get().getBarbarianRod = getBarbarianRodCheckBox.isSelected();
    }


 /*   @FXML
    @DoNotRename
    void setUseCBalls(ActionEvent event) {
        Vars.get().smeltCannonballs = smithCannonballsBox.isSelected();
        if (smithCannonballsBox.isSelected())
            useBlastFurnaceBox.setSelected(false);
    }*/


    @FXML
    @DoNotRename
    void setUseMlmBox(ActionEvent event) {
        General.println("[GUIController]: Updated useMLM Boolean to: " + useMlmBox1.isSelected());
        if (useMlmBox1.isSelected())
            Vars.get().useMLM = true;
        else
            Vars.get().useMLM = false;
    }

    @FXML
    @DoNotRename
    void mouseSpeedAdjusted(ActionEvent event) {
        General.println("[GUIController]: Mouse Speed adjusted to " + mouseSpeedBox.getText());
        Vars.get().mouseSpeed = Integer.parseInt(mouseSpeedBox.getText());
    }

    @FXML
    @DoNotRename
    void updateAFKFrequency(ActionEvent event) {
        Vars.get().afkFrequencyAvg = Integer.parseInt(afkEveryAverage.getText()) * 1000;
        Vars.get().afkFrequencySD = Integer.parseInt(afkEverySD.getText()) * 1000;
        Vars.get().afkTimer = new Timer(General.randomSD(Vars.get().afkFrequencyAvg, Vars.get().afkFrequencySD));
        General.println("[GUIController]: Updated AFK Frequency");
    }

    @FXML
    @DoNotRename
    void updateAfkDuration(ActionEvent event) {
        General.println("[GUIController]: Updated AFK duration");
        Vars.get().afkDurationAvg = Integer.parseInt(afkForAverage.getText()) * 1000;
        Vars.get().afkDurationSD = Integer.parseInt(afkForSD.getText()) * 1000;
    }

    @FXML
    @DoNotRename
    void updateSkillSwitchTimer(ActionEvent event) {
        General.println("[GUIController]: Updated Skill Switch duration: " + switchSkillMinTime.getText());
        Vars.get().skillSwitchMin = Integer.parseInt(switchSkillMinTime.getText()) * 60000;
        Vars.get().skillSwitchMax = Integer.parseInt(switchSkillMaxTime.getText()) * 60000;
        Vars.get().skillSwitchTimer = new Timer(General.random(Vars.get().skillSwitchMin, Vars.get().skillSwitchMax));
        General.println("[GUIController]: Updated Skill Switch duration");
        General.println("[GUIController]: skillSwitchTimer.getRemaining()" + Vars.get().skillSwitchTimer.getRemaining());
    }

    @FXML
    @DoNotRename
    void updateABC2Modifier(MouseDragEvent event) {
        General.println("[GUIController]: Updated ABC2 Modifier to " + event.toString());
        Utils.FACTOR = (abc2Modifier.getValue() / 100);
    }

    @FXML
    @DoNotRename
    void updateSkillEndLevel(ActionEvent event) {
        General.println("[GUIController]: Updating end level");
        SkillTasks.AGILITY.setEndLevel(Integer.parseInt(String.valueOf(agilityGoalLevelBox.getText())));
        SkillTasks.HERBLORE.setEndLevel(Integer.parseInt(String.valueOf(herbloreGoalLevelBox.getText())));
        SkillTasks.CRAFTING.setEndLevel(Integer.parseInt(String.valueOf(craftingGoalLevelBox.getText())));
        SkillTasks.CRAFTING.setEndLevel(Integer.parseInt(String.valueOf(craftingGoalLevelBox.getText())));
        SkillTasks.FLETCHING.setEndLevel(Integer.parseInt(String.valueOf(fletchingGoalLevelBox.getText())));
        SkillTasks.MINING.setEndLevel(Integer.parseInt(String.valueOf(miningGoalLevelBox.getText())));
        SkillTasks.FIREMAKING.setEndLevel(Integer.parseInt(String.valueOf(firemakingGoalLevelBox.getText())));
        SkillTasks.WOODCUTTING.setEndLevel(Integer.parseInt(String.valueOf(woodcuttingGoalLevelBox.getText())));
        SkillTasks.COOKING.setEndLevel(Integer.parseInt(String.valueOf(cookingGoalLevelBox.getText())));
        SkillTasks.FISHING.setEndLevel(Integer.parseInt(String.valueOf(fishingGoalLevel.getText())));
        SkillTasks.PRAYER.setEndLevel(Integer.parseInt(String.valueOf(prayerGoalLevelBox.getText())));
        SkillTasks.MAGIC.setEndLevel(Integer.parseInt(String.valueOf(magicGoalLevelBox.getText())));
        SkillTasks.SLAYER.setEndLevel(Integer.parseInt(String.valueOf(slayerGoalLevelBox.getText())));
        SkillTasks.CONSTRUCTION.setEndLevel(Integer.parseInt(String.valueOf(constructionGoalLevelBox.getText())));
        SkillTasks.THIEVING.setEndLevel(Integer.parseInt(String.valueOf(thievingGoalLevelBox.getText())));
        SkillTasks.RUNECRAFTING.setEndLevel(Integer.parseInt(String.valueOf(runecraftingGoalLevelBox.getText())));
        SkillTasks.HUNTER.setEndLevel(Integer.parseInt(String.valueOf(hunterGoalLevelBox.getText())));

        SkillTasks.SMITHING.setEndLevel(Integer.parseInt(String.valueOf(smithingGoalLevelBox.getText())));
        General.println("[GUIController]: Updating end level");

    }

    @FXML
    @DoNotRename
    void skillSelected(ActionEvent event) {

        switch (startingSkillDropDown.getValue()) {
            case ("AGILITY"):
                Vars.get().currentTask = SkillTasks.AGILITY;
                break;
            case ("FISHING"):
                Vars.get().currentTask = SkillTasks.FISHING;
                break;
            case ("FIREMAKING"):
                Vars.get().currentTask = SkillTasks.FIREMAKING;
                break;
            case ("CRAFTING"):
                Vars.get().currentTask = SkillTasks.CRAFTING;
                break;
            case ("COOKING"):
                Vars.get().currentTask = SkillTasks.COOKING;
                break;
            case ("FLETCHING"):
                Vars.get().currentTask = SkillTasks.FLETCHING;
                break;
            case ("HERBLORE"):
                Vars.get().currentTask = SkillTasks.HERBLORE;
                break;
            case ("HUNTER"):
                Vars.get().currentTask = SkillTasks.HUNTER;
                break;
            case ("MAGIC"):
                Vars.get().currentTask = SkillTasks.MAGIC;
                break;
            case ("MINING"):
                Vars.get().currentTask = SkillTasks.MINING;
                break;
            case ("PRAYER"):
                Vars.get().currentTask = SkillTasks.PRAYER;
                break;
            case ("WOODCUTTING"):
                Vars.get().currentTask = SkillTasks.WOODCUTTING;
                break;
            case ("PEST_CONTROL"):
                Vars.get().currentTask = SkillTasks.PEST_CONTROL;
                break;
            case ("CONSTRUCTION"):
                Vars.get().currentTask = SkillTasks.CONSTRUCTION;
                break;
            case ("SLAYER"):
                Vars.get().currentTask = SkillTasks.SLAYER;
                break;
            case ("SMITHING"):
                Vars.get().currentTask = SkillTasks.SMITHING;
            case ("THIEVING"):
                Vars.get().currentTask = SkillTasks.THIEVING;
                break;
        }
    }

    @FXML
    @DoNotRename
    void updateFishingLocations() {
        if (troutLocationsDropDown.getValue() != null) {
            switch (shrimpLocationsDropDown.getValue()) {
                case ("Lumbridge"):
                    Vars.get().fishingLocation = ShrimpLocations.LUMBRIDGE_SWAMP;
                    break;
                case ("Catherby"):
                    Vars.get().fishingLocation = ShrimpLocations.CATHERBY;
                    break;
                default:
                    Vars.get().fishingLocation = ShrimpLocations.LUMBRIDGE_SWAMP;
            }
        } else {
            Vars.get().fishingLocation = ShrimpLocations.LUMBRIDGE_SWAMP;
        }
        if (troutLocationsDropDown.getValue() != null) {
            switch (troutLocationsDropDown.getValue()) {
                case ("Barbarian_village"):
                    Vars.get().fishingLocation = RodLocations.BARBARIAN_VILLAGE;
                    break;
                case ("Ottos_grotto"):
                    if (Skill.FISHING.getActualLevel() >= RodLocations.OTTOS_GROTTO.getRequiredLevel()) {
                        Vars.get().fishingLocation = RodLocations.OTTOS_GROTTO;
                        break;
                    }
                case ("Al_kharid"):
                    Vars.get().fishingLocation = RodLocations.AL_KHARID;
                    break;

                default:
                    Vars.get().fishingLocation = RodLocations.BARBARIAN_VILLAGE;
            }
        } else {
            Vars.get().fishingLocation = RodLocations.BARBARIAN_VILLAGE;
        }
    }

    @FXML
    @DoNotRename
    void updateSlayerPotion() {
        if (slayerCombatPotionBox.getValue() != null) {
            switch (slayerCombatPotionBox.getValue()) {
                case ("Super_attack"):
                    SlayerVars.get().potionToUse = ItemID.SUPER_ATTACK_POTION;
                    break;
                case ("Super_strength"):
                    SlayerVars.get().potionToUse = ItemID.SUPER_STRENGTH_POTION;
                    break;
                case ("Ranging"):
                    SlayerVars.get().potionToUse = ItemID.RANGING_POTION;
                    break;
                case ("Super_defence"):
                    SlayerVars.get().potionToUse = ItemID.SUPER_DEFENCE_POTION;
                    break;
                default:
                    SlayerVars.get().potionToUse = ItemID.SUPER_COMBAT_POTION;
            }
        } else {
            SlayerVars.get().potionToUse = ItemID.SUPER_COMBAT_POTION;
        }
    }


    @FXML
    @DoNotRename
    void updateAlchItem(ActionEvent event) {
        for (Alch.AlchItems item : Alch.AlchItems.values()) {
            String s = magicAlchItemBox.getValue().replace(" ", "_");
            if (s.contains(item.toString())) {
                Vars.get().alchItem = item;
                Log.info("[Debug]: Updated Alch item to " + item.toString());
                break;
            }
        }
    }


    @FXML
    @DoNotRename
    void addStrSkillTaskToList(ActionEvent event) {
        Vars.get().combatTaskList.add(new CombatTask(Skills.SKILLS.STRENGTH,
                Integer.parseInt(strLvlGoalBox.getText())));
        combatProgressionBox.getChildren().add(new Text("Train Strength to Lvl " + strLvlGoalBox.getText()));
    }

    @FXML
    @DoNotRename
    void addAtkSkillTaskToList(ActionEvent event) {
        Vars.get().combatTaskList.add(new CombatTask(Skills.SKILLS.ATTACK,
                Integer.parseInt(atkLvlGoalBox.getText())));
        combatProgressionBox.getChildren().add(new Text("Train Attack to Lvl " + atkLvlGoalBox.getText()));
    }

    @FXML
    @DoNotRename
    void addDefSkillTaskToList(ActionEvent event) {
        Vars.get().combatTaskList.add(new CombatTask(Skills.SKILLS.DEFENCE,
                Integer.parseInt(defLvlGoalBox.getText())));
        combatProgressionBox.getChildren().add(new Text("Train Defence to Lvl " + defLvlGoalBox.getText()));
    }

    @FXML
    @DoNotRename
    void addRangedSkillTaskToList(ActionEvent event) {
        Vars.get().combatTaskList.add(new CombatTask(Skills.SKILLS.RANGED,
                Integer.parseInt(rangedLvlGoalBox.getText())));
        combatProgressionBox.getChildren().add(new Text("Train Ranged to Lvl " + defLvlGoalBox.getText()));
    }

    @FXML
    @DoNotRename
    void addWCAreaToLists(){
        mapleTreeAreaBox.getItems().add(WcLocations.SEERS_MAPLES.toString());
        willowTreeAreaBox.getItems().add(WcLocations.PORT_SARIM_WILLOWS.toString());
        willowTreeAreaBox.getItems().add(WcLocations.DRAYNOR_WILLOWS.toString());
        willowTreeAreaBox.getItems().add(WcLocations.SEERS_WILLOWS.toString());
        regularTreeAreaBox.getItems().add(WcLocations.VARROCK_WEST_TREES.toString());
        oakTreeAreaBox.getItems().add(WcLocations.VARROCK_WEST_OAKS.toString());
    }


    @FXML
    @DoNotRename
    void resetSkillsButtonPressed(ActionEvent event){
        addCurrentLevels();
    }

    @FXML
    @DoNotRename
    void addCurrentLevels(){
        agilityGoalLevelBox.setText(String.valueOf(Skills.SKILLS.AGILITY.getActualLevel()));
        herbloreGoalLevelBox.setText(String.valueOf(Skills.SKILLS.HERBLORE.getActualLevel()));
        craftingGoalLevelBox.setText(String.valueOf(Skills.SKILLS.CRAFTING.getActualLevel()));
        fletchingGoalLevelBox.setText(String.valueOf(Skills.SKILLS.FLETCHING.getActualLevel()));
        miningGoalLevelBox.setText(String.valueOf(Skills.SKILLS.MINING.getActualLevel()));
        firemakingGoalLevelBox.setText(String.valueOf(Skills.SKILLS.FIREMAKING.getActualLevel()));
        woodcuttingGoalLevelBox.setText(String.valueOf(Skills.SKILLS.WOODCUTTING.getActualLevel()));
        cookingGoalLevelBox.setText(String.valueOf(Skills.SKILLS.COOKING.getActualLevel()));
        fishingGoalLevel.setText(String.valueOf(Skills.SKILLS.FISHING.getActualLevel()));
        prayerGoalLevelBox.setText(String.valueOf(Skills.SKILLS.PRAYER.getActualLevel()));
        magicGoalLevelBox.setText(String.valueOf(Skills.SKILLS.MAGIC.getActualLevel()));
        thievingGoalLevelBox.setText(String.valueOf(Skills.SKILLS.THIEVING.getActualLevel()));
        smithingGoalLevelBox.setText(String.valueOf(Skills.SKILLS.SMITHING.getActualLevel()));
        runecraftingGoalLevelBox.setText(String.valueOf(Skills.SKILLS.RUNECRAFTING.getActualLevel()));
        hunterGoalLevelBox.setText(String.valueOf(Skills.SKILLS.HUNTER.getActualLevel()));
        slayerGoalLevelBox.setText(String.valueOf(Skills.SKILLS.SLAYER.getActualLevel()));
        constructionGoalLevelBox.setText(String.valueOf(Skills.SKILLS.CONSTRUCTION.getActualLevel()));
        thievingGoalLevelBox.setText(String.valueOf(Skills.SKILLS.THIEVING.getActualLevel()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScriptSettings settingsHandler = ScriptSettings.getDefault();
        settingsHandler.load("last", Vars.class)
                .ifPresent(s -> {
                    Log.info("Loaded settings object: " + s);
                });
        skillsImage.setImage(new Image("https://i.imgur.com/9WGq5Sy.png"));
        addCurrentLevels();
        switchSkillMinTime.setText(String.valueOf(Vars.get().skillSwitchMin / 60000));
        switchSkillMaxTime.setText(String.valueOf(Vars.get().skillSwitchMax / 60000));
        afkEveryAverage.setText(String.valueOf(Vars.get().afkFrequencyAvg / 1000));
        afkEverySD.setText(String.valueOf(Vars.get().afkFrequencySD / 1000));
        afkForAverage.setText(String.valueOf(Vars.get().afkDurationAvg / 1000));
        afkForSD.setText(String.valueOf(Vars.get().afkDurationSD / 1000));

        //TODO implement fishign location reading above
        for (RodLocations r : RodLocations.values()) {
            troutLocationsDropDown.getItems().add(StringUtils.capitalize(r.toString().toLowerCase()));
        }
        for (ShrimpLocations r : ShrimpLocations.values()) {
            shrimpLocationsDropDown.getItems().add(StringUtils.capitalize(r.toString().toLowerCase()));
        }
        for (CombatPotions c : CombatPotions.values()) {
            slayerCombatPotionBox.getItems().add(StringUtils.capitalize(c.toString().toLowerCase()));
        }
        for (CraftMethods c : CraftMethods.values()) {
            craftingMethodsDropDown.getItems().add(StringUtils.capitalize(c.toString().toLowerCase()));
        }
        for (SkillTasks s : SkillTasks.values()) {
            startingSkillDropDown.getItems().add(s.getSkillName());
        }
        for (Const.LOG_ACTIONS action : Const.LOG_ACTIONS.values()) {
            //logActionBox.getItems().add(StringUtils.capitalize(action.toString().toLowerCase()));
        }
        for (Alch.AlchItems item : Alch.AlchItems.values()) {
            magicAlchItemBox.getItems().add(item.toString().replace("_", " ")
                    + " (" + item.getProfit() + ")");
        }
        // populate trees
        addWCAreaToLists();

        for (Methods.HERBLORE meth : Methods.HERBLORE.values()) {

        }
        for (Methods.COOKING meth : Methods.COOKING.values()) {

        }
        for (Methods.CRAFTING meth : Methods.CRAFTING.values()) {

        }
        for (Methods.FISHING meth : Methods.FISHING.values()) {

        }
        for (Methods.FLETCHING meth : Methods.FLETCHING.values()) {

        }
        for (Methods.HUNTER meth : Methods.HUNTER.values()) {

        }
        for (Methods.MINING meth : Methods.MINING.values()) {

        }
    }

}
