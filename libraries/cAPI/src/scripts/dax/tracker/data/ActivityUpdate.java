package scripts.dax.tracker.data;

import com.allatori.annotations.DoNotRename;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ActivityUpdate {
    @DoNotRename
    private String scriptId;
    @DoNotRename
    private Map<String, Long> resources;
}
