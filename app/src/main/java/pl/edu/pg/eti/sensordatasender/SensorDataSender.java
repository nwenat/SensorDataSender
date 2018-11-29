package pl.edu.pg.eti.sensordatasender;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SensorDataSender {


    public void sendData(Context context, String serverAddress) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        String dateTime = simpleDateFormat.format(new Date());

        Log.d("activityyy", dateTime);

        getCallsData(context);
        getSMSDate(context);
    }

    public void getCallsData(Context context) {

        String[] projection = { CallLog.Calls.CACHED_NAME, CallLog.Calls.CACHED_NUMBER_LABEL, CallLog.Calls.TYPE };
        String where;

        where = CallLog.Calls.TYPE+"="+CallLog.Calls.INCOMING_TYPE;
        Cursor incomingType = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, where, null, null);
        Log.d("INCOMING CALL", ""+incomingType.getCount());

        where = CallLog.Calls.TYPE+"="+CallLog.Calls.OUTGOING_TYPE;
        Cursor outGoingType = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, where, null, null);
        Log.d("OUT GOING CALL", ""+outGoingType.getCount());

        where = CallLog.Calls.TYPE+"="+CallLog.Calls.MISSED_TYPE;
        Cursor missedType = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, where, null, null);
        Log.d("MISSED CALL", ""+missedType.getCount());

        int incoming = incomingType.getCount();
        int outGoing = outGoingType.getCount();
        int missed = missedType.getCount();
    }

    private void getSMSDate(Context context) {

        Uri sms_content = Uri.parse("content://sms/inbox");
        Cursor inboxSMS = context.getContentResolver().query(sms_content, null,null, null, null);

        Uri sms_content2 = Uri.parse("content://sms/sent");
        Cursor outboxSMS = context.getContentResolver().query(sms_content2, null,null, null, null);

        Log.d("INBOX SMS", ""+inboxSMS.getCount());
        Log.d("OUTBOX SMS", ""+outboxSMS.getCount());

        int inbox = inboxSMS.getCount();
        int outbox = outboxSMS.getCount();

    }

}
