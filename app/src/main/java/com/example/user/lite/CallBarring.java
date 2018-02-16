package com.example.user.lite;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class CallBarring extends BroadcastReceiver {

    private String number;
    public DataBase2 db;
    MainActivity m;


    @Override
    public void onReceive(Context context, Intent intent)
    {
        db = new DataBase2(context);
        if (!intent.getAction().equals("android.intent.action.PHONE_STATE"))
            return;

        else
        {
                number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                MainActivity m=new MainActivity();
                ArrayList al=m.blockedList();
                int flag=1;
                for(int i=0;i<al.size() &&  flag==1;i++)
                {
                    Contact c=(Contact)al.get(i);
                    String s="+91"+c.getPhoneNumber();
                    if(s.equals(number))
                    {
                        db.addContact(c);
                        flag=0;
                    }
                }

                if(flag==0)
                {
                    disconnectPhoneItelephony(context);
                    return;
                }

        }
    }
    private void disconnectPhoneItelephony(Context context)
    {
        ITelephony telephonyService;
        TelephonyManager telephony = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        try
        {
            Class c = Class.forName(telephony.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            telephonyService = (ITelephony) m.invoke(telephony);
            telephonyService.endCall();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
