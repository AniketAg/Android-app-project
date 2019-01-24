package com.example.scoda.booksharing;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by scoda on 11/23/2016.
 */
public class MessagesFragment extends Fragment {


    public static Cursor dbCursor;
    private ArrayList<Messages> messagesArrayList = new ArrayList<Messages>();
    String userName;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        Bundle b = getArguments();
        userName = b.getString("userName");
        ListView listView = (ListView) view.findViewById(R.id.messages_list);
        MessagesDatabase messagesDatabase = new MessagesDatabase(getActivity().getApplicationContext());
        SQLiteDatabase db = messagesDatabase.getReadableDatabase();
        dbCursor = db.query(MessagesDatabase.TableClass.EntryClass.TABLE_NAME,null,
                MessagesDatabase.TableClass.EntryClass.FROM+" = ?"+" OR "+MessagesDatabase.TableClass.EntryClass.TO+" = ?",new String[]{userName,userName},null,null,MessagesDatabase.TableClass.EntryClass.TIME+" DESC");

        if(dbCursor!=null && dbCursor.moveToFirst()) {

            do{
                Messages messages = new Messages(dbCursor.getString(dbCursor.getColumnIndexOrThrow("FromUser")),
                        dbCursor.getString(dbCursor.getColumnIndexOrThrow("ToUser")),
                        dbCursor.getString(dbCursor.getColumnIndexOrThrow("Message")),dbCursor.getString(dbCursor.getColumnIndexOrThrow("Time")));
                messagesArrayList.add(messages);
            }while (dbCursor.moveToNext());
        }

        ArrayAdapter adapter = new BookAdapter(getActivity(),R.layout.single_message_layout,R.id.userName,messagesArrayList);
        listView.setAdapter(adapter);
        return view;
    }

    class ViewHolder  {

        TextView fromToUser;
        TextView userMessage;
        ImageView inboxOutBoxImage;
        TextView time;

    }
    public class BookAdapter extends ArrayAdapter {

        Context mContext;
        int mResource;
        ArrayList<Messages> arrayList;

        public BookAdapter(Context context, int resource, int textViewResourceId,ArrayList<Messages> objects) {
            super(context, resource, textViewResourceId, objects);
            this.mResource = resource;
            this.mContext = context;
            this.arrayList = objects;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = new ViewHolder();
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(mResource, parent, false);
            }
            dbCursor.moveToPosition(position);
            final Messages msg = arrayList.get(position);
            viewHolder.fromToUser = (TextView) convertView.findViewById(R.id.userName);
            viewHolder.userMessage = (TextView) convertView.findViewById(R.id.userMessage);
            viewHolder.inboxOutBoxImage = (ImageView) convertView.findViewById(R.id.inboxOutBoxImage);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            if(msg.getfromUser().equals(userName)) {
                viewHolder.fromToUser.setText(msg.gettoUser());
                viewHolder.inboxOutBoxImage.setImageResource(R.drawable.outbox);
            } else {
                viewHolder.fromToUser.setText(msg.getfromUser());
            }
            viewHolder.userMessage.setText(msg.getMessage());
            viewHolder.time.setText(getDate(Long.valueOf(msg.getTime()),"MM/dd/yyyy"));
            //   viewHolder.bookImage.setImageResource(R.drawable.book);
            return convertView;
        }

        public String getDate(long milliSeconds, String dateFormat)
        {
            // Create a DateFormatter object for displaying date in specified format.
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.US);

            // Create a calendar object that will convert the date and time value in milliseconds to date.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSeconds);
            return formatter.format(calendar.getTime());
        }
    }
}


