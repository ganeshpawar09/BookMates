package com.example.bookmates;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
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


public class _11_Add_Books extends Fragment {

    EditText name,price,edition,branch,location,number;
    TextView add_book;
    ImageView add_image;
    private final int Gallery_code=1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment__11__add__books, container, false);
        name=view.findViewById(R.id.name);
        price=view.findViewById(R.id.price);
        edition=view.findViewById(R.id.edition);
        branch=view.findViewById(R.id.branch);
        location=view.findViewById(R.id.location);
        number=view.findViewById(R.id.mobile_number);
        add_book=view.findViewById(R.id.add_book);
        add_image=view.findViewById(R.id.add_book_images);
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Gallery= new Intent(Intent.ACTION_PICK);
                Gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Gallery,Gallery_code);
            }
        });


        add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("") ||
                        price.getText().toString().equals("") ||
                        edition.getText().toString().equals("") ||
                        branch.getText().toString().equals("") ||
                        location.getText().toString().equals("") ||
                        number.getText().toString().equals("") ||
                        number.getText().toString().length()!=10
                )
                {
                    Toast.makeText(requireContext(), "Fill Form Correctly", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        Drawable drawable = add_image.getDrawable();
                        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] byteArray = baos.toByteArray();
                        String imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        SharedPreferences pre=requireContext().getSharedPreferences("LoginAndLogout", Context.MODE_PRIVATE);
                        String key=pre.getString("mobile_number", "0000000000");

                        DatabaseReference flatsRef = FirebaseDatabase.getInstance().getReference("Books");
                        DatabaseReference newFlatRef = flatsRef.push();
                        String newFlatId = newFlatRef.getKey();

                        _6_Book_Info a = new _6_Book_Info(fun(name.getText().toString()), edition.getText().toString() , fun(branch.getText().toString()), price.getText().toString(), fun(location.getText().toString()), number.getText().toString(),imageString,key,newFlatId);
                        newFlatRef.setValue(a);

                        Toast.makeText(requireContext(), "Book is successfully added", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        edition.setText("");
                        branch.setText("");
                        location.setText("");
                        price.setText("");
                        number.setText("");
                        add_image.setImageResource(R.drawable.addimage);
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
            add_image.setImageURI(data.getData());
        } else {
            if (getActivity() != null) {
            }
        }
    }
}