package Blackjack;

public class BlackjackGame {
    private final Hand playerHand;
    private final Hand dealerHand;
    private final Deck deck;
    private double betAmount;
    private double totalMoney;
    
	//Initialise deck, playerHand et dealerHand
    public BlackjackGame() {

        //Crée 52 cartes non ordonnées
        this.deck = new Deck();

        this.playerHand = new Hand("player");
        this.dealerHand = new Hand("dealer");

    } 
    
    public void loadMoney() {
        totalMoney = 100;
    }
    
	//Retourne le montant total dans la banque
    public double getTotalMoney() {
        return this.totalMoney;
    }
    
	//Initialise le montant de la mise que l'on fait
    public void setBet(double amt) {
        this.betAmount = amt;
    }
    
	//Distribue deux cartes pour le joueur (playerHand) et deux cartes pour le dealer (dealerHand)
    public void deal() {
        playerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());

        dealerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
    }
    
	//Ajoute une carte aux mains du player quand il décide de hit
    public void hit() {
        playerHand.addCard(deck.drawCard());
    }
    
	//Ajoute des cartes aux mains du dealer tant que la somme des points dont il dispose est moins que 17
    public void stand() {
        while (dealerHand.getPoints() < 17 && !playerHand.isBust()) {
            dealerHand.addCard(deck.drawCard());
        }
    }

	//Retourne la deuxième carte dans la main du dealer
    public Card getDealerShowCard() {
        return dealerHand.getCards().get(1);
    }
    
	//Retourne dealerHand
    public Hand getDealerHand() {
        return this.dealerHand;
    }

	//Retourne playerHand
    public Hand getPlayerHand() {
        return this.playerHand;
    }

    
	//Retourne true si les points dans la main du joueur est inférieur ou égale 21 et si ses points sont égales aux points du dealer. False sinon.
    public boolean isPush() {
        if (!getPlayerHand().isBust() && getDealerHand().getPoints() == getPlayerHand().getPoints())
            return true;
        else
            return false;
    }
	
	//Retourne true si le player gagne. False sinon.
    public boolean playerWins() {
        if(!getPlayerHand().isBust() && getPlayerHand().getPoints() > getDealerHand().getPoints() && !getDealerHand().isBust())
            return true;
        else if (!getPlayerHand().isBust() && getDealerHand().isBust()) {
            return true;
        } else
            return false;
    }
    
	//Ajoute le montant misé au montant total
    public void addBetToTotal() {
        this.totalMoney += this.betAmount;
    }
    
	//Ajoute le montant misé selon 3:2 au montant total dans le cas de blackjack
    public void addBlackjackToTotal() {
        this.totalMoney += this.betAmount * 1.5;
    }
    
	//Soustraie le montant du bet perdu du montant total
    public void subtractBetFromTotal() {
        this.totalMoney -= this.betAmount;
    }

}
