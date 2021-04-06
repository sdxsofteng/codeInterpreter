/**
 * Cette Classe représente la Commande Classe Début
 */
public class ClasseDebut extends Commande{

    /**
     * Nom que l'on veut donner à la classe
     */
    private final String nomDeLaClasse;

    public ClasseDebut(String nomDeLaClasse) {
        this.nomDeLaClasse = nomDeLaClasse;
    }

    public String getNomDeLaClasse() {
        return nomDeLaClasse;
    }

    /**
     * Voir Expression
     * @param contexte Interpréteur.
     */
    @Override
    public void interprete(ContexteInterpretation contexte) {
        contexte.genDebutClasse(this);
    }
}
