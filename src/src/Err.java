public enum Err {
    ERR_ENTREE("Fichier introuvable ou innaccessible!", -101),
    ERR_SYNTAXE("Il y a une erreur dans la syntaxe du fichier!", -102),
    ERR_ORDRE("Il y a une erreure dans l'ordre des commandes.", -103),
    ERR_ECRITURE("Il y a une erreur dans l'ecriture des commandes", -104),
    ERR_CREATION_UML("Il y a une errreure dans la creation du fichier UML", -105);
    ;

    private String message;
    private int code;

    Err(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public void sortir(){
        System.err.println(this.message);
        System.exit(this.code);
    }
}
