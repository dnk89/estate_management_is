package edu.rtu.cs.pit.estatemanagement.users.mvc;

import edu.rtu.cs.pit.estatemanagement.users.domain.Role;
import edu.rtu.cs.pit.estatemanagement.users.domain.User;
import edu.rtu.cs.pit.estatemanagement.users.services.RoleService;
import edu.rtu.cs.pit.estatemanagement.users.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String users(@RequestParam(value = "error", required=false) String error, Model model) {
        model.addAttribute("error", (error != null));
        model.addAttribute("errorText", error);
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("role", "");
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.POST)
    public String usersSearch(@ModelAttribute("username") String username,
                              @ModelAttribute("email") String email,
                              @ModelAttribute("role") String role,
                              Model model) {

        model.addAttribute("error", false);
        model.addAttribute("errorText", "");
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("role", role);
        model.addAttribute("username", username);
        model.addAttribute("email", email);
        List<User> users = userService.findBy(username, email, role);
        model.addAttribute("users", users);
        return "users";
    }

    @RequestMapping(value = "/admin/users/create", method = RequestMethod.GET)
    public String createUser(Model model) {
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("role", roles.get(0).getName());
        model.addAttribute("username", "");
        model.addAttribute("password", "");
        model.addAttribute("email", "");
        model.addAttribute("error", false);
        model.addAttribute("errorText", "");
        return "usersCreate";
    }

    @RequestMapping(value = "/admin/users/create", method = RequestMethod.POST)
    public String createUserPost(@ModelAttribute("username") String username,
                                 @ModelAttribute("password") String password,
                                 @ModelAttribute("email") String email,
                                 @ModelAttribute("role") String role,
                                 Model model) throws Exception {
        try {
            userService.createUser(username, password, email, role);
        } catch (Exception e) {
            String message = String.format("Nevar izveidot lietotāju! Kļūda: %s", e.getMessage());
            LOG.error(message);
            model.addAttribute("error", true);
            model.addAttribute("errorText", message);
            List<Role> roles = roleService.findAll();
            model.addAttribute("roles", roles);
            model.addAttribute("role", role);
            model.addAttribute("username", username);
            model.addAttribute("password", password);
            model.addAttribute("email", email);
            return "usersCreate";
        }
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/admin/users/edit", method = RequestMethod.GET)
    public String editUser(@RequestParam(value = "username") String username, Model model) {
        Optional<User> editUser = userService.findByUsername(username);
        if (!editUser.isPresent()) {
            throw new UsernameNotFoundException(String.format("Lietotājs %s nav atrasts", username));
        }
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("role", editUser.get().getRole().getName());
        model.addAttribute("username", editUser.get().getUsername());
        model.addAttribute("password", userService.decodePassword(editUser.get().getPassword()));
        model.addAttribute("email", editUser.get().getEmail());
        model.addAttribute("error", false);
        model.addAttribute("errorText", "");
        return "usersEdit";
    }

    @RequestMapping(value = "/admin/users/edit", method = RequestMethod.POST)
    public String editUserPost(@ModelAttribute("username") String username,
                               @ModelAttribute("password") String password,
                               @ModelAttribute("email") String email,
                               @ModelAttribute("role") String role,
                               Model model) throws Exception {
        try {
            userService.editUser(username, password, email, role);
        } catch (Exception e) {
            String message = String.format("Nevar izlabot lietotāju! Kļūda: %s", e.getMessage());
            LOG.error(message);
            model.addAttribute("error", true);
            model.addAttribute("errorText", message);
            List<Role> roles = roleService.findAll();
            model.addAttribute("roles", roles);
            model.addAttribute("role", role);
            model.addAttribute("username", username);
            model.addAttribute("password", password);
            model.addAttribute("email", email);
            return "usersEdit";
        }
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/admin/users/delete")
    public String deleteUser(@RequestParam(value = "username") String username, Model model) {
        try {
            userService.deleteUser(username);
        } catch (Exception e) {
            String message = String.format("Nevar izdzēst lietotāju! Kļūda: %s", e.getMessage());
            LOG.error(message);
            return "redirect:/admin/users?error=" + UriUtils.encodeQuery(message, "UTF-8");
        }
        return "redirect:/admin/users";
    }
}
