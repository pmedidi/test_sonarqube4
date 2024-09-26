package com.example.greenplate.ui.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.greenplate.R;

import com.example.greenplate.database.UserDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class AccountCreationActivity extends AppCompatActivity {

    //private ActivityLoginBinding binding;

    private AccountCreationViewModel viewModel;
    private TextInputEditText firstNameText;
    private TextInputEditText lastNameText;
    private TextInputEditText usernameText;
    private TextInputEditText passwordText;
    private TextInputEditText password2Text;
    private TextView passwordMismatch;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account_creation);

        Button submit = findViewById(R.id.submit_button);
        Button back = findViewById(R.id.back_button);
        usernameText = findViewById(R.id.new_username);
        passwordText = findViewById(R.id.new_password);
        password2Text = findViewById(R.id.reenter_password);
        firstNameText = findViewById(R.id.first_name);
        lastNameText = findViewById(R.id.last_name);
        passwordMismatch = findViewById(R.id.password_mismatch);

        viewModel = AccountCreationViewModel.getInstance();
        mAuth = FirebaseAuth.getInstance();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountCreationActivity.this,
                        com.example.greenplate.ui.login.LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameMessage = null;
                String lastNameMessage = null;
                String usernameMessage = null;
                String passwordMessage = null;
                String password2Message = null;
                firstNameMessage = firstNameText.getText().toString();
                lastNameMessage = lastNameText.getText().toString();
                usernameMessage = usernameText.getText().toString();
                passwordMessage = passwordText.getText().toString();
                password2Message = password2Text.getText().toString();


                // IF none of the inputs are null and the passwords match, THEN
                // writes the new user data to the FireBase and switches to login
                // screen.
                if (viewModel.filterPasswords(firstNameMessage, lastNameMessage,
                        usernameMessage, passwordMessage, password2Message)) {
                    String finalFirstNameMessage = firstNameMessage;
                    String finalLastNameMessage = lastNameMessage;
                    String finalUsernameMessage = usernameMessage;
                    String finalPasswordMessage = passwordMessage;
                    viewModel.saveAccountData(usernameMessage, passwordMessage, mAuth)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new
                                                Intent(AccountCreationActivity.this,
                                                com.example.greenplate.ui.
                                                        login.LoginActivity.class);

                                        // writes data to FireBase
                                        System.out.println("Creating New Account");
                                        UserDatabase user = UserDatabase.getInstance();
                                        user.writeNewUser(finalFirstNameMessage,
                                                finalLastNameMessage, finalUsernameMessage,
                                                finalPasswordMessage);

                                        // switches screen
                                        Toast.makeText(AccountCreationActivity.this,
                                                "Account Created.",
                                                Toast.LENGTH_SHORT).show();

                                        finish();
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(AccountCreationActivity.this,
                                                "Invalid Account Details, Try Again.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    passwordMismatch.setText(viewModel.getPasswordMismatch());
                }


            }
        });
    }


    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}