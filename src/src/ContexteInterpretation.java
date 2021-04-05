/**
 *
 */
public interface ContexteInterpretation {
    void genDebutClasse( ClasseDebut classeDebut);
    void genFinClasse( ClasseFin classeFin);
    void genDebutMethode( MethodeDebut methodeDebut);
    void genAttribut ( Attribut attribut);
    void genAbstrait ( Abstrait abstrait);
    void genParametre( Parametre parametre);
    void genFinMethode( MethodeFin finMethode);
}
