package scripts.Data;

import lombok.Getter;
import lombok.Setter;
import org.tribot.api.General;
import org.tribot.api2007.Skills;
import org.tribot.script.sdk.Log;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.tribot.api2007.Skills.SKILLS;

public enum SkillTasks {


    AGILITY(1, 1, 1, SKILLS.AGILITY),
    CONSTRUCTION(2, 1, 1, SKILLS.CONSTRUCTION), // end point is ? here really
    COOKING(3, 1, 1, SKILLS.COOKING), //13 from gertrudes cat
    CRAFTING(4, 1, 1, SKILLS.CRAFTING),
    // FARMING(5, 1, 1, SKILLS.FARMING),
    ATTACK(2, 1, 1, SKILLS.ATTACK),
    STRENGTH(2, 1, 1, SKILLS.STRENGTH),
    DEFENCE(2, 1, 1, SKILLS.DEFENCE),
    RANGED(2, 1, 1, SKILLS.RANGED),
    FIREMAKING(5, 1, 1, SKILLS.FIREMAKING),
    FISHING(6, 15, 15, SKILLS.FISHING), //use seaslug for 1-24 ideally
    FLETCHING(7, 1, 1, SKILLS.FLETCHING),
    HERBLORE(8, 3, 3, SKILLS.HERBLORE),
    HUNTER(9, 9, 9, SKILLS.HUNTER),
    MAGIC(10, 7, 7, SKILLS.MAGIC),
    MINING(11, 1, 1, SKILLS.MINING), //Dig site, plague city and doric's quest get 33 (i think)
    PRAYER(12, 1, 1, SKILLS.PRAYER),
    RUNECRAFTING(13, 9, 9, SKILLS.RUNECRAFTING),
    SLAYER(14, 1, 1, SKILLS.SLAYER),
    SMITHING(15, 0, 0, SKILLS.SMITHING), //knights sword for 1-29
    THIEVING(16, 5, 5, SKILLS.THIEVING), //use Fight arena for 1-14
    WOODCUTTING(17, 1, 1, SKILLS.WOODCUTTING), // use Monk's Friend for 1-13
    PEST_CONTROL(18, 99, 99, SKILLS.HITPOINTS),
    KOUREND_FAVOUR(19, 99, 99, SKILLS.HITPOINTS),
    KILL_CRABS(20, 99, 99, SKILLS.HITPOINTS), //TODO use setter to change this skill
    TRAIN_NMZ(21, 99, 99, SKILLS.HITPOINTS),
    DEFENDERS(22, 99, 99, SKILLS.HITPOINTS),
    SORCERESS_GARDEN(23, 1, 1, SKILLS.THIEVING);

    @Getter
    @Setter
    private int startLevel;

    @Getter
    @Setter
    private int endLevel;

    @Getter
    @Setter
    private SKILLS skill;

    @Getter
    @Setter
    private int seedNumber;


    @Getter
    private String skillName;

    @Getter
    private boolean overrideSkillCheck; //for things like pest control, will be toggled with GUI


    @Override
    public String toString() {
        return this.skillName;
    }

    SkillTasks(int seedNumber, int startLevel, int endLevel, SKILLS skill) {
        this.seedNumber = seedNumber;
        this.startLevel = startLevel;
        this.endLevel = endLevel;
        this.skill = skill;
        this.skillName = this.name();
    }

    SkillTasks(int seedNumber, int startLevel, int endLevel, boolean overrideSkillCheck) {
        this.seedNumber = seedNumber;
        this.startLevel = startLevel;
        this.endLevel = endLevel;
        this.overrideSkillCheck = overrideSkillCheck;
        this.skillName = this.name();
    }

    public boolean isWithinLevelRange() {
        int currentLevel = Skills.getActualLevel(this.skill);
        boolean b = (currentLevel >= this.startLevel) && (currentLevel < this.endLevel);
        //    System.out.println("[SkillTasks]: Are we within level range of " + this.getSkillName() + ": " + b);
        return b;
    }

    public static List<SkillTasks> generateList() {
        List<SkillTasks> list = new ArrayList<>();
        Log.info("[SkillTasks]: Creating task list");
        for (SkillTasks tsk : SkillTasks.values()) {
            if (tsk.isWithinLevelRange()) {
                Log.info("[SkillTasks]: Added " + tsk.getSkillName() + " to task list");
                list.add(tsk);
            }
        }
        Log.info("[SkillTasks]: Generated a task list " + list.size() + " tasks long");
        return list;
    }

    public static SkillTasks getSkillTask() {

        List<SkillTasks> list = generateList();
        if (Vars.get().prevTask != null && list.contains(Vars.get().prevTask)) {
            Log.info("[Debug]: removing prev task from list");
            list.remove(Vars.get().prevTask);
        }
        if (list.size() == 0)
            return null;
        int seed = General.random(1, list.size()) - 1;
        Log.info("[SkillTasks]: Task is: " + list.get(seed).getSkillName());
        return list.get(seed); //avoids use of designated seeds
    }


}
