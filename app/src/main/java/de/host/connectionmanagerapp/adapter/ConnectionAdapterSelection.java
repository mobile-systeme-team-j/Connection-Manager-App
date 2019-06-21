package de.host.connectionmanagerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.database.Connection;
import de.host.connectionmanagerapp.helper.Connection_id_holder;

public class ConnectionAdapterSelection extends RecyclerView.Adapter<ConnectionAdapterSelection.ConnectionViewHolder> {

    private List<Connection> connections;
    private Context application;
    private final LayoutInflater mInflater;

    public ConnectionAdapterSelection(Context application){

        mInflater = LayoutInflater.from(application);
    }

    @NonNull
    @Override
    public ConnectionAdapterSelection.ConnectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.identity_view, parent, false);
        return new ConnectionAdapterSelection.ConnectionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConnectionAdapterSelection.ConnectionViewHolder holder, int position) {
        if (connections != null) {
            Connection current = connections.get(position);
            holder.textTitel.setText(current.getTitel());
        } else {
            // Covers the case of data not being ready yet.
            holder.textTitel.setText("No Word");
        }
    }

    @Override
    public int getItemCount() {
        if (connections != null) {
            return connections.size();
        }
        return 0;
    }
    public void setConnections(List<Connection> connections){
        this.connections = connections;
        notifyDataSetChanged();
    }

    public class ConnectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textTitel;

        public ConnectionViewHolder(View itemView) {
            super(itemView);
            textTitel = itemView.findViewById(R.id.identity_view_titel);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick (View v){
            Connection connection = connections.get(getAdapterPosition());
            String id = connection.getTitel();

            Connection_id_holder holder = new Connection_id_holder();
            holder.id = id;

            Toast.makeText(itemView.getContext(),"Selected: " + connection.getTitel(),Toast.LENGTH_SHORT).show();

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().popBackStack();

        }
    }
}
