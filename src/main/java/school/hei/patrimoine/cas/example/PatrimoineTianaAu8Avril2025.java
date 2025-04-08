package school.hei.patrimoine.cas.example;

import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.function.Supplier;

import static school.hei.patrimoine.modele.Argent.ariary;
import static school.hei.patrimoine.modele.Devise.MGA;

public class PatrimoineTianaAu8Avril2025 implements Supplier<Patrimoine> {

    public static final LocalDate AU_8_AVRIL_2025 = LocalDate.of(2025, 4, 8);
    public static final LocalDate DEBUT_PROJET = LocalDate.of(2025, 6, 1);
    public static final LocalDate FIN_PROJET = LocalDate.of(2025, 12, 31);
    public static final LocalDate AU_31_MARS_2026 = LocalDate.of(2026, 3, 31);

    private static Set<Possession> possessionsDu8Avril2025(
            Compte compteBancaire, Materiel terrain) {

        new FluxArgent(
                "Dépenses familiales",
                compteBancaire,
                LocalDate.of(2025, 5, 1),
                LocalDate.MAX,
                1,
                ariary(-4_000_000));

        new FluxArgent(
                "Dépenses projet",
                compteBancaire,
                DEBUT_PROJET,
                FIN_PROJET,
                5,
                ariary(-5_000_000));

        new FluxArgent(
                "Avance projet (10%)",
                compteBancaire,
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5, 1),
                1,
                ariary(7_000_000));

        new FluxArgent(
                "Solde projet (90%)",
                compteBancaire,
                LocalDate.of(2026, 1, 31),
                LocalDate.of(2026, 1, 31),
                31,
                ariary(63_000_000));

        new FluxArgent(
                "Réception prêt",
                compteBancaire,
                LocalDate.of(2025, 7, 27),
                LocalDate.of(2025, 7, 27),
                27,
                ariary(20_000_000));

        new FluxArgent(
                "Remboursement prêt",
                compteBancaire,
                LocalDate.of(2025, 8, 27),
                LocalDate.of(2026, 7, 27),
                27,
                ariary(-2_000_000));

        return Set.of(compteBancaire, terrain);
    }

    private Compte compteBancaire() {
        return new Compte("Compte bancaire", AU_8_AVRIL_2025, ariary(60_000_000));
    }

    private Materiel terrain() {
        return new Materiel(
                "Terrain bâti",
                AU_8_AVRIL_2025,
                AU_8_AVRIL_2025,
                ariary(100_000_000),
                0.10);
    }

    @Override
    public Patrimoine get() {
        var tiana = new Personne("Tiana");
        var compteBancaire = compteBancaire();
        var terrain = terrain();

        Set<Possession> possessionsDu8Avril = possessionsDu8Avril2025(compteBancaire, terrain);
        return Patrimoine.of(
                "Tiana au 8 avril 2025",
                MGA,
                AU_8_AVRIL_2025,
                tiana,
                possessionsDu8Avril);
    }

    public Patrimoine patrimoineFinMars2026() {
        var tiana = new Personne("Tiana");
        var compteBancaire = compteBancaire();
        var terrain = terrain();

        double soldeCompteBancaire = 60_000_000
                - 48_000_000
                - 30_000_000
                + 7_000_000
                + 63_000_000
                + 20_000_000
                - 24_000_000;

        double valeurTerrain = 100_000_000 * (1 + 0.10);

        GroupePossession possessionsDu8Avril = new GroupePossession(
                "Possessions du 8 avril 2025",
                MGA,
                AU_8_AVRIL_2025,
                possessionsDu8Avril2025(compteBancaire, terrain));

        return Patrimoine.of(
                        "Tiana au 31 mars 2026",
                        MGA,
                        AU_31_MARS_2026,
                        tiana,
                        Set.of(possessionsDu8Avril))
                .projectionFuture(AU_31_MARS_2026);
    }
}