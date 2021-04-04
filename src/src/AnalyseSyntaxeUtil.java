import java.util.ArrayDeque;
import java.util.Scanner;

public class AnalyseSyntaxeUtil {

    public final String CLASSE_FIN = "classeFin";
    public final String METHODE_FIN = "methodeFin";
    public final String ABSTRAIT = "abstrait";
    public final String CLASSE_DEBUT = "classeDebut";
    public final String METHODE_DEBUT = "methodeDebut";
    public final String ATTRIBUT = "attribut";
    public final String PARAMETRE = "parametre";

    ArrayDeque<Commande> commandesValidees = new ArrayDeque<>();

    public AnalyseSyntaxeUtil(){}

    public void analyserSyntaxe(Scanner scannerFichier){
        scannerFichier.useDelimiter("\n");
        while( scannerFichier.hasNext() ){
            String ligne = scannerFichier.next().replaceAll("\\r", "");
            if (!ligne.isBlank()){
                ligne = ligne.replaceAll(" ", "");
                detecterPattern(ligne);
            }
        }
    }

    private void detecterPattern(String ligne){

        Scanner scannerLigne = new Scanner(ligne);
        scannerLigne.useDelimiter("\\(");
        String nom = null;
        String parametre = null;
        String [] parametres = new String[2];

        if (ligne.equals(CLASSE_FIN) || ligne.equals(METHODE_FIN) || ligne.equals(ABSTRAIT)){
            commandesValidees.add(creerNullaire(ligne));
        }else if (ligne.matches(".*\\([^,]*\\)")){
            nom = scannerLigne.next();
            validerNomUnaire(nom);
            parametre = scannerLigne.next();
            parametre = validerParamUnaire(parametre);
            commandesValidees.add(creerUnaire(parametre));
        }else if (ligne.matches(".*\\(.*[,].*\\).*")){
            nom = scannerLigne.next();
            validerNomBinaire(nom);
            parametre = scannerLigne.next();
            parametres = validerParamBinaire(parametre);
            commandesValidees.add(creerBinaire(nom, parametres));
        }else{
            Err.ERR_SYNTAXE.sortir();
        }
    }

    private Commande creerBinaire(String nom, String[] identificateurs){
        return switch (nom) {
            case METHODE_DEBUT -> new MethodeDebut(identificateurs[0], identificateurs[1]);
            case ATTRIBUT -> new Attribut(identificateurs[0], identificateurs[1]);
            case PARAMETRE -> new Parametre(identificateurs[0], identificateurs[1]);
            default -> null;
        };
    }

    private Commande creerUnaire(String identificateur){
        return new ClasseDebut(identificateur);
    }


    private Commande creerNullaire(String nom){
        return switch (nom) {
            case CLASSE_FIN -> new ClasseFin();
            case METHODE_FIN -> new MethodeFin();
            case ABSTRAIT -> new Abstrait();
            default -> null;
        };
    }

    private void validerNomBinaire(String temp) {
        if (!(temp.equals(METHODE_DEBUT) || temp.equals(ATTRIBUT) || temp.equals(PARAMETRE))){
            Err.ERR_SYNTAXE.sortir();
        }
    }

    private void validerNomUnaire(String temp) {
        if (!temp.equals(CLASSE_DEBUT)){
            Err.ERR_SYNTAXE.sortir();
        }
    }

    private String validerParamUnaire(String ligne){
        String identificateur = null;

        if (!ligne.matches("^[a-zA-Z][a-zA-Z0-9_]*\\)")){
            Err.ERR_SYNTAXE.sortir();
        }else {
            Scanner scanIdentificateur = new Scanner(ligne);
            scanIdentificateur.useDelimiter("\\)");
            identificateur = scanIdentificateur.next();
        }

        return identificateur;
    }

    private String[] validerParamBinaire(String ligne) {

        String[] identificateurs = new String[2];

        if (!ligne.matches("^[a-zA-Z][a-zA-Z0-9_]*,[a-zA-Z][a-zA-Z0-9_]*\\)")) {
            Err.ERR_SYNTAXE.sortir();
        }else {
            Scanner scanIdentificateur = new Scanner(ligne);
            scanIdentificateur.useDelimiter(",");
            identificateurs[0] = scanIdentificateur.next();
            scanIdentificateur.useDelimiter("\\)");
            identificateurs[1] = scanIdentificateur.next();
        }
        return identificateurs;
    }

    public ArrayDeque<Commande> getCommandesValidees() {
        return commandesValidees;
    }

    public void setCommandesValidees(ArrayDeque<Commande> commandesValidees) {
        this.commandesValidees = commandesValidees;
    }
}
