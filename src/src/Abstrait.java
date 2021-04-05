public class Abstrait extends Commande{

    @Override
    public void interprete(ContexteInterpretation contexte) {
        contexte.genAbstrait(this);
    }
}
