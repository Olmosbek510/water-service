package uz.inha.waterdeliveryProj.bot.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.inha.waterdeliveryProj.bot.entity.TelegramUser;
import uz.inha.waterdeliveryProj.bot.entity.enums.TelegramState;
import uz.inha.waterdeliveryProj.bot.service.BotService;
import uz.inha.waterdeliveryProj.bot.service.OperatorControllerService;
import uz.inha.waterdeliveryProj.bot.service.TelegramUserService;
import uz.inha.waterdeliveryProj.bot.service.UserService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/operator")
@RequiredArgsConstructor
public class OperatorController {
    private final OperatorControllerService operatorControllerService;
    private final TelegramUserService telegramUserService;
    private final BotService botService;

    @GetMapping
    public String operatorPage(Model model) {
        return "operator/operator";
    }

    @GetMapping("/waiting-users")
    public String waitingUsers(Model model) {
        operatorControllerService.addUnverifiedUsers(model);
        return "operator/waitingUsers";
    }

    @PostMapping("/userverify/{id}")
    public String verifyUser(@PathVariable UUID id, Model model) {
        operatorControllerService.loadChosenUser(id, model);
        return "operator/verify";
    }

    @PostMapping("/wronglocation")
    public String wrongLocation(@RequestParam(name = "userId") UUID tgUserId) {
        TelegramUser tgUser = telegramUserService.findById(tgUserId);
        botService.sendLocationButton(tgUser);
        return "redirect:/operator";
    }
}