package logique;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Laboratoire
{
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Recette> recettes;
    private Alchimiste proprietaire;

    public Laboratoire(Alchimiste alchimiste)
    {
        this.chargerIngredients();
        this.chargerRecettes();

        this.proprietaire = alchimiste;
    }

    public List<Ingredient> getIngredients()
    {
        return ingredients;
    }
    public List<Recette> getRecettes()
    {
        return recettes;
    }
    public Alchimiste getProprietaire()
    {
        return proprietaire;
    }

    public ResultatExperience fairePotion(String ing1, String ing2, String ing3)
    {
        ResultatExperience experience = new ResultatExperience();

        Recette recette = this.trouverRecette(ing1, ing2, ing3);
        if(recette != null)
        {
            boolean success = this.proprietaire.fairePotion(recette);

            experience.setExiste(true);
            experience.setSuccess(success);
        }

        return experience;
    }

    public ResultatExperience creerNouvellePotion(String ing1, String ing2, String ing3, String nom, int difficulte, int pointExperience)
    {
        ResultatExperience experience = new ResultatExperience();
        experience.setExiste(true);

        Recette recette = this.trouverRecette(ing1, ing2, ing3);

        if(recette == null)
        {
            experience.setExiste(false);
            experience.setSuccess(true);

            Ingredient ingredient1 = this.trouverIngredient(ing1);
            Ingredient ingredient2 = this.trouverIngredient(ing2);
            Ingredient ingredient3 = this.trouverIngredient(ing3);

            recette = new Recette(ingredient1, ingredient2, ingredient3, nom, difficulte, pointExperience);
            ajouterRecette(recette);
        }

        return experience;
    }

    public Recette trouverRecette(String ing1, String ing2, String ing3)
    {
        Recette resultat = null;

        for(Recette element : this.recettes)
        {
            if (element.contientIngredient(ing1) && element.contientIngredient(ing2) && element.contientIngredient(ing3))
            {
                resultat = element;
                break;
            }
        }

        return resultat;
    }

    public Ingredient trouverIngredient(String nom)
    {
        Ingredient resultat = null;

        for (Ingredient ing : ingredients)
        {
            if (ing.getNom().equals(nom))
            {
                resultat = ing;
                break;
            }
        }

        return resultat;
    }

    private ArrayList<Ingredient> chargerIngredients()
    {
        ingredients = new ArrayList<Ingredient>();
        List<String> lignesFichier = null;

        try
        {
            Path path = Paths.get("src/ingredients.txt");
            lignesFichier = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Fichier non trouvé");
        }

        for (String ligneFichier : lignesFichier)
        {
            String[] valeurs = ligneFichier.split("\\|");
            String nomIngredient = valeurs[0];
            int prixIngredient = Integer.parseInt(valeurs[1]);

            Ingredient ingredient = new Ingredient(nomIngredient, prixIngredient);
            ingredients.add(ingredient);
        }

        return ingredients;
    }

    private ArrayList<Recette> chargerRecettes()
    {
        recettes = new ArrayList<Recette>();
        List<String> lignesFichier = null;

        try
        {
            Path path = Paths.get("src/recettes.txt");
            lignesFichier = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Fichier non trouvé");
        }

        for (String ligneFichier : lignesFichier)
        {
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

    private void ajouterRecette(Recette recette)
    {
        String nouvelleRecette = recette.toString();
        try(PrintWriter output = new PrintWriter(new FileWriter("src/recettes.txt",true)))
        {
            output.printf("%s\r\n", nouvelleRecette);
        }
        catch (Exception e){}

        this.recettes.add(recette);
    }
}