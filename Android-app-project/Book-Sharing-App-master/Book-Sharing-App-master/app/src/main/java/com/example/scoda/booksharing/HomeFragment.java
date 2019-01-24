package com.example.scoda.booksharing;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.DataSetObserver;
import android.net.Uri;
import android.support.v4.app.ListFragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
//import java.util.List;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener {

    static Double total = 0.00;
    static final User[] loggedInUser = new User[1];
    private ArrayList<Book> books = new ArrayList<Book>();
    private ArrayList<Book> copyOfbooks = new ArrayList<Book>();
    ArrayList<Book> tempbooks = new ArrayList<Book>();
    static HashMap<String, Integer> quantityMap;
    ListView booksListview;

    Cursor dbCursor;
    private static final String TAG = "com.example.scoda.booksharing.HomeFragment";
    SQLiteDatabase db;
    BookDatabase bookDatabase;
    ArrayAdapter adapter;
    android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> callbacks;

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG,"onCreateView is called");
        final View view = inflater.inflate(R.layout.home_layout, container, false);
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            if(bundle.containsKey("userName")) {
                loggedInUser[0] = new User(bundle.getString("userName"),bundle.getString("password"));
                //((MainActivity) getActivity()).initInfo(loggedInUser[0]);
            } else {
                if (bundle.getSerializable("HashMap") != null) {
                    quantityMap = (HashMap<String, Integer>) bundle.getSerializable("HashMap");
                }
            }

        } else {
            quantityMap = new HashMap<String, Integer>();
        }
      /*  Button insert = (Button) view.findViewById(R.id.insert);
        final EditText name = (EditText) view.findViewById(R.id.name);
        final EditText author = (EditText) view.findViewById(R.id.author);
        final EditText price = (EditText) view.findViewById(R.id.price);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BookDatabase bookDatabase = new BookDatabase(getActivity().getApplicationContext());
                SQLiteDatabase inserter = bookDatabase.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(BookDatabase.TableClass.EntryClass.BOOK_NAME,name.getText().toString());
                values.put(BookDatabase.TableClass.EntryClass.BOOK_AUTHOR, author.getText().toString());
                values.put(BookDatabase.TableClass.EntryClass.BOOK_PRICE, price.getText().toString());
                if(inserter.insert(BookDatabase.TableClass.EntryClass.TABLE_NAME,null,values)!=-1) {
                    Toast.makeText(getActivity().getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
*/
        bookDatabase = new BookDatabase(getActivity().getApplicationContext());
        db = bookDatabase.getReadableDatabase();
        dbCursor = db.query(BookDatabase.TableClass.EntryClass.TABLE_NAME,
                null,null,null,null,null,BookDatabase.TableClass.EntryClass.BOOK_NAME);

        if(dbCursor!=null && dbCursor.moveToFirst()) {

            do{
                Book book = new Book(dbCursor.getString(dbCursor.getColumnIndexOrThrow("BookName")),
                        dbCursor.getString(dbCursor.getColumnIndexOrThrow("BookAuthor")),
                        dbCursor.getString(dbCursor.getColumnIndexOrThrow("BookPrice")),dbCursor.getString(dbCursor.getColumnIndexOrThrow("PostedBy")));
                books.add(book);
            }while (dbCursor.moveToNext());
        }

        for (int i=0;i<books.size();i++) {
            copyOfbooks.add(books.get(i));
        }

        ((MainActivity)getActivity()).findViewById(R.id.toolbar).setVisibility(View.VISIBLE);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) view.findViewById(R.id.actionbar_searchView);
        int searchImgId = getResources().getIdentifier("android:id/search_button", null, null);
        ImageView v = (ImageView) searchView.findViewById(searchImgId);
        v.setImageResource(R.drawable.ic_search_black_18dp);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                searchView.setLayoutParams(layoutParams);
                searchView.setBackgroundColor(Color.WHITE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(60, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
                searchView.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
                searchView.setLayoutParams(layoutParams);
                searchView.setBackgroundColor(Color.TRANSPARENT);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doMySearch(query);
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    Log.d(TAG, "onQueryTextChange  if is called");
                    doMySearch(newText);
                } else {
                    Log.d(TAG, "onQueryTextChange  else is called");
                    tempbooks.clear();
                    Iterator iterator = copyOfbooks.iterator();
                    while (iterator.hasNext()) {
                        Book Book = (Book) iterator.next();
                        tempbooks.add(Book);
                    }
                    Log.d(TAG, "temp books size is " + tempbooks.size());
                    adapter.clear();
                    adapter.addAll(tempbooks);
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });
         callbacks = new android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader onCreateLoader(int id,final Bundle args) {

                switch (id) {
                    case 0:
                        return new CursorLoader(getActivity().getApplicationContext()) {
                            @Override
                            public Cursor loadInBackground() {
                                // You better know how to get your database.
                                SQLiteDatabase DB = bookDatabase.getReadableDatabase();
                                // You can use any query that returns a cursor.
                                return DB.query(BookDatabase.TableClass.EntryClass.TABLE_NAME, null,null, null, null, null, BookDatabase.TableClass.EntryClass.BOOK_NAME, null);
                            }
                        };

                    case 1:
                        return new CursorLoader(getActivity().getApplicationContext()) {

                            @Override
                            public Cursor loadInBackground() {
                                final String query = args.getString("query");
                                // You better know how to get your database.
                                SQLiteDatabase DB = bookDatabase.getReadableDatabase();
                                // You can use any query that returns a cursor.
                                return DB.query(BookDatabase.TableClass.EntryClass.TABLE_NAME, null, BookDatabase.TableClass.EntryClass.BOOK_NAME + " LIKE ?", new String[]{"%" + query + "%"}, null, null, BookDatabase.TableClass.EntryClass.BOOK_NAME, null);
                            }
                        };
                }
                return null;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

                booksListview.setAdapter(adapter);
            }


            @Override
            public void onLoaderReset(Loader loader) {
             //   adapter.swapCursor(null);
            }
        };
        booksListview = (ListView) view.findViewById(R.id.books_list);
        String[] from = {"BookName","BookAuthor","BookPrice"};
        int[] to = {R.id.bookName,R.id.bookAuthor};
        Log.d(TAG,"books list size is "+books.size());
        adapter = new BookAdapter(getActivity(),R.layout.single_row_layout,R.id.bookName,books);
        booksListview.setAdapter(adapter);
        booksListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Log.d(TAG,"pos is "+position+" id is "+id);
             /*   Cursor cursor = db.query(BookDatabase.TableClass.EntryClass.TABLE_NAME,
                        new String[]{BookDatabase.TableClass.EntryClass.POSTED_BY},BookDatabase.TableClass.EntryClass._ID+" = ?",
                        new String[]{String.valueOf(id)},null,null,null);
              if(cursor!=null && cursor.moveToFirst()) {*/
                  Log.d(TAG, "cursor is not null");
                  Intent intent = new Intent(getActivity(),SendMessagesActivity.class);
                  intent.putExtra("bookName",books.get(position).getName());
                  intent.putExtra("bookAuthor",books.get(position).getauthor());
                  intent.putExtra("bookPrice",books.get(position).getPrice());
                intent.putExtra("postedBy",books.get(position).getPostedBy());
                //  intent.putExtra("postedBy",cursor.getString(cursor.getColumnIndexOrThrow("PostedBy")));
                  startActivity(intent);
           //   }
          }
      });
        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "onQueryTextSubmit is called");
        doMySearch(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!newText.isEmpty()) {
            doMySearch(newText);
        }
        else {
            tempbooks.clear();
            Iterator iterator = copyOfbooks.iterator();
            while(iterator.hasNext()) {
                Book Book =(Book)iterator.next();
                tempbooks.add(Book);
            }
            adapter.clear();
            adapter.addAll(tempbooks);
            adapter.notifyDataSetChanged();
        }
        return false;
    }

    public void doMySearch(String query) {

        tempbooks.clear();
        Log.d(TAG, "doMySearch is called");
        Iterator iterator = copyOfbooks.iterator();
        Log.d(TAG,"copyOfbooks size is "+copyOfbooks.size());
        while(iterator.hasNext()) {
            Book Book =(Book)iterator.next();
            if(Book.getName().contains(query)) {
                tempbooks.add(Book);
            }
        }
        Log.d(TAG, "temp books size is " + tempbooks.size());
        adapter.clear();
        adapter.addAll(tempbooks);
        adapter.notifyDataSetChanged();
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

            Log.d(TAG, "getView is called position is " + position);
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
            viewHolder.bookImage.setImageResource(R.drawable.book);

            return convertView;
        }
    }
}



