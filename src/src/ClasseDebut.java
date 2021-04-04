public class ClasseDebut extends Commande{

    private String nomDeLaClasse;
    private Commande suivant = null;
    private Commande precedent = null;

    public ClasseDebut(String nomDeLaClasse) {
        this.nomDeLaClasse = nomDeLaClasse;
    }

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

    public String getNomDeLaClasse() {
        return nomDeLaClasse;
    }

    public void setNomDeLaClasse(String nomDeLaClasse) {
        this.nomDeLaClasse = nomDeLaClasse;
    }

    @Override
    public void interprete(ContexteInterpretation contexte) {

    }
}
