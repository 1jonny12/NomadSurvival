package core.utils;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util_String {

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    private static final HashMap<String, String> REPLACEMENTS = new HashMap<>();
    private static final Pattern HEX_PATTERN = Pattern.compile("<([A-Fa-f\\d]{6})>");
    private static final Pattern RGB_PATTERN = Pattern.compile("[*]+\\d+_\\d+_\\d+[*]");
    private static final char[] alphabet = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    {
        REPLACEMENTS.put("^m", "&9");
        REPLACEMENTS.put("^s", "&3");
        REPLACEMENTS.put("^e", "&c");

        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "B");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "QUAD");
        suffixes.put(1_000_000_000_000_000_000L, "QUIN");
    }

    /**
     * Will replace [Player] with the name of p
     */
    public String formatStringWithPlayerReplace(String s, Player p) {
        s = s.replaceAll("[Player]", p.getName());
        return formatString(s);
    }

    /**
     * To use the RGB colorCode you need to use *R_G_B*
     * Example "Hey you *10_20_256* okay"
     */
    public String formatString(String s) {
        s = formatReplacements(s);
        s = formatBukkitColorCodes(s);
        s = formatRBGColorCodes(s);
        s = formatHexColorCodes(s);
        return s;
    }

    public ArrayList<String> formatStringList(ArrayList<String> list) {
        ArrayList<String> formattedStrings = new ArrayList<>();
        for (String s : list) {
            formattedStrings.add(formatString(s));
        }
        return formattedStrings;
    }

    private String formatReplacements(String s) {
        for (Map.Entry<String, String> replacemnts : REPLACEMENTS.entrySet()) {
            s = s.replace(replacemnts.getKey(), replacemnts.getValue());
        }

        return s;
    }

    private String formatBukkitColorCodes(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private String formatRBGColorCodes(String message) {
        Matcher matcher = RGB_PATTERN.matcher(message);
        while (matcher.find()) {
            String match = matcher.group(0);

            String[] rgb = match.replace("*", "").split("_");
            String hex = "<" + String.format("%02x%02x%02x", Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[1])) + ">";
            message = message.replace(match, hex);
        }

        return message;

    }

    private String formatHexColorCodes(String message) {
        Matcher matcher = HEX_PATTERN.matcher(message);

        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

        while (matcher.find()) {
            String group = matcher.group(1);
            StringBuilder replaceBuilder = new StringBuilder().append(ChatColor.COLOR_CHAR).append("x");

            for (int i = 0; i <= 5; i++) {
                replaceBuilder.append(ChatColor.COLOR_CHAR).append(group.charAt(i));
            }

            matcher.appendReplacement(buffer, replaceBuilder.toString()
            );
        }

        return matcher.appendTail(buffer).toString();
    }

    public String removeFormat(final String Message) {
        return ChatColor.stripColor(formatString(Message));
    }

    public String formatMoneyToLetterMoney(long value) {
        if (value == Long.MIN_VALUE) {
            return formatMoneyToLetterMoney(Long.MIN_VALUE + 1);
        }
        if (value < 0) {
            return "-" + formatMoneyToLetterMoney(-value);
        }
        if (value < 1000) {
            return Long.toString(value);
        }

        final Map.Entry<Long, String> e = suffixes.floorEntry(value);
        final Long divideBy = e.getKey();
        final String suffix = e.getValue();
        final long truncated = value / (divideBy / 10);
        return truncated / 10d + suffix;
    }

    public String formatTimeStampToDateFormat(Long dateTimeStampMills) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(dateTimeStampMills));
    }

    public String locationToXYZString(Location l, String color) {
        return formatString(color + l.getX() + "&c/" + color + l.getY() + "&c/" + color + l.getZ());
    }

    public String toInvisible(final String text) {
        final StringBuilder hiddenText = new StringBuilder();

        for (final char c : text.toCharArray()) {
            hiddenText.append(ChatColor.COLOR_CHAR);
            hiddenText.append(c);
        }
        return hiddenText.toString();

    }

    public ArrayList<String> getCallTree(boolean includeFullError){
        ArrayList<String> calls = new ArrayList<>();
        for (StackTraceElement s : Thread.currentThread().getStackTrace()){

            String check = s.getClassName().toUpperCase();

            if (!includeFullError) {
                if (check.contains("NET.MINECRAFT") || check.contains("ORG.BUKKIT") || check.contains("JAVA.LANG") || check.contains("UTILS.ERRORREPORTER") || check.contains("JDK.INTERNAL")) continue;
                if (s.getMethodName().toUpperCase().contains("GETCALLTREE")) continue;
            }
            calls.add(s.toString());
        }

        return calls;

    }


    //----------------------------------------------------
    // [Default] -> Getters and Setters
    //----------------------------------------------------

    public char[] getAlphabet() {
        return alphabet;
    }
}
