package minhfx03283.funix.prm391_asm_1;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static minhfx03283.funix.prm391_asm_1.QuizRecyclerViewType.LAYOUT_1;
import static minhfx03283.funix.prm391_asm_1.QuizRecyclerViewType.LAYOUT_2;
import static minhfx03283.funix.prm391_asm_1.QuizRecyclerViewType.LAYOUT_3;

public class QuizRecyclerAdapter extends RecyclerView.Adapter {
    Context context;
    // List of quizzes to feed data
    List<Quiz> quizzes;
    // List of viewTypes
    private List<QuizRecyclerViewType> viewTypes;

    public QuizRecyclerAdapter(Context context, List<QuizRecyclerViewType> viewTypes, List<Quiz> quizzes) {
        this.context = context;
        this.viewTypes = viewTypes;
        this.quizzes = quizzes;
    }

    // Creates new ViewHolders (invoked by the layout manager)
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        switch (viewType) {
            case LAYOUT_1:
                View layout1 = LayoutInflater.from(context)
                        .inflate(R.layout.quiz_type_1, parent, false);
                return new ViewHolderType1(layout1);
            case LAYOUT_2:
                View layout2 = LayoutInflater.from(context)
                        .inflate(R.layout.quiz_type_2, parent, false);
                return new ViewHolderType2(layout2);
            case LAYOUT_3:
                View layout3 = LayoutInflater.from(context)
                        .inflate(R.layout.quiz_type_3, parent, false);
                return new ViewHolderType3(layout3);
            default:
                return null;
        }
    }

    // Replace the content of views
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final String TAG = "Quizzes iterator: ";
        Quiz quiz = quizzes.get(position);
        String numberOrder = position + 1 + ". ";

        if (quiz instanceof QuizType1) {
            // Views of Type 1 quiz
            if (quiz instanceof QuizType1) {

                ViewHolderType1 holderType1 = (ViewHolderType1) holder;
                QuizType1 quizType1 = (QuizType1) quiz;
                holderType1.setTvQuestion(numberOrder + quizType1.getQuiz());

                holderType1.removeDuplicate(holderType1.getRdOptions());

                for (String s : quizType1.getOptionList()) {
                    RadioButton rb = new RadioButton(
                            holderType1.getLinearLayout().getContext());
                    rb.setText(s);
                    holderType1.getRdOptions().addView(rb);
                }
            }
        }

        if (quiz instanceof QuizType2) {
            // Views of Type 2 quiz
            if (quiz instanceof QuizType2) {
                ViewHolderType2 holderType2 = (ViewHolderType2) holder;
                QuizType2 quizType2 = (QuizType2) quiz;
                holderType2.setTvQuestion(numberOrder + quizType2.getQuiz());

                // Remove checkboxes from duplicating
                holderType2.removeDuplicate(holderType2.getLinearLayoutCheckBoxes());

                for (String s : quizType2.getOptionList()) {
                    CheckBox cb = new CheckBox(
                            holderType2.getLinearLayout().getContext());
                    cb.setText(s);
                    holderType2.getLinearLayout().addView(cb);
                }
            }
        }

        if (quiz instanceof QuizType3) {
            ViewHolderType3 holderType3 = (ViewHolderType3) holder;
            QuizType3 quizType3 = (QuizType3) quiz;
            holderType3.setTvQuestion(numberOrder + quizType3.getQuiz());
        }

    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (quizzes.get(position) instanceof QuizType1) return LAYOUT_1;
        if (quizzes.get(position) instanceof QuizType2) return LAYOUT_2;
        return LAYOUT_3;
    }

    public class ViewHolderType1 extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private TextView tvQuestion;
        private RadioGroup rdOptions;

        public ViewHolderType1(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout_type_1);
            tvQuestion = (TextView) itemView.findViewById(R.id.tv_question);
            rdOptions = (RadioGroup) itemView.findViewById(R.id.rg_answers);
        }

        public TextView getTvQuestion() {
            return tvQuestion;
        }

        // Method to set value of views that will be
        // used further in onBindViewHold method.
        private void setTvQuestion(String text) {
            tvQuestion.setText(text);
        }

        public void setTvQuestion(TextView tvQuestion) {
            this.tvQuestion = tvQuestion;
        }

        public RadioGroup getRdOptions() {
            return rdOptions;
        }

        public void setRdOptions(RadioGroup rdOptions) {
            this.rdOptions = rdOptions;
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }

        public void setLinearLayout(LinearLayout linearLayout) {
            this.linearLayout = linearLayout;
        }

        /**
         * Dynamically added Views duplicate every time onBindViewHolder got invoked.
         * This method removes the duplicates view, in this case, radioButtons.
         * @param radioGroup the Recycler View's radioGroup
         */
        public void removeDuplicate(RadioGroup radioGroup) {
            radioGroup.removeAllViews();
        }
    }

    public class ViewHolderType2 extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private LinearLayout linearLayoutCheckBoxes;
        private TextView tvQuestion;

        public ViewHolderType2(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout_type_2);
            linearLayoutCheckBoxes = (LinearLayout) itemView.findViewById(R.id.linear_layout_type_2);
            tvQuestion = (TextView) itemView.findViewById(R.id.tv_question);
        }


        public LinearLayout getLinearLayout() {
            return linearLayout;
        }

        public void setLinearLayout(LinearLayout linearLayout) {
            this.linearLayout = linearLayout;
        }

        public LinearLayout getLinearLayoutCheckBoxes() {
            return linearLayoutCheckBoxes;
        }

        public void setLinearLayoutCheckBoxes(LinearLayout linearLayoutCheckBoxes) {
            this.linearLayoutCheckBoxes = linearLayoutCheckBoxes;
        }

        public TextView getTvQuestion() {
            return tvQuestion;
        }

        private void setTvQuestion(String text) {
            tvQuestion.setText(text);
        }

        public void setTvQuestion(TextView tvQuestion) {
            this.tvQuestion = tvQuestion;
        }

        /**
         * Dynamically added Views duplicate every time onBindViewHolder got invoked.
         * This method removes the duplicates view, in this case, Checkboxes.
         * @param layoutCheckBoxes ViewHolder's linearLayout that holds checkBox items.
         */
        public void removeDuplicate(LinearLayout layoutCheckBoxes) {
            layoutCheckBoxes.removeAllViews();
        }
    }

    public class ViewHolderType3 extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private TextView tvQuestion;
        private EditText etAnswer;

        public ViewHolderType3(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout_type_3);
            tvQuestion = (TextView) itemView.findViewById(R.id.tv_question);
            etAnswer = (EditText) itemView.findViewById(R.id.et_user_answer);
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }

        public void setLinearLayout(LinearLayout linearLayout) {
            this.linearLayout = linearLayout;
        }

        public TextView getTvQuestion() {
            return tvQuestion;
        }

        private void setTvQuestion(String text) {
            tvQuestion.setText(text);
        }

        public void setTvQuestion(TextView tvQuestion) {
            this.tvQuestion = tvQuestion;
        }

        public EditText getEtAnswer() {
            return etAnswer;
        }

        private void setEtAnswer(String text) {
            etAnswer.setText(text);
        }

        public void setEtAnswer(EditText etAnswer) {
            this.etAnswer = etAnswer;
        }
    }
}
