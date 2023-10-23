package com.casemodul4.repository.role;

import com.casemodul4.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
Role findByName(String name);
}