package game.ResourcePack;

import org.bukkit.Bukkit;

import java.util.HashMap;

public class TexturedProgressBar {

    HashMap<Integer, String> barStages = new HashMap<>();

    {
        barStages.put(0, "\uE000");
        barStages.put(1, "\uE001");
        barStages.put(2, "\uE002");
        barStages.put(3, "\uE003");
        barStages.put(4, "\uE004");
        barStages.put(5, "\uE005");
        barStages.put(6, "\uE006");
        barStages.put(7, "\uE007");
        barStages.put(8, "\uE008");
        barStages.put(9, "\uE009");
        barStages.put(10, "\uE010");
        barStages.put(11, "\uE011");
        barStages.put(12, "\uE012");
        barStages.put(13, "\uE013");
        barStages.put(14, "\uE014");
        barStages.put(15, "\uE015");
        barStages.put(16, "\uE016");
        barStages.put(17, "\uE017");
        barStages.put(18, "\uE018");
        barStages.put(19, "\uE019");
        barStages.put(20, "\uE020");
        barStages.put(21, "\uE021");
        barStages.put(22, "\uE022");
        barStages.put(23, "\uE023");
        barStages.put(24, "\uE024");
        barStages.put(25, "\uE025");
        barStages.put(26, "\uE026");
        barStages.put(27, "\uE027");
        barStages.put(28, "\uE028");
        barStages.put(29, "\uE029");
        barStages.put(30, "\uE030");
        barStages.put(31, "\uE031");
        barStages.put(32, "\uE032");
        barStages.put(33, "\uE033");
        barStages.put(34, "\uE034");
    }



    public String getBar(double progress, double total) {
        return barStages.get((int) (progress / (total / 34)));

    }

}
