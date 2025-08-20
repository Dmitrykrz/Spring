package fr.diginamic.hello.controleurs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class testControleur {

    /**
     * @return Returns test
     * very usefull function
     */
    @GetMapping
    public String test_bla_bla_bla() {
        return ("test");
    }
}
