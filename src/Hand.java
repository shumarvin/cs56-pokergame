import java.util.*;

/**
   Class that represents a hand of 5 Cards.
*/
public class Hand extends ArrayList<Card>{

    /**
       No arg constructor for Hand
    */
    public Hand(){
	super(1);
    }
    /**
       Constructor that sets a hand given 5 Cards as arguments.
       @param a Card a
       @param b Card b
       @param c Card c
       @param d Card d
       @param e Card e
    */	
    public Hand(Card a, Card b, Card c, Card d, Card e){
	super(5);
	this.add(a); this.add(b); this.add(c); this.add(d); this.add(e);
    }

    public Hand(Card a, Card b){
	super(2);
	this.add(a); this.add(b); 
    }

    public Hand(Card a, Card b, Card c, Card d, Card e, Card f, Card g){
	super(7);
	this.add(a); this.add(b); this.add(c); this.add(d); this.add(e);
	this.add(f); this.add(g);
    }
        
    //copy constructor
    public Hand copyHand(Hand h){
	Hand handCopy = new Hand();
	for(Card c: h){
	    handCopy.add(c);
	}
	return handCopy;
    }

    /**
       Constructor that creates a hand of size handSize
       @param deck deck of Cards
       @param handSize the size of the hand
    */
    public Hand(Deck deck, int handSize){
	super(handSize);
	deck.dealCards(this, handSize);
    }

    /**
       Adds a specified number of cards to the hand
       @param deck deck of Cards
       @param numCardsToAdd the number of cards to add to the hand
    */	
    public void draw(Deck deck, int numCardsToAdd){
	deck.dealCards(this, numCardsToAdd);
    }

    /**
       Returns the Card with the highest value.
    */
    public Card getHighCard(){
	Card max=this.get(0);
	for(Card c:this){
	    if(c.getValue()>max.getValue())
		max=c;
	}
	return max; 
    }

    /**
       Returns the int value of the highest Card.
    */
    public int getHighCardValue(){
	int max=0;
	for(Card c:this)
	    {
		if(c.getValue()>max)
		    max=c.getValue();
	    }
	return max;
    }

    /**
       Returns an ArrayList of Card int values in a numerical order.
    */
    public ArrayList<Integer> sortHand(){
	ArrayList<Integer> sortedHand=new ArrayList<Integer>();
	for(int i=0;i<this.size();i++)
	    {
		sortedHand.add(this.get(i).getValue());
	    }
	Collections.sort(sortedHand);
	return sortedHand;
    }
	
    /**
       Returns boolean for if the hand is a straight flush
    */	
    public boolean isStraightFlush(){
	int straightFlushCounter=0;
	if(this.isStraight())
	    {
		for(int i=0;i<4;i++)
		    {
			if(this.get(i).getSuit()==this.get(i+1).getSuit())
			    straightFlushCounter++;
		    }
		if(straightFlushCounter==4)
		    return true;
		else
		    return false;
	    }
	else
	    return false;
    }

    /**
       Returns boolean for if the hand has a four of a kind.
    */		
    public boolean isFourOfAKind(){
	ArrayList<Integer> sortedHand=this.sortHand();
	for(int i = 0;i < sortedHand.size();i++)
	    if(Collections.frequency(sortedHand, sortedHand.get(i)) == 4)
                return true;
	
        return false;
    }

    /**
       Returns boolean for if the hand is a full house.
    */			
    public boolean isFullHouse(){
	ArrayList<Integer> sortedHand=this.sortHand();
	boolean doubleCounter = false;
	boolean tripleCounter = false;
	
	for(int i = 0;i < sortedHand.size();i++){
            if(Collections.frequency(sortedHand, sortedHand.get(i)) == 2)
                doubleCounter = true;
	    if(Collections.frequency(sortedHand, sortedHand.get(i)) == 3)
                tripleCounter = true;
	}
	
	return doubleCounter && tripleCounter;
    }

    /**
       Returns boolean for if the hand is a flush.
    */		
    public boolean isFlush(){
	if(this.isStraightFlush())
	    return false;
	int flushCounter=0;
	for(int i=0;i<4;i++)
	    {
		if(this.get(i).getSuit()==this.get(i+1).getSuit())
		    {
			flushCounter++;
		    }
	    }
	if(flushCounter==4)
	    return true;
	else
	    return false;
    }

    /**
       Returns boolean for if the hand is a straight.
    */		
    public boolean isStraight(){
	ArrayList<Integer> sortedHand=this.sortHand();
	int straightCounter=0;
	for(int i=0;i<4;i++)
	    {
		if(sortedHand.get(i)==(sortedHand.get(i+1)-1))
		    {
			straightCounter++;
		    }
	    }
	return(straightCounter==4);
    }

    /**
       Returns boolean for if the hand has three of a kind.
    */			
    public boolean isThreeOfAKind(){
	if(this.isFullHouse())
	    return false;
        
        ArrayList<Integer> sortedHand=this.sortHand();
	for(int i = 0;i < sortedHand.size();i++)
	    if(Collections.frequency(sortedHand, sortedHand.get(i)) == 3)
                return true;
        return false;
		
    }
	
