package fr.correntin.android.mytools.boatlocation.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import fr.correntin.android.mytools.boatlocation.services.BoatLocationService;

/**
 * Created by Corentin on 12/08/2016.
 */
public class SmsReceiver extends BroadcastReceiver
{
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "Corentin";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(SMS_RECEIVED))
        {
            Bundle bundle = intent.getExtras();
            if (bundle != null)
            {
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++)
                {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                if (messages.length > -1)
                {
                    String message = messages[0].getMessageBody();

                    if (message.equals("start-service") || message.equals("stop-service"))
                    {
                        Intent boatLocationService = new Intent(context, BoatLocationService.class);
                        boatLocationService.setAction("LAUNCH_BY_SMS");
                        boatLocationService.putExtra("command", message);
                        context.startService(boatLocationService);
                    }

                    Log.i(TAG, "Message recieved: " + messages[0].getMessageBody());
                }
            }
        }
    }
}
