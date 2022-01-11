package scripts.QuestPackages.LegendsQuest;


import org.tribot.api2007.Game;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;

public class FinishLegendsQuest implements Task {
    
    
    
    @Override
    public Priority priority() {
        return null;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {
        if (Game.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 45){
            // talk to Radimus Erkle in the little house in guild
        } else  if (Game.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 50){
            // talk to Radimus Erkle in guild
            // claim reward 1
        }
        else  if (Game.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 55){
            // claim reward 2
        }
        else  if (Game.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 60){
            // claim reward 3
        }
        else  if (Game.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 65){
            // claim reward 4
        }
        else  if (Game.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 70){
                //finish dialogue
        } else  if (Game.getSetting(LegendsUtils.LEGENDS_GAME_SETTING) == 75){
            // Done Quest, close window
        }

    }
}
