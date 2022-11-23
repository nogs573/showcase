package com.group3.movieguide.Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.android.material.chip.Chip;
import com.group3.movieguide.Business.QuizChips;
import com.group3.movieguide.Business.QuizLogic;
import com.group3.movieguide.Object.MovieModel;
import com.group3.movieguide.R;

import java.util.ArrayList;
import java.util.List;

public class QuizQuestions extends AppCompatActivity {

    private ArrayList<String> questionList;             // Contains all the questions for the Quiz feature
    private ArrayList<String> chipList;                 // List of chips (genres) selected by the user form question 5
    private ArrayList<String> ratingChipList;           // List of chips (PG Rating)selected by the user from question 6
    private ArrayList<String> genreInterested;          // Contains all genres selected by the user that they prefer
    private ArrayList<String> genreNotInterested;       // Contains all genres selected by the user that they don't prefer
    private  String favoriteMovie;                      // User's favorite movie asked in the first question
    private static  int currentQuestionIndex;           // The index that keeps track of the current index in the list
    private Button nextQuestionButton;                  // Load or submit the next question in the quiz
    private QuizLogic logicLayer;                       // The Object that handles the logic part such and querying of this quiz

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questions);

        // This will be used by the manageNextButton method
        nextQuestionButton = findViewById(R.id.nextButton);

        // Once the page is loaded fill the list of questions
        fillList();

        // Start from question 1
        currentQuestionIndex = 0;

        // This will load the first question as soon as this activity is loaded
        nextQuestion();

        genreInterested = new ArrayList<>();
        genreNotInterested = new ArrayList<>();

        // Initialize the logic
        logicLayer = new QuizLogic();

        // Initialize list of chips
        chipList = new ArrayList<>();
        ratingChipList = new ArrayList<>();
    }

    /**
     * This method will fill the question list will all the question that are to be asked in the Quiz
     */
    private void fillList()
    {
        questionList = new ArrayList<>();
        questionList.add("If you were stranded on an island and could only have one of these things,which would you choose?");         // Q1
        questionList.add("Which of these series would you be most likely to binge on a lazy weekend?");                                // Q2
        questionList.add("Which of these settings would you prefer to live in?");                                                      // Q3
        questionList.add("Your friend excitedly invites you to hang out and watch a bad movie with them. What's your reaction?");      // Q4
        questionList.add("Are there any movie genres that you don't want to see in your personalized recommendations?");               // Q5
        questionList.add("Please select which ratings you want to allow:");                                                            // Q6
    }

    /**
     * This onclick method will load next next question when the user presses the next button.
     * @param v
     */
    public void loadNextQuestion(View v)
    {
        // Based on the Question number and the Button selected, send the data to the logic layer
        processQuizLogic(currentQuestionIndex - 1, findViewById(v.getId()));
        nextQuestion();
    }

    public void nextQuestion()
    {
        // Make sure the app is showing the correct layout according to the question
        setProperLayout();

        // Select the correct text box area based on the layout
        TextView questionText = selectQuestionTextBox();

        // This method will update the text on the button and make it visible or gone based on the current question
        manageNextButton(nextQuestionButton);

        if(currentQuestionIndex < questionList.size())
        {
            String question = questionList.get(currentQuestionIndex);
            questionText.setText(question);
            if(currentQuestionIndex == 0)
            {
                loadQuestion1();
            }
            else if(currentQuestionIndex == 1)
            {
                loadQuestion2();
            }
            else if(currentQuestionIndex == 2)
            {
                loadQuestion3();
            }
            else if(currentQuestionIndex == 3)
                loadQuestion4();
//            else if(currentQuestionIndex == 4)
//                loadQuestion5();
//            else if(currentQuestionIndex == 5)
//                loadQuestion6();

            currentQuestionIndex++;
        }
        else
        {
            // This means that the SUBMIT button has been clicked by the user
            quizSubmitted();
        }
    }

    /**
     * This method will manage the next button at the bottom of the Quiz screen
     * For questions 1-4, the user does not need this button, hence making it Gone
     * On question 5, the user will need this button to go on question 5.
     * On question 6, i.e., the final question, this button should become the submit button
     */
    private void manageNextButton(Button button)
    {
        if(currentQuestionIndex >= 0 && currentQuestionIndex <= 3)
        {
            // Questions 1-4
            if(button.getVisibility() == View.VISIBLE)
                button.setVisibility(View.GONE);
        }
        else if(currentQuestionIndex == 4)
        {
            // Question 5
            if(button.getVisibility() == View.GONE)
                button.setVisibility(View.VISIBLE);
        }
        else if(currentQuestionIndex == 5)
        {
            // Question 6 (last question)
            if(button.getVisibility() == View.GONE)
                button.setVisibility(View.VISIBLE);
            button.setText("SUBMIT");
        }
//        else if(currentQuestionIndex == 6)
//            quizSubmitted();
    }

    /**
     * This method returns the correct text box (Area where the quiz questions are displayed)
     * As there are three layouts, correct question text box needs to be selected according to the question
     */
    private TextView selectQuestionTextBox()
    {
        TextView questionArea;
        if(currentQuestionIndex >= 0 && currentQuestionIndex <= 3)
            questionArea = findViewById(R.id.questionText);
        else if(currentQuestionIndex == 4)
            questionArea = findViewById(R.id.questionTextPart2);
        else if(currentQuestionIndex == 5)
            questionArea = findViewById(R.id.questionTextPart3);
        else
            questionArea = null;
        return questionArea;
    }

    /**
     * This method will change the layout according to the question.
     * Questions 1 - 4 will have have buttons as their way of user interaction, while Questions 5 and 6 will chips as their user input.
     * Questions 1-4 will user linearLayoutPart1
     * Questions 5 will user linearLayoutPart2
     * Questions 6 will user linearLayoutPart3
     */
    private void setProperLayout()
    {
        LinearLayout layoutPart1 = findViewById(R.id.linearLayoutPart1);
        LinearLayout layoutPart2 = findViewById(R.id.linearLayoutPart2);
        LinearLayout layoutPart3 = findViewById(R.id.linearLayoutPart3);

        if(currentQuestionIndex >= 0 && currentQuestionIndex <= 3)
        {
            // Make the layout1(one with buttons) Visible.
            if(layoutPart2.getVisibility() == View.VISIBLE)
                layoutPart2.setVisibility(View.GONE);

            if(layoutPart3.getVisibility() == View.VISIBLE)
                layoutPart3.setVisibility(View.GONE);

            if(layoutPart1.getVisibility() == View.GONE)
                layoutPart1.setVisibility(View.VISIBLE);
        }
        else if(currentQuestionIndex == 4)
        {
            // Make layout2 (the one with chips) Visible.
            if(layoutPart1.getVisibility() == View.VISIBLE)
                layoutPart1.setVisibility(View.GONE);

            if(layoutPart3.getVisibility() == View.VISIBLE)
                layoutPart3.setVisibility(View.GONE);

            if(layoutPart2.getVisibility() == View.GONE)
                layoutPart2.setVisibility(View.VISIBLE);
        }
        else if(currentQuestionIndex == 5)
        {
            if(layoutPart1.getVisibility() == View.VISIBLE)
                layoutPart1.setVisibility(View.GONE);

            if(layoutPart2.getVisibility() == View.VISIBLE)
                layoutPart2.setVisibility(View.GONE);

            if(layoutPart3.getVisibility() == View.GONE)
                layoutPart3.setVisibility(View.VISIBLE);
        }
    }

    /************************************************ Sending Quiz data to Logic Layer ************************************************/

    private List<String> processQuestion1Logic(View selectedOption)
    {
        Button selectedButton = (Button) selectedOption;

        int optionNumber = -1;
        if(selectedButton.getId() == R.id.button5)
            optionNumber = 0;
        else if(selectedButton.getId() == R.id.button6)
            optionNumber = 1;
        else if(selectedButton.getId() == R.id.button7)
            optionNumber = 2;
        else if(selectedButton.getId() == R.id.button8)
            optionNumber = 3;
        else if(selectedButton.getId() == R.id.button9)
            optionNumber = 4;
        else
            // Something wrong happened
            return null;

        return logicLayer.getQuestionGenre(0, optionNumber);
    }

    private List<String> processQuestion2Logic(View selectedOption)
    {
        Button selectedButton = (Button) selectedOption;

        int optionNumber = -1;
        if(selectedButton.getId() == R.id.button2)
            optionNumber = 0;
        else if(selectedButton.getId() == R.id.button3)
            optionNumber = 1;
        else if(selectedButton.getId() == R.id.button4)
            optionNumber = 2;
        else if(selectedButton.getId() == R.id.button5)
            optionNumber = 3;
        else if(selectedButton.getId() == R.id.button6)
            optionNumber = 4;
        else if(selectedButton.getId() == R.id.button7)
            optionNumber = 5;
        else if(selectedButton.getId() == R.id.button8)
            optionNumber = 6;
        else if(selectedButton.getId() == R.id.button9)
            optionNumber = 7;
        else
            // Something wrong happened
            return null;

        return logicLayer.getQuestionGenre(1, optionNumber);
    }

    private List<String> processQuestion3Logic(View selectedOption)
    {
        Button selectedButton = (Button) selectedOption;

        int optionNumber = -1;
        if(selectedButton.getId() == R.id.button4)
            optionNumber = 0;
        else if(selectedButton.getId() == R.id.button5)
            optionNumber = 1;
        else if(selectedButton.getId() == R.id.button6)
            optionNumber = 2;
        else if(selectedButton.getId() == R.id.button7)
            optionNumber = 3;
        else if(selectedButton.getId() == R.id.button8)
            optionNumber = 4;
        else if(selectedButton.getId() == R.id.button9)
            optionNumber = 5;
        else
            // Something wrong happened
            return null;

        return logicLayer.getQuestionGenre(2, optionNumber);
    }

    private int processQuestion4Logic(View selectedOption)
    {
        Button selectedButton = (Button) selectedOption;

        int optionNumber = -1;
        if(selectedButton.getId() == R.id.button5)
            optionNumber = 0;
        else if(selectedButton.getId() == R.id.button6)
            optionNumber = 1;
        else if(selectedButton.getId() == R.id.button7)
            optionNumber = 2;
        else if(selectedButton.getId() == R.id.button8)
            optionNumber = 3;
        else if(selectedButton.getId() == R.id.button9)
            optionNumber = 4;
        else
            // Something wrong happened
            return -1;

        return logicLayer.getQuestionScore(optionNumber);
    }

    private List<String> processQuestion5Logic()
    {
        return chipList;
    }

    private List<String> processQuestion6Logic()
    {
        return ratingChipList;
    }

    private void processQuizLogic(int questionNumber, View selectedOption)
    {
        // selectedOption should actually be a button
        Button selectedButton = (Button) selectedOption;

        // List of genre to be sent for processing
        List<String> listToSendGenre = null;

        // Score (out of 5) from question 5
        int scoreToSend = -1;

        // List of ratings (PG Rating) from Question 6
        List<String> listToSendRating = null;

        if(questionNumber == 0)
        {
            listToSendGenre = processQuestion1Logic(selectedOption);
        }
        else if(questionNumber == 1)
        {
            listToSendGenre = processQuestion2Logic(selectedOption);
        }
        else if(questionNumber == 2)
        {
            listToSendGenre = processQuestion3Logic(selectedOption);
        }
        else if(questionNumber == 3)
        {
            scoreToSend = processQuestion4Logic(selectedOption);
        }
        else if(questionNumber == 4)
        {
            listToSendGenre = processQuestion5Logic();
        }
        else if(questionNumber == 5)
        {
            listToSendRating = processQuestion6Logic();
        }

        if(questionNumber == 3)
        {
            // if Question 4
            logicLayer.sendScore(scoreToSend);
            Log.d("logicStr", scoreToSend + "");
        }
        else if(questionNumber == 5)
        {
            // if Question 6
            logicLayer.sendPGRating(listToSendRating);
            Log.d("logicStr", listToSendRating.toString());
        }
        else if(questionNumber == 4)
        {
            // If question 5
            // The list of genres must be removed
            logicLayer.removeGenre(listToSendGenre);
        }
        else
        {
            // If question 1, 2, 3, or 5
            // Send the list to the QuizLogic to be processed
            logicLayer.sendGenre(listToSendGenre);
            Log.d("logicStr", listToSendGenre.toString());
        }
    }

    /************************************************ Done Sending Quiz data to Logic Layer ************************************************/

    /**
     * This an onClick method called when on of the options are selected by the user
     * Whenever there is a single choice for the user, the next question is loaded upon clicking on the option
     * @param v
     */
    public void optionSelected(View v)
    {
        Button b = findViewById(v.getId());
        Toast.makeText(this, b.getText().toString(), Toast.LENGTH_SHORT).show();

        // Based on the Question number and the Button selected, send the data to the logic layer
        processQuizLogic(currentQuestionIndex - 1, findViewById(v.getId()));

        // Before moving on to the next question, making all the disabled (GONE) buttons visible again
        makeButtonsVisible();

        // Load next question from the the question list
        nextQuestion();
    }

    /**
     * This method will be called when the user clicks on the submit button
     * At the moment nothing will happen upon submitting the quiz
     */
    private void quizSubmitted()
    {
        logicLayer.printAllData();
//        Intent i = new Intent(this, HomeActivity.class);
//        Toast.makeText(this, "Quiz Submitted!", Toast.LENGTH_SHORT).show();
//        startActivity(i);
        List<MovieModel> m_list = QuizLogic.getMovies();
        Intent i = new Intent(this, QuizResults.class);
        //i.putExtras("movieList", m_list);
//        MovieModel m = m_list.get(0);
//        i.putExtra("movie", m);
        Toast.makeText(this, "Quiz Submitted!", Toast.LENGTH_SHORT).show();
        startActivity(i);
    }


    /**
     * This helper method will check if a button was disabled by a question (Gone), and make it visible again.
     * The minimum number of options in any question are five, and buttons are disabled from top to bottom, so only first three
     * button be disabled at max
     */
    private void makeButtonsVisible()
    {
        // First Button
        Button b = findViewById(R.id.button2);
        if(b.getVisibility() == View.GONE)
            b.setVisibility(View.VISIBLE);

        // Second Button
        b = findViewById(R.id.button3);
        if(b.getVisibility() == View.GONE)
            b.setVisibility(View.VISIBLE);

        // Third Button
        b = findViewById(R.id.button4);
        if(b.getVisibility() == View.GONE)
            b.setVisibility(View.VISIBLE);
    }


    private void loadQuestion1()
    {

        // This question will give the user to choose from five options
        // Disabling first three buttons out of five buttons
        Button b = findViewById(R.id.button2);
        b.setVisibility(View.GONE);

        b = findViewById(R.id.button3);
        b.setVisibility(View.GONE);

        b = findViewById(R.id.button4);
        b.setVisibility(View.GONE);

        Button option1 = findViewById(R.id.button5);
        option1.setText("A machete to help survive the dangers of the island");

        Button option2 = findViewById(R.id.button6);
        option2.setText("A TV that can play your favorite childhood movies");

        Button option3 = findViewById(R.id.button7);
        option3.setText("A cryptic map that might lead to an escape");

        Button option4 = findViewById(R.id.button8);
        option4.setText("A phone that may or may not have reception at higher altitude");

        Button option5 = findViewById(R.id.button9);
        option5.setText("A partner to spend your remaining days alongside");
    }

    private void loadQuestion2()
    {
        // This question will give user eight options to select from.
        // No buttons need to be disabled here.

        Button option1 = findViewById(R.id.button2);
        option1.setText("Star Wars");

        Button option2 = findViewById(R.id.button3);
        option2.setText("Harry Potter");

        Button option3 = findViewById(R.id.button4);
        option3.setText("The Fast and the Furious");

        Button option4 = findViewById(R.id.button5);
        option4.setText("Sherlock Holmes");

        Button option5 = findViewById(R.id.button6);
        option5.setText("Love is Blind");

        Button option6 = findViewById(R.id.button7);
        option6.setText("Planet Earth");

        Button option7 = findViewById(R.id.button8);
        option7.setText("Dumb and Dumber");

        Button option8 = findViewById(R.id.button9);
        option8.setText("The Godfather");
    }

    private void loadQuestion3()
    {
        // This question will have six options.
        // First two option buttons will be disabled

        Button b = findViewById(R.id.button2);
        b.setVisibility(View.GONE);

        b = findViewById(R.id.button3);
        b.setVisibility(View.GONE);

        // Now changing text on buttons
        Button option1 = findViewById(R.id.button4);
        option1.setText("The quiet village in the countryside");

        Button option2 = findViewById(R.id.button5);
        option2.setText("The popular tropical resort by the beach");

        Button option3 = findViewById(R.id.button6);
        option3.setText("The futuristic home with automated everything");

        Button option4 = findViewById(R.id.button7);
        option4.setText("The bustling city full of opportunity");

        Button option5 = findViewById(R.id.button8);
        option5.setText("The comfortable childhood neighborhood");

        Button option6 = findViewById(R.id.button9);
        option6.setText("The rustic medieval castle");
    }

    private void loadQuestion4()
    {
        // This question will have five option
        // First three option buttons will be disabled

        Button b = findViewById(R.id.button2);
        b.setVisibility(View.GONE);

        b = findViewById(R.id.button3);
        b.setVisibility(View.GONE);

        b = findViewById(R.id.button4);
        b.setVisibility(View.GONE);

        Button option1 = findViewById(R.id.button5);
        option1.setText("Can we please do something else?");

        Button option2 = findViewById(R.id.button6);
        option2.setText("If that's what you want to do, that's fine.");

        Button option3 = findViewById(R.id.button7);
        option3.setText("I'd prefer watching a good movie, but let's try!");

        Button option4 = findViewById(R.id.button8);
        option4.setText("I do that sometimes! Let's do it.");

        Button option5 = findViewById(R.id.button9);
        option5.setText("Sounds amazing! The worse, the better!");

    }

    /**
     * This method will load question 5 of the quiz and its options.
     * Here, as the user will be able to select multiple options using chips on the screen
     * This will keep adding all genres (chips) selected to a list.
     * Upon clicking the next button, the data will be sent to the logic layer via processQuizLogic method in this class
     */
    public void loadQuestion5(View v)
    {
        Chip currentChip = findViewById(v.getId());
        String chipName = QuizChips.getChipName(currentChip);

        if(currentChip.isChecked())
        {
            QuizChips.addChipsToList(chipName, chipList);
        }
        else
        {
            QuizChips.removeChipsFormList(chipName, chipList);
        }
    }

    /**
     * This question will allow user to select multiple chips where each chip denote a PG rating
     * This method will keep adding and removing the selected chips by the user to the ratingChipList
     * Upon clicking submit/next button, processQuizLogic will send this data to logic layer
     */
    public void loadQuestion6(View v)
    {
        Chip currentChip = findViewById(v.getId());
        String chipName = QuizChips.getRatingChipName(currentChip);

        if(currentChip.isChecked())
        {
            QuizChips.addChipsToList(chipName, ratingChipList);
        }
        else
        {
            QuizChips.removeChipsFormList(chipName, ratingChipList);
        }
    }
}