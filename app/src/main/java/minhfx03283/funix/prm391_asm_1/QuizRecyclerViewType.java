package minhfx03283.funix.prm391_asm_1;

/**
 * Defines view type for Object ViewHolder for each question type
 */
public class QuizRecyclerViewType {
    public static final int LAYOUT_1 = 0;
    public static final int LAYOUT_2 = 1;
    public static final int LAYOUT_3 = 2;

    private int viewType;
    // Elements of quizzes
    private String question;

    public QuizRecyclerViewType(int viewType) {
        this.viewType = viewType;
    }

    public QuizRecyclerViewType(int viewType, String question) {
        this.viewType = viewType;
        this.question = question;
    }

    public static int getLayout1() {
        return LAYOUT_1;
    }

    public static int getLayout2() {
        return LAYOUT_2;
    }

    public static int getLayout3() {
        return LAYOUT_3;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }


}
