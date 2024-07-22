package uz.inha.waterdeliveryProj.bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.inha.waterdeliveryProj.bot.entity.Role;
import uz.inha.waterdeliveryProj.bot.entity.enums.RoleName;
import uz.inha.waterdeliveryProj.bot.service.RoleService;
import uz.inha.waterdeliveryProj.repo.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findByName(RoleName roleName) {
        return roleRepository.findByName(roleName);
    }
}
