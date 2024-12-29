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
            if(commande.startsWith("help")){
                System.out.println("\n'§' avant '<>' signifie que l'option est facultative\n");
                System.out.println(" §<numero_de_la_commande>  boardsize  <taille>                     Définir la taille du plateau de 1 à 26");
                System.out.println(" §<numero_de_la_commande>  showboard                               Afficher le plateau");
                System.out.println(" §<numero_de_la_commande>  clear_board  <taille>                   Enlève tous les pions du plateau");
                System.out.println(" §<numero_de_la_commande>  play  <couleur>  <coordonnee>           Place un pion de la couleur saisie au coordonnée indiquée, coordonnée de format (A3, C4, J3)");
                System.out.println(" §<numero_de_la_commande>  genmove <couleur>  §<profondeur>        Place un pion de la couleur saisie au coordonnée obtenue grace à un bot, la profondeur permet de régler le niveau de celui-ci");
                System.out.println(" §<numero_de_la_commande>  quit                                    Quitter le jeu");
                continue;
            }
            List<String> commandeFragment = new ArrayList<>(Arrays.asList(commande.split(" ")));
            if(!estUnNombre(commandeFragment.get(0))){
                commandeFragment.add(0,Integer.toString(numCommande == 1 ? 1 : numCommande + 1));
            }
            int newNumCommande = Integer.parseInt(commandeFragment.get(0));
            String couleur;
            String coordonnee;
            int taille;


            if(commandeFragment.size() < 2){
                System.out.println("? unknown command");
                System.out.println("Saissisez 'help' pour plus d'information");
                continue;
            }
            if (newNumCommande >= numCommande || newNumCommande == 1) {
                String getCommande = commandeFragment.get(1);

                switch (getCommande){
                    case "boardsize" :
                        try {
                            taille = Integer.parseInt(commandeFragment.get(2));
                            plateau.boardsize(taille);
                            System.out.println("="+numCommande);
                        } catch (NumberFormatException e) {
                            System.out.println("Erreur : Taille du plateau invalide.");
                        }
                        break;
                    case "showboard" :
                        System.out.println("="+numCommande);
                        plateau.showboard();
                        break;
                    case "play" :
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
                        couleur = commandeFragment.get(2);
                        String coupJoueBot;
                        char colonne;
                        String ligne;

                        if (couleur.equals("black")) {
                            if(commandeFragment.size() == 4) {
                                int profondeur = Integer.parseInt(commandeFragment.get(3));
                                BotMinimax botMinimax = new BotMinimax(plateau,joueurBlack.getSymbole(),joueurWhite.getSymbole(),profondeur);

                                int[] coup = botMinimax.choisirCoup();
                                plateau.setCase(coup[0], coup[1], joueurBlack.getSymbole());

                                colonne = (char) ('A' + coup[1]);
                                ligne = Integer.toString(coup[0] + 1);
                                coupJoueBot = colonne + ligne;
                            }else{
                                coupJoueBot = bot.jouer(plateau, joueurBlack.getSymbole());

                            }
                        } else {
                            if(commandeFragment.size() == 4) {
                                int profondeur = Integer.parseInt(commandeFragment.get(3));
                                BotMinimax botMinimax = new BotMinimax(plateau,joueurWhite.getSymbole(),joueurBlack.getSymbole(),profondeur);

                                int[] coup = botMinimax.choisirCoup();
                                plateau.setCase(coup[0], coup[1], joueurWhite.getSymbole());

                                colonne = (char) ('A' + coup[1]);
                                ligne = Integer.toString(coup[0] + 1);
                                coupJoueBot = colonne + ligne;
                            }else{
                                coupJoueBot = bot.jouer(plateau, joueurWhite.getSymbole());
                            }
                        }

                        if (!coupJoueBot.equals(""))
                            System.out.println("=" + numCommande + " " + coupJoueBot);
                        break;
                    case "quit" :
                        System.out.println("="+numCommande);
                        plateau.quit();
                        PartieContinue = false;
                        break;
                    default:
                        System.out.println("? unknown command");
                        System.out.println("Saissisez 'help' pour plus d'information");
                        continue;

                }
                numCommande = (newNumCommande == numCommande) ? newNumCommande + 1 : newNumCommande;
            }else{
                System.out.println("? le numero de la commande doit superieur au precedent");
                System.out.println("Saissisez 'help' pour plus d'information");
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