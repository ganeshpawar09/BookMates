package com.example.bookmates;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class _7_Custom_Adapter_For_Book extends RecyclerView.Adapter<_7_Custom_Adapter_For_Book.ViewHolder> {

    Context context;
    ArrayList<_6_Book_Info> list;
    _7_Custom_Adapter_For_Book(Context context, ArrayList<_6_Book_Info>list)
    {
        this.list=list;
        this.context=context;
        notifyDataSetChanged();
    }
    public void set_filtered_list(ArrayList<_6_Book_Info>list)
    {
        this.list=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout._1_book_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.Name.setText("Name: "+list.get(position).getName_of_book());
        holder.Price.setText("Price: \u20B9"+list.get(position).getPrice_of_book());
        holder.Location.setText("Location: "+list.get(position).getLocation_of_book());
        holder.Edition.setText("Edition: "+list.get(position).getEdition_of_book());
        holder.Branch.setText("Branch: "+list.get(position).getBranch());

        byte[] decodedString = Base64.decode(list.get(position).getImage(), Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imageView.setImageBitmap(decodedBitmap);


        holder.Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number=list.get(position).getNumber();
                if(!number.startsWith("+91"))
                {
                    number="+91"+number;
                }
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" + Uri.encode(number)));
                intent.putExtra("sms_body", "Hello");
                context.startActivity(intent);

            }
        });

        holder.Call.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String phoneNumber = list.get(position).getNumber();
            if (!phoneNumber.startsWith("+91")) {
                phoneNumber = "+91" + phoneNumber;
            }
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            holder.itemView.getContext().startActivity(callIntent);
        }
        });

        holder.line.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences preferences = v.getContext().getSharedPreferences("LoginAndLogout", Context.MODE_PRIVATE);
                if(list.size() > position && list.get(position).getKey().equals(preferences.getString("mobile_number", "0000000000"))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Confirm Delete");
                    builder.setMessage("Do you want to delete this?");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Books");
                            DatabaseReference myDataRef = databaseRef.child(list.get(position).getId());
                            list.remove(position);
                            notifyDataSetChanged();
                            myDataRef.removeValue(new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError != null) {
                                        // Handle the error here
                                        Toast.makeText(v.getContext(), "Sorry, could not delete the item.", Toast.LENGTH_SHORT).show();
                                    } else {

                                        Toast.makeText(v.getContext(), "Item deleted successfully.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing and dismiss the dialog
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name, Edition,Branch, Price, Location, Message, Call;
        ImageView imageView;
        LinearLayout line;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.name_of_book);
            Edition=itemView.findViewById(R.id.year_of_book);
            Price=itemView.findViewById(R.id.price_of_book);
            Location=itemView.findViewById(R.id.location_of_book);
            Message=itemView.findViewById(R.id.message_to_book_owner);
            Call=itemView.findViewById(R.id.call_to_book_owner);
            Branch=itemView.findViewById(R.id.branch_of_book);
            imageView=itemView.findViewById(R.id.picture_of_book);
            line=itemView.findViewById(R.id.line);

        }
    }
}
