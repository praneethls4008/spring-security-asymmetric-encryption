package com.learning.spring_security_asymmetric_encryption.user;

import com.learning.spring_security_asymmetric_encryption.user.request.ChangePasswordRequest;
import com.learning.spring_security_asymmetric_encryption.user.request.ProfileUpdateRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserService userService;

    @PatchMapping("/me")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateProfileInfo(
            @RequestBody
            @Valid
            final ProfileUpdateRequest request,
            final Authentication principle
            ){
        this.userService.updateProfileInfo(request, getUserId(principle));
    }

    @PostMapping("/me/password")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void changePassword(
            @RequestBody
            @Valid
            final ChangePasswordRequest request,
            final Authentication principle
    ){
        this.userService.changePassword(request, getUserId(principle));
    }

    @PatchMapping("/me/deactivate")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deactivateAccount(final Authentication principle){
        this.userService.deactivateAccount(getUserId(principle));
    }

    @PatchMapping("/me/reactivate")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void reactivateAccount(final Authentication principle){
        this.userService.reactivateAccount(getUserId(principle));
    }

    private String getUserId(Authentication principle) {
        return ((User) principle.getPrincipal()).getId();
    }
}
