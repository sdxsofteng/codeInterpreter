/**
 * Représente la commande Abstrait
 */
public class Abstrait extends Commande{

    /**
     * Voir explication dans Expression
     * @param contexte Interpréteur.
     */
    @Override
    public void interprete(ContexteInterpretation contexte) {
        contexte.genAbstrait(this);
    }
}
