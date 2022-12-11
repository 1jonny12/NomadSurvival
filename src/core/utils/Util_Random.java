package core.utils;

import org.bukkit.Location;

import java.util.concurrent.ThreadLocalRandom;

public class Util_Random {

    public int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public double randomDouble(double min, double max) {return ThreadLocalRandom.current().nextDouble(min, max); }

    public char randomLetter(boolean uppercase) {
        return uppercase ? Character.toUpperCase(Util.STRING.getAlphabet()[randomInt(0, 25)]) : Util.STRING.getAlphabet()[randomInt(0, 25)];
    }

    public boolean oneIn(int chance) {
        return randomInt(0, chance) == 0;
    }

    public boolean percentChance(final int Chance) {
        return Math.random() * 100.0D < Chance;
    }


}
