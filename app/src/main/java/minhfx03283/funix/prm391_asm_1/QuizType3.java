package minhfx03283.funix.prm391_asm_1;

import java.util.Set;

/**
 * This question type requires users to enter an answers.
 * Since the correct answers might be plenty, they will be hold in a set of mAnswers.
 *
 */

public class QuizType3 extends Quiz{
    private Set<String> mAnswers;

    public QuizType3() {
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
                "mId: " + super.getId() + "\n" +
                "mQuiz: " + super.getQuiz() + "\n" +
                "mAnswers: " + mAnswers;
    }

    /**
     * Check if user's answer is correct.
     * @param userAnswer user's answer.
     * @return true if userAnswer matches one element in the correct answers set.
     */
    public boolean checkResult(String userAnswer) {
        for (String s : this.mAnswers) {
                if (s.equalsIgnoreCase(userAnswer)) return true;
            }
        return false;
    }

}
