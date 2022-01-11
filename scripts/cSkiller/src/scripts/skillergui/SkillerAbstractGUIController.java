package scripts.skillergui;

import javafx.fxml.Initializable;

/**
 * @author Laniax
 */

public abstract class SkillerAbstractGUIController implements Initializable {

    private SkillerGUI gui = null;

    public void setGUI(SkillerGUI gui) {
        this.gui = gui;
    }

    public SkillerGUI getGUI() {
        return this.gui;
    }


}