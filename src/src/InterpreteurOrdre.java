import java.util.ArrayDeque;

public class InterpreteurOrdre implements ContexteInterpretation{
    Mode mode = Mode.FClasse;
    boolean estAbstrait = false;
    int nbrOuverture = 0;

    public void verificationFin(){
        if(!(!estAbstrait && nbrOuverture == 0)){
            Err.ERR_ORDRE.sortir();
        }
    }

    @Override
    public void genAbstrait(Abstrait abstrait) {
        if (!estAbstrait){
            estAbstrait = true;
        }else {
            Err.ERR_ORDRE.sortir();
        }
    }

    @Override
    public void genDebutClasse(ClasseDebut classeDebut) {
        if (mode.equals(Mode.DClasse) || mode.equals(Mode.FClasse) || mode.equals(Mode.DAttribut)
                || mode.equals(Mode.FMethode)){
            nbrOuverture++;
            setMode(Mode.DClasse);
            estAbstrait = false;
        }else {
            Err.ERR_ORDRE.sortir();
        }
    }

    @Override
    public void genFinClasse(ClasseFin classeFin) {
        if ((mode.equals(Mode.DClasse) || mode.equals(Mode.FClasse) || mode.equals(Mode.DAttribut)
                || mode.equals(Mode.FMethode)) && !estAbstrait && nbrOuverture > 0){
            nbrOuverture--;
            setMode(Mode.FClasse);
        }else {
            Err.ERR_ORDRE.sortir();
        }
    }

    @Override
    public void genAttribut(Attribut attribut) {
        if ((mode.equals(Mode.DClasse) || mode.equals(Mode.DAttribut)) && !estAbstrait){
            setMode(Mode.DAttribut);
        }else {
            Err.ERR_ORDRE.sortir();
        }
    }

    @Override
    public void genDebutMethode(MethodeDebut methodeDebut) {
        if (mode.equals(Mode.DClasse) || mode.equals(Mode.DAttribut) || mode.equals(Mode.FMethode)){
            setMode(Mode.DMethode);
            estAbstrait = false;
        }else {
            Err.ERR_ORDRE.sortir();
        }
    }

    @Override
    public void genParametre(Parametre parametre) {
        if ((mode.equals(Mode.DMethode) || mode.equals(Mode.DParametre)) && !estAbstrait){
            setMode(Mode.DParametre);
        }else {
            Err.ERR_ORDRE.sortir();
        }
    }

    @Override
    public void genFinMethode(MethodeFin finMethode) {
        if ((mode.equals(Mode.DMethode) || mode.equals(Mode.DParametre)) && !estAbstrait){
            setMode(Mode.FMethode);
        }else {
            Err.ERR_ORDRE.sortir();
        }
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

}
