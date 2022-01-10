package scripts.dax.tracker.data;

import com.allatori.annotations.DoNotRename;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

@Getter
@ToString
public class Activity {
    @DoNotRename
    private String id;
    @DoNotRename
    private String userID;
    @DoNotRename
    private String scriptID;
    @DoNotRename
    private Date timestamp;
    @DoNotRename
    private Map<String, Integer> resources;
}
