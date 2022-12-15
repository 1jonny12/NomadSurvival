package core.scoreboard;

import core.utils.Task;
import core.utils.Util;
import core.scoreboard.Boards.Board_Main;
import game.NomadSurvival;
import game.gamePlayer.GPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.UUID;

public class ScoreBoardManager {
    private final HashMap<String, SBoard> scoreBoards = new HashMap<>();
    private final HashMap<UUID, String> playersAssignedBoard = new HashMap<>();
    private final HashMap<UUID, CurrentBoard> playerScoreBoards = new HashMap<>();
    private Boolean GenericFlip = true;

    {
        registerScoreBoards();
        updateScoreBoard();
    }


    private void registerScoreBoards() {
        registerScoreBoard(Scoreboard.MAIN, new Board_Main(this));
    }

    private void registerScoreBoard(Scoreboard scoreboard, SBoard sBoard) {
        scoreBoards.put(scoreboard.getBoardID(), sBoard);
    }

    public void assignBoardToPlayer(Scoreboard scoreboard, Player p) {
        String boardId = scoreboard.getBoardID();
        if (!scoreBoards.containsKey(boardId)) {
            Util.ERROR_REPORTER.report("Unable to assign board " + boardId + ", There is no board registered with that ID");
            return;
        }

        playersAssignedBoard.put(p.getUniqueId(), boardId);
    }


    private void updateScoreBoard() {
        Task.repeat(3, () -> {
            GenericFlip = !GenericFlip;

            for (Player p : Bukkit.getOnlinePlayers()) {
                GPlayer gPlayer = NomadSurvival.G_PLAYER_MANAGER.getGPlayer(p);
                String assignedBoardType = playersAssignedBoard.get(p.getUniqueId());

                if (assignedBoardType == null) {
                    return;
                }

                CurrentBoard currentBoard = playerScoreBoards.get(p.getUniqueId());

                if (currentBoard != null && !currentBoard.getScoreboard().equals(p.getScoreboard())) {
                    p.setScoreboard(currentBoard.getScoreboard());
                }

                if (HasScoreboard(p)) {

                    if (!currentBoard.getCurrentBoardName().equalsIgnoreCase(assignedBoardType)) {
                        Reset(currentBoard.getScoreboard());
                        SetUpScoreBoard(p, assignedBoardType);
                        currentBoard = playerScoreBoards.get(p.getUniqueId());
                    }

                    SBoard playersAssignedBoard = scoreBoards.get(assignedBoardType);

                    if (playersAssignedBoard == null) {
                        return;
                    }

                    if (p.isSneaking()){
                        playersAssignedBoard.updateScoreBoardSideBarSneaking(gPlayer, currentBoard.getScoreboard());

                    }else {
                        playersAssignedBoard.updateScoreBoardSideBarStanding(gPlayer, currentBoard.getScoreboard());
                    }

                    playersAssignedBoard.updateNames(currentBoard.getScoreboard());
                } else {
                    SetUpScoreBoard(p, assignedBoardType);
                }
            }
        });
    }

    private void SetUpScoreBoard(Player p, String boardType) {
        SBoard playersAssignedBoard = scoreBoards.get(boardType);

        if (playersAssignedBoard == null) {
            return;
        }

        org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective sideBoard = board.getObjective("SideBoard");

        if (sideBoard == null) {
            sideBoard = board.registerNewObjective("SideBoard", "SideBoard");
            sideBoard.setDisplaySlot(DisplaySlot.SIDEBAR);
            sideBoard.setDisplayName(Util.STRING.formatString(playersAssignedBoard.getBoardName()));
        }


        int LineID = 1;

        for (int i = 0; i < playersAssignedBoard.getLineCount(); i++) {
            Team team = board.registerNewTeam(Util.STRING.formatString("&" + LineID));
            team.addEntry(Util.STRING.formatString("&" + LineID));
            team.setPrefix(Util.STRING.formatString("&r-"));
            team.setSuffix(Util.STRING.formatString("&r-"));
            sideBoard.getScore(Util.STRING.formatString("&" + LineID)).setScore(8);
            LineID++;
        }

        SetupNameColorTeams(board);
        playerScoreBoards.put(p.getUniqueId(), new CurrentBoard(board, boardType));
        p.setScoreboard(board);
    }

    private void SetupNameColorTeams(org.bukkit.scoreboard.Scoreboard board) {
        for (ChatColor chatColor : ChatColor.values()) {
            Team team = board.registerNewTeam("NameColor-" + chatColor.getChar());
            team.setColor(chatColor);
        }
    }

    private boolean HasScoreboard(Player p) {
        return playerScoreBoards.containsKey(p.getUniqueId());
    }

    private void Reset(org.bukkit.scoreboard.Scoreboard scoreboard) {
        for (Team teams : scoreboard.getTeams()) {
            teams.unregister();
        }

        for (Objective objective : scoreboard.getObjectives()) {
            if (objective.getCriteria().equalsIgnoreCase("CW_SideBoard")) {
                objective.unregister();
            }
        }
    }
}
