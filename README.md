# Gomoku
Projet Gomoku développer en Java.

Assurant les règles de bases du Gomoku, intégrant aussi le protocol GTP.

Membres de l'équipe : 
- Phuong Nguyen 204
- Elsa Ferry 204
- Kenza Brahimi 204
- Shihong  203
- Jordan Mvotio 203

Fonctionnalités :

- Jouer entre deux Humains
- Jouer entre un Humain et un bot naif
- Jouer entre un Humain et un bot utilisant l'algorithme minmax
- Jouer entre deux bots un naif et l'autre utilisant l'algorithme minmax
- Choix de la taille du plateau (boardsize)
- Effacer le plateau (clear_board)
- Quitter le jeu (quit)
- Afficher le plateau (showboard)
- Play
- Genmove black
- Genmove white
- Genmove black\white (1,2,3...) pour le MiniMax
- Message d'erreur
- Message victoire

Nous pensons avoir fait toutes les fonctionnalités nécessaires pour le jeu de Gomoku.

Fonctionnalités à ajouter le score et une meilleure interface du jeu.

A noter :
Dans le GTP, le joueur peut jouer plusieurs fois d'affiler la même couleur mais dans les parties il ne pourra pas. 
Il peut aussi taper les commandes avec le numéro au début ou sans.
Le BotMinimax est performant sur les petits plateaux et la profondeur conseillé est entre 3 et 5 pour les plateaux petits(3-8) et moyens(10-12)


A améliorer :

Le BotMinimax n'est pas très performant pour les grands plateau et dans le GTP il choisit la facilité(choisit que la première ligne)

Pour les Test: 

On a réalisé deux tests : le TestPlateau qui comprend 7 tests et le TestJouer qui en comprend 5 en plus du GTP. Tout cela fonctionne.

Bilan du projet: 

Ce fut un projet très intéressant et amusant à réaliser car le Gomoku a permis de travailler sur plusieurs aspects du développement. 
La mise en place des Bots, qu'il s'agisse du BotMinimax et BotNaif. Ils nous ont permis de comprendre des algorithmes ce qui ajoute
une dimension d'intelligence artificiel au jeu. Même si le BotMinimax est pas très bien réussi et opti, quant à celui du BotNaif 
est bien réussi avec la commande genmove pour jouer une case aléatoire. Intégré un protocole GTP à été une étape importante du projet car on voulait respecter au maximum le protocole proposé et on pense qu'on a bien réussis avec le numéro de commande au debut puis les actions du joueurs avec un message d'erreur pour chaque commande tapé fausse et on a ajouter une commande help pour aider au joueur qu'est ce qu'il faut taper. Le joueur peut aussi taper le numéro de commande ou le faire sans s'il le met pas cela affichera quand même = numéro de commande en implémentant au précédent et l'action du joueur sera quand même lancer, permettant au joueur à quel tour il en est et pour pas se perdre. Il est interdit taper un numéro de commande inférieur au précédent cependant il est possible de sauter des numéros, par exemple passer de 1 à 3. Cela à été compliqué de le produire et on a plutôt bien réussis. Les tests unitaires ont été réussis dans l'ensemble. Même si nous avons fait de notre mieux, nous pensons pourvoir encore organiser mieux notre code afin de le rendre plus lisible et compréhensible .On a bien aimé faire ce projet, il nous permis d'acquérir plusieurs compétences.
