// Created by Tyler Hinkie in April 2025
public class Player {
    // Instance Variables
    String name;
    double cGames
    double cPoints;
    double cRebounds;
    double cAssists;
    double cPPG;
    double cRPG;
    double cAPG;
    double pGames;
    double pPoints;
    double pRebounds;
    double pAssists;
    double pPPG;
    double pRPG;
    double pAPG;
    // Constructor

    public Player(String name) {
        this.name = name;
    }

    // Methods
    public void setcGames(double cGames) {
        this.cGames += cGames;
    }

    public void setcPoints(double cPoints) {
        this.cPoints += cPoints;
    }

    public void setcRebounds(double cRebounds) {
        this.cRebounds += cRebounds;
    }

    public void setcAssists(double cAssists) {
        this.cAssists += cAssists;
    }

    public void setcPPG(double cPPG) {
        this.cPPG = cPPG;
    }

    public void setcRPG(double cRPG) {
        this.cRPG = cRPG;
    }

    public void setcAPG(double cAPG) {
        this.cAPG = cAPG;
    }

    public void setpGames(double pGames) {
        this.pGames += pGames;
    }

    public void setpPoints(double pPoints) {
        this.pPoints += pPoints;
    }

    public void setpRebounds(double pRebounds) {
        this.pRebounds += pRebounds;
    }

    public void setpAssists(double pAssists) {
        this.pAssists += pAssists;
    }

    public void setpPPG(double pPPG) {
        this.pPPG = pPPG;
    }

    public void setpRPG(double pRPG) {
        this.pRPG = pRPG;
    }

    public void setpAPG(double pAPG) {
        this.pAPG = pAPG;
    }
}