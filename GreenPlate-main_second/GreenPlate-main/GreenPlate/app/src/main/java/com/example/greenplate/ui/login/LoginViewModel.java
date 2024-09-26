package com.example.greenplate.ui.login;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel {

    private static LoginViewModel instance;

    private boolean success;

    private String toastMessage;


    private LoginViewModel() {
        success = false;
        toastMessage = "";
    }

    public static synchronized LoginViewModel getInstance() {
        if (instance == null) {
            instance = new LoginViewModel();
        }
        return instance;
    }

    public String getToastMessage() {
        return toastMessage;
    }

    public void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean succ) {
        success = succ;
    }

    public Task verifyLogin(String email, String password, FirebaseAuth mAuth) {
        //success = false;
        return mAuth.signInWithEmailAndPassword(email, password);
        /*.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    toastMessage = "Logged In!";
                    success = true;
                } else {
                    toastMessage = "Incorrect Username or Password.";
                    success = false;
                }
            }

        });*/
        //return success;
        /*if (email.equals("") || password.equals("")) {
            // If you inputted empty strings

            toastMessage = "Empty Inputs, Try Again.";
        } else {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                toastMessage = "Logged In!";
            } else {
                toastMessage = "Incorrect Username or Password.";
            }
        }*/


        //return success;
    }
}
