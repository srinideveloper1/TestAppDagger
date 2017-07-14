package com.srinug.testapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.srinug.testapp.R;
import com.srinug.testapp.api.model.ApiError;
import com.srinug.testapp.api.model.GitCommitsData;
import com.srinug.testapp.api.service.GitClient;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL  = "https://api.github.com/";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    /**
     * Initialize views
     */
    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadAndDisplayDaggerCommits();
    }

    /**
     * Load Api response data and Display in the Recycler view
     */
    private void loadAndDisplayDaggerCommits() {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();

        final Retrofit retrofit = retrofitBuilder
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitClient gitClient = retrofit.create(GitClient.class);

        Call<List<GitCommitsData>> gitCommitsData = gitClient.getRepoCommits("google", "dagger");

        gitCommitsData.enqueue(new Callback<List<GitCommitsData>>() {
            @Override
            public void onResponse(Call<List<GitCommitsData>> call, Response<List<GitCommitsData>> response) {
                if (response.isSuccessful()) {
                    CommitsAdapter commitsAdapter = new CommitsAdapter(response.body());
                    recyclerView.setAdapter(commitsAdapter);
                } else {
                    handleErrorScenario(retrofit, response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<GitCommitsData>> call, Throwable t) {
                showToast("Request Failed!");
            }
        });
    }

    /**
     * Handles Error scenarios
     * @param retrofit - Retrofit reference
     * @param responseBody - ResponseBody reference
     */
    private void handleErrorScenario(Retrofit retrofit, ResponseBody responseBody) {
        Converter<ResponseBody, ApiError> errorBodyConverter = retrofit
                .responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError error = null;
        try {
            error = errorBodyConverter.convert(responseBody);
        } catch (IOException e) {
            showToast("Unknown error");
        }

        if (error != null) {
            showToast(error.message());
        }
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
