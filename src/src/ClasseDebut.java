public class ClasseDebut extends Commande{

    private String nomDeLaClasse;

    public ClasseDebut(String nomDeLaClasse) {
        this.nomDeLaClasse = nomDeLaClasse;
    }

    public String getNomDeLaClasse() {
        return nomDeLaClasse;
    }

    public void setNomDeLaClasse(String nomDeLaClasse) {
        this.nomDeLaClasse = nomDeLaClasse;
    }

    @Override
    public void interprete(ContexteInterpretation contexte) {
        contexte.genDebutClasse(this);
    }
}
