package com.example.news.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.news.R;
import com.example.news.api.ApiManager;
import com.example.news.local.storage.LocalStorageManager;
import com.example.news.models.ApiResponse;
import com.example.news.models.ArticleItem;
import com.example.news.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ArticlesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private ApiManager apiManager;
    private List<ArticleItem> items;
    private RecyclerView articlesRecyclerView;
    private ArticlesListAdapter articlesListAdapter;
    private LocalStorageManager localStorageManager;
    private String token;

    public ArticlesFragment() {
        // Required empty public constructor
    }

    public static ArticlesFragment newInstance() {
        ArticlesFragment fragment = new ArticlesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        items = new ArrayList<>();
        apiManager = new ApiManager();

        articlesListAdapter = new ArticlesListAdapter(items);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);

        articlesRecyclerView = view.findViewById(R.id.articles_view);

        articlesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        articlesRecyclerView.setAdapter(articlesListAdapter);

        localStorageManager = LocalStorageManager.getInstance(getActivity().getApplicationContext());
        User user = localStorageManager.getUser();
        token = user.getToken();
        List<String> toSearch = user.getFavorites();

        for(int i = 0; i < toSearch.size(); i++)
            getArticles(toSearch.get(i));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void getArticles(String q)   {
        apiManager.getArticles(q).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful()) {
                    ApiResponse articles = response.body();
                    items.addAll(articles.getArticles());
                    articlesListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }


    public static class ArticlesListAdapter extends RecyclerView.Adapter<ArticlesFragment.ArticlesListAdapter.ViewHolder>    {

        private List<ArticleItem> items;

        public ArticlesListAdapter(List<ArticleItem> items)    {
            this.items = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new ArticlesListAdapter.ViewHolder(view);
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
