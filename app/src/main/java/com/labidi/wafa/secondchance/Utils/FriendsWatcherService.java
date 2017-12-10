package com.labidi.wafa.secondchance.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Entities.Response.DemandesResponse;
import com.labidi.wafa.secondchance.Entities.User;
import com.labidi.wafa.secondchance.HomeActivity;
import com.labidi.wafa.secondchance.MainActivity;
import com.labidi.wafa.secondchance.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sofien on 09/12/2017.
 */

public class FriendsWatcherService extends JobService {
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private int notification_id;
    private RemoteViews remoteViews;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        prepareNotification();
        checkPost(jobParameters);


        return true;
    }

    private void prepareNotification() {
        Intent intent = new Intent(this , MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this ,0,intent,0);
        notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        remoteViews = new RemoteViews(getPackageName(), R.layout.notification_friend_request);
        notification_id = (int) System.currentTimeMillis();
        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_x).setCustomContentView(remoteViews).setContentIntent(pendingIntent);

    }

    private void checkPost(JobParameters jobParameters) {
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface invtations = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        Call<DemandesResponse> call = invtations.checkInvitation(User.Id);
        call.enqueue(new Callback<DemandesResponse>() {
            @Override
            public void onResponse(Call<DemandesResponse> call, Response<DemandesResponse> response) {
                if (response.body().getDemandes() != null) {
                    notificationManager.notify(notification_id,builder.build());
                    jobFinished(jobParameters, false);
                }
            }

            @Override
            public void onFailure(Call<DemandesResponse> call, Throwable t) {
                Log.e("erruerrécupération", t.getMessage());
            }
        });

    }


    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
