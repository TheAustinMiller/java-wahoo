import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> deck;
    private static final char[] suits = {'♥', '♦', '♠', '♣'};
    private static final String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};


    public Deck() {
        deck = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                deck.add(new Card(suits[i], ranks[j]));
            }
        }
        shuffle();
    }

    public Card drawCard() {
        Card c = deck.get(0);
        deck.remove(0);
        return c;
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }
}
