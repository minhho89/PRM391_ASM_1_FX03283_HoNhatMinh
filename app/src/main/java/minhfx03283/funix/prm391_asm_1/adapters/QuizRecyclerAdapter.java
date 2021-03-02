package minhfx03283.funix.prm391_asm_1.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import minhfx03283.funix.prm391_asm_1.R;
import minhfx03283.funix.prm391_asm_1.models.Quiz;
import minhfx03283.funix.prm391_asm_1.models.QuizType1;
import minhfx03283.funix.prm391_asm_1.models.QuizType2;
import minhfx03283.funix.prm391_asm_1.models.QuizType3;
import minhfx03283.funix.prm391_asm_1.models.UserAnswer;
import minhfx03283.funix.prm391_asm_1.models.UserAnswersSet;

import static android.content.ContentValues.TAG;
import static minhfx03283.funix.prm391_asm_1.adapters.QuizRecyclerViewType.LAYOUT_1;
import static minhfx03283.funix.prm391_asm_1.adapters.QuizRecyclerViewType.LAYOUT_2;
import static minhfx03283.funix.prm391_asm_1.adapters.QuizRecyclerViewType.LAYOUT_3;
import static minhfx03283.funix.prm391_asm_1.adapters.QuizRecyclerViewType.LAYOUT_BUTTON;

public class QuizRecyclerAdapter extends RecyclerView.Adapter {
    // To store checked RadioButton
    private final HashMap<Integer, String> radioBtnCheckedHshMap = new HashMap<>();

    // List of viewTypes
    private final List<QuizRecyclerViewType> viewTypes;
    private UserAnswersSet userAnswersSet;
    private Context context;
    // List of quizzes to feed data
    private List<Quiz> quizzes;
    private int score;

