package ConModel;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="groups")
public class Group implements Serializable {
    @Id
    @Column(name = "groupid")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST,})
    @JoinTable(
            name = "usersgroup",
            joinColumns = @JoinColumn(name = "groupid",referencedColumnName = "groupid"),
            inverseJoinColumns = @JoinColumn(name = "login",referencedColumnName = "login")
    )
    private User user;

    @OneToMany(cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST,})
    @JoinTable(
            name = "contactgroup",
            joinColumns = @JoinColumn(name = "groupid",referencedColumnName = "groupid"),
            inverseJoinColumns = @JoinColumn(name = "contactid",referencedColumnName = "contactid")
    )
    private List<Contact> contactList;

    public Group() {
    }

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Group(int id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }
}
