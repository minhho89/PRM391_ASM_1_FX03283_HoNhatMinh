package minhfx03283.funix.prm391_asm_1;

import java.util.Set;

public class UserAnswer {
    private int quizId;
    private Set<String> mAnswers;
    private boolean mResult = false;

    public UserAnswer() {
    }

    public UserAnswer(int quizId, Set<String> mAnswers) {
        this.quizId = quizId;
        this.mAnswers = mAnswers;
    }

    public Set<String> getAnswers() {
        return mAnswers;
    }

    public void setAnswers(Set<String> answers) {
        this.mAnswers = answers;
    }

    public boolean isResult() {
        return mResult;
    }

    public void setResult(boolean result) {
        this.mResult = result;
    }


    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int userId) {
        this.quizId = userId;
    }

    @Override
    public String toString() {
        return "UserAnswer: " + "\n" +
                "answers: " + mAnswers + "\n" +
                "result: "  + mResult + "\n";
    }
}
