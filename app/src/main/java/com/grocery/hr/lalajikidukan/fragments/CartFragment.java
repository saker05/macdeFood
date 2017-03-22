package com.grocery.hr.lalajikidukan.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.adapters.*;
/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    private ArrayList countries;
    public static final String TAG= CartFragment.class.getSimpleName();

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance()
    {
        return  new CartFragment();
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View recycleview = inflater.inflate(R.layout.fragment_cart, container, false);




        RecyclerView recyclerView = (RecyclerView)recycleview.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        countries = new ArrayList<>();
        countries.add("Australia");
        countries.add("India");
        countries.add("United States of America");
        countries.add("Germany");
        countries.add("Russia");
        countries.add("Russia1");
        countries.add("Russia2");
        countries.add("Russia3");
        countries.add("Russia4");
        countries.add("Russia5");
        countries.add("Russia6");
        countries.add("Russia7");
        countries.add("Russia8");


        DataAdapter adapter = new DataAdapter(countries);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {


                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
                    // Toast.makeText(getContext(),countries.get(position), Toast.LENGTH_SHORT).show();
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        return recycleview;
    }
}


