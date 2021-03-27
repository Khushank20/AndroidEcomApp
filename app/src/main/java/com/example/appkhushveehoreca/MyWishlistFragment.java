package com.example.appkhushveehoreca;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class MyWishlistFragment extends Fragment {

    public MyWishlistFragment() {
        // Required empty public constructor
    }

    private RecyclerView wishlistRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wishlist, container, false);

        wishlistRecyclerView = view.findViewById(R.id.my_wishlist_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        wishlistRecyclerView.setLayoutManager(linearLayoutManager);

        List<WishlistModel> wishlistModelList = new ArrayList<>();
        wishlistModelList.add(new WishlistModel(R.drawable.steelfour,"Steel Vessel","SC-125","Contact us for","Rs.1200/-","Rs.1400/-"));
        wishlistModelList.add(new WishlistModel(R.drawable.steelfour,"Steel Vessel","SC-125","Contact us for","Rs.1200/-","Rs.1400/-"));
        wishlistModelList.add(new WishlistModel(R.drawable.steelfour,"Steel Vessel","SC-125","Contact us for","Rs.1200/-","Rs.1400/-"));
        wishlistModelList.add(new WishlistModel(R.drawable.steelfour,"Steel Vessel","SC-125","Contact us for","Rs.1200/-","Rs.1400/-"));

        WishlistAdapter wishlistAdapter = new WishlistAdapter(wishlistModelList,true);
        wishlistRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();

        return view;

    }
}