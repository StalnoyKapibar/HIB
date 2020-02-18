package tst.pp08.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tst.pp08.model.Role;


import java.util.List;


@Service
public class RoleServiceImp implements RoleService {


    @Autowired
    private RestTemplate restTemplate;




    @Override
    public void add(Role role) {
        final String uri = "http://localhost:8081/server/addrole";
        restTemplate.postForObject(uri, role, String.class);


    }

    @Override
    public List<Role> getAllRoles() {
        final String uri = "http://localhost:8081/server/allroles";
        return restTemplate.getForObject(uri, List.class);
    }

    @Override
    public Role getRoleById(int id) {
        final String uri = "http://localhost:8081/server/getrolebyid";
        return restTemplate.postForObject(uri, id, Role.class);
    }


}
