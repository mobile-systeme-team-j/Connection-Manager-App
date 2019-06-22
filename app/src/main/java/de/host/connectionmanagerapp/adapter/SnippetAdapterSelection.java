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
import de.host.connectionmanagerapp.database.Snippet;
import de.host.connectionmanagerapp.helper.Snippet_id_holder;

public class SnippetAdapterSelection extends RecyclerView.Adapter<SnippetAdapterSelection.SnippetViewHolder> {

    private List<Snippet> snippets;
    private Context application;
    private final LayoutInflater mInflater;

    public SnippetAdapterSelection(Context application){

        mInflater = LayoutInflater.from(application);
    }

    @NonNull
    @Override
    public SnippetAdapterSelection.SnippetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.identity_view, parent, false);
        return new SnippetAdapterSelection.SnippetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SnippetAdapterSelection.SnippetViewHolder holder, int position) {
        if (snippets != null) {
            Snippet current = snippets.get(position);
            holder.textTitel.setText(current.getTitel());
        } else {
            // Covers the case of data not being ready yet.
            holder.textTitel.setText("No Word");
        }
    }

    @Override
    public int getItemCount() {
        if (snippets != null) {
            return snippets.size();
        }
        return 0;
    }
    public void setSnippets(List<Snippet> snippets){
        this.snippets = snippets;
        notifyDataSetChanged();
    }

    public class SnippetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textTitel;

        public SnippetViewHolder(View itemView) {
            super(itemView);
            textTitel = itemView.findViewById(R.id.identity_view_titel);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick (View v){
            Snippet snippet = snippets.get(getAdapterPosition());
            String titel = snippet.getTitel();

            Snippet_id_holder holder = new Snippet_id_holder();
            holder.snippet_Titel = titel;

            Toast.makeText(itemView.getContext(),"Selected: " + snippet.getTitel(),Toast.LENGTH_SHORT).show();

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().popBackStack();

        }
    }
}

