package logique;

public class Ingredient
{
    private String nom;
    private int prix;

    public Ingredient(String nom, int prix)
    {
        this.setNom(nom);
        this.setPrix(prix);
    }

    public String getNom() {
        return nom;
    }

    private void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrix() {
        return prix;
    }

    private void setPrix(int prix) {
        this.prix = prix;
    }

    @Override
    public String toString()
    {
        return "Ingredient{" + "nom=" + nom + ", prix=" + prix + '}';
    }
}