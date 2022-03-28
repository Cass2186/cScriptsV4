package scripts.gui;

import com.allatori.annotations.DoNotRename;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import org.tribot.api.General;
import org.tribot.script.sdk.Log;
import scripts.QuestUtils.SupportedQuests;

import java.net.URL;
import java.util.ResourceBundle;

@DoNotRename
public class QuestGUIController extends AbstractGUIController {

    @DoNotRename
    @FXML
    private Button addQuestButton;

    @DoNotRename
    @FXML
    private ListView<String> questListView, selectedQuestsListView;

    @DoNotRename
    @FXML
    private Button removeQuestButton;

    @DoNotRename
    @FXML
    private Button startScriptButton;

    @DoNotRename
    @FXML
    void addQuestPressed(ActionEvent event) {
        int index = questListView.getSelectionModel().getSelectedIndex();
        if (questListView.getItems().get(index) != null) {
            questListView.getItems().remove(questListView.getItems().get(index));
            selectedQuestsListView.getItems().add(questListView.getItems().get(index));

        }

    }

    @DoNotRename
    @FXML
    void removedQuestPressed(ActionEvent event) {
        int index = selectedQuestsListView.getSelectionModel().getSelectedIndex();
        if (selectedQuestsListView.getItems().get(index) != null) {
            questListView.getItems().add(questListView.getItems().get(index));
            selectedQuestsListView.getItems().remove(selectedQuestsListView.getItems().get(index));
        }
    }

    @FXML
    @DoNotRename
    void startButtonPressed(ActionEvent event) {
        General.println("[GUIController]: Start button pressed");
        // Vars settings = Vars.get();
        // ScriptSettings handler = ScriptSettings.getDefault();
        // handler.save("last", settings);


        this.getGUI().close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (SupportedQuests s : SupportedQuests.values()) {
            questListView.getItems().add(s.toString());
        }
    }


}
