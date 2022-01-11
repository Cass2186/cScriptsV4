package scripts.Tasks;


import org.tribot.api.General;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Prayer;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.sdk.MyPlayer;
import org.tribot.script.sdk.Waiting;
import scripts.EatUtil;
import scripts.ItemId;
import scripts.PrayerUtil;
import scripts.Timer;
import scripts.VorkUtils.Vars;
import scripts.VorkUtils.VorkthUtil;

public class EatDrink implements Task{



    public boolean drinkPotion(int[] potionArray) {
        RSItem[] potion = Inventory.find(potionArray);
        return potion.length > 0 && potion[0].click("Drink");
    }


    public void handlePotions(){
        if (Prayer.getPrayerPoints() <= Vars.get().drinkPrayerAt){
            if (Inventory.find(ItemId.PRAYER_POTION).length ==0){
                //tele out
            }
            PrayerUtil.drinkPrayerPotion();
            Vars.get().drinkPrayerAt = General.randomSD(5,40, 18,7);
        }

        else   if (Combat.getHP() <= Vars.get().eatAtHP){
            VorkthUtil.eatFood();
            Vars.get().eatAtHP = Combat.getMaxHP()- General.randomSD(32,4);
        }
        else  if (!Vars.get().antiFireTimer.isRunning()){
            RSItem[] anti = Inventory.find(22209, 22212, 22215, 22218);
            if (anti.length ==0){
                // leave
            }
            if(drinkPotion(new int[]{22209, 22212, 22215, 22218})){
                Vars.get().antiFireTimer =  new Timer(General.random(300000,350000));
            }

        }
        else    if (MyPlayer.isVenomed()){
            RSItem[] anti = Inventory.find(12913, 12915, 12917, 12919);
            if (anti.length ==0){
                // leave
            }
            drinkPotion(new int[]{12913, 12915, 12917, 12919});
        }
       // VorkthUtil.clickVorkath("Attack");
        Waiting.waitNormal(250,75);
    }

    public void equipDiamondBolts(){

    }

    @Override
    public String toString(){
        return "Eating/Drinking";
    }


    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return NPCs.findNearest(VorkthUtil.ATTACKING_VORK, VorkthUtil.SLEEPING_VORK).length >0 &&
                (Combat.getHP() <= Vars.get().eatAtHP ||
                        !Vars.get().antiFireTimer.isRunning() ||
                        Prayer.getPrayerPoints() <= Vars.get().drinkPrayerAt ||
                        MyPlayer.isVenomed());
    }

    @Override
    public void execute() {
        handlePotions();
    }
}
