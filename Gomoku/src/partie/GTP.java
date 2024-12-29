package partie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import jouer.IHumain;
import jouer.Jouer;
import joueur.BotMinimax;
import joueur.BotNaif;
import joueur.JoueurBlack;
import joueur.JoueurWhite;
import plateau.Plateau;

public class GTP {

    private static boolean PartieContinue;
    private static final String UNKNOWN_COMMAND = "? Unknown command";
    private static final String FORMAT_INCORRECT = "? Format incorrect";
    private static final String NUM_COMMANDE_INCORRECT = "? Le numero de la commande doit superieur au precedent";
    private static final String HELP = "Saissisez 'help' pour plus d'information";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Plateau plateau = new Plateau();
        IHumain joueurBlack = new JoueurBlack("B");
        IHumain joueurWhite = new JoueurWhite("W");
        BotNaif bot = new BotNaif();
        int numCommande = 1;
        PartieContinue = true;

        while (PartieContinue) {
            String commande = scanner.nextLine();
            if(commande.startsWith("help")){ // Si 'help' est saisie
                System.out.println("\n'§' avant '<>' signifie que l'option est facultative\n");
                System.out.println(" §<numero_de_la_commande>  boardsize  <taille>                     Définir la taille du plateau de 1 à 26");
                System.out.println(" §<numero_de_la_commande>  showboard                               Afficher le plateau");
                System.out.println(" §<numero_de_la_commande>  clear_board  <taille>                   Enlève tous les pions du plateau");
                System.out.println(" §<numero_de_la_commande>  play  <couleur>  <coordonnee>           Place un pion de la couleur saisie au coordonnée indiquée, coordonnée de format (A3, C4, J3)");
                System.out.println(" §<numero_de_la_commande>  genmove <couleur>  §<profondeur>        Place un pion de la couleur saisie au coordonnée obtenue grace à un bot, la profondeur permet de régler le niveau de celui-ci");
                System.out.println(" §<numero_de_la_commande>  quit                                    Quitter le jeu");
                continue;
            }
            // On coupe la commande saisie en plusieurs fragments, les espaces sont utilisé comme separateur
            List<String> commandeFragment = new ArrayList<>(Arrays.asList(commande.split(" ")));
            if(!estUnNombre(commandeFragment.get(0))){ // Si la commande ne commence pas par un numero
                commandeFragment.add(0,Integer.toString(numCommande == 1 ? 1 : numCommande + 1));
            }
            int newNumCommande = Integer.parseInt(commandeFragment.get(0)); // Le numero de la commande
            String couleur;
            String coordonnee;
            int taille;

            if(commandeFragment.size() < 2){ // Le cas ou seul un nombre est saisie
                System.err.println(FORMAT_INCORRECT);
                System.out.println(HELP);
                continue;
            }

            if (newNumCommande >= numCommande || newNumCommande == 1) { // il faut que le numero de la commande saisie soit supérieur au prédecesant
                String getCommande = commandeFragment.get(1);

                switch (getCommande){ // On effectue une action selon la commande saisie
                    case "boardsize" :
                        try {
                            taille = Integer.parseInt(commandeFragment.get(2));
                            plateau.boardsize(taille);
                            System.out.println("="+numCommande);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "showboard" :
                        System.out.println("="+numCommande);
                        plateau.showboard();
                        break;
                    case "play" :
                        if(commandeFragment.size() != 4){ // Si tous les paramètres ne sont pas saisie
                            System.err.println(FORMAT_INCORRECT);
                            System.out.println(HELP);
                            continue;
                        }
                        couleur = commandeFragment.get(2);
                        coordonnee = commandeFragment.get(3);
                        commande = getCommande +" "+ couleur+" "+ coordonnee;
                        boolean coupJoue;
                        if(couleur.equals("black")){
                            coupJoue = Jouer.play(commande, joueurBlack, plateau);
                        }else{
                            coupJoue = Jouer.play(commande, joueurWhite, plateau);
                        }
                        if(coupJoue)
                            System.out.println("="+numCommande);
                        break;
                    case "clear_board" :
                        plateau.clearPlateau();
                        System.out.println("="+numCommande);
                        break;
                    case "genmove":
                        if(commandeFragment.size() != 4 && commandeFragment.size() != 3){ // Si tous les paramètres ne sont pas saisie
                            System.err.println(FORMAT_INCORRECT);
                            System.out.println(HELP);
                            continue;
                        }
                        couleur = commandeFragment.get(2);
                        String coupJoueBot;
                        char colonne;
                        String ligne;

                        if (!couleur.equals("black") && !couleur.equals("white")) { // Couleur incorrecte
                            System.err.println(FORMAT_INCORRECT);
                            System.out.println(HELP);
                            continue;
                        }

                        // On détermine les symboles selon la couleur en parametre
                        char symboleJoueur = couleur.equals("black") ? joueurBlack.getSymbole() : joueurWhite.getSymbole();
                        char symboleAdversaire = couleur.equals("black") ? joueurWhite.getSymbole() : joueurBlack.getSymbole();

                        if (commandeFragment.size() == 4) { // Si une profondeur est spécifiée
                            int profondeur;
                            try {
                                profondeur = Integer.parseInt(commandeFragment.get(3));
                            } catch (NumberFormatException e) {
                                System.err.println("? La valeur '" + commandeFragment.get(3) + "' n'est pas un nombre valide.");
                                continue;
                            }

                            // Création du BotMinimax avec la profondeur
                            BotMinimax botMinimax = new BotMinimax(plateau, symboleJoueur, symboleAdversaire, profondeur);
                            int[] coup = botMinimax.choisirCoup();
                            plateau.setCase(coup[0], coup[1], symboleJoueur);

                            // Conversion du coup en format A12
                            colonne = (char) ('A' + coup[1]);
                            ligne = Integer.toString(coup[0] + 1);
                            coupJoueBot = colonne + ligne;
                        } else { // Pas de profondeur spécifiée, bot aléatoire
                            coupJoueBot = bot.jouer(plateau, symboleJoueur);
                        }

                        if (!coupJoueBot.equals("")) // Dans le cas ou le bot ne renvoie pas de position
                            System.out.println("=" + numCommande + " " + coupJoueBot);
                        break;

                    case "quit" :
                        System.out.println("="+numCommande);
                        plateau.quit();
                        PartieContinue = false; // On arrete la boucle white et donc le GTP
                        break;
                    default:
                        System.err.println(UNKNOWN_COMMAND);
                        System.out.println(HELP);
                        continue;

                }
                numCommande = (newNumCommande == numCommande) ? newNumCommande + 1 : newNumCommande; // On actualise le numero de la commande
            }else{
                System.err.println(NUM_COMMANDE_INCORRECT);
                System.out.println(HELP);
            }
        }
    }

    private static boolean estUnNombre(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
