import com.seb.pa4.exception.*;

import java.util.*;

public class PatternFinder {
    private static String randomStringGenerator(int length) {// generates a string made of randomly generated lowercase
        // letters.
        Random random = new Random(System.nanoTime());
        char[] array = new char[length];
        for (int i = 0; i < length; i++)
            array[i] = (char) ('a' + random.nextInt(26));
        return new String(array);
    }

    private static void singletonMiner(String mine, int length) throws SingletonException {
        for (int start = 0; start < mine.length() - length; start++) {
            int i;
            for (i = start + 1; i < start + length; i++)
                if (mine.charAt(i) != mine.charAt(i - 1))
                    break;
            if (i == start + length)
                throw new SingletonException(mine.substring(start, start + length), start);
        }
    }

    private static void arithmeticMiner(String mine, int length) throws ArithmeticOrder1Exception, ArithmeticOrderMinus1Exception {
        for (int start = 0; start <= mine.length() - length; start++) {
            boolean isAscending = true;
            boolean isDescending = true;

            for (int i = 1; i < length; i++) {
                char prev = mine.charAt(start + i - 1);
                char curr = mine.charAt(start + i);

                if (curr != prev + 1) isAscending = false;
                if (curr != prev - 1) isDescending = false;

                if (!isAscending && !isDescending) break;
            }

            if (isAscending)
                throw new ArithmeticOrder1Exception(mine.substring(start, start + length), start);
            else if (isDescending)
                throw new ArithmeticOrderMinus1Exception(mine.substring(start, start + length), start);
        }
    }


    private static void balancedMiner(String mine, int length) throws BipartiteException, TripartiteException {
        if (length % 2 == 0) {
            // Bipartite check: 2 identical halves
            for (int start = 0; start <= mine.length() - length; start++) {
                String firstHalf = mine.substring(start, start + length / 2);
                String secondHalf = mine.substring(start + length / 2, start + length);

                if (firstHalf.equals(secondHalf)) {
                    throw new BipartiteException(mine.substring(start, start + length), start);
                }
            }
        }

        if (length % 3 == 0) {
            // Tripartite check: 3 identical parts
            for (int start = 0; start <= mine.length() - length; start++) {
                int partLen = length / 3;
                String part1 = mine.substring(start, start + partLen);
                String part2 = mine.substring(start + partLen, start + 2 * partLen);
                String part3 = mine.substring(start + 2 * partLen, start + length);

                if (part1.equals(part2) && part2.equals(part3)) {
                    throw new TripartiteException(mine.substring(start, start + length), start);
                }
            }
        }
    }


    private static void palindromeMiner(String mine, int length) throws PalindromeException {
        for (int start = 0; start <= mine.length() - length; start++) {
            String sub = mine.substring(start, start + length);
            boolean isPalindrome = true;

            for (int i = 0; i < length / 2; i++) {
                if (sub.charAt(i) != sub.charAt(length - 1 - i)) {
                    isPalindrome = false;
                    break;
                }
            }

            if (isPalindrome) {
                throw new PalindromeException(sub, start);
            }
        }
    }


    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        //Step 1: handling input...
        //Handle input for length or random string
        System.out.println("Enter the length of random string: ");
        int randomStringLength = keyboard.nextInt();
        while (true) {
            try {
                if (randomStringLength < 100000 || randomStringLength > 1000000000)
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("Try again!");
                randomStringLength = keyboard.nextInt();
                continue;
            }
            break;
        }

        //handle input for length of maximum pattern length
        System.out.println("Enter the length of the maximum pattern: ");
        int patternMaxLength = keyboard.nextInt();
        while (true) {
            try {
                if (patternMaxLength < 3 || patternMaxLength > 15)
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("Try again! Enter a number between 3 and 15 inclusive.");
                patternMaxLength = keyboard.nextInt();
                continue;
            }
            break;
        }


        //Step 2: generating random string...
        String randomString = randomStringGenerator(randomStringLength);
        //Step 3: finding the interesting patterns
        for (int length = patternMaxLength; length >= 1; length--) {
            try {
                // 1️⃣ Singleton
                singletonMiner(randomString, length);
            } catch (SingletonException e) {
                System.out.println(e.getMessage());
                break;
            }

            try {
                // 2️⃣ Arithmetic +1 / -1
                arithmeticMiner(randomString, length);
            } catch (ArithmeticOrder1Exception | ArithmeticOrderMinus1Exception e) {
                System.out.println(e.getMessage());
                break;
            }

            try {
                // 3️⃣ Balanced (tripartite and bipartite)
                balancedMiner(randomString, length);
            } catch (TripartiteException | BipartiteException e) {
                System.out.println(e.getMessage());
                break;
            }

            try {
                // 4️⃣ Palindrome
                palindromeMiner(randomString, length);
            } catch (PalindromeException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }
}
