/**
 * Cette classe représente la commande ClasseFin
 */
public class ClasseFin extends Commande{

    /**
     * Voir Expression
     * @param contexte Interpréteur.
     */
    @Override
    public void interprete(ContexteInterpretation contexte) {
        contexte.genFinClasse(this);
    }

}
