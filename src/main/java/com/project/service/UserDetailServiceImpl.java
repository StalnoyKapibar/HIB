package com.project.service;

import com.project.dao.UserAccountDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Transactional
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserAccountDao userAccountDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, NoResultException {
        return userAccountDao.findByEmail(username).get();
    }
}
