/**
 *
 */
public abstract class Commande implements Expression{

    protected Commande suivant = null;
    protected Commande precedent = null;

    public Commande getSuivant() {
        return suivant;
    }

    public void setSuivant(Commande suivant) {
        this.suivant = suivant;
    }

    public Commande getPrecedent() {
        return precedent;
    }

    public void setPrecedent(Commande precedent) {
        this.precedent = precedent;
    }
}
