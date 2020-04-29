package ru.rti.crm_modules_stub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rti.crm_modules_stub.service.StubStateService;

import javax.servlet.http.HttpServletRequest;

import static java.lang.Boolean.parseBoolean;

@Controller
@RequestMapping("/api")
public class StubStateController {

    private StubStateService stubStateService;

    @Autowired
    public StubStateController(StubStateService stubStateService) {
        this.stubStateService = stubStateService;
    }

    @PostMapping("/setOpvStubValue")
    public String getStubState(HttpServletRequest request) {
        boolean opvStubState = parseBoolean(request.getParameter("opvStubState"));
        boolean orrStubState = parseBoolean(request.getParameter("orrStubState"));
        boolean ccmStubState = parseBoolean(request.getParameter("ccmStubState"));
        stubStateService.setOpvStubIsEnabled(opvStubState);
        stubStateService.setOrrStubIsEnabled(orrStubState);
        stubStateService.setCcmStubIsEnabled(ccmStubState);
        return "redirect:/api/getStubValue";
    }

    @GetMapping("/getStubValue")
    public String getStubStateView(Model model) {
        model.addAttribute("opvStubState", stubStateService.isOpvStubIsEnabled());
        model.addAttribute("orrStubState", stubStateService.isOrrStubIsEnabled());
        model.addAttribute("ccmStubState", stubStateService.isCcmStubIsEnabled());
        return "index";
    }
}
