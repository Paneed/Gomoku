package joueur;

import jouer.IHumain;

public class JoueurWhite implements IHumain {

    private String nom;
    private char symbole;

    public JoueurWhite(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du joueur ne peut pas être vide.");
        }
        this.nom = nom;
        this.symbole = 'O';
    }
    @Override
    public String getName() {
        return this.nom;
    }

    @Override
    public void afficheInfos() {
        System.out.println("Joueur : " + nom + " (Symbole : " + symbole + ")");
    }

    @Override
    public char getSymbole() {
        return this.symbole;
    }
}
