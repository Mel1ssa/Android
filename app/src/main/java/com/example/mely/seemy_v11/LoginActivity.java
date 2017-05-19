package com.example.mely.seemy_v11;

import android.app.ProgressDialog;
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
import java.util.Map;
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
        //bouton de connexion
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
        //lien vers l'inscription
        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
            }
        });
    }




    public void login() throws ExecutionException, InterruptedException {
        //vérifie la validité des infos
       if (!validate()) {
           Toast.makeText(getBaseContext(), R.string.echec, Toast.LENGTH_LONG).show();
           _loginButton.setEnabled(true);
            return;
        }

        _loginButton.setEnabled(false);

        //barre de progression on attendant de vérfier dans la base si l'utilisateur existe
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getText(R.string.auth));
        progressDialog.show();

        String login = _pseudoText.getText().toString();
        String password = _passwordText.getText().toString();

        // vérifie que l'utilisateur existe
        AsyncTask AT=  new LoginBackgroundActivity(this).execute(login,password);
        Map<String, String> S= (Map<String, String>) AT.get();

        if(Integer.parseInt(S.get("success"))==1){
            //Création du user en local

            Utilisateur user= new Utilisateur(S.get("Id"),login,S.get("Sexe"),S.get("Age"),S.get("Dist"));
            //recup les tags
            AsyncTask AT2=  new InfoProfilBackgroundActivity(this).execute(S.get("Id"));
            String S2 = (String) AT2.get();
            if(S2.length()>0) {
                Log.e("debug : coucou", S2);
                String[] Tags = S2.split(" ");
                user.setTags(Tags);
            }

            // intent vers la page d'acceuil
            Intent intent = new Intent(getApplicationContext(), MainUserActivity.class);
            intent.putExtra("USER",user);
            startActivity(intent);
            //finish();
        }

        else {
            Toast.makeText(getBaseContext(), getText(R.string.inconnu), Toast.LENGTH_LONG).show();
        }

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        _loginButton.setEnabled(true);
                        progressDialog.dismiss();
                    }
                }, 3000);
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
