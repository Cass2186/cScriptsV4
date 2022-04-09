package scripts.Tasks.Construction.ConsData;


import lombok.Getter;
import scripts.ItemID;
import scripts.Requirements.ItemRequirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Plank {
    REGULAR(29, new ItemRequirement("Plank", ItemID.PLANK),
            new ItemRequirement("Steel nails", ItemID.STEEL_NAILS, 2)),
    OAK(60, new ItemRequirement("Oak plank", ItemID.OAK_PLANK)),
    TEAK(90, new ItemRequirement("Teak plank", ItemID.TEAK_PLANK)),
    MAHOGANY(140, new ItemRequirement("Mahogany plank", ItemID.MAHOGANY_PLANK));

    @Getter
    int experienceGiven;

    @Getter
    ItemRequirement[] itemRequirements;

    Plank(int experienceGiven, ItemRequirement... itemRequirements) {
        this.experienceGiven = experienceGiven;
        this.itemRequirements = itemRequirements;
    }

    public int getPlankId(){
        return this.itemRequirements.length > 0 ? this.itemRequirements[0].getId() : -1;
    }
}