import java.util.*;

public class Hand {
    String hand;
    int bid;
    long priority;

    public Hand(String hand, int bid) {
        this.hand = hand;
        this.bid = bid;
        this.priority = getPriority(hand);
    }

    /*
     * 
     * Five of a kind, where all five cards have the same label: AAAAA
     * Four of a kind, where four cards have the same label and one card has a different label: AA8AA
     * Full house, where three cards have the same label, and the remaining two cards share a different label: 23332
     * Three of a kind, where three cards have the same label, and the remaining two cards are each different from any other card in the hand: TTT98
     * Two pair, where two cards share one label, two other cards share a second label, and the remaining card has a third label: 23432
     * One pair, where two cards share one label, and the other three cards have a different label from the pair and each other: A23A4
     * High card, where all cards' labels are distinct: 23456
     */

    private static long getPriority(String hand) {
        Map<Character, Integer> cardWeightMap = new HashMap<>();
        cardWeightMap.put('A', 14);
        cardWeightMap.put('K', 13);
        cardWeightMap.put('Q', 12);
        cardWeightMap.put('J', 1); // part 2 - J is now 1
        cardWeightMap.put('T', 10);
        cardWeightMap.put('9', 9);
        cardWeightMap.put('8', 8);
        cardWeightMap.put('7', 7);
        cardWeightMap.put('6', 6);
        cardWeightMap.put('5', 5);
        cardWeightMap.put('4', 4);
        cardWeightMap.put('3', 3);
        cardWeightMap.put('2', 2);

        Map<Character, Integer> cardCountMap = new HashMap<>();

        for (int i = 0; i < hand.length(); i++) {
            char c = hand.charAt(i);
            cardCountMap.put(c, cardCountMap.getOrDefault(c, 0) + 1);
        }

        int jokerCount = cardCountMap.getOrDefault('J', 0);
        List<Integer> cardCountList = new ArrayList<>(cardCountMap.values());
        cardCountList.remove(Integer.valueOf(jokerCount));
        Collections.sort(cardCountList, Collections.reverseOrder());
        if (cardCountList.size() > 0)
            cardCountList.set(0, cardCountList.get(0) + jokerCount);
        int size = Math.max(1, cardCountList.size());

        long priority = 0;
        
        switch(size) {
            case 1:
                priority =  6;
                break;
            case 2:
                if (cardCountList.get(0) == 4)
                    priority =  5;
                else if (cardCountList.get(0) == 3 && cardCountList.get(1) == 2) {
                    priority =  4;
                break;
                }
            case 3:
                if (cardCountList.get(0) == 3)
                    priority =  3;
                else if (cardCountList.get(0) == 2 && cardCountList.get(1) == 2)
                    priority =  2;
                break;
            case 4:
                priority =  1;
                break;
            case 5:
                priority =  0;
                break;
            default:
                //
        }

        priority *= 10000000000L;
        long multiplier = 100000000L;

        for (int i = 0; i < hand.length(); i++) {
            priority += cardWeightMap.get(hand.charAt(i)) * multiplier;
            multiplier /= 100;
        }

        return priority;
    }
}
