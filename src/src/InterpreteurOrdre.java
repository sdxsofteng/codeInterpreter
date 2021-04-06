/**
 * Ceci est le premier Interpreteur a etre utiliser dans le programme.
 * Dans cet interpreteur nous verifions si les Commandes sont dans le bon ordre.
 */
public class InterpreteurOrdre implements ContexteInterpretation{

    /**
     * Variable representant l'etat interne.
     * mode: commande traitees precedemment
     * estAbstrait: si la commande presente est precedees d'une Commande Abstraite
     * nbrOuverture: compteur du nombre de classe ouverte. Permet de calculer si il y a plus d'ouverture que de
     * fermeture
     */
    Mode mode = Mode.FClasse;
    boolean estAbstrait = false;
    int nbrOuverture = 0;

    /**
     * Derniere verification a faire lorsqu'on a fini de tout interpreter les Commandes
     */
    public void verificationFin(){
        if(!(!estAbstrait && nbrOuverture == 0)){
            Err.ERR_ORDRE_NBR_FERMETURE.sortir();
        }
    }

    /**
     * Verification de l'ordre pour la commande Abstraite
     * @param abstrait Commande en cours de verification
     */
    @Override
    public void genAbstrait(Abstrait abstrait) {
        if (!estAbstrait){
            estAbstrait = true;
        }else {
            Err.ERR_ORDRE_ABSTRAIT.sortir();
        }
    }

    /**
     * On verifie tous d'abord le Mode et on valide pour qu'il soit valide. Ensuite, ajustement du nombre d'ouverture,
     * changement de Mode et remise de estAbstrait a faux
     * @param classeDebut Commande ClasseDebut en cours de verification
     */
    @Override
    public void genDebutClasse(ClasseDebut classeDebut) {
        if (mode.equals(Mode.DClasse) || mode.equals(Mode.FClasse) || mode.equals(Mode.DAttribut)
                || mode.equals(Mode.FMethode)){
            nbrOuverture++;
            setMode(Mode.DClasse);
            estAbstrait = false;
        }else {
            Err.ERR_ORDRE_DEBUT_CLASSE.sortir();
        }
    }

    /**
     * Verification des modes valides, diminution du nombre d'ouverture et changement de mode
     * @param classeFin ClasseFin en cours de verification
     */
    @Override
    public void genFinClasse(ClasseFin classeFin) {
        if ((mode.equals(Mode.DClasse) || mode.equals(Mode.FClasse) || mode.equals(Mode.DAttribut)
                || mode.equals(Mode.FMethode)) && !estAbstrait && nbrOuverture > 0){
            nbrOuverture--;
            setMode(Mode.FClasse);
        }else {
            Err.ERR_ORDRE_FIN_CLASSE.sortir();
        }
    }

    /**
     * Verification de modes valides et changement de mode.
     * @param attribut Attribut en cours de verification
     */
    @Override
    public void genAttribut(Attribut attribut) {
        if ((mode.equals(Mode.DClasse) || mode.equals(Mode.DAttribut)) && !estAbstrait){
            setMode(Mode.DAttribut);
        }else {
            Err.ERR_ORDRE_ATTRIBUT.sortir();
        }
    }

    /**
     * Verification de modes valides, changement de mode, et remise de estAbstrait a faux.
     * @param methodeDebut MethodeDebut en cours de verification
     */
    @Override
    public void genDebutMethode(MethodeDebut methodeDebut) {
        if (mode.equals(Mode.DClasse) || mode.equals(Mode.DAttribut) || mode.equals(Mode.FMethode)){
            setMode(Mode.DMethode);
            estAbstrait = false;
        }else {
            Err.ERR_ORDRE_DEBUT_METHODE.sortir();
        }
    }

    /**
     * Validation de modes et changement de mode
     * @param parametre Parametre en cours de validation
     */
    @Override
    public void genParametre(Parametre parametre) {
        if ((mode.equals(Mode.DMethode) || mode.equals(Mode.DParametre)) && !estAbstrait){
            setMode(Mode.DParametre);
        }else {
            Err.ERR_ORDRE_PARAMETRE.sortir();
        }
    }

    /**
     * Validation mode valides et changement de mode
     * @param finMethode FinMethode en cours de verification
     */
    @Override
    public void genFinMethode(MethodeFin finMethode) {
        if ((mode.equals(Mode.DMethode) || mode.equals(Mode.DParametre)) && !estAbstrait){
            setMode(Mode.FMethode);
        }else {
            Err.ERR_ORDRE_FIN_METHODE.sortir();
        }
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

}
