package scripts.dax.tracker.data;

import com.allatori.annotations.DoNotRename;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActivitiesRequest {
    @DoNotRename
    private String scriptID;
    @DoNotRename
    private String period;
}
