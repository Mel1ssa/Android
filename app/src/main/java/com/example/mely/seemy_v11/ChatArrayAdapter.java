package com.example.mely.seemy_v11;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatArrayAdapter extends ArrayAdapter<ChatMessage>{
	private TextView chatText;
	private List<ChatMessage> MessageList = new ArrayList<ChatMessage>();
    private LinearLayout layout;
	
	
    public ChatArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}
	

	public void add(ChatMessage object) {
		MessageList.add(object);
		super.add(object);
		
	}
	
	 public int getCount() {
		return this.MessageList.size();
	 }
	
	 public ChatMessage getItem(int index){
		return this.MessageList.get(index);
	 }
	 
	  public View getView(int position,View ConvertView, ViewGroup parent){
		   View v = ConvertView;
		   if(v==null){
			  LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			   v =inflater.inflate(R.layout.chat_row, parent,false);
		   }

		  layout = (LinearLayout)v.findViewById(R.id.Message1);
		  ChatMessage messageobj = getItem(position);
		  chatText =(TextView)v.findViewById(R.id.SingleMessage);
		  chatText.setText(messageobj.message);
		  chatText.setBackgroundResource(messageobj.side ? R.drawable.bulle_b :R.drawable.bulle_a);
		  layout.setGravity(messageobj.side?Gravity.LEFT:Gravity.RIGHT);

		  return v;
		  
	  }
	  
	 }
