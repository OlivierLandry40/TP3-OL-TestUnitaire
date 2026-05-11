package logique;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Laboratoire {
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Recette> recettes;
    private Alchimiste proprietaire;

    /**
     * Constructeur du Laboratoire.
     * <p>
     * Préconditions :
     * - alchimiste != null
     * <p>
     * Postconditions :
     * - Les listes d'ingrédients et de recettes sont chargées depuis les fichiers
     * - this.proprietaire == alchimiste
     *
     * @param alchimiste Le propriétaire du laboratoire (non null)
     * @throws IllegalArgumentException si alchimiste est null
     */
    public Laboratoire(Alchimiste alchimiste) {
        if (alchimiste == null)
            throw new IllegalArgumentException("L'alchimiste ne peut pas être null.");

        this.chargerIngredients();
        this.chargerRecettes();

        this.proprietaire = alchimiste;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Recette> getRecettes() {
        return recettes;
    }

    public Alchimiste getProprietaire() {
        return proprietaire;
    }

    /**
     * Tente de fabriquer une potion a partir de 3 ingrédients
     * <p>
     * Préconditions :
     * - ing1, ing2, ing3 != null
     * - Les trois noms désignent des ingrédients distincts existants
     * <p>
     * Postconditions :
     * *   - Retourne un ResultatExperience indiquant si la recette existe et si la tentative a réussi
     *
     * @param ing1 nom du premier ingrédient (non null)
     * @param ing2 nom du deuxième ingrédient (non null)
     * @param ing3 nom du troisième ingrédient (non null)
     * @return le résultat de l'expérience
     * @throws IllegalArgumentException si un nom est null
     */

    public ResultatExperience fairePotion(String ing1, String ing2, String ing3) {
        if (ing1 == null || ing2 == null || ing3 == null)
            throw new IllegalArgumentException("Les noms d'ingrédients ne peuvent pas être null.");

        ResultatExperience experience = new ResultatExperience();

        Recette recette = this.trouverRecette(ing1, ing2, ing3);
        if (recette != null) {
            boolean success = this.proprietaire.fairePotion(recette);

            experience.setExiste(true);
            experience.setSuccess(success);
        }

        return experience;
    }

    /**
     * Crée une nouvelle recette dans le laboratoire si elle n'existe pas déjà.
     * <p>
     * Préconditions :
     * - ing1, ing2, ing3 != null et existent dans la liste des ingrédients
     * - Les trois ingrédients sont distincts
     * - nom != null et nom.length() >= 10
     * - 1 <= difficulte <= 5
     * - pointExperience > 0
     * <p>
     * Postconditions :
     * - Si la recette n'existait pas : elle est ajoutée au compendium et au fichier
     * - Retourne un ResultatExperience indiquant si la création a réussi
     *
     * @param ing1            Nom du premier ingrédient (non null)
     * @param ing2            Nom du deuxième ingrédient (non null)
     * @param ing3            Nom du troisième ingrédient (non null)
     * @param nom             Nom de la nouvelle recette
     * @param difficulte      Difficulté (1 à 5)
     * @param pointExperience Points d'expérience accordés (> 0)
     * @return Le résultat de l'expérience
     * @throws IllegalArgumentException si un paramètre est invalide
     */
    public ResultatExperience creerNouvellePotion(String ing1, String ing2, String ing3, String nom, int difficulte, int pointExperience) {
        if (ing1 == null || ing2 == null || ing3 == null)
            throw new IllegalArgumentException("Les noms d'ingrédients ne peuvent pas être null.");
        if (nom == null)
            throw new IllegalArgumentException("Le nom de la recette ne peut pas être null.");

        ResultatExperience experience = new ResultatExperience();
        experience.setExiste(true);

        Recette recette = this.trouverRecette(ing1, ing2, ing3);

        if (recette == null) {
            experience.setExiste(false);
            experience.setSuccess(true);

            Ingredient ingredient1 = this.trouverIngredient(ing1);
            Ingredient ingredient2 = this.trouverIngredient(ing2);
            Ingredient ingredient3 = this.trouverIngredient(ing3);

            if (ingredient1 == null || ingredient2 == null || ingredient3 == null)
                throw new IllegalArgumentException("Un ou plusieurs ingrédients sont introuvables dans la liste.");

            recette = new Recette(ingredient1, ingredient2, ingredient3, nom, difficulte, pointExperience);
            ajouterRecette(recette);
        }

        return experience;
    }

    /**
     * Recherche une recette correspondant aux trois ingrédients fournis.
     * <p>
     * Préconditions :
     * - ing1, ing2, ing3 != null
     * <p>
     * Postconditions :
     * - Retourne la recette si elle existe, null sinon
     *
     * @param ing1 Nom du premier ingrédient (non null)
     * @param ing2 Nom du deuxième ingrédient (non null)
     * @param ing3 Nom du troisième ingrédient (non null)
     * @return La recette correspondante ou null
     * @throws IllegalArgumentException si un nom est null
     */
    public Recette trouverRecette(String ing1, String ing2, String ing3) {
        if (ing1 == null || ing2 == null || ing3 == null)
            throw new IllegalArgumentException("Les noms d'ingrédients ne peuvent pas être null.");

        Recette resultat = null;

        for (Recette element : this.recettes) {
            if (element.contientIngredient(ing1) && element.contientIngredient(ing2) && element.contientIngredient(ing3)) {
                resultat = element;
                break;
            }
        }

        return resultat;
    }

    /**
     * Recherche un ingrédient par son nom.
     * <p>
     * Préconditions :
     * - nom != null
     * <p>
     * Postconditions :
     * - Retourne l'ingrédient si trouvé, null sinon
     *
     * @param nom Le nom de l'ingrédient à rechercher (non null)
     * @return L'ingrédient correspondant ou null
     * @throws IllegalArgumentException si nom est null
     */
    public Ingredient trouverIngredient(String nom) {
        if (nom == null)
            throw new IllegalArgumentException("Le nom ne peut pas être null.");

        Ingredient resultat = null;

        for (Ingredient ing : ingredients) {
            if (ing.getNom().equals(nom)) {
                resultat = ing;
                break;
            }
        }

        return resultat;
    }

    private ArrayList<Ingredient> chargerIngredients() {
        ingredients = new ArrayList<Ingredient>();
        List<String> lignesFichier = null;

        try {
            Path path = Paths.get("src/ingredients.txt");
            lignesFichier = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Fichier non trouvé");
        }

        for (String ligneFichier : lignesFichier) {
            String[] valeurs = ligneFichier.split("\\|");
            String nomIngredient = valeurs[0];
            int prixIngredient = Integer.parseInt(valeurs[1]);

            Ingredient ingredient = new Ingredient(nomIngredient, prixIngredient);
            ingredients.add(ingredient);
        }

        return ingredients;
    }

    private ArrayList<Recette> chargerRecettes() {
        recettes = new ArrayList<Recette>();
        List<String> lignesFichier = null;

        try {
            Path path = Paths.get("src/recettes.txt");
            lignesFichier = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Fichier non trouvé");
        }

        for (String ligneFichier : lignesFichier) {
            String[] valeurs = ligneFichier.split("\\|");
            String nomRecette = valeurs[0];
            Ingredient ingredent1 = this.trouverIngredient(valeurs[1]);
            Ingredient ingredent2 = this.trouverIngredient(valeurs[2]);
            Ingredient ingredent3 = this.trouverIngredient(valeurs[3]);
            int difficulte = Integer.parseInt(valeurs[4]);
            int pointExperience = Integer.parseInt(valeurs[5]);

            Recette recette = new Recette(ingredent1, ingredent2, ingredent3, nomRecette, difficulte, pointExperience);
            recettes.add(recette);
        }

        return recettes;
    }

    private void ajouterRecette(Recette recette) {
        String nouvelleRecette = recette.toString();
        try (PrintWriter output = new PrintWriter(new FileWriter("src/recettes.txt", true))) {
            output.printf("%s\r\n", nouvelleRecette);
        } catch (Exception e) {
        }

        this.recettes.add(recette);
    }
}