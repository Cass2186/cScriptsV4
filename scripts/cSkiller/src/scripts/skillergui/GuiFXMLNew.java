package scripts.skillergui;

public class GuiFXMLNew {
    public static String gui = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "\n" +
            "<?import javafx.scene.control.Button?>\n" +
            "<?import javafx.scene.control.CheckBox?>\n" +
            "<?import javafx.scene.control.ComboBox?>\n" +
            "<?import javafx.scene.control.Label?>\n" +
            "<?import javafx.scene.control.Menu?>\n" +
            "<?import javafx.scene.control.MenuBar?>\n" +
            "<?import javafx.scene.control.MenuItem?>\n" +
            "<?import javafx.scene.control.SeparatorMenuItem?>\n" +
            "<?import javafx.scene.control.Slider?>\n" +
            "<?import javafx.scene.control.Tab?>\n" +
            "<?import javafx.scene.control.TabPane?>\n" +
            "<?import javafx.scene.control.TextField?>\n" +
            "<?import javafx.scene.image.ImageView?>\n" +
            "<?import javafx.scene.layout.AnchorPane?>\n" +
            "<?import javafx.scene.layout.VBox?>\n" +
            "<?import javafx.scene.text.Font?>\n" +
            "\n" +
            "<VBox prefHeight=\"548.0\" prefWidth=\"703.0\" xmlns=\"http://javafx.com/javafx/17\" xmlns:fx=\"http://javafx.com/fxml/1\" fx:controller=\"scripts.SkillerGUIController\">\n" +
            "    <children>\n" +
            "        <MenuBar VBox.vgrow=\"NEVER\">\n" +
            "            <menus>\n" +
            "                <Menu mnemonicParsing=\"false\" text=\"File\">\n" +
            "                    <items>\n" +
            "                        <MenuItem mnemonicParsing=\"false\" text=\"New\" />\n" +
            "                        <MenuItem mnemonicParsing=\"false\" text=\"Open…\" />\n" +
            "                        <Menu mnemonicParsing=\"false\" text=\"Open Recent\" />\n" +
            "                        <SeparatorMenuItem mnemonicParsing=\"false\" />\n" +
            "                        <MenuItem mnemonicParsing=\"false\" text=\"Close\" />\n" +
            "                        <MenuItem mnemonicParsing=\"false\" text=\"Save\" />\n" +
            "                        <MenuItem mnemonicParsing=\"false\" text=\"Save As…\" />\n" +
            "                        <MenuItem mnemonicParsing=\"false\" text=\"Revert\" />\n" +
            "                        <SeparatorMenuItem mnemonicParsing=\"false\" />\n" +
            "                        <MenuItem mnemonicParsing=\"false\" text=\"Preferences…\" />\n" +
            "                        <SeparatorMenuItem mnemonicParsing=\"false\" />\n" +
            "                        <MenuItem mnemonicParsing=\"false\" text=\"Quit\" />\n" +
            "                    </items>\n" +
            "                </Menu>\n" +
            "                <Menu mnemonicParsing=\"false\" text=\"Edit\">\n" +
            "                    <items>\n" +
            "                        <MenuItem mnemonicParsing=\"false\" text=\"Undo\" />\n" +
            "                        <MenuItem mnemonicParsing=\"false\" text=\"Redo\" />\n" +
            "                        <SeparatorMenuItem mnemonicParsing=\"false\" />\n" +
            "                        <MenuItem mnemonicParsing=\"false\" text=\"Cut\" />\n" +
            "                        <MenuItem mnemonicParsing=\"false\" text=\"Copy\" />\n" +
            "                        <MenuItem mnemonicParsing=\"false\" text=\"Paste\" />\n" +
            "                        <MenuItem mnemonicParsing=\"false\" text=\"Delete\" />\n" +
            "                        <SeparatorMenuItem mnemonicParsing=\"false\" />\n" +
            "                        <MenuItem mnemonicParsing=\"false\" text=\"Select All\" />\n" +
            "                        <MenuItem mnemonicParsing=\"false\" text=\"Unselect All\" />\n" +
            "                    </items>\n" +
            "                </Menu>\n" +
            "                <Menu mnemonicParsing=\"false\" text=\"Help\">\n" +
            "                    <items>\n" +
            "                        <MenuItem mnemonicParsing=\"false\" text=\"About MyHelloApp\" />\n" +
            "                    </items>\n" +
            "                </Menu>\n" +
            "            </menus>\n" +
            "        </MenuBar>\n" +
            "        <AnchorPane prefHeight=\"589.0\" prefWidth=\"703.0\">\n" +
            "            <children>\n" +
            "                <TabPane prefHeight=\"520.5\" prefWidth=\"703.0\" tabClosingPolicy=\"UNAVAILABLE\" AnchorPane.bottomAnchor=\"0.0\" AnchorPane.leftAnchor=\"0.0\" AnchorPane.rightAnchor=\"0.0\" AnchorPane.topAnchor=\"0.0\">\n" +
            "                    <tabs>\n" +
            "                        <Tab text=\"Home Tab\">\n" +
            "                            <content>\n" +
            "                                <AnchorPane minHeight=\"0.0\" minWidth=\"0.0\" prefHeight=\"180.0\" prefWidth=\"200.0\">\n" +
            "                                    <children>\n" +
            "                                        <Button id=\"saveProfileButton\" layoutX=\"304.0\" layoutY=\"127.0\" mnemonicParsing=\"false\" text=\"Save Profile\">\n" +
            "                                            <font>\n" +
            "                                                <Font size=\"15.0\" />\n" +
            "                                            </font>\n" +
            "                                        </Button>\n" +
            "                                        <Button id=\"loadProfileButton\" layoutX=\"304.0\" layoutY=\"230.0\" mnemonicParsing=\"false\" text=\"Load Profile\">\n" +
            "                                            <font>\n" +
            "                                                <Font size=\"15.0\" />\n" +
            "                                            </font>\n" +
            "                                        </Button>\n" +
            "                                        <Button id=\"startButton\" layoutX=\"304.0\" layoutY=\"391.0\" mnemonicParsing=\"false\" onAction=\"#startButtonPressed\" prefHeight=\"29.0\" prefWidth=\"95.0\" text=\"Start Build\">\n" +
            "                                            <font>\n" +
            "                                                <Font size=\"15.0\" />\n" +
            "                                            </font>\n" +
            "                                        </Button>\n" +
            "                                    </children>\n" +
            "                                </AnchorPane>\n" +
            "                            </content>\n" +
            "                        </Tab>\n" +
            "                        <Tab text=\"Skills\">\n" +
            "                            <content>\n" +
            "                                <AnchorPane minHeight=\"0.0\" minWidth=\"0.0\" prefHeight=\"180.0\" prefWidth=\"200.0\">\n" +
            "                                    <children>\n" +
            "                                        <AnchorPane id=\"startingSkillBox\" maxHeight=\"-1.0\" maxWidth=\"-1.0\" prefHeight=\"489.0\" prefWidth=\"703.0\" AnchorPane.bottomAnchor=\"0.0\" AnchorPane.leftAnchor=\"0.0\" AnchorPane.rightAnchor=\"0.0\" AnchorPane.topAnchor=\"0.0\">\n" +
            "                                            <children>\n" +
            "                                                <ImageView fitHeight=\"420.0\" fitWidth=\"306.0\" layoutX=\"29.0\" layoutY=\"33.0\" />\n" +
            "                                                <TextField fx:id=\"agilityGoalLevelBox\" alignment=\"CENTER\" layoutX=\"187.0\" layoutY=\"97.0\" onAction=\"#updateSkillEndLevel\" prefHeight=\"30.0\" prefWidth=\"35.0\" promptText=\"99\" text=\"30\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"12.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </TextField>\n" +
            "                                                <TextField fx:id=\"herbloreGoalLevelBox\" alignment=\"CENTER\" layoutX=\"187.0\" layoutY=\"147.0\" onAction=\"#updateSkillEndLevel\" prefHeight=\"30.0\" prefWidth=\"35.0\" promptText=\"99\" text=\"30\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"12.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </TextField>\n" +
            "                                                <TextField fx:id=\"craftingGoalLevelBox\" alignment=\"CENTER\" layoutX=\"187.0\" layoutY=\"250.0\" onAction=\"#updateSkillEndLevel\" prefHeight=\"30.0\" prefWidth=\"35.0\" promptText=\"99\" text=\"53\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"12.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </TextField>\n" +
            "                                                <TextField fx:id=\"fletchingGoalLevelBox\" alignment=\"CENTER\" layoutX=\"187.0\" layoutY=\"300.0\" onAction=\"#updateSkillEndLevel\" prefHeight=\"30.0\" prefWidth=\"35.0\" promptText=\"99\" text=\"15\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"12.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </TextField>\n" +
            "                                                <TextField fx:id=\"miningGoalLevelBox\" alignment=\"CENTER\" layoutX=\"286.0\" layoutY=\"45.0\" onAction=\"#updateSkillEndLevel\" prefHeight=\"30.0\" prefWidth=\"35.0\" promptText=\"99\" text=\"60\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"12.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </TextField>\n" +
            "                                                <TextField fx:id=\"firemakingGoalLevelBox\" alignment=\"CENTER\" layoutX=\"286.0\" layoutY=\"250.0\" onAction=\"#updateSkillEndLevel\" prefHeight=\"30.0\" prefWidth=\"35.0\" promptText=\"99\" text=\"50\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"12.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </TextField>\n" +
            "                                                <TextField fx:id=\"woodcuttingGoalLevelBox\" alignment=\"CENTER\" layoutX=\"286.0\" layoutY=\"300.0\" onAction=\"#updateSkillEndLevel\" prefHeight=\"30.0\" prefWidth=\"35.0\" promptText=\"99\" text=\"53\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"12.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </TextField>\n" +
            "                                                <TextField fx:id=\"cookingGoalLevelBox\" alignment=\"CENTER\" layoutX=\"286.0\" layoutY=\"198.0\" onAction=\"#updateSkillEndLevel\" prefHeight=\"30.0\" prefWidth=\"35.0\" promptText=\"99\" text=\"53\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"12.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </TextField>\n" +
            "                                                <TextField fx:id=\"fishingGoalLevel\" alignment=\"CENTER\" layoutX=\"286.0\" layoutY=\"147.0\" onAction=\"#updateSkillEndLevel\" prefHeight=\"30.0\" prefWidth=\"35.0\" promptText=\"99\" text=\"53\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"12.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </TextField>\n" +
            "                                                <TextField fx:id=\"prayerGoalLevelBox\" alignment=\"CENTER\" layoutX=\"85.0\" layoutY=\"250.0\" onAction=\"#updateSkillEndLevel\" prefHeight=\"30.0\" prefWidth=\"35.0\" promptText=\"99\" text=\"43\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"12.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </TextField>\n" +
            "                                                <TextField fx:id=\"magicGoalLevelBox\" alignment=\"CENTER\" layoutX=\"85.0\" layoutY=\"300.0\" onAction=\"#updateSkillEndLevel\" prefHeight=\"30.0\" prefWidth=\"35.0\" promptText=\"99\" text=\"55\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"12.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </TextField>\n" +
            "                                                <TextField fx:id=\"thievingGoalLevelBox\" alignment=\"CENTER\" layoutX=\"187.0\" layoutY=\"198.0\" onAction=\"#updateSkillEndLevel\" prefHeight=\"30.0\" prefWidth=\"35.0\" promptText=\"99\" text=\"25\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"12.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </TextField>\n" +
            "                                                <TextField id=\"smithingTargetLevelBox\" fx:id=\"smithingGoalLevelBox\" alignment=\"CENTER\" layoutX=\"286.0\" layoutY=\"97.0\" onAction=\"#updateSkillEndLevel\" prefHeight=\"30.0\" prefWidth=\"35.0\" promptText=\"99\" text=\"29\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"12.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </TextField>\n" +
            "                                                <TextField fx:id=\"runecraftingGoalLevelBox\" alignment=\"CENTER\" layoutX=\"85.0\" layoutY=\"351.0\" onAction=\"#updateSkillEndLevel\" prefHeight=\"30.0\" prefWidth=\"35.0\" promptText=\"99\" text=\"1\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"12.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </TextField>\n" +
            "                                                <TextField fx:id=\"hunterGoalLevelBox\" alignment=\"CENTER\" layoutX=\"187.0\" layoutY=\"405.0\" onAction=\"#updateSkillEndLevel\" prefHeight=\"30.0\" prefWidth=\"35.0\" promptText=\"99\" text=\"9\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"12.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </TextField>\n" +
            "                                                <TextField fx:id=\"slayerGoalLevelBox\" alignment=\"CENTER\" layoutX=\"187.0\" layoutY=\"351.0\" onAction=\"#updateSkillEndLevel\" prefHeight=\"30.0\" prefWidth=\"35.0\" promptText=\"99\" text=\"18\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"12.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </TextField>\n" +
            "                                                <TextField fx:id=\"constructionGoalLevelBox\" alignment=\"CENTER\" layoutX=\"85.0\" layoutY=\"405.0\" onAction=\"#updateSkillEndLevel\" prefHeight=\"30.0\" prefWidth=\"35.0\" promptText=\"99\" text=\"1\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"12.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </TextField>\n" +
            "                                            </children>\n" +
            "                                        </AnchorPane>\n" +
            "                                    </children>\n" +
            "                                </AnchorPane>\n" +
            "                            </content>\n" +
            "                        </Tab>\n" +
            "                        <Tab text=\"Preferences\">\n" +
            "                            <content>\n" +
            "                                <AnchorPane minHeight=\"0.0\" minWidth=\"0.0\" prefHeight=\"180.0\" prefWidth=\"200.0\">\n" +
            "                                    <children>\n" +
            "                                        <AnchorPane id=\"startingSkillBox\" layoutX=\"-4.0\" layoutY=\"1.0\" maxHeight=\"-1.0\" maxWidth=\"-1.0\" prefHeight=\"489.0\" prefWidth=\"703.0\" AnchorPane.bottomAnchor=\"-1.0\" AnchorPane.leftAnchor=\"-4.0\" AnchorPane.rightAnchor=\"4.0\" AnchorPane.topAnchor=\"1.0\">\n" +
            "                                            <children>\n" +
            "                                                <TextField id=\"minTimeBettwenSkillsBox\" fx:id=\"switchSkillMinTime\" layoutX=\"134.0\" layoutY=\"115.0\" onAction=\"#updateSkillSwitchTimer\" prefHeight=\"26.0\" prefWidth=\"43.0\" text=\"40\" />\n" +
            "                                                <TextField id=\"maxTimeBetweenSkillsBox\" fx:id=\"switchSkillMaxTime\" layoutX=\"202.0\" layoutY=\"115.0\" onAction=\"#updateSkillSwitchTimer\" prefHeight=\"26.0\" prefWidth=\"43.0\" text=\"60\" />\n" +
            "                                                <Label layoutX=\"23.0\" layoutY=\"118.0\" text=\"Switch skill every:\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"14.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </Label>\n" +
            "                                                <Label layoutX=\"182.0\" layoutY=\"119.0\" text=\"to\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"14.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </Label>\n" +
            "                                                <Label layoutX=\"249.0\" layoutY=\"119.0\" text=\"min \">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"14.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </Label>\n" +
            "                                                <ComboBox fx:id=\"startingSkillDropDown\" layoutX=\"29.0\" layoutY=\"56.0\" onAction=\"#skillSelected\" prefHeight=\"26.0\" prefWidth=\"216.0\" promptText=\"Starting Skill\" />\n" +
            "                                                <TextField id=\"minTimeBettwenSkillsBox\" fx:id=\"afkEveryAverage\" layoutX=\"137.0\" layoutY=\"165.0\" onAction=\"#updateAFKFrequency\" prefHeight=\"26.0\" prefWidth=\"43.0\" text=\"420\" />\n" +
            "                                                <TextField id=\"maxTimeBetweenSkillsBox\" fx:id=\"afkEverySD\" layoutX=\"227.0\" layoutY=\"165.0\" onAction=\"#updateAFKFrequency\" prefHeight=\"26.0\" prefWidth=\"43.0\" text=\"600\" />\n" +
            "                                                <Label alignment=\"CENTER_RIGHT\" layoutX=\"62.0\" layoutY=\"168.0\" text=\"AFK every:\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"14.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </Label>\n" +
            "                                                <Label layoutX=\"182.0\" layoutY=\"169.0\" text=\"s \">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"14.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </Label>\n" +
            "                                                <Label layoutX=\"273.0\" layoutY=\"168.0\" text=\"(SD)\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"14.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </Label>\n" +
            "                                                <TextField id=\"minTimeBettwenSkillsBox\" fx:id=\"afkForAverage\" layoutX=\"137.0\" layoutY=\"201.0\" onAction=\"#updateAfkDuration\" prefHeight=\"26.0\" prefWidth=\"43.0\" text=\"20\" />\n" +
            "                                                <TextField id=\"maxTimeBetweenSkillsBox\" fx:id=\"afkForSD\" layoutX=\"227.0\" layoutY=\"201.0\" onAction=\"#updateAfkDuration\" prefHeight=\"26.0\" prefWidth=\"43.0\" text=\"120\" />\n" +
            "                                                <Label alignment=\"CENTER_RIGHT\" layoutX=\"76.0\" layoutY=\"204.0\" text=\"AFK for:\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"14.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </Label>\n" +
            "                                                <Label layoutX=\"182.0\" layoutY=\"205.0\" text=\"s\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"14.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </Label>\n" +
            "                                                <Label layoutX=\"273.0\" layoutY=\"204.0\" text=\"(SD)\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"14.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </Label>\n" +
            "                                                <CheckBox layoutX=\"28.0\" layoutY=\"269.0\" mnemonicParsing=\"false\" text=\"Do Birdhouse runs when switching skills\" />\n" +
            "                                                <CheckBox layoutX=\"28.0\" layoutY=\"299.0\" mnemonicParsing=\"false\" text=\"Do Herb runs when switching skills\" />\n" +
            "                                                <Label layoutX=\"28.0\" layoutY=\"24.0\" text=\"Skill Preferences\" underline=\"true\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font name=\"System Font\" size=\"17.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </Label>\n" +
            "                                                <TextField id=\"minTimeBettwenSkillsBox\" fx:id=\"mouseSpeedBox\" layoutX=\"445.0\" layoutY=\"56.0\" onAction=\"#mouseSpeedAdjusted\" prefHeight=\"26.0\" prefWidth=\"43.0\" text=\"100\" />\n" +
            "                                                <Label layoutX=\"353.0\" layoutY=\"60.0\" text=\"Mouse Speed:\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"14.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </Label>\n" +
            "                                                <Label layoutX=\"202.0\" layoutY=\"168.0\" text=\"+/-\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"14.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </Label>\n" +
            "                                                <Label layoutX=\"202.0\" layoutY=\"204.0\" text=\"+/-\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"14.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </Label>\n" +
            "                                                <Label layoutX=\"352.0\" layoutY=\"24.0\" text=\"Misc Preferences\" underline=\"true\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font name=\"System Font\" size=\"17.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </Label>\n" +
            "                                                <TextField id=\"minTimeBettwenSkillsBox\" layoutX=\"575.0\" layoutY=\"94.0\" prefHeight=\"26.0\" prefWidth=\"43.0\" text=\"2\" />\n" +
            "                                                <Label layoutX=\"353.0\" layoutY=\"97.0\" text=\"World Hop if &gt;= x players in area:\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"14.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </Label>\n" +
            "                                                <Label layoutX=\"352.0\" layoutY=\"141.0\" text=\"ABC2 Modifier (% of max):\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"14.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </Label>\n" +
            "                                                <Slider layoutX=\"526.0\" layoutY=\"185.0\" max=\"200.0\" min=\"100.0\" snapToTicks=\"true\" value=\"150.0\" />\n" +
            "                                                <Label layoutX=\"352.0\" layoutY=\"182.0\" text=\"Drop Speed Modifier:\">\n" +
            "                                                    <font>\n" +
            "                                                        <Font size=\"14.0\" />\n" +
            "                                                    </font>\n" +
            "                                                </Label>\n" +
            "                                                <Slider fx:id=\"abc2Modifier\" layoutX=\"526.0\" layoutY=\"141.0\" onDragDetected=\"#updateABC2Modifier\" onDragDone=\"#updateABC2Modifier\" onDragDropped=\"#updateABC2Modifier\" onMouseDragReleased=\"#updateABC2Modifier\" showTickLabels=\"true\" showTickMarks=\"true\" value=\"25.0\" />\n" +
            "                                                <CheckBox layoutX=\"29.0\" layoutY=\"239.0\" mnemonicParsing=\"false\" text=\"Move mouse off screen while AFK?\" />\n" +
            "                                            </children>\n" +
            "                                        </AnchorPane>\n" +
            "                                    </children>\n" +
            "                                </AnchorPane>\n" +
            "                            </content>\n" +
            "                        </Tab>\n" +
            "                        <Tab text=\"Skill Methods\">\n" +
            "                            <content>\n" +
            "                                <AnchorPane minHeight=\"0.0\" minWidth=\"0.0\" prefHeight=\"180.0\" prefWidth=\"200.0\">\n" +
            "                                    <children>\n" +
            "                                        <ComboBox fx:id=\"logActionBox\" layoutX=\"482.0\" layoutY=\"377.0\" prefWidth=\"150.0\" promptText=\"Log Action\" />\n" +
            "                                        <Label layoutX=\"482.0\" layoutY=\"352.0\" text=\"Woodcutting\" underline=\"true\">\n" +
            "                                            <font>\n" +
            "                                                <Font size=\"16.0\" />\n" +
            "                                            </font>\n" +
            "                                        </Label>\n" +
            "                                        <Label layoutX=\"37.0\" layoutY=\"290.0\" text=\"Magic \" underline=\"true\">\n" +
            "                                            <font>\n" +
            "                                                <Font size=\"16.0\" />\n" +
            "                                            </font>\n" +
            "                                        </Label>\n" +
            "                                        <ComboBox fx:id=\"logActionBox1\" layoutX=\"37.0\" layoutY=\"315.0\" prefWidth=\"150.0\" promptText=\"Alch Item\" />\n" +
            "                                        <Label layoutX=\"266.0\" layoutY=\"85.0\" text=\"Herblore\" underline=\"true\">\n" +
            "                                            <font>\n" +
            "                                                <Font size=\"16.0\" />\n" +
            "                                            </font>\n" +
            "                                        </Label>\n" +
            "                                        <CheckBox layoutX=\"266.0\" layoutY=\"110.0\" mnemonicParsing=\"false\" text=\"Make Tar?\" />\n" +
            "                                        <Label layoutX=\"40.0\" layoutY=\"352.0\" text=\"Runecrafting\" underline=\"true\">\n" +
            "                                            <font>\n" +
            "                                                <Font size=\"16.0\" />\n" +
            "                                            </font>\n" +
            "                                        </Label>\n" +
            "                                        <ComboBox fx:id=\"logActionBox11\" layoutX=\"37.0\" layoutY=\"377.0\" prefWidth=\"150.0\" promptText=\"Combination Rune\" />\n" +
            "                                        <Label layoutX=\"39.0\" layoutY=\"224.0\" text=\"Prayer\" underline=\"true\">\n" +
            "                                            <font>\n" +
            "                                                <Font size=\"16.0\" />\n" +
            "                                            </font>\n" +
            "                                        </Label>\n" +
            "                                        <ComboBox fx:id=\"prayerMethodBox\" layoutX=\"37.0\" layoutY=\"249.0\" prefWidth=\"150.0\" promptText=\"Prayer Method\" />\n" +
            "                                        <Label layoutX=\"268.0\" layoutY=\"26.0\" text=\"Agility\" underline=\"true\">\n" +
            "                                            <font>\n" +
            "                                                <Font size=\"16.0\" />\n" +
            "                                            </font>\n" +
            "                                        </Label>\n" +
            "                                        <CheckBox layoutX=\"268.0\" layoutY=\"51.0\" mnemonicParsing=\"false\" text=\"Alch?\" />\n" +
            "                                        <Label layoutX=\"265.0\" layoutY=\"148.0\" text=\"Thieving\" underline=\"true\">\n" +
            "                                            <font>\n" +
            "                                                <Font size=\"16.0\" />\n" +
            "                                            </font>\n" +
            "                                        </Label>\n" +
            "                                        <ComboBox fx:id=\"prayerMethodBox1\" layoutX=\"266.0\" layoutY=\"173.0\" prefWidth=\"150.0\" promptText=\"Prayer Method\" />\n" +
            "                                        <Label layoutX=\"266.0\" layoutY=\"222.0\" text=\"Crafting\" underline=\"true\">\n" +
            "                                            <font>\n" +
            "                                                <Font size=\"16.0\" />\n" +
            "                                            </font>\n" +
            "                                        </Label>\n" +
            "                                        <ComboBox fx:id=\"prayerMethodBox11\" layoutX=\"267.0\" layoutY=\"247.0\" prefWidth=\"150.0\" promptText=\"Crafting Method\" />\n" +
            "                                        <Label layoutX=\"267.0\" layoutY=\"290.0\" text=\"Fletching\" underline=\"true\">\n" +
            "                                            <font>\n" +
            "                                                <Font size=\"16.0\" />\n" +
            "                                            </font>\n" +
            "                                        </Label>\n" +
            "                                        <ComboBox fx:id=\"prayerMethodBox111\" layoutX=\"266.0\" layoutY=\"315.0\" prefWidth=\"150.0\" promptText=\"Fletching Method\" />\n" +
            "                                        <Label layoutX=\"266.0\" layoutY=\"352.0\" text=\"Slayer\" underline=\"true\">\n" +
            "                                            <font>\n" +
            "                                                <Font size=\"16.0\" />\n" +
            "                                            </font>\n" +
            "                                        </Label>\n" +
            "                                        <ComboBox fx:id=\"prayerMethodBox1111\" layoutX=\"266.0\" layoutY=\"377.0\" prefWidth=\"150.0\" promptText=\"Slayer Master\" />\n" +
            "                                        <Label layoutX=\"266.0\" layoutY=\"415.0\" text=\"Hunter\" underline=\"true\">\n" +
            "                                            <font>\n" +
            "                                                <Font size=\"16.0\" />\n" +
            "                                            </font>\n" +
            "                                        </Label>\n" +
            "                                        <ComboBox fx:id=\"prayerMethodBox11111\" layoutX=\"266.0\" layoutY=\"440.0\" prefWidth=\"150.0\" promptText=\"Hunter Method\" />\n" +
            "                                        <Label layoutX=\"482.0\" layoutY=\"28.0\" text=\"Mining\" underline=\"true\">\n" +
            "                                            <font>\n" +
            "                                                <Font size=\"16.0\" />\n" +
            "                                            </font>\n" +
            "                                        </Label>\n" +
            "                                        <Label layoutX=\"482.0\" layoutY=\"87.0\" text=\"Smithing\" underline=\"true\">\n" +
            "                                            <font>\n" +
            "                                                <Font size=\"16.0\" />\n" +
            "                                            </font>\n" +
            "                                        </Label>\n" +
            "                                        <ComboBox fx:id=\"fishingMethodBox\" layoutX=\"482.0\" layoutY=\"175.0\" prefWidth=\"150.0\" promptText=\"Fishing Method\" />\n" +
            "                                        <Label layoutX=\"482.0\" layoutY=\"150.0\" text=\"Fishing\" underline=\"true\">\n" +
            "                                            <font>\n" +
            "                                                <Font size=\"16.0\" />\n" +
            "                                            </font>\n" +
            "                                        </Label>\n" +
            "                                        <ComboBox fx:id=\"cookingMethodBox\" layoutX=\"482.0\" layoutY=\"247.0\" prefWidth=\"150.0\" promptText=\"Cooking Method\" />\n" +
            "                                        <Label layoutX=\"482.0\" layoutY=\"222.0\" text=\"Cooking\" underline=\"true\">\n" +
            "                                            <font>\n" +
            "                                                <Font size=\"16.0\" />\n" +
            "                                            </font>\n" +
            "                                        </Label>\n" +
            "                                        <ComboBox fx:id=\"firemakingMethodBox\" layoutX=\"482.0\" layoutY=\"315.0\" prefWidth=\"150.0\" promptText=\"Firemaking Pref\" />\n" +
            "                                        <Label layoutX=\"482.0\" layoutY=\"290.0\" text=\"Firemaking\" underline=\"true\">\n" +
            "                                            <font>\n" +
            "                                                <Font size=\"16.0\" />\n" +
            "                                            </font>\n" +
            "                                        </Label>\n" +
            "                                        <CheckBox fx:id=\"useMlmBox1\" layoutX=\"482.0\" layoutY=\"51.0\" mnemonicParsing=\"false\" onAction=\"#setUseMlmBox\" prefHeight=\"15.0\" prefWidth=\"142.0\" text=\"Use Motherlaod Mine?\" />\n" +
            "                                    </children>\n" +
            "                                </AnchorPane>\n" +
            "                            </content>\n" +
            "                        </Tab>\n" +
            "                    </tabs>\n" +
            "                </TabPane>\n" +
            "            </children>\n" +
            "        </AnchorPane>\n" +
            "    </children>\n" +
            "</VBox>\n";
}
