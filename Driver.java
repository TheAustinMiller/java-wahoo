import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Driver {

    private static final int HANDED = 6;
    private static final int TIMES = 10000;
    private static ArrayList<Card[]> winningHands;
    private static ArrayList<Card[]> losingHands;


    public static void main(String[] args) {
        winningHands = new ArrayList<Card[]>();

        for (int i = 0; i < TIMES; i++) {
            Wahoo w = new Wahoo(HANDED);
            Card[] winHand = w.getWinningHand();
            Card[] loseHand = w.getLosingHand();
            if (winHand != null) { winningHands.add(winHand); }
            if (loseHand != null) { winningHands.add(loseHand); }
        }

        System.out.println("Average value of a card in the winning hand: " + averageVal(winningHands));
        System.out.println("Average amount of suits in the winning hand: " + averageSuit(winningHands));
        System.out.println("Average aces the winning hand gets dealt: " + getAces(winningHands));
    }

    public static double getAces(ArrayList<Card[]> winningHands) {
        double totalAces = 0;
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

        return handCount > 0 ? (double) totalAces / handCount : 0;
    }

    private static double averageVal(ArrayList<Card[]> winningHands) {
        int handCount = 0;
        double valSum = 0;
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
        return valSum;
    }

    private static double averageSuit(ArrayList<Card[]> winningHands) {
        double suitSum = 0;
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
        return suitSum;
    }
}
