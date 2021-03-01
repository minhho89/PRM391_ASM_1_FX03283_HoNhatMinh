package minhfx03283.funix.prm391_asm_1;

import android.content.Context;
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

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {

    private List<Quiz> quizzes;
    Context context;
    View view;

    public QuizAdapter(List<Quiz> quizzes, Context context) {
        this.quizzes = quizzes;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param quizzes List<Quiz> containing the data to populate views to be used
     * by RecyclerView.
     */
    public QuizAdapter(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_item, parent, false);

        return new ViewHolder(view);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Quiz quiz = quizzes.get(position);
        String positionNo = position + 1 + ". ";
        LinearLayout lnl = holder.lnl;

        holder.setIsRecyclable(false);

        // Add textView for question
        TextView tv = new TextView(lnl.getContext());
        tv.setText(positionNo + quiz.getQuestion());
        lnl.addView(tv);

        if (quiz instanceof QuizType1) {
            // Add option lists to radioButton
            RadioGroup rdGroup = new RadioGroup(lnl.getContext());
            int padding = (int) context.getResources().getDimension(R.dimen.padding_element);
            rdGroup.setPadding(0, padding, 0, 0);
            for (String s : ((QuizType1) quiz).getOptionList()) {
                RadioButton rb = new RadioButton(lnl.getContext());
                rb.setText(s);
                rb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                rdGroup.addView(rb);
            }
            lnl.addView(rdGroup);
        }

        if (quiz instanceof QuizType2) {
            for (String s : ((QuizType2) quiz).getOptionList()) {
                CheckBox cb = new CheckBox(lnl.getContext());
                cb.setText(positionNo + s);
                lnl.addView(cb);
            }
        }

        if (quiz instanceof QuizType3) {
            EditText edt = new EditText(lnl.getContext());
            edt.setHint("Your answer");
            lnl.addView(edt);
        }


    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lnl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lnl = (LinearLayout) itemView.findViewById(R.id.quiz_item_layout);
        }
    }
}
