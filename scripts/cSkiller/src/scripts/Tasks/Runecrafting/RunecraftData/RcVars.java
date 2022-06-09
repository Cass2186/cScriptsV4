package scripts.Tasks.Runecrafting.RunecraftData;

import org.tribot.script.sdk.Skill;

public class RcVars {
    private static RcVars vars;

    public static RcVars get() {
        return vars == null ? vars = new RcVars() : vars;
    }


    public static void reset() {
        vars = new RcVars();
    }

    public boolean scriptStatus = true;

    /**
     * Strings
     */
    public String target = null;

    public String status = "Intializing";

    public long currentTime;

    public long startTime;

    public boolean usingOuraniaAlter = Skill.RUNECRAFT.getActualLevel() > 70;

    public boolean useStamina = true;

    public boolean lava = false;

    public boolean usingPouches = false;

    public boolean shouldAfk = false;

    public boolean usingLunarImbue = false;

    public boolean isImbueActive = false;

    public boolean managedToPassAbyss = false;

    public boolean failedObstacle = false;

    public boolean abyssCrafting = false;

    public boolean needToRepairPouches = false;

    public boolean collectPouches = false;

    public boolean zanarisCrafting = false;

    public boolean mudRuneCrafting = false;

    public boolean goToBloodAltar = false;

    public boolean bloodRuneCrafting = false;
}
