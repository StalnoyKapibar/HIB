package tst.pp08.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Role implements GrantedAuthority {



    private int id;



    private String role;



    private List<User> users;


    public Role() {

    }


    public Role(String roleName) {
        role = roleName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    @Override
    public String getAuthority() {
        return role;
    }


}
