package com.example.kas_project.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextClassification;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kas_project.R;
import com.example.kas_project.database.ProfileKeysDatabaseGetter;
import com.example.kas_project.models.ProfileKey;

import java.util.List;

class PeopleDecryptionAdapter extends RecyclerView.Adapter<PeopleDecryptionAdapter.ViewHolder> {

    List<ProfileKey> profileKeysPeople;

    public PeopleDecryptionAdapter(List<ProfileKey> profileKeysPeople) {
        this.profileKeysPeople = profileKeysPeople;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.people_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.email.setText(profileKeysPeople.get(position).getEmail());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileKeysDatabaseGetter db = new ProfileKeysDatabaseGetter();
                db.delete(profileKeysPeople.get(position));
                profileKeysPeople.clear();
                profileKeysPeople = db.getAll();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return profileKeysPeople.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView email;
        public ImageButton imageButton;
        public TextView noDataTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.text01);
            imageButton = itemView.findViewById(R.id.imageButtonDelete);
            noDataTextView = itemView.findViewById(R.id.noDataTextView);
        }
    }

}

