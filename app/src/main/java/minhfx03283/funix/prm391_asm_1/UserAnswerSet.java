package minhfx03283.funix.prm391_asm_1;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Calculates the total score of answers.
 */
public class UserAnswerSet {
    private HashMap<Integer, UserAnswer> mUserAnswerHashMap;
    private int mScore;

    public UserAnswerSet() {
    }

    public UserAnswerSet(HashMap<Integer, UserAnswer> userAnswerHashMap) {
        this.mUserAnswerHashMap = userAnswerHashMap;
    }

    public HashMap<Integer, UserAnswer> getUserAnswerSets() {
        return mUserAnswerHashMap;
    }

    public void setUserAnswerSets(HashMap<Integer, UserAnswer> mUserAnswerSets) {
        this.mUserAnswerHashMap = mUserAnswerSets;
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
                "UserAnswerSets: " + mUserAnswerHashMap.toString() + "\n" +
                "Score: " + mScore + "\n";
    }

    /**
     * Counts the total correct answers.
     * @return
     */
    public int calculateScore() {
        int count = 0;
        for (Map.Entry m : mUserAnswerHashMap.entrySet()) {
            UserAnswer userAnswer = (UserAnswer) m.getValue();
            if (userAnswer.isResult()) count++;
        }
        return count;
    }

    /**
     * Evaluates an answer.
     * @param quiz
     */
    private void evaluateResult(Quiz quiz) {
        UserAnswer userAnswer = this.mUserAnswerHashMap.get(quiz.getId());
        if (!(quiz instanceof QuizType3)) {
            userAnswer.setResult(quiz.checkResult(userAnswer.getAnswers()));
        } else {
            // QuizType3
            String s = "";
            if(userAnswer.getAnswers()!=null) {
                Iterator it = userAnswer.getAnswers().iterator();
                while (it.hasNext()) {
                    s = it.next().toString();
                    userAnswer.setResult(((QuizType3) quiz).checkResult(s));
                }
            }
        }
    }
}
