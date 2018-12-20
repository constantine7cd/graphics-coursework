package SentenceBuilder;

import AbstractLetter.AnimatedObject;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SentenceBuilder
{
    private static char[] russianAlphabet = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м',
            'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э',
            'ю', 'я'};


    public static List<AnimatedObject> extractObjectsFromString(String rawString, List<AnimatedObject> letters)
    {
        List <AnimatedObject> res = new ArrayList<AnimatedObject>();

        if (letters.size() != russianAlphabet.length)
        {
            return res;
        }

        String processedStr = processString(rawString);

        char c;
        int idx;

        for (int i = 0; i < processedStr.length(); ++i) {
            c = processedStr.charAt(i);

            res.add(letters.get(getIndex(c, russianAlphabet)));
        }

        return res;

    }

    public static int getIndex(char c, char[] array)
    {
        for (int i = 0; i < array.length; ++i) {
            if (c == array[i]) {
                return i;
            }
        }

        return 0;
    }

    private static String processString(String rawString)
    {
        String lowered = rawString.toLowerCase(new Locale("ru"));

        String res = new String();

        for (int i = 0; i < lowered.length(); i++) {
            char c = lowered.charAt(i);

            if (charIn(c, russianAlphabet)) {
                res += c;
            }
        }

        return res;
    }

    private static boolean charIn(char c, char[] array)
    {
        for (int i = 0; i < array.length; ++i) {
            if (c == array[i]) {
                return true;
            }
        }

        return false;
    }
}
