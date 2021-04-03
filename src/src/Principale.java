import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Nom: Simon Desormeaux
 * Code Permanent: DESS21079605
 * @version TODO
 */
public class Principale {

    public static void main(String[] args) {
        Scanner scannerFichier = demandeUtilisateur();
    }

    public static Scanner demandeUtilisateur(){

        Scanner scPathFichier = new Scanner(System.in);
        System.out.println(Texte.DMD_ENTREE_UTILISATEUR);
        String pathFichier = scPathFichier.next();

        Scanner scannerOut = null;
        try {
            File fichierRetour = new File(pathFichier);
            scannerOut = new Scanner(fichierRetour);
        }catch (FileNotFoundException e){
            Erreures erreurFichier = new Erreures(Texte.ERR_FICHIER_INTROUVABLE);
        }
        return scannerOut;
    }
}

