package school.hei.patrimoine.cas.example;

import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.function.Supplier;

import static java.time.Month.*;
import static school.hei.patrimoine.modele.Argent.ariary;
import static school.hei.patrimoine.modele.Devise.MGA;

public class PatrimoineBakoAu8Avril2025 implements Supplier<Patrimoine> {

    public static final LocalDate AU_8_AVRIL_2025 = LocalDate.of(2025, APRIL, 8);
    public static final LocalDate AU_31_DECEMBRE_2025 = LocalDate.of(2025, DECEMBER, 31);

    private static Set<Possession> possessionsDu8Avril2025(
            Materiel ordinateur, Compte compteBNI, Compte compteBMOI, Compte coffreFort) {

        new FluxArgent(
                "Salaire",
                compteBNI,
                LocalDate.of(2025, MAY, 2),
                LocalDate.of(2025, DECEMBER, 2),
                2,
                ariary(2_125_000));

        new FluxArgent(
                "Virement épargne",
                compteBNI,
                LocalDate.of(2025, MAY, 3),
                LocalDate.of(2025, DECEMBER, 3),
                3,
                ariary(-200_000));

        new FluxArgent(
                "Virement épargne crédit",
                compteBMOI,
                LocalDate.of(2025, MAY, 3),
                LocalDate.of(2025, DECEMBER, 3),
                3,
                ariary(200_000));

        new FluxArgent(
                "Paiement loyer",
                compteBNI,
                LocalDate.of(2025, APRIL, 26),
                LocalDate.of(2025, DECEMBER, 26),
                26,
                ariary(-600_000));

        new FluxArgent(
                "Dépenses mensuelles",
                compteBNI,
                LocalDate.of(2025, MAY, 1),
                LocalDate.of(2025, DECEMBER, 1),
                1,
                ariary(-700_000));

        return Set.of(ordinateur, compteBNI, compteBMOI, coffreFort);
    }

    private Compte compteBNI() {
        return new Compte("Compte BNI", AU_8_AVRIL_2025, ariary(2_000_000));
    }

    private Compte compteBMOI() {
        return new Compte("Compte BMOI", AU_8_AVRIL_2025, ariary(625_000));
    }

    private Compte coffreFort() {
        return new Compte("Coffre fort", AU_8_AVRIL_2025, ariary(1_750_000));
    }

    private Materiel ordinateur() {
        return new Materiel(
                "Ordinateur portable",
                AU_8_AVRIL_2025,
                AU_8_AVRIL_2025,
                ariary(3_000_000),
                -0.12);
    }

    @Override
    public Patrimoine get() {
        var bako = new Personne("Bako");
        var ordinateur = ordinateur();
        var compteBNI = compteBNI();
        var compteBMOI = compteBMOI();
        var coffreFort = coffreFort();

        Set<Possession> possessionsDu8Avril = possessionsDu8Avril2025(
                ordinateur, compteBNI, compteBMOI, coffreFort);

        return Patrimoine.of(
                "Bako au 8 avril 2025",
                MGA,
                AU_8_AVRIL_2025,
                bako,
                possessionsDu8Avril);
    }

    public Patrimoine patrimoineFinAnnee() {
        var bako = new Personne("Bako");
        var ordinateur = ordinateur();
        var compteBNI = compteBNI();
        var compteBMOI = compteBMOI();
        var coffreFort = coffreFort();

        GroupePossession possessionsDu8Avril = new GroupePossession(
                "Possessions du 8 avril 2025",
                MGA,
                AU_8_AVRIL_2025,
                possessionsDu8Avril2025(ordinateur, compteBNI, compteBMOI, coffreFort));

        return Patrimoine.of(
                        "Bako au 31 décembre 2025",
                        MGA,
                        AU_31_DECEMBRE_2025,
                        bako,
                        Set.of(possessionsDu8Avril))
                .projectionFuture(AU_31_DECEMBRE_2025);
    }

    public static void main(String[] args) {
        PatrimoineBakoAu8Avril2025 bakoPatrimoine = new PatrimoineBakoAu8Avril2025();
        Patrimoine finAnnee = bakoPatrimoine.patrimoineFinAnnee();

        System.out.println("Patrimoine de Bako au 31 décembre 2025:");
        System.out.println(finAnnee);
    }
}