import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
/**
 * Cette classe represente le troisieme et dernier interpreteur. Cet interpreteur prend la liste de Commande et la
 * transforme en un fichier en format UML qui permet de representer graphiquement le programme.
 * La javadoc pour les methodes est minime. Seule les clarifications necessaires sont incluses, sinon, les etapes
 * realisees sont celles inscrites dans l'enonce.
 * Les phrases utilisees pour l'ecriture sont dans la @class DescriptionLatex14
 */
public class InterpreteurUML implements ContexteInterpretation{

    /**
     * PATH_FICHIER_UML: path du fichier uml que l'on va creer.
     * pileEtat: Pile des etats qui permet de garder en memoire les modifications sur les fichier
     * fichierUML: Fichier creer pour contenir le code LaTeX
     * writerUML: writer unique pour ecrire dans le fichierUML
     */
    public final String PATH_FICHIER_UML = "uml.tex";

    int nbrClasse = 0;
    Stack<Etat> pileEtat = new Stack<>();
    boolean estAbstrait = false;
    boolean estPremierParametre = false;
    File fichierUML;
    FileWriter writerUML;

    /**
     * Des la creation de l'interpreteur, on creer tous de suite le Fichier, le FileWriter.
     */
    public InterpreteurUML() {
        fichierUML = new File(PATH_FICHIER_UML);
        try {
            writerUML = new FileWriter(fichierUML);
        }catch (IOException e){
            Err.ERR_CREATION_UML.sortir();
        }
    }

    /**
     * Maintien l'etat interne, est utiliser dans les autres fonctions.
     * @param abstrait Abstrait en cours d'ecriture
     */
    @Override
    public void genAbstrait(Abstrait abstrait) {
        estAbstrait = true;
    }

    /**
     * Si le compteur de classe est == 0, on ecrit les lignes requises pour commencer un ficheir LaTeX. Ensuite
     * on met un nouvel Etat pour la classe sur le Stack, on ecrit ensuite les phrases requises selon l'etat interne
     * @param classeDebut ClasseDebut en cours d'ecriture
     */
    @Override
    public void genDebutClasse(ClasseDebut classeDebut) {
        try {
            if (nbrClasse == 0) {
                writerUML.write(DescriptionLatex14.FICHIER_DEBUT);
                writerUML.write(DescriptionLatex14.PAGE_DEBUT);
            }else {
                genererDebutClasse();
            }
            pileEtat.push(new Etat());
            writerUML.write(DescriptionLatex14.CLASSE_DEBUT);
            if (estAbstrait){
                writerUML.write(DescriptionLatex14.ABSTRAIT_DEBUT);
            }
            writerUML.write(String.format("%s\n", classeDebut.getNomDeLaClasse()));
            if(estAbstrait){
                writerUML.write(DescriptionLatex14.ABSTRAIT_FIN);
                estAbstrait = false;
            }
            nbrClasse++;
        }catch (IOException e){
            Err.ERR_CREATION_UML.sortir();
        }
    }

    /**
     * Sous-programme de genClasseDebut qui permet d'ecrire certaines phrases precises si l'on est a la premiere classe
     * de l'Etat courant.
     * @throws IOException lance l'exception qui est catcher dans le sur-programme genDebutClasse
     */
    private void genererDebutClasse() throws IOException {
        if (pileEtat.peek().isPremiereClasse()) {
            writerUML.write(DescriptionLatex14.CLASSE_FIN);
            writerUML.write(DescriptionLatex14.LISTE_CLASSE_DEBUT);
            pileEtat.peek().setPremiereClasse(false);
            writerUML.write(DescriptionLatex14.CLASSE_INTERNE_PREFIX);
        } else {
            writerUML.write(DescriptionLatex14.CLASSE_INTERNE_PREFIX);
        }
    }

