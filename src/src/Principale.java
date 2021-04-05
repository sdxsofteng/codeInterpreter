import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Nom: Simon Desormeaux
 * Code Permanent: DESS21079605
 * @version TODO
 */
public class Principale {

    public static void main(String[] args) {

        ArrayList<ContexteInterpretation> interpreteurs = new ArrayList<>();
        ArrayDeque<Commande> commandesValidees;
        interpreteurs.add(new InterpreteurOrdre());
        interpreteurs.add(new InterpreteurEcriture());

        Scanner scannerFichier = demandeUtilisateur();
        AnalyseSyntaxeUtil analyse = new AnalyseSyntaxeUtil();
        commandesValidees = analyse.analyserSyntaxe(scannerFichier);

        for ( ContexteInterpretation intepreteur : interpreteurs){
            commandesValidees.forEach( x -> x.interprete(intepreteur));
            intepreteur.verificationFin();
        }
    }

    private static Scanner demandeUtilisateur(){

        Scanner scPathFichier = new Scanner(System.in);
        System.out.print(Texte.DMD_ENTREE_UTILISATEUR);
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

