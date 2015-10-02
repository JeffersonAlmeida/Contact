package rga.contact.util;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

public class UIHelper {

    private static final ColorGenerator COLOR_GENERATOR;

    private static final int MAX_LETTERS_PROFILE_IMAGE = 2;
    private static final int MAX_LETTERS_UI_NAME = 20;

    static {
        COLOR_GENERATOR = ColorGenerator.MATERIAL;
    }

    public static TextDrawable getTextDrawable(String fullName) {
        int color = COLOR_GENERATOR.getRandomColor();
        String initialLetters = getInitialLetters(fullName);
        return TextDrawable.builder().
                beginConfig().bold().toUpperCase().endConfig()
                .buildRound(initialLetters, color);
    }

    public static String getInitialLetters(String name){
        if (name.isEmpty() )
            return name;

        String[] words = name.trim().split("\\s+");
        int length = words.length;
        int limit = ( length >= MAX_LETTERS_PROFILE_IMAGE ) ?
                MAX_LETTERS_PROFILE_IMAGE : length;

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0 ; i < limit ; i++ ){
            char letter = words[i].charAt(0);
            stringBuilder.append(letter);
        }

        return stringBuilder.toString();
    }

    public static String getSuitableName(String name){
        String suitableName = name.trim();
        int start = 0, end = MAX_LETTERS_UI_NAME - 3 ;
        return ( suitableName.length() > MAX_LETTERS_UI_NAME ) ?
                suitableName.substring(start, end).trim() + "..." :
                suitableName;
    }

}
