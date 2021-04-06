import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class InterpreteurUML implements ContexteInterpretation{

    public final String PATH_FICHIER_UML = "uml.tex";

    int nbrClasse = 0;
    Stack<Etat> pileEtat = new Stack<>();
    boolean estAbstrait = false;
    boolean estPremierParametre = false;
    File fichierUML;
    FileWriter writerUML;

    public InterpreteurUML() {
        fichierUML = new File(PATH_FICHIER_UML);
        try {
            writerUML = new FileWriter(fichierUML);
        }catch (IOException e){
            Err.ERR_CREATION_UML.sortir();
        }
    }

    @Override
    public void genAbstrait(Abstrait abstrait) {
        estAbstrait = true;
    }

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

    @Override
    public void genAttribut(Attribut attribut) {
        try {
            if (pileEtat.peek().premierAttribut) {
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

    @Override
    public void verificationFin() {
        try {
            writerUML.close();
        } catch (IOException e){
            Err.ERR_CREATION_UML.sortir();
        }
    }
}
