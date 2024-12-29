package joueur;

import jouer.IHumain;

public class JoueurBlack implements IHumain {

    private String nom;
    private char symbole;

    public JoueurBlack(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du joueur ne peut pas être vide.");
        }
        this.nom = nom;
        this.symbole = 'X';
    }


    @Override
    public String getName() {
        return nom;
    }

    @Override
    public char getSymbole() {
        return symbole;
    }

    @Override
    public void afficheInfos() {
        System.out.println("Joueur : " + nom + " (Symbole : " + symbole + ")");
    }
}
