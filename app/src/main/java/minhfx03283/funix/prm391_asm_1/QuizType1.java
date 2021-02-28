package minhfx03283.funix.prm391_asm_1;

import java.util.Set;

/**
 * This question type is for single choice option (radioButtons will be displayed in UI).
 * mOptionsList is a set of multiple choice questions for users to pick.
 */
public class QuizType1 extends Quiz {
    private Set<String> mOptionList;

    public QuizType1() {
    }


    public Set<String> getOptionList() {
        return mOptionList;
    }

    public void setOptionList(Set<String> mOptionList) {
        this.mOptionList = mOptionList;
    }

}
