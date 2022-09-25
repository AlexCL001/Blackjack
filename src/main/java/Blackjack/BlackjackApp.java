package Blackjack;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;

import java.io.IOException;


public class BlackjackApp extends Application {

    private static BlackjackGame game;

    Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {

        //Création de l'interface graphique
        primaryStage = stage;
        primaryStage.setTitle("Blackjack");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        Label moneyLabel = new Label("Money:");
        grid.add(moneyLabel, 0, 0);

        TextField moneyInput = new TextField();
        grid.add(moneyInput, 1, 0);

        Label betLabel = new Label("Bet:");
        grid.add(betLabel, 0, 1);

        TextField betInput = new TextField();
        grid.add(betInput, 1, 1);

        Label dealerLabel = new Label("DEALER");
        grid.add(dealerLabel, 0, 2);

        Label dealerCardsLabel = new Label("Cards:");
        grid.add(dealerCardsLabel, 0, 3);

        ListView<String> dealerListView = new ListView<>();
        dealerListView.getItems().addAll();
        grid.add(dealerListView, 1, 3);

        Label dealerPointsLabel = new Label("Points:");
        grid.add(dealerPointsLabel, 0, 4);

        TextField dealerPointsInput = new TextField();
        grid.add(dealerPointsInput, 1, 4);

        Label playerLabel = new Label("YOU");
        grid.add(playerLabel, 0, 5);

        Label playerCardsLabel = new Label("Cards:");
        grid.add(playerCardsLabel, 0, 6);

        ListView<String> playerListView = new ListView<>();
        playerListView.getItems().addAll();
        grid.add(playerListView, 1, 6);

        Label playerPointsLabel = new Label("Points:");
        grid.add(playerPointsLabel, 0, 7);

        TextField playerPointsInput = new TextField();
        grid.add(playerPointsInput, 1, 7);

        Button hitButton = new Button("Hit");
        grid.add(hitButton, 0, 8);

        Button standButton = new Button("Stand");
        grid.add(standButton, 1, 8);

        Label resultLabel = new Label("RESULT:");
        grid.add(resultLabel, 0, 9);

        TextField resultInput = new TextField();
        grid.add(resultInput, 1, 9);

        Button playButton = new Button("Play");
        grid.add(playButton, 0, 10);

        Button exitButton = new Button("Exit");
        grid.add(exitButton, 1, 10);

        //Crée la scène et l'affiche
        Scene scene = new Scene(grid, 350, 450);
        primaryStage.setScene(scene);
        primaryStage.show();

        hitButton.setDisable(true);
        standButton.setDisable(true);

        //Blackjack en dessous ici
        //****************************************************************
        game = new BlackjackGame();

        //Initialise un montant de 100$ comme banque disponible
        game.loadMoney();

        //Met le montant disponible au début du jeu dans moneyInput
        moneyInput.setText(String.valueOf(game.getTotalMoney()));

        //Met un placeHolder dans le betInput
        betInput.setPromptText("Place a bet over $10 here");

        playButton.setOnAction(e -> {

            //S'occupe de supprimer les éléments de l'ancienne partie
            resultInput.clear();
            dealerListView.getItems().clear();
            playerListView.getItems().clear();
            dealerPointsInput.clear();
            playerPointsInput.clear();


            //Fait partir le jeu si un bet valide est entré
            if (Integer.parseInt(betInput.getText()) >= 10 && Integer.parseInt(betInput.getText()) <= game.getTotalMoney()) {

                //Distribue les cartes au joueur et au dealer
                game.deal();

                //Enregistre le montant misé
                game.setBet(Integer.parseInt(betInput.getText()));

                //Montre la première carte du dealer
                dealerListView.getItems().addAll(game.getDealerShowCard().display());

                //Montre les premières cartes du player
                for (Card card : game.getPlayerHand().getCards()) {
                    playerListView.getItems().add(String.valueOf(card.display()));
                }

                //Affiche les points initiale du player
                playerPointsInput.setText(String.valueOf(game.getPlayerHand().getPoints()));

                playButton.setDisable(true);
                exitButton.setDisable(true);

                hitButton.setDisable(false);
                standButton.setDisable(false);

                //Effectue l'événement du standButton si le joueur commence avec un blackjack
                if (game.getPlayerHand().isBlackjack()){ standButton.fire(); }

            } else {

                //Alerte en cas de mise invalide dans betInput
                Alert betError = new Alert(Alert.AlertType.INFORMATION);
                betError.setContentText("The bet amount is invalid!\nBet must be over $10 or have enough fund.");
                betError.show();

            }

        });

        hitButton.setOnAction(e -> {

            //Tire une nouvelle carte au joueur
            game.hit();

            //Efface les cartes avant de les afficher à nouveau
            playerListView.getItems().clear();

            //Affiche les cartes avec la nouvelle pige
            for (Card card : game.getPlayerHand().getCards()) {
                playerListView.getItems().add(String.valueOf(card.display()));
            }

            //Ajuste le nombre de point du player
            playerPointsInput.clear();
            playerPointsInput.setText(String.valueOf(game.getPlayerHand().getPoints()));

            //Effectue la fonction du standButton afin de terminer la partie si les cartes du joueur dépasse 21.
            if (game.getPlayerHand().isBust()) { standButton.fire(); }

        });

        standButton.setOnAction(e -> {

            //Distribue les cartes du dealer si le joueur n'as pas de blackjack
            if (!game.getPlayerHand().isBlackjack() && game.getDealerHand().getPoints() < game.getPlayerHand().getPoints()) { game.stand(); }

            //Affiche les cartes du dealer
            dealerListView.getItems().clear();
            for (Card card : game.getDealerHand().getCards()) {
                dealerListView.getItems().add(String.valueOf(card.display()));
            }

            //Affiche les points du dealer
            dealerPointsInput.setText(String.valueOf(game.getDealerHand().getPoints()));

            //Regarde et calcul tout selon si tu gagne, perd ou a un Blackjack.
            if (game.isPush()) {
                resultInput.setText("Push!");
            } else if (game.getPlayerHand().isBlackjack()) {
                resultInput.setText("BLACKJACK! You win!");
                game.addBlackjackToTotal();
            } else if (game.playerWins()) {
                resultInput.setText("You win!");
                game.addBetToTotal();
            } else {
                resultInput.setText("Sorry, you lose.");
                game.subtractBetFromTotal();
            }

            //Gère et affiche l'argent disponible
            moneyInput.clear();
            if (game.getTotalMoney() < 10) {
                game.loadMoney();
            }
            moneyInput.setText(String.valueOf(game.getTotalMoney()));

            //Vide les cartes du jeu
            game.getPlayerHand().resetHand();
            game.getDealerHand().resetHand();

            playButton.setDisable(false);
            exitButton.setDisable(false);

            hitButton.setDisable(true);
            standButton.setDisable(true);

        });

        //Permet de quitter le jeu
        exitButton.setOnAction(e -> primaryStage.close());

    }

    public static void main(String[] args) { launch(); }

}