package com.example.user.lite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.*;

public class Time extends AppCompatActivity {

    EditText hour;
    EditText minute;
    EditText second;
    public DatabaseHandler db;
    String name;
    String number;
    ArrayList al;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        hour=(EditText)findViewById(R.id.hour);
        minute=(EditText)findViewById(R.id.minute);
        second=(EditText)findViewById(R.id.second);
        db = new DatabaseHandler(this);
        Bundle data=getIntent().getExtras();
        String toBlock=data.getString("toBlock");
        String []words=toBlock.split("\\s");
        name=words[0];
        number=words[1];
        MainActivity m=new MainActivity();
        al=m.blockedList();
    }
    public void fBlock(View v)
    {
        String s1=name;
        String s2=number;
        if(s1.equals("") || s2.equals(""))
        {
            Toast.makeText(this,"PLEASE FILL IN THE DETAILS PROPERLY",Toast.LENGTH_LONG).show();
        }
        else if(s2.length()<10 || s2.length()>13)
        {
            Toast.makeText(this,"ENTERED NUMBER IS NOT VALID",Toast.LENGTH_LONG).show();
        }
        else {
            int flag=1;
            for(int i=0;i<al.size() && flag==1;i++)
            {
                Contact c=(Contact)al.get(i);
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
                db.addContact(new Contact(s1, s2));
                Toast.makeText(this, s1 + " Blocked", Toast.LENGTH_LONG).show();
                al=db.getAllContacts();

            }
            else if(flag==0)
            {
                Toast.makeText(this, s1 + " Is Already Blocked", Toast.LENGTH_LONG).show();
            }
        }
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }
    public void timeBlock(View v)
    {
        String sec=second.getText().toString();
        int secs=0;
        ///*
        if(sec.equals("")==false)
        {
            secs=Integer.parseInt(sec);
        }
       // */
        String min=minute.getText().toString();
        int mins=0;
       // /*
        if(min.equals("")==false)
       {
            mins=Integer.parseInt(min);
        }
       // */
       String hr=hour.getText().toString();
        int hrs=0;
       // /*
        if(hr.equals("")==false)
        {
            hrs=Integer.parseInt(hr);
        }
       // */
        long time=0;
        time+=(long)secs*(long)1000;
        time+=(long)mins*(long)60000;
        time+=(long)hrs*(long)3600000;
        //time+=System.currentTimeMillis();
        String data=name+" "+number+" "+time;
        String s1=name;
        String s2=number;
        if(s1.equals("") || s2.equals(""))
        {
            Toast.makeText(this,"PLEASE FILL IN THE DETAILS PROPERLY",Toast.LENGTH_LONG).show();
            Intent in=new Intent(this,MainActivity.class);
            startActivity(in);
            finish();
        }
        else if(s2.length()<10 || s2.length()>13)
        {
            Toast.makeText(this,"ENTERED NUMBER IS NOT VALID",Toast.LENGTH_LONG).show();
        }
        else {
            int flag=1;
            for(int i1=0;i1<al.size() && flag==1;i1++)
            {
                Contact c=(Contact)al.get(i1);
                if(c.getPhoneNumber().equals(s2))
                {
                    flag=0;
                }
            }

            for(int i1=0;i1<s2.length();i1++)
            {
                char c=s2.charAt(i1);
                int ascii=(int)c;
                if(ascii<48 || ascii>57)
                {
                    Toast.makeText(this,"A PHONE NUMBER MUST CONTAIN ONLY DIGITS",Toast.LENGTH_LONG).show();
                    flag=-1;
                    i1=s2.length();
                }
            }

            if(flag==1) {
                //db.addContact(new Contact(s1, s2));
                Toast.makeText(this, s1 + " Blocked", Toast.LENGTH_LONG).show();
               // al=db.getAllContacts();
                Intent i=new Intent(this,MyIntentService.class);
                i.putExtra("data",data);
                startService(i);
                Intent i1=new Intent(this,MainActivity.class);
                startActivity(i1);
                finish();

            }
            else if(flag==0)
            {
                Toast.makeText(this, s1 + " Is Already Blocked", Toast.LENGTH_LONG).show();
            }
        }
        /*
        Intent i=new Intent(this,MyIntentService.class);
        i.putExtra("data",data);
        startService(i);
        Intent i1=new Intent(this,MainActivity.class);
        startActivity(i1);
        */

    }

}
