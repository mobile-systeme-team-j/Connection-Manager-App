package de.host.connectionmanagerapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.database.Connection;

public class MultiAdapterConnection extends RecyclerView.Adapter<MultiAdapterConnection.MultiViewHolder> {

    private List<Connection> connections;
    private Context application;

    public MultiAdapterConnection(Context application) {
        this.application = application;
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(application).inflate(R.layout.connection_view, parent, false);
        return new MultiAdapterConnection.MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MultiAdapterConnection.MultiViewHolder holder, int position) {
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

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
        notifyDataSetChanged();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {
        TextView textTitel;
        CardView cardView;

        MultiViewHolder(View itemView) {
            super(itemView);
            textTitel = itemView.findViewById(R.id.connection_view_titel);
            cardView = itemView.findViewById(R.id.conCard);
        }

        void bind(final Connection connection) {


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    connection.setChecked(!connection.isChecked());
                    cardView.setBackgroundColor(connection.isChecked() ? Color.WHITE : Color.BLUE);
                }
            });
        }
    }
    public List<Connection> Selected(){
        List<Connection> selected =new ArrayList<Connection>();

        for(Connection connection : connections){
            if(connection.isChecked()){
                selected.add(connection);
            }
        }

        return selected;
    }

}

