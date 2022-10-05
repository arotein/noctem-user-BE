package noctem.userService.domain.user.service;

import noctem.userService.domain.user.dto.request.AddMyMenuReqDto;
import noctem.userService.domain.user.dto.request.ChangeMyMenuAliasReqDto;
import noctem.userService.domain.user.dto.request.ChangeMyMenuOrderReqDto;
import noctem.userService.domain.user.dto.response.MyMenuListResDto;

import java.util.List;

public interface MyMenuService {
    List<MyMenuListResDto> getMyMenuList();

    Boolean addMyMenu(AddMyMenuReqDto dto);

    Boolean changeMyMenuOrder(ChangeMyMenuOrderReqDto dto);

    Boolean changeMyMenuAlias(Long myMenuId, ChangeMyMenuAliasReqDto dto);

    Boolean delMyMenu(Long myMenuId);

}
