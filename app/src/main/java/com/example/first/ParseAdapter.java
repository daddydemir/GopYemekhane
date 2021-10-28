package com.example.first;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ParseAdapter extends RecyclerView.Adapter<ParseAdapter.ViewHolder> {


    private ArrayList<ParseItem> parseItems ;
    private Context context;

    public ParseAdapter(ArrayList<ParseItem> parseItems , Context context){
        this.parseItems = parseItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ParseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout._tmp , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParseAdapter.ViewHolder holder, int position) {

        ParseItem parseItem = parseItems.get(position);
        holder.day.setText(parseItem.getDay());
        holder.eat1.setText(parseItem.getEat1());
        holder.eat2.setText(parseItem.getEat2());
        holder.eat3.setText(parseItem.getEat3());
        holder.eat4.setText(parseItem.getEat4());
        holder.eat5.setText(parseItem.getEat5());

    }

    @Override
    public int getItemCount() {
        return parseItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView day;
        TextView eat1;
        TextView eat2;
        TextView eat3;
        TextView eat4;
        TextView eat5;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            day = itemView.findViewById(R.id.day);
            eat1 = itemView.findViewById(R.id.eat1);
            eat2 = itemView.findViewById(R.id.eat2);
            eat3 = itemView.findViewById(R.id.eat3);
            eat4 = itemView.findViewById(R.id.eat4);
            eat5 = itemView.findViewById(R.id.eat5);
        }
    }
}
