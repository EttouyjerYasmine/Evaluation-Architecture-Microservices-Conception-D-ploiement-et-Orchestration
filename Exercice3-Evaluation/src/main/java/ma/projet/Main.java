package ma.projet;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static final SimpleDateFormat DF = new SimpleDateFormat("dd/MM/yyyy");

    private static Date d(String s) {
        try { return DF.parse(s); } catch (ParseException e) { throw new RuntimeException(e); }
    }

    public static void main(String[] args) {
        FemmeService femmeService = new FemmeService();
        HommeService hommeService = new HommeService();
        MariageService mariageService = new MariageService();

        // Seed data: 10 femmes, 5 hommes
        List<Femme> femmes = new ArrayList<>();
        femmes.add(f("ALAMI", "KARIMA", "01/01/1970"));
        femmes.add(f("RAMI", "SALIMA", "04/04/1972"));
        femmes.add(f("ALI", "AMAL", "05/05/1975"));
        femmes.add(f("ALAOUI", "WAFA", "06/06/1978"));
        femmes.add(f("NASSER", "HIBA", "07/07/1980"));
        femmes.add(f("SAIDI", "RANIA", "08/08/1982"));
        femmes.add(f("KHALIL", "SARA", "09/09/1984"));
        femmes.add(f("ZAHRA", "DINA", "10/10/1986"));
        femmes.add(f("FARES", "LINA", "11/11/1988"));
        femmes.add(f("BARAKA", "MAYA", "12/12/1990"));
        femmes.forEach(femmeService::create);

        List<Homme> hommes = new ArrayList<>();
        hommes.add(h("SAFI", "SAID", "01/01/1965")); // index 0
        hommes.add(h("OMARI", "YASSINE", "02/02/1968"));
        hommes.add(h("TAZI", "NABIL", "03/03/1970"));
        hommes.add(h("EL FADIL", "OMAR", "04/04/1973"));
        hommes.add(h("JABRI", "ANAS", "05/05/1976"));
        hommes.forEach(hommeService::create);

        // Create marriages for SAID SAFI to match the example
        Homme said = hommes.get(0);
        Femme karima = femmes.get(0);
        Femme salima = femmes.get(1);
        Femme amal = femmes.get(2);
        Femme wafa = femmes.get(3);

        m(mariageService, said, karima, "03/09/1989", "03/09/1990", 0);
        m(mariageService, said, salima, "03/09/1990", null, 4);
        m(mariageService, said, amal, "03/09/1995", null, 2);
        m(mariageService, said, wafa, "04/11/2000", null, 3);

        // Add more marriages to satisfy other requirements
        // Make another man married >= 2 times for query testing
        Homme yassine = hommes.get(1);
        m(mariageService, yassine, femmes.get(4), "01/01/2001", "01/01/2003", 1);
        m(mariageService, yassine, femmes.get(5), "01/02/2004", null, 2);

        // Make a man married to exactly four women between 2010-2015 for criteria count
        Homme nabil = hommes.get(2);
        m(mariageService, nabil, femmes.get(6), "01/01/2010", "01/01/2011", 0);
        m(mariageService, nabil, femmes.get(7), "01/02/2011", "01/02/2012", 1);
        m(mariageService, nabil, femmes.get(8), "01/03/2012", "01/03/2013", 0);
        m(mariageService, nabil, femmes.get(9), "01/04/2013", "01/04/2014", 2);

        // Required outputs
        System.out.println("===== Liste des femmes =====");
        femmeService.findAll().forEach(fm ->
                System.out.println(fm.getNom() + " " + fm.getPrenom() + " (" + DF.format(fm.getDateNaissance()) + ")")
        );

        System.out.println("\n===== Femme la plus âgée =====");
        Femme oldest = femmeService.findAll().stream().min(Comparator.comparing(Femme::getDateNaissance)).orElse(null);
        if (oldest != null) System.out.println(oldest.getNom() + " " + oldest.getPrenom());

        System.out.println("\n===== Épouses de SAID SAFI entre 01/01/1990 et 31/12/2000 =====");
        List<Femme> epouses = hommeService.epousesEntreDates(said.getId(), d("01/01/1990"), d("31/12/2000"));
        epouses.forEach(fm -> System.out.println(fm.getNom() + " " + fm.getPrenom()));

        System.out.println("\n===== Nombre d'enfants de SALIMA RAMI entre 01/01/1980 et 31/12/2020 =====");
        long nbEnfantsSalima = femmeService.nombreEnfantsEntreDates(salima.getId(), d("01/01/1980"), d("31/12/2020"));
        System.out.println(nbEnfantsSalima);

        System.out.println("\n===== Femmes mariées au moins deux fois =====");
        femmeService.femmesMarieesAuMoinsDeuxFois().forEach(fm -> System.out.println(fm.getNom() + " " + fm.getPrenom()));

        System.out.println("\n===== Nombre d'hommes mariés à quatre femmes entre 01/01/2010 et 31/12/2015 =====");
        long count = new FemmeService().nombreHommesMarieesAQuatreFemmesEntre(d("01/01/2010"), d("31/12/2015"));
        System.out.println(count);

        System.out.println("\n===== Détails des mariages de SAID SAFI =====");
        printMariagesDetails("SAFI SAID", hommeService.mariagesAvecDetails(said.getId()));
    }

    private static Femme f(String nom, String prenom, String dn) {
        Femme f = new Femme();
        f.setNom(nom);
        f.setPrenom(prenom);
        f.setDateNaissance(d(dn));
        return f;
    }

    private static Homme h(String nom, String prenom, String dn) {
        Homme h = new Homme();
        h.setNom(nom);
        h.setPrenom(prenom);
        h.setDateNaissance(d(dn));
        return h;
    }

    private static void m(MariageService s, Homme h, Femme f, String dd, String df, int nb) {
        Mariage m = new Mariage();
        m.setHomme(h);
        m.setFemme(f);
        m.setDateDebut(d(dd));
        m.setDateFin(df == null ? null : d(df));
        m.setNbEnfants(nb);
        s.create(m);
    }

    private static void printMariagesDetails(String nomCompletHomme, List<Mariage> mariages) {
        System.out.println("Nom : " + nomCompletHomme);
        System.out.println("Mariages En Cours :");
        int i = 1;
        for (Mariage m : mariages) {
            if (m.isEnCours()) {
                System.out.printf("%d. Femme : %s %s   Date Début : %s    Nbr Enfants : %d%n",
                        i++, m.getFemme().getPrenom(), m.getFemme().getNom(), DF.format(m.getDateDebut()), m.getNbEnfants());
            }
        }
        System.out.println();
        System.out.println("Mariages échoués :");
        i = 1;
        for (Mariage m : mariages) {
            if (!m.isEnCours()) {
                System.out.printf("%d. Femme : %s %s  Date Début : %s    %nDate Fin : %s    Nbr Enfants : %d%n",
                        i++, m.getFemme().getPrenom(), m.getFemme().getNom(), DF.format(m.getDateDebut()),
                        m.getDateFin() == null ? "" : DF.format(m.getDateFin()), m.getNbEnfants());
            }
        }
    }
}
