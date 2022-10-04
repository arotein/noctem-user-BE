package noctem.userService.domain.user.controller;

import lombok.RequiredArgsConstructor;
import noctem.userService.domain.user.dto.request.SearchReqDto;
import noctem.userService.domain.user.service.SearchService;
import noctem.userService.global.common.CommonResponse;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-service/search")
@RequiredArgsConstructor
@PostAuthorize("hasRole('USER')")
public class SearchController {
    private final SearchService searchService;

    @GetMapping("")
    public CommonResponse getSearch() {
        return CommonResponse.builder()
                .data(searchService.getAllQuery())
                .build();
    }

    @PostMapping("")
    public CommonResponse search(@Validated @RequestBody SearchReqDto dto) {
        return CommonResponse.builder()
                .data(searchService.save(dto))
                .build();
    }
}
