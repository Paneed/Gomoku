package joueur;

public class Humain {

    private String nom;      // Le nom du joueur
    private char symbole;    // Le symbole du joueur ('O' pour noir, 'X' pour blanc)

    // Constructeur
    public Humain(String nom, char symbole) {
        if (symbole != 'O' && symbole != 'X') {
            throw new IllegalArgumentException("Symbole invalide : doit être 'O' ou 'X'.");
        }
        this.nom = nom;
        this.symbole = symbole;
    }

    // Getter pour le nom
    public String getNom() {
        return nom;
    }

    // Getter pour le symbole
    public char getSymbole() {
        return symbole;
    }

    // Afficher les informations du joueur
    public void afficherInfo() {
        System.out.println("Joueur : " + nom + " (Symbole : " + symbole + ")");
    }
}
