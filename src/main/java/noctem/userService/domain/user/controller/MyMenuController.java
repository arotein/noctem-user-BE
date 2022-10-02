package noctem.userService.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-service/myMenu")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class MyMenuController {
}
