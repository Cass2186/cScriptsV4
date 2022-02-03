package scripts.QuestPackages.lairoftarnrazorlor;

import scripts.QuestPackages.HauntedMine.HauntedMine;

public class LairOfTarnRazorlor {
    private static HauntedMine quest;

    public static HauntedMine get() {
        return quest == null ? quest = new HauntedMine() : quest;
    }
}
