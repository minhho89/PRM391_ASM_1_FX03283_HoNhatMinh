package minhfx03283.funix.prm391_asm_1;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.Inflater;

import static minhfx03283.funix.prm391_asm_1.QuizRecyclerViewType.LAYOUT_1;
import static minhfx03283.funix.prm391_asm_1.QuizRecyclerViewType.LAYOUT_2;
import static minhfx03283.funix.prm391_asm_1.QuizRecyclerViewType.LAYOUT_3;
import static minhfx03283.funix.prm391_asm_1.QuizRecyclerViewType.LAYOUT_BUTTON;

public class QuizRecyclerAdapter extends RecyclerView.Adapter {
    Context context;
    // List of quizzes to feed data
    List<Quiz> quizzes;
    // List of viewTypes
    private final List<QuizRecyclerViewType> viewTypes;
    private final HashMap<Integer, UserAnswer> userAnswerHashMap; // to store user answers
    private final HashMap<Integer, String> radioBtnCheckedHshMap = new HashMap<>(); // to store checked RadioButton
    private final HashMap<Integer, Set<String>> checkBoxCheckedHshMap = new HashMap<>(); // to store checked CheckedBoxes

    public QuizRecyclerAdapter(Context context, List<QuizRecyclerViewType> viewTypes, List<Quiz> quizzes) {
        this.context = context;
        this.viewTypes = viewTypes;
        this.quizzes = quizzes;
        userAnswerHashMap = new HashMap<>();

        // Initialize all of UserAnswers relative to quizzes
        for (Quiz q : quizzes) {
            Set<String> emptySet = new HashSet<>();
            userAnswerHashMap.put(q.getId(), new UserAnswer(q.getId(), emptySet));
        }
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
                holderType1.setTvQuestion(numberOrder + quizType1.getQuestion());

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
                                saveAnswerType1(userAnswerHashMap,
                                        rb.getText().toString(), quiz.getId());
                                // Save state of selection
                                radioBtnCheckedHshMap.put(quiz.getId(),
                                        rb.getText().toString());
                            }
                        }
                    });

                }
            }

            if (quiz instanceof QuizType2) {
                // Views of Type 2 quiz

                ViewHolderType2 holderType2 = (ViewHolderType2) holder;
                QuizType2 quizType2 = (QuizType2) quiz;
                holderType2.setTvQuestion(numberOrder + quizType2.getQuestion());

                // Remove checkboxes from duplicating
                holderType2.removeDuplicate(holderType2.getLinearLayoutCheckBoxes());

                for (String s : quizType2.getOptionList()) {
                    CheckBox cb = new CheckBox(
                            holderType2.getLinearLayout().getContext());
                    cb.setText(s);
                    holderType2.getLinearLayout().addView(cb);

                    loadCheckBoxCheckedStated(quiz, cb);

                    cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        final Set<String> checkedStringSet = new HashSet<>();

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            // Load checked state
                            loadCheckBoxCheckedStated(quiz, cb);

                            String buttonText = cb.getText().toString();
                            if (isChecked) {
                                // Save user choices to HashMap
                                userAnswerHashMap.get(quiz.getId()).getAnswers()
                                        .add(buttonText);
                            } else {
                                // Remove choices from HashMap
                                userAnswerHashMap.get(quiz.getId()).getAnswers()
                                        .remove(buttonText);
                                cb.setChecked(false);
                            }
                        }
                    });
                }
            }

            if (quiz instanceof QuizType3) {
                ViewHolderType3 holderType3 = (ViewHolderType3) holder;
                QuizType3 quizType3 = (QuizType3) quiz;
                holderType3.setTvQuestion(numberOrder + quizType3.getQuestion());
                EditText etAnswer = ((ViewHolderType3) holder).etAnswer;

                // Load editText contents
                loadEditTextContents(quiz, etAnswer);

                etAnswer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        UserAnswer userAnswer = new UserAnswer();
                        Set<String> answerSet = new HashSet<>();
                        userAnswer.setQuizId(quiz.getId());

                        answerSet.add(etAnswer.getText().toString());
                        userAnswer.setAnswers(answerSet);
                        userAnswerHashMap.put(quiz.getId(), userAnswer);
                    }
                });
            }
        }

    }

    /**
     * For QuizType 3, loads the contents of EditText that user have already entered.
     * @param quiz current quiz
     * @param etAnswer current EditText
     */
    private void loadEditTextContents(Quiz quiz, EditText etAnswer) {
        if (userAnswerHashMap.get(quiz.getId()) != null
                && userAnswerHashMap.get(quiz.getId()).getAnswers() != null
                && !userAnswerHashMap.get(quiz.getId()).getAnswers().isEmpty()) {
            Iterator it = userAnswerHashMap.get(quiz.getId()).getAnswers().iterator();
            // Since QuizType 3, user answers set just hold 1 item of String
            etAnswer.setText(it.next().toString());
        }
    }

    /**
     * For QuizType 2, loads the CheckBoxes state that user have already ticked.
     * @param quiz current quiz
     * @param cb current checkBox
     */
    private void loadCheckBoxCheckedStated(Quiz quiz, CheckBox cb) {
        if (quiz instanceof QuizType2 &&
                userAnswerHashMap.get(quiz.getId()).getAnswers() != null) {
            for (String s : userAnswerHashMap.get(quiz.getId()).getAnswers()) {
                if (s.equalsIgnoreCase(cb.getText().toString())) {
                    cb.setChecked(true);
                }
            }

        }
    }

    /**
     * For QuizType 1, loads the checked state of a radioButton that user already checked.
     * @param quiz current quiz
     * @param rb current checkbox
     */
    private void loadRadioButtonCheckedState(Quiz quiz, RadioButton rb) {
        if (quiz instanceof QuizType1 &&
                radioBtnCheckedHshMap.get(quiz.getId()) == rb.getText().toString()) {
            rb.setChecked(true);
        }
    }

    /**
     * Saves user answers to HashMap (dataset) of Type 1 quiz
     *
     * @param userAnswerHashMap
     */
    private void saveAnswerType1(HashMap<Integer, UserAnswer> userAnswerHashMap,
                                 String answer, int quizId) {
        UserAnswer userAnswer = new UserAnswer();
        Set<String> answers = new HashSet<String>(Arrays.asList(answer));
        userAnswer.setQuizId(quizId);
        userAnswer.setAnswers(answers);
        this.userAnswerHashMap.put(quizId, userAnswer);
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
         *
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
        private void submitButtonClicked() {
            View view = (View)LayoutInflater.from(context).inflate(R.layout.quiz_type_3, null);
            EditText etAnswer = (EditText)view.findViewById(R.id.et_user_answer);

            // Handles last editText View in case of user clicks button without leaving it.
            // First checks if the last view is a editText or not
            // TODO:  checks if the last view is a editText or not
            if(isLastViewEditText(quizzes)) {
                // Then save the answer to HashMap
//                UserAnswer userAnswer = new UserAnswer();
//                userAnswer.setQuizId(quizzes.get(quizzes.size()-1).getId());
//                userAnswer.setAnswers(new HashSet<>(Arrays.asList(etAnswer.getText().toString())));
//                // TODO: adds userAnswer in editText to HashMap
//                userAnswerHashMap.put(userAnswer.getQuizId(), userAnswer);
            }

            // Calculates score and displays by Toast
            for (Quiz q : quizzes) {
                evaluateResult(q, userAnswerHashMap);
            }
            int score = calculateScore(userAnswerHashMap);
            Log.d("button submit clicked", "submitButtonClicked: " + userAnswerHashMap.toString());
            Toast.makeText(context, "" + score, Toast.LENGTH_SHORT).show();


        }

        private boolean isLastViewEditText(List<Quiz> quizzes) {
            int lastViewPosition = quizzes.size() - 1;
            return (quizzes.get(lastViewPosition) instanceof QuizType3);

        }

        /**
         * Evaluates all the user answers at once.
         * @param quiz
         * @param userAnswerHashMap
         */
        private void evaluateResult(Quiz quiz,
                                    HashMap<Integer, UserAnswer> userAnswerHashMap) {
            UserAnswer userAnswer = userAnswerHashMap.get(quiz.getId());
            if (!(quiz instanceof QuizType3)) {
                userAnswer.setResult(quiz.checkResult(userAnswer.getAnswers()));
            } else {
                // QuizType3
                String s = "";
                if(userAnswer.getAnswers()!=null) {
                    Iterator it = userAnswer.getAnswers().iterator();
                    while (it.hasNext()) {
                        s = it.next().toString();
                        userAnswer.setResult(((QuizType3) quiz).checkResult(s));
                    }
                }
            }
        }

        /**
         * Counts the total correct answers.
         * @param userAnswerHashMap
         * @return
         */
        private int calculateScore(HashMap<Integer, UserAnswer> userAnswerHashMap) {
            int count = 0;
            for (Map.Entry m : userAnswerHashMap.entrySet()) {
                UserAnswer userAnswer = (UserAnswer) m.getValue();
                if (userAnswer.isResult()) count++;
            }
            return count;
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }

        public void setLinearLayout(LinearLayout linearLayout) {
            this.linearLayout = linearLayout;
        }

        public Button getButton() {
            return button;
        }

        public void setButton(Button button) {
            this.button = button;
        }

    }


}
