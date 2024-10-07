package com.glossy.evolchat.model;

public class GameResult {
    private final String winner;
    private final int playerScore;
    private final int dealerScore;
    private final boolean playerHasBlackjack;
    private final boolean dealerHasBlackjack;

    public GameResult(String winner, int playerScore, int dealerScore, boolean playerHasBlackjack, boolean dealerHasBlackjack) {
        this.winner = winner;
        this.playerScore = playerScore;
        this.dealerScore = dealerScore;
        this.playerHasBlackjack = playerHasBlackjack;
        this.dealerHasBlackjack = dealerHasBlackjack;
    }

    // Getters and toString() method

    public String getWinner() {
        return winner;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getDealerScore() {
        return dealerScore;
    }

    public boolean isPlayerHasBlackjack() {
        return playerHasBlackjack;
    }

    public boolean isDealerHasBlackjack() {
        return dealerHasBlackjack;
    }

    @Override
    public String toString() {
        return "GameResult{" +
                "winner='" + winner + '\'' +
                ", playerScore=" + playerScore +
                ", dealerScore=" + dealerScore +
                ", playerHasBlackjack=" + playerHasBlackjack +
                ", dealerHasBlackjack=" + dealerHasBlackjack +
                '}';
    }
}
