public class Card {
    private char suit;
    private String rank;
    private int val;

    public Card(char suit, String rank) {
        this.suit = suit;
        this.rank = rank;
        if (rank.equals("J") || rank.equals("Q") || rank.equals("K")) {
            val = 10;
        } else if (rank.equals("A")) {
            val = 15;
        } else {
            val = Integer.parseInt(rank);
        }
    }

    public char getSuit() { return suit; }
    public void setSuit(char suit) { this.suit = suit; } // in case of wild card
    public String getRank() { return rank; }
    public int getVal() { return val; }
    public void setVal(int val) { this.val = val; }

    @Override
    public String toString() {
        return rank + "" + suit;
    }
}
