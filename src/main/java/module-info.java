module Blackjack {
    requires javafx.controls;
    requires javafx.fxml;


    opens Blackjack to javafx.fxml;
    exports Blackjack;
}