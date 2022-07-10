package scripts.RcApi;

import lombok.Data;
import lombok.Getter;
import org.tribot.script.sdk.Skill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public enum Pouches {
    SMALL(1, 3),
    MEDIUM(25, 6),
    LARGE(50, 9),
    GIANT(75, 12),
    COLOSSAL(85, 40);

    @Getter
    private int requiredLevel;
    @Getter
    private int essenceNumber;

    Pouches(int reqLevel, int essNumber) {
        this.requiredLevel = reqLevel;
        this.essenceNumber = essNumber;
    }

    public static List<Pouches> getValidPouches() {
        return Arrays.stream(Pouches.values())
                .filter(p -> Skill.RUNECRAFT.getActualLevel() >= p.getRequiredLevel())
                .collect(Collectors.toList());
    }
}
