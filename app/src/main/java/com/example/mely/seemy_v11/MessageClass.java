package com.example.mely.seemy_v11;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        String pseudo =  getIntent().getStringExtra("LOGIN");
        user = (TextView)findViewById(R.id.user_profile_name);
        user.setText(pseudo);
        listeV = (ListView)findViewById(R.id.listview);
        chatText =(EditText)findViewById(R.id.chat_text);
        send =(Button)findViewById(R.id.btn);

        adp = new ChatArrayAdapter(getApplicationContext(), R.layout.chat_row);
        listeV.setAdapter(adp);

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
        chatText.setText("");// raz du champs de text
        return true;
    }
}
