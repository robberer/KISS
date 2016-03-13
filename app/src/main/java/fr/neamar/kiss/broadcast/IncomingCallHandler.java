package fr.neamar.kiss.broadcast;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;

import fr.neamar.kiss.DataHandler;
import fr.neamar.kiss.KissApplication;
import fr.neamar.kiss.dataprovider.ContactsProvider;
import fr.neamar.kiss.pojo.ContactsPojo;

public class IncomingCallHandler extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        try {
            DataHandler dataHandler = KissApplication.getDataHandler(context);
            ContactsProvider contactsProvider = dataHandler.getContactsProvider();

            // Stop if contacts are not enabled
            if (contactsProvider == null) {
                return;
            }

            contactsProvider.reload();

            if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

                if(phoneNumber == null) {
                    // Skipping (private call)
                    return;
                }

                ContactsPojo contactPojo = contactsProvider.findByPhone(phoneNumber);
                if (contactPojo != null) {
                    ContentResolver resolver = context.getContentResolver();
                    ContactsContract.Contacts.markAsContacted(resolver, contactPojo._id);
                    dataHandler.addToHistory(contactPojo.id);
                    contactsProvider.reload();
                }
            }
        } catch (Exception e) {
            Log.e("Phone Receive Error", " " + e);
        }
    }
}