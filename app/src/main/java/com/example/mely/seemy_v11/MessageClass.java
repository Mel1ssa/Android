package com.example.mely.seemy_v11;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by MELY on 5/19/2017.
 */

public class MessageClass  extends AppCompatActivity{
    private ChatArrayAdapter adp;
    private ListView listeV;
    private EditText chatText;
    private TextView user;
    private Button send;
    private boolean side = false;

    String id_recep;
    String id_emet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        String pseudo =  getIntent().getStringExtra("LOGIN");
        id_recep = getIntent().getStringExtra("ID_RECEP");
        id_emet = getIntent().getStringExtra("ID_EMET");
        user = (TextView)findViewById(R.id.user_profile_name);
        user.setText(pseudo);
        listeV = (ListView)findViewById(R.id.listview);
        chatText =(EditText)findViewById(R.id.chat_text);
        send =(Button)findViewById(R.id.btn);

        adp = new ChatArrayAdapter(getApplicationContext(), R.layout.chat_row);
        listeV.setAdapter(adp);
        try {
            this.receivChatMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });

        listeV.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL); // garde le dernier message visible
        listeV.setAdapter(adp);

    }

    private boolean sendChatMessage(){
        adp.add(new ChatMessage(side, chatText.getText().toString()));
        AsyncTask AT2=  new MessageBackground(this).execute("envoie",id_emet,id_recep, chatText.getText().toString());

        chatText.setText("");// raz du champs de text
        return true;
    }

    private boolean receivChatMessage() throws ExecutionException, InterruptedException {

        AsyncTask AT2=  new MessageBackground(this).execute("recup",id_emet,id_recep);
        ArrayList<ChatMessage> M =  (ArrayList<ChatMessage>) AT2.get();

        if (M.size()>0)
            for(ChatMessage m : M){
                Log.e("Message recu",m.getMessage());
                adp.add(m);
            }

        return true;
    }

}
