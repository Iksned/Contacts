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

import java.util.ArrayList;
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

    private RequestResult requestFactory(Object ob) {
        if (ob == null)
            return new RequestResult("FAIL",
                    null);
        else
            return new RequestResult("SUCCESS",
                    ob);
    }

    @RequestMapping("/getcontact")
    public RequestResult getContact(@RequestParam(value="id", required=false) int contactid) {
        Contact contact = contactService.getContactById(contactid);
        return requestFactory(contact);
    }

    @RequestMapping("/getallcontacts")
    public RequestResult getAllContacts(@RequestParam(value="username", required=false, defaultValue="User3") String username) {
        List<Contact> contactList = contactService.getAllContacts(username);
        return requestFactory(contactList);
    }

    @RequestMapping("/addcontact")
    public RequestResult addContact(@RequestParam(value="username", required=true) String username,
                                    @RequestParam(value="name", required=true) String name,
                                    @RequestParam(value="ph_num", required=true) int ph_num,
                                    @RequestParam(value="groupid", required=true) int groupid) {
        Group group = groupService.getGroupById(groupid);
        User user = userService.getUserById(username);
        List<Group> groupList = new ArrayList<>();
        groupList.add(group);
        Contact contact = new Contact(name,ph_num,groupList);
        contact.setUser(user);
        return requestFactory(contact);
    }

    @RequestMapping("/updatecontact")
    public RequestResult updateContact(@RequestParam(value="contactid", required=true) int contactid,
                                       @RequestParam(value="name", required=true) String name,
                                       @RequestParam(value="ph_num", required=true) int ph_num,
                                       @RequestParam(value="groupid", required=true) int groupid) {
        Group group = groupService.getGroupById(groupid);
        Contact contact = contactService.getContactById(contactid);
        List<Group> groupList = new ArrayList<>();
        groupList.add(group);
        contact.setName(name);
        contact.setPh_number(ph_num);
        contact.setGroup(groupList);
        return requestFactory(contact);
    }

    @RequestMapping("/removecontact")
    public RequestResult removeContact(@RequestParam(value="id", required=false) int contactid) {
        Contact contact = contactService.getContactById(contactid);
        return requestFactory(contact);
    }

    @RequestMapping("/getgroup")
    public RequestResult getGroup(@RequestParam(value="id", required=true) int groupid) {
        Group group = groupService.getGroupById(groupid);
        return requestFactory(group);
    }

    @RequestMapping("/getallgroup")
    public RequestResult getAllGroup(@RequestParam(value="username", required=false, defaultValue="User3") String username) {
        List<Group> groupList = groupService.getAllGroups(username);
        return requestFactory(groupList);
    }

    @RequestMapping("/addgroup")
    public RequestResult addGroup(@RequestParam(value="username", required=true) String username,
                                  @RequestParam(value="name", required=true) String name) {
        Group group = new Group(name);
        User user = userService.getUserById(username);
        group.setUser(user);
        return requestFactory(group);
    }

    @RequestMapping("/updategroup")
    public RequestResult updateGroup(@RequestParam(value="groupid", required=true) int groupid,
                                     @RequestParam(value="name", required=true) String name) {
        Group group = groupService.getGroupById(groupid);
        group.setName(name);
        return requestFactory(group);
    }

    @RequestMapping("/removegroup")
    public RequestResult removeGroup(@RequestParam(value="id", required=false) int groupid) {
        Group group = groupService.getGroupById(groupid);
        return requestFactory(group);
    }
}


