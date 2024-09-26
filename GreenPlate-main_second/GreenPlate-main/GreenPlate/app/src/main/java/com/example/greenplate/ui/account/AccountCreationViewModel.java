package com.example.greenplate.ui.account;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AccountCreationViewModel {

    private static AccountCreationViewModel instance;

    private String errorMessage;

    public AccountCreationViewModel() {
        errorMessage = "";
    }

    public String getPasswordMismatch() {
        return errorMessage;
    }

    public static synchronized AccountCreationViewModel getInstance() {
        if (instance == null) {
            instance = new AccountCreationViewModel();
        }
        return instance;
    }

    public boolean filterPasswords(String firstNameMessage,
                                   String lastNameMessage, String usernameMessage,
                                   String passwordMessage, String password2Message) {
        if (firstNameMessage.equals("") || lastNameMessage.equals("") || usernameMessage.equals("")
                || passwordMessage.equals("") || password2Message.equals("")) {
            errorMessage = "Fill in all blanks!";
            //passwordMismatch.setText(passwordError);
            return false;
        }

        if (!passwordMessage.equals(password2Message)) {
            errorMessage = "Passwords do not match. Try Again.";
            return false;
        }

        for (int i = 0; i < usernameMessage.length(); i++) {
            if (usernameMessage.substring(i, i + 1).equals(" ")) {
                errorMessage = "Don't use spaces in username.";
                //passwordMismatch.setText(usernameError);
                return false;
            }
        }
        for (int i = 0; i < passwordMessage.length(); i++) {
            if (passwordMessage.substring(i, i + 1).equals(" ")) {
                errorMessage = "Don't use spaces in password";
                //passwordMismatch.setText(passwordError);
                return false;
            }
        }

        return true;
    }

    public Task saveAccountData(String email, String password, FirebaseAuth mAuth) {
        //FirebaseDatabase mAuth = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");

        //myRef.setValue(usernameText + "\n" + passwordText + "\n");

        return mAuth.createUserWithEmailAndPassword(email, password);

    }
}
