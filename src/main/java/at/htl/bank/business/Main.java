package at.htl.bank.business;

import at.htl.bank.model.BankKonto;
import at.htl.bank.model.Girokonto;
import at.htl.bank.model.Sparkonto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Legen Sie eine statische Liste "konten" an, in der Sie die einzelnen Konten speichern
 *
 */
public class Main {

  // die Konstanten sind package-scoped wegen der Unit-Tests
  static final double GEBUEHR = 0.02;
  static final double ZINSSATZ = 3.0;

  static final String KONTENDATEI = "erstellung.csv";
  static final String BUCHUNGSDATEI = "buchungen.csv";
  static final String ERGEBNISDATEI = "ergebnis.csv";

  static List<BankKonto> konten = new ArrayList<>();
  
  /**
   * Führen Sie die drei Methoden erstelleKonten, fuehreBuchungenDurch und
   * findKontoPerName aus
   *
   * @param args
   */
  public static void main(String[] args) {

    erstelleKonten(KONTENDATEI);
    fuehreBuchungenDurch(BUCHUNGSDATEI);
    schreibeKontostandInDatei(ERGEBNISDATEI);

  }

  /**
   * Lesen Sie aus der Datei (erstellung.csv) die Konten ein.
   * Je nach Kontentyp erstellen Sie ein Spar- oder Girokonto.
   * Gebühr und Zinsen sind als Konstanten angegeben.
   *
   * Nach dem Anlegen der Konten wird auf der Konsole folgendes ausgegeben:
   * Erstellung der Konten beendet
   *
   * @param datei KONTENDATEI
   */
  private static void erstelleKonten(String datei) {

    int counter = 0;
    String[] line;
    String name;
    double kontoStand;
    String kontoTyp;

    try(Scanner scanner = new Scanner(new FileReader(KONTENDATEI))) {
      while (scanner.hasNextLine()) {
        line = scanner.nextLine().split(";");
        name = line[1];
        kontoStand = Double.parseDouble(line[2]);
        kontoTyp = line[0];
        //konten.add(counter, scanner.nextLine());
        //System.out.println(scanner.nextLine());
        //counter++;

        if (kontoTyp.equalsIgnoreCase("Sparkonto")) {
          Sparkonto sparkonto = new Sparkonto(kontoStand, name, ZINSSATZ);
          konten.add(sparkonto);
        } else if (kontoTyp.equalsIgnoreCase("Girokonto")) {
          Girokonto girokonto = new Girokonto(kontoStand, name, GEBUEHR);
        }
      }
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
    }


    System.out.println("Konten wurden erstellt.");
  }

  /**
   * Die einzelnen Buchungen werden aus der Datei eingelesen.
   * Es wird aus der Liste "konten" jeweils das Bankkonto für
   * kontoVon und kontoNach gesucht.
   * Anschließend wird der Betrag vom kontoVon abgebucht und
   * der Betrag auf das kontoNach eingezahlt
   *
   * Nach dem Durchführen der Buchungen wird auf der Konsole folgendes ausgegeben:
   * Buchung der Beträge beendet
   *
   * Tipp: Verwenden Sie hier die Methode 'findeKontoPerName(String name)'
   *
   * @param datei BUCHUNGSDATEI
   */
  private static void fuehreBuchungenDurch(String datei) {

    int counter = 0;
    String[] line;
    BankKonto vonKonto;
    double betrag;
    BankKonto zuKonto;

    try(Scanner scanner = new Scanner(new FileReader(datei))) {

      scanner.nextLine();
      while (scanner.hasNextLine()) {
        line = scanner.nextLine().split(";");
        vonKonto = findeKontoPerName(line[0]);
        zuKonto = findeKontoPerName(line[1]);
        betrag = Integer.parseInt(line[2]);


        vonKonto.abheben(betrag);
        zuKonto.einzahlen(betrag);
      }

    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
    }

        System.out.println("Buchungen erfolgreich durchgeführt.");
  }

  /**
   * Es werden die Kontostände sämtlicher Konten in die ERGEBNISDATEI
   * geschrieben. Davor werden bei Sparkonten noch die Zinsen dem Konto
   * gutgeschrieben
   *
   * Die Datei sieht so aus:
   *
   * name;kontotyp;kontostand
   * Susi;SparKonto;875.5
   * Mimi;GiroKonto;949.96
   * Hans;GiroKonto;1199.96
   *
   * Vergessen Sie nicht die Überschriftenzeile
   *
   * Nach dem Schreiben der Datei wird auf der Konsole folgendes ausgegeben:
   * Ausgabe in Ergebnisdatei beendet
   *
   * @param datei ERGEBNISDATEI
   */
  private static void schreibeKontostandInDatei(String datei) {
    try(PrintWriter printWriter = new PrintWriter(new FileWriter(datei))) {

      printWriter.print("name;kontotyp;kontostand");
      for (BankKonto i: konten) {
        if (i instanceof Sparkonto) {

          printWriter.println(i.getName() + ";Sparkonto;" + i.getKontoStand());

        } else if (i instanceof Girokonto) {
          printWriter.println(i.getName() + ";Girokonto;" + i.getKontoStand());
        }
      }

    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    System.out.println("Kontostand wurde in die Datei geschrieben.");
  }

  /**
   */
  /**
   * Durchsuchen Sie die Liste "konten" nach dem ersten Konto mit dem als Parameter
   * übergebenen Namen
   * @param name
   * @return Bankkonto mit dem gewünschten Namen oder NULL, falls der Namen
   *         nicht gefunden wird
   */
  public static BankKonto findeKontoPerName(String name) {

    for (int i = 0; i < konten.size(); i++) {
      if (name.equals(konten.get(i).getName())) {
        return konten.get(i);
      }
    }

       return null;
  }

}
