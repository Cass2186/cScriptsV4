package scripts.dax.tracker.data;

import com.allatori.annotations.DoNotRename;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserCredentials {
    @DoNotRename
    private String name;
    @DoNotRename
    private String id;
    @DoNotRename
    private String secretKey;
}