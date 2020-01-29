package web.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "role")
    private String role;


    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
/*
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
*/

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
