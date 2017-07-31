package ConModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Contact implements Serializable{
    private String name;
    private String ph_number;
    private Group group;

    Contact(){};

    public Contact(String name, String ph_number, Group group) {
        this.name = name;
        this.ph_number = ph_number;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPh_number() {
        return ph_number;
    }

    public void setPh_number(String ph_number) {
        this.ph_number = ph_number;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (!name.equals(contact.name)) return false;
        if (!ph_number.equals(contact.ph_number)) return false;
        return group != null ? group.equals(contact.group) : contact.group == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + ph_number.hashCode();
        result = 31 * result + (group != null ? group.hashCode() : 0);
        return result;
    }
}