    /**
     * Diminue le nombre de classe, ecrit les phrases appropriees dans le fichier et enleve l'etat de la pile.
     * @param classeFin ClasseFin en cours d'ecriture.
     */
    @Override
    public void genFinClasse(ClasseFin classeFin) {
        nbrClasse--;
        try {
            String temp = pileEtat.peek().isPremiereClasse() ? DescriptionLatex14.CLASSE_FIN
                    : DescriptionLatex14.LISTE_CLASSE_FIN;
            writerUML.write(temp);
            if (nbrClasse == 0){
                writerUML.write(DescriptionLatex14.PAGE_FIN);
                writerUML.write(DescriptionLatex14.FICHIER_FIN);
            }else {
                writerUML.write(DescriptionLatex14.CLASSE_INTERNE_SUFFIX);
            }
            pileEtat.pop();
        } catch (IOException e){
            Err.ERR_CREATION_UML.sortir();
        }
    }

    /**
     * Ecrit les commandes appropriees dans le fichier. Manipulation speciale si c'est le premier Attribut
     * @param attribut Attribut en cours d'ecriture
     */
    @Override
    public void genAttribut(Attribut attribut) {
        try {
            if (pileEtat.peek().isPremierAttribut()) {
                writerUML.write(DescriptionLatex14.LISTE_ATTRIBUT_DEBUT);
                pileEtat.peek().setPremierAttribut(false);
            }else {
                writerUML.write(DescriptionLatex14.LISTE_ATTRIBUT_SEP);
            }
            writerUML.write(String.format("%s:%s", attribut.getNom(), attribut.getType()));
        }catch (IOException e){
            Err.ERR_CREATION_UML.sortir();
        }
    }

    /**
     * Ecrit les phrases appropries dans le fichier. Manipulations speciales si c'est la premiere methode du fichier.
     * @param methodeDebut MethodeDebut en cours d'ecriture
     */
    @Override
    public void genDebutMethode(MethodeDebut methodeDebut) {
        try{
            if (pileEtat.peek().isPremiereMethode()){
                writerUML.write(DescriptionLatex14.LISTE_METHODE_DEBUT);
                pileEtat.peek().setPremiereMethode(false);
            }else{
                writerUML.write(DescriptionLatex14.LISTE_METHODE_SEP);
            }
            if (estAbstrait){
                writerUML.write(DescriptionLatex14.ABSTRAIT_DEBUT);
            }
            if (!methodeDebut.getType().equals("void")){
                writerUML.write(String.format("%s ", methodeDebut.getType()));
            }
            writerUML.write(methodeDebut.getNom());
            writerUML.write(DescriptionLatex14.PARAMETRE_DEBUT);
            estPremierParametre = true;
        }catch (IOException e){
            Err.ERR_CREATION_UML.sortir();
        }
    }

    /**
     * Ecrit les phrases appropries pour Parametre selon l'etat interne
     * @param parametre Parametre en cours d'ecriture
     */
    @Override
    public void genParametre(Parametre parametre) {
        try {
            if (estPremierParametre) {
                estPremierParametre = false;
            }else {
                writerUML.write(DescriptionLatex14.PARAMETRE_SEP);
            }
            writerUML.write(parametre.getType());
        }catch (IOException e){
            Err.ERR_CREATION_UML.sortir();
        }
    }

    /**
     * Ecrit les phrases appropries pour FinMethode selon l'etatInterne
     * @param finMethode FinMethode en cours d'ecriture
     */
    @Override
    public void genFinMethode(MethodeFin finMethode) {
        try {
            writerUML.write(DescriptionLatex14.PARAMETRE_FIN);
            if (estAbstrait){
                writerUML.write(DescriptionLatex14.ABSTRAIT_FIN);
                estAbstrait = false;
            }
        }catch (IOException e){
            Err.ERR_CREATION_UML.sortir();
        }
    }

    /**
     * Ferme le FileWriter etant donner que toute l'ecriture est faite.
     */
    @Override
    public void verificationFin() {
        try {
            writerUML.close();
        } catch (IOException e){
            Err.ERR_CREATION_UML.sortir();
        }
    }
}
