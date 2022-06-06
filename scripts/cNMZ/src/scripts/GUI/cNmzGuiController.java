package scripts.GUI;

import com.allatori.annotations.DoNotRename;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.tribot.api.General;
import org.tribot.script.sdk.Log;
import scripts.NmzData.Vars;
import scripts.Tasks.DrinkPotion;

import java.net.URL;
import java.util.ResourceBundle;

@DoNotRename
public class cNmzGuiController extends AbstractGUIController {


    @DoNotRename
    @FXML
    private Button startScriptButton;

    @DoNotRename
    @FXML
    private Button loadSettingsButton;

    @DoNotRename
    @FXML
    private TextField numberOfAbsPotionsText;

    @DoNotRename
    @FXML
    private TextField numberOfOverloadPotionsText;

    @DoNotRename
    @FXML
    private TextField numberOfPrayerPotionsText;

    @DoNotRename
    @FXML
    private Button saveSettingsButton;

    @DoNotRename
    @FXML
    private CheckBox useMagicBoostButton ,useAbsorptionButton ,useMeleeBoostButton;

    @DoNotRename
    @FXML
    private CheckBox useOverloadButton, useRockCakeButton, usingSuperCombatButton ,useRangeBoostButton ,usePrayMeleeButton;

    @DoNotRename
    @FXML
    void loadSettingsPressed(ActionEvent event) {

    }

    @DoNotRename
    @FXML
    void saveSettingsPressed(ActionEvent event) {

    }

    @FXML
    @DoNotRename
    void startButtonPressed(ActionEvent event) {
        Log.info("[GUIController]: Start button pressed");

        if (useRockCakeButton.isSelected()) {

        }
        if (useRangeBoostButton.isSelected()) {
            Vars.get().usingRangingBoost = true;
        }
        if (usePrayMeleeButton.isSelected()) {
            Vars.get().usingPrayerPots = true;
        }
        if (useOverloadButton.isSelected()) {
            Vars.get().usingOverloadPots = true;
        }
        if (useMeleeBoostButton.isSelected()) {
            Vars.get().usingMeleeBoost = true;
        }
        if (useMagicBoostButton.isSelected()) {
            Vars.get().usingMagicBoost = true;
        }
        if (useAbsorptionButton.isSelected()) {
            Vars.get().usingAbsorptions = true;
        }
        if (usingSuperCombatButton.isSelected()) {
            Vars.get().usingSuperCombat = true;
        }


        this.getGUI().close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DrinkPotion.determinePotion();
        usingSuperCombatButton.setSelected(Vars.get().usingSuperCombat);
        useAbsorptionButton.setSelected(Vars.get().usingAbsorptions);
        useOverloadButton.setSelected(Vars.get().usingOverloadPots);
        usePrayMeleeButton.setSelected(Vars.get().usingPrayerPots);
        useRockCakeButton.setSelected(Vars.get().usingLocatorOrb);
    }


}
