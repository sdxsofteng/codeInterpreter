import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

/**
 * Cette classe represente le deuxieme interpreteur qui permet de transformer le code entree en fichier .java valide
 * Les methodes ayant du code necessitant clarification sont decrites plus en profondeur mais, sinon, les etapes faites
 * par le code sont celles de l'enonce.
 */
public class InterpreteurEcriture implements ContexteInterpretation{

    /**
     * pileFichiers: pile de fichiers ouverts
     * pileNoms: pile des noms de fichiers couramment ouverts
     * pileWriters: permet de garder le writer courant au top et d'ecrire dans le bon fichier.
     * writerCourant: writer au top de la pile, donc le writer utiliser pour ecrire dans le fichier courant
     */
    Stack<File> pileFichiers = new Stack<>();
    Stack<String> pileNoms = new Stack<>();
    Stack<FileWriter> pileWriters = new Stack<>();
    boolean estAbstrait = false;
    boolean estPremierParametre = false;
    FileWriter writerCourant;

    /**
     * Met la valeur abstrait et la met a true pour utilisation future dans les autres fonctions de l'interpreteur.
     * @param abstrait Commande Abstrait en cours de traitement
     */
    @Override
    public void genAbstrait(Abstrait abstrait) {
        estAbstrait = true;
    }

    /**
     * Creer un nouveau fichier sur la pile et ecrit les informations appropriees selon l'etat dans le fichier
     * Quand on veut trouver le nom de la classe precedente on va prendre le size du Stack et on enleve -2 pour
     * aller chercher le nom sous le nomCourant
     * @param classeDebut ClasseDebut en cours de traitement
     */
    @Override
    public void genDebutClasse(ClasseDebut classeDebut) {
        String nom = classeDebut.getNomDeLaClasse();
        pileFichiers.push(new File(nom));
        pileNoms.push(nom);
        try {
            pileWriters.push(new FileWriter(pileFichiers.peek()));
            writerCourant = pileWriters.peek();
            writerCourant.write("public ");
            if (estAbstrait){
                writerCourant.write("abstract ");
            }
            writerCourant.write(String.format("classe %s ", nom));
            if (pileNoms.size() > 1){
                writerCourant.write(String.format("extends %s {\n", pileNoms.get(pileNoms.size() - 2)));
            }else {
                writerCourant.write("{\n");
            }
            estAbstrait = false;
        } catch (IOException e){
            Err.ERR_ECRITURE.sortir();
        }
    }

    /**
     * Ecrit les information appropriees dans le fichier, ferme le fichier et pop les piles appropries pour continuer
     * l'interpretation selon l'etat interne
     * @param classeFin ClasseFin courante
     */
    @Override
    public void genFinClasse(ClasseFin classeFin) {
        writerCourant = pileWriters.peek();
        try {
            writerCourant.write("}\n");
            writerCourant.close();
            pileWriters.pop();
            pileFichiers.pop();
            pileNoms.pop();
        } catch (IOException e){
            Err.ERR_ECRITURE.sortir();
        }
    }

    /**
     * Ecrit le code dans le fichier courant pour la Commande Attribut.
     * @param attribut Attribut courant pour ecriture
     */
    @Override
    public void genAttribut(Attribut attribut) {
        writerCourant = pileWriters.peek();
        String type = attribut.getType();
        String nom = attribut.getNom();
        try {
            String nomGetSet = nom.substring(0,1).toUpperCase() + nom.substring(1);
            writerCourant.write(String.format("    private %s %s ;\n\n", type, nom));
            writerCourant.write(String.format("    public %s get%s(){\n        return %s ;\n    " +
                    "}\n\n", type, nomGetSet, nom));
            writerCourant.write(String.format("    public void set%s( %s, %s ){\n        this.%s = %s ;\n    }\n\n",
                    nomGetSet, type, nom, nom, nom));
        } catch (IOException e){
            Err.ERR_ECRITURE.sortir();
        }
    }

    /**
     * Ecrite le code dans le fichier pour la generation de debut de methode.
     * @param methodeDebut MethodeDebut en cours d'ecriture
     */
    @Override
    public void genDebutMethode(MethodeDebut methodeDebut) {
        writerCourant = pileWriters.peek();
        String type = methodeDebut.getType();
        String nom = methodeDebut.getNom();
        try {
            writerCourant.write("    public ");
            if (estAbstrait){
                writerCourant.write("abstract ");
            }
            writerCourant.write(String.format("%s %s( ", type, nom));
            estPremierParametre = true;
        }catch (IOException e){
            Err.ERR_ECRITURE.sortir();
        }
    }

    /**
     * Ecrit le code dans le fichier pour la generation de parametre
     * @param parametre Parametre en cours d'ecriture
     */
    @Override
    public void genParametre(Parametre parametre) {
        writerCourant = pileWriters.peek();
        String type = parametre.getType();
        String nom = parametre.getNom();
        try{
            if(!estPremierParametre){
                writerCourant.write(", ");
            }
            writerCourant.write(String.format("%s %s ", type, nom));
            estPremierParametre = false;
        }catch (IOException e){
            Err.ERR_ECRITURE.sortir();
        }
    }

    /**
     * Ecrit le code dans le fichier pour la generation de fin de methode
     * @param finMethode FinMethode en cours d'ecriture
     */
    @Override
    public void genFinMethode(MethodeFin finMethode) {
        writerCourant = pileWriters.peek();
        try{
            writerCourant.write(")");
            if (estAbstrait){
                writerCourant.write(";\n");
            }else {
                writerCourant.write(" {\n    }\n");
            }
            estAbstrait = false;
        }catch (IOException e){
            Err.ERR_ECRITURE.sortir();
        }
    }

    /**
     * Aucune verification de Fin a faire dans cet interpreteur.
     */
    @Override
    public void verificationFin() {}
}
