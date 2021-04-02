/**
 *
 */
public interface ContexteInterpretation {
    void genDebutClasse( MotDebutClasse motDebutClasse);
    void genFinClasse( MotFinClasse motFinClasse);
    void genDebutMethode( MotDebutMethode motDebutMethode);
    void genAttribut ( MotAttribut motAttribut);
    void genAbstrait ( MotAbstrait motAbstrait);
    void genParametre( MotParametre motParametre);
    void genFinMethode( MotFinMethode motFinMethode);
}
