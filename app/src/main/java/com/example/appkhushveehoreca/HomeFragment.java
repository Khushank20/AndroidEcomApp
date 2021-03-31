package com.example.appkhushveehoreca;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.appkhushveehoreca.DBqueries.categoryModelList;
import static com.example.appkhushveehoreca.DBqueries.firebaseFirestore;
import static com.example.appkhushveehoreca.DBqueries.lists;
import static com.example.appkhushveehoreca.DBqueries.loadCategories;
import static com.example.appkhushveehoreca.DBqueries.loadCategoriesNames;
import static com.example.appkhushveehoreca.DBqueries.loadFragmentData;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    public static SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView homePageRecyclerView;
    private HomePageAdapter adapter;
    private ImageView noInternetConnection;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        noInternetConnection = view.findViewById(R.id.no_internet_connection);

        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected() == true){
            noInternetConnection.setVisibility(View.GONE);

            categoryRecyclerView = (RecyclerView) view.findViewById(R.id.category_recyclerview);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            categoryRecyclerView.setLayoutManager(layoutManager);
            categoryAdapter = new CategoryAdapter(categoryModelList);
            categoryRecyclerView.setAdapter(categoryAdapter);

            if(categoryModelList.size() == 0){
                loadCategories(categoryAdapter,getContext());
            }else{
                categoryAdapter.notifyDataSetChanged();
            }

            homePageRecyclerView = view.findViewById(R.id.home_page_recyclerview);
            LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
            testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            homePageRecyclerView.setLayoutManager(testingLayoutManager);

            if(lists.size() == 0){
                loadCategoriesNames.add("Home");
                lists.add(new ArrayList<HomePageModel>());
                adapter = new HomePageAdapter(lists.get(0));
                loadFragmentData(adapter,getContext(),0,"Home");
            }else{
                adapter = new HomePageAdapter(lists.get(0));
                adapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(adapter);

        }else{
            Glide.with(this).load(R.drawable.nointernetconnection).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
        }

//        adapter.notifyDataSetChanged();

        ///////// refresh layout

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);

                categoryModelList.clear();
                lists.clear();
                loadCategoriesNames.clear();
                if (networkInfo != null && networkInfo.isConnected() == true) {
                    noInternetConnection.setVisibility(View.GONE);

                    loadCategories(categoryAdapter,getContext());

                    loadCategoriesNames.add("Home");
                    lists.add(new ArrayList<HomePageModel>());
                    loadFragmentData(adapter,getContext(),0,"Home");

                } else {
                    Glide.with(getContext()).load(R.drawable.nointernetconnection).into(noInternetConnection);
                    noInternetConnection.setVisibility(View.VISIBLE);
                }
            }
        });

        ///////// refresh layout


        return view;
    }
}