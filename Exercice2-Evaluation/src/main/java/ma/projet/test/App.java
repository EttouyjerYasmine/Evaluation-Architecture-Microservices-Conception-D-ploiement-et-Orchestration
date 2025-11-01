package ma.projet.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;

public class App {
    private static Date d(String s) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        EmployeService employeService = new EmployeService();
        ProjetService projetService = new ProjetService();
        TacheService tacheService = new TacheService();
        EmployeTacheService etService = new EmployeTacheService();

        Employe e1 = new Employe();
        e1.setNom("Dupont"); e1.setPrenom("Jean");
        employeService.create(e1);

        Projet p = new Projet();
        p.setNom("Gestion de stock");
        p.setDateDebut(d("14/01/2013"));
        p.setGerant(e1);
        projetService.create(p);

        Tache t1 = new Tache();
        t1.setNom("Analyse");
        t1.setProjet(p);
        t1.setDateDebutReelle(d("10/02/2013"));
        t1.setDateFinReelle(d("20/02/2013"));
        t1.setPrix(1200.0);
        tacheService.create(t1);

        Tache t2 = new Tache();
        t2.setNom("Conception");
        t2.setProjet(p);
        t2.setDateDebutReelle(d("10/03/2013"));
        t2.setDateFinReelle(d("15/03/2013"));
        t2.setPrix(800.0);
        tacheService.create(t2);

        Tache t3 = new Tache();
        t3.setNom("Développement");
        t3.setProjet(p);
        t3.setDateDebutReelle(d("10/04/2013"));
        t3.setDateFinReelle(d("25/04/2013"));
        t3.setPrix(3000.0);
        tacheService.create(t3);

        EmployeTache et1 = new EmployeTache();
        et1.setEmploye(e1); et1.setTache(t1); et1.setDateAffectation(d("09/02/2013"));
        etService.create(et1);

        EmployeTache et2 = new EmployeTache();
        et2.setEmploye(e1); et2.setTache(t2); et2.setDateAffectation(d("09/03/2013"));
        etService.create(et2);

        EmployeTache et3 = new EmployeTache();
        et3.setEmploye(e1); et3.setTache(t3); et3.setDateAffectation(d("09/04/2013"));
        etService.create(et3);

        System.out.println("Projet : " + p.getId() + "      Nom : " + p.getNom() + "     Date début : " + new SimpleDateFormat("dd MMMM yyyy").format(p.getDateDebut()));
        System.out.println("Liste des tâches:");
        System.out.println("Num Nom            Date Début Réelle   Date Fin Réelle");
        List<Tache> taches = projetService.listRealizedTasksWithRealDates(p.getId());
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for (Tache t : taches) {
            System.out.println(t.getId() + "  " + t.getNom() + "        " + (t.getDateDebutReelle()!=null?df.format(t.getDateDebutReelle()):"") + "          " + (t.getDateFinReelle()!=null?df.format(t.getDateFinReelle()):""));
        }

        List<Tache> expensive = tacheService.findByPriceGreaterThan(1000);
        System.out.println("Tâches prix > 1000: " + expensive.size());

        List<Tache> between = tacheService.findRealizedBetween(d("01/03/2013"), d("30/04/2013"));
        System.out.println("Tâches réalisées entre 01/03/2013 et 30/04/2013: " + between.size());

        List<Tache> tachesEmp = employeService.listTasksByEmployee(e1.getId());
        System.out.println("Tâches de l'employé " + e1.getNom() + ": " + tachesEmp.size());

        System.out.println("Projets gérés par l'employé: " + employeService.listProjectsManagedByEmployee(e1.getId()).size());
    }
}
