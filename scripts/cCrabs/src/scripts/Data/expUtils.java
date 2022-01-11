package scripts.Data;

import static org.tribot.api2007.Skills.SKILLS;
import static org.tribot.api2007.Skills.getXP;

public class expUtils {

    public static void recordExp() {

      /*  Vars.get().mageXp = getXP(SKILLS.MAGIC);

        Vars.get().defXp = getXP(SKILLS.DEFENCE);

        Vars.get().attXp = getXP(SKILLS.ATTACK);

        Vars.get().strXp = getXP(SKILLS.STRENGTH);

        Vars.get().hpXp = getXP(SKILLS.HITPOINTS);

        Vars.get().rangeXp = getXP(SKILLS.RANGED);*/
    }

    public static void recordStartExp() {

        Vars.get().startMageXp = getXP(SKILLS.MAGIC);

        Vars.get().startDefXp = getXP(SKILLS.DEFENCE);

        Vars.get().startAttXp = getXP(SKILLS.ATTACK);

        Vars.get().startStrXp = getXP(SKILLS.STRENGTH);

        Vars.get().startRangeXp = getXP(SKILLS.RANGED);

        Vars.get().startSlayerXP = getXP(SKILLS.SLAYER);
    }


    public static boolean checkExp() {
        return  false;
       /* return (getXP(SKILLS.MAGIC) > Vars.get().mageXp)

                || (getXP(SKILLS.DEFENCE) > Vars.get().defXp)

                || (getXP(SKILLS.ATTACK) > Vars.get().attXp)

                || (getXP(SKILLS.STRENGTH) > Vars.get().strXp)

                || (getXP(SKILLS.HITPOINTS) > Vars.get().hpXp)

                || (getXP(SKILLS.RANGED) > Vars.get().rangeXp)

                || (getXP(SKILLS.RUNECRAFTING) > Vars.get().rcXp)

                || (getXP(SKILLS.SLAYER) > Vars.get().slayerXp);*/
    }


}
