///////modified from http://www.cs.cf.ac.uk/Dave/HCI/HCI_Handout_CALLER/node61.html

import java.awt.*;
import javax.swing.*;

/**
This TabbedPane shows how to play poker. There will be 3 tabs: one for
general rules, one for betting, and one for hand rankings

@author Marvin Shu
@author Samuel E. Chu
@version CS56, W16, P. Conrad
 **/
class TabbedPane extends  JFrame
{
    private JTabbedPane tabbedPane;
    private JPanel	panel1;
    private JPanel    	panel2;
    private JPanel	panel3;


    public TabbedPane()
    {		
	setTitle( "Poker Instructions" );
	setSize( 796, 1000 );
	setBackground( Color.gray );

	JPanel topPanel = new JPanel();
	topPanel.setLayout( new BorderLayout() );
	getContentPane().add( topPanel );

	// Create the tab pages
	createPage1();
	createPage2();
	createPage3();

	// Create a tabbed pane
	tabbedPane = new JTabbedPane();
	tabbedPane.addTab( "General Rules", panel1 );
	tabbedPane.addTab( "Betting", panel2 );
	tabbedPane.addTab( "Hand Rankings", panel3 );
	topPanel.add( tabbedPane, BorderLayout.CENTER );
    }
    
    //create the first tabbed page, "General Rules"
    public void createPage1()
    {
	ImageIcon image = new ImageIcon("images/instructions1.jpg");
	JLabel label = new JLabel("", image, JLabel.CENTER);
	panel1 = new JPanel(new BorderLayout());
	panel1.add( label, BorderLayout.CENTER );
    }
    //create the second tabbed page, "Betting"
    public void createPage2()
    {
	ImageIcon image = new ImageIcon("images/instructions2.jpg");
	JLabel label = new JLabel("", image, JLabel.CENTER);
	panel2 = new JPanel(new BorderLayout());
	panel2.add( label, BorderLayout.CENTER );
    }
    //create the third tabbed page, "Hand Rankings"
    public void createPage3()
    {
	ImageIcon image = new ImageIcon("images/poker_hand_rankings.jpg");
	JLabel label = new JLabel("", image, JLabel.CENTER);
	panel3 = new JPanel(new BorderLayout());
	panel3.add( label, BorderLayout.CENTER );
    }

}
