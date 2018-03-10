package com.esprit.secondchance.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.esprit.secondchance.API.RetrofitClient;
import com.esprit.secondchance.API.UserService;
import com.esprit.secondchance.Entities.Response.DemandesResponse;
import com.esprit.secondchance.PendingFriendRequestActivity;
import com.esprit.secondchance.R;

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
        return false;
    }

    private void prepareNotification() {
        Intent intent = new Intent(this, PendingFriendRequestActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        remoteViews = new RemoteViews(getPackageName(), R.layout.notification_friend_request);
        notification_id = 100;
        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_x).setCustomContentView(remoteViews).setContentIntent(pendingIntent).setAutoCancel(true);

    }

    private void checkPost(JobParameters jobParameters) {
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.RegisterInterface invtations = retrofitClient.getRetrofit().create(UserService.RegisterInterface.class);
        LocalFiles localFiles = new LocalFiles(getSharedPreferences(LocalFiles.USER_FILE, Context.MODE_PRIVATE));
        Call<DemandesResponse> call = invtations.checkInvitation(localFiles.getInt(LocalFiles.Id));
        call.enqueue(new Callback<DemandesResponse>() {
            @Override
            public void onResponse(Call<DemandesResponse> call, Response<DemandesResponse> response) {
                if (response.body().getDemandes() != null) {
                    notificationManager.notify(notification_id, builder.build());
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
