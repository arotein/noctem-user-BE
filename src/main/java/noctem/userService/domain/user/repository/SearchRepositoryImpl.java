package noctem.userService.domain.user.repository;

import lombok.RequiredArgsConstructor;
import noctem.userService.domain.user.entity.Search;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepository {
    private final long NUMBER_TO_SAVE = 5L;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Boolean save(Search search) {
        ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
        opsForZSet.add(search.getId(), search.getQuery(), search.getCreatedAt().getTime());
        opsForZSet.removeRange(search.getId(), -1 - NUMBER_TO_SAVE, -1 - NUMBER_TO_SAVE);
        return true;
    }

    @Override
    public Set<String> findAllByUserAccountId(Long id) {
        return redisTemplate.opsForZSet().reverseRange(Search.getSearchIdFormat(id), 0, NUMBER_TO_SAVE);
    }
}
