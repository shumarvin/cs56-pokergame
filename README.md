
W16 Final Remarks
How to Run:
1. type "ant cards" first. This will generate the images for all the cards
2. type "ant run"
*can also type ant -p to see other commands


Right now, the game simulates up to 4 player's hands and determines the winner among them. Turn and betting support
has not yet been implemented. 

How it's designed currently:
You have a Card, which has a suit and a value. It also has a boolean to determine
if it should be face down or not. You have a Deck, which is an ArrayList, of 52 Cards. A Hand, which 
is also an ArrayList, uses the draw method of the Deck class to get 2 cards in the hand. PokerGame has the main method 
and is where the GUI and Hand comparing take place. 

There's alot of similar code that could be refactored, such as creating and initializing the Hands in 
PokerGame. Turns and betting still need to be implemented. Hide/show can be useful for turns. You could
also take advantage of the state design pattern to implement turns.

