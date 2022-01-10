package scripts.Requirements;

import org.tribot.script.sdk.query.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FollowerRequirement implements Requirement {
    List<Integer> followers;
    String text;

    public FollowerRequirement(String text, Integer... followers) {
        this.text = text;
        this.followers = new ArrayList<>();
        Collections.addAll(this.followers, followers);
    }

    public FollowerRequirement(String text, List<Integer> followers) {
        this.text = text;
        this.followers = followers;
    }

    @Override
    public boolean check() {
        return Query.npcs().isInteractingWithMe()
                .stream()
                .anyMatch(npc -> followers.contains(npc.getId()));
    }


}
