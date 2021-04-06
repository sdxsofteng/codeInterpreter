/**
 * Énumération de mode. Ces modes sont utilisés dans l'interpréteur Ordre. Le mode nous permet de déterminer quel
 * était la commande précédente dans l'état interne.
 */
public enum Mode {
    DClasse(),
    DAttribut(),
    DMethode(),
    DParametre(),
    FClasse(),
    FMethode(),
    ;
}
