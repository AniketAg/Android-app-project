package com.example.scoda.booksharing;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Map;
import java.util.Random;

public class SignUpFragment extends Fragment {


    SignUpFragment.OnFragmentInteractionListener myListener;
    ArrayList<Integer> count;
    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof SignUpFragment.OnFragmentInteractionListener){
            myListener = (SignUpFragment.OnFragmentInteractionListener)context;
        }
        else {
            throw new RuntimeException(context.toString() + " must implement Signup.OnfragmentationListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);
        count = new ArrayList<>();
        ((MainActivity)getActivity()).findViewById(R.id.toolbar).setVisibility(View.INVISIBLE);
        final EditText emailField = (EditText) view.findViewById(R.id.email_signup_field);
        final EditText passwordField = (EditText) view.findViewById(R.id.password_signup_field);
        final EditText fullnameField = (EditText) view.findViewById(R.id.username_field);
        final EditText phoneField = (EditText) view.findViewById(R.id.phonenmber_field);
        Button signupButton = (Button) view.findViewById(R.id.signup_buton);
        Button cancelButton = (Button) view.findViewById(R.id.cancel_button);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.default_user_icon_profile);
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        final byte[] image=stream.toByteArray();
        final String img_base64 = Base64.encodeToString(image, 0);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).gotoLoginFragment();
             //   Intent i = new Intent();
             //   i.putExtra(MainActivity.RESULT_KEY, "Action canceled!");
            //    ((MainActivity) getActivity()).setResult(((MainActivity) getActivity()).RESULT_CANCELED, i);
            //    ((MainActivity) getActivity()).finish();
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDatabase userDatabase = new UserDatabase(getActivity().getApplicationContext());
                SQLiteDatabase helper = userDatabase.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(UserDatabase.TableClass.EntryClass.NAME, fullnameField.getText().toString());
                contentValues.put(UserDatabase.TableClass.EntryClass.EMAIL,emailField.getText().toString());
                contentValues.put(UserDatabase.TableClass.EntryClass.PHONE_NUMBER, phoneField.getText().toString());
                contentValues.put(UserDatabase.TableClass.EntryClass.USER_PASSWORD,passwordField.getText().toString());

                if(helper.insert(UserDatabase.TableClass.EntryClass.TABLE_NAME,null,contentValues)!=-1) {
                    Toast.makeText(getActivity().getApplicationContext(),"Registration Successful",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),"Registration Failed",Toast.LENGTH_SHORT).show();
                }
                userDatabase.close();
                ((MainActivity) getActivity()).gotoLoginFragment();

            }
        });
        return view;
    }
    public interface OnFragmentInteractionListener {
        void gotoLoginFragment();
    }
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    private static String encryptPassword(String password)
    {
        String sha1 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

}
