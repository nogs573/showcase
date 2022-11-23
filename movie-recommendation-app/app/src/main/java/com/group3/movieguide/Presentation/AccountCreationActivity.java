package com.group3.movieguide.Presentation;

import androidx.appcompat.app.*;

import android.content.*;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.android.material.card.MaterialCardView;
import com.group3.movieguide.Business.*;
import com.group3.movieguide.Object.*;
import com.group3.movieguide.R;

import java.util.ArrayList;

public class AccountCreationActivity extends AppCompatActivity {

    //Business layer connection to send queries to database
    private AccessUsers accessUsers;

    //Preferred genres that a user selected during account creation
    private String[] userGenres;

    //genre menu stuff
    MaterialCardView selectCard;
    TextView genres;
    boolean[] selectedGenre; //for display
    ArrayList<Integer> genreList = new ArrayList<>();
    String[] genreArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);
        initializeGenreMenu();
    }

    public void checkAccountCreation(View v) {
        UserModel user;
        UserModel checkForUser;
        TextView emailView = findViewById(R.id.accCreEmailInput);
        String email = emailView.getText().toString();
        TextView passwordView = findViewById(R.id.accCrePasswordInput);
        String password = passwordView.getText().toString();
        TextView nameView = findViewById(R.id.accCreNameInput);
        String name = nameView.getText().toString();

        //Initialize connection to the business layer
        accessUsers = new AccessUsers();

        if (ValidateEmail.validate(email))
        {
            checkForUser = accessUsers.queryUsers(email);

            if (checkForUser == null)
            {
                user = accessUsers.createAccount(email, password, name, userGenres);

                if (user != null)
                {
                    Toast.makeText(this, "User account created. Welcome " + user.getName() + "!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(this, HomeActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(this, "Account creation failed", Toast.LENGTH_LONG).show();
                }
            }
            else
                Toast.makeText(this, "Account with this email already exists!", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "Invalid email format for account creation.", Toast.LENGTH_LONG).show();
    }

    public void initializeGenreMenu(){
        //genre menu stuff
        String[] tempArray = getResources().getStringArray(R.array.genre_array);
        genreArray = new String[tempArray.length-1]; //All genres displayed as checkboxes
        userGenres = new String[tempArray.length-1]; //Genres that were selected for the user
        int count = 0;
        for (int i=1; i<tempArray.length; i++)
            genreArray[count++] = tempArray[i];


        selectCard = findViewById(R.id.accCreListView_data);
        genres = findViewById(R.id.accCreGenres);
        selectedGenre = new boolean[genreArray.length];

        selectCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                showGenreSelection();
            }
        });
    }

    private void showGenreSelection(){

        AlertDialog.Builder builder = new AlertDialog.Builder(AccountCreationActivity.this);
        builder.setTitle("Select Genre Preference(s)");

        builder.setCancelable(false);

        builder.setMultiChoiceItems(genreArray, selectedGenre, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b)
            {
                if(b)
                {
                    if(!genreList.contains(i))
                        genreList.add(i);
                }
                else
                {
                    boolean removed = false;
                    for (int counter = 0; counter < genreList.size() && !removed; counter++)
                    {
                        if (genreList.get(counter) == i)
                        {
                            genreList.remove(counter);
                            removed = true;
                        }
                    }
                }
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                StringBuilder stringBuilder = new StringBuilder();

                for(int j = 0; j < genreList.size(); j++)
                {
                    stringBuilder.append(genreArray[genreList.get(j)]);
                    userGenres[j] = genreArray[genreList.get(j)];
                    if(j != genreList.size()-1)
                    {
                        stringBuilder.append(", ");
                    }
                }
                genres.setText(stringBuilder.toString());
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.dismiss();
            }
        }).setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                for(int j = 0; j < selectedGenre.length; j++)
                {
                    selectedGenre[j] = false;
                    genreList.clear();
                    genres.setText("");
                }
            }
        });
        builder.show();
    }
}