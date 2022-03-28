package scripts.gui;

import com.allatori.annotations.DoNotRename;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.tribot.api.General;
import org.tribot.script.sdk.Log;
import scripts.QuestUtils.SupportedQuests;
import scripts.cQuesterV2;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
        String s =  questListView.getItems().get(index);
        if (s != null) {
            questListView.getItems().remove(s);
            selectedQuestsListView.getItems().add(s);

        }

    }

    @DoNotRename
    @FXML
    void removedQuestPressed(ActionEvent event) {
        int index = selectedQuestsListView.getSelectionModel().getSelectedIndex();
        String s = selectedQuestsListView.getItems().get(index);
        if (s != null) {
            selectedQuestsListView.getItems().remove(s);
            questListView.getItems().add(s);
        }
    }

    @FXML
    @DoNotRename
    void startButtonPressed(ActionEvent event) {
        General.println("[GUIController]: Start button pressed");
        // Vars settings = Vars.get();
        // ScriptSettings handler = ScriptSettings.getDefault();
        // handler.save("last", settings);
        for (String str : selectedQuestsListView.getItems()){
            String s = str.replace(" ", "");
            cQuesterV2.handleArgs(s);
        }

        this.getGUI().close();
    }

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (SupportedQuests s : SupportedQuests.values()) {
            questListView.getItems().add(StringUtils.capitalize(s.getQuestName()));
        }

    }


}
