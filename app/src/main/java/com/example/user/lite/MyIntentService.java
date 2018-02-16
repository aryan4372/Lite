package com.example.user.lite;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MyIntentService extends IntentService {
    public DatabaseHandler db;
    public ArrayList al;
    public MyIntentService()
    {
        super("MyIntentService");
    }

    protected void onHandleIntent(Intent i)
    {
        Bundle data=i.getExtras();
        String toBlock=data.getString("data");
        MainActivity m=new MainActivity();
        al=m.blockedList();
        String []words=toBlock.split("\\s");
        String name=words[0];
        String number=words[1];
        String time1=words[2];
        Integer time=Integer.parseInt(time1);
        long time2=(long)time;
        db = new DatabaseHandler(this);
       // db.addContact(new Contact(name, number));
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
                db.addContact(new Contact(s1, s2));
                Toast.makeText(this, s1 + " Blocked", Toast.LENGTH_LONG).show();
                al=db.getAllContacts();

            }
            else if(flag==0)
            {
                Toast.makeText(this, s1 + " Is Already Blocked", Toast.LENGTH_LONG).show();
            }
        }
        time2+=System.currentTimeMillis();
        al=db.getAllContacts();
        while(System.currentTimeMillis()<time2)
        {}
        int flag=0;
        for(int i1=0;i1<al.size() && flag==0;i1++)
        {
            Contact c=(Contact)al.get(i1);
            if(c.getPhoneNumber().equals(number))
            {
                flag=1;
                if(c.getName().equals(name))
                {
                    db.deleteContact(c);
                    al=db.getAllContacts();
                }
            }
        }

    }
}
