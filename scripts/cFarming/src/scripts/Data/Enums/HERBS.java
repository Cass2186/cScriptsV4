package scripts.Data.Enums;

public enum HERBS {

    GUAM(5291),
    MARRENTILL(5292),
    TARROMIN(5293),
    HARRALANDER(5294),
    RANARR(5295),
    TOADFLAX(5296),
    IRIT(5297),
    KWUARM(5299),
    SNAPDRAGON(5300);

    public int id;
    public String name;

    private HERBS(int id){
        this.id = id;
    }


}
