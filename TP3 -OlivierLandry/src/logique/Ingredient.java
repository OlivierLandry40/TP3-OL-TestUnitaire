package logique;

public class Ingredient {
    private String nom;
    private int prix;

    public Ingredient(String nom, int prix) {
        this.setNom(nom);
        this.setPrix(prix);
    }

    public String getNom() {
        return nom;
    }

    /**
     * Preéconditions;
     *  - nom != null
     *  - nom >= 6
     * @param nom
     */

    private void setNom(String nom) {
        if (nom == null)
            throw new IllegalArgumentException("Le nom ne peut pas être null.");
        if (nom.length() < 6)
            throw new IllegalArgumentException("Le nom doit contenir au moins 6 caractères.");
        this.nom = nom;
    }

    public int getPrix() {
        return prix;
    }

    /**
     * Préconditions :
     * - prix > 0
     *
     * @throws IllegalArgumentException si la précondition n'est pas respectée
     */

    private void setPrix(int prix) {
        if (prix <= 0)
            throw new IllegalArgumentException("Le prix doit être supérieur à 0.");
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Ingredient{" + "nom=" + nom + ", prix=" + prix + '}';
    }
}