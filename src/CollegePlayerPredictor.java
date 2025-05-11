import java.io.*;
import java.util.*;

public class CollegePlayerPredictor {
    static HashMap<Integer, Player> players;
    static ArrayList<Integer>[][] heightWeight;

    public CollegePlayerPredictor() {
        players = new HashMap<>();
        heightWeight = new ArrayList[92][381];
        for (int i = 0; i < heightWeight.length; i++) {
            for (int j = 0; j < heightWeight[0].length; j++) {
                heightWeight[i][j] = new ArrayList<>();
            }
        }
        data();
    }

    private void data() {
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
                    heightWeight[ht][wt].add(id);
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
                    players.get(id).calculatePerGameMetrics();
                }
            }
        }
    }

    public Result compare(String name, int height, int weight, double cPPG, double cRPG, double cAPG, boolean blueBlood) {
        double blueBloodFactor = blueBlood ? 1.5 : 1.0;

        Player p = new Player(name, height, weight, cPPG, cRPG, cAPG);
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int i = height - 2; i <= height + 2; i++) {
            for (int j = weight - 20; j <= weight + 20; j++) {
                if (i >= 0 && j >= 0 && i < heightWeight.length && j < heightWeight[0].length)
                    candidates.addAll(heightWeight[i][j]);
            }
        }

        ArrayList<Integer>[] distances = new ArrayList[50];
        for (int i = 0; i < distances.length; i++) distances[i] = new ArrayList<>();
        for (int candidate : candidates) {
            Player x = players.get(candidate);
            int distance = (int) Math.round(Math.sqrt(
                    Math.pow(cPPG - x.getcPPG(), 2) +
                            5 * Math.pow(cRPG - x.getcRPG(), 2) +
                            5 * Math.pow(cAPG - x.getcAPG(), 2)
            ));
            distances[distance].add(x.getId());
        }

        ArrayList<Player> similar = new ArrayList<>();
        int i = 0;
        while (similar.size() < 5 && i < 50) {
            for (int id : distances[i]) {
                Player player = players.get(id);
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

        double avgP = blueBloodFactor * totalP / 5;
        double avgR = blueBloodFactor * totalR / 5;
        double avgA = blueBloodFactor * totalA / 5;

        String role;
        if (avgP > 30 && (avgR > 10 || avgA > 10)) role = "ALL-TIME";
        else if (avgP > 25 && (avgR > 10 || avgA > 8)) role = "MVP Candidate";
        else if (avgP > 25 && (avgR > 5 && avgA > 5)) role = "ALL-NBA";
        else if (avgP > 20 && (avgR > 5 || avgA > 5)) role = "ALL-STAR";
        else role = "Role Player";

        return new Result(similar, avgP, avgR, avgA, role);
    }

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