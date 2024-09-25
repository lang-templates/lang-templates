package jc.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String SayHello(Model model) {
        System.out.println("SayHello() called!");
        model.addAttribute("msg", "This is Hello World Message!!!");
        return "page";
    }
}
