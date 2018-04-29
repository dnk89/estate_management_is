package edu.rtu.cs.pit.estatemanagement.users.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @RequestMapping(value = "/admin/users")
    public String users(Model model) {
        return "users";
    }
}
