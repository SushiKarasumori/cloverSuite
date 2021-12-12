package me.karasumori.cloversuite.utils;

import me.karasumori.cloversuite.Main;

import java.util.ArrayList;
import java.util.List;

public class ApplyQuestionUtil {

    private static List<String> applyQuestions = new ArrayList<>();
    public static List<String> getApplyQuestions() { return applyQuestions; }

    public static String getApplyQuestion(int questionNum) {
        return getApplyQuestions().get(questionNum);
    }

    public static void loadApplyQuestions() { //use an enhanced for to loop through the array
        applyQuestions.clear();
        for (Object o : Main.getInstance().getConfig().getList("applications.questions")) {
            /*loops through every entry of the config
            list and adds the entry to the questions
            array until 'o' does not return a String*/
            if (!(o instanceof String)) return;
            applyQuestions.add((String) o);
        }
    }
}
