package logique;

import java.util.ArrayList;

/**
 * Author : Mathieu Bourgoin
 * Ordre de conception : 3e
 */
public class Recette
{
    private ArrayList<Ingredient> ingredients;
    private String nom;
    private int difficulte;
    private int pointExperience;

    public Recette(Ingredient ing1, Ingredient ing2, Ingredient ing3, String nom, int difficulte, int pointExperience)
    {
        this.ingredients = new ArrayList<Ingredient>();
        this.ingredients.add(ing1);
        this.ingredients.add(ing2);
        this.ingredients.add(ing3);

        this.setDifficulte(difficulte);
        this.setNom(nom);
        this.setPointExperience(pointExperience);
    }


    public String getNom() {
        return nom;
    }

    private void setNom(String nom) {
        this.nom = nom;
    }

    public int getDifficulte() {
        return difficulte;
    }

    private void setDifficulte(int difficulte) {
        this.difficulte = difficulte;
    }

    public int getPointExperience() {
        return pointExperience;
    }

    private void setPointExperience(int pointExperience) {
        this.pointExperience = pointExperience;
    }

    public int obtenirPrix()
    {
        int prixTotal = 0;

        for (Ingredient ing : this.ingredients)
            prixTotal += ing.getPrix();

        return prixTotal;
    }

    public boolean contientIngredient(String nom)
    {
        boolean estContenu = false;

        for (Ingredient ing : this.ingredients)
        {
            if (ing.getNom().equals(nom))
            {
                estContenu = true;
                break;
            }
        }

        return estContenu;
    }
    @Override
    public String toString()
    {
        return String.format("%s|%s|%s|%s|%s|%s", this.getNom(), this.ingredients.get(0).getNom(), this.ingredients.get(1).getNom(), this.ingredients.get(2).getNom(), this.getDifficulte(), this.getPointExperience());
    }


}
