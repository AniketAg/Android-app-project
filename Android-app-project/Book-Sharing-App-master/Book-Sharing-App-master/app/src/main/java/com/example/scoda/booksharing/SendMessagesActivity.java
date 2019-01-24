package com.example.scoda.booksharing;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendMessagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_messages);

        Intent intent = getIntent();
        String bookName = intent.getStringExtra("bookName");
        String bookAuthor = intent.getStringExtra("bookAuthor");
        String bookPrice = intent.getStringExtra("bookPrice");
        final String bookPostedBy = intent.getStringExtra("postedBy");

        TextView name = (TextView) findViewById(R.id.name);
        TextView author = (TextView) findViewById(R.id.author);
        TextView price = (TextView) findViewById(R.id.price);
        TextView postedBy = (TextView) findViewById(R.id.postedBy);
        final EditText editText = (EditText) findViewById(R.id.editText);
        Button sendButton = (Button) findViewById(R.id.sendButton);

        name.setText("Name: "+bookName);
        author.setText("Author: "+bookAuthor);
        price.setText("Price: "+bookPrice);
        postedBy.setText("Posted By: " + bookPostedBy);

        if (sendButton!=null)
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessagesDatabase messagesDatabase = new MessagesDatabase(SendMessagesActivity.this);
                SQLiteDatabase db = messagesDatabase.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(MessagesDatabase.TableClass.EntryClass.FROM,MainActivity.userName);
                values.put(MessagesDatabase.TableClass.EntryClass.TO,bookPostedBy);
                values.put(MessagesDatabase.TableClass.EntryClass.MESSAGE,editText.getText().toString());
                values.put(MessagesDatabase.TableClass.EntryClass.TIME,String.valueOf(System.currentTimeMillis()));
                if(db.insert(MessagesDatabase.TableClass.EntryClass.TABLE_NAME,null,values)!=-1) {
                    Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Error sending message. Try Later",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
