package minhfx03283.funix.prm391_asm_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {

    private List<Quiz> quizzes;
    Context context;

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
        LinearLayout lln = holder.lln;

        TextView tv = new TextView(lln.getContext());
        tv.setText(positionNo + quiz.getQuestion());

        lln.addView(tv);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lln;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lln = (LinearLayout) itemView.findViewById(R.id.quiz_item_layout);
        }
    }
}
