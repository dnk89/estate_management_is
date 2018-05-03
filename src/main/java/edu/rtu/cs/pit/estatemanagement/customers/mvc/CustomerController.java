package edu.rtu.cs.pit.estatemanagement.customers.mvc;

import edu.rtu.cs.pit.estatemanagement.contractservices.domain.AmountCalculationType;
import edu.rtu.cs.pit.estatemanagement.contractservices.domain.ContractService;
import edu.rtu.cs.pit.estatemanagement.customers.domain.Customer;
import edu.rtu.cs.pit.estatemanagement.customers.domain.CustomerType;
import edu.rtu.cs.pit.estatemanagement.customers.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CustomerController {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/operator/customers", method = RequestMethod.GET)
    public String services(@RequestParam(value = "error", required=false) String error, Model model) {
        model.addAttribute("error", (error != null));
        model.addAttribute("errorText", error);
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "customers";
    }

    @RequestMapping(value = "/operator/customers", method = RequestMethod.POST)
    public String customersSearch(@ModelAttribute("code") String code,
                              @ModelAttribute("description") String description,
                              @ModelAttribute("phone") String phone,
                              @ModelAttribute("email") String email,
                              @ModelAttribute("address") String address,
                              @ModelAttribute("customerType") String customerType,
                              Model model) {

        model.addAttribute("error", false);
        model.addAttribute("errorText", "");
        model.addAttribute("code", code);
        model.addAttribute("description", description);
        model.addAttribute("phone", phone);
        model.addAttribute("email", email);
        model.addAttribute("address", address);
        List<Customer> customers = null;
        if (customerType.isEmpty()) {
            model.addAttribute("customerType", customerType);
            customers = customerService.findBy(code, description, phone, email, address);
        } else {
            model.addAttribute("customerType", CustomerType.valueOf(customerType));
            customers = customerService.findBy(code, description, phone, email, address, CustomerType.valueOf(customerType));
        }
        model.addAttribute("customers", customers);
        return "customers";
    }

    @RequestMapping(value = "/operator/customers/create", method = RequestMethod.GET)
    public String createCustomer(Model model) {
        model.addAttribute("code", "");
        model.addAttribute("description", "");
        model.addAttribute("phone", "");
        model.addAttribute("email", "");
        model.addAttribute("address", "");
        model.addAttribute("customerType", CustomerType.PERSON);
        model.addAttribute("error", false);
        model.addAttribute("errorText", "");
        return "customersCreate";
    }

    @RequestMapping(value = "/operator/customers/create", method = RequestMethod.POST)
    public String createCustomerPost(@ModelAttribute("code") String code,
                                     @ModelAttribute("description") String description,
                                     @ModelAttribute("phone") String phone,
                                     @ModelAttribute("email") String email,
                                     @ModelAttribute("address") String address,
                                     @ModelAttribute("customerType") CustomerType customerType,
                                     Model model) throws Exception {
        try {
            customerService.createCustomer(code, description, phone, email, address, customerType);
        } catch (Exception e) {
            String message = String.format("Nevar izveidot klientu! Kļūda: %s", e.getMessage());
            LOG.error(message);
            model.addAttribute("error", true);
            model.addAttribute("errorText", message);
            model.addAttribute("code", code);
            model.addAttribute("description", description);
            model.addAttribute("phone", phone);
            model.addAttribute("email", email);
            model.addAttribute("address", address);
            model.addAttribute("customerType", customerType);
            return "customersCreate";
        }
        return "redirect:/operator/customers";
    }

    @RequestMapping(value = "/operator/customers/edit", method = RequestMethod.GET)
    public String editCustomer(@RequestParam(value = "code") String code, Model model) {
        Optional<Customer> editCustomer = customerService.findByCode(code);
        if (!editCustomer.isPresent()) {
            return "redirect:/operator/customers?error=" + UriUtils.encodeQuery(String.format("Klients ar kodu '%s' nav atrasts", code), "UTF-8");
        } else {
            model.addAttribute("code", editCustomer.get().getCode());
            model.addAttribute("description", editCustomer.get().getDescription());
            model.addAttribute("phone", editCustomer.get().getPhone());
            model.addAttribute("email", editCustomer.get().getEmail());
            model.addAttribute("address", editCustomer.get().getAddress());
            model.addAttribute("customerType", editCustomer.get().getCustomerType());
            model.addAttribute("error", false);
            model.addAttribute("errorText", "");
        }
        return "customersEdit";
    }

    @RequestMapping(value = "/operator/customers/edit", method = RequestMethod.POST)
    public String editCustomerPost(@ModelAttribute("code") String code,
                                   @ModelAttribute("description") String description,
                                   @ModelAttribute("phone") String phone,
                                   @ModelAttribute("email") String email,
                                   @ModelAttribute("address") String address,
                                   @ModelAttribute("customerType") CustomerType customerType,
                                   Model model) throws Exception {
        try {
            customerService.editCustomer(code, description, phone, email, address, customerType);
        } catch (Exception e) {
            String message = String.format("Nevar izlabot klientu! Kļūda: %s", e.getMessage());
            LOG.error(message);
            model.addAttribute("error", true);
            model.addAttribute("errorText", message);
            model.addAttribute("code", code);
            model.addAttribute("description", description);
            model.addAttribute("phone", phone);
            model.addAttribute("email", email);
            model.addAttribute("address", address);
            model.addAttribute("customerType", customerType);
            return "customersEdit";
        }
        return "redirect:/operator/customers";
    }

    @RequestMapping(value = "/operator/customers/delete")
    public String deleteCustomer(@RequestParam(value = "code") String code, Model model) {
        try {
            customerService.deleteCustomer(code);
        } catch (Exception e) {
            String message = String.format("Nevar izdzēst klientu! Kļūda: %s", e.getMessage());
            LOG.error(message);
            return "redirect:/operator/customers?error=" + UriUtils.encodeQuery(message, "UTF-8");
        }
        return "redirect:/operator/customers";
    }
}
