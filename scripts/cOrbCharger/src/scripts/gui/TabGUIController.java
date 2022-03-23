package scripts.gui;

import com.allatori.annotations.DoNotRename;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import org.tribot.api.General;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.util.ScriptSettings;
import scripts.Data.Tabs;
import scripts.Data.Vars;

import java.net.URL;
import java.util.ResourceBundle;

@DoNotRename
public class TabGUIController extends AbstractGUIController {


    @DoNotRename
    @FXML
    private CheckBox makeProfitableTabButton;

    @DoNotRename
    @FXML
    private ComboBox<String> selectTabComboBox;

    @DoNotRename
    @FXML
    private Button startScriptButton;

    @FXML
    @DoNotRename
    public Tabs getSelectedTab() {
        switch (selectTabComboBox.getValue().toLowerCase()) {
            case ("varrock teleport"):
                return Tabs.VARROCK;

            case ("lumbridge teleport"):
                return Tabs.LUMBRIDGE;

            case("house teleport"):
                return Tabs.HOUSE;

            case("falador teleport"):
                Log.debug("falador selected");
                return Tabs.FALADOR;


            case("camelot teleport"):
                Log.debug("Camelot selected");
                return Tabs.CAMELOT;

            case("enchant sapphire"):
                Log.debug("Sapphire enchant selected");
                return Tabs.ENCHANT_SAPPHIRE;

            case("enchant emerald"):
                Log.debug("emerald enchant selected");
                return Tabs.ENCHANT_EMERALD;

            default:
                return Tabs.VARROCK;

        }

    }

    @FXML
    @DoNotRename
    void startButtonPressed(ActionEvent event) {
        General.println("[GUIController]: Start button pressed");
       // Vars settings = Vars.get();
        // ScriptSettings handler = ScriptSettings.getDefault();
       // handler.save("last", settings);

        if (makeProfitableTabButton.isSelected()) {
            Vars.get().shouldMakeMostProfitableTab = true;
            Vars.get().selectedTab = Tabs.getMostProfitableTab();
        }else {
            Vars.get().shouldMakeMostProfitableTab = false;
            Vars.get().selectedTab = getSelectedTab();
        }

        this.getGUI().close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Tabs s : Tabs.values()) {
            selectTabComboBox.getItems().add(s.getName());
        }
    }


}
