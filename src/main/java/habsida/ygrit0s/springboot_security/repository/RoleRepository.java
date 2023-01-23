package habsida.ygrit0s.springboot_security.repository;

import habsida.ygrit0s.springboot_security.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
