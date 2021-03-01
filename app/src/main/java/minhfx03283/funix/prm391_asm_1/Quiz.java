package minhfx03283.funix.prm391_asm_1;

import java.util.HashSet;
import java.util.Set;

/**
 * An abstraction class of Quiz requires 3 main fields:
 * - ID: auto-increment number: when a object of Quiz created without any params.
 * - Quiz: question of quiz.
 * - Answers: a set of correct answers.
 */
public abstract class Quiz {
    private static int mCount = 0;
    private int mId;
    private String mQuestion;
    private Set<String> mAnswers;

    // Auto generate Id number for everytime an instance created
    public Quiz() {
        this.mId = ++mCount;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuiz(String mQuiz) {
        this.mQuestion = mQuiz;
    }

    public Set<String> getAnswers() {
        return mAnswers;
    }

    public void setAnswers(Set<String> mAnswers) {
        this.mAnswers = mAnswers;
    }

    @Override
    public String toString() {
        return "Quiz" + "\n" +
                "mId: " + mId + "\n" +
                "mQuiz: " + mQuestion + "\n" +
                "mAnswers: " + mAnswers;
    }

    /**
     * Check if user's answer is correct.
     * @param userAnswers user's answers set.
     * @return true if userAnswers matches the correct answers set.
     */
    public boolean checkResult(Set<String> userAnswers) {
        if (!userAnswers.isEmpty() || userAnswers!=null) {
            return mAnswers.equals(userAnswers);
        }
        return false;
    }
}
