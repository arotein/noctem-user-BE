package noctem.userService.global.security.auth;

import lombok.Getter;
import noctem.userService.domain.user.entity.UserAccount;
import noctem.userService.global.security.dto.ClientInfoDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class UserDetailsImpl implements UserDetails {
    private final Long id;
    private String email;
    private String password;
    private final ClientInfoDto clientInfoDto;
    private final List<GrantedAuthority> authorities;

    public UserDetailsImpl(UserAccount userAccount, Collection<? extends GrantedAuthority> authorities) {
        this.id = userAccount.getId();
        this.email = userAccount.getEmail();
        this.password = userAccount.getPassword();
        this.authorities = (List<GrantedAuthority>) authorities;
        this.clientInfoDto = new ClientInfoDto(userAccount.getId(), userAccount.getNickname(), userAccount.getEmail(), userAccount.getRole());
    }

    public UserDetailsImpl(ClientInfoDto clientInfoDto, Collection<? extends GrantedAuthority> authorities) {
        this.id = clientInfoDto.getId();
        this.clientInfoDto = clientInfoDto;
        this.authorities = (List<GrantedAuthority>) authorities;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
