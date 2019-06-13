//Mattis Uphoff nach https://www.simplifiedcoding.net/android-room-database-example/
package de.host.connectionmanagerapp.adapter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.host.connectionmanagerapp.MainActivity;
import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.activityFragments.ConnectionDetailFragment;
import de.host.connectionmanagerapp.database.Connection;

public class ConnectionAdapter extends  RecyclerView.Adapter<ConnectionAdapter.ConnectionViewHolder> {

    private List<Connection> connections;
    private Context application;

    public ConnectionAdapter(Context application) {
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
        if (connections != null) {
            return connections.size();
        }
        return 0;
    }

    public void setConnections(List<Connection>connections){
        this.connections = connections;
        notifyDataSetChanged();
    }

    class ConnectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView textTitel;

        public ConnectionViewHolder(View itemView) {
            super(itemView);
            textTitel = itemView.findViewById(R.id.connection_view_titel);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Connection connection = connections.get(getAdapterPosition());
            long id = connection.getConnection_id();
            Fragment fram = new ConnectionDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("class", id);
            fram.setArguments(bundle);

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, fram).addToBackStack(null).commit();


        }
        @Override
        public boolean onLongClick(View v) {
            PopupMenu popupMenu = new PopupMenu(application, v);
            popupMenu.getMenuInflater().inflate(R.menu.menu_longclick, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.duplicate:
                            return true;
                        case R.id.placeholder:
                            return true;

                        default:
                            return true;
                    }
                }
            });
            popupMenu.show();
            return true;
        }
    }
}