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
import de.host.connectionmanagerapp.database.Snippet;

public class SnippetAdapter extends RecyclerView.Adapter<SnippetAdapter.SnippetViewHolder>{

    List<Snippet> snippets;
    Application application;

    public SnippetAdapter(Application application, List<Snippet> snippets){
        this.snippets = snippets;
        this.application = application;
    }
    @NonNull
    @Override
    public SnippetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(application).inflate(R.layout.snippet_view, parent, false);
        return new SnippetAdapter.SnippetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SnippetViewHolder holder, int position) {
        Snippet snippet = snippets.get(position);
        holder.txtTitel.setText(snippet.getTitel());
    }

    @Override
    public int getItemCount() {
        return snippets.size();
    }

    public class SnippetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtTitel;

        public SnippetViewHolder(View itemView){
            super(itemView);

            txtTitel = itemView.findViewById(R.id.snippet_view_titel);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Snippet snippet = snippets.get(getAdapterPosition());
        }

    }
}
