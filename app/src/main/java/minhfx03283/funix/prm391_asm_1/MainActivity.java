package minhfx03283.funix.prm391_asm_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import minhfx03283.funix.prm391_asm_1.adapters.QuizRecyclerAdapter;
import minhfx03283.funix.prm391_asm_1.adapters.QuizRecyclerViewType;
import minhfx03283.funix.prm391_asm_1.fragments.InputNameFragment;
import minhfx03283.funix.prm391_asm_1.models.Quiz;
import minhfx03283.funix.prm391_asm_1.models.QuizType1;
import minhfx03283.funix.prm391_asm_1.models.QuizType2;
import minhfx03283.funix.prm391_asm_1.models.QuizType3;
import minhfx03283.funix.prm391_asm_1.models.UserAnswersSet;

public class MainActivity extends AppCompatActivity
        implements InputNameFragment.NoticeDialogListener {
    private UserAnswersSet mUserAnswersSet = new UserAnswersSet();
    private List<Quiz> quizzes = new ArrayList<>();

    private String mUserName;
    List<QuizRecyclerViewType> viewTypeLists = new ArrayList<>(Arrays.asList(
            new QuizRecyclerViewType(QuizRecyclerViewType.LAYOUT_1),
            new QuizRecyclerViewType(QuizRecyclerViewType.LAYOUT_2),
            new QuizRecyclerViewType(QuizRecyclerViewType.LAYOUT_3)));

    QuizRecyclerAdapter adapter = new QuizRecyclerAdapter(this, viewTypeLists,
            mUserAnswersSet, quizzes);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Get reference to RecyclerView
        RecyclerView rvQuiz = (RecyclerView) findViewById(R.id.recycler_view);
        //  Create and set the layout manager for RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvQuiz.setLayoutManager(layoutManager);

        quizzes = addQuizzesInstance();
        adapter.setQuizzes(quizzes);
        rvQuiz.setAdapter(adapter);

        TextView txtName = (TextView)findViewById(R.id.txt_name);
        if (mUserName == null) {
            InputNameFragment inputNameFragment = new InputNameFragment();
            inputNameFragment.show(getSupportFragmentManager(), "inputName");
        } else {
            txtName.setText(mUserName);
        }




    }

    private List<Quiz> addQuizzesInstance() {
        List<Quiz> quizzes = new ArrayList<>();

        // 1. What is Deoxyribonucleic acid commonly referred to as? (Correct Answer is #3 (DNA)
        //RNA
        //CIA
        //DNA
        QuizType1 q1 = new QuizType1();
        q1.setQuiz(getResources().getString(R.string.q1));
        Set<String> q1Option = new HashSet<String>(
                Arrays.asList(
                        getResources().getString(R.string.q1_1),
                        getResources().getString(R.string.q1_2),
                        getResources().getString(R.string.q1_3)));
        q1.setOptionList(q1Option);
        Set<String> q1Answer = new HashSet<>(Arrays.asList(getResources().getString(R.string.q1_3)));
        q1.setAnswers(q1Answer);

        // 2. What process involves treating rubber with sulfur to harden it? (Correct Answer is "Vulcanizing")
        QuizType3 q2 = new QuizType3();
        q2.setQuiz(getResources().getString(R.string.q2));
        Set<String> q2Answer = new HashSet<String>(Arrays.asList(getResources().getString(R.string.q2_ans)));
        q2.setAnswers(q2Answer);

        // 3. Name two different organelles of a eukaryotic cell. (Correct Answers are #1 (Ribosomes) and #3 (Golgi Apparatus))
        //Ribosome
        //Plasmids
        //Golgi apparatus
        //Diploid
        QuizType2 q3 = new QuizType2();
        q3.setQuiz(getResources().getString(R.string.q3));
        Set<String> q3Option = new HashSet<String>(Arrays.asList(
                getResources().getString(R.string.q3_1),
                getResources().getString(R.string.q3_2),
                getResources().getString(R.string.q3_3),
                getResources().getString(R.string.q3_4)));
        q3.setOptionList(q3Option);
        Set<String> q3Answer = new HashSet<>(Arrays.asList(
                getResources().getString(R.string.q3_1),
                getResources().getString(R.string.q3_3)));
        q3.setAnswers(q3Answer);

        //4. This word describes the force that pulls objects to the middle of the earth?
        // (Correct Answer is "Gravity")
        QuizType3 q4 = new QuizType3();
        q4.setQuiz(getResources().getString(R.string.q4));
        Set<String> q4Answer = new HashSet<>(Arrays.asList(getResources().getString(R.string.q4_ans)));
        q4.setAnswers(q4Answer);

//        5 What type of trees yield the resin used to produce turpentine?
//        (Correct Answers is #2 "Pine trees")
//        Palm trees
//        Pine trees
//        Oak trees
        QuizType1 q5 = new QuizType1();
        q5.setQuiz(getResources().getString(R.string.q5));
        Set<String> q5Option = new HashSet<>(Arrays.asList(
                getResources().getString(R.string.q5_1),
                getResources().getString(R.string.q5_2),
                getResources().getString(R.string.q5_3)));
        q5.setOptionList(q5Option);
        Set<String> q5Answer = new HashSet<>(Arrays.asList(getResources().getString(R.string.q5_2)));
        q5.setAnswers(q5Answer);

//        6. Cumulus and Cirrus are types of what? (Correct Answer is "Clouds" or "Cloud")
        QuizType3 q6 = new QuizType3();
        q6.setQuiz(getResources().getString(R.string.q6));
        Set<String> q6Answer = new HashSet<>(Arrays.asList(
                getResources().getString(R.string.q6_ans1),
                getResources().getString(R.string.q6_ans2)));
        q6.setAnswers(q6Answer);

        // 7. Which two planets have one or more moons? (Correct Answers are #3 (Earth) and #4 (Pluto))
        //Ceres
        //Mercury
        //Earth
        //Pluto
        QuizType2 q7 = new QuizType2();
        q7.setQuiz( getResources().getString(R.string.q7));
        Set<String> q7Option = new HashSet<>(Arrays.asList(
                getResources().getString(R.string.q7_1),
                getResources().getString(R.string.q7_2),
                getResources().getString(R.string.q7_3),
                getResources().getString(R.string.q7_4)));
        q7.setOptionList(q7Option);
        Set<String> q7Answer = new HashSet<>(Arrays.asList(
                getResources().getString(R.string.q7_3),
                getResources().getString(R.string.q7_4)));
        q7.setAnswers(q7Answer);

        //8. Where in the human body would you find the scaphoid bone? (Correct Answer is "Wrist")
        QuizType3 q8 = new QuizType3();
        q8.setQuiz( getResources().getString(R.string.q8));
        q8.setAnswers(new HashSet<String>(Arrays.asList(getResources().getString(R.string.q8_ans))));

        //9. Which grow upwards Stalactites or Stalagmites? (Correct Answers is #2 "Stalagmites")
        //Stalactites
        //Stalagmites
        QuizType1 q9 = new QuizType1();
        q9.setQuiz(getResources().getString(R.string.q9));
        q9.setOptionList(new HashSet<String>(Arrays.asList(getResources().getString(R.string.q9_1),
                getResources().getString(R.string.q9_2))));
        q9.setAnswers(new HashSet<String>(Arrays.asList(getResources().getString(R.string.q9_2))));

        //10. What process involves heating an ore to obtain a metal? (Correct Answer is "Smelting")
        QuizType3 q10 = new QuizType3();
        q10.setQuiz(getResources().getString(R.string.q10));
        q10.setAnswers(new HashSet<String>(Arrays.asList(getResources().getString(R.string.q10_ans))));

        // Add quiz to Quiz list
        quizzes.add(q1);
        quizzes.add(q2);
        quizzes.add(q3);
        quizzes.add(q4);
        quizzes.add(q5);
        quizzes.add(q6);
        quizzes.add(q7);
        quizzes.add(q8);
        quizzes.add(q9);
        quizzes.add(q10);

//        quizzes.add(q1);
//        quizzes.add(q2);
//        quizzes.add(q3);
//        quizzes.add(q4);
//        quizzes.add(q5);
        return quizzes;
    }
    @Override
    public void onDialogPositiveClick(InputNameFragment dialog) {
        TextView txtName = (TextView)findViewById(R.id.txt_name);
        txtName.setText(dialog.getUserName());
        mUserName = dialog.getUserName();

        TextView txtClock = (TextView)findViewById(R.id.txt_clock);
        insertCountDownClock(txtClock, adapter);
    }

    @Override
    public void onDialogNegativeClick(InputNameFragment dialog) {
        TextView txtName = (TextView)findViewById(R.id.txt_name);
        txtName.setText(dialog.getUserName());
        mUserName = dialog.getUserName();

        TextView txtClock = (TextView)findViewById(R.id.txt_clock);
        insertCountDownClock(txtClock, adapter);
    }

    private void insertCountDownClock(TextView txtClock, QuizRecyclerAdapter adapter) {
        final CountDownTimer COUNTDOWN_TIMER = new CountDownTimer(120_000, 1_000) {

            @Override
            public void onTick(long millisUntilFinished) {
                txtClock.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                // Display the toast
                for (Quiz q : quizzes) {
                    mUserAnswersSet.evaluateResult(q);
                }

                mUserAnswersSet.setScore(mUserAnswersSet.calculateScore());
                adapter.bringToast(mUserAnswersSet.getScore());
            }
        };
        COUNTDOWN_TIMER.start();
    }


}
