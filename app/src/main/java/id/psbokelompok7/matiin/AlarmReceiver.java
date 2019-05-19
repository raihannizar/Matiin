package id.psbokelompok7.matiin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intentRingtone = new Intent(context, RingtonePlayingService.class);      //Kasih intent ke ringtone service
        context.startService(intentRingtone);       //Start ringtone service

        context.sendBroadcast(showButtonMatikanAlarm());        //Kirim intent kalo alarm nyala munculin tombol "matikan alarm".
    }

    private Intent showButtonMatikanAlarm() {
        Intent intentShowButtonMatikan = new Intent("show button");
        intentShowButtonMatikan.putExtra("muncul", 0);
        return intentShowButtonMatikan;
    }

}
