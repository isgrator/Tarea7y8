package org.imgt.tarea7y8;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by Isabel María on 07/12/2017.
 */

public class ServicioMusica extends Service {
    private static final int ID_NOTIFICACION_CREAR = 1;
    MediaPlayer reproductor;

    @Override public void onCreate() {
        Toast.makeText(this,"Servicio creado",
                Toast.LENGTH_SHORT).show();
        reproductor = MediaPlayer.create(this, R.raw.audio);
    }

    @Override
    public int onStartCommand(Intent intenc, int flags, int idArranque) {
        //Creamos una notificación (pg.357)
        NotificationCompat.Builder notific = new NotificationCompat.Builder(this)
                .setContentTitle("¡Has recibido un mensaje!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Creando Servicio de Música al recibir SMS")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        android.R.drawable.ic_media_play))
                .setWhen(System.currentTimeMillis() + 1000 * 60 * 60)
                .setContentInfo("más info")
                .setTicker("Texto en barra de estado") ;

        //Lanzamiento de actividad desde la notificación (pg 357)
        PendingIntent intencionPendiente = PendingIntent.getActivity(
                this, 0, new Intent(this, MainActivity.class), 0);
        notific.setContentIntent(intencionPendiente);

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_NOTIFICACION_CREAR, notific.build());



        Toast.makeText(this,"Servicio arrancado "+ idArranque,
                Toast.LENGTH_SHORT).show();
        reproductor.start();
        return START_STICKY;
    }

    @Override public void onDestroy() {
        Toast.makeText(this,"Servicio detenido",
                Toast.LENGTH_SHORT).show();
        reproductor.stop();
        reproductor.release();

        //Eliminar notificación (pág. 358)
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(ID_NOTIFICACION_CREAR);

    }

    @Override public IBinder onBind(Intent intencion) {
        return null;
    }
}
