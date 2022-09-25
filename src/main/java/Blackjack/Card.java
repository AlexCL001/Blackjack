package Blackjack;

public class Card {
    private final String suite; // Spades", "Hearts", "Clubs" ou "Diamonds"
    private final String rank;  //2, 3, 4, ..., 10,
    private final int points; 
    
    public Card(String suite, String rank, int points) {
        this.suite = suite;
        this.rank = rank;
        this.points = points;
    }
    
    public int getPoints() {
        return points;
    }

	//Retourne true si la carte est un Ace
    public boolean isAce() {
        if(rank.equalsIgnoreCase("ace"))
            return true;
        else
            return false;
    }
    
	//Retourne une chaine de caractère par exemple : 3 of Hearts
    public String display() {
        return this.rank + " of " + this.suite;
    }
}