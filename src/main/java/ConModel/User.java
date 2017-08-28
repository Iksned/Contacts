package ConModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="users")

public class User implements Serializable {
    @Id
    @Column(name = "login")
    private String username;

    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "usersgroup",
            joinColumns = @JoinColumn(name = "login",referencedColumnName = "login"),
            inverseJoinColumns = @JoinColumn(name = "groupid",referencedColumnName = "groupid")
    )
    private List<Group> grouplist;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "userscontact",
            joinColumns = @JoinColumn(name = "login",referencedColumnName = "login"),
            inverseJoinColumns = @JoinColumn(name = "contactid",referencedColumnName = "contactid")
    )
    private List<Contact> contactList;

    public User() {
    }

    public User(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Group> getGrouplist() {
        return grouplist;
    }

    public void setGrouplist(List<Group> grouplist) {
        this.grouplist = grouplist;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }
}
