/**
 * Cette classe représente la Commande méthode début
 */
public class MethodeDebut extends Commande{
    /**
     * type: Type de retour de la méthode
     * nom: Nom de la méthode.
     */
    String type;
    String nom;

    public MethodeDebut(String type, String nom) {
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
        contexte.genDebutMethode(this);
    }
}
