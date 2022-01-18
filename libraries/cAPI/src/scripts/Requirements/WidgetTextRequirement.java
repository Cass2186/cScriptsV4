package scripts.Requirements;

import lombok.Getter;
import lombok.Setter;
import org.tribot.script.sdk.Widgets;
import org.tribot.script.sdk.query.Query;
import org.tribot.script.sdk.types.Widget;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class WidgetTextRequirement implements Requirement {

    @Setter
    @Getter
    protected boolean hasPassed;
    protected boolean onlyNeedToPassOnce;

    @Getter
    private final int groupId;

    private final int childId;
    private final List<String> text;
    private int childChildId = -1;
    private boolean checkChildren;

    // Used to restrict the considered set of children
    private int min = -1;
    private int max = -1;

   /* public WidgetTextRequirement(Widget widgetInfo, String... text) {

        this.groupId = widgetInfo.getGroupId();
        this.childId = widgetInfo.getChildId();
        this.text = Arrays.asList(text);
    }*/

    public WidgetTextRequirement(int groupId, int childId, boolean checkChildren, String... text) {
        this.groupId = groupId;
        this.childId = childId;
        this.checkChildren = checkChildren;
        this.text = Arrays.asList(text);
    }

    public WidgetTextRequirement(int groupId, int childId, String... text) {
        this.groupId = groupId;
        this.childId = childId;
        this.text = Arrays.asList(text);
    }

    public WidgetTextRequirement(int groupId, int childId, int childChildId, String... text) {
        this.groupId = groupId;
        this.childId = childId;
        this.childChildId = childChildId;
        this.text = Arrays.asList(text);
    }

    public void addRange(int min, int max) {
        this.min = min;
        this.max = max;
    }


    public boolean checkWidget() {
        Optional<Widget> widget = Query.widgets().inIndexPath(groupId, childId).findFirst();

        if (widget.isPresent()) {
            if (childChildId != -1) {
                widget = widget.get().getChild(childChildId);
            }
            if (widget.isPresent()) {
                for (String textOption : text) {
                    if (checkChildren) {
                        if (getChildren(widget.get(), textOption)) {
                            return true;
                        }
                    }
                    Optional<String> textOptional = widget.get().getText();
                    if (textOptional.isPresent() && textOptional.get().contains(textOption)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean getChildren(Widget parentWidget, String textOption) {
        Widget[] children = parentWidget.getChildren().toArray(Widget[]::new);
        if (children.length == 0) {
            return false;
        }

        int currentMin;
        int currentMax;
        if (max == -1 || min == -1) {
            currentMin = 0;
            currentMax = children.length;
        } else {
            currentMax = Math.min(children.length, max);
            currentMin = Math.max(0, min);
        }

        for (int i = currentMin; i < currentMax; i++) {
            Widget currentWidget = parentWidget.getChildren().get(i);
            if (currentWidget.getChildren().size() != 0) {
                Optional<String> text = currentWidget.getText();
                if (text.isPresent() &&
                        text.get().contains(textOption)) {
                    return true;
                }
            } else {
                if (getChildren(currentWidget, textOption)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void checkWidgetText() {
        hasPassed = hasPassed || checkWidget();
    }

    @Override
    public boolean check() {
        //     return checkWidget();
        return false;
    }
}
