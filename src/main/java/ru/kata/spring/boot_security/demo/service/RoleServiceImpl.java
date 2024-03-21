package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.utils.RoleNotFoundException;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;




    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
//    @PostConstruct
//    public void initDB() {
//        Role roleAdmin = new Role();
//        roleAdmin.setName("ROLE_ADMIN");
//        Role roleUser = new Role();
//        roleUser.setName("ROLE_USER");
//        roleDao.save(roleAdmin);
//        roleDao.save(roleUser);
//    }

    public Role getRoleAdmin() {
        return roleDao.findByName("ROLE_ADMIN");
    }

    public Role getRoleUser() {
        return roleDao.findByName("ROLE_USER");
    }

    public Set<Role> getAllRole() {
        return new HashSet<>(roleDao.findAll());
    }

    public Role findById(Long id){
        return roleDao.findById(id).orElseThrow(RoleNotFoundException::new);
    }

}
