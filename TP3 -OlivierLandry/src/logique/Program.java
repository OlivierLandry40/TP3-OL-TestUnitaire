package logique;

import java.util.Scanner;

public class Program
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Bienvenue sorcier!");
        System.out.println("Veuillez entrez votre nom");
        String nomAlchimiste = sc.nextLine();

        Alchimiste alchimiste = new Alchimiste(nomAlchimiste);
        ResultatExperience resultatExperience = new ResultatExperience();
        Laboratoire laboratoire = new Laboratoire(alchimiste);

        String choix = "";
        while (!choix.equals("3"))
        {
            System.out.println("Bonjour " + nomAlchimiste + "!" + " Que voulez vous faire?");
            System.out.println("(1) – Faire une potion");
            System.out.println("(2) – Créer une nouvelle recette");
            System.out.println("(3) – Quitter (ATTENTION! Vous perdrez votre progression.)");

            choix = sc.nextLine();
            switch (choix)
            {
                case "1":
                    System.out.println();
                    System.out.println("Très bon choix! Nous allons maintenant tenter de faire une potion!");
                    System.out.println("Veuillez écrire le numéro de 3 ingrédients parmis la liste :");

                    afficherListeIngredients(laboratoire);
                    String choixIng1 = getUserInputIngredient(laboratoire, sc);
                    String choixIng2 = getUserInputIngredient(laboratoire, sc);
                    String choixIng3 = getUserInputIngredient(laboratoire, sc);

                    resultatExperience = laboratoire.fairePotion(choixIng1, choixIng2, choixIng3);

                    if (!resultatExperience.getExiste())
                        System.out.println("Désolé cette recette n'existe pas");
                    else if (resultatExperience.getSuccess())
                    {
                        System.out.println("Bravo vous avez réussi!");
                        System.out.println("Vous avez fait une potion de " + laboratoire.trouverRecette(choixIng1, choixIng2, choixIng3).getNom());
                    }
                    else
                        System.out.println("Malheureusement la potion est un échec");
                    System.out.println("Vous etes un alchimiste de niveau: " + alchimiste.getNiveau() + " (Expérience: " + alchimiste.getExperience()+").");
                    System.out.println();
                    break;
                case "2":
                    System.out.println("Très bon choix! Nous allons maintenant créer une nouvelle potion!");
                    System.out.println("Veuillez écrire le numéro de 3 ingrédients parmis la liste :");

                    afficherListeIngredients(laboratoire);
                    String choixIngCreation1 = getUserInputIngredient(laboratoire, sc);
                    String choixIngCreation2 = getUserInputIngredient(laboratoire, sc);
                    String choixIngCreation3 = getUserInputIngredient(laboratoire, sc);

                    System.out.println("Veuillez choisir un nom pour votre recette");
                    String nom = sc.nextLine();

                    System.out.println("Veuillez choisir la difficulté de votre recette (1-5)");
                    int difficulte = Integer.parseInt(sc.nextLine());

                    System.out.println("Veuillez choisir le nombre de point d'expérience que donnera votre recette");
                    int pointExperience = Integer.parseInt(sc.nextLine());

                    resultatExperience = laboratoire.creerNouvellePotion(choixIngCreation1, choixIngCreation2, choixIngCreation3, nom, difficulte, pointExperience);
                    if (resultatExperience.getExiste())
                        System.out.println("Désolé cette recette existe déja");
                    else if (resultatExperience.getSuccess())
                        System.out.println("Bravo vous créé votre recette!");

                    System.out.println();
                    break;
                case "3":
                    System.out.println("Au revoir!");
                    break;
            }
        }
    }

    private static void afficherListeIngredients(Laboratoire laboratoire)
    {
        for (int i = 0; i < laboratoire.getIngredients().size(); i++)
        {
            System.out.println("(" + (i + 1) + ") - " + laboratoire.getIngredients().get(i).toString());
        }
    }
    private static String getUserInputIngredient(Laboratoire laboratoire, Scanner sc)
    {
        System.out.print("Votre choix :");
        int choixIng = Integer.parseInt(sc.nextLine());
        while (choixIng < 1 || choixIng > laboratoire.getIngredients().size())
        {
            System.out.println("Cet ingrédient n'existe pas!");
            System.out.print("Votre choix :");
            choixIng = Integer.parseInt(sc.nextLine());
        }
        return laboratoire.getIngredients().get(choixIng-1).getNom();
    }


}
