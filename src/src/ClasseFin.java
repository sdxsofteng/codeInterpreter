public class ClasseFin extends Commande{

    @Override
    public void interprete(ContexteInterpretation contexte) {
        contexte.genFinClasse(this);
    }

}
