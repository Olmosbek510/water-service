package uz.inha.waterdeliveryProj.bot.service;

import org.springframework.ui.Model;

import java.util.UUID;

public interface OperatorControllerService {
    void addUnverifiedUsers(Model model);

    void loadChosenUser(UUID id, Model model);
}
