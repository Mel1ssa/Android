package com.example.mely.seemy_v11;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.ExecutionException;
import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

   // equivalent a (button)findViewById(bouton)
    @Bind(R.id.input_pseudo) EditText _pseudoText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    login();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
            }
        });
    }




    public void login() throws ExecutionException, InterruptedException {
       if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getText(R.string.auth));
        progressDialog.show();

        String login = _pseudoText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        AsyncTask AT=  new LoginBackgroundActivity(this).execute(login,password);
        String S = (String) AT.get();
        //String S= "1";
        if(Integer.parseInt(S)==1){

            Toast.makeText(getBaseContext(), getText(R.string.supper), Toast.LENGTH_LONG).show();// a supprimer
            //recup les tags
            AsyncTask AT2=  new InfoProfilBackgroundActivity(this).execute(login);
            String S2 = (String) AT2.get();


            // intent vers profil
            Intent intent = new Intent(getApplicationContext(), MainUserActivity.class);
            intent.putExtra("USER_LOGIN",login);
            intent.putExtra("USER_TAGS",S2);
            startActivity(intent);
            //finish();
        }

        else
            Toast.makeText(getBaseContext(), getText(R.string.inconnu), Toast.LENGTH_LONG).show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }
    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        // TODO : intent pour rentrer dans l'appli
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), R.string.echec, Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String login = _pseudoText.getText().toString();
        String password = _passwordText.getText().toString();

        if (login.isEmpty() || login.length() < 4 || login.length() > 20) {
            _pseudoText.setError(getText(R.string.errorValue));
            valid = false;
        } else {
            _pseudoText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 20) {
            _passwordText.setError(getText(R.string.errorValue));
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
