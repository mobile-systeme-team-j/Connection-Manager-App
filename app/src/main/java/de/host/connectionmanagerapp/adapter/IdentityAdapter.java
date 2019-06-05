package de.host.connectionmanagerapp.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.database.Identity;

public class IdentityAdapter extends  RecyclerView.Adapter<IdentityAdapter.IdentityViewHolder> {

    private List<Identity> identities;
    private Application application;

    public IdentityAdapter(Application application, List<Identity> identities){

        this.application = application;
        this.identities = identities;
    }

    @NonNull
    @Override
    public IdentityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(application).inflate(R.layout.identity_view, parent, false);
        return new IdentityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IdentityViewHolder holder, int position) {
        Identity identity = identities.get(position);
        holder.textTitel.setText(identity.getTitel());
    }

    @Override
    public int getItemCount() {
        return identities.size();
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


            }
    }
}

