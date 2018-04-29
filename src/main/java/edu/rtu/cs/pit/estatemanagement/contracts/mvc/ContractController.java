package edu.rtu.cs.pit.estatemanagement.contracts.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContractController {

    @RequestMapping(value = "/operator/contracts")
    public String operatorContracts(Model model) {
        return "operatorContracts";
    }

    @RequestMapping(value = "/customer/contracts")
    public String customerContracts(Model model) {
        return "customerContracts";
    }
}
