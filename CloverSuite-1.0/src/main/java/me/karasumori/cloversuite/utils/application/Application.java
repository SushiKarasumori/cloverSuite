package me.karasumori.cloversuite.utils.application;

import me.karasumori.cloversuite.Main;
import me.karasumori.cloversuite.utils.MessageUtil;
import me.karasumori.cloversuite.utils.application.events.ApplyAnswerEvent;
import me.karasumori.cloversuite.utils.ApplyQuestionUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class Application {//stores players' answers to an array titled as their playername

    private Player appPlayer;
    private boolean applying;

    private boolean reviewed = false;
    private List<String> answers = new ArrayList<>();

    public Application(Player player) {
        ApplicationManager.addApplication(this);

        appPlayer = player;
        applying = false;
    }

    public Player getAppPlayer() { return appPlayer; }
    public boolean isApplying() { return applying; }
    public List<String> getAnswers() { return answers; }

    public boolean wasReviewed() { return reviewed; }

    public int getCurrentIndex() { return getAnswers().size(); }

    public void review() {reviewed = true; }

    public void giveAnswer(String message) {//stores cancelled player chat from FrozenChatHandler.java as string to answer array
        ApplyAnswerEvent event = new ApplyAnswerEvent(getAppPlayer(), this, message);

        if (event.isCancelled()) return;

        List<String> answerList = new ArrayList<>();
        for (String s : getAnswers()) {
            answerList.add(s);
        }
        answerList.add(message);
        answers = answerList;
    }



    public void sendNextQuestion() {
        if (getCurrentIndex() + 1 <= ApplyQuestionUtil.getApplyQuestions().size()) {
            getAppPlayer().sendMessage(MessageUtil.getQuestionMessage(getCurrentIndex()));
        }

        else {
            ApplicationManager.stop(getAppPlayer());
        }
    }

    public void start() {
        applying = true;
        ApplicationManager.start(getAppPlayer());
    }

    public void stop() {
        applying = false;
        ApplicationManager.stop(getAppPlayer());
    }
}
