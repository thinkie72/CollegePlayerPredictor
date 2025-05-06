import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;

// Created by Tyler Hinkie in April 2025
public class CollegePlayerPredictor {
    static HashMap<Integer, Player> players;
    static ArrayList<Integer>[][] heightWeight;

    public CollegePlayerPredictor() {
        players = new HashMap<>();
        heightWeight = new ArrayList[92][381];
        for (int i = 0; i < heightWeight.length; i++) {
            for (int j = 0; j < heightWeight[0].length; j++) {
                heightWeight[i][j] = new ArrayList<Integer>();
            }
        }
    }

    public static void data() {
        // Try block to check for exceptions
        try {

            // Creating object of File class to get file path
            File obj1 = new File("data (cloned)/tbls/cleaned_players.txt");

            if (obj1.length() != 0) {
                Scanner myReader = new Scanner(obj1);
                myReader.nextLine();

                while (myReader.hasNextLine()) {
                    String str = myReader.nextLine();
                    // Split values at each comma
                    String[] splitString = str.split(",");
                    // Puts the player at their height and weight index
                    int id = Integer.parseInt(splitString[0]);

                    int ht = Integer.parseInt(splitString[2]);

                    int wt = Integer.parseInt(splitString[3]);

                    heightWeight[ht][wt].add(id);
                    players.put(id, new Player(id, splitString[1], ht, wt));
                }
                myReader.close();
            }
        }
        // Catch block to handle the exceptions
        catch (Exception e) {
            System.out.println("An error occurred." + e);
            e.printStackTrace();
        }

        // Try block to check for exceptions
        try {
            // Creating object of File class to get file path
            File obj2 = new File("data (cloned)/tbls/cleaned_playerHistory.txt");

            if (obj2.length() != 0) {
                Scanner myReader = new Scanner(obj2);
                myReader.nextLine();

                while (myReader.hasNextLine()) {
                    String str = myReader.nextLine();

                    // Split values at each comma
                    String[] splitString = str.split(",");
                    Player p = players.get(Integer.parseInt(splitString[5]));
                    if (splitString[3].charAt(1) == 'c') {
                        p.addcGames(Integer.parseInt(splitString[6]));
                        p.addcPoints(Integer.parseInt(splitString[30]));
                        p.addcRebounds(Integer.parseInt(splitString[24]));
                        p.addcAssists(Integer.parseInt(splitString[25]));
                    } else {
                        p.addpGames(Integer.parseInt(splitString[6]));
                        p.addpPoints(Integer.parseInt(splitString[30]));
                        p.addpRebounds(Integer.parseInt(splitString[24]));
                        p.addpAssists(Integer.parseInt(splitString[25]));
                    }
                }
                myReader.close();
            }
        }
        // Catch block to handle the exceptions
        catch (Exception e) {
            System.out.println("An error occurred." + e);
            e.printStackTrace();
        }

        for (int i = 0; i < heightWeight.length; i++) {
            for (int j = 0; j < heightWeight[0].length; j++) {
                for (int id : heightWeight[i][j]) {
                    players.get(id).calculatePerGameMetrics();
                }
            }
        }
    }

    public static String[] compare() {
        Player y = players.get(7248636);
        System.out.println(y.getName());
        System.out.println(y.getId());
        System.out.println(y.getcPPG());
        System.out.println(y.getpPPG());
        Scanner s = new Scanner(System.in);
        System.out.print("Enter Name: ");
        String name = s.nextLine();
        System.out.println();
        System.out.print("Enter Height: ");
        int ht = Integer.parseInt(s.nextLine());
        System.out.println();
        System.out.print("Enter Weight: ");
        int wt = Integer.parseInt(s.nextLine());
        System.out.println();
        System.out.print("Enter PPG: ");
        double pts = Double.parseDouble(s.nextLine());
        System.out.println();
        System.out.print("Enter RPG: ");
        double reb = Double.parseDouble(s.nextLine());
        System.out.println();
        System.out.print("Enter APG: ");
        double ast = Double.parseDouble(s.nextLine());
        System.out.println();
        Player p = new Player(name, ht, wt, pts, reb, ast);
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int i = ht - 2; i <= ht + 2; i++) {
            for (int j = wt - 20; j <= wt + 20; j++) {
                candidates.addAll(heightWeight[i][j]);
            }
        }

        Player x;
        ArrayList<Integer>[] distances = new ArrayList[50];
        for (int i = 0; i < distances.length; i++) {
            distances[i] = new ArrayList<Integer>();
        }
        int distance;
        double points;
        double rebounds;
        double assists;
        for (int candidate : candidates) {
            x = players.get(candidate);
            points = Math.pow(p.getcPPG() - x.getcPPG(), 2);
            rebounds = Math.pow(p.getcRPG() - x.getcRPG(), 2);
            assists = Math.pow(p.getcAPG() - x.getcAPG(), 2);
            distance = (int) Math.round(Math.sqrt(points + rebounds + assists));
            distances[distance].add(x.getId());
        }



        ArrayList<Integer> similarPlayers = new ArrayList<Integer>();

        int i = 0;

        while (i < 50) {
            if (!distances[i].isEmpty()) {
                similarPlayers.addAll(distances[i]);
            }
            i++;
        }

        String[] out = new String[5];
        Player finalist;

        int index = 0;
        while (index < 5 && !similarPlayers.isEmpty()) {
            finalist = players.get(similarPlayers.removeFirst());
            if (finalist.getpPPG() > 5) {
                out[index] = finalist.getName();
                index++;
            }
        }

        return out;
    }

    public static void start() {
        data();
        System.out.println(Arrays.toString(compare()));
    }

    public static void main(String[] args) {
        CollegePlayerPredictor cPP = new CollegePlayerPredictor();
        start();
    }
}
