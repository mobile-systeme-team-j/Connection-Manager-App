//Mattis Uphoff nach https://www.simplifiedcoding.net/android-room-database-example/
package de.host.connectionmanagerapp.adapter;

import android.app.Application;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.database.Connection;

public class ConnectionAdapter extends  RecyclerView.Adapter<ConnectionAdapter.ConnectionViewHolder> {

    private List<Connection> connections;
    private Application application;

    public ConnectionAdapter(Application application, List<Connection> connections) {
        this.connections = connections;
        this.application = application;
    }

    @NonNull
    @Override
    public ConnectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(application).inflate(R.layout.connection_view, parent, false);
        return new ConnectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConnectionViewHolder holder, int position) {
        Connection connection = connections.get(position);
        holder.textTitel.setText(connection.getTitel());
    }

    @Override
    public int getItemCount() {
        return connections.size();
    }

    class ConnectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textTitel;

        public ConnectionViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textTitel = itemView.findViewById(R.id.connection_view_titel);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Connection connection = connections.get(getAdapterPosition());

            //Intent intent = new Intent(application, .class);
            //intent.putExtra("connection", connection);

        }
    }
}