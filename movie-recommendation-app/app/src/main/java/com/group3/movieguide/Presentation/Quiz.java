package com.group3.movieguide.Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.group3.movieguide.R;

public class Quiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setQuizDescription();

    }

    private void setQuizDescription()
    {
        String s = "";
        TextView t = findViewById(R.id.quizDescription);
        s = "Welcome to the Quiz feature!\n You will be asked six questions to help personalize your movie prefrences.\n In order to start the Quiz, click on the START button.";
        t.setText(s);
    }

    public void startQuiz(View v)
    {
        Intent i = new Intent(this, QuizQuestions.class);
        startActivity(i);
    }
}