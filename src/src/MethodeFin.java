/**
 * Cette classe représente la Commande MéthodeFin
 */
public class MethodeFin extends Commande{

    /**
     * Voir Expression
     * @param contexte Interpréteur.
     */
    @Override
    public void interprete(ContexteInterpretation contexte) {
        contexte.genFinMethode(this);
    }
}
