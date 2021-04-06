/**
 * Cette classe représente des États pour l'écriture du fichier LaTeX
 * Permet de déterminer l'état de certaines commandes et d'écrire les commande LaTeX appropriées.
 * Trois booleans permettent de déterminer l'état pour l'interpréteur UML
 */
public class Etat {

    public boolean premierAttribut = true;
    public boolean premiereMethode =  true;
    public boolean premiereClasse = true;

    public Etat() {}

    public boolean isPremierAttribut() {
        return premierAttribut;
    }

    public void setPremierAttribut(boolean premierAttribut) {
        this.premierAttribut = premierAttribut;
    }

    public boolean isPremiereMethode() {
        return premiereMethode;
    }

    public void setPremiereMethode(boolean premiereMethode) {
        this.premiereMethode = premiereMethode;
    }

    public boolean isPremiereClasse() {
        return premiereClasse;
    }

    public void setPremiereClasse(boolean premiereClasse) {
        this.premiereClasse = premiereClasse;
    }
}
