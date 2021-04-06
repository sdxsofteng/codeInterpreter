/**
 * Cette classe représente la commande Paramètre
 */
public class Parametre extends Commande{

    /**
     * type: Type du paramètre
     * nom: Nom du paramètre
     */
    String type;
    String nom;

    public Parametre(String type, String nom) {
        this.type = type;
        this.nom = nom;
    }


    public String getType() {
        return type;
    }

    public String getNom() {
        return nom;
    }

    /**
     * Voir Expression
     * @param contexte Interpréteur.
     */
    @Override
    public void interprete(ContexteInterpretation contexte) {
        contexte.genParametre(this);
    }
}
