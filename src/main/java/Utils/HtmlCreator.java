package Utils;

import ConModel.Contact;
import ConModel.services.Services;

import java.io.IOException;

public class HtmlCreator {

    public static String createFailLoginHTML() throws IOException {
        String html =  "<html>\n" +
                "    <head>\n" +
                "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "        <title>Authorithation</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                " <center> "+ "Wrong Login or Password" + " </center>" +
                "<center>"+
                "\t\t<table border=\"0\" width=\"30%\" cellpadding=\"3\">\n" +
                "\t\t<tbody>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "\t\t\t\t\t<a href=\"jsp/countUsers.jsp\">Count All Users</a>" +
                "\t\t\t\t</td>\n" +
                "                <td>\n" +
                "\t\t\t\t\t<a href=\"jsp/countUsersContacts.jsp\">Count Users Contacts</a>" +
                "\t\t\t\t</td>\n" +
                "                <td>\n" +
                "\t\t\t\t\t<a href=\"jsp/countUsersGroups.jsp\">Count Users Groups</a>" +
                "\t\t\t\t</td>\n" +
                "                <td>\n" +
                "\t\t\t\t\t<a href=\"jsp/avgContactsinGroups.jsp\">AVG Contacts in Groups</a>" +
                "\t\t\t\t</td>\n" +
                "                <td>\n" +
                "\t\t\t\t\t<a href=\"jsp/avgContacts.jsp\">Users AVG Contacts</a>" +
                "\t\t\t\t</td>\n" +
                "                <td>\n" +
                "\t\t\t\t\t<a href=\"jsp/inactiveUsers.jsp\">Inactive Users</a>" +
                "\t\t\t\t</td>\n" +
                "            </tr>\n" +
                "\t\t</tbody>\n" +
                "\t\t</table>\n" +
                "</center>"+
                "        <form method=\"post\" action=\"login\">\n" +
                "            <center>\n" +
                "            <table border=\"1\" width=\"30%\" cellpadding=\"3\">\n" +
                "                <thead>\n" +
                "                    <tr>\n" +
                "                        <th colspan=\"2\">Login Here</th>\n" +
                "                    </tr>\n" +
                "                </thead>\n" +
                "                <tbody>\n" +
                "                    <tr>\n" +
                "                        <td>User Name</td>\n" +
                "                        <td><input type=\"text\" name=\"uname\" value=\"User2\" /></td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td>Password</td>\n" +
                "                        <td><input type=\"password\" name=\"pass\" value=\"Password2\" /></td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td><input type=\"submit\" value=\"Login\" /></td>\n" +
                "                        <td><input type=\"reset\" value=\"Reset\" /></td>\n" +
                "                    </tr>                    \n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "            </center>\n" +
                "        </form>\n" +
                "    </body>\n" +
                "</html>";
        return html;
    }

