package minhfx03283.funix.prm391_asm_1.models;

import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class UserAnswersSet {
    public HashMap<Integer, UserAnswer> userAnswersHashMap() {
        return mUserAnswersHashMap;
    }

    private HashMap<Integer, UserAnswer> mUserAnswersHashMap;
    private int mScore;

    public UserAnswersSet() {
    }

    public UserAnswersSet(HashMap<Integer, UserAnswer> userAnswerHashMap) {
        this.mUserAnswersHashMap = userAnswerHashMap;
    }

    public HashMap<Integer, UserAnswer> getmUserAnswersHashMap() {
        return mUserAnswersHashMap;
    }

    public void setUserAnswersHashMap(HashMap<Integer, UserAnswer> userAnswersHashMap) {
        this.mUserAnswersHashMap = userAnswersHashMap;
    }



    public int getScore() {
        return mScore;
    }

    public void setScore(int mScore) {
        this.mScore = mScore;
    }

    @Override
    public String toString() {
        return "UserAnswers: " +
                "UserAnswerSets: " + mUserAnswersHashMap.toString() + "\n" +
                "Score: " + mScore + "\n";
    }

    /**
     * Counts the total correct answers.
     * @return
     */
    public int calculateScore() {
        int count = 0;
        for (Map.Entry m : mUserAnswersHashMap.entrySet()) {
            UserAnswer userAnswer = (UserAnswer) m.getValue();
            if (userAnswer.isResult()) count++;
        }
        return count;
    }

    /**
     * Evaluates an answer.
     * @param quiz
     */
    public void evaluateResult(Quiz quiz) {
        // If instance has not yet initialized, create an empty one
        if (this.mUserAnswersHashMap.get(quiz.getId()) == null){
            UserAnswer ua = new UserAnswer();
            ua.setQuizId(quiz.getId());
            Set<String> empty = new HashSet<>(Arrays.asList());
            ua.setAnswers(empty);
            ua.setResult(false);
        } else {
            UserAnswer userAnswer = this.mUserAnswersHashMap.get(quiz.getId());
            Log.d("TAG", "evaluateResult: " + userAnswer.toString());
            if (!(quiz instanceof QuizType3)) {
                // if answers has not yet added, add an empty one
                if (userAnswer.getAnswers() == null) {
                    userAnswer.setAnswers(new HashSet<>(Arrays.asList("")));
                } else {
                    userAnswer.setResult(quiz.checkResult(userAnswer.getAnswers()));
                }
            } else {
                // QuizType3
                String s = "";
                if(userAnswer.getAnswers() == null) {
                    userAnswer.setAnswers(new HashSet<>());
                } else {
                    Iterator it = userAnswer.getAnswers().iterator();
                    while (it.hasNext()) {
                        s = it.next().toString();
                        userAnswer.setResult(((QuizType3) quiz).checkResult(s));
                    }
                }
            }
        }

    }
}