    public QuizRecyclerAdapter(Context context, List<QuizRecyclerViewType> viewTypes,
                               UserAnswersSet userAnswersSet, List<Quiz> quizzes) {
        this.context = context;
        this.viewTypes = viewTypes;
        this.userAnswersSet = userAnswersSet;
        this.quizzes = quizzes;

        userAnswersSet.setUserAnswersHashMap(new HashMap<>());

        // Initialize all of UserAnswers relative to quizzes
        for (Quiz q : quizzes) {
            Set<String> emptySet = new HashSet<>();
            UserAnswer userAnswer = new UserAnswer();
            userAnswer.setQuizId(q.getId());
            userAnswer.setAnswers(emptySet);

            userAnswersSet.getmUserAnswersHashMap().put(q.getId(), userAnswer);
        }
    }


    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public void setScore(int score) {
        this.score = score;
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
            case LAYOUT_BUTTON:
                View layoutButton = LayoutInflater.from(context)
                        .inflate(R.layout.button_recycler, parent, false);
                return new ViewHolderButton(layoutButton);
            default:
                return null;
        }
    }

    // Replace the content of views
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final String TAG = "**Holder Load** ";

        // If position == quizzes.size() then button added
        if (position < quizzes.size()) {
            Quiz quiz = quizzes.get(position);
            String numberOrder = position + 1 + ". ";

            if (quiz instanceof QuizType1) {
                // Views of Type 1 quiz
                ViewHolderType1 holderType1 = (ViewHolderType1) holder;
                QuizType1 quizType1 = (QuizType1) quiz;
                holderType1.setTvQuestion(numberOrder + quizType1.getQuiz());

                holderType1.removeDuplicate(holderType1.getRdOptions());

                // Add radioButtons
                for (String s : quizType1.getOptionList()) {
                    RadioButton rb = new RadioButton(
                            holderType1.getLinearLayout().getContext());
                    rb.setText(s);
                    holderType1.getRdOptions().addView(rb);

                    // Load radioButton checked state
                    loadRadioButtonCheckedState(quiz, rb);

                    // Set Listener
                    rb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean isChecked = rb.isChecked();
                            // Save answer to userAnswer
                            if (isChecked) {
                                saveAnswerType1(rb.getText().toString(), quiz.getId());
                                // Save state of selection
                                radioBtnCheckedHshMap.put(quiz.getId(), rb.getText().toString());
                            }
                        }
                    });

                }
            }

            if (quiz instanceof QuizType2) {
                // Views of Type 2 quiz
                ViewHolderType2 holderType2 = (ViewHolderType2) holder;
                QuizType2 quizType2 = (QuizType2) quiz;
                holderType2.setTvQuestion(numberOrder + quizType2.getQuiz());

                // Remove checkboxes from duplicating
                holderType2.removeDuplicate(holderType2.getLinearLayoutCheckBoxes());

                // Add checkBoxes
                for (String s : quizType2.getOptionList()) {
                    CheckBox cb = new CheckBox(holderType2.getLinearLayout().getContext());
                    cb.setText(s);
                    holderType2.getLinearLayout().addView(cb);

                    loadCheckBoxCheckedStates(quiz, cb);

                    cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            // Load checked state
                            loadCheckBoxCheckedStates(quiz, cb);
                            if (isChecked) {
                                // Save user choices to HashMap
                                saveAnswerType2(quiz, cb);
                            } else {
                                // Remove choices from HashMap
                                removeAnswerType2(quiz, cb);
                                cb.setChecked(false);
                            }
                        }
                    });
                }
            }

            if (quiz instanceof QuizType3) {
                holder.setIsRecyclable(false); // Work around bug of recycler View for EditText
                // Views of QuizType 3
                ViewHolderType3 holderType3 = (ViewHolderType3) holder;
                QuizType3 quizType3 = (QuizType3) quiz;
                holderType3.setTvQuestion(numberOrder + quizType3.getQuiz());
                EditText etAnswer = ((ViewHolderType3) holder).etAnswer;

                // Load editText contents
                loadEditTextContents(quiz, etAnswer);

                // Handle text change
                etAnswer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        saveAnswerType3(quiz, etAnswer);
                    }
                });
            }
        }
        Log.d(TAG, "onBindViewHolder: " + userAnswersSet.getmUserAnswersHashMap().toString());
    }

    /**
     * For QuizType 3, loads the contents of EditText that user have already entered.
     *
     * @param quiz     current quiz
     * @param etAnswer current EditText
     */
    private void loadEditTextContents(Quiz quiz, EditText etAnswer) {
        if (userAnswersSet.getmUserAnswersHashMap().get(quiz.getId()) != null
                && userAnswersSet.getmUserAnswersHashMap().get(quiz.getId()).getAnswers() != null
                && !userAnswersSet.getmUserAnswersHashMap().get(quiz.getId()).getAnswers().isEmpty()) {
            Iterator it = Objects.requireNonNull(userAnswersSet.getmUserAnswersHashMap()
                    .get(quiz.getId())).getAnswers().iterator();
            // Since QuizType 3, user answers set just hold 1 item of String
            etAnswer.setText(it.next().toString());
        }
    }

    /**
     * For QuizType 2, loads the CheckBoxes state that user have already ticked.
     * Condition: quiz must be type2, answer must not be null
     *
     * @param quiz current quiz
     * @param cb   current checkBox
     */
    private void loadCheckBoxCheckedStates(Quiz quiz, CheckBox cb) {
        if (quiz instanceof QuizType2 &&
                userAnswersSet.getmUserAnswersHashMap().get(quiz.getId()) != null &&
                userAnswersSet.getmUserAnswersHashMap().get(quiz.getId()).getAnswers() != null) {
            for (String s : userAnswersSet.getmUserAnswersHashMap()
                    .get(quiz.getId()).getAnswers()) {
                if (s.equalsIgnoreCase(cb.getText().toString())) {
                    cb.setChecked(true);
                }
            }
        }
    }

    /**
     * For QuizType 1, loads the checked state of a radioButton that user already checked.
     *
     * @param quiz current quiz
     * @param rb   current checkbox
     */
    private void loadRadioButtonCheckedState(Quiz quiz, RadioButton rb) {
        if (quiz instanceof QuizType1 &&
                radioBtnCheckedHshMap.get(quiz.getId()) == rb.getText().toString()) {
            rb.setChecked(true);
        }
    }

    /**
     * Saves user answer to Hashmap (dataset) of Type 3
     *
     * @param quiz     current quiz
     * @param editText editText that user uses to enter answer
     */
    private void saveAnswerType3(Quiz quiz, EditText editText) {
        UserAnswer userAnswer = new UserAnswer();
        Set<String> answerSet = new HashSet<>();
        userAnswer.setQuizId(quiz.getId());

        // Save answer to HashMap
        answerSet.add(editText.getText().toString());
        userAnswer.setAnswers(answerSet);
        userAnswersSet.getmUserAnswersHashMap()
                .put(quiz.getId(), userAnswer);
    }

    /**
     * Saves user answer to HashMap (dataset) of Type 2 quiz
     *
     * @param quiz     current quiz
     * @param checkbox checkbox that user uses to answer
     */
    private void saveAnswerType2(Quiz quiz, CheckBox checkbox) {
        if (userAnswersSet.getmUserAnswersHashMap().get(quiz.getId()) == null) {
            UserAnswer ua = new UserAnswer();
            ua.setQuizId(quiz.getId());
            ua.setAnswers(new HashSet<>());
            userAnswersSet.getmUserAnswersHashMap().put(quiz.getId(), ua);
        }

        userAnswersSet.getmUserAnswersHashMap()
                .get(quiz.getId()).getAnswers()
                .add(checkbox.getText().toString());
    }

    /**
     * Removes user answer from HashMap (dataset) of Type 2 quiz
     */
    private void removeAnswerType2(Quiz quiz, CheckBox checkBox) {
        userAnswersSet.getmUserAnswersHashMap()
                .get(quiz.getId()).getAnswers()
                .remove(checkBox.getText().toString());
        checkBox.setChecked(false);
    }

    /**
     * Saves user answers to HashMap (dataset) of Type 1 quiz
     *
     * @param answer user's answer
     * @param quizId quiz ID
     */
    private void saveAnswerType1(String answer, int quizId) {
        UserAnswer userAnswer = new UserAnswer();
        Set<String> answers = new HashSet<String>(Arrays.asList(answer));
        userAnswer.setQuizId(quizId);
        userAnswer.setAnswers(answers);
        this.userAnswersSet.getmUserAnswersHashMap().put(quizId, userAnswer);
    }

    /**
     * Configures Toast to display result
     *
     * @param countCorrect
     */
    public void bringToast(int countCorrect) {
        String message = "";
        String compliment = "";

        //[Perfect!]|[Try again!]+" "+[You scored] + " " + %d + " " + [out of] + " " + %d
        if (countCorrect == quizzes.size()) {
            compliment = context.getResources().getString(R.string.perfect);
        } else {
            compliment = context.getResources().getString(R.string.try_again);
        }
        message = String.format("%s %s %s %s %s.",
                compliment,
                context.getResources().getString(R.string.scored_1),
                String.valueOf(countCorrect),
                context.getResources().getString(R.string.scored_2),
                String.valueOf(quizzes.size()));

        Log.d(TAG, "onClick: " + message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return quizzes.size() + 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        try {
            if (quizzes.get(position) instanceof QuizType1) return LAYOUT_1;
            if (quizzes.get(position) instanceof QuizType2) return LAYOUT_2;
            if (quizzes.get(position) instanceof QuizType3) return LAYOUT_3;
        } catch (IndexOutOfBoundsException e) {
            return LAYOUT_BUTTON;
        }
        return LAYOUT_BUTTON;
    }

    public List<QuizRecyclerViewType> getViewTypes() {
        return viewTypes;
    }


    /*
        ViewHolders classes below
     */
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
         *
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

        public LinearLayout getLinearLayoutCheckBoxes() {
            return linearLayoutCheckBoxes;
        }

        private void setTvQuestion(String text) {
            tvQuestion.setText(text);
        }

        /**
         * Dynamically added Views duplicate every time onBindViewHolder got invoked.
         * This method removes the duplicates view, in this case, Checkboxes.
         *
         * @param layoutCheckBoxes ViewHolder's linearLayout that holds checkBox items.
         */
        public void removeDuplicate(LinearLayout layoutCheckBoxes) {
            if (layoutCheckBoxes.getChildCount() > 0) {
                for (int i = 0; i < layoutCheckBoxes.getChildCount(); i++) {
                    if (layoutCheckBoxes.getChildAt(i) instanceof CheckBox) {
                        layoutCheckBoxes.removeAllViews();
                    }
                }
            }
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
    }

    public class ViewHolderButton extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private Button button;

        public ViewHolderButton(@NonNull View itemView) {
            super(itemView);
            this.linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout_button);
            this.button = (Button) itemView.findViewById(R.id.btn_submit);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitButtonClicked();
                }
            });
        }

        /**
         * Handles activity when Submit button got clicked.
         */
        public void submitButtonClicked() {
            // Calculates score and displays by Toast

            // Evaluates all answers
            for (Quiz q : quizzes) {
                userAnswersSet.evaluateResult(q);
            }

            // set score for adapter
            setScore(userAnswersSet.calculateScore());

            bringToast(score);

            Log.d("button submit clicked", "submitButtonClicked: "
                    + userAnswersSet.getmUserAnswersHashMap().toString());
//            Toast.makeText(context, "" + score, Toast.LENGTH_SHORT).show();
        }
    }
}
