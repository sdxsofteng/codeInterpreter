/**
 * Enumeration des differents types d'erreures avec leurs messages et leur code d'erreurs selon le cas.
 */
public enum Err {

    ERR_ENTREE("Fichier introuvable ou innaccessible!", -100),
    ERR_SYNTAXE("Il y a une erreur dans la syntaxe du fichier!", -200),
    ERR_ORDRE_NBR_FERMETURE("Il y a un probleme avec les ouvertures et les fermetures de classes!", -300),
    ERR_ORDRE_ABSTRAIT("On ne peut pas placer un Abstrait a cet endroit!", -301),
    ERR_ORDRE_DEBUT_CLASSE("On ne peut pas placer un DebutClasse a cet endroit!",-302),
    ERR_ORDRE_FIN_CLASSE("On ne peut pas placer une FinClasse a cet endroit!", -303),
    ERR_ORDRE_ATTRIBUT("On ne pas placer un Attribut a cet endroit!", -304),
    ERR_ORDRE_DEBUT_METHODE("On ne peut pas placer un debut de methode a cet endroit!", -305),
    ERR_ORDRE_PARAMETRE("On ne peut pas placer un parametre a cet endroit!", -306),
    ERR_ORDRE_FIN_METHODE("On ne peut pas placer une FinMethode a cet endroit!", -307),
    ERR_ECRITURE("Il y a une erreur dans l'ecriture des commandes", -400),
    ERR_CREATION_UML("Il y a une errreure dans la creation du fichier UML", -500);


    private final String message;
    private final int code;

    Err(String message, int code) {
        this.message = message;
        this.code = code;
    }

    /**
     * Permet de sortir du programme. Quand on appelle une Enum et qu'on fait.sortir() on print le message et on sort
     * avec le code d'erreur.
     */
    public void sortir(){
        System.err.println(this.message);
        System.exit(this.code);
    }
}
