/**
 * Cette interface représente les méthodes qui doivent être implémentées par les interpréteurs.
 * Toutes les méthodes commencant par gen... sont assez "self-explanatory"
 * Pour la méthodes vérificationFin. C'est une méthode qui est éxécuté à chaque fois que l'on termine
 * une loop complète des commandes dans l'interpréteur. Donc, dernière méthode éxécuter dans le Contexte
 * d'Interprétation.
 */
public interface ContexteInterpretation {
    void genDebutClasse( ClasseDebut classeDebut);
    void genFinClasse( ClasseFin classeFin);
    void genDebutMethode( MethodeDebut methodeDebut);
    void genAttribut ( Attribut attribut);
    void genAbstrait ( Abstrait abstrait);
    void genParametre( Parametre parametre);
    void genFinMethode( MethodeFin finMethode);
    void verificationFin();
}
