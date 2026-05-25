package bo.com.micrium.modulobase.security.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

/**
 *
 * @author alepaco.maton
 * @deprecated No estoy usando esta clase
 */
@Component
public class JwtUserDetailsService implements UserDetailsService, Serializable {

    private static final long serialVersionUID = 1L;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

        return new User(username, "$2y$11$V.E4/XV6cC2ws7Xm7pojBuDRZQZjJFDbgAW1usXIZroYfAbBWIuCu", authorities);
    }
}
