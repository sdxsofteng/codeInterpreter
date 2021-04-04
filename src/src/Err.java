public enum Err {
    ERR_ENTREE("Fichier introuvable ou innaccessible!", -1),
    ERR_SYNTAXE("Il y a une erreur dans la syntaxe du fichier!", -2)
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
