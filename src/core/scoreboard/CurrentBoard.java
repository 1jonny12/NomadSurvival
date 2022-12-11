package core.scoreboard;

import org.bukkit.scoreboard.Scoreboard;

public class CurrentBoard {

    Scoreboard scoreboard;
    String currentBoardName;

    public CurrentBoard(Scoreboard scoreboard, String currentBoardName) {
        this.scoreboard = scoreboard;
        this.currentBoardName = currentBoardName;
    }

    //----------------------------------------------------
    // [Default] -> Getters and Setters
    //----------------------------------------------------

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public String getCurrentBoardName() {
        return currentBoardName;
    }
}
