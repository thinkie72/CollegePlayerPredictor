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

                    int ht;
                    if (!splitString[2].isEmpty()) {
                        ht = Integer.parseInt(splitString[2]);
                    } else ht = 0;

                    int wt;
                    if (!splitString[3].isEmpty()) {
                        wt = Integer.parseInt(splitString[3]);
                    } else wt = 0;

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

//        // Try block to check for exceptions
//        try {
//
//            // Creating object of File class to get file path
//            File myObj = new File("data (cloned)/tbls/playerHistory.txt");
//
//            if (myObj.length() != 0) {
//                Scanner myReader = new Scanner(myObj);
//                myReader.useDelimiter(",");
//
//                myReader.nextLine();
//
//                while (myReader.hasNextLine()) {
//                    String str = myReader.nextLine();
//
//                    // Split values at each comma
//                    String[] splitString = str.split(",");
//                    // Puts the player at their height and weight index
//                    int id = Integer.parseInt(splitString[0]);
//
//                    int ht;
//                    if (!splitString[2].isEmpty()) {
//                        ht = Integer.parseInt(splitString[2]);
//                    } else ht = 0;
//
//                    int wt;
//                    if (!splitString[3].isEmpty()) {
//                        wt = Integer.parseInt(splitString[3]);
//                    } else wt = 0;
//
//                    heightWeight[ht][wt].add(id);
//                    players.put(id, new Player(id, splitString[1], ht, wt));
//                }
//                myReader.close();
//            }
//        }
//        // Catch block to handle the exceptions
//        catch (Exception e) {
//            System.out.println("An error occurred." + e);
//            e.printStackTrace();
//        }
    }

    public static String[] compare() {
        Player y = players.get(7248636);
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

        while (similarPlayers.size() < 5 && i < 50) {
            similarPlayers.addAll(distances[i]);
            i++;
        }

        String[] out = new String[5];
        for (int j = 0; j < out.length; j++) {
            out[j] = players.get(similarPlayers.removeFirst()).getName();
        }

        return out;
    }

    public static void start() {
        data();
        System.out.println(Arrays.toString(compare()));
    }
//
//    public static void main(String[] args) {
//        CollegePlayerPredictor cPP = new CollegePlayerPredictor();
//        cPP.start();
//    }


    public static void main(String[] args) throws IOException {
        File inputFile1 = new File("data (cloned)/tbls/players.txt");            // Main file to clean
        File inputFile2 = new File ("data (cloned)/tbls/playerHistory.txt");    // Second file to filter
        File outputFile1 = new File("data (cloned)/tbls/cleaned_players.txt");            // Main file to clean
        File outputFile2 = new File ("data (cloned)/tbls/cleaned_playerHistory.txt"); // Cleaned output of file 2

        Set<String> badIds = new HashSet<>();
        List<String[]> validRows1 = new ArrayList<>();

        // Step 1: Read first file and collect bad IDs
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile1))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) {
                    parts = Arrays.copyOf(parts, 5);
                }

                boolean isBad = parts[2].isEmpty() || parts[3].isEmpty();
                if (isBad) {
                    badIds.add(parts[0]);
                } else {
                    validRows1.add(parts);
                }
            }
        }

        // Step 2: Remove rows in first file with bad ID in index 5
        List<String> cleanedLines1 = new ArrayList<>();
        for (String[] row : validRows1) {
            if (row.length > 5 && badIds.contains(row[5])) {
                continue;
            }
            cleanedLines1.add(String.join(",", row));
        }

        // Step 3: Filter second file using the bad IDs
        List<String> cleanedLines2 = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile2))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 5 && badIds.contains(parts[5])) {
                    continue;  // Skip if index 5 is a bad ID
                }
                cleanedLines2.add(line);
            }
        }

        // Step 4: Write cleaned first file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile1))) {
            for (String line : cleanedLines1) {
                writer.write(line);
                writer.newLine();
            }
        }

        // Step 5: Write cleaned second file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile2))) {
            for (String line : cleanedLines2) {
                writer.write(line);
                writer.newLine();
            }
        }

        System.out.println("Cleaning complete. Outputs written to:");
        System.out.println(STR." - \{outputFile1}");
        System.out.println(STR." - \{outputFile2}");
    }
}
