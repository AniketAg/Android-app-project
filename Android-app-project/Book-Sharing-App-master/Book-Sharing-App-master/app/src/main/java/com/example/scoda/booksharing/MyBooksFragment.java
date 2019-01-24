package com.example.scoda.booksharing;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.ArrayList;

/**
 * Created by scoda on 11/23/2016.
 */
public class MyBooksFragment extends Fragment {


    public static Cursor dbCursor;
    private ArrayList<Book> books = new ArrayList<Book>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_books, container, false);
        Bundle b = getArguments();
        final String userName = b.getString("userName");
        ListView listView = (ListView) view.findViewById(R.id.books_list_my_books);
        BookDatabase bookDatabase = new BookDatabase(getActivity().getApplicationContext());
        SQLiteDatabase db = bookDatabase.getReadableDatabase();
        dbCursor = db.query(BookDatabase.TableClass.EntryClass.TABLE_NAME,null,
                BookDatabase.TableClass.EntryClass.POSTED_BY+" LIKE ?",new String[]{userName},null,null,BookDatabase.TableClass.EntryClass.BOOK_NAME);

        if(dbCursor!=null && dbCursor.moveToFirst()) {

            do{
                Book book = new Book(dbCursor.getString(dbCursor.getColumnIndexOrThrow("BookName")),
                        dbCursor.getString(dbCursor.getColumnIndexOrThrow("BookAuthor")),
                        dbCursor.getString(dbCursor.getColumnIndexOrThrow("BookPrice")),dbCursor.getString(dbCursor.getColumnIndexOrThrow("PostedBy")));
                books.add(book);
            }while (dbCursor.moveToNext());
        }

        ArrayAdapter adapter = new BookAdapter(getActivity(),R.layout.single_row_layout,R.id.bookName,books);
        listView.setAdapter(adapter);
        return view;
    }

    class ViewHolder  {
        ImageView bookImage;
        TextView bookName;
        TextView bookAuthor;
        TextView bookPrice;

    }
    public class BookAdapter extends ArrayAdapter {

        Context mContext;
        int mResource;
        ArrayList<Book> arrayList;

        public BookAdapter(Context context, int resource, int textViewResourceId,ArrayList<Book> objects) {
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
            final Book book = arrayList.get(position);
            viewHolder.bookName = (TextView) convertView.findViewById(R.id.bookName);
            viewHolder.bookAuthor = (TextView) convertView.findViewById(R.id.bookAuthor);
            viewHolder.bookImage = (ImageView) convertView.findViewById(R.id.bookImage);
            viewHolder.bookImage.setTag(getItemId(position));

            viewHolder.bookName.setText(book.getName());
            viewHolder.bookAuthor.setText(book.getauthor());
         //   viewHolder.bookImage.setImageResource(R.drawable.book);
            return convertView;
        }
    }
}


