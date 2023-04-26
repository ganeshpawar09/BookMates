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

public class _9_Custom_Adapter_For_Flat extends RecyclerView.Adapter<_9_Custom_Adapter_For_Flat.ViewHolder> {
    Context context;
    ArrayList<_8_Flat_info>list1=new ArrayList<>();
    _9_Custom_Adapter_For_Flat(Context context, ArrayList<_8_Flat_info>list)
    {
        this.context=context;
        this.list1=list;
    }
    public void set_filtered_list1(ArrayList<_8_Flat_info>list)
    {
        this.list1=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout._2_flat_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        _8_Flat_info temp=list1.get(position);
        holder.preferred.setText("Preferred: "+temp.getPreferred());
        holder.member_required.setText("Member Required: "+temp.getMember_required());
        holder.rent.setText("Rent: \u20B9"+temp.getRent());
        holder.bhk.setText("BHK: "+temp.getBhk());
        holder.furnished.setText("Furnished: "+temp.getFurnished());
        holder.location.setText("Location: "+temp.getLocation());

        byte[] decodedString = Base64.decode(list1.get(position).getImage(), Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imageView.setImageBitmap(decodedBitmap);


        holder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number=list1.get(position).getNumber();
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

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = list1.get(position).getNumber();
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
                if(list1.size() > position && list1.get(position).getKey().equals(preferences.getString("mobile_number", "0000000000"))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Confirm Delete");
                    builder.setMessage("Do you want to delete this?");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Flats");
                            DatabaseReference myDataRef = databaseRef.child(list1.get(position).getId());
                            list1.remove(position);
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
        return list1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView preferred, member_required, rent, bhk, furnished, location, message,call;
        ImageView imageView;
        LinearLayout line;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            preferred=itemView.findViewById(R.id.flat_preferred);
            member_required=itemView.findViewById(R.id.flat_member);
            rent=itemView.findViewById(R.id.flat_rent);
            bhk=itemView.findViewById(R.id.flat_bhk);
            furnished=itemView.findViewById(R.id.flat_furnished_or_not);
            location=itemView.findViewById(R.id.flat_location);
            message=itemView.findViewById(R.id.flat_message);
            call=itemView.findViewById(R.id.flat_call);
            imageView=itemView.findViewById(R.id.image_for_flat);
            line=itemView.findViewById(R.id.line);
        }
    }
}
