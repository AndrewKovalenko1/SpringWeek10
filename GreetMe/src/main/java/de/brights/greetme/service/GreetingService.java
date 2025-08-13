package de.brights.greetme.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GreetingService {

    //storage for names + counters
    private Map<String, Integer> greetingsMap = new HashMap<>();

    public String getGreeting(String name, String lang) {
        String greeting;
        switch (lang.toLowerCase()) {
            case "de":
                greeting = "Hallo, " + name + "!";
                break;
            case "en":
                greeting = "Hello, " + name + "!";
                break;
            case "uk":
                greeting = "Привіт, " + name + "!";
                break;
            default:
                greeting = "Salve, " + name + "!";
        }
        increaseCounter(name);
        return greeting + "\n This is your visit: " + greetingsMap.get(name);
    }

    private void increaseCounter(String name) {
        if (greetingsMap.containsKey(name)) {
            greetingsMap.put(name, greetingsMap.get(name) + 1);
        } else  {
            greetingsMap.put(name, 1);
        }

        //greetingsMap.computeIfPresent(name, (k,v) -> v + 1);

        //greetingsMap.merge(name, 1, Integer::sum);

       // greetingsMap.compute(name, (k, v) -> (v == null) ? 1 : v + 1);
    }

}
