import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class InterpreteurEcriture implements ContexteInterpretation{

    Stack<File> pileFichiers = new Stack<>();
    Stack<String> pileNoms = new Stack<>();
    Stack<FileWriter> pileWriters = new Stack<>();
    boolean estAbstrait = false;
    boolean estPremierParametre = false;
    FileWriter writerCourant;

    @Override
    public void genAbstrait(Abstrait abstrait) {
        estAbstrait = true;
    }

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
                writerCourant.write(String.format("extends %s {\n", pileNoms.get(1)));
            }else {
                writerCourant.write("{\n");
            }
            estAbstrait = false;
        } catch (IOException e){
            Err.ERR_ECRITURE.sortir();
        }
    }

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

    @Override
    public void genAttribut(Attribut attribut) {
        writerCourant = pileWriters.peek();
        String type = attribut.getType();
        String nom = attribut.getNom();
        try {
            writerCourant.write(String.format("private %s %s ;\n\n", type, nom));
            writerCourant.write(String.format("public %s get%s(){\n    return %s ;\n}\n\n", type, nom, nom));
            writerCourant.write(String.format("public void set%s ( %s, %s){\n    this.%s = %s ;\n}\n\n",
                    nom, type, nom, nom, nom));
        } catch (IOException e){
            Err.ERR_ECRITURE.sortir();
        }
    }

    @Override
    public void genDebutMethode(MethodeDebut methodeDebut) {
        writerCourant = pileWriters.peek();
        String type = methodeDebut.getType();
        String nom = methodeDebut.getNom();
        try {
            writerCourant.write("public ");
            if (estAbstrait){
                writerCourant.write("abstract ");
            }
            writerCourant.write(String.format("%s %s (", type, nom));
            estPremierParametre = true;
        }catch (IOException e){
            Err.ERR_ECRITURE.sortir();
        }
    }

    @Override
    public void genParametre(Parametre parametre) {
        writerCourant = pileWriters.peek();
        String type = parametre.getType();
        String nom = parametre.getNom();
        try{
            if(!estPremierParametre){
                writerCourant.write(", ");
            }
            writerCourant.write(String.format("%s %s", type, nom));
            estPremierParametre = false;
        }catch (IOException e){
            Err.ERR_ECRITURE.sortir();
        }
    }

    @Override
    public void genFinMethode(MethodeFin finMethode) {
        writerCourant = pileWriters.peek();
        try{
            writerCourant.write(")");
            if (estAbstrait){
                writerCourant.write(";\n");
            }else {
                writerCourant.write("{}\n");
            }
            estAbstrait = false;
        }catch (IOException e){
            Err.ERR_ECRITURE.sortir();
        }
    }

    @Override
    public void verificationFin() {}
}
