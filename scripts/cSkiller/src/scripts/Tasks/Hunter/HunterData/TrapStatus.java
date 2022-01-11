package scripts.Tasks.Hunter.HunterData;

import lombok.Getter;
import lombok.Setter;

public enum TrapStatus {

    CAPTURED(HunterConst.SHAKING_BOX_TRAP, HunterConst.NET_TRAP_SUCCESSFUL, HunterConst.CAUGHT_BIRD_SNARE),
    IN_PROGRESS(-1, HunterConst.NET_TRAP_ENGAGED, -1),
    SETUP(HunterConst.SET_UP_BOX_TRAP, HunterConst.NET_TRAP_RESTING, HunterConst.LAID_BIRD_SNARE),
    FAILED(HunterConst.FAILED_BOX_TRAP, 0, HunterConst.DISABLED_BIRD_SNARE),
    NONE;

    TrapStatus(int idBox, int idNet, int idBird) {
        this.BOX = idBox;
        this.NET = idNet;
        this.BIRD = idBird;
    }

    TrapStatus() {
        this.BOX = -1;
        this.NET = -1;
        this.BIRD = -1;
    }


    @Getter
    @Setter
    private int BOX;

    @Getter
    @Setter
    private int NET;

    @Getter
    @Setter
    private int BIRD;
}
