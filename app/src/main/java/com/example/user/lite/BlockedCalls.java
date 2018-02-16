package com.example.user.lite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import java.util.ArrayList;

public class BlockedCalls extends AppCompatActivity {

    public TextView t1;
    public TextView t2;
    public DataBase2 db;
    public static ArrayList<Contact> al;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked_calls);
        db=new DataBase2(this);
        t1=(TextView)findViewById(R.id.name);
        t2=(TextView)findViewById(R.id.number);
        MainActivity m=new MainActivity();
        al=m.blockedCalls();



        String s="";
        String s1="";
        for(int i=al.size()-1;i>=0;i--)
        {
            Contact c=(Contact)al.get(i);
            s+=c.getName();
            s1+=c.getPhoneNumber();
            s+="\n";
            s+="\n";
            s1+="\n";
            s1+="\n";
        }
        t1.setText(s);
        t2.setText(s1);



    }

}

