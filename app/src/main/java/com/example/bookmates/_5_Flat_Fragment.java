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


public class _5_Flat_Fragment extends Fragment {

    RecyclerView recyclerView;
    static _9_Custom_Adapter_For_Flat customAdapterForFlat;
    static ArrayList<_8_Flat_info>list1=new ArrayList<>();
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment__5__flat_, container, false);
        _5_Flat_Fragment.flat();
        recyclerView=view.findViewById(R.id.flat_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        customAdapterForFlat=new _9_Custom_Adapter_For_Flat(requireContext(),list1);
        recyclerView.setAdapter(customAdapterForFlat);
        searchView=view.findViewById(R.id.search_flat);
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
        ArrayList<_8_Flat_info>new_list=new ArrayList<>();

        for (_8_Flat_info i:list1)
        {
            if(i.getFurnished().toLowerCase().contains(newText.toLowerCase()) ||
                    i.getBhk().toLowerCase().contains(newText.toLowerCase()) ||
                    i.getRent().toLowerCase().contains(newText.toLowerCase()) ||
                    i.getPreferred().toLowerCase().contains(newText.toLowerCase()) ||
                    i.getLocation().toLowerCase().contains(newText.toLowerCase())||
                    i.getMember_required().toLowerCase().contains(newText.toLowerCase()))
            {
                new_list.add(i);
            }
        }
        if(new_list.isEmpty())
        {
        }
        else
        {
            customAdapterForFlat.set_filtered_list1(new_list);
        }
    }
    public static void flat()
    {
        FirebaseDatabase.getInstance().getReference().child("Flats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    if (snapshot.exists()) {
                        list1.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            // deserialize flat info object from database
                            _8_Flat_info temp = snapshot1.getValue(_8_Flat_info.class);
                            if (temp != null) {
                                list1.add(temp);
                                customAdapterForFlat.notifyDataSetChanged();

                            }
                        }
                    } else {
                    }
                }catch (Exception e)
                {
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}