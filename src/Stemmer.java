import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by boggs on 9/19/15.
 */
public class Stemmer {
    public Writer writer;
    public Scanner scanner;
    public int counter = 0;
    public int trueCounter = 0;
    public HashMap<String, String> map = new HashMap<String, String>();
    public HashMap<String, Boolean> verifyMap = new HashMap<String, Boolean>();

    public Stemmer() {

    }

    public Stemmer(Writer writer, Scanner scanner) {
        this.writer = writer;
        this.scanner = scanner;
    }

    public String stem(String word) {
        String original = word;
        if(!map.containsKey(original)) {
            word = word.toLowerCase();
            word = isAccepted(word, removePrefixes(word));
            word = isAccepted(word, removeSuffixes(word));
            word = isAccepted(word, removeRepeating(word));
            if(!original.toLowerCase().equals(word)){
                map.put(original, word);
                return word;
            }
//            System.out.println(String.format("%s %s", original, word));
        } else {
//            word = word.toLowerCase();
//            word = isAccepted(word, removePrefixes(word));
//            word = isAccepted(word, removeSuffixes(word));
//            word = isAccepted(word, removeRepeating(word));
//            return word;
        }
        word = word.toLowerCase();
        word = isAccepted(word, removePrefixes(word));
        word = isAccepted(word, removeSuffixes(word));
        word = isAccepted(word, removeRepeating(word));
        return word;
    }

    public void stem(String word, boolean verbose) {
        stem(word);
        String newWord = map.get(word);
        System.out.println(String.format("in %s out %s", word.toLowerCase(), newWord));
    }

    public String isAccepted(String original, String modified) {
        boolean returnModified = false;
        if (modified.length() <= 0) {
            returnModified =  false;
        } else if(vowelCount(String.valueOf(modified.charAt(0))) > 0) {
            if(modified.length() >= 3 && consonantCount(modified) >= 1) {
                returnModified = true;
            }
        } else if (consonantCount(String.valueOf(modified.charAt(0))) > 0) {
            if(modified.length() >= 4 && vowelCount(modified) >= 1) {
                returnModified = true;
            }
        }

        if(returnModified)
            return modified;
        else
            return original;
    }

    public int vowelCount(String word) {
        int counter = 0;
        ArrayList<Character> vowels = new ArrayList<Character>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
        char[] chars = word.toCharArray();

        for(char c : chars) {
            if(vowels.contains(c)) {
                counter++;
            }
        }

        return counter;
    }

    public int consonantCount(String word) {
        int counter = 0;
        ArrayList<Character> consonants = new ArrayList<Character>(Arrays.asList('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'z'));
        char[] chars = word.toCharArray();

        for(char c : chars) {
            if(consonants.contains(c)) {
                counter++;
            }
        }

        return counter;
    }


    public String removePrefixes(String word) {
        String[] prefixes = {"pinag" ,"maka", "maki", "mang", "mag", "ma", "nang", "nag", "na", "pag", "pan", "pa", "in", "ka"};
        for(String prefix : prefixes) {
            if(word.indexOf(prefix) == 0) {
                word = word.substring(prefix.length());
                if (word.length() > 0 && word.charAt(0) == '-') {
                    word = word.substring(1);
                }
                break;
            }
        }
        return word;
    }

    public String removeSuffixes(String word) {
        String[] suffixes = {"han", "hin", "in", "an", "ng", "g"};
        for(String suffix : suffixes) {
            if(word.indexOf(suffix) > 0 && word.indexOf(suffix) + suffix.length() == word.length()) {
                word = word.substring(0, word.length() - suffix.length());
                if (word.charAt(word.length() - 1) == '-') {
                    word = word.substring(0, word.length() - 1);
                }
                break;
            }
        }
        return word;
    }

    public String removeRepeating(String word) {
        ArrayList<Character> vowels = new ArrayList<Character>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
        int[] blockSizes = {3, 2, 1};
        for(int blockSize : blockSizes) {
            int baseIndex = 0;

            while(baseIndex + blockSize + blockSize <= word.length()) {
                String block1 = word.substring(baseIndex, baseIndex + blockSize);
                String block2 = word.substring(baseIndex + blockSize, baseIndex + blockSize + blockSize);
                if(blockSize == 1) {
                    if(block1.equals(block2) && vowels.contains(block1.charAt(0)))
                        word = word.replace(block1.concat(block2), block2);
                } else {
                    if(block1.equals(block2))
                        word = word.replace(block1.concat(block2), block2);
                }
                baseIndex++;
            }
        }

//        word = word.replaceAll("(.)\\1{1,}", "$1");
        System.out.println(word);
        return word;
    }

    public void write() {
        writer.writeLine("");
        Object[] keys = map.keySet().toArray();
        writer.writeLine("Stemmed");
        for(Object o : keys) {
            String s = (String) o;
            writer.writeLine(s.toLowerCase() + ", " + map.get(s));
        }
    }

    public void verification(double percentage) {
        int iterations = (int) (percentage * counter) + 1;
        int index = 0;
        Object[] keys = map.keySet().toArray();
        String value = "";
        for(int i = 0; i < iterations; i++) {
            do {
                Random r = new Random();
                index = r.nextInt(keys.length);
                value = map.get(keys[index]);
            } while (verifyMap.containsKey(keys[index]));
            ask((String) keys[index], value, (i + 1) + " of " + iterations);
        }
    }

    public void ask(String key, String value, String progress) {
        System.out.println(progress + " - Is " + key + " an instance of " + value + "? (y/n): ");
        String in = scanner.nextLine();
        boolean response = in.equals("y");
        System.out.println(response);
        verifyMap.put(key, response);
    }

    public void countVerification() {
        Object[] keys = verifyMap.keySet().toArray();
        for(Object o : keys) {
            String key = (String) o;
            if(verifyMap.get(key))
                trueCounter++;
        }
    }

    public void writeVerification() {
        writer.writeLine("");
        Object[] keys = verifyMap.keySet().toArray();
        ArrayList<String> lines = new ArrayList<String>();
        trueCounter = 0;
        countVerification();
        writer.writeLine("Verification:  (" + trueCounter  + " correct of " + keys.length + ")");
        for(Object o : keys) {
            String key = (String) o;
            writer.writeLine(key + ",  - " + verifyMap.get(key));
        }
    }

    public void writeVerificationCounter() {
        countVerification();
        int max = verifyMap.keySet().toArray().length;
        writer.writeLine("Verification: , " + trueCounter + " correct of " + max + " (" + (trueCounter * 1.00 / max) * 100 + "%)");
    }

    public static void main(String[] args) {
        Stemmer test = new Stemmer();
        test.stem("baba", true);
        test.stem("baabaa", true);
        test.stem("baabaaa", true);
        test.stem("babala", true);
        test.stem("bababa", true);
        test.stem("babaan", true);
        test.stem("halalan", true);
    }
}
