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

    public JSONObject sendData(Context context) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
            json.put("incoming", incomingType.getCount());
            json.put("outgoing", outGoingType.getCount());
            json.put("missed", missedType.getCount());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getSMSDate(Context context) {

        Uri sms_content = Uri.parse("content://sms/inbox");
        Cursor inboxSMS = context.getContentResolver().query(sms_content, null,null, null, null);

        Uri sms_content2 = Uri.parse("content://sms/sent");
        Cursor outboxSMS = context.getContentResolver().query(sms_content2, null,null, null, null);

        Log.d("inboxSMS", ""+inboxSMS.getCount());
        Log.d("outboxSMS", ""+outboxSMS.getCount());

        try {
            json.put("inboxSMS", inboxSMS.getCount());
            json.put("outboxSMS", outboxSMS.getCount());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
