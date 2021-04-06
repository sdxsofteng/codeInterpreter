/**
 * Cette interface représente les expressions.
 * Expression -> Commandes -> Abstrait,Attribut,etc.
 * Ceci nous permet d'obliger les Commandes a avoir une méthode interprete.
 */
public interface Expression {

    /**
     * C'est dans cette méthode que l'on fournit l'interpréteur qui sera une fonction de call-back.
     * On fournit ensuite le this (Commande) a l'interpréteur et sa fonction appropriée.
     * @param contexte Interpréteur.
     */
    void interprete( ContexteInterpretation contexte);

}
