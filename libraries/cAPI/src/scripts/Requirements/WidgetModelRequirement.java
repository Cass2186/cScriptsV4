package scripts.Requirements;

import lombok.Getter;
import lombok.Setter;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Widget;

import java.util.Optional;

public class WidgetModelRequirement implements Requirement{

    @Setter
    @Getter
    protected boolean hasPassed;
    protected boolean onlyNeedToPassOnce;

    @Getter
    private final int groupId;

    private final int childId;
    private final int id;
    private int childChildId = -1;

    public WidgetModelRequirement(int groupId, int childId, int childChildId, int id) {
        this.groupId = groupId;
        this.childId = childId;
        this.childChildId = childChildId;
        this.id = id;
    }

    public WidgetModelRequirement(int groupId, int childId, int id) {
        this.groupId = groupId;
        this.childId = childId;
        this.id = id;
    }

    @Override
    public boolean check() {
        if (onlyNeedToPassOnce && hasPassed) {
            return true;
        }
        return checkWidget();
    }

    public boolean checkWidget() {
        Optional<Widget> widget = Query.widgets().inIndexPath(groupId, childId).findFirst();

        if (widget.isEmpty()) {
            return false;
        }
        if (childChildId != -1)
        {
            widget = widget.get().getChild(childChildId);
        }
        if (widget.isPresent())
        {
            return widget.get().getTextureId() == id; //was getModelID for runelitecode
        }
        return false;
    }

    public void checkWidgetText() {
        hasPassed = hasPassed || checkWidget();
    }

}
