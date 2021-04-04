public class MethodeDebut extends Commande{

    Commande precedent;
    Commande suivant;
    String type;
    String nom;

    public MethodeDebut(String type, String nom) {
        this.type = type;
        this.nom = nom;
    }

    public Commande getPrecedent() {
        return precedent;
    }

    public void setPrecedent(Commande precedent) {
        this.precedent = precedent;
    }

    public Commande getSuivant() {
        return suivant;
    }

    public void setSuivant(Commande suivant) {
        this.suivant = suivant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public void interprete(ContexteInterpretation contexte) {

    }
}
