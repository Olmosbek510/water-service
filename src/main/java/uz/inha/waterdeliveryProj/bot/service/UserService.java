package uz.inha.waterdeliveryProj.bot.service;

import uz.inha.waterdeliveryProj.bot.entity.User;

public interface UserService {
    User save(User user);

    User findByPhone(String username);
}
