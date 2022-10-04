package noctem.userService.domain.user.service;

import noctem.userService.domain.user.dto.request.SearchReqDto;

import java.util.Set;

public interface SearchService {
    Boolean save(SearchReqDto dto);

    Set<String> getAllQuery();
}
