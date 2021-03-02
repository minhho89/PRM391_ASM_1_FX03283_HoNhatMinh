package minhfx03283.funix.prm391_asm_1.models;


import java.util.Set;

/**
 * This question type is for multiple choice option (checkboxes will be displayed in UI).
 * mOptionsList is a set of multiple choice questions for users to pick.
 */
public class QuizType2 extends Quiz {
    private Set<String> mOptionList;

    public Set<String> getOptionList() {
        return mOptionList;
    }

    public void setOptionList(Set<String> mOptionList) {
        this.mOptionList = mOptionList;
    }
}
