package scripts.Data;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public enum Spawns {
    NW(new RSArea(new RSTile(2374, 5113, 0), new RSTile(2389, 5098, 0))),
    C(new RSArea(new RSTile(2389, 5096, 0), new RSTile(2402, 5082, 0))),
    SE(new RSArea(new RSTile(2414, 5085, 0), new RSTile(2419, 5079, 0))),
    S(new RSArea(new RSTile(2396, 5077, 0), new RSTile(2405, 5068, 0))),
    SW(new RSArea(new RSTile(2373, 5073, 0), new RSTile(2384, 5064, 0)));

    private RSArea spawn;
    private static RSArea jadSpawn = null;

    Spawns(RSArea spawn) {
        this.spawn = spawn;
    }

    public RSArea getSpawn() {
        return spawn;
    }

    public static void setJadSpawn() {
        if (jadSpawn == null) {
            switch (Rotations.getRotation()) {
                case (1):
                    jadSpawn = SW.getSpawn();
                    break;
                case (2):
                    jadSpawn = SE.getSpawn();
                    break;
                case (3):
                    jadSpawn = SW.getSpawn();
                    break;

                case (4):
                    jadSpawn = S.getSpawn();
                    break;
                case (5):
                    jadSpawn = SE.getSpawn();
                    break;
                case (6):
                    jadSpawn = SE.getSpawn();
                    break;
                case (7):
                    jadSpawn = C.getSpawn();
                    break;
                case (8):
                    jadSpawn = C.getSpawn();
                    break;
                case (9):
                    jadSpawn = SW.getSpawn();
                    break;
                case (10):
                    jadSpawn = NW.getSpawn();
                    break;
                case (11):
                    jadSpawn = C.getSpawn();
                    break;
                case (12):
                    jadSpawn = S.getSpawn();
                    break;
                case (13):
                    jadSpawn = NW.getSpawn();
                    break;
                case (14):
                    jadSpawn = NW.getSpawn();
                    break;
                case (15):
                    jadSpawn = S.getSpawn();
                    break;
            }
        }
    }
}
