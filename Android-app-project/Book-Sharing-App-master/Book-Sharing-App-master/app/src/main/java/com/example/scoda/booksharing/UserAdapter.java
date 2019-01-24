package com.example.scoda.booksharing;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scoda
 */
public class UserAdapter extends ArrayAdapter {

    List<User> mData;
    Context mContext;
    int mResource;
    String myName;
    int count;
    final ArrayList<String> names = new ArrayList<>();
    public UserAdapter(Context context, int resource, ArrayList<User> objects,String user) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;
        this.myName = user;
        //this.myName = myName;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }
        final User user = mData.get(position);
     //   TextView contactName = (TextView) convertView.findViewById(R.id.contact_name);
        ImageView contactImage = (ImageView) convertView.findViewById(R.id.book_image);
        //final ImageView newMessageIcon = (ImageView) convertView.findViewById(R.id.status_message_icon);
        //ImageView phoneIcon = (ImageView) convertView.findViewById(R.id.phone_icon);
      //  contactName.setText(user.getFullName());
        contactImage.setImageBitmap(user.imgProfile());


        return convertView;
    }

}