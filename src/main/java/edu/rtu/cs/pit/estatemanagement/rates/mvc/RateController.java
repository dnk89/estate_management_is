package edu.rtu.cs.pit.estatemanagement.rates.mvc;

import edu.rtu.cs.pit.estatemanagement.rates.domain.Rate;
import edu.rtu.cs.pit.estatemanagement.rates.services.RateService;
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
public class RateController {

    private static final Logger LOG = LoggerFactory.getLogger(RateController.class);

    @Autowired
    private RateService rateService;

    @RequestMapping(value = "/operator/rates", method = RequestMethod.GET)
    public String rates(@RequestParam(value = "error", required=false) String error, Model model) {
        model.addAttribute("error", (error != null));
        model.addAttribute("errorText", error);
        List<Rate> rates = rateService.findAll();
        model.addAttribute("rates", rates);
        return "rates";
    }

    @RequestMapping(value = "/operator/rates", method = RequestMethod.POST)
    public String ratesSearch(@ModelAttribute("code") String code,
                              @ModelAttribute("description") String description,
                              Model model) {

        model.addAttribute("error", false);
        model.addAttribute("errorText", "");
        model.addAttribute("code", code);
        model.addAttribute("description", description);
        List<Rate> rates = rateService.findBy(code, description);
        model.addAttribute("rates", rates);
        return "rates";
    }

    @RequestMapping(value = "/operator/rates/create", method = RequestMethod.GET)
    public String createRate(Model model) {
        model.addAttribute("code", "");
        model.addAttribute("description", "");
        model.addAttribute("value", "0.00");
        model.addAttribute("error", false);
        model.addAttribute("errorText", "");
        return "ratesCreate";
    }

    @RequestMapping(value = "/operator/rates/create", method = RequestMethod.POST)
    public String createRatePost(@ModelAttribute("code") String code,
                                 @ModelAttribute("description") String description,
                                 @ModelAttribute("value") String value,
                                 Model model) throws Exception {
        try {
            rateService.createRate(code, description, Double.valueOf(value));
        } catch (Exception e) {
            String message = String.format("Nevar izveidot tarifu! Kļūda: %s", e.getMessage());
            LOG.error(message);
            model.addAttribute("error", true);
            model.addAttribute("errorText", message);
            model.addAttribute("code", code);
            model.addAttribute("description", description);
            model.addAttribute("value", value);
            return "ratesCreate";
        }
        return "redirect:/operator/rates";
    }

    @RequestMapping(value = "/operator/rates/edit", method = RequestMethod.GET)
    public String editRate(@RequestParam(value = "code") String code, Model model) {
        Optional<Rate> editRate = rateService.findByCode(code);
        if (!editRate.isPresent()) {
            return "redirect:/operator/rates?error=" + UriUtils.encodeQuery(String.format("Tarifs ar kodu '%s' nav atrasts", code), "UTF-8");
        } else {
            model.addAttribute("code", editRate.get().getCode());
            model.addAttribute("description", editRate.get().getDescription());
            model.addAttribute("value", editRate.get().getValue());
            model.addAttribute("error", false);
            model.addAttribute("errorText", "");
        }
        return "ratesEdit";
    }

    @RequestMapping(value = "/operator/rates/edit", method = RequestMethod.POST)
    public String editRatePost(@ModelAttribute("code") String code,
                               @ModelAttribute("description") String description,
                               @ModelAttribute("value") String value,
                               Model model) throws Exception {
        try {
            rateService.editRate(code, description, Double.valueOf(value));
        } catch (Exception e) {
            String message = String.format("Nevar izlabot tarifu! Kļūda: %s", e.getMessage());
            LOG.error(message);
            model.addAttribute("error", true);
            model.addAttribute("errorText", message);
            model.addAttribute("code", code);
            model.addAttribute("description", description);
            model.addAttribute("value", value);
            return "ratesEdit";
        }
        return "redirect:/operator/rates";
    }

    @RequestMapping(value = "/operator/rates/delete")
    public String deleteRate(@RequestParam(value = "code") String code, Model model) {
        try {
            rateService.deleteRate(code);
        } catch (Exception e) {
            String message = String.format("Nevar izdzēst tarifu! Kļūda: %s", e.getMessage());
            LOG.error(message);
            return "redirect:/operator/rates?error=" + UriUtils.encodeQuery(message, "UTF-8");
        }
        return "redirect:/operator/rates";
    }
}
