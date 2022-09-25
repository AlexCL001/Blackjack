package Blackjack;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand;
    private String user; //Soit le joueur, soit le dealer. Dépend selon comment on appelle le constructeur et le paramètre quand on l'utilise
    public Hand(String user) {
        this.user = user;
        hand = new ArrayList<>();
    }

    //Retourne le tableau hand
    public ArrayList<Card> getCards() {
      return this.hand;
    }

    //Retourne la somme des cartes dans le tableau hand. Si la somme est >21, il faut recompter les cartes
    // pour vérifier s'il y a un ACE. Si oui on le considère comme 1, sinon on ajoute la somme des points.
    public int getPoints() {
        int total = 0;
        for (Card card : hand)
            total = total + card.getPoints();

        if(total > 21) {
            total = 0;
            for(Card card : hand){
                if(card.isAce())
                    total = total + 1;
                else
                    total = total + card.getPoints();
            }
        }
        return total;
    }

    //Ajoute une carte au tableau
    public void addCard(Card card) {
        this.hand.add(card);
    }

    //Retourne true si la somme des deux cartes est égale à 21. False sinon
    public boolean isBlackjack() {
        if(this.getPoints() == 21 && this.hand.size() == 2)
            return true;
        else
            return false;
    }

    //Retourne true si la somme des points a une valeur supérieure à 21. False sinon.
    public boolean isBust() {
        if(this.getPoints() > 21)
            return true;
        else
            return false;
    }

    //Supprimer les cartes du ArrayList pour jouer à nouveau
    public void resetHand() {
        this.hand.removeAll(this.hand);
    }

}