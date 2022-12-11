package core.scoreboard.Boards;

import Groups.GroupType;
import PrisonPlayers.PrisonPlayer;
import RPGPRISON.RpgPrison;
import SmallFeatures.ServerTime;
import core.scoreboard.SBoard;
import core.scoreboard.ScoreBoardManager;
import org.bukkit.scoreboard.Scoreboard;

public class Board_Main extends SBoard {


    public Board_Main(ScoreBoardManager scoreBoardManager) {
        super(scoreBoardManager);
    }

    @Override
    public String getBoardName() {
        return "^eMC Lockup";
    }

    @Override
    public int getLineCount() {
        return 6;
    }

    @Override
    public void updateScoreBoardSideBarStanding(PrisonPlayer pp, Scoreboard scoreboard) {
        ServerTime current = RpgPrison.SMALL_FEATURE_MANAGER.getServerTimeController().getCurrentTime();
        updateStaticLine(scoreboard, 1, "&7Time&8: " , current.getTimeString_24h("^m") + current.getDayNightSymbol());
        updateStaticLine(scoreboard, 2, "", "");
        updateStaticLine(scoreboard, 3, "&7Bal&8: ", "^m" + RpgPrison.getEconomy().getBalance(pp.getName()));
        updateStaticLine(scoreboard, 4, "", "");
        updateStaticLine(scoreboard, 5, "", "");
        updateStaticLine(scoreboard, 6, "", "");

    }

    @Override
    public void updateScoreBoardSideBarSneaking(PrisonPlayer pp, Scoreboard scoreboard) {
        updateStaticLine(scoreboard, 1, "^mReputation" , "");
        updateStaticLine(scoreboard, 2, GroupType.GUARDS.getColorCode() + GroupType.GUARDS.getSymbol() + "&7 ▸ ", GroupType.GUARDS.getColorCode() + pp.getReputaion().getValue(GroupType.GUARDS));
        updateStaticLine(scoreboard, 3, GroupType.KNIGHTS.getColorCode() + GroupType.KNIGHTS.getSymbol() + "&7 ▸ ", GroupType.KNIGHTS.getColorCode() + pp.getReputaion().getValue(GroupType.KNIGHTS));
        updateStaticLine(scoreboard, 4, GroupType.REAPERS.getColorCode() + GroupType.REAPERS.getSymbol() + "&7 ▸ ", GroupType.REAPERS.getColorCode() + pp.getReputaion().getValue(GroupType.REAPERS));
        updateStaticLine(scoreboard, 5, GroupType.ROYALS.getColorCode() + GroupType.ROYALS.getSymbol() + "&7 ▸ ", GroupType.ROYALS.getColorCode() + pp.getReputaion().getValue(GroupType.ROYALS));
        updateStaticLine(scoreboard, 6, GroupType.SLIMEZ.getColorCode() + GroupType.SLIMEZ.getSymbol() + "&7 ▸ ", GroupType.SLIMEZ.getColorCode() + pp.getReputaion().getValue(GroupType.SLIMEZ));

    }

    @Override
    public void updateNames(Scoreboard scoreboard) {

    }
}
