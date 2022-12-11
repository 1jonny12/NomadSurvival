package localUtil;

import java.util.concurrent.ThreadLocalRandom;

public class SecureKeyGenerator {

    public static void main(String[] args) {


        char c = 4;
        System.out.println(c);

        StringBuilder symbolKey = new StringBuilder();
        StringBuilder numericKey = new StringBuilder();

        for (int i = 0; i < 50; i++) {
            int random = randomInt(1, 999);
            numericKey.append(random);
            symbolKey.append((char) random);

        }

        System.out.println(numericKey);
        System.out.println(symbolKey);
    }

    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
