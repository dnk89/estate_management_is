package edu.rtu.cs.pit.estatemanagement.common.mvc;

import com.sun.deploy.security.UserDeclinedException;
import edu.rtu.cs.pit.estatemanagement.users.domain.Role;
import edu.rtu.cs.pit.estatemanagement.users.domain.User;
import edu.rtu.cs.pit.estatemanagement.users.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomeController {

    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "redirect:/admin/users";
    }

    @RequestMapping("/operator")
    public String operator() {
        return "redirect:/operator/contracts";
    }

    @RequestMapping("/customer")
    public String customer() {
        return "redirect:/customer/contracts";
    }

    @RequestMapping("/authorization")
    public String authorization(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user.getRole().equalsIgnoreCase(Role.ADMIN.toString())) {
            return "redirect:/admin";
        } else if (user.getRole().equalsIgnoreCase(Role.OPERATOR.toString())) {
            return "redirect:/operator";
        } else if (user.getRole().equalsIgnoreCase(Role.CUSTOMER.toString())) {
            return "redirect:/customer";
        } else {
            LOG.warn(String.format("User with role %s is not allowed", user.getRole()));
            return "redirect:/index?error";
        }
    }
}
