package com.example.news.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.news.R;
import com.example.news.api.ApiManager;
import com.example.news.models.ApiResponse;
import com.example.news.models.ArticleItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestActivity extends AppCompatActivity {

    private ApiManager apiManager;
    private RecyclerView articlesRecyclerView;
    private List<ArticleItem> items;
    private ArticlesGuestListAdapter articlesGuestListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        items = new ArrayList<>();
        apiManager = new ApiManager();
        articlesRecyclerView = findViewById(R.id.guestView);

        articlesGuestListAdapter = new ArticlesGuestListAdapter(items);

        articlesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        articlesRecyclerView.setAdapter(articlesGuestListAdapter);

        getRandom("us");
        getRandom("au");

    }

    public void getRandom(String random)   {
        apiManager.getRandom(random).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful()) {
                    ApiResponse articles = response.body();
                    items.addAll(articles.getArticles());
                    articlesGuestListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.auth:
                goToAuth();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToAuth()  {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public static class ArticlesGuestListAdapter extends RecyclerView.Adapter<ArticlesGuestListAdapter.ViewHolder>    {

        private List<ArticleItem> items;

        public ArticlesGuestListAdapter(List<ArticleItem> items)    {
            this.items = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Context context = holder.itemView.getContext();
            ArticleItem articleItem = items.get(position);

            String title = articleItem.getTitle();
            holder.title.setText(title);

            String description = articleItem.getDescription();
            holder.description.setText(description);

            String link = articleItem.getURL();
            holder.link.setText(link);

            String iconUrl = articleItem.getImageURL();
            Picasso.with(context).load(iconUrl).into(holder.image);

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView image;
            TextView title, description, link;

            public ViewHolder(View itemView)    {
                super(itemView);
                image = itemView.findViewById(R.id.imageView);
                title = itemView.findViewById(R.id.textView2);
                description = itemView.findViewById(R.id.textView3);
                link = itemView.findViewById(R.id.textView4);
            }
        }
    }
}
