package at.htl.bank.model;

public class Girokonto extends BankKonto {

    private double gebuehr;

    public Girokonto(double gebuehr, String name) {
        super(name);
        this.gebuehr = gebuehr;
    }

    public Girokonto(double gebuehr, String name, double kontoStand) {
        super(name, kontoStand);
        this.gebuehr = gebuehr;
    }

    public void abheben() {

    }

    public void einzahlen() {

    }
}
