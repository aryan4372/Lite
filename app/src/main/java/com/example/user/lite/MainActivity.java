package com.example.user.lite;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    public EditText name;
    public EditText number;
    public DatabaseHandler db;
    public DataBase2 db1;
    public static ArrayList<Contact> al;
    public static ArrayList<Contact> blocked;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(EditText)findViewById(R.id.name);
        number=(EditText)findViewById(R.id.number);
        db = new DatabaseHandler(this);
        db1=new DataBase2(this);
        al=db.getAllContacts();
        blocked=db1.getAllContacts();

    }
    public ArrayList<Contact> blockedList()
    {
        return al;
    }

    public  ArrayList<Contact> blockedCalls()
    {
    return blocked;
    }

    public void blocked(View v)
    {
        Intent i=new Intent(this,BlockedContacts.class);
        startActivity(i);
        //finish();
    }

    public void block(View v)
    {
        String s1=name.getText().toString();
        String s2=number.getText().toString();
        ///*
        if(s1.equals("") || s2.equals(""))
        {
            Toast.makeText(this,"PLEASE FILL IN THE DETAILS PROPERLY",Toast.LENGTH_LONG).show();
        }
        else if(s2.length()<10 || s2.length()>13)
        {
            Toast.makeText(this,"ENTERED NUMBER IS NOT VALID",Toast.LENGTH_LONG).show();
        }
        else {
            name.setText("");
            number.setText("");
            int flag=1;
            for(int i=0;i<al.size() && flag==1;i++)
            {
                Contact c=al.get(i);
                if(c.getPhoneNumber().equals(s2))
                {
                    flag=0;
                }
            }

            for(int i=0;i<s2.length();i++)
            {
                char c=s2.charAt(i);
                int ascii=(int)c;
                if(ascii<48 || ascii>57)
                {
                    Toast.makeText(this,"A PHONE NUMBER MUST CONTAIN ONLY DIGITS",Toast.LENGTH_LONG).show();
                    flag=-1;
                    i=s2.length();
                }
            }

            if(flag==1) {
                /*
                db.addContact(new Contact(s1, s2));
                Toast.makeText(this, s1 + " Blocked", Toast.LENGTH_LONG).show();
                al=db.getAllContacts();
                */
                String s=s1+" "+s2;
                Intent i=new Intent(this,Time.class);
                i.putExtra("toBlock",s);
                startActivity(i);
                finish();

            }
            else if(flag==0)
            {
                Toast.makeText(this, s1 + " Is Already Blocked", Toast.LENGTH_LONG).show();
            }
        }
        //*/

    }

    public void getContactList(View v)
    {
        Intent i=new Intent(this,ConatctList.class);
        startActivity(i);
    }

    public void unblock(View v)
    {
        String s1=name.getText().toString();
        String s2=number.getText().toString();

        if(s1.equals("") || s2.equals(""))
        {
            Toast.makeText(this,"PLEASE FILL IN THE DETAILS PROPERLY",Toast.LENGTH_LONG).show();
        }
        else
        {
            int flag=0;
            for(int i=0;i<al.size() && flag==0;i++)
            {
                Contact c=(Contact)al.get(i);
                if(c.getPhoneNumber().equals(s2))
                {
                    flag=1;
                    if(c.getName().equals(s1))
                    {
                        db.deleteContact(c);
                        al=db.getAllContacts();
                        Toast.makeText(this,"CONTACT REMOVED FROM BLOCKED LIST SUCCESSFULLY",Toast.LENGTH_LONG).show();
                        name.setText("");
                        number.setText("");
                    }
                    else
                    {
                        Toast.makeText(this,"THIS NUMBER IS SAVED IN YOUR BLOCKED LIST AS "+s1,Toast.LENGTH_LONG).show();
                    }
                }
            }
            if(flag==0)
            {
                Toast.makeText(this,"CONTACT NOT FOUND IN YOUR BLOCKED LIST",Toast.LENGTH_LONG).show();
            }
        }

    }

    public void calls(View v)
    {
        Intent i=new Intent(this,BlockedCalls.class);
        startActivity(i);
    }
}
