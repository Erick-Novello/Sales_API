package ericknovello.com.github.sales_api.controller;

import ericknovello.com.github.sales_api.exception.InvalidPassowrdException;
import ericknovello.com.github.sales_api.model.Users;
import ericknovello.com.github.sales_api.model.dto.CredencialDto;
import ericknovello.com.github.sales_api.model.dto.TokenDto;
import ericknovello.com.github.sales_api.service.UsersService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @PostMapping
    @ApiOperation(value = "Create new user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User create with successfully"),
            @ApiResponse(code = 400, message = "Validation error"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    public Users saveUsers(@RequestBody @Valid Users users) {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return usersService.save(users);
    }

    public TokenDto authenticate(@RequestBody CredencialDto credencialDto) {
        try {
            Users users = Users.builder()
                    .login(credencialDto.getLogin())
                    .password(credencialDto.getPassword())
                    .build();
            usersService.authenticate(users);
            String token = jwtService.generateToken(users);
            return new TokenDto(users.getLogin(), token);
        } catch (UsernameNotFoundException | InvalidPassowrdException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
