package com.example.mely.seemy_v11;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    @Bind(R.id.input_name) EditText _nameText;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @Bind(R.id.btn_signup) Button _signupButton;
    @Bind(R.id.link_login) TextView _loginLink;
    @Bind(R.id.input_age) TextView _age;
    @Bind(R.id.radio_femme) RadioButton _F;
    @Bind(R.id.radio_homme) RadioButton _H;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);
        //bouton d'inscription
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // verifier que la connexion existe !
                    ConnectivityManager connMgr = (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo == null || !networkInfo.isConnected()) {
                        Toast.makeText(getBaseContext(),"Pas de connexion internet", Toast.LENGTH_LONG).show();
                        return;
                    }
                    signup();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //lien vers la connexion
     _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);

            }
        });

    }

    public void signup() throws ExecutionException, InterruptedException {
        //on verifie la validité des infos
        if (!validate()) {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
            _signupButton.setEnabled(true);
            return;
        }

        _signupButton.setEnabled(false);
        //barre de progression en attendant la création dans la bd
        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Création du compte...");
        progressDialog.show();
        progressDialog.dismiss();

        //recup des infos
        String login = _nameText.getText().toString();
        String age = _age.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();
        String sexe= null;
        if(_F.isChecked() )
            sexe="F";
        else
            sexe="H";

        //création du compte en bd avec les infos si dessus
        AsyncTask AT=  new SignupBackgroundActivity(this).execute(login,password,age,email,sexe);
        String S = (String) AT.get();
        switch(Integer.parseInt(S)){
            case 0:
                Toast.makeText(getBaseContext(), "Erreur lors de la création en bd", Toast.LENGTH_LONG).show();

                break;
            case 1:
                Toast.makeText(getBaseContext(), "Vous pouvez vous connecter !...", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(getBaseContext(), getText(R.string.existe), Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(getBaseContext(), "Parametre manquant", Toast.LENGTH_LONG).show();
                break;
        }
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        _signupButton.setEnabled(true);
                        setResult(RESULT_OK, null);
                        //on passe a la page de connexion
                        Intent in = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(in);
                        progressDialog.dismiss();
                    }
                }, 3000);


    }


    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();
        String age = _age.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }



        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }
        //verif age

        return valid;
    }
}
