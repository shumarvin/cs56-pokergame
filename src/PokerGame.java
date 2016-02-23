import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;
import java.util.ArrayList;

/**
   Class that represents a Poker 5 card draw game.
*/

public class PokerGame {

    private JPanel panel;
    private JFrame mainFrame,mainFrame2;
    private JFrame playButtonFrame;
    private JButton playButton, playAgainButton, infoButton;
    private JLabel winnerLabel;
    private JPanel dealerPanel,player1Panel,player2Panel,player3Panel,
	player4Panel,centerPanel;
    private Hand dealerHand; 
    private ArrayList<Hand> playerHands;
    private Deck deck;
    private int numPlayers;
	
    /**
       No arg constructor that initializes a new deck.
    */
    public PokerGame(){
	deck=new Deck();
    }
	
    /**
       Main method of PokerGame class.
    */
    public static void main(String[] args)
    {
	PokerGame gui=new PokerGame();
	gui.go();
    }
	
    /**
       Creates a window with a Play button.
    */
    public void go(){
		
	playButtonFrame = new JFrame();
	playButtonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
                
		
	panel=new JPanel();
		
	playButton=new JButton("Click to Play");
	playButton.addActionListener(new playButtonListener());
	panel.add(playButton,BorderLayout.CENTER);
		
	infoButton = new JButton("Rules");
	infoButton.addActionListener(new infoButtonListener());
	panel.add(infoButton, BorderLayout.SOUTH);
		
		
	panel.setBackground(Color.darkGray);
		
	playButtonFrame.add(BorderLayout.CENTER, panel);
	playButtonFrame.setSize(200,200);
		
	playButtonFrame.setVisible(true);
    }
	
    /**
       Sets up the player's and dealer's hand.
    */	
    public void playerSetUp(int numPlayers){
	for(int i = 0; i< numPlayers; i++){
	    Hand playerHand = new Hand(deck, 2);
	    playerHands.add(playerHand);
	}
	dealerHand=new Hand(deck, 5);
    }
	
    /**
       Returns an ImageIcon by using the URL class in order to make the 
       ImageIcon web compatible.
       @param c card whose image is to be retrieved.
    */
    public ImageIcon getCardImage(Card c){
	String dir="Cards/";
	String cardFile=c.toString()+".png";
	URL url=getClass().getResource(dir+cardFile);
	return new ImageIcon(url);
    }
    //The listener for the How To Play button
	class infoButtonListener implements ActionListener{
	    public void actionPerformed(ActionEvent event){
		TabbedPane infoPane = new TabbedPane();
		infoPane.setVisible(true);
	    }
	}
	
	/**
	   Sets up the Poker game when the client clicks the Play button.
	*/
	class playButtonListener implements ActionListener{
	    public void actionPerformed(ActionEvent event){

		/**After clicking the play button, make a pop-up
		   box that allows the user to choose the number of 
		   players
		**/
		JFrame selectFrame = new JFrame();	
		Object[] possibilities = {"2", "3", "4"};
		String s = (String)JOptionPane.showInputDialog(selectFrame,
                      "Select number of players",
		      "Number of Players",
		       JOptionPane.PLAIN_MESSAGE,
		       null,
		       possibilities,
		       "2");
		numPlayers = Integer.parseInt(s);

		///set up the player hands
		playerHands = new ArrayList<Hand>();
		playerSetUp(numPlayers);
		Hand player1 = playerHands.get(0);
		Hand player2 = playerHands.get(1);
		Hand player3 = new Hand();
		Hand player4 = new Hand();
		if(playerHands.size() >= 3)
		    player3 = playerHands.get(2);
		if(playerHands.size() == 4)
		    player4 = playerHands.get(3);
		
		//set up the panels for the deal and all the players
		dealerPanel=new JPanel();
		player1Panel = new JPanel();
		player2Panel = new JPanel();
		player3Panel = new JPanel();
		player4Panel = new JPanel();
		
		JPanel bestHandsPanel = new JPanel();		
		centerPanel=new JPanel();
		playAgainButton=new JButton("Play Again");
		playAgainButton.addActionListener(new playButtonListener());
		centerPanel.add(BorderLayout.NORTH,playAgainButton);

		/**Get the card images for all the cards in the dealer's
		   and players' hands and put them in their respective
		   panels
		**/
		for(int i=0;i<dealerHand.size();i++)
		    dealerPanel.add(new JLabel(getCardImage(dealerHand.get(i)))); 			
		for(int i=0;i<player1.size();i++)
			player1Panel.add(new JLabel(getCardImage(player1.get(i))));
	      
		for(int i=0;i<player2.size();i++)
			player2Panel.add(new JLabel(getCardImage(player2.get(i))));
		if(playerHands.size() >= 3){
		for(int i=0;i<player3.size();i++)
			player3Panel.add(new JLabel(getCardImage(player3.get(i))));
	        }
		if(playerHands.size() == 4){
		for(int i=0;i<player4.size();i++)
			player4Panel.add(new JLabel(getCardImage(player4.get(i))));
		}
		
		//add labels to all the panels
		dealerPanel.add(new JLabel("DEALER"));
		player1Panel.add(new JLabel("PLAYER 1"));
		player2Panel.add(new JLabel("PLAYER 2"));
		if(playerHands.size() >= 3)
		    player3Panel.add(new JLabel("PLAYER 3"));
		if(playerHands.size() == 4)
		    player4Panel.add(new JLabel("PLAYER 4"));
		bestHandsPanel.add(new JLabel("Best Hands"));
		
		 
		   /**
		   if(playerHand.compareHands(dealerHand)==1)
		   winnerLabel=new JLabel("YOU WON!");
		   else
		   winnerLabel=new JLabel("Dealer won");
		   centerPanel.add(winnerLabel);
		   deck.reShuffle();
		**/
  		
		ArrayList<Hand> bestPlayerHands = new ArrayList<Hand>();

		for(Hand h : playerHands){
			bestPlayerHands.add(h.getBestHand(dealerHand));
			
		}

		//Create a FlowLayout Panel to display the players' cards
		JPanel main = new JPanel( new FlowLayout(FlowLayout.CENTER,20, 0) );
		main.add(player1Panel);
		main.add(player2Panel);
		main.add(player3Panel);
		main.add(player4Panel);

		JPanel bestHands = new JPanel( new FlowLayout(FlowLayout.CENTER, 30, 0) );
		
		for(int i=0;i<bestPlayerHands.size();i++){
		    Hand currentPlayer = bestPlayerHands.get(i);
		    for(int j = 0; j < currentPlayer.size(); j++)
			bestHandsPanel.add(new JLabel(getCardImage(currentPlayer.get(j))));
		}
		bestHands.add(bestHandsPanel);

		
		mainFrame=new JFrame();
		mainFrame.setSize(1800,1800);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/**For the GUI, the dealer will be on top while the players
		   will be in the center
		**/
		mainFrame.getContentPane().add(BorderLayout.NORTH, dealerPanel);
		mainFrame.getContentPane().add(BorderLayout.CENTER, main);
		//mainFrame.getContentPane().add(BorderLayout.SOUTH, bestHands);
		mainFrame.getContentPane().add(BorderLayout.SOUTH, centerPanel);
		playButtonFrame.dispose();
		mainFrame.setVisible(true);
	    }	
  	}
}
















