package scripts.GUI;

import com.allatori.annotations.DoNotRename;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Prayer;
import org.tribot.script.sdk.types.Area;
import org.tribot.script.sdk.types.WorldTile;
import org.tribot.script.sdk.util.ScriptSettings;

import scripts.Data.Vars;

import scripts.Timer;
import scripts.Utils;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

@DoNotRename
public class GUIController extends AbstractGUIController {

    @DoNotRename
    @FXML
    private TextField npcNameEnterBox, lootOverThresholdBox, areaRadiusBox, centreTileBox, resetTileBox,
            defLvlGoalBox, rangedLvlGoalBox, atkLvlGoalBox, strLvlGoalBox;

    @DoNotRename
    @FXML
    private Button addNpcButton;

    @DoNotRename
    @FXML
    private CheckBox alchLootCheckBox;

    @DoNotRename
    @FXML
    private Button getCentreTileButton;

    @DoNotRename
    @FXML
    private CheckBox resetAgroCheckBox;

    @DoNotRename
    @FXML
    private Button getResetTileButton;


    @DoNotRename
    @FXML
    private CheckBox killingCrabsCheckBox;

    @DoNotRename
    @FXML
    private AnchorPane prayersListBox;

    @DoNotRename
    @FXML
    private CheckBox enablePrayerCheckBox;

    @DoNotRename
    @FXML
    private AnchorPane selectedPrayersListBox;

    @DoNotRename
    @FXML
    private Button getInventoryButon;

    @DoNotRename
    @FXML
    private CheckBox killingDruidsButton;

    @DoNotRename
    @FXML
    private Button startScriptButton;

    @DoNotRename
    @FXML
    private ListView<String> npcListView, targetListView;

    ArrayList<String> targets = new ArrayList<>();


    @DoNotRename
    @FXML
    void addNpcToList(ActionEvent event) {
        int index = npcListView.getSelectionModel().getSelectedIndex();
        targetListView.getItems().add(npcListView.getItems().get(index));
        targets.add(npcListView.getItems().get(index));
        // Vars.get().targets = new String[]{npcListView.getItems().get(index)};
    }

    @DoNotRename
    @FXML
    void updateResetAgroTile(ActionEvent event) {

    }

    @DoNotRename
    @FXML
    void updateCentreTile(ActionEvent event) {
        WorldTile tile = MyPlayer.getTile();
        centreTileBox.setText(tile.getX() + "," + tile.getY() + "," + tile.getPlane());
        Vars.get().combatCentreTile = tile;
        Vars.get().fightArea = Area.fromRadius(Vars.get().combatCentreTile, Vars.get().areaRadius);
    }

    @DoNotRename
    @FXML
    public void removeNpcFromList(ActionEvent event) {
        int index = targetListView.getSelectionModel().getSelectedIndex();
        targets.remove(targetListView.getItems().get(index));
        targetListView.getItems().remove(targetListView.getItems().get(index));
        npcListView.getItems().add(targetListView.getItems().get(index));
    }


    @FXML
    @DoNotRename
    private Button atkAddButton, defAddButton, rangedAddButton, strAddButton;

    @FXML
    @DoNotRename
    void startButtonPressed(ActionEvent event) {
        General.println("[GUIController]: Start button pressed");
        Vars settings = Vars.get();
        ScriptSettings handler = ScriptSettings.getDefault();
        handler.save("last", settings);
        updateSkillEndLevel(event);
        updateAfkDuration(event);
        updateAFKFrequency(event);

        this.getGUI().close();
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

    }

    @FXML
    @DoNotRename
    void mouseSpeedAdjusted(ActionEvent event) {

    }

    @FXML
    @DoNotRename
    void updateAFKFrequency(ActionEvent event) {
        General.println("[GUIController]: Updated AFK Frequency");
    }

    @FXML
    @DoNotRename
    void updateAfkDuration(ActionEvent event) {
        General.println("[GUIController]: Updated AFK duration");

    }

    @FXML
    @DoNotRename
    void updateABC2Modifier(MouseDragEvent event) {
        General.println("[GUIController]: Updated ABC2 Modifier to " + event.toString());
    }

    @FXML
    @DoNotRename
    void updateSkillEndLevel(ActionEvent event) {
        General.println("[GUIController]: Updating end level");

    }

    @FXML
    @DoNotRename
    void skillSelected(ActionEvent event) {

    }


    @FXML
    @DoNotRename
    void addStrSkillTaskToList(ActionEvent event) {
       //Vars.get().combatTaskList.add(new CombatTask(Skills.SKILLS.STRENGTH,
         //       Integer.parseInt(strLvlGoalBox.getText())));
      //  combatProgressionBox.getChildren().add(new Text("Train Strength to Lvl " + strLvlGoalBox.getText()));
    }

    @FXML
    @DoNotRename
    void addAtkSkillTaskToList(ActionEvent event) {
    //    Vars.get().combatTaskList.add(new CombatTask(Skills.SKILLS.ATTACK,
     //           Integer.parseInt(atkLvlGoalBox.getText())));
     //   combatProgressionBox.getChildren().add(new Text("Train Attack to Lvl " + atkLvlGoalBox.getText()));
    }

    @FXML
    @DoNotRename
    void addDefSkillTaskToList(ActionEvent event) {
        //Vars.get().combatTaskList.add(new CombatTask(Skills.SKILLS.DEFENCE,
      //          Integer.parseInt(defLvlGoalBox.getText())));
      //  combatProgressionBox.getChildren().add(new Text("Train Defence to Lvl " + defLvlGoalBox.getText()));
    }

    @FXML
    @DoNotRename
    void addRangedSkillTaskToList(ActionEvent event) {
    //    Vars.get().combatTaskList.add(new CombatTask(Skills.SKILLS.RANGED,
    //            Integer.parseInt(rangedLvlGoalBox.getText())));
    //    combatProgressionBox.getChildren().add(new Text("Train Ranged to Lvl " + defLvlGoalBox.getText()));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
     //  skillsImage.setImage(new Image("https://i.imgur.com/9WGq5Sy.png"));


        for (Prayer s : Prayer.values()) {
            //startingSkillDropDown.getItems().add(s.getSkillName());
        }

    }

}


