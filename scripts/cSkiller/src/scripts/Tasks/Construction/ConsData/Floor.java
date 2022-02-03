package scripts.Tasks.Construction.ConsData;

import lombok.Getter;

public enum Floor {
    DUNGEON("Dungeon"),
    GROUND("Ground"),
    UPSTAIRS("Upstairs");

    @Getter
    String name;

    Floor(String name) {
        this.name = name;
    }

}