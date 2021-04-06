import java.util.ArrayDeque;
import java.util.Scanner;
/**
 * Cette classe représente un Utilitaire de validation de syntaxe sur le fichier en entrée.
 */
public class AnalyseSyntaxeUtil {

    /**
     * Ces constantes représentent tous les noms possibles de commandes dans le fichier entrant.
     * Ceci sera utilisé pour vérifier que les noms sont bel et bien valides.
     */
    public final String CLASSE_FIN = "classeFin";
    public final String METHODE_FIN = "methodeFin";
    public final String ABSTRAIT = "abstrait";
    public final String CLASSE_DEBUT = "classeDebut";
    public final String METHODE_DEBUT = "methodeDebut";
    public final String ATTRIBUT = "attribut";
    public final String PARAMETRE = "parametre";

    /**
     * On met nos commandes validées dans le ArrayDeque pour un usage futur.
     * J'aurais pu utiliser un ArrayList et ça aurait fonctionné mais je voulais essayer ArrayDeque.
     */
    ArrayDeque<Commande> commandesValidees = new ArrayDeque<>();

    public AnalyseSyntaxeUtil(){}

    /**
     * Méthode principale de cette classe. C'est ici qu'on loop à travers les lignes du fichier et qu'on valide les
     * commandes.
     * On remplace les \\r pour les lignes pour évaluer correctement les lignes (un problème arrivait lorsque la
     *      ligne était vide, elle ne comptait pas comme vide à cause de \\r)
     * On trim les espaces pour faciliter la validation de la syntaxe
     * @param scannerFichier scanner reçu en entrée. Scanner que l'on a créé avec le fichier entré par l'utilisateur.
     * @return un ArrayDeque de Commandes validées quand le fichier est valide, syntaxiquement parlant.
     */
    public ArrayDeque<Commande> analyserSyntaxe(Scanner scannerFichier){

        scannerFichier.useDelimiter("\n");
        while( scannerFichier.hasNext() ){
            String ligne = scannerFichier.next().replaceAll("\\r", "");
            if (!ligne.isBlank()){
                ligne = ligne.replaceAll(" ", "");
                traiterLigne(ligne);
            }
        }
        return commandesValidees;
    }

    /**
     * Permet de traiter la ligne recue en argument.
     * Nullaire -> Aucun traitement necessaire. Simple .equals()
     * Unaire -> On verifie si il y a un match de REGEX ensuite, on valide le Nom, on valide le parametre et on cree
     *      la commande
     * Binaire -> Verification match REGEX, valider le nom, valider les parametres et creer la commande.
     * Si rien fit avec le Nullaire ou les Regex, la syntaxe est automatiquement invalide.
     * @param ligne Ligne a traiter
     */
    private void traiterLigne(String ligne){

        Scanner scannerLigne = new Scanner(ligne);
        scannerLigne.useDelimiter("\\(");
        String nom;
        String parametre;
        String [] parametres;

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

    /**
     * Creation d'une commande binaire
     * @param nom nom de la commande binaire
     * @param identificateurs tableau de 2 identificateur -> [0] premier parametre, [1] deuxieme parametre
     * @return une Commande binaire.
     */
    private Commande creerBinaire(String nom, String[] identificateurs){
        return switch (nom) {
            case METHODE_DEBUT -> new MethodeDebut(identificateurs[0], identificateurs[1]);
            case ATTRIBUT -> new Attribut(identificateurs[0], identificateurs[1]);
            case PARAMETRE -> new Parametre(identificateurs[0], identificateurs[1]);
            default -> null;
        };
    }

    /**
     * Retourne ClasseDebut (seule Commande unaire)
     * @param identificateur parametre 1
     * @return une Commande Unaire
     */
    private Commande creerUnaire(String identificateur){
        return new ClasseDebut(identificateur);
    }

    /**
     * Creation d'une commande nullaire. On match le nom avec les noms possibles de commandes unaires.
     * @param nom nom de la commande a verifer
     * @return Commande nullaire
     */
    private Commande creerNullaire(String nom){
        return switch (nom) {
            case CLASSE_FIN -> new ClasseFin();
            case METHODE_FIN -> new MethodeFin();
            case ABSTRAIT -> new Abstrait();
            default -> null;
        };
    }

    /**
     * Simple validation de nom, selon les noms valides de commande binaire
     * @param temp nom a verifier
     */
    private void validerNomBinaire(String temp) {
        if (!(temp.equals(METHODE_DEBUT) || temp.equals(ATTRIBUT) || temp.equals(PARAMETRE))){
            Err.ERR_SYNTAXE.sortir();
        }
    }

    /**
     * Validation de nom selon les noms valides de commande unaire
     * @param temp nom a verifier
     */
    private void validerNomUnaire(String temp) {
        if (!temp.equals(CLASSE_DEBUT)){
            Err.ERR_SYNTAXE.sortir();
        }
    }

    /**
     * On valide le format du parametres entrer
     * Ne commance pas par 0-9 et est a-zA-Z0-9 ou _
     * @param ligne parametre a verifer
     * @return parametre valider sans la parenthes fermante
     */
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

    /**
     * On valide tous d'abord les deux parametre avec un REGEX. les deux ne doivent pas commencer par des 0-9
     * et etre a-zA-Z0-9 ou _
     * Ensuite on les separe et on enleve les virgules/parentheses et on les mets dans le tableau de String
     * @param ligne deux parametres a valider
     * @return tableau de parametres valides
     */
    private String[] validerParamBinaire(String ligne) {

        String[] identificateurs = new String[2];

        if (!ligne.matches("^[a-zA-Z][a-zA-Z0-9_]*,[a-zA-Z][a-zA-Z0-9_]*\\)")) {
            Err.ERR_SYNTAXE.sortir();
        }else {
            Scanner scanIdentificateur = new Scanner(ligne);
            scanIdentificateur.useDelimiter(",");
            identificateurs[0] = scanIdentificateur.next();
            scanIdentificateur.useDelimiter("\\)");
            identificateurs[1] = scanIdentificateur.next().replaceAll(",","");
        }
        return identificateurs;
    }
}
