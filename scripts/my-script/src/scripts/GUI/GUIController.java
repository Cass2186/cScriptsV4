package scripts.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import org.tribot.api.General;
import scripts.AgilityAPI.COURSES;
import scripts.Data.Vars;
import scripts.Timer;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIController extends AbstractGUIController {


    @FXML
    private Button startButton;

    @FXML
    private TextField afkFrequencyMin;

    @FXML
    private TextField afkFrequencyMax;

    @FXML
    private TextField afkDurationMin;

    @FXML
    private TextField afkDurationMax;

    @FXML
    private CheckBox afkModeCheckBox;

    @FXML
    private ComboBox<String> courseSelectionDropDown;

    @FXML
    void startButtonPressed(ActionEvent event) {
        General.println("[GUIController]: Start button pressed");
        this.getGUI().close();
    }


    @FXML
    void updateAFKFrequency(ActionEvent event) {
        Vars.get().afkFrequencyMin = Integer.parseInt(afkFrequencyMin.getText()) * 1000;
        Vars.get().afkFrequencyMax = Integer.parseInt(afkFrequencyMin.getText()) * 1000;
        Vars.get().afkTimer = new Timer(General.random(Vars.get().afkFrequencyMin, Vars.get().afkFrequencyMax));
        General.println("[GUIController]: Updated AFK Frequency");
    }

    @FXML
    void updateAfkDuration(ActionEvent event) {
        General.println("[GUIController]: Updated AFK duration");
         Vars.get().afkDurationMin = Integer.parseInt(afkDurationMin.getText()) * 1000;
           Vars.get().afkDurationMax = Integer.parseInt(afkDurationMax.getText()) * 1000;
    }

    @FXML
    void updateABC2Modifier(MouseDragEvent event) {
        //    General.println("[GUIController]: Updated ABC2 Modifier to " + (abc2Modifier.getValue() / 100));
        //  Utils.FACTOR = (abc2Modifier.getValue() / 100);
    }

    @FXML
    void updateAfkBoolean(ActionEvent event) {
        Vars.get().afkMode = afkModeCheckBox.isSelected();
    }

    @FXML
    void updateSkillEndLevel(ActionEvent event) {
        General.println("[GUIController]: Updating end level");
        /* SkillTasks.AGILITY.setEndLevel(Integer.parseInt(String.valueOf(agilityGoalLevelBox)));
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
        //SkillTasks.THIEVING.setEndLevel(Integer.parseInt(String.valueOf(thievingGoalLevelBox)));

        //SkillTasks.SMITHING.setEndLevel(Integer.parseInt(String.valueOf(smithingGoalLevelBox)));

        //SkillTasks.RUNECRAFTING.setEndLevel(Integer.parseInt(String.valueOf(runecraftingGoalLevelBox)));*/

    }

    @FXML
    void courseSelected(ActionEvent event) {

        switch (courseSelectionDropDown.getValue()) {
            case ("TREE_GNOME_STRONGHOLD"):
                Vars.get().currentCourse = COURSES.TREE_GNOME_STRONGHOLD;
                break;
            case ("DRAYNOR_VILLAGE"):
                Vars.get().currentCourse = COURSES.DRAYNOR_VILLAGE;
                break;
            case ("VARROCK"):
                Vars.get().currentCourse = COURSES.VARROCK;
                break;
            case ("CANIFIS"):
                Vars.get().currentCourse = COURSES.CANIFIS;
                break;
            case ("FALADOR"):
                Vars.get().currentCourse = COURSES.FALADOR;
                break;
            case ("SEERS_VILLAGE"):
                Vars.get().currentCourse = COURSES.SEERS_VILLAGE;
                break;
            case ("POLLNIVEACH"):
                Vars.get().currentCourse = COURSES.POLLNIVEACH;
                break;


        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (COURSES s : COURSES.values()) {
            courseSelectionDropDown.getItems().add(s.toString());
        }
        Vars.get().afkMode = afkModeCheckBox.isSelected();

    }
}


