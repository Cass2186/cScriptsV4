package scripts;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSInterfaceComponent;

import java.awt.event.KeyEvent;

public class InterfaceUtil {

    public static boolean clickInterfaceAction(int interfaceParent, String action) {
        RSInterface i = Interfaces.findWhereAction(action, interfaceParent);
        return Interfaces.isInterfaceSubstantiated(i) && i.click(action);
    }

    public static boolean click(int parent, int child, int comp) {
        return Interfaces.isInterfaceSubstantiated(parent, child, comp) &&
                Interfaces.get(parent, child, comp).click();

    }

    public static boolean click(int parent, int child) {
        return Interfaces.isInterfaceSubstantiated(parent, child) &&
                Interfaces.get(parent, child).click();
    }
    public static boolean searchInterfaceItemAndClickAction(int interfaceParent, int interfaceChild, int ItemID, String action) {
        if (Interfaces.isInterfaceSubstantiated(interfaceParent, interfaceChild)) {
            int childrenNumber = Interfaces.get(interfaceParent, interfaceChild).getChildren().length;
            for (int i = 0; i < childrenNumber; i++) {
                RSInterface[] items = Interfaces.get(interfaceParent, interfaceChild).getChildren();
                if (items != null) {
                    for (RSInterface x : items) {
                        if (x.getComponentItem() == ItemID)
                            return x.click(action);
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkText(int parent, int child, String text) {
        if (Interfaces.isInterfaceSubstantiated(parent, child)) {
            return Interfaces.get(parent, child).getText().equals(text);
        }
        return false;
    }

    public static boolean clickInterfaceText(int interfaceParent, int interfaceChild, String action) { // capitalization matters
        if (Interfaces.isInterfaceSubstantiated(interfaceParent, interfaceChild)) {
            int childrenNumber = Interfaces.get(interfaceParent, interfaceChild).getChildren().length;

            for (int i = 0; i < childrenNumber; i++) {
                if (Interfaces.get(interfaceParent, interfaceChild, i) != null) {
                    String text = Interfaces.get(interfaceParent, interfaceChild, i).getText();
                    if (text != null) {
                        if (text.contains(action))
                            return Interfaces.get(interfaceParent, interfaceChild, i).click();
                    }
                }
            }

        }
        return false;
    }

    public static boolean clickInterfaceText(int interfaceParent, String action) { // capitalization matters
        if (Interfaces.isInterfaceSubstantiated(interfaceParent)) {
            int childrenNumber = Interfaces.get(interfaceParent).getChildren().length;

            for (int i = 0; i < childrenNumber; i++) {
                if (Interfaces.get(interfaceParent, i) != null) {
                    String text = Interfaces.get(interfaceParent, i).getText();
                    if (text != null) {
                        if (text.contains(action))
                            return Interfaces.get(interfaceParent, i).click();
                    }
                }
            }

        }
        return false;
    }
    public static boolean getInterfaceKeyAndPress(int interfaceParent, String componentName) {
        RSInterfaceComponent[] children;
        int index = 0;
        if (Interfaces.isInterfaceSubstantiated(interfaceParent)) {

            int childrenNumber = Interfaces.get(interfaceParent).getChildren().length;
            General.println("Num of children == " + childrenNumber);
            for (int i = 0; i < childrenNumber; i++) {
                if (Interfaces.get(interfaceParent, i) != null) {
                    String comp = Interfaces.get(interfaceParent, i).getComponentName();

                    if (comp.toLowerCase().contains(componentName.toLowerCase())) {
                        index = i - 14;
                        General.println("Contains; " + componentName + " at index " + (index));

                        children = Interfaces.get(interfaceParent, 13).getChildren();

                        if (children == null) {
                            General.println("[InterfaceUtil]: No text options - Pressing Space");
                            org.tribot.api.input.Keyboard.pressKeys(KeyEvent.VK_SPACE);
                            return true;
                        }

                        if (children.length >= index) {
                            General.println("line 132 : " + children[index].getText());
                            switch (children[index].getText()) {

                                case ("Space"):
                                    General.println("[InterfaceUtil]: Pressing Space");
                                    org.tribot.api.input.Keyboard.pressKeys(KeyEvent.VK_SPACE);
                                    Timer.waitCondition(() -> Interfaces.get(interfaceParent) == null, 2500, 5000);
                                    return true;

                                case ("1"):
                                    General.println("[InterfaceUtil]: Pressing 1");
                                    org.tribot.api.input.Keyboard.typeKeys((char) KeyEvent.VK_1);
                                    Timer.waitCondition(() -> Interfaces.get(interfaceParent) == null, 2500, 5000);
                                    return true;

                                case ("2"):
                                    General.println("[InterfaceUtil]: Pressing 2");
                                    org.tribot.api.input.Keyboard.typeKeys((char) KeyEvent.VK_2);
                                    Timer.waitCondition(() -> Interfaces.get(interfaceParent) == null, 2500, 5000);
                                    return true;

                                case ("3"):
                                    General.println("[InterfaceUtil]: Pressing 3");
                                    org.tribot.api.input.Keyboard.typeKeys((char) KeyEvent.VK_3);
                                    Timer.waitCondition(() -> Interfaces.get(interfaceParent) == null, 2500, 5000);
                                    return true;

                                case ("4"):
                                    General.println("[InterfaceUtil]: Pressing 4");
                                    org.tribot.api.input.Keyboard.typeKeys((char) KeyEvent.VK_4);
                                    Timer.waitCondition(() -> Interfaces.get(interfaceParent) == null, 2500, 5000);
                                    return true;

                                case ("5"):
                                    General.println("[InterfaceUtil]: Pressing 5");
                                    org.tribot.api.input.Keyboard.typeKeys((char) KeyEvent.VK_5);
                                    Timer.waitCondition(() -> Interfaces.get(interfaceParent) == null, 2500, 5000);
                                    return true;

                                case ("6"):
                                    General.println("[InterfaceUtil]: Pressing 6");
                                    org.tribot.api.input.Keyboard.typeKeys((char) KeyEvent.VK_6);
                                    Timer.waitCondition(() -> Interfaces.get(interfaceParent) == null, 2500, 5000);
                                    return true;

                                case ("7"):
                                    General.println("[InterfaceUtil]: Pressing 7");
                                    org.tribot.api.input.Keyboard.typeKeys((char) KeyEvent.VK_7);
                                    Timer.waitCondition(() -> Interfaces.get(interfaceParent) == null, 2500, 5000);
                                    return true;

                                case ("8"):
                                    General.println("[InterfaceUtil]: Pressing 8");
                                    Keyboard.typeKeys((char) KeyEvent.VK_8);
                                    Timer.waitCondition(() -> Interfaces.get(interfaceParent) == null, 2500, 5000);
                                    return true;
                            }
                        }
                    }


                }
            }

        }
        return false;
    }

}
