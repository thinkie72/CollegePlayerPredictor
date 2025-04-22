// Created by Tyler Hinkie in April 2025
public class CollegePlayerPredictor {
    HashMap<int, Player> players;
    ArrayList[][] heightWeight;

    public CollegePlayerPredictor {
        players = new HashMap<>();
        heightWeight = new ArrayList[30][280];
        for (int i = 0; i < heightWeight.length; i++) {
            for (int j = 0; j < heightWeight[0].length; j++) {
                heightWeight[i][j] = new ArrayList<int>();
            }
        }
    }

    public static data() {
        // Try block to check for exceptions
        try {

            // Creating object of File class to get file path
            File myObj = new File("data/tbls/players.txt");

            if (myObj.length() != 0) {
                Scanner myReader = new Scanner(myObj);
                myReader.useDelimiter(",");

                int index = 0;

                while (myReader.hasNextLine()) {
                    String str = myReader.nextLine();

                    // Split values at each comma
                    String[] splitString = str.split(",");
                    splitString[0]
                    months[index] = new Month(splitString[0], parseInt(splitString[1]), parseInt(splitString[2]));
                    index++;
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

    public static void start() {

    }

    public static void main(String[] args) {
        CollegePlayerPredictor cPP = new CollegePlayerPredictor();
        cPP.start();
    }
}