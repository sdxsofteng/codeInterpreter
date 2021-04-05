public class MethodeDebut extends Commande{

    String type;
    String nom;

    public MethodeDebut(String type, String nom) {
        this.type = type;
        this.nom = nom;
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
        contexte.genDebutMethode(this);
    }
}
