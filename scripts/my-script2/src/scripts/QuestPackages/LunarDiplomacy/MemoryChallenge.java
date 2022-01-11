package scripts.QuestPackages.LunarDiplomacy;

import org.tribot.api2007.types.RSTile;
import scripts.QuestSteps.QuestTask;
import scripts.Tasks.Priority;
import scripts.Tasks.Task;
import scripts.Utils;

import java.util.Arrays;
import java.util.List;

public class MemoryChallenge implements QuestTask {

    public List<RSTile> setupPaths() {
        // Path 1
        if (Utils.getVarBitValue(2414) == 83) {
            return Arrays.asList(
                    new RSTile(1731, 5106, 2),
                    new RSTile(1731, 5103, 2),
                    new RSTile(1734, 5103, 2),
                    new RSTile(1737, 5103, 2),
                    new RSTile(1740, 5103, 2),
                    new RSTile(1740, 5100, 2),
                    new RSTile(1740, 5097, 2),
                    new RSTile(1737, 5097, 2),
                    new RSTile(1734, 5097, 2),
                    new RSTile(1731, 5097, 2),
                    new RSTile(1731, 5094, 2),
                    new RSTile(1731, 5091, 2),
                    new RSTile(1731, 5088, 2),
                    new RSTile(1734, 5088, 2),
                    new RSTile(1737, 5088, 2),
                    new RSTile(1737, 5085, 2),
                    new RSTile(1737, 5083, 2)
            );

        }
        // Path 2
        else if (Utils.getVarBitValue(2414) == 192) {
            return Arrays.asList(
                    new RSTile(1737, 5106, 2),
                    new RSTile(1737, 5103, 2),
                    new RSTile(1734, 5103, 2),
                    new RSTile(1731, 5103, 2),
                    new RSTile(1731, 5100, 2),
                    new RSTile(1731, 5097, 2),
                    new RSTile(1734, 5097, 2),
                    new RSTile(1734, 5094, 2),
                    new RSTile(1734, 5091, 2),
                    new RSTile(1731, 5091, 2),
                    new RSTile(1731, 5088, 2),
                    new RSTile(1731, 5085, 2),
                    new RSTile(1731, 5083, 2)
            );
           // setRSTile(1731, 5083, 2);
        }
        // Path 3
        else if (Utils.getVarBitValue(2415) == 7) {
            return Arrays.asList(
                    new RSTile(1731, 5106, 2),
                    new RSTile(1731, 5103, 2),
                    new RSTile(1734, 5103, 2),
                    new RSTile(1737, 5103, 2),
                    new RSTile(1737, 5100, 2),
                    new RSTile(1737, 5097, 2),
                    new RSTile(1734, 5097, 2),
                    new RSTile(1731, 5097, 2),
                    new RSTile(1731, 5094, 2),
                    new RSTile(1731, 5091, 2),
                    new RSTile(1734, 5091, 2),
                    new RSTile(1737, 5091, 2),
                    new RSTile(1740, 5091, 2),
                    new RSTile(1740, 5088, 2),
                    new RSTile(1740, 5085, 2),
                    new RSTile(1740, 5083, 2)
            );
          //  setRSTile(1740, 5083, 2);
        }
        // Path 4, shared varbit with 3 but will 3 has already passed
        else if (Utils.getVarBitValue(2412) == 28) {
            return Arrays.asList(
                    new RSTile(1734, 5106, 2),
                    new RSTile(1734, 5103, 2),
                    new RSTile(1737, 5103, 2),
                    new RSTile(1737, 5100, 2),
                    new RSTile(1737, 5097, 2),
                    new RSTile(1734, 5097, 2),
                    new RSTile(1731, 5097, 2),
                    new RSTile(1731, 5094, 2),
                    new RSTile(1731, 5091, 2),
                    new RSTile(1734, 5091, 2),
                    new RSTile(1734, 5088, 2),
                    new RSTile(1737, 5088, 2),
                    new RSTile(1737, 5085, 2),
                    new RSTile(1737, 5083, 2)
            );

        }
        // Path 5
        else if (Utils.getVarBitValue(2415) == 123) {
            return Arrays.asList(
                    new RSTile(1734, 5106, 2),
                    new RSTile(1734, 5103, 2),
                    new RSTile(1737, 5103, 2),
                    new RSTile(1740, 5103, 2),
                    new RSTile(1740, 5100, 2),
                    new RSTile(1740, 5097, 2),
                    new RSTile(1740, 5094, 2),
                    new RSTile(1737, 5094, 2),
                    new RSTile(1734, 5094, 2),
                    new RSTile(1731, 5094, 2),
                    new RSTile(1731, 5091, 2),
                    new RSTile(1731, 5088, 2),
                    new RSTile(1734, 5088, 2),
                    new RSTile(1737, 5088, 2),
                    new RSTile(1740, 5088, 2),
                    new RSTile(1740, 5085, 2),
                    new RSTile(1740, 5083, 2)
            );
        }
        // Path 6
        else if (Utils.getVarBitValue(2414) == 42) {
            return Arrays.asList(
                    new RSTile(1731, 5106, 2),
                    new RSTile(1731, 5103, 2),
                    new RSTile(1731, 5100, 2),
                    new RSTile(1734, 5100, 2),
                    new RSTile(1737, 5100, 2),
                    new RSTile(1740, 5100, 2),
                    new RSTile(1740, 5097, 2),
                    new RSTile(1740, 5094, 2),
                    new RSTile(1737, 5094, 2),
                    new RSTile(1734, 5094, 2),
                    new RSTile(1734, 5091, 2),
                    new RSTile(1734, 5088, 2),
                    new RSTile(1737, 5088, 2),
                    new RSTile(1740, 5088, 2),
                    new RSTile(1740, 5085, 2),
                    new RSTile(1740, 5083, 2)
            );

        }
        // Path 7
        else if (Utils.getVarBitValue(2413) == 218) {
            return Arrays.asList(
                    new RSTile(1734, 5106, 2),
                    new RSTile(1734, 5103, 2),
                    new RSTile(1737, 5103, 2),
                    new RSTile(1737, 5100, 2),
                    new RSTile(1737, 5097, 2),
                    new RSTile(1734, 5097, 2),
                    new RSTile(1734, 5094, 2),
                    new RSTile(1731, 5094, 2),
                    new RSTile(1731, 5091, 2),
                    new RSTile(1731, 5088, 2),
                    new RSTile(1734, 5088, 2),
                    new RSTile(1737, 5088, 2),
                    new RSTile(1737, 5085, 2),
                    new RSTile(1737, 5083, 2)
            );
        }
        // Path 8
        else if (Utils.getVarBitValue(2414) == 91) {
            return Arrays.asList(
                    new RSTile(1734, 5106, 2),
                    new RSTile(1734, 5103, 2),
                    new RSTile(1737, 5103, 2),
                    new RSTile(1740, 5103, 2),
                    new RSTile(1740, 5100, 2),
                    new RSTile(1740, 5097, 2),
                    new RSTile(1737, 5097, 2),
                    new RSTile(1737, 5094, 2),
                    new RSTile(1734, 5094, 2),
                    new RSTile(1734, 5091, 2),
                    new RSTile(1734, 5088, 2),
                    new RSTile(1737, 5088, 2),
                    new RSTile(1737, 5085, 2),
                    new RSTile(1737, 5083, 2)
            );
        }
        // Path 9
        else if (Utils.getVarBitValue(2413) == 3) {
            return Arrays.asList(
                    new RSTile(1740, 5106, 2),
                    new RSTile(1740, 5103, 2),
                    new RSTile(1740, 5100, 2),
                    new RSTile(1737, 5100, 2),
                    new RSTile(1737, 5097, 2),
                    new RSTile(1737, 5094, 2),
                    new RSTile(1740, 5094, 2),
                    new RSTile(1740, 5091, 2),
                    new RSTile(1740, 5088, 2),
                    new RSTile(1737, 5088, 2),
                    new RSTile(1734, 5088, 2),
                    new RSTile(1734, 5085, 2),
                    new RSTile(1734, 5083, 2)
            );
        }
        // Path 10
        else if (Utils.getVarBitValue(2412) == 30) {
            return Arrays.asList(
                    new RSTile(1734, 5106, 2),
                    new RSTile(1734, 5103, 2),
                    new RSTile(1737, 5103, 2),
                    new RSTile(1737, 5100, 2),
                    new RSTile(1737, 5097, 2),
                    new RSTile(1734, 5097, 2),
                    new RSTile(1731, 5097, 2),
                    new RSTile(1731, 5094, 2),
                    new RSTile(1731, 5091, 2),
                    new RSTile(1731, 5088, 2),
                    new RSTile(1734, 5088, 2),
                    new RSTile(1737, 5088, 2),
                    new RSTile(1737, 5085, 2),
                    new RSTile(1737, 5083, 2)
            );
        }
        return null;
    }

    @Override
    public Priority priority() {
        return Priority.MEDIUM;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {

    }

    @Override
    public String questName() {
        return null;
    }

    @Override
    public boolean checkRequirements() {
        return false;
    }
}
