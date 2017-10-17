package kasembhundit.kutthareeya.thiti.manageres;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.ImageView;

public class ShowNotification extends AppCompatActivity {
    private ImageView checkorderImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notification);

        showNoti();

        checkorderImageView = (ImageView) findViewById(R.id.imvOrder);
        checkorderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowNotification.this, ReceiveActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }   // Main Method

    private void showNoti() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_authen);
        builder.setTicker("MasterUNG Alert");
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle("อาหารเสร็จแล้ว");
        builder.setContentText("โปรดไปรับอาหารด้วยนะค่ะ");
        builder.setAutoCancel(false);


        Uri soundUri = RingtoneManager.getDefaultUri(Notification.DEFAULT_SOUND);
        builder.setSound(soundUri);

        android.app.Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1000, notification);


    }
}   // Main Class