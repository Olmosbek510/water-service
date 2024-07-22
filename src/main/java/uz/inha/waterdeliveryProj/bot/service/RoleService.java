package uz.inha.waterdeliveryProj.bot.service;

import uz.inha.waterdeliveryProj.bot.entity.Role;
import uz.inha.waterdeliveryProj.bot.entity.enums.RoleName;

public interface RoleService {
    Role save(Role role);

    Role findByName(RoleName roleName);
}
