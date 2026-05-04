package logique;

import java.util.Random;

public class Alchimiste
{
    public static final int EXPERIENCE_POUR_NIVEAU_SUIVANT = 500;
    private String nom;
    private int niveau;
    private int experience;

    public Alchimiste(String nom)
    {
        this.setNom(nom);
        this.setNiveau(1);
        this.setExperience(0);
    }

    public Alchimiste(String nom,int niveau)
    {
        this(nom);
        setNiveau(niveau);
    }

    public String getNom()
    {
        return this.nom;
    }
    public int getNiveau()
    {
        return this.niveau;
    }
    public int getExperience()
    {
        return this.experience;
    }

    private void setNom(String nom)
    {
        this.nom = nom;
    }
    private void setNiveau(int niveau)
    {
        this.niveau = niveau;
    }
    private void setExperience(int experience)
    {
        this.experience = experience;
    }

    public boolean fairePotion(Recette recette)
    {
        boolean estReussi = false;
        double tauxExperience = this.niveau * 0.05;
        double tauxEchec = (recette.getDifficulte() * 0.25) - tauxExperience;

        if (tauxEchec < 0)
            tauxEchec = 0;

        Random random = new Random();
        double jetSuccess = ((double) random.nextInt(1, 101))/100;

        if (jetSuccess >= tauxEchec)
        {
            int nbExperience = recette.getPointExperience();
            this.ajouterExperience(nbExperience);
            estReussi = true;
        }

        return estReussi;
    }

    private void ajouterExperience(int experience)
    {
        this.setExperience(this.getExperience() + experience);

        if(this.getExperience() >= EXPERIENCE_POUR_NIVEAU_SUIVANT)
        {
            this.setNiveau(this.getNiveau() + 1);
            this.setExperience(this.getExperience() - EXPERIENCE_POUR_NIVEAU_SUIVANT);
            System.out.println("Vous êtes maintenant un alchimiste de niveau " + this.getNiveau());
        }
    }

}
