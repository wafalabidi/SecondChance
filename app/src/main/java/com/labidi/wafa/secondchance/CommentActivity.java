package com.labidi.wafa.secondchance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.labidi.wafa.secondchance.API.RetrofitClient;
import com.labidi.wafa.secondchance.API.UserService;
import com.labidi.wafa.secondchance.Adapters.CommentAdapter;
import com.labidi.wafa.secondchance.Entities.Commentaire;
import com.labidi.wafa.secondchance.Entities.ConfirmationResponse;
import com.labidi.wafa.secondchance.Entities.Response.CommentaireResponse;
import com.labidi.wafa.secondchance.Entities.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends Activity {
    Commentaire commentaire;
    ArrayList<Commentaire> commentaires;
    @BindView(R.id.newComment)
    EditText newComment;
    @BindView(R.id.comments)
    RecyclerView comments;
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comment);
        this.setFinishOnTouchOutside(false);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ButterKnife.bind(this);
        button.setOnClickListener(view -> {
            commentaire.setSayin(newComment.getText().toString());
            insertComment();
        });
        if (getIntent().getIntExtra("idPost", 0) == 0) {
            Toast.makeText(this, "Connexion Erreur", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            commentaire = new Commentaire();
            commentaire.setIdPost(getIntent().getIntExtra("idPost", 0));
            commentaire.setIdUser(User.Id);
            commentaires = new ArrayList<>();
            getComments(commentaire.getIdPost());


        }
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        comments.setLayoutManager(layoutManager);
    }

    private void getComments(int idPost) {
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.Commentaire getComments = retrofitClient.getRetrofit().create(UserService.Commentaire.class);
        Call<CommentaireResponse> call = getComments.getCommentaire(idPost);
        call.enqueue(new Callback<CommentaireResponse>() {
            @Override
            public void onResponse(Call<CommentaireResponse> call, Response<CommentaireResponse> response) {
                if (response.body() != null) {
                    commentaires = response.body().getComs();
                    if (commentaire != null) {
                        CommentAdapter commentAdapter = new CommentAdapter(CommentActivity.this, commentaires);
                        comments.setAdapter(commentAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<CommentaireResponse> call, Throwable t) {

            }
        });
    }

    private void insertComment() {
        RetrofitClient retrofitClient = new RetrofitClient();
        UserService.Commentaire getComments = retrofitClient.getRetrofit().create(UserService.Commentaire.class);
        Call<ConfirmationResponse> call = getComments.comment(commentaire);
        call.enqueue(new Callback<ConfirmationResponse>() {
            @Override
            public void onResponse(Call<ConfirmationResponse> call, Response<ConfirmationResponse> response) {

            }

            @Override
            public void onFailure(Call<ConfirmationResponse> call, Throwable t) {

            }
        });
    }
}
