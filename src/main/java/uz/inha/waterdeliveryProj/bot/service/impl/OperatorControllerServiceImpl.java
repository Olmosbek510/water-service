package uz.inha.waterdeliveryProj.bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import uz.inha.waterdeliveryProj.bot.entity.District;
import uz.inha.waterdeliveryProj.bot.entity.TelegramUser;
import uz.inha.waterdeliveryProj.bot.entity.enums.TelegramState;
import uz.inha.waterdeliveryProj.bot.model.Location;
import uz.inha.waterdeliveryProj.bot.model.response.UserInfoDTO;
import uz.inha.waterdeliveryProj.bot.model.response.WaitingUserDto;
import uz.inha.waterdeliveryProj.bot.service.DistrictService;
import uz.inha.waterdeliveryProj.bot.service.OperatorControllerService;
import uz.inha.waterdeliveryProj.bot.service.TelegramUserService;
import uz.inha.waterdeliveryProj.bot.utils.DistrictUtil;
import uz.inha.waterdeliveryProj.repo.DistrictRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperatorControllerServiceImpl implements OperatorControllerService {
    private final TelegramUserService telegramUserService;
    private final DistrictService districtService;
    private final DistrictUtil districtUtil;

    @Override
    public void addUnverifiedUsers(Model model) {
        List<TelegramUser> telegramUsers = telegramUserService.findByState(TelegramState.WAITING);
        List<WaitingUserDto> waitingUserDtos = telegramUsers.stream().map(telegramUser -> WaitingUserDto.builder()
                .id(telegramUser.getId())
                .phone(telegramUser.getUser().getPhone())
                .createdAt(telegramUser.getCreatedAt())
                .build()).toList();
        System.out.println(waitingUserDtos);
        model.addAttribute("unverifiedUsers", waitingUserDtos);
    }

    @Transactional
    @Override
    public void loadChosenUser(UUID id, Model model) {
        TelegramUser telegramUser = telegramUserService.findById(id);
        model.addAttribute("districts", districtService.findByRegionId(telegramUser.getRegion().getId()));
        model.addAttribute("userInfo", telegramUser);
        String districtName = districtUtil.getDistrictName(telegramUser.getLocation());
        System.out.println(districtName);
        District bySimilarity = districtService.findBySimilarity(districtName);
        model.addAttribute("currentDistrict", bySimilarity);
    }
}
