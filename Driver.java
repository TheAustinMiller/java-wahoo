import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Driver {

    private static final int HANDED = 6;
    private static final int TIMES = 10000;
    private static ArrayList<Card[]> winningHands;
    private static double valSum;
    private static double suitSum;
    private static double aces;

    public static void main(String[] args) {
        winningHands = new ArrayList<Card[]>();
        valSum = 0;
        suitSum = 0;
        aces = 0;

        for (int i = 0; i < TIMES; i++) {
            Wahoo w = new Wahoo(HANDED);
            Card[] hand = w.getWinningHand();
            if (hand != null) { winningHands.add(hand); }
        }

        averageVal();
        averageSuit();
        getAces();
        System.out.println("Average value of a card in the winning hand: " + valSum);
        System.out.println("Average amount of suits in the winning hand: " + suitSum);
        System.out.println("Average aces the winning hand gets dealt: " + aces);
    }

    public static void getAces() {
        int totalAces = 0;
        int handCount = 0;

        for (Card[] hand : winningHands) {
            int aceCount = 0;

            for (Card card : hand) {
                if (card != null && card.getRank().equals("A")) {
                    aceCount++;
                }
            }

            totalAces += aceCount;
            handCount++;
        }

        aces = handCount > 0 ? (double) totalAces / handCount : 0;
    }

    private static void averageVal() {
        int handCount = 0;

        for (Card[] hand : winningHands) {
            double handSum = 0;
            int cardCount = 0;

            for (Card card : hand) {
                handSum += card.getVal();
                cardCount++;
            }

            if (cardCount > 0) {
                valSum += handSum / cardCount;
                handCount++;
            }
        }
        if (handCount > 0) {
            valSum /= handCount;
        }
    }

    private static void averageSuit() {

        for (Card[] hand : winningHands) {
            HashSet<Character> uniqueSuits = new HashSet<>();
            for (Card card : hand) {
                if (card != null) {
                    uniqueSuits.add(card.getSuit());
                }
            }
            suitSum += uniqueSuits.size();
        }

        suitSum /= winningHands.size();
    }
}
