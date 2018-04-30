package com.example.news.fragments;

import android.app.Fragment;
import android.content.Context;
 import android.net.Uri;
 import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news.R;
import com.example.news.api.ApiManager;
import com.example.news.api.LoginApiManager;
import com.example.news.local.storage.LocalStorageManager;
import com.example.news.models.ApiResponse;
import com.example.news.models.ArticleItem;
import com.example.news.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private LocalStorageManager localStorageManager;
    private static LoginApiManager loginApiManager;
    private String token;
    private RecyclerView favoritesRecyclerView;
    private FavoritesListAdapter favoritesListAdapter;
    private List<String> toSearch;
    private User user;
    private Button add;
    private EditText addEditText;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toSearch = new ArrayList<>();

        loginApiManager = LoginApiManager.getInstance();
        localStorageManager = LocalStorageManager.getInstance(getActivity().getApplicationContext());
        user = localStorageManager.getUser();
        token = user.getToken();
        toSearch = user.getFavorites();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        addEditText = view.findViewById(R.id.editText);

        add = view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toAdd = addEditText.getText().toString();
                String jdid = toAdd.replace("","");
                toSearch.add(jdid);
                updateFavorite(toSearch, token);
                addEditText.setText("");
                favoritesListAdapter.notifyDataSetChanged();
            }
        });


        favoritesListAdapter = new FavoritesListAdapter(toSearch);
        favoritesRecyclerView = view.findViewById(R.id.favoritesRecyclerView);

        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        favoritesRecyclerView.setAdapter(favoritesListAdapter);

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

    public void updateFavorite(List<String> q, String token)   {
        loginApiManager.updateFavorite(q, token).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }

    public class FavoritesListAdapter extends RecyclerView.Adapter<FavoriteFragment.FavoritesListAdapter.ViewHolder>    {

        private List<String> toSearch;
        private String token;
        private LocalStorageManager localStorageManager;
        private User user;

        public FavoritesListAdapter(List<String> toSearch)    {
            this.toSearch = toSearch;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_layout, parent, false);

            loginApiManager = LoginApiManager.getInstance();
            localStorageManager = LocalStorageManager.getInstance(getActivity().getApplicationContext());
            user = localStorageManager.getUser();
            token = user.getToken();

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            Context context = holder.itemView.getContext();
            String type = toSearch.get(position);

            type = type.replace("\"", "").replace("\\", "");
            holder.title.setText(type.toString());
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    toSearch.remove(holder.getAdapterPosition());
                    updateFavorite(toSearch, token);
                    holder.imageView.setVisibility(View.INVISIBLE);
                    notifyItemRemoved(holder.getAdapterPosition());
                    notifyItemRangeChanged(holder.getAdapterPosition(), toSearch.size());

                }
            });

        }

        @Override
        public int getItemCount() {
            return toSearch.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView title;
            ImageView imageView;

            public ViewHolder(View itemView)    {
                super(itemView);

                title = itemView.findViewById(R.id.typeTextView);
                imageView = itemView.findViewById(R.id.imageButton);

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        imageView.setVisibility(View.VISIBLE);
                        return true;
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageView.setVisibility(View.INVISIBLE);
                    }
                });

            }
        }
    }

}