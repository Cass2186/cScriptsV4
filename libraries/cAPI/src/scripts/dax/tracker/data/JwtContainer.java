package scripts.dax.tracker.data;

import com.allatori.annotations.DoNotRename;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class JwtContainer {
    @DoNotRename
    private String token;
    @DoNotRename
    private String tokenExpiration;
    @DoNotRename
    private String refreshToken;
    @DoNotRename
    private String refreshTokenExpiration;
}
