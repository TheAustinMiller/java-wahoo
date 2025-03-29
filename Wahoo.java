import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Wahoo {
    private ArrayList<Card[]> hands;
    private final ArrayList<Card[]> finalHands;
    private Stack<Card> stack;
    private Deck deck;
    private int pass;
    private int winner = -1;
    private int loser = -1;

    public Wahoo(int h) {
        deck = new Deck();
        hands = new ArrayList<>();
        pass = 0;

        // Initialize the hands
        for (int i = 0; i < h; i++) {
            Card[] hand = new Card[5];
            hands.add(hand);
        }

        // Draw cards to the hands
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < hands.size(); j++) {
                hands.get(j)[i] = deck.drawCard(); // Fixed indexing issue
            }
        }

        // Sort the hands
        for (int i = 0; i < h; i++) {
            hands.set(i, insertionSort(hands.get(i)));
        }

        // Create a deep copy of the initial hands for the finalHands
        finalHands = new ArrayList<>();
        for (Card[] hand : hands) {
            Card[] handCopy = new Card[hand.length];
            System.arraycopy(hand, 0, handCopy, 0, hand.length);
            finalHands.add(handCopy);
        }

        stack = new Stack<Card>();
        stack.push(deck.drawCard()); // start the game

        play(h);
    }

    public void play(int h) {
        int turn = 0;
        boolean gameOver = false;

        while (!gameOver) {
            int playerIndex = turn % h;
            Card[] playerHand = hands.get(playerIndex);
            Card topCard = stack.peek();

            // Find the highest valid card to play
            Card bestCard = null;
            int bestCardIndex = -1;
            int highestValue = -1;

            for (int i = 0; i < playerHand.length; i++) {
                if (playerHand[i] != null && isValidMove(playerHand[i], topCard)) {
                    if (playerHand[i].getVal() > highestValue) {
                        highestValue = playerHand[i].getVal();
                        bestCard = playerHand[i];
                        bestCardIndex = i;
                    }
                }
            }

            if (bestCard != null) {
                // Play the highest valid card
                stack.push(bestCard);
                playerHand[bestCardIndex] = null;
                pass = 0; // Reset pass counter

                // If rank matches, change to the most advantageous suit for the player
                if (bestCard.getRank().equals(topCard.getRank())) {
                    char newSuit = 'X';
                    for (Card card : playerHand) {
                        if (card != null) {
                            newSuit = card.getSuit();
                            break;
                        }
                    }

                    // If no cards left or no preference
                    if (newSuit == 'X') {
                        newSuit = '♥';
                    }

                    bestCard.setSuit(newSuit);
                }

                // Check if player has won
                boolean handEmpty = true;
                for (Card card : playerHand) {
                    if (card != null) {
                        handEmpty = false;
                        break;
                    }
                }

                if (handEmpty) {
                    winner = playerIndex;
                    gameOver = true;
                    findLoser(h);
                }
            } else {
                pass++;

                // Check if all players have passed
                if (pass == h) {
                    gameOver = true;
                    findLoser(h);
                }
            }

            turn++;
        }
    }

    public void findLoser(int h) {
        int[] leftoverVal = new int[h];
        for (int i = 0; i < h; i++) {
            leftoverVal[i] = getSum(i);
        }
        int largest = 0;
        for (int i = 0; i < leftoverVal.length; i++) {
            if (leftoverVal[i] > largest) {
                loser = i;
                largest = leftoverVal[i];
            }
        }
    }

    public int getSum(int index) {
        int sum = 0;
        for (int i = 0; i < hands.get(index).length; i++) {
            if (hands.get(index)[i] != null) {
                sum += hands.get(index)[i].getVal();
            }
        }
        return sum;
    }

    public Card[] getWinningHand() {
        return winner != -1 ? finalHands.get(winner) : null;
    }

    public Card[] getLosingHand() {
        return loser != -1 ? finalHands.get(loser) : null;
    }

    public int getWinner() {
        return winner;
    }

    private boolean isValidMove(Card card, Card topCard) {
        return card.getSuit() == (topCard.getSuit()) || card.getRank().equals(topCard.getRank());
    }

    private static Card[] insertionSort(Card[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            Card key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j].getVal() > key.getVal()) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }

        return arr;
    }
}
