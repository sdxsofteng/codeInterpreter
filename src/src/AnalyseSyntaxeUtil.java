import java.util.ArrayDeque;
import java.util.Scanner;

public class AnalyseSyntaxeUtil {

    public static final String CLASSE_FIN = "classeFin";
    public static final String METHODE_FIN = "methodeFin";
    public static final String ABSTRAIT = "abstrait";

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
        scannerLigne.next();
        String temp = null;

        if (ligne.equals(CLASSE_FIN) || ligne.equals(METHODE_FIN) || ligne.equals(ABSTRAIT)){

        }else if (ligne.matches(".*\\([^,]*\\)")){
            temp = scannerLigne.next();
            validerUnaire(temp);
        }else if (ligne.matches(".*\\(.*[,].*\\).*")){
            temp = scannerLigne.next();
            validerBinaire(temp);
        }else{
            Err.ERR_SYNTAXE.sortir();
        }

    }

    private void validerUnaire(String ligne){
        if (!ligne.matches("^[a-zA-Z][a-zA-Z0-9_]*\\)")){
            Err.ERR_SYNTAXE.sortir();
        }
    }

    private void validerBinaire(String ligne) {
        if (!ligne.matches("^[a-zA-Z][a-zA-Z0-9_]*,[a-zA-Z][a-zA-Z0-9_]*\\)")) {
            Err.ERR_SYNTAXE.sortir();
        }
    }

    public ArrayDeque<Commande> getCommandesValidees() {
        return commandesValidees;
    }

    public void setCommandesValidees(ArrayDeque<Commande> commandesValidees) {
        this.commandesValidees = commandesValidees;
    }
}
