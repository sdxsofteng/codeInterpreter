public class MethodeFin extends Commande{
    @Override
    public void interprete(ContexteInterpretation contexte) {
        contexte.genFinMethode(this);
    }
}
