package REST;

import model.Contact;
import model.Group;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import services.AnalyticalService;
import services.ContactService;
import services.GroupService;
import services.UserService;
import utils.SpringUtils;

import java.util.List;

@RestController
public class ContactController {

    @Autowired
    UserService userService;
    @Autowired
    ContactService contactService;
    @Autowired
    GroupService groupService;
    @Autowired
    AnalyticalService analyticalService;

    @RequestMapping("/getcontact")
    public RequestResult getContact(@RequestParam(value="id", required=false) int contactid) {
        Contact contact = contactService.getContactById(contactid);
        if (contact == null)
            return new RequestResult("FAIL",
                    new Contact());
            else
            return new RequestResult("SUCSESS",
               contact);
    }

    @RequestMapping("/getallcontacts")
    public RequestResult getAllContacts(@RequestParam(value="username", required=false, defaultValue="User3") String username) {
        List<Contact> contactList = contactService.getAllContacts(username);
        if (contactList == null)
            return new RequestResult("FAIL",
                    new Contact());
        else
            return new RequestResult("SUCSESS",
                    contactList);
    }

    @RequestMapping("/addcontact")
    public RequestResult addContact(@RequestParam(value="username", required=true) String username,
                                    @RequestParam(value="name", required=true) String name,
                                    @RequestParam(value="ph_num", required=true) int ph_num,
                                    @RequestParam(value="groupid", required=true) int groupid) {
        Group group = groupService.getGroupById(groupid);
        User user = userService.getUserById(username);
        Contact contact = new Contact(name,ph_num,group);
        contact.setUser(user);
        if (contact == null)
            return new RequestResult("FAIL",
                    new Contact());
        else {
            contactService.addContact(contact);
            return new RequestResult("SUCSESS",
                    contact);
        }
    }

    @RequestMapping("/updatecontact")
    public RequestResult updateContact(@RequestParam(value="contactid", required=true) int contactid,
                                       @RequestParam(value="name", required=true) String name,
                                       @RequestParam(value="ph_num", required=true) int ph_num,
                                       @RequestParam(value="groupid", required=true) int groupid) {
        Group group = groupService.getGroupById(groupid);
        Contact contact = contactService.getContactById(contactid);
        contact.setName(name);
        contact.setPh_number(ph_num);
        contact.setGroup(group);
        if (contact == null)
            return new RequestResult("FAIL",
                    new Contact());
        else {
            contactService.updateContact(contact);
            return new RequestResult("SUCSESS",
                    contact);
        }
    }

    @RequestMapping("/removecontact")
    public RequestResult removeContact(@RequestParam(value="id", required=false) int contactid) {
        Contact contact = contactService.getContactById(contactid);
        if (contact == null)
            return new RequestResult("FAIL",
                    new Contact());
        else
            contactService.delContact(contact);
            return new RequestResult("SUCSESS",
                    contact);
    }

    @RequestMapping("/getgroup")
    public RequestResult getGroup(@RequestParam(value="id", required=true) int groupid) {
        Group group = groupService.getGroupById(groupid);
        if (group == null)
            return new RequestResult("FAIL",
                    new Group());
        else
            return new RequestResult("SUCSESS",
                    group);
    }

    @RequestMapping("/getallgroup")
    public RequestResult getAllGroup(@RequestParam(value="username", required=false, defaultValue="User3") String username) {
        List<Group> groupList = groupService.getAllGroups(username);
        if (groupList == null)
            return new RequestResult("FAIL",
                    new Contact());
        else
            return new RequestResult("SUCSESS",
                    groupList);
    }

    @RequestMapping("/addgroup")
    public RequestResult addGroup(@RequestParam(value="username", required=true) String username,
                                  @RequestParam(value="name", required=true) String name) {
        Group group = new Group(name);
        User user = userService.getUserById(username);
        group.setUser(user);
        if (group == null)
            return new RequestResult("FAIL",
                    new Group());
        else
            groupService.addGroup(group);
            return new RequestResult("SUCSESS",
                    group);
    }

    @RequestMapping("/updategroup")
    public RequestResult updateGroup(@RequestParam(value="groupid", required=true) int groupid,
                                     @RequestParam(value="name", required=true) String name) {
        Group group = groupService.getGroupById(groupid);
        group.setName(name);
        if (group == null)
            return new RequestResult("FAIL",
                    new Group());
        else
        groupService.updateGroup(group);
        return new RequestResult("SUCSESS",
                group);
    }

    @RequestMapping("/removegroup")
    public RequestResult removeGroup(@RequestParam(value="id", required=false) int groupid) {
        Group group = groupService.getGroupById(groupid);
        if (group == null)
            return new RequestResult("FAIL",
                    new Group());
        else
            groupService.delGroup(group);
            return new RequestResult("SUCSESS",
                   group);
    }
}


