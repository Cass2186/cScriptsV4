package scripts;

import lombok.Getter;
import org.tribot.script.sdk.types.LocalTile;

public class InstanceUtil {

    @Getter
    private LocalTile anchorTile;

    public InstanceUtil(LocalTile anchorTile){
        this.anchorTile = anchorTile;
    }


    public LocalTile getTile(int x, int y){
        return this.anchorTile.translate(x,y);
    }



}
