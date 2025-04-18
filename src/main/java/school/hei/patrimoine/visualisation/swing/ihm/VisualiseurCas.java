package school.hei.patrimoine.visualisation.swing.ihm;

import static java.awt.EventQueue.invokeLater;

import java.util.List;

import school.hei.patrimoine.cas.example.*;

public class VisualiseurCas {

  public static void main(String[] args) {
    invokeLater(
        () ->
            new MainIHM(
                List.of(
                    new EtudiantPireCas().patrimoine(),
                    new PatrimoineRicheSupplier().get(),
                    new PatrimoineBakoAu8Avril2025().patrimoineFinAnnee(),
                    new PatrimoineTianaAu8Avril2025().get(),
                    new PatrimoineCresusSupplier().get())));
  }
}
