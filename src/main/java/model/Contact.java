package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;

@JsonSerialize
@Entity
@Table(name="contacts")
public class Contact implements Serializable{
    @Id
    @Column(name = "contactid")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private int ph_number;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(
            name = "contactgroup",
            joinColumns = @JoinColumn(name = "contactid",referencedColumnName = "contactid"),
            inverseJoinColumns = @JoinColumn(name = "groupid",referencedColumnName = "groupid")
    )
    private Group group;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(
            name = "userscontact",
            joinColumns = @JoinColumn(name = "contactid",referencedColumnName = "contactid"),
            inverseJoinColumns = @JoinColumn(name = "login",referencedColumnName = "login")
    )
    private User user;

    public Contact(){};

    public Contact(String name, int ph_number, Group group) {
        this.name = name;
        this.ph_number = ph_number;
        this.group = group;
    }

    public Contact(String name, int ph_number) {
        this.name = name;
        this.ph_number = ph_number;
    }

    public Contact(int id, String name, int ph_number, Group group, User user) {
        this.id = id;
        this.name = name;
        this.ph_number = ph_number;
        this.group = group;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPh_number() {
        return ph_number;
    }

    public void setPh_number(int ph_number) {
        this.ph_number = ph_number;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (!name.equals(contact.name)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        return result;
    }
}
