/**
 * Représente la commande Attribut
 */
public class Attribut extends Commande{

    /**
     * type: Type de l'attribut
     * nom: Nom que l'on veut donner à l'attribut
     */
    String type;
    String nom;

    public Attribut(String type, String nom) {
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
        contexte.genAttribut(this);
    }
}
