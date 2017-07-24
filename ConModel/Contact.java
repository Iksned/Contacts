package ConModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Contact implements Serializable{
    private String name;
    private String ph_number;
    private String group;
    private List<String> contactGroups;

    Contact(String name) {
        this.name = name;
    }

    public Contact(String name, String ph_number, String group) {
        this.name = name;
        this.ph_number = ph_number;
        this.group = group;
        contactGroups = new ArrayList<>();
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (!name.equals(contact.name)) return false;
        if (!ph_number.equals(contact.ph_number)) return false;
        return contactGroups != null ? contactGroups.equals(contact.contactGroups) : contact.contactGroups == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + ph_number.hashCode();
        result = 31 * result + (contactGroups != null ? contactGroups.hashCode() : 0);
        return result;
    }
}
