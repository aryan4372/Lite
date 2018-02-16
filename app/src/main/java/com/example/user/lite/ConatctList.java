package com.example.user.lite;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ConatctList extends AppCompatActivity {

    public DatabaseHandler db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conatct_list);

        ContentResolver resolver=getContentResolver();
        Cursor cursor=resolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        ArrayList al=new ArrayList();
        db = new DatabaseHandler(this);
        while(cursor.moveToNext())
        {
            String  s="";
            String id=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            s+=name;
            s+=" ";
            Cursor phoneCursor=resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"=?",new String[]{id},null);
            while(phoneCursor.moveToNext())
            {
                String phoneNumber=phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String s1=s+phoneNumber;
                al.add(s1);
            }
        }
        String [] contacts=new String[al.size()];
        for(int i=0;i<al.size();i++)
        {
            contacts[i]=(String)al.get(i);
        }
        ListAdapter myAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contacts);
        ListView myListView=(ListView)findViewById(R.id.myListView);
        myListView.setAdapter(myAdapter);

        myListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    public void onItemClick(AdapterView<?> parent, View v,int position,long id)
                    {
                        String contact_to_block=String.valueOf(parent.getItemAtPosition(position));
                        String s1 = "";
                        int flag = 1;
                        int length = 0;
                        for (int i = contact_to_block.length() - 1; i >= 0 && flag == 1; i--) {
                            if (contact_to_block.charAt(i) != ' ') {
                                length++;
                                s1 += "" + contact_to_block.charAt(i);
                            }
                            if (length == 10) {
                                flag = 0;
                            }
                        }
                        StringBuilder sb = new StringBuilder(s1);
                        sb.reverse();
                        String s3 = sb.toString();
                        String s2 = "";
                        flag = 1;
                        for (int i = 0; i < contact_to_block.length() && flag == 1; i++) {
                            if (contact_to_block.charAt(i) == ' ') {
                                flag = 0;
                            } else {
                                s2 += "" + contact_to_block.charAt(i);
                            }
                        }
                        db.addContact(new Contact(s2, s3));
                        Toast.makeText(ConatctList.this, s2 + " Blocked", Toast.LENGTH_LONG).show();

                        Intent i=new  Intent(ConatctList.this,MainActivity.class);
                        startActivity(i);
                        finish();

                    }
                }
        );

    }
}
