package scripts.Data.Enums;

public enum Trees {

    OAK_SAPPLING(5370,15, 12000),
    WILLOW_SAPPLING(5371,30,16800),
    MAPLE_SAPPLING(5372,45,19200),
    YEW_SAPPLING(5373,60,24000),
    MAGIC_SAPPLING(5374,75,28800),

    // FRUIT
    APPLE_SAPPLING(5496, 27),
    BANANA_SAPPLING(5497, 33),
    ORANGE_SAPPLING(5498, 39),
    CURRY_SAPPLING(5499, 42),
    PINEAPPLE_SAPPLING(5500,51),
    PAPAYA_SAPPLING(5501,57),
    PALM_SAPPLING(5502, 68),
    DRAGONFRUIT_SAPPLING(22866, 81);



    public int id;
    public int levelReq;
    public int timeToGrowMs;

    Trees(int id, int level){
        this.id = id;
        this.levelReq = level;
    }

    Trees(int id, int level, int secToGrow){
        this.id = id;
        this.levelReq = level;
        this.timeToGrowMs = secToGrow*1000;
    }

}