package uk.ac.york.student.ScoreFiles;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import uk.ac.york.student.ScoreFiles.UserData;

/*
 * Used for reading and writing to score files.
 */
public final class ScoreFileManager {
    // Returns each leaderboard entry in current leaderboard. 
    // Within the leaderboard each value is seperated by a comma and
    // each new entry is specified by a new line.
    public static ArrayList<UserData> getUserLeaderboardEntries() {
        
        ArrayList<UserData> leaderboardEntries = new ArrayList<UserData>();

        try {
            File leaderboardFile = new File("core/src/uk/ac/york/student/ScoreFiles/leaderboard.txt");
            FileReader fr = new FileReader(leaderboardFile);
            BufferedReader br = new BufferedReader(fr);

            String line;

            while ((line = br.readLine()) != null) {
                List<String> lineSeperated = Arrays.asList(line.split(","));
                String username = lineSeperated.get(0);
                float score = Float.parseFloat(lineSeperated.get(1));
                String degree = lineSeperated.get(2);

                UserData leaderboardEntry = new UserData(username, score, degree);
                leaderboardEntries.add(leaderboardEntry);
            }
            fr.close();
            br.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return leaderboardEntries;
    }

    // Used to create an entry with given username in the temporary user entry file.
    // If the player finishes the game then the entry will be added to leaderboard file.
    // Returns true if file is successfully written to.
    public static boolean CreateNewTempEntry(String username) {
        try {
            FileWriter tempWriter = new FileWriter("core/src/uk/ac/york/student/ScoreFiles/temp.txt");
            tempWriter.write(username);
            tempWriter.close();
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String GetCurrentGameUsername() {
        try {
            File currentGameFile = new File("core/src/uk/ac/york/student/ScoreFiles/temp.txt");
            Scanner currentGameFileReader = new Scanner(currentGameFile);
            String username = "";
            while (currentGameFileReader.hasNextLine()) {
                username = currentGameFileReader.nextLine();
            }
            currentGameFileReader.close();
            return username;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void AddCurrentGameToLeaderBoard(float score, String degree) {
        String username = GetCurrentGameUsername();
        UserData newEntry = new UserData(username, score, degree);

        ArrayList<UserData> leaderboard = getUserLeaderboardEntries();
        if (leaderboard.size() == 0) {
            leaderboard.add(newEntry);
        }
        else {
            for (int i = 0; i < leaderboard.size(); i++) {
                UserData currentEntry = leaderboard.get(i);
                Float currentScore = currentEntry.getScore();
                Float newScore = newEntry.getScore();
    
                int comparisonResult = Float.compare(currentScore, newScore);
    
                if (comparisonResult < 0 || comparisonResult == 0) {
                    leaderboard.add(i, newEntry);
                    break;
                }
                else if (i == leaderboard.size()-1) {
                    leaderboard.add(newEntry);
                    break;
                }
            }
        }
    
        try {
            FileWriter leaderboardWriter = new FileWriter("core/src/uk/ac/york/student/ScoreFiles/leaderboard.txt");
            for (int i = 0; i < leaderboard.size(); i++) {
                UserData userEntry = leaderboard.get(i);
                String line = "";
                line += userEntry.getUsername();
                line += ",";
                line += userEntry.getScore();
                line += ",";
                line += userEntry.getDegree();
                leaderboardWriter.write(line + System.lineSeparator());
            }
            leaderboardWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearLeaderboardFile() {
        try {
            FileWriter leaderboardWriter = new FileWriter("core/src/uk/ac/york/student/ScoreFiles/leaderboard.txt");
            leaderboardWriter.write("");
            leaderboardWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}