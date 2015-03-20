package fr.istic.m2gla.mmm.client;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;

/**
 * @author appsrox.com
 */
public class Common extends Application {

    public static final String PROFILE_ID = "profile_id";

    public static final String ACTION_REGISTER = "fr.istic.m2gla.mmm.REGISTER";
    public static final String EXTRA_STATUS = "status";
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_FAILED = 0;

    //parameters recognized by demo server
    public static final String FROM = "email";
    public static final String REG_ID = "regId";
    public static final String MSG = "msg";
    public static final String TO = "to";

    public static String[] email_arr = {};

    private static SharedPreferences prefs;

    @Override
    public void onCreate() {
        Log.i("mds", "Super on Create");
        super.onCreate();
        Log.i("mds", "oncreate");
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        List<String> emailList = getEmailList();
        email_arr = emailList.toArray(new String[emailList.size()]);
    }

    private List<String> getEmailList() {
        List<String> lst = new ArrayList<String>();
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (Patterns.EMAIL_ADDRESS.matcher(account.name).matches()) {
                lst.add(account.name);
            }
        }
        return lst;
    }

    public static String getPreferredEmail() {
        return prefs.getString("chat_email_id", email_arr.length == 0 ? "abc@example.com" : email_arr[0]);
    }

    public static String getDisplayName() {
        String email = getPreferredEmail();
        return prefs.getString("display_name", email.substring(0, email.indexOf('@')));
    }

    public static boolean isNotify() {
        return prefs.getBoolean("notifications_new_message", true);
    }

    public static String getRingtone() {
        return prefs.getString("notifications_new_message_ringtone", android.provider.Settings.System.DEFAULT_NOTIFICATION_URI.toString());
    }

    public static String getServerUrl() {
        return prefs.getString("server_url_pref", Constants.SERVER_URL);
    }

    public static String getSenderId() {
        Log.i("mds", "Sender Id  " + Constants.SENDER_ID);
        return prefs.getString("sender_id_pref", Constants.SENDER_ID);
    }
//
//    public static void initPrefs() {
//        if (prefs == null) {
//            prefs = PreferenceManager.getDefaultSharedPreferences();
//        }
//    }

}
