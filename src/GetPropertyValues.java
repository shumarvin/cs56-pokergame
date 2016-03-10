import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;
import java.io.*;
 
public class GetPropertyValues {
	String result = "";
	InputStream inputStream;
	OutputStream outputStream;
 
 	//adds 1 to the current win number of specified player (1 = player1, 2 = player2, etc.)
	public String updatePropValues(int playerNum) throws IOException {
 
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
 			//if save file (config.properties) exists, update player score on file
			if (inputStream != null) {
                            prop.load(inputStream);
                            
                            Date time = new Date(System.currentTimeMillis());
    
                          
                            String player1Score = prop.getProperty("player1");
                            String player2Score = prop.getProperty("player2");
                            String player3Score = prop.getProperty("player3");
                            String player4Score = prop.getProperty("player4");
                            
                            String winner = "player" + playerNum;
                            String winnerScore = (Integer.parseInt(prop.getProperty(winner)) + 1) + "";
                            prop.setProperty("player1", player1Score);
                            prop.setProperty("player2", player2Score);
                            prop.setProperty("player3", player3Score);
                            prop.setProperty("player4", player4Score);
                            prop.setProperty(winner, winnerScore);
    
                            File file = new File("build/config.properties");
                            FileOutputStream fileOut = new FileOutputStream(file);
                            prop.store(fileOut,"");
                            fileOut.close();
			}
			
			//else create a new save file before updating player score
			 else {
				createStatistics();
                                updatePropValues(playerNum);
			}
 
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return result;
	}
	
	//create new save file
	private void createStatistics() throws IOException{
	
            Properties prop = new Properties();
             File newFile = new File("build/config.properties");
            FileOutputStream newFileOut = new FileOutputStream(newFile);
            
            prop.setProperty("player1", "0");
            prop.setProperty("player2", "0");
            prop.setProperty("player3", "0");
            prop.setProperty("player4", "0");
            prop.store(newFileOut,"");
            newFileOut.close();
	
	
	}
	
	public void resetStatistics() throws IOException {
	
            createStatistics();
	
	}
	
	//return a formatted string of players' scores
	public String getScores()  throws IOException{
            String scores = "";
            try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
                            prop.load(inputStream);
                            
                            Date time = new Date(System.currentTimeMillis());
    
                            // get the property value and print it out
                            String player1Score = prop.getProperty("player1");
                            String player2Score = prop.getProperty("player2");
                            String player3Score = prop.getProperty("player3");
                            String player4Score = prop.getProperty("player4");
                            
                            scores = "Player             Number of Wins\n" +
                                     "-----------------------------------\n" +
                                     "Player 1          "+ player1Score + "\n" +
                                     "Player 2          "+ player2Score + "\n" +
                                     "Player 3          "+ player3Score + "\n" +
                                     "Player 4          "+ player4Score + "\n" +
                                     "-----------------------------------";
			}
			
			 else {
				createStatistics();
                                scores = getScores();
			}
 
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return scores;
            
            
            
            
            
            
	
	}
}
