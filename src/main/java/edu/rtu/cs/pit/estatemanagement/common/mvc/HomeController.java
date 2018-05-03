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
import java.util.Optional;

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
        return "redirect:/operator/rates";
    }

    @RequestMapping("/customer")
    public String customer() {
        return "redirect:/customer/contracts";
    }

    @RequestMapping("/authorization")
    public String authorization(Principal principal) {
        Optional<User> user = userService.findByUsername(principal.getName());
        if (!user.isPresent()) {
            LOG.warn(String.format("Lietotājs %s nav atrasts", principal.getName()));
            return "redirect:/index?error";
        }
        if (user.get().getRole().getName().equalsIgnoreCase("ROLE_ADMIN")) {
            return "redirect:/admin";
        } else if (user.get().getRole().getName().equalsIgnoreCase("ROLE_OPERATOR")) {
            return "redirect:/operator";
        } else if (user.get().getRole().getName().equalsIgnoreCase("ROLE_CUSTOMER")) {
            return "redirect:/customer";
        } else {
            LOG.warn(String.format("Lietotājs ar tipu %s nav atļauts", user.get().getRole()));
            return "redirect:/index?error";
        }
    }
}
