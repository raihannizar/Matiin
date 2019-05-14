package id.psbokelompok7.matiin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("Mantap jiwa!","Terima di reciever bosqu!");

        //Kasih intent ke ringtone service
        Intent intentRingtone = new Intent(context, RingtonePlayingService.class);

        //Start ringtone service
        context.startService(intentRingtone);

    }
}