    public static String createContactListHTML(String username,String[] names)
    {
        String[] contactNames = names;
        String html = "<html>\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <title>Contact List</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <center>\n" +
                "\t\t<table border=\"0\" width=\"30%\" cellpadding=\"3\">\n" +
                "\t\t<tbody>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "\t\t\t\t\t<a href=\"jsp/countUsers.jsp\">Count All Users</a>" +
                "\t\t\t\t</td>\n" +
                "                <td>\n" +
                "\t\t\t\t\t<a href=\"jsp/countUsersContacts.jsp\">Count Users Contacts</a>" +
                "\t\t\t\t</td>\n" +
                "                <td>\n" +
                "\t\t\t\t\t<a href=\"jsp/countUsersGroups.jsp\">Count Users Groups</a>" +
                "\t\t\t\t</td>\n" +
                "                <td>\n" +
                "\t\t\t\t\t<a href=\"jsp/avgContactsinGroups.jsp\">AVG Contacts in Groups</a>" +
                "\t\t\t\t</td>\n" +
                "                <td>\n" +
                "\t\t\t\t\t<a href=\"jsp/avgContacts.jsp\">Users AVG Contacts</a>" +
                "\t\t\t\t</td>\n" +
                "                <td>\n" +
                "\t\t\t\t\t<a href=\"jsp/inactiveUsers.jsp\">Inactive Users</a>" +
                "\t\t\t\t</td>\n" +
                "            </tr>\n" +
                "\t\t</tbody>\n" +
                "\t\t</table>\n" +
                "        <table border=\"1\" width=\"30%\" cellpadding=\"3\">\n" +
                "            <thead>\n" +
                "            <tr>\n" +
                "                <th colspan=\"5\">" +username +" Contacts</th>\n" +
                "            </tr>\n" +
                "            </thead>\n" +
                "            <tbody>" +
                "            <tr><td>Name</td>" +
                "            <td>Phone Number</td>" +
                "            <td>Group</td></tr>       ";

        for (int i = 0;i<contactNames.length;i++)
        {
            Contact contact = Services.getContactByName(contactNames[i],username);
            String name = contactNames[i];
            String phnumber = contact.getPh_number();
            String groupName = contact.getGroup().getName();
            html = html +  "<tr><td>"+ name+ "</td>";
            html = html + "<td>"+phnumber+"</td>";
            html = html + "<td>"+groupName+"</td>";
            html = html + "<td> <form method=\"get\" action=\"updateservlet\"> <input type=\"submit\" value=\"Update\"/><input type=\"hidden\"\n" +
                    "            name=\"name\"\n" +
                    "            value="+name+">" +
                    " <input type=\"hidden\"\n" +
                    "            name=\"phnum\"\n" +
                    "            value="+phnumber+">" +
                    " <input type=\"hidden\"\n" +
                    "            name=\"groupname\"\n" +
                    "            value="+groupName+"></form></td>";
            html = html + "<td> <form method=\"post\" action=\"deleteservlet\"> <input type=\"submit\" value=\"Delete\"/> <input type=\"hidden\"\n" +
                    "            name=\"name\"\n" +
                    "            value="+name+">" +
                    " <input type=\"hidden\"\n" +
                    "            name=\"phnum\"\n" +
                    "            value="+phnumber+">" +
                    " <input type=\"hidden\"\n" +
                    "            name=\"groupname\"\n" +
                    "            value="+groupName+"></form></td></tr>";

        }
        html = html+
                "            </tbody>\n" +
                "        </table>\n" +
                "<form method=\"get\" action=\"addservlet\"> <input type=\"submit\" value=\"Add Contact\"/></form> " +
                " <form method=\"get\" action=\"grouplist\"> <input type=\"submit\" value=\"Show Groups\"/></form>  " +
                "</center>\n" +
                "</body>\n" +
                "</html>";
        return html;
    }

    public static String createAddContactHTML(String[] groupNames) {
        String html = "<html>\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <title>Add Contact</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form method=\"post\" action=\"addservlet\">\n" +
                "    <center>\n" +
                "        <table border=\"1\" width=\"30%\" cellpadding=\"3\">\n" +
                "            <thead>\n" +
                "            <tr>\n" +
                "                <th colspan=\"2\">Enter Data</th>\n" +
                "            </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                "            <tr>\n" +
                "                <td>Name</td>\n" +
                "                <td><input type=\"text\" name=\"name\" value=\"\" /></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>Phone Number</td>\n" +
                "                <td><input type=\"text\" name=\"ph_num\" value=\"\" /></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>Group</td>\n" +
                "                <td><select name=\"groupname\" datatype=\"text\">\n";
                  for (String groupName : groupNames) {
                      html = html + "<option value=" + groupName + ">" + groupName + "</option>";
                  }
               html = html +
                "                </select> </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td><input type=\"submit\" value=\"Add\" /></td>\n" +
                "                <td><input type=\"reset\" value=\"Reset\" /></td>\n" +
                "            </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </center>\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>";

        return html;
    }

    public static String createUpdateContactHTML(String[] groupNames) {
        String html = "<html>\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <title>Update Contact</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form method=\"post\" action=\"updateservlet\">\n" +
                "    <center>\n" +
                "        <table border=\"1\" width=\"30%\" cellpadding=\"3\">\n" +
                "            <thead>\n" +
                "            <tr>\n" +
                "                <th colspan=\"2\">Enter Data</th>\n" +
                "            </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                "            <tr>\n" +
                "                <td>Name</td>\n" +
                "                <td><input type=\"text\" name=\"name\" value=\"\" /></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>Phone Number</td>\n" +
                "                <td><input type=\"text\" name=\"ph_num\" value=\"\" /></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>Group</td>\n" +
                "                <td><select name=\"groupname\" datatype=\"text\">\n";
        for (String groupName : groupNames) {
            html = html + "<option value=" + groupName + ">" + groupName + "</option>";
        }
        html = html +
                "                </select> </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td><input type=\"submit\" value=\"Update\" /></td>\n" +
                "                <td><input type=\"reset\" value=\"Reset\" /></td>\n" +
                "            </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </center>\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>";

        return html;
    }

