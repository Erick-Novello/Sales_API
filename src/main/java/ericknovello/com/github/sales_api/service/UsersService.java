package ericknovello.com.github.sales_api.service;

import ericknovello.com.github.sales_api.exception.InvalidPassowrdException;
import ericknovello.com.github.sales_api.model.Users;
import ericknovello.com.github.sales_api.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Users save(Users users) {
        return usersRepository.save(users);
    }

    public void authenticate(Users user) {
        UserDetails userDetails = loadUserByUsername(user.getLogin());
        boolean passwordOk = passwordEncoder.matches(user.getPassword(), userDetails.getPassword());

        if (passwordOk) {
            return;
        }

        throw new InvalidPassowrdException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users userReceipt = usersRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in dataBase"));
        String[] roles = userReceipt.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User.builder()
                .username(userReceipt.getLogin())
                .password(userReceipt.getPassword())
                .roles(roles)
                .build();
    }
}
