package school.hei.patrimoine.cas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import school.hei.patrimoine.cas.example.PatrimoineBakoAu8Avril2025;
import school.hei.patrimoine.modele.Argent;
import school.hei.patrimoine.modele.Patrimoine;

import java.time.LocalDate;

class PatrimoineBakoTest {

    private final PatrimoineBakoAu8Avril2025 patrimoineDeBakoSupplier = new PatrimoineBakoAu8Avril2025();
    private static final LocalDate AU_31_DECEMBRE_2025 = LocalDate.of(2025, 12, 31);

    @Test
    void projectionPatrimoineBakoFinAnnee() {
        Patrimoine patrimoineFinAnnee = patrimoineDeBakoSupplier.patrimoineFinAnnee();

        double tolerance = 1000;

        double expectedValeurComptable = 13_135_000;
        double actualValeurComptable = Double.parseDouble(patrimoineFinAnnee.getValeurComptable().ppMontant());
        assertTrue(Math.abs(expectedValeurComptable - actualValeurComptable) <= tolerance,
                "La différence de valeur comptable est trop grande, attendu : " + expectedValeurComptable + ", obtenu : " + actualValeurComptable);

        var compteBNI = patrimoineFinAnnee.possessionParNom("Compte BNI");
        var compteBMOI = patrimoineFinAnnee.possessionParNom("Compte BMOI");
        var coffreFort = patrimoineFinAnnee.possessionParNom("Coffre fort");
        var ordinateur = patrimoineFinAnnee.possessionParNom("Ordinateur portable");

        assertTrue(Math.abs(6_400_000 - Double.parseDouble(compteBNI.projectionFuture(AU_31_DECEMBRE_2025).valeurComptable().ppMontant())) <= tolerance,
                "La valeur du compte BNI au 31 décembre 2025 est incorrecte");

        assertTrue(Math.abs(2_225_000 - Double.parseDouble(compteBMOI.projectionFuture(AU_31_DECEMBRE_2025).valeurComptable().ppMontant())) <= tolerance,
                "La valeur du compte BMOI au 31 décembre 2025 est incorrecte");

        assertTrue(Math.abs(1_750_000 - Double.parseDouble(coffreFort.projectionFuture(AU_31_DECEMBRE_2025).valeurComptable().ppMontant())) <= tolerance,
                "La valeur du coffre fort au 31 décembre 2025 est incorrecte");

        var valeurOrdinateur = ordinateur.projectionFuture(AU_31_DECEMBRE_2025).valeurComptable();
        assertTrue(Math.abs(2_760_000 - Double.parseDouble(valeurOrdinateur.ppMontant())) <= tolerance,
                "La valeur de l'ordinateur devrait être autour de 2.76M");
    }
}

