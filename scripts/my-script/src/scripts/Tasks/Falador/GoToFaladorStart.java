package scripts.Tasks.Falador;

import org.tribot.api2007.Player;
import scripts.API.Priority;
import scripts.API.Task;
import scripts.AgilityAPI.AgilUtils;
import scripts.Data.AgilityAreas;
import scripts.Data.Vars;
import scripts.PathingUtil;

public class GoToFaladorStart implements Task {
    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return AgilUtils.isWithinLevelRange(52,60) &&
                !Vars.get().overridingCourse && Player.getPosition().getPlane() == 0 &&
                !AgilityAreas.FALADOR_FINISH_AREA.contains(Player.getPosition());
    }

    @Override
    public void execute() {
        PathingUtil.walkToArea(AgilityAreas.FALADOR_FINISH_AREA, false);
    }

    @Override
    public String course() {
        return "Falador";
    }
}
