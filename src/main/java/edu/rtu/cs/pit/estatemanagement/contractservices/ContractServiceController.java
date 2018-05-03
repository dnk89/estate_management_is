package edu.rtu.cs.pit.estatemanagement.contractservices;

import edu.rtu.cs.pit.estatemanagement.contractservices.domain.AmountCalculationType;
import edu.rtu.cs.pit.estatemanagement.contractservices.domain.ContractService;
import edu.rtu.cs.pit.estatemanagement.contractservices.services.ContractServiceService;
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
public class ContractServiceController {

    private static final Logger LOG = LoggerFactory.getLogger(ContractServiceController.class);

    @Autowired
    private ContractServiceService contractServiceService;

    @RequestMapping(value = "/operator/services", method = RequestMethod.GET)
    public String services(@RequestParam(value = "error", required=false) String error, Model model) {
        model.addAttribute("error", (error != null));
        model.addAttribute("errorText", error);
        List<ContractService> contractServices = contractServiceService.findAll();
        model.addAttribute("services", contractServices);
        return "services";
    }

    @RequestMapping(value = "/operator/services", method = RequestMethod.POST)
    public String ratesSearch(@ModelAttribute("code") String code,
                              @ModelAttribute("description") String description,
                              @ModelAttribute("calculationType") String calculationType,
                              Model model) {

        model.addAttribute("error", false);
        model.addAttribute("errorText", "");
        model.addAttribute("code", code);
        model.addAttribute("description", description);
        List<ContractService> services = null;
        if (calculationType.isEmpty()) {
            model.addAttribute("calculationType", calculationType);
            services = contractServiceService.findBy(code, description);
        } else {
            model.addAttribute("calculationType", AmountCalculationType.valueOf(calculationType));
            services = contractServiceService.findBy(code, description, AmountCalculationType.valueOf(calculationType));
        }
        model.addAttribute("services", services);
        return "services";
    }


    @RequestMapping(value = "/operator/services/create", method = RequestMethod.GET)
    public String createService(Model model) {
        model.addAttribute("code", "");
        model.addAttribute("description", "");
        model.addAttribute("calculationType", AmountCalculationType.AREA);
        model.addAttribute("error", false);
        model.addAttribute("errorText", "");
        return "servicesCreate";
    }

    @RequestMapping(value = "/operator/services/create", method = RequestMethod.POST)
    public String createServicePost(@ModelAttribute("code") String code,
                                    @ModelAttribute("description") String description,
                                    @ModelAttribute("calculationType") AmountCalculationType calculationType,
                                    Model model) throws Exception {
        try {
            contractServiceService.createService(code, description, calculationType);
        } catch (Exception e) {
            String message = String.format("Nevar izveidot pakalpojumu! Kļūda: %s", e.getMessage());
            LOG.error(message);
            model.addAttribute("error", true);
            model.addAttribute("errorText", message);
            model.addAttribute("code", code);
            model.addAttribute("description", description);
            model.addAttribute("calculationType", calculationType);
            return "servicesCreate";
        }
        return "redirect:/operator/services";
    }

    @RequestMapping(value = "/operator/services/edit", method = RequestMethod.GET)
    public String editService(@RequestParam(value = "code") String code, Model model) {
        Optional<ContractService> editService = contractServiceService.findByCode(code);
        if (!editService.isPresent()) {
            return "redirect:/operator/services?error=" + UriUtils.encodeQuery(String.format("Pakalpojums ar kodu '%s' nav atrasts", code), "UTF-8");
        } else {
            model.addAttribute("code", editService.get().getCode());
            model.addAttribute("description", editService.get().getDescription());
            model.addAttribute("calculationType", editService.get().getCalculationType());
            model.addAttribute("error", false);
            model.addAttribute("errorText", "");
        }
        return "servicesEdit";
    }

    @RequestMapping(value = "/operator/services/edit", method = RequestMethod.POST)
    public String editServicePost(@ModelAttribute("code") String code,
                                  @ModelAttribute("description") String description,
                                  @ModelAttribute("calculationType") AmountCalculationType calculationType,
                                  Model model) throws Exception {
        try {
            contractServiceService.editService(code, description, calculationType);
        } catch (Exception e) {
            String message = String.format("Nevar izlabot pakalpojumu! Kļūda: %s", e.getMessage());
            LOG.error(message);
            model.addAttribute("error", true);
            model.addAttribute("errorText", message);
            model.addAttribute("code", code);
            model.addAttribute("description", description);
            model.addAttribute("calculationType", calculationType);
            return "servicesEdit";
        }
        return "redirect:/operator/services";
    }

    @RequestMapping(value = "/operator/services/delete")
    public String deleteService(@RequestParam(value = "code") String code, Model model) {
        try {
            contractServiceService.deleteService(code);
        } catch (Exception e) {
            String message = String.format("Nevar izdzēst pakalpojumu! Kļūda: %s", e.getMessage());
            LOG.error(message);
            return "redirect:/operator/services?error=" + UriUtils.encodeQuery(message, "UTF-8");
        }
        return "redirect:/operator/services";
    }
}
