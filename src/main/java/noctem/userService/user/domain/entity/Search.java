package noctem.userService.user.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@RedisHash(value = "search")
public class Search implements Serializable {
    @Id // org.springframework.data.annotation.Id
    private String id;
    private String query;
    private LocalDateTime createdAt;

    @Builder
    public Search(String query) {
        this.query = query;
        this.createdAt = LocalDateTime.now();
    }

    public Search generateByUserAccountId(Long userAccountId) {
        this.id = String.format("search:query:%d", userAccountId);
        return this;
    }

    public static String getSearchIdFormat(Long userAccountId) {
        return String.format("search:query:%d", userAccountId);
    }
}
