package Blackjack;

public class Deck {
    private Card[] deck;
    private int currentCardIndex;
    
	//Stocke les cartes dans Card[ ] deck et ensuite il appelle la fonction shuflleDeck().
    public Deck() {
        this.deck = new Card[52];
        this.currentCardIndex = 0;

        String[] allRanks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        String[] allSuites = { "Clubs", "Diamonds", "Hearts", "Spades" };

        int counter = 0;

        for(String suite : allSuites){
            for(String rank : allRanks){
                int points = switch(rank) {
                    case "Jack", "Queen", "King" -> 10;
                    case "Ace" -> 11;
                    default -> Integer.parseInt(rank);
                };

                deck[counter] = new Card(suite, rank, points);
                counter++;
            }
        }
        shuffleDeck();

    }
    
	//Mélange les cartes
    private void shuffleDeck() {
       for (int i = 51; i > 0; i--) {
            int j = (int)(Math.random() * i);
            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
       }
    }

    //Fonction permettant de piger une carte
    public Card drawCard() {
        if(this.currentCardIndex == 51) {
            Card currCard = this.deck[this.currentCardIndex];
            shuffleDeck();
            return currCard;
        }
        else {
            return this.deck[this.currentCardIndex++];
        }
    }
}
