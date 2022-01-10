package scripts.dax.tracker.data;

import com.allatori.annotations.DoNotRename;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class LoginRequest {
    @DoNotRename
    private String userId;
    @DoNotRename
    private String secretKey;
    @DoNotRename
    private String refreshToken;
}
