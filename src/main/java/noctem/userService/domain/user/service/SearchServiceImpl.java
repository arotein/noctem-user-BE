package noctem.userService.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.userService.domain.user.dto.request.SearchReqDto;
import noctem.userService.domain.user.entity.Search;
import noctem.userService.domain.user.repository.SearchRepository;
import noctem.userService.global.security.bean.ClientInfoLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final SearchRepository searchRepository;
    private final ClientInfoLoader clientInfoLoader;

    @Override
    public Boolean save(SearchReqDto dto) {
        return searchRepository.save(Search.builder().query(dto.getQuery()).build().generateByUserAccountId(clientInfoLoader.getUserAccountId()));
    }

    @Override
    @Transactional(readOnly = true)
    public Set<String> getAllQuery() {
        return searchRepository.findAllByUserAccountId(clientInfoLoader.getUserAccountId());
    }
}
