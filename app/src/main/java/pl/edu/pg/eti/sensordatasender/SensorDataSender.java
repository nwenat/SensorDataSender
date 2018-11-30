package pl.edu.pg.eti.sensordatasender;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SensorDataSender {
    JSONObject json = new JSONObject();



    public JSONObject sendData(Context context, String serverAddress) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        String dateTime = simpleDateFormat.format(new Date());

        try {
            json.put("dateTime", dateTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("activityyy", dateTime);

        getCallsData(context);
        getSMSDate(context);
        Log.d("activityyy", json.toString());
        return json;
    }

    public void getCallsData(Context context) {

        JSONObject jsonCall = new JSONObject();

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

        try {
            jsonCall.put("INCOMING", incomingType.getCount());
            jsonCall.put("OUT", outGoingType.getCount());
            jsonCall.put("MISSED", missedType.getCount());
            json.put("call", jsonCall);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getSMSDate(Context context) {

        JSONObject jsonSMS = new JSONObject();

        Uri sms_content = Uri.parse("content://sms/inbox");
        Cursor inboxSMS = context.getContentResolver().query(sms_content, null,null, null, null);

        Uri sms_content2 = Uri.parse("content://sms/sent");
        Cursor outboxSMS = context.getContentResolver().query(sms_content2, null,null, null, null);

        Log.d("INBOX SMS", ""+inboxSMS.getCount());
        Log.d("OUTBOX SMS", ""+outboxSMS.getCount());

        try {
            jsonSMS.put("INBOX", inboxSMS.getCount());
            jsonSMS.put("OUTBOX", outboxSMS.getCount());
            json.put("SMS", jsonSMS);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