    /**
       Returns boolean for if the hand has two pairs.
    */		
    public boolean isTwoPair(){
	ArrayList<Integer> sortedHand=this.sortHand();
	int numPairs = 0;
	
	for(int i = 0;i < sortedHand.size();i++){
            if(Collections.frequency(sortedHand, sortedHand.get(i)) == 2){
                numPairs++;
                i++;
            }
	}
	
	return numPairs == 2;
    }

    /**
       Returns boolean for if the hand has only one pair.
    */			
    public boolean isOnePair(){
        if(this.isFullHouse())
	    return false;
        
	ArrayList<Integer> sortedHand=this.sortHand();
	int numPairs = 0;
	
	for(int i = 0;i < sortedHand.size();i++){
            if(Collections.frequency(sortedHand, sortedHand.get(i)) == 2){
                numPairs++;
                i++;
            }
	}
	
	return numPairs == 1;
    }
	
    /**
       Returns 1 if "this" hand is better than "otherHand" or returns 0
       if the opposite.
       @param otherHand hand to be compared
       Precondition: both hands must be of size 5
    */
    public int compareHands(Hand otherHand){
	int player1Value=0;
	int player2Value=0;
		
	if(this.isStraightFlush())
	    player1Value=8;
	else if(this.isFourOfAKind())
	    player1Value=7;
	else if(this.isFullHouse())
	    player1Value=6;
	else if(this.isFlush())
	    player1Value=5;
	else if(this.isStraight())
	    player1Value=4;
	else if(this.isThreeOfAKind())
	    player1Value=3;
	else if(this.isTwoPair())
	    player1Value=2;
	else if(this.isOnePair())	
	    player1Value=1;
	else
	    player1Value=0;
		
	if(otherHand.isStraightFlush())
	    player2Value=8;
	else if(otherHand.isFourOfAKind())
	    player2Value=7;
	else if(otherHand.isFullHouse())
	    player2Value=6;
	else if(otherHand.isFlush())
	    player2Value=5;
	else if(otherHand.isStraight())
	    player2Value=4;
	else if(otherHand.isThreeOfAKind())
	    player2Value=3;
	else if(otherHand.isTwoPair())
	    player2Value=2;
	else if(otherHand.isOnePair())	
	    player2Value=1;
	else
	    player2Value=0;
		
	if(player1Value>player2Value)
	    return 1;
	else if(player2Value>player1Value)
	    return 0;
	else
	    return this.sameHand(otherHand);
    }

