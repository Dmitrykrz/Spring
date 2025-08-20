package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.Ville;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    @GetMapping
    public List<Ville> getVilles() {

        List<Ville> villes = new ArrayList<>();
        villes.add(new Ville("Montpellier", 200000));
        villes.add(new Ville("Perols", 15000));

        return villes;
    }
}



