package minhfx03283.funix.prm391_asm_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

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

    public QuizRecyclerAdapter(List<QuizRecyclerViewType> viewTypes) {
        this.viewTypes = viewTypes;
    }

    public QuizRecyclerAdapter(Context context, List<Quiz> quizzes) {
        this.context = context;
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
        Quiz quiz = quizzes.get(position);

        // Views of Type 1 quiz
        if (quiz instanceof QuizType1) {
            position = 0;
            ViewHolderType1 holderType1 = (ViewHolderType1) holder;
            QuizType1 type1Quiz = (QuizType1)quiz;
            for (String s : type1Quiz.getOptionList()) {
                RadioButton rb = new RadioButton(holderType1.getLinearLayout().getContext());
                rb.setText(s);
            }
        }
        switch (viewTypes.get(position).getViewType()) {

            case LAYOUT_1:


                break;
            case LAYOUT_2:
                break;
            case LAYOUT_3:
                break;
            default: return;
        }
    }
    @Override
    public int getItemCount() {
        return quizzes.size();
    }


    @Override
    public int getItemViewType(int position) {
        switch (viewTypes.get(position).getViewType()) {
            case 0:
                return LAYOUT_1;
            case 1:
                return LAYOUT_2;
            case 3:
                return LAYOUT_3;
            default:
                return -1;
        }
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

        // Method to set value of views that will be
        // used further in onBindViewHold method.
        private void setTvQuestion(String text) {
            tvQuestion.setText(text);
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }
    }

    public class ViewHolderType2 extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private TextView tvQuestion;

        public ViewHolderType2(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout_type_2);
            tvQuestion = (TextView) itemView.findViewById(R.id.tv_question);
        }
        private void setTvQuestion(String text) {
            tvQuestion.setText(text);
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
        private void setTvQuestion(String text) {
            tvQuestion.setText(text);
        }
        private void setEtAnswer(String text) {
            etAnswer.setText(text);
        }
    }
}
