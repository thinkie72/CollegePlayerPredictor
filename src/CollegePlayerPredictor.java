import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

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
            File myObj = new File("data (cloned)/tbls/players.txt");

            if (myObj.length() != 0) {
                Scanner myReader = new Scanner(myObj);
                myReader.useDelimiter(",");

                myReader.nextLine();

                while (myReader.hasNextLine()) {
                    String str = myReader.nextLine();

                    // Split values at each comma
                    String[] splitString = str.split(",");
                    // Puts the player at their height and weight index
                    int id = Integer.parseInt(splitString[0]);
                    int ht = Integer.parseInt(splitString[2]);
                    System.out.println(ht);
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
    }

    public static String[] compare() {
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

        for (int candidate : candidates) {
            System.out.println(candidate);
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

        while (similarPlayers.size() < 5 && i < 50) {
            similarPlayers.addAll(distances[i]);
            i++;
        }

        String[] out = new String[similarPlayers.size()];
        for (int j = 0; j < out.length; j++) {
            out[j] = players.get(similarPlayers.removeFirst()).getName();
        }

        return out;
    }

    public static void start() {
        data();
        System.out.println(Arrays.toString(compare()));
    }

    public static void main(String[] args) {
        CollegePlayerPredictor cPP = new CollegePlayerPredictor();
        cPP.start();
    }
}