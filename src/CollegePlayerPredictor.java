// Created by Tyler Hinkie in April 2025

import java.io.*;
import java.util.*;

public class CollegePlayerPredictor {
    // Instance Variables
    private static HashMap<Integer, Player> players;
    private static ArrayList<Integer>[][] heightWeight;

    // Constructor
    public CollegePlayerPredictor() {
        // A map to map the players to their id in the input file
        players = new HashMap<>();
        // A map to hold the players (well, their id's) at their height and weight
        heightWeight = new ArrayList[92][381];
        for (int i = 0; i < heightWeight.length; i++) {
            for (int j = 0; j < heightWeight[0].length; j++) {
                // Initializes the arraylists for adding later
                heightWeight[i][j] = new ArrayList<>();
            }
        }
        // Loads all the data in
        data();
    }

    private void data() {
        // Catches if any of the reading in doesn't work
        try {
            File obj1 = new File("data (cloned)/tbls/cleaned_players.txt");
            if (obj1.length() != 0) {
                Scanner myReader = new Scanner(obj1);
                myReader.nextLine();
                while (myReader.hasNextLine()) {
                    String[] split = myReader.nextLine().split(",");
                    int id = Integer.parseInt(split[0]);
                    int ht = Integer.parseInt(split[2]);
                    int wt = Integer.parseInt(split[3]);
                    // Puts player id at their respective height and weight
                    heightWeight[ht][wt].add(id);
                    // Initializes new player
                    players.put(id, new Player(id, split[1], ht, wt));
                }
                myReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File obj2 = new File("data (cloned)/tbls/cleaned_playerHistory.txt");
            if (obj2.length() != 0) {
                Scanner myReader = new Scanner(obj2);
                myReader.nextLine();
                while (myReader.hasNextLine()) {
                    String[] split = myReader.nextLine().split(",");
                    Player p = players.get(Integer.parseInt(split[5]));
                    // Determines whether the season was college or pro (nca or nba)
                    if (split[3].charAt(1) == 'c') {
                        p.addcGames(Integer.parseInt(split[6]));
                        p.addcPoints(Integer.parseInt(split[30]));
                        p.addcRebounds(Integer.parseInt(split[24]));
                        p.addcAssists(Integer.parseInt(split[25]));
                    } else {
                        p.addpGames(Integer.parseInt(split[6]));
                        p.addpPoints(Integer.parseInt(split[30]));
                        p.addpRebounds(Integer.parseInt(split[24]));
                        p.addpAssists(Integer.parseInt(split[25]));
                    }
                }
                myReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < heightWeight.length; i++) {
            for (int j = 0; j < heightWeight[0].length; j++) {
                for (int id : heightWeight[i][j]) {
                    // Calculates the per-game metrics for every player that's been read in
                    players.get(id).calculatePerGameMetrics();
                }
            }
        }
    }

    // Returns a Result (packaged set of data to be shown on the screen) by comparing the
    // inputted player to other players in the data that was read in
    public Result compare(String name, int height, int weight, double cPPG, double cRPG, double cAPG, boolean blueBlood) {
        // Now that we get the blue blood question from the front end
        // we can use a ternary expression for the multiplier
        double blueBloodFactor = blueBlood ? 1.5 : 1.0;

        // Initializes player that was entered in the front end
        Player p = new Player(name, height, weight, cPPG, cRPG, cAPG);
        ArrayList<Integer> candidates = new ArrayList<>();
        // Adds candidates with same height and weight first
        candidates.addAll(heightWeight[height][weight]);
        // Adds all candidates within two inches and 20 pounds of the player
        for (int i = height - 2; i <= height + 2; i++) {
            for (int j = weight - 20; j <= weight + 20; j++) {
                // Skips same height + weight case to prevent duplicates
                if (i == height && j == weight) continue;
                    // Checks to ensure the heights and weight are in bounds
                else if (i >= 0 && i < heightWeight.length && j >= 0 && j < heightWeight[0].length) {
                    candidates.addAll(heightWeight[i][j]);
                }
            }
        }

        /** Holds Euclidian distance for players within 50
         * This is reasonable because the difference between Steph Curry (roughly 25, 5, 5)
         * and a player without a single stat (0, 0, 0) is still roughly 30 in distance
         */
        ArrayList<Integer>[] distances = new ArrayList[50];
        for (int i = 0; i < distances.length; i++) distances[i] = new ArrayList<>();
        for (int candidate : candidates) {
            Player x = players.get(candidate);
            /** The equation is such:
             * square root of (diff in PPG squared + diff in RPG squared + diff in APG squared).
             *
             * I multiplied the rebounds and assists by 5 because the values are around 5 times smaller,
             * and the correlation is much higher, as all nba-level players score a lot in college.
             */
            int distance = (int) Math.round(Math.sqrt(
                    Math.pow(cPPG - x.getcPPG(), 2) +
                            5 * Math.pow(cRPG - x.getcRPG(), 2) +
                            5 * Math.pow(cAPG - x.getcAPG(), 2)
            ));
            distances[distance].add(x.getId());
        }

        ArrayList<Player> similar = new ArrayList<>();
        int i = 0;
        // Adds 5 players to similar ArrayList
        while (similar.size() < 5 && i < 50) {
            for (int id : distances[i]) {
                Player player = players.get(id);
                // I added the PPG limit to have fun players that
                // people would know (it betters user experience)
                if (player.getpPPG() > 5 && similar.size() < 5) similar.add(player);
            }
            i++;
        }

        double totalP = 0, totalR = 0, totalA = 0;
        for (Player player : similar) {
            totalP += player.getpPPG();
            totalR += player.getpRPG();
            totalA += player.getpAPG();
        }

        // Factors in if they went to a blue blood school, all of
        // which produce a disproportionate number of NBA players
        double avgP = blueBloodFactor * totalP / 5;
        double avgR = blueBloodFactor * totalR / 5;
        double avgA = blueBloodFactor * totalA / 5;

        String role;
        // Determines the predicted role of NBA players based on recent and historical
        // trends of statistical cutoffs for different recognitions
        if (avgP > 30 && (avgR > 10 || avgA > 10)) role = "Legend";
        else if (avgP > 25 && (avgR > 10 || avgA > 8)) role = "MVP Candidate";
        else if (avgP > 25 && (avgR > 5 && avgA > 5)) role = "ALL-NBA";
        else if (avgP > 20 && (avgR > 5 || avgA > 5)) role = "ALL-STAR";
        else if (avgP > 10 || avgR > 10 || avgA > 10) role = "Starter";
        else role = "Role Player";

        return new Result(similar, avgP, avgR, avgA, role);
    }

    // Package of results to be sent to front end for displaying
    public static class Result {
        public ArrayList<Player> similarPlayers;
        public double avgPPG, avgRPG, avgAPG;
        public String role;

        public Result(ArrayList<Player> players, double ppg, double rpg, double apg, String role) {
            this.similarPlayers = players;
            this.avgPPG = ppg;
            this.avgRPG = rpg;
            this.avgAPG = apg;
            this.role = role;
        }
    }
}