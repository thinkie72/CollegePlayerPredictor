// Created by Tyler Hinkie in April 2025
public class Player {
    // Instance Variables
    private int id;
    private String name;
    private int ht;
    private int wt;
    private double cGames;
    private double cPoints;
    private double cRebounds;
    private double cAssists;
    private double cPPG;
    private double cRPG;
    private double cAPG;
    private double pGames;
    private double pPoints;
    private double pRebounds;
    private double pAssists;
    private double pPPG;
    private double pRPG;
    private double pAPG;

    // Constructor
    public Player(int id, String name, int ht, int wt) {
        this.id = id;
        this.name = name;
        this.ht = ht;
        this.wt = wt;
    }

    public Player(String name, int ht, int wt, double cPPG, double cRPG, double cAPG) {
        this.name = name;
        this.ht = ht;
        this.wt = wt;
        this.cPPG = cPPG;
        this.cRPG = cRPG;
        this.cAPG = cAPG;
    }

    // Methods
    public void addcGames(double cGames) {
        this.cGames += cGames;
    }

    public void addcPoints(double cPoints) {
        this.cPoints += cPoints;
    }

    public void addcRebounds(double cRebounds) {
        this.cRebounds += cRebounds;
    }

    public void addcAssists(double cAssists) {
        this.cAssists += cAssists;
    }

    public void addpGames(double pGames) {
        this.pGames += pGames;
    }

    public void addpPoints(double pPoints) {
        this.pPoints += pPoints;
    }

    public void addpRebounds(double pRebounds) {
        this.pRebounds += pRebounds;
    }

    public void addpAssists(double pAssists) {
        this.pAssists += pAssists;
    }

    public void calculatePerGameMetrics() {
        if (cGames == 0) {
            cPPG = 0;
            cRPG = 0;
            cAPG = 0;
        } else {
            cPPG = cPoints / cGames;
            cRPG = cRebounds / cGames;
            cAPG = cAssists / cGames;
        }

        if (pGames == 0) {
            pPPG = 0;
            pRPG = 0;
            pAPG = 0;
        } else {
            pPPG = pPoints / pGames;
            pRPG = pRebounds / pGames;
            pAPG = pAssists / pGames;
        }
    }

    public double getcPPG() {
        return cPPG;
    }

    public double getcRPG() {
        return cRPG;
    }

    public double getcAPG() {
        return cAPG;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}