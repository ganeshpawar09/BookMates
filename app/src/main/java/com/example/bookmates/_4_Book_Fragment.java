package com.example.bookmates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class _4_Book_Fragment extends Fragment {
    static _7_Custom_Adapter_For_Book adapter;
    static ArrayList<_6_Book_Info> list = new ArrayList<>();
    RecyclerView recyclerView;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__4__book_, container, false);
        recyclerView = view.findViewById(R.id.book_recycle_view);
        searchView = view.findViewById(R.id.search_book);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new _7_Custom_Adapter_For_Book(requireContext(), list);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter_list(newText);
                return true;
            }
        });


        return view;
    }
    private void filter_list(String newText)
    {
        ArrayList<_6_Book_Info>new_list=new ArrayList<>();

        for (_6_Book_Info i:list)
        {
            if(i.getBranch().toLowerCase().contains(newText.toLowerCase()) ||
                    i.getBranch().toLowerCase().contains(newText.toLowerCase()) ||
                    i.getEdition_of_book().toLowerCase().contains(newText.toLowerCase()) ||
                    i.getName_of_book().toLowerCase().contains(newText.toLowerCase()) ||
                    i.getLocation_of_book().toLowerCase().contains(newText.toLowerCase())||
                    i.getPrice_of_book().toLowerCase().contains(newText.toLowerCase()))
            {
                new_list.add(i);
            }
        }
        if(new_list.isEmpty())
        {
        }
        else
        {
            adapter.set_filtered_list(new_list);
        }
    }
    public static void book()
    {
        FirebaseDatabase.getInstance().getReference("Books").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear(); // clear the list to avoid duplicates
                    for (DataSnapshot bookSnapshot : snapshot.getChildren()) {
                        _6_Book_Info bookInfo = bookSnapshot.getValue(_6_Book_Info.class);
                        if (bookInfo != null) {
                            list.add(bookInfo);
                            adapter.notifyDataSetChanged(); // update adapter with new data

                        }
                    }
                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}
