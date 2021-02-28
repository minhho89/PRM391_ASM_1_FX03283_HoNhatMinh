package minhfx03283.funix.prm391_asm_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.ViewHolder> {
    Context context;
    // List of quizzes to feed data
    List<Quiz> quizzes;

    // Creates new ViewHolders (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        // Inflate to the quiz_recycler layout to get the reference
        View quizView = LayoutInflater.from(context).inflate(R.layout.quiz_recycler, null);

        // Create a new ViewHolder
        ViewHolder viewHolder = new ViewHolder(quizView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout_type_one);
        }
    }
}