    public static String createGroupListHTML(String usetName,String[] groupNames) {
        String html = "<html>\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <title>Group List</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <center>\n" +
                "\t\t<table border=\"0\" width=\"30%\" cellpadding=\"3\">\n" +
                "\t\t<tbody>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "\t\t\t\t\t<a href=\"jsp/countUsers.jsp\">Count All Users</a>" +
                "\t\t\t\t</td>\n" +
                "                <td>\n" +
                "\t\t\t\t\t<a href=\"jsp/countUsersContacts.jsp\">Count Users Contacts</a>" +
                "\t\t\t\t</td>\n" +
                "                <td>\n" +
                "\t\t\t\t\t<a href=\"jsp/countUsersGroups.jsp\">Count Users Groups</a>" +
                "\t\t\t\t</td>\n" +
                "                <td>\n" +
                "\t\t\t\t\t<a href=\"jsp/avgContactsinGroups.jsp\">AVG Contacts in Groups</a>" +
                "\t\t\t\t</td>\n" +
                "                <td>\n" +
                "\t\t\t\t\t<a href=\"jsp/avgContacts.jsp\">Users AVG Contacts</a>" +
                "\t\t\t\t</td>\n" +
                "                <td>\n" +
                "\t\t\t\t\t<a href=\"jsp/inactiveUsers.jsp\">Inactive Users</a>" +
                "\t\t\t\t</td>\n" +
                "            </tr>\n" +
                "\t\t</tbody>\n" +
                "\t\t</table>\n" +
                "        <table border=\"1\" width=\"30%\" cellpadding=\"3\">\n" +
                "            <thead>\n" +
                "            <tr>\n" +
                "                <th colspan=\"5\">" + usetName +" Groups</th>\n" +
                "            </tr>\n" +
                "            </thead>\n" +
                "            <tbody>" +
                "            <tr><td>Group name</td>" +
                "            </tr>       ";

        for (int i = 0;i<groupNames.length;i++)
        {
            String name = groupNames[i];
            html = html +  "<tr><td>"+ name+ "</td>";
            html = html + "<td> <form method=\"get\" action=\"updategroupservlet\"> <input type=\"submit\" value=\"Update\"/><input type=\"hidden\"\n" +
                    "            name=\"name\"\n" +
                    "            value="+name+">" +
                    "            </form></td>";
            html = html + "<td> <form method=\"post\" action=\"deletegroupservlet\"> <input type=\"submit\" value=\"Delete\"/> <input type=\"hidden\"\n" +
                    "            name=\"name\"\n" +
                    "            value="+name+">" +
                    "            </form></td></tr>";
        }
        html = html+
                "            </tbody>\n" +
                "        </table>\n" +
                "<form method=\"get\" action=\"addgroupservlet\"> <input type=\"submit\" value=\"Add Group\"/></form> " +
                " <form method=\"get\" action=\"contactlist\"> <input type=\"submit\" value=\"Show Contacts\"/></form>  " +
                "</center>\n" +
                "</body>\n" +
                "</html>";
        return html;
    }

    public static String createAddGroupHTML() {
        String html = "<html>\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <title>Add Group</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form method=\"post\" action=\"addgroupservlet\">\n" +
                "    <center>\n" +
                "        <table border=\"1\" width=\"30%\" cellpadding=\"3\">\n" +
                "            <thead>\n" +
                "            <tr>\n" +
                "                <th colspan=\"2\">Enter Data</th>\n" +
                "            </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                "            <tr>\n" +
                "                <td>Name</td>\n" +
                "                <td><input type=\"text\" name=\"name\" value=\"\" /></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td><input type=\"submit\" value=\"Add\" /></td>\n" +
                "                <td><input type=\"reset\" value=\"Reset\" /></td>\n" +
                "            </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </center>\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>";

        return html;
    }

    public static String createUpdateGroupHTML(String name) {
        String html = "<html>\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <title>Update Group</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form method=\"post\" action=\"updategroupservlet\">\n" +
                "    <center>\n" +
                "        <table border=\"1\" width=\"30%\" cellpadding=\"3\">\n" +
                "            <thead>\n" +
                "            <tr>\n" +
                "                <th colspan=\"2\">Enter Data</th>\n" +
                "            </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                "            <tr>\n" +
                "                <td>Name</td>\n" +
                "                <td><input type=\"text\" name=\"name\" value=\""+name+"\" /></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td><input type=\"submit\" value=\"Update\" /></td>\n" +
                "                <td><input type=\"reset\" value=\"Reset\" /></td>\n" +
                "            </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </center>\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>";
        return html;
    }
}
