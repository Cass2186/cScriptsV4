package scripts.AgilityAPI;

public enum COURSES {

    TREE_GNOME_STRONGHOLD("Tree Gnome Stronghold"),
    DRAYNOR_VILLAGE ("Draynor Village"),
    VARROCK("Varrock"),
    CANIFIS("Canifis"),
    FALADOR("Falador"),
    SEERS_VILLAGE("Seers Village"),
    POLLNIVEACH("Pollnivneach"),
    RELLEKA("Relleka");


    public String courseName;

    COURSES(String name){
        this.courseName = name;
    }
}
