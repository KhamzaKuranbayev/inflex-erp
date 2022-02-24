package ai.ecma.appauth.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO {
    private String tokenType;

    private String accessToken;

    private String refreshToken;

    private UUID deviceKey;
}
