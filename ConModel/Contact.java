package ConModel;

import java.io.Serializable;


public class Contact implements Serializable{
    private String name;
    private String ph_number;
    private String group;

    public Contact(String name) {
        this.name = name;
    }

    public Contact(String name, String ph_number, String group) {
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
