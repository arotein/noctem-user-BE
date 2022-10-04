package noctem.userService.domain.user.repository;

import noctem.userService.domain.user.entity.Search;

import java.util.Set;

public interface SearchRepository {
    Boolean save(Search search);

    Set<String> findAllByUserAccountId(Long id);
}
