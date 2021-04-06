import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Nom: Simon Desormeaux
 * Code Permanent: DESS21079605
 * Courriel: desormeaux.simon@courrier.uqam.ca
 * @version 06-04-2021
 *
 * Cette classe représente la classe Principale de notre programme. C'est ici que nous éxécutons toutes les étapes
 * globales du projet d'interpréteur de code.
 */
public class Principale {

    public static String DMD_ENTREE_UTILISATEUR = "Veuillez entrez le path du fichier a interpreter: ";

    /**
     * Nous demandons tous d'abord à l'utlisateur d'entrer le path du fichier
     * qui sera interprété. Ensuite, on analyse le fichier pour vérifier que la syntaxe est correcte. On retourne
     * ensuite un ArrayDeque de Commandes. On prend chaque interpreteur et on interprète chacune des commandes
     * une fois par interpréteur.
     * @param args X N/A
     */
    public static void main(String[] args) {

        ArrayList<ContexteInterpretation> interpreteurs = new ArrayList<>();
        ArrayDeque<Commande> commandesValidees;
        interpreteurs.add(new InterpreteurOrdre());
        interpreteurs.add(new InterpreteurEcriture());
        interpreteurs.add(new InterpreteurUML());

        Scanner scannerFichier = demandeUtilisateur();
        AnalyseSyntaxeUtil analyse = new AnalyseSyntaxeUtil();
        commandesValidees = analyse.analyserSyntaxe(scannerFichier);

        for ( ContexteInterpretation intepreteur : interpreteurs){
            commandesValidees.forEach( x -> x.interprete(intepreteur));
            intepreteur.verificationFin();
        }
    }

    /**
     * Méthode pour faire la demande à l'utilisateur.
     * @return un scanner qui contient le fichier en entier.
     */
    private static Scanner demandeUtilisateur(){

        Scanner scPathFichier = new Scanner(System.in);
        System.out.print(DMD_ENTREE_UTILISATEUR);
        String pathFichier = scPathFichier.next();

        Scanner scannerOut = null;
        try {
            File fichierRetour = new File(pathFichier);
            scannerOut = new Scanner(fichierRetour);
        }catch (FileNotFoundException e){
            Err.ERR_ENTREE.sortir();
        }
        return scannerOut;
    }
}

