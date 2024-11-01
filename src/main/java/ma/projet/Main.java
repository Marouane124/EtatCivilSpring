package ma.projet;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.services.FemmeService;
import ma.projet.services.HommeService;
import ma.projet.services.MariageService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("ma.projet");
        context.refresh();

        FemmeService femmeService = context.getBean(FemmeService.class);
        HommeService hommeService = context.getBean(HommeService.class);
        MariageService mariageService = context.getBean(MariageService.class);

        // Création de 10 femmes
        for (int i = 1; i <= 10; i++) {
            Femme femme = new Femme();
            femme.setNom("Femme" + i);
            femme.setPrenom("Prenom" + i);
            femme.setTelephone("01234567" + i);
            femme.setAdresse(i + " rue de la Femme");
            try {
                femme.setDateNaissance(new SimpleDateFormat("yyyy-MM-dd").parse("1980-01-" + (10 + i)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            femmeService.create(femme);
        }

        // Création de 5 hommes
        for (int i = 1; i <= 5; i++) {
            Homme homme = new Homme();
            homme.setNom("Homme" + i);
            homme.setPrenom("Prenom" + i);
            homme.setTelephone("09876543" + i);
            homme.setAdresse(i + " avenue de l'Homme");
            try {
                homme.setDateNaissance(new SimpleDateFormat("yyyy-MM-dd").parse("1975-01-" + (10 + i)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            hommeService.create(homme);
        }

        // Afficher la liste des femmes
        System.out.println("Liste des femmes : ");
        List<Femme> femmes = femmeService.getAll();
        femmes.forEach(System.out::println);

        // Afficher la femme la plus âgée
        Femme oldestFemme = null;
        for (Femme f : femmes) {
            if (oldestFemme == null || f.getDateNaissance().before(oldestFemme.getDateNaissance())) {
                oldestFemme = f;
            }
        }
        if (oldestFemme != null) {
            System.out.println("La femme la plus agee: " + oldestFemme.getNom());
        }

        // Afficher les mariages d'un homme passé en paramètre
        //hommeService.afficherMariagesDunHomme(1);

        // Afficher les épouses d'un homme passé en paramètre entre deux dates
        Homme hommeParam = hommeService.getById(1);
        if (hommeParam != null) {
            try {
                List<Femme> epouses = hommeService.getEpousesEntreDates(hommeParam,
                        new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01"),
                        new Date());
                System.out.println("Épouses de " + hommeParam.getNom() + " entre 2000 et maintenant : ");
                epouses.forEach(System.out::println);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucun homme trouvé avec l'ID 1.");
        }

        // Afficher le nombre d'enfants d'une femme entre deux dates
        /** TODO **/

        // Afficher la liste des femmes mariées deux fois ou plus
        List<Femme> femmesMarieesDeuxFois = femmeService.findMarriedMoreThanTwice();
        System.out.println("Femmes mariées deux fois ou plus : ");
        femmesMarieesDeuxFois.forEach(System.out::println);

        // Afficher les hommes mariés à quatre femmes entre deux dates
        /*try {
            hommeService.countHommesMarriedToFourFemmesBetweenDates(
                    new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01"),
                    new Date()
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }*/


    }
}
