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
            Departement herault = departementService.extractDepartement("Hérault");
            if (herault == null) {
                herault = new Departement();
                herault.setNom("Hérault");
                departementService.insertDepartement(herault);
            }

            Ville montpellier = villeService.extractVille("Montpellier");
            if (montpellier == null) {
                montpellier = new Ville("Montpellier", 300000);
                montpellier.setDepartement(herault);
                villeService.insertVille(montpellier);
            }

            Ville Lattes = villeService.extractVille("Lattes");
            if (Lattes == null) {
                Lattes = new Ville("Lattes", 3000);
                Lattes.setDepartement(herault);
                villeService.insertVille(Lattes);
            }


            Ville Bezier = villeService.extractVille("Bezier");
            if (Bezier == null) {
                Bezier = new Ville("Bezier", 20000);
                Bezier.setDepartement(herault);
                villeService.insertVille(Bezier);
            }




        };
    }
}

//http://localhost:8080/departements

//{
//        "nom": "Occitanie"
//        }



//http://localhost:8080/villes
//{
//  "nom": "Lattes",
//  "nbHabitants": 3000,
//  "departement": {
//    "id": 1
//  }
//}




