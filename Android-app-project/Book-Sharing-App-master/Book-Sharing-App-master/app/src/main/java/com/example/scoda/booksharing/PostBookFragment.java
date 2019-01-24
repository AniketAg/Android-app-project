package com.example.scoda.booksharing;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by scoda on 11/23/2016.
 */
public class PostBookFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post_book, container, false);


        final EditText postBookName = (EditText) view.findViewById(R.id.postBookName);
        final EditText postBookAuthor = (EditText) view.findViewById(R.id.postBookAuthor);
        final EditText postBookPrice = (EditText) view.findViewById(R.id.postbookPrice);
        Button postBookButton = (Button) view.findViewById(R.id.postBookButton);
        postBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b = getArguments();
                String userName = b.getString("userName");
                BookDatabase bookDatabase = new BookDatabase(getActivity().getApplicationContext());
                SQLiteDatabase db = bookDatabase.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(BookDatabase.TableClass.EntryClass.BOOK_NAME,postBookName.getText().toString());
                values.put(BookDatabase.TableClass.EntryClass.BOOK_AUTHOR,postBookAuthor.getText().toString());
                values.put(BookDatabase.TableClass.EntryClass.BOOK_PRICE,"$"+postBookPrice.getText().toString());
                Log.d("com.example.scoda.booksharing","useraName is "+userName);
                values.put(BookDatabase.TableClass.EntryClass.POSTED_BY,userName);
                if(db.insert(BookDatabase.TableClass.EntryClass.TABLE_NAME,null,values)!=-1) {
                    Toast.makeText(getActivity().getApplicationContext(),"Successfully Posted",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();

                }
            }
        });
        return view;
    }
}
