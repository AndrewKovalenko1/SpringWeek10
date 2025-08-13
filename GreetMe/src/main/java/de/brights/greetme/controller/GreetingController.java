package de.brights.greetme.controller;

import de.brights.greetme.service.GreetingService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private final GreetingService greetingService;

    //Dependency injection for GreetingService is used here
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("greet/{lang}/{name}")
    public ResponseEntity<String> greet(
            @PathVariable String lang,
            @PathVariable String name,
            HttpServletResponse response
    ) {
        String greeting;

        //Save cookies
        Cookie langCookie = new Cookie("lang", lang);
        langCookie.setPath("/greet");
        response.addCookie(langCookie);
        Cookie nameCookie = new Cookie("name", name);
        nameCookie.setPath("/greet");
        response.addCookie(nameCookie);

        return new ResponseEntity<>(greetingService.getGreeting(name, lang), HttpStatus.OK);
    }

    @GetMapping("/greet")
    public ResponseEntity<String> greetWithCookie(HttpServletRequest request) {
        String lang = null;
        String name = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("lang")) {
                    lang = cookie.getValue();
                }
                if (cookie.getName().equals("name")) {
                    name = cookie.getValue();
                }
            }
        }
        if (lang != null && name != null) {
            return new ResponseEntity<>(greetingService.getGreeting(name, lang), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No name/lang saved in cookies", HttpStatus.BAD_REQUEST);
        }
    }

}


