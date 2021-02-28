package minhfx03283.funix.prm391_asm_1;

import java.util.Set;

public class UserAnswer {
    private Set<String> answers;
    private boolean result;

    public UserAnswer() {
    }

    public Set<String> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<String> answers) {
        this.answers = answers;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "UserAnswer: " + "\n" +
                "answers: " + answers + "\n" +
                "result: "  + result;
    }
}
