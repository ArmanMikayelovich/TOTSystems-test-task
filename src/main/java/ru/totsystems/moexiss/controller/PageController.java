package ru.totsystems.moexiss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("")
public class PageController {
    @GetMapping("/page")
    public ModelAndView getMainPage() {
        return new ModelAndView("index");
    }

    @GetMapping("")
    public ModelAndView getHomePage() {
        return new ModelAndView("index");
    }


    @GetMapping("/security-crud")
    public ModelAndView getSecurityCRUDPage() {
        return new ModelAndView("security");
    }

    @GetMapping("/history-crud")
    public ModelAndView getHistoryCRUDPage() {
        return new ModelAndView("history");
    }

}
