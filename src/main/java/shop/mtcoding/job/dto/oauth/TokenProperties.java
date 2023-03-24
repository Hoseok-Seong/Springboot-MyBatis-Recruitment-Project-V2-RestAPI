package shop.mtcoding.job.dto.oauth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenProperties {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;
    private String email;
}
