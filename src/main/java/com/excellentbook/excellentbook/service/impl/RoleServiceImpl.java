package com.excellentbook.excellentbook.service.impl;

import com.excellentbook.excellentbook.entity.Role;
import com.excellentbook.excellentbook.repository.RoleRepository;
import com.excellentbook.excellentbook.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void createRoles() {

        List<Role> roles = roleRepository.findAll();

        if (roles.isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }
    }
}