    /**
       This method is used if both hands are of the same type(two pairs,
       full house, etc.) Returns 1 if "this" hand is better or 0 if otherwise.
       @param otherHand hand to be compared
    */	
    public int sameHand(Hand otherHand){
	ArrayList<Integer> sortedHand=new ArrayList<Integer>();
	sortedHand=this.sortHand();
	ArrayList<Integer> otherSortedHand=new ArrayList<Integer>();
	otherSortedHand=otherHand.sortHand();
		
	int handValue=0;
	int otherHandValue=0;
	int handPairIndex=0;
	int otherHandPairIndex=0;
		
	if(isOnePair()){
            for(int i = 0;i < sortedHand.size();i++){
                if(Collections.frequency(sortedHand, sortedHand.get(i)) == 2){
                    handValue = sortedHand.get(i);
                }
                if(Collections.frequency(otherSortedHand, otherSortedHand.get(i)) == 2){
                    otherHandValue = otherSortedHand.get(i);
                }
            }
            
            if(handValue>otherHandValue)
                return 1;
            else if(handValue<otherHandValue)
                return 0;
            else{
                return sameHandMethod(otherHand);
            }
	
        }
		
	else if(isTwoPair())
	    {
		Integer handCard=0; Integer otherHandCard=0;
		for(int i=0;i<4;i++)
		    {
			if(sortedHand.get(i)==sortedHand.get(i+1))
			    {
				if(sortedHand.get(i)>handValue){
				    handValue=sortedHand.get(i);
				    handCard=handValue;
				}
			    }
			remove(handCard);
			if(otherSortedHand.get(i)==otherSortedHand.get(i+1))
			    {
				if(otherSortedHand.get(i)>otherHandValue){
				    otherHandValue=otherSortedHand.get(i);
				    otherHandCard=otherHandValue;
				}
			    }
			remove(otherHandCard);
		    }
		if(handValue>otherHandValue)
		    return 1;
		else if(handValue<otherHandValue) 
		    return 0;
		//test second pair if first two same	
		else {
		    int secHandVal = handValue;
		    int secOtherHandVal = otherHandValue;
		    handValue = 0;
		    otherHandValue = 0;
                                
		    for(int i=0;i<4;i++)
			{
			    if(sortedHand.get(i)==sortedHand.get(i+1))
				{
				    if(sortedHand.get(i)>handValue && sortedHand.get(i)!= secHandVal){
					handValue=sortedHand.get(i);
					handCard=handValue;
				    }
				}
			    remove(handCard);
			    if(otherSortedHand.get(i)==otherSortedHand.get(i+1))
				{
				    if(otherSortedHand.get(i)>otherHandValue && otherSortedHand.get(i) != secOtherHandVal){
					otherHandValue=otherSortedHand.get(i);
					otherHandCard=otherHandValue;
				    }
				}
			    remove(otherHandCard);
			}
                                
		    if(handValue>otherHandValue)
			return 1;
		    else if(handValue<otherHandValue) 
			return 0;
		    else{
			return sameHandMethod(otherHand);
		    }
		}
	    }
		
	else if(isStraight())
	    {
		if(this.getHighCardValue()>otherHand.getHighCardValue())
		    return 1;
		else
		    return 0;
	    }
	else if(isStraightFlush())
	    {
		if(this.getHighCardValue()>otherHand.getHighCardValue())
		    return 1;
		else
		    return 0;
	    }
	else if(isFullHouse())
	    {
		for(int i=0;i<3;i++)
		    {
			if(sortedHand.get(i)==sortedHand.get(i+1) && sortedHand.get(i)==sortedHand.get(i+2))
			    {
				if(sortedHand.get(i)>handValue)
				    handValue=sortedHand.get(i);
			    }	
			if(otherSortedHand.get(i)==otherSortedHand.get(i+1) && otherSortedHand.get(i)==otherSortedHand.get(i+2))
			    {
				if(otherSortedHand.get(i)>otherHandValue)
				    otherHandValue=otherSortedHand.get(i);	
			    }
		    }
		if(handValue>otherHandValue)
		    return 1;
		else
		    return 0;
	    }
	else if(isThreeOfAKind())
	    {
		for(int i=0;i<3;i++)
		    {
			if(sortedHand.get(i)==sortedHand.get(i+1) && sortedHand.get(i)==sortedHand.get(i+2))
			    {
				if(sortedHand.get(i)>handValue)
				    handValue=sortedHand.get(i);
			    }	
			if(otherSortedHand.get(i)==otherSortedHand.get(i+1) && otherSortedHand.get(i)==otherSortedHand.get(i+2))
			    {
				if(otherSortedHand.get(i)>otherHandValue)
				    otherHandValue=otherSortedHand.get(i);	
			    }
		    }
		if(handValue>otherHandValue)
		    return 1;
				
		else if(handValue == otherHandValue){
		    return sameHandMethod(otherHand);
                        
		}
		else{
		    return 0;
		}
	    }
	else if(isFourOfAKind())
	    {
		for(int i=0;i<2;i++)
		    {
			if(sortedHand.get(i)==sortedHand.get(i+1) && sortedHand.get(i)==sortedHand.get(i+3))
			    {
				if(sortedHand.get(i)>handValue)
				    handValue=sortedHand.get(i);
			    }	
			if(otherSortedHand.get(i)==otherSortedHand.get(i+1) && otherSortedHand.get(i)==otherSortedHand.get(i+3))
			    {
				if(otherSortedHand.get(i)>otherHandValue)
				    otherHandValue=otherSortedHand.get(i);	
			    }
		    }
		if(handValue>otherHandValue)
		    return 1;
		else
		    return 0;
	    }
	else
	    {
		return sameHandMethod(otherHand);
	    }
    }

    /**
       Method used to determine which hand has the higher high card. If high
       cards are the same, continuously checks for next high card until a 
       higher card is found.
       @param otherHand hand to be compared
    */
    public int sameHandMethod(Hand otherHand){
	if(this.isEmpty())
	    return 0;
	else if(this.getHighCardValue()>otherHand.getHighCardValue())
	    return 1;
	else if(this.getHighCardValue()<otherHand.getHighCardValue())
	    return 0;
	else{
	    this.remove(this.getHighCard()); 
	    otherHand.remove(otherHand.getHighCard());
	    return sameHandMethod(otherHand);
	}
    }

    /**
       Gets the best possible hand combination using the dealer's and
       the player's hands
       Precondition: dealerHand has 5 cards
       @param dealerHand the dealer's Hand
    **/
    public Hand getBestHand(Hand dealerHand){
	
	Hand allCards = new Hand(this.get(0), this.get(1), dealerHand.get(0), dealerHand.get(1), dealerHand.get(2), dealerHand.get(3), dealerHand.get(4));
		
	Hand bestHand = new Hand(this.get(0), this.get(1), dealerHand.get(0), dealerHand.get(1), dealerHand.get(2));


	for (int a = 0; a < allCards.size(); ++a) {
	    for (int b = a + 1; b < allCards.size(); ++b) {
		for (int c = b + 1; c < allCards.size(); ++c) {
		    for (int d = c + 1; d < allCards.size(); ++d) {
			for (int e = d + 1; e < allCards.size(); ++e) {
			    Hand curHand = new Hand(allCards.get(a), allCards.get(b), allCards.get(c), allCards.get(d), allCards.get(e));
			    if(copyHand(curHand).compareHands(copyHand(bestHand))==1)
				bestHand = curHand;
			}
		    }	
		}
	    }
	}
	return bestHand;
    }

}
	
	
	


















