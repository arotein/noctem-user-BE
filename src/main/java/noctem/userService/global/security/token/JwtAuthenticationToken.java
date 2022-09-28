package noctem.userService.global.security.token;

import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import noctem.userService.global.security.auth.UserDetailsImpl;
import noctem.userService.global.security.dto.ClientInfoDto;
import noctem.userService.global.security.dto.SecurityLoginReqDto;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal; // UserDetailsImpl객체 저장
    private String jwt;
    private Object credentials;
    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_SIGNER = System.getenv("NOCTEM_JWT_SIGNER");
    public static final String JWT_ISSUER = "Cafe Noctem";
    public static final String JWT_USER_ID = "id";
    public static final String JWT_NICKNAME = "nickname";
    public static final String JWT_EMAIL = "email";
    public static final String JWT_ROLE = "role";

    public JwtAuthenticationToken(SecurityLoginReqDto loginReqDto) {
        super(null);
        this.principal = loginReqDto.getEmail();
        this.credentials = loginReqDto.getPassword();
        setAuthenticated(false);
    }

    public JwtAuthenticationToken(UserDetailsImpl userDetails) {
        super(userDetails.getAuthorities());
        this.principal = userDetails;
        this.jwt = generateJwt(userDetails);
        super.setAuthenticated(true);
    }

    private String generateJwt(UserDetailsImpl userDetails) {
        ClientInfoDto clientInfoDto = userDetails.getClientInfoDto();

        HMACSigner secretKey = HMACSigner.newSHA256Signer(JWT_SIGNER);
        JWT rawJwt = new JWT()
                .setIssuer(JWT_ISSUER)
                .addClaim(JWT_USER_ID, clientInfoDto.getId())
                .addClaim(JWT_NICKNAME, clientInfoDto.getNickname())
                .addClaim(JWT_EMAIL, clientInfoDto.getEmail())
                .addClaim(JWT_ROLE, clientInfoDto.getRole())
                .setExpiration(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).plusYears(1));
        // 요청헤더 Authorization : Bearer <JWT>
        return String.format("Bearer %s", JWT.getEncoder().encode(rawJwt, secretKey));
    }

    public String getJwt() {
        return jwt;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}