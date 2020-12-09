# Gyromite
## Description du jeu : 
[https://nintendo.fandom.com/wiki/Gyromite](https://nintendo.fandom.com/wiki/Gyromite "Descrption du jeu")
## Résumé :
Vous contrôlez le Professeur Raoult qui se déplace pour ramasser les gélules d'[hydroxychloroquine](https://fr.wikipedia.org/wiki/Hydroxychloroquine "hydroxychloroquine") dans son laboratoire, vu en 2D de côté. Le laboratoire est composé d’un ensemble de niveaux. Le scientifique peut se déplacer à gauche, à droite, et grimper ou descendre à l’aide de cordes. Un niveau peut contenir des piliers (bleus et rouges) qui peuvent monter ou descendre lorsque le joueur appuie sur une touche spécifique (respectivement les touches `e` et `a`). Enfin, le professeur doit éviter les ennemis, les Macron qui sont stoppés lorsqu'ils rencontrent un gilet-jaune. L’objectif est donc de récupérer toutes les gélules, en évitant les ennemis, tout en manipulant les piliers pour créer des chemins si nécessaire.
## Précisions concernant l’implémentation :
* Le plateau est représenté par une grille de cases du côté de la vue et une grille de cases du côté du modèle. Les murs et sols sont matérialisés par des cases pleines, les couloirs par des cases vides.
* Les mouvements sont discrétisés : on avance case par case, pas d’autres positions intermédiaires (pixel, etc.). Le temps est discrétisé : pour chaque entité, l’entité se déplace d’une seule case par pas de temps.
* Les déplacements des Entités sont gérés au tour-par-tour par le biais des classes `RéalisateurDeDéplacement` et `Ordonnanceur`. L’`Ordonnanceur` déclenche les `RéalisateurDeDéplacements` de manière séquentielle.
* Une instance de RéalisateurDeDéplacement représente le déplacement d’une ou plusieurs Entités, par le biais d’une méthode `realiserDeplacement()`. Cette méthode (à implémenter pour les classes filles de `Deplacement`) appelle la méthode `avancerDirectionChoisie(Direction)` sur les instances d’EntiteDynamique qui sont gérées par ce déplacement (par exemple, le Professeur ou toutes les cases d’une Colonne). En interne, `avancerDirectionChoisie(Direction)` utilise la méthode `deplacerEntite(Entite, Direction)` dans Jeu. Nous vousinvitons à regarder le diagramme UML sur Claroline pour mieux comprendre les relations entre ces différentes classes;
* Les Colonnes sont composées de N cases (N > 1) ; lorsqu’une Colonne se déplace, toutes ses cases se déplacent N-1 fois (à raison de 1 déplacement / tick de temps). Les Colonnes sont uniquement verticales. Illustration (vue horizontale)du déplacement (état initial à t → temps t+1 → temps t+2) :_ x x x _ _ _  → _ _ x x x _ _  → _ _ _ x x x _ - Concernant les Macron (ennemis), leur déplacement est principalement horizontal mais ils peuvent se déplacerà l’aide des cordes. En revanche, les Macron ne peuvent pas tomber dans le vide : ils font demi-tour à l’extrémité d’une plateforme.