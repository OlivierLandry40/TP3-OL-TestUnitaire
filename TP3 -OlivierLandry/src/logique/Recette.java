package logique;

import java.util.ArrayList;

/**
 * Author : Mathieu Bourgoin
 * Ordre de conception : 3e
 */
public class Recette {
    private ArrayList<Ingredient> ingredients;
    private String nom;
    private int difficulte;
    private int pointExperience;

    public Recette(Ingredient ing1, Ingredient ing2, Ingredient ing3, String nom, int difficulte, int pointExperience) {
        this.ingredients = new ArrayList<Ingredient>();
        this.ingredients.add(ing1);
        this.ingredients.add(ing2);
        this.ingredients.add(ing3);

        this.setDifficulte(difficulte);
        this.setNom(nom);
        this.setPointExperience(pointExperience);
    }


    /**
     * Préconditions :
     * - nom != null
     * - nom.length() >= 10
     *
     * @throws IllegalArgumentException si la précondition n'est pas respectée
     */
    public String getNom() {
        return nom;
    }

    private void setNom(String nom) {
        if (nom == null)
            throw new IllegalArgumentException("Le nom ne peut pas être null.");
        if (nom.length() < 10)
            throw new IllegalArgumentException("Le nom doit contenir au moins 10 caractères.");
        this.nom = nom;
    }

    /**
     * Préconditions :
     * - 1 <= difficulte <= 5
     *
     * @throws IllegalArgumentException si la précondition n'est pas respectée
     */
    public int getDifficulte() {
        return difficulte;
    }

    private void setDifficulte(int difficulte) {
        if (difficulte < 1 || difficulte > 5)
            throw new IllegalArgumentException("La difficulté doit être entre 1 et 5 inclusivement.");
        this.difficulte = difficulte;
    }

    public int getPointExperience() {
        return pointExperience;
    }

    /**
     * Préconditions :
     * - pointExperience > 0
     *
     * @throws IllegalArgumentException si la précondition n'est pas respectée
     */
    private void setPointExperience(int pointExperience) {
        if (pointExperience <= 0)
            throw new IllegalArgumentException("Les points d'expérience doivent être strictement supérieurs à 0.");
        this.pointExperience = pointExperience;
    }

    public int obtenirPrix() {
        int prixTotal = 0;

        for (Ingredient ing : this.ingredients)
            prixTotal += ing.getPrix();

        return prixTotal;
    }

    /**
     * Vérifie si un ingrédient au nom donné fait partie de la recette.
     * <p>
     * Préconditions :
     * - nom != null
     * <p>
     * Postconditions :
     * - retourne true si un ingrédient de la recette a ce nom, false sinon
     *
     * @param nom Le nom à rechercher (non null)
     * @return true si l'ingrédient est présent, false sinon
     * @throws IllegalArgumentException si nom est null
     */
    public boolean contientIngredient(String nom) {
        if (nom == null)
            throw new IllegalArgumentException("Le nom ne peut pas être null.");

        boolean estContenu = false;

        for (Ingredient ing : this.ingredients) {
            if (ing.getNom().equals(nom)) {
                estContenu = true;
                break;
            }
        }

        return estContenu;
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%s|%s", this.getNom(),
                this.ingredients.get(0).getNom(),
                this.ingredients.get(1).getNom(), this.ingredients.get(2).getNom(),
                this.getDifficulte(), this.getPointExperience());
    }


}
