import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;
import java.util.ArrayList;
import java.io.IOException;
/**
   Class that represents a Poker 5 card draw game.
*/

public class PokerGame {

    private JPanel panel;
    private JFrame mainFrame,mainFrame2;
    private JFrame playButtonFrame;
    private JButton playButton, playAgainButton, infoButton, statisticsButton;
    private JLabel winnerLabel;
    private JPanel dealerPanel,player1Panel,player2Panel,player3Panel,
	player4Panel,bottomPanel;
    private Hand dealerHand, player1, player2, player3, player4; 
    private ArrayList<Hand> playerHands;
    private Deck deck;
    private int numPlayers;
    private GetPropertyValues properties = new GetPropertyValues();
    private JButton hideShowButton1, hideShowButton2, hideShowButton3, hideShowButton4;
	
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
	
	statisticsButton = new JButton("Statistics");
	statisticsButton.addActionListener(new statisticsButtonListener());
	panel.add(statisticsButton, BorderLayout.SOUTH);
		
		
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
	    Hand playerHand = new Hand();
	    deck.dealCards(playerHand,2);
	    playerHands.add(playerHand);
	}
	dealerHand = new Hand();
	deck.dealCards(dealerHand,5);
    }
	
	public void nextTurn(){
		
		//deck.dealCards(dealerHand,1);
		//dealerPanel.add(new JLabel(getCardImage(dealerHand.get(dealerHand.size()-1)))); 
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
    
    //outputs window with the current number of wins each player has
    class statisticsButtonListener implements ActionListener{
	    public void actionPerformed(ActionEvent event){
                String statistics = "";
                
                try{
		 statistics = properties.getScores();
		}catch(IOException e){
                    System.out.println("Could not find scores");
		}
                 Object[] Btns = {"Reset Statistics", "Exit" }; 
		 int choice =JOptionPane.showOptionDialog( null, statistics, "Statistics",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE, null,Btns, "Exit" ); 
		 
		 
		 if(choice == 0){
                    try{
                    properties.resetStatistics();
                    actionPerformed(event);
                    }catch(IOException e){
                    System.out.println("Could not reset scores");
                    }
                     
                
                }
	    }
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
	class playButtonListener implements ActionListener {
	
            
	    public void actionPerformed(ActionEvent event) {

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

		mainFrame=new JFrame();
		mainFrame.setSize(1800,1800);
		//mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//shuffle the deck if playing again
		deck.reShuffle();

		///set up the player and dealer hands
		playerHands = new ArrayList<Hand>();
		playerSetUp(numPlayers);
		player1 = playerHands.get(0);
		player2 = playerHands.get(1);
		player3 = new Hand();
		player4 = new Hand();
		if(playerHands.size() >= 3)
		    player3 = playerHands.get(2);
		if(playerHands.size() == 4)
		    player4 = playerHands.get(3);
		
		//set up the panels for the dealer and all the players
		dealerPanel  = new JPanel();
		player1Panel = new JPanel();
		player2Panel = new JPanel();
		player3Panel = new JPanel();
		player4Panel = new JPanel();

		/**Get the card images for all the cards in the dealer's
		   and players' hands and put them in their respective
		   panels. The Player Cards will start face down 
		**/
		for(int i=0;i<dealerHand.size();i++){
                    dealerPanel.add(new JLabel(getCardImage(dealerHand.get(i))));
                   
                }
		for(int i=0;i<player1.size();i++)
			player1Panel.add(new JLabel(getCardImage(new Card(0, "S"))));
	      
		for(int i=0;i<player2.size();i++)
			player2Panel.add(new JLabel(getCardImage(new Card(0, "S"))));
		if(playerHands.size() >= 3){
		    for(int i=0;i<player3.size();i++)
			player3Panel.add(new JLabel(getCardImage(new Card(0, "S"))));
	        }
		if(playerHands.size() == 4){
		    for(int i=0;i<player4.size();i++)
			player4Panel.add(new JLabel(getCardImage(new Card(0, "S"))));
		}
		
		//add labels to all the panels
		dealerPanel.add(new JLabel("DEALER"));
		player1Panel.add(new JLabel("PLAYER 1"));
		player2Panel.add(new JLabel("PLAYER 2"));
		if(playerHands.size() >= 3)
		    player3Panel.add(new JLabel("PLAYER 3"));
		if(playerHands.size() == 4)
		    player4Panel.add(new JLabel("PLAYER 4"));
      
		//Create a FlowLayout Panel to display the players' cards
		JPanel players = new JPanel( new FlowLayout(FlowLayout.CENTER,20, 0) );
		players.add(player1Panel);
		players.add(player2Panel);
		if(playerHands.size() >= 3)
		    players.add(player3Panel);
		if(playerHands.size() == 4)
		    players.add(player4Panel);

		//for(int i = 0; i < 5; i++)
			//nextTurn();
		/**
		  Create a list of the best possible hand for each player
		  using their 2 cards and the dealer's 5
		**/
		ArrayList<Hand> bestPlayerHands = new ArrayList<Hand>();
		for(Hand h : playerHands){
		    bestPlayerHands.add(h.getBestHand(dealerHand));    
		}
		
		Hand bestPlayer1Hand = bestPlayerHands.get(0);
		Hand bestPlayer2Hand = bestPlayerHands.get(1);
		Hand bestPlayer3Hand = new Hand();
		Hand bestPlayer4Hand = new Hand();

		//Determine the winners
		int bestHand = 0;
		for(int i = 1; i < bestPlayerHands.size(); i++){
			if(bestPlayerHands.get(i).compareHands(bestPlayerHands.get(bestHand)) == 1){
				bestHand = i;
			}
		}
		winnerLabel = new JLabel("Player " + (bestHand + 1) + " wins!");
		try{
		properties.updatePropValues(bestHand + 1);
		}catch(IOException e){
                    System.out.println("Could not update scores");
		}

		/**
		   Instantiate the bottom Panel and add the replay button and the winner label
		   to it
		**/
		bottomPanel=new JPanel();
		playAgainButton=new JButton("Play Again");
		playAgainButton.addActionListener(new playButtonListener());
                
                
                //hide/show buttons
                hideShowButton1=new JButton("Hide/Show");
                hideShowButton1.addActionListener(new hideShowButtonListener(player1, 1, player1Panel));
                player1Panel.add(hideShowButton1);
                
                hideShowButton2=new JButton("Hide/Show");
                hideShowButton2.addActionListener(new hideShowButtonListener(player2, 2, player2Panel));
                player2Panel.add(hideShowButton2);
                
                hideShowButton3=new JButton("Hide/Show");
                hideShowButton3.addActionListener(new hideShowButtonListener(player3, 3, player3Panel));
                player3Panel.add(hideShowButton3);
                
                hideShowButton4=new JButton("Hide/Show");
                hideShowButton4.addActionListener(new hideShowButtonListener(player4, 4,  player4Panel));
                player4Panel.add(hideShowButton4);
                
                
		bottomPanel.add(BorderLayout.NORTH, winnerLabel);
		bottomPanel.add(BorderLayout.SOUTH,playAgainButton);

		/**For the GUI, the dealer will be on top while the players
		   will be in the center and the victory message and replay 
		   button will be on the bottom
		**/
		mainFrame.getContentPane().add(BorderLayout.NORTH, dealerPanel);
		mainFrame.getContentPane().add(BorderLayout.CENTER, players);
		mainFrame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
		playButtonFrame.dispose();
		mainFrame.setVisible(true);
	    }	
	    
	    
	    class hideShowButtonListener implements ActionListener{
                private Hand playerHand;
                private int playerNum;
                private JPanel playerPanel;
                
                /**
                    Constructor
                    @param playerhand the player Hand tied to this listener
                    @param playerNum which player (i.e. player 1 or 2) this listener is tied to
                    @param playerPanel the panel this listener is tied to
                    **/
                public hideShowButtonListener(Hand playerHand, int playerNum,JPanel playerPanel){
                    this.playerHand = playerHand;
                    this.playerNum = playerNum;
                    this.playerPanel = playerPanel;
                }
                /**
                    Upon pressing the button, it will remove all the components
                    from playerPanel, then re-add the cards depending on their flipped
                    status, the JLabel telling which player it is, and the hide/show button
                    **/
                public void actionPerformed(ActionEvent event){
                        //remove all the components
                        playerPanel.removeAll();
                        
                        //if it's face down
                        if(playerHand.get(0).getIsFaceDown()){
                            for(int i=0;i<playerHand.size();i++){
                                playerPanel.add(new JLabel(getCardImage(playerHand.get(i))));
                                playerHand.get(i).flip();
                            }
                        }
                        //if it's face up
                        else{
                            for(int i=0;i<playerHand.size();i++){
                                playerPanel.add(new JLabel(getCardImage(new Card(0, "S"))));
                                playerHand.get(i).flip();
                               
                            }
                        }
                        
                        //re-add the other components
                        playerPanel.add(new JLabel("Player " + playerNum));
                        JButton newHideShowButton = new JButton("Hide/Show");
                        newHideShowButton.addActionListener(new hideShowButtonListener(playerHand,playerNum,playerPanel));
                        playerPanel.add(newHideShowButton);
                        playerPanel.revalidate();
                        playerPanel.repaint();
                }
            }
  	}
}
















