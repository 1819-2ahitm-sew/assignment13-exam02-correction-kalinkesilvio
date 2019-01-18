package at.htl.bank.model;

public class Sparkonto extends BankKonto {
    private double zinsSatz;

    public Sparkonto(double zinsSatz, String name) {
        super(name);
        this.zinsSatz = zinsSatz;
    }

    public Sparkonto(double zinsSatz, String name, double kontoStand) {
        super(name);
        this.zinsSatz = zinsSatz;
    }

    public void zinsenAnrechnen() {

    }


}
