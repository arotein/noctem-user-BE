package noctem.userService.user.domain.repository;

import noctem.userService.user.domain.entity.Search;

import java.util.Set;

public interface SearchRepository {
    Boolean save(Search search);

    Set<String> findAllByUserAccountId(Long id);
}
