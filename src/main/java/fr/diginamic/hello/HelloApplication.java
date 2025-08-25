package fr.diginamic.hello;

import fr.diginamic.hello.services.DepartementService;
import fr.diginamic.hello.services.VilleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(VilleService villeService, DepartementService departementService) {
        return args -> {
            Departement herault = departementService
                    .extractDepartement("Hérault")
                    .orElseGet(() -> {
                        Departement d = new Departement();
                        d.setId(34);
                        d.setNom("Hérault");
                        departementService.insertDepartement(d);
                        return d;
                    });


            Ville montpellier = villeService.extractVille("Montpellier");
            if (montpellier == null) {
                montpellier = new Ville("Montpellier", 300000);
                montpellier.setDepartement(herault);
                villeService.insertVille(montpellier);
            }

            Ville lattes = villeService.extractVille("Lattes");
            if (lattes == null) {
                lattes = new Ville("Lattes", 3000);
                lattes.setDepartement(herault);
                villeService.insertVille(lattes);
            }


            Ville bezier = villeService.extractVille("Bezier");
            if (bezier == null) {
                bezier = new Ville("Bezier", 20000);
                bezier.setDepartement(herault);
                villeService.insertVille(bezier);
            }




        };
    }
}





