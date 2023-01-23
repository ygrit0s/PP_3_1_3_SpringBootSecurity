package habsida.ygrit0s.springboot_security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import habsida.ygrit0s.springboot_security.entity.*;
import habsida.ygrit0s.springboot_security.repository.*;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> roleList() {
        return roleRepository.findAll();
    }


    public void addRole(Role role) {
        roleRepository.save(role);
    }

    public Role getRole(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Role with id %s not found", id));
        } else {
            return role.get();
        }
    }
}
