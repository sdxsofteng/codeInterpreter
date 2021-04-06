public class Etat {

    public boolean premierAttribut = true;
    public boolean premiereMethode =  true;
    public boolean premiereClasse = true;

    public Etat() {}

    public Etat(boolean premierAttribut, boolean premiereMethode, boolean premiereClasse) {
        this.premierAttribut = premierAttribut;
        this.premiereMethode = premiereMethode;
        this.premiereClasse = premiereClasse;
    }

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
