package core.scoreboard;

import core.utils.Util;
import game.gamePlayer.GPlayer;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public abstract class SBoard {

    private final ScoreBoardManager scoreBoardManager;

    public SBoard(ScoreBoardManager scoreBoardManager) {
        this.scoreBoardManager = scoreBoardManager;
    }

    public abstract String getBoardName();

    public abstract int getLineCount();

    public abstract void updateScoreBoardSideBarStanding(GPlayer gPlayer, Scoreboard scoreboard);

    public abstract void updateScoreBoardSideBarSneaking(GPlayer gPlayer, Scoreboard scoreboard);

    public abstract void updateNames(Scoreboard scoreboard);

    /**
     * Add a dynamic line that can be removed and added.
     *
     * @param lineName This is what the player will see on the ScoreBoard and what you must use to remove the line.
     * @param score    This is the number to the right of the line. Static Lines have a value of 8, Higher values show above lower values.
     */
    public void addDynamicLine(Scoreboard scoreboard, String lineName, String prefix, String suffix, int score) {
        if (scoreboard.getTeam(Util.STRING.formatString(lineName)) == null) {
            Team team = scoreboard.registerNewTeam(Util.STRING.formatString(lineName));
            team.addEntry(Util.STRING.formatString(lineName));
            team.setPrefix(Util.STRING.formatString(prefix));
            team.setSuffix(Util.STRING.formatString(suffix));
            Objective sideBoard = scoreboard.getObjective("SideBoard");
            sideBoard.getScore(Util.STRING.formatString(lineName)).setScore(score);
        }
    }

    /**
     * Remove a dynamic line from the scoreboard
     *
     * @param lineName Must be the same of the name when adding the line to remove it (Including Color Formatting)
     */
    public void removeDynamicLine(Scoreboard scoreboard, String lineName) {
        Team team = scoreboard.getTeam(Util.STRING.formatString(lineName));
        if (team != null) {
            team.unregister();
        }
    }

    public void useDefaultNames(Scoreboard scoreboard) {
        /*
        for (Player p : Bukkit.getOnlinePlayers()) {
            Profile playerData = (Profile) MineVadeCore.getAPI().GetProfileManager().GetPlayerProfile(p);
            PlayerRank rank = (PlayerRank) playerData.getPlayerRank().getData();
            ChatColor nameColor = playerData.getNameColor();

            if (nameColor == ChatColor.RESET) {
                nameColor = ChatColor.getByChar(rank.getNameColor().replace("&", ""));
            }

            String TeamID = rank.name() + "-" + nameColor.getChar();

            Team team = scoreboard.getTeam(TeamID);

            if (scoreboard.getTeam(TeamID) == null) {
                team = scoreboard.registerNewTeam(TeamID);
                team.setPrefix(Util.STRING.formatString(rank.getTag()));
                team.setColor(nameColor);
            }

            if (!team.hasPlayer(p)) {
                team.addPlayer(p);
            }

        }
        */

    }

    /**
     * A static line is always on the scoreboard and is added and created when the scoreboard is assigned
     *
     * @param LineNumber The number of the line. (Note even if dynamic lines are above the static lines the number order is still the same.)
     * @param Prefix     What shows on the line. Used in conjunction with the suffix
     * @param Suffix     What shows on the line. Used in conjunction with the prefix
     */
    public void updateStaticLine(Scoreboard scoreboard, int LineNumber, String Prefix, String Suffix) {
        Team team = scoreboard.getTeam(Util.STRING.formatString("&" + LineNumber));
        team.setPrefix(Util.STRING.formatString("&f" + Prefix));
        team.setSuffix(Util.STRING.formatString("&f" + Suffix));
    }

    //----------------------------------------------------
    // [Default] -> Getters and Setters
    //----------------------------------------------------


    public ScoreBoardManager getScoreBoardManager() {
        return scoreBoardManager;
    }
}
