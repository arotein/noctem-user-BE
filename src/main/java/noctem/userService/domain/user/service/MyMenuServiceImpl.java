package noctem.userService.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.userService.domain.user.dto.MenuComparisonJsonDto;
import noctem.userService.domain.user.dto.request.*;
import noctem.userService.domain.user.dto.response.MyMenuListResDto;
import noctem.userService.domain.user.entity.MyMenu;
import noctem.userService.domain.user.entity.MyPersonalOption;
import noctem.userService.domain.user.feignClient.MenuFeignClient;
import noctem.userService.domain.user.repository.MyMenuRepository;
import noctem.userService.domain.user.repository.UserAccountRepository;
import noctem.userService.global.common.CommonException;
import noctem.userService.global.enumeration.Amount;
import noctem.userService.global.security.bean.ClientInfoLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MyMenuServiceImpl implements MyMenuService {
    private final UserAccountRepository userAccountRepository;
    private final MyMenuRepository myMenuRepository;
    private final ClientInfoLoader clientInfoLoader;
    private final MenuFeignClient menuFeignClient;

    @Override
    public List<MyMenuListResDto> getMyMenuList() {
        List<MyMenu> myMenuList = myMenuRepository.findAllByUserAccountId(clientInfoLoader.getUserAccountId());
        List<MyMenuListResDto> data = menuFeignClient.getMyMenuListDtoList(myMenuList.stream()
                        .map(e -> new MyMenuAndOptionsReqServDto(e.getSizeId(),
                                e.getMyPersonalOptionList().stream()
                                        .map(MyPersonalOption::getPersonalOptionId)
                                        .collect(Collectors.toList())))
                        .collect(Collectors.toList()))
                .getData();
        // MyMenuListResDto에 별칭 추가하는 로직 추가하여 리턴
        return null;
    }

    @Override
    public Boolean addMyMenu(AddMyMenuReqDto dto) {
        List<MyMenu> myMenuList = myMenuRepository.findAllByUserAccountId(clientInfoLoader.getUserAccountId());
        Map<String, MyMenu> myMenuMap = new HashMap<>();
        myMenuList.forEach(e -> myMenuMap.put(new MenuComparisonJsonDto().myMenuAndOptionEntityToJson(e), e));
        String dtoJson = new MenuComparisonJsonDto().addMyMenuReqDtoToJson(dto);
        if (myMenuMap.containsKey(dtoJson)) {
            // 이미 존재하는 메뉴
            return false;
        } else {
            // 존재하지 않는 메뉴 -> 추가
            MyMenu myMenu = MyMenu.builder().alias(dto.getAlias()).sizeId(dto.getSizeId()).build();
            myMenu.linkToUserAccount(userAccountRepository.getById(clientInfoLoader.getUserAccountId()));
            dto.getPersonalOptionList().forEach(e ->
                    myMenu.linkToMyPersonalOption(MyPersonalOption.builder()
                            .personalOptionId(e.getOptionId())
                            .amount(Amount.findByValue(e.getAmount()))
                            .build())
            );
            myMenuRepository.save(myMenu);
        }
        return true;
    }

    @Override
    public Boolean changeMyMenuOrder(ChangeMyMenuOrderReqDto dto) {
        Map<Long, Integer> indexMap = dto.generateIndexMap();
        List<MyMenu> myMenuList = myMenuRepository.findAllByUserAccountId(clientInfoLoader.getUserAccountId());
        myMenuList.forEach(e -> e.changeMyMenuOrder(indexMap.get(e.getId())));
        return true;
    }

    @Override
    public Boolean changeMyMenuAlias(Long myMenuId, ChangeMyMenuAliasReqDto dto) {
        identificationMyMenu(myMenuId).changeAlias(dto.getAlias());
        return true;
    }

    @Override
    public Boolean delMyMenu(Long myMenuId) {
        myMenuRepository.delete(identificationMyMenu(myMenuId));
        return true;
    }

    // 요청한 myMenu가 본인 것이 맞는지 확인
    private MyMenu identificationMyMenu(Long myMenuId) {
        MyMenu myMenu = myMenuRepository.getById(myMenuId);
        if (!Objects.equals(myMenu.getUserAccount().getId(), clientInfoLoader.getUserAccountId())) {
            throw CommonException.builder().errorCode(2001).httpStatus(HttpStatus.UNAUTHORIZED).build();
        }
        return myMenu;
    }
}