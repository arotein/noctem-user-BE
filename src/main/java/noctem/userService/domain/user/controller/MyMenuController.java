package noctem.userService.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${global.api.base-path}/myMenu")
@RequiredArgsConstructor
@PostAuthorize("hasRole('USER')")
public class MyMenuController {
}
