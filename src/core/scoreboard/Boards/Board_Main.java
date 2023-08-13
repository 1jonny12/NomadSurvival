package core.scoreboard.Boards;

import core.scoreboard.SBoard;
import core.scoreboard.ScoreBoardManager;
import game.NomadSurvival;
import game.gamePlayer.GPlayer;
import org.bukkit.scoreboard.Scoreboard;

public class Board_Main extends SBoard {


    public Board_Main(ScoreBoardManager scoreBoardManager) {
        super(scoreBoardManager);
    }

    @Override
    public String getBoardName() {
        return "";
    }

    @Override
    public int getLineCount() {
        return 2;
    }

    @Override
    public void updateScoreBoardSideBarStanding(GPlayer gPlayer, Scoreboard scoreboard) {
        updateStaticLine(scoreboard,  1, "\uE036" + NomadSurvival.RESOURCE_PACK_MANAGER.getTexturedProgressBar().getBar((int) gPlayer.getSmell(), (int) gPlayer.getMaxSmell()),"");
        updateStaticLine(scoreboard,  2, "\uE035" + NomadSurvival.RESOURCE_PACK_MANAGER.getTexturedProgressBar().getBar(gPlayer.getNoise().getTotalNoise(), 400),"");



    }

    @Override
    public void updateScoreBoardSideBarSneaking(GPlayer gPlayer, Scoreboard scoreboard) {
        updateStaticLine(scoreboard,  1, "\uE035","");
    }

    @Override
    public void updateNames(Scoreboard scoreboard) {

    }
}
