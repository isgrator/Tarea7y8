package org.imgt.tarea7y8;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by Isabel María on 07/12/2017.
 */

public class ReceptorSMS extends BroadcastReceiver {

    @Override public void onReceive(Context context, Intent intent) {

        //Arranca el servicio de música (pág.369, paso 1)
        context.startService(new Intent(context,ServicioMusica.class));

        //Abre la actividad que arranca el servicio de música
         /*       Intent i= new Intent(context, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);*/

    }

}
