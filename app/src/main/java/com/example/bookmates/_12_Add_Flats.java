package com.example.bookmates;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class _12_Add_Flats extends Fragment {
    EditText pre,member,rent,bhk,furnished,location,number;
    TextView add_flat;
    ImageView image;
    private final int Gallery_code=1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment__12__add__flats, container, false);
        pre=view.findViewById(R.id.preferred_flat);
        member=view.findViewById(R.id.member_required_flat);
        rent=view.findViewById(R.id.rent_flat);
        bhk=view.findViewById(R.id.bhk_flat);
        furnished=view.findViewById(R.id.furnished_flat);
        location=view.findViewById(R.id.location_flat);
        number=view.findViewById(R.id.mobile_number_flat);
        add_flat=view.findViewById(R.id.add_flat);
        image=view.findViewById(R.id.flat_images);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Gallery= new Intent(Intent.ACTION_PICK);
                Gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Gallery,Gallery_code);
            }
        });
        add_flat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pre.getText().toString().equals("") ||
                        member.getText().toString().equals("") ||
                        rent.getText().toString().equals("") ||
                        bhk.getText().toString().equals("") ||
                        furnished.getText().toString().equals("")||
                        location.getText().toString().equals("") ||
                        number.getText().toString().equals("") ||
                        number.getText().toString().length()!=10
                )
                {
                    Toast.makeText(requireContext(), "Fill Form Correctly", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        Drawable drawable = image.getDrawable();
                        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] byteArray = baos.toByteArray();
                        String imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        SharedPreferences pref=requireContext().getSharedPreferences("LoginAndLogout", Context.MODE_PRIVATE);
                        String key=pref.getString("mobile_number", "0000000000");

                        DatabaseReference flatsRef = FirebaseDatabase.getInstance().getReference("Flats");
                        DatabaseReference newFlatRef = flatsRef.push();
                        String newFlatId = newFlatRef.getKey();
                        _8_Flat_info a = new _8_Flat_info(fun(pre.getText().toString()),member.getText().toString(),rent.getText().toString(),bhk.getText().toString(),fun(furnished.getText().toString()),fun(location.getText().toString()),number.getText().toString(),imageString,key,newFlatId);
                        newFlatRef.setValue(a);
                        Toast.makeText(requireContext(), "Flat is successfully added", Toast.LENGTH_SHORT).show();

                        pre.setText("");
                        member.setText("");
                        rent.setText("");
                        bhk.setText("");
                        location.setText("");
                        furnished.setText("");
                        number.setText("");
                        image.setImageResource(R.drawable.addimage);
                    }catch (Exception e)
                    {
                        Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }
    public String fun(String str)
    {
        String[] words = str.split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String word : words) {
            sb.append(word.substring(0, 1).toUpperCase() + word.substring(1)).append(" ");
        }

        String result = sb.toString().trim();
        return result;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_code && resultCode == requireActivity().RESULT_OK && data != null) {
            // If an image is selected, set it in the ImageView
            image.setImageURI(data.getData());
        } else {
            if (getActivity() != null) {
            }
        }
    }




}
