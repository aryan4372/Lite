package com.example.user.lite;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BlockedContacts extends AppCompatActivity {

    public TextView t1;
    public TextView t2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked_contacts);
        t1=(TextView)findViewById(R.id.name);
        t2=(TextView)findViewById(R.id.number);
        MainActivity m=new MainActivity();
        ArrayList<Contact> al=m.blockedList();

        String s="";
        String s1="";
        for(int i=0;i<al.size();i++)
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
