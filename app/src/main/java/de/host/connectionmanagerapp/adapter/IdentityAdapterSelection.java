package de.host.connectionmanagerapp.adapter;


import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.database.Identity;
import de.host.connectionmanagerapp.helper.Identity_id_holder;

public class IdentityAdapterSelection extends RecyclerView.Adapter<IdentityAdapterSelection.IdentityViewHolder> {

    private List<Identity> identities;
    private Context application;
    private final LayoutInflater mInflater;

    public IdentityAdapterSelection(Context application){

        mInflater = LayoutInflater.from(application);
    }

    @NonNull
    @Override
    public IdentityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.identity_view, parent, false);
        return new IdentityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IdentityViewHolder holder, int position) {
        if (identities != null) {
            Identity current = identities.get(position);
            holder.textTitel.setText(current.getTitel());
        } else {
            // Covers the case of data not being ready yet.
            holder.textTitel.setText("No Word");
        }
    }

    @Override
    public int getItemCount() {
    if (identities != null) {
        return identities.size();
    }
        return 0;
    }
    public void setIdentities(List<Identity> identities){
        this.identities = identities;
        notifyDataSetChanged();
    }

    public class IdentityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textTitel;

        public IdentityViewHolder(View itemView) {
            super(itemView);
            textTitel = itemView.findViewById(R.id.identity_view_titel);
            itemView.setOnClickListener(this);
        }
            @Override
            public void onClick (View v){
                Identity identity = identities.get(getAdapterPosition());
                long id = identity.getIdentiy_id();

                Identity_id_holder holder = new Identity_id_holder();
                holder.id = id;

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().popBackStack();

            }
    }
}
