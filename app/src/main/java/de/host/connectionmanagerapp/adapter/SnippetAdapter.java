package de.host.connectionmanagerapp.adapter;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.activityFragments.SnippetDetailFragment;
import de.host.connectionmanagerapp.database.Snippet;

public class SnippetAdapter extends RecyclerView.Adapter<SnippetAdapter.SnippetViewHolder>{

    List<Snippet> snippets;
    private final LayoutInflater mInflater;

    public SnippetAdapter(Context application){
        mInflater = LayoutInflater.from(application);
    }
    @NonNull
    @Override
    public SnippetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.snippet_view, parent, false);
        return new SnippetAdapter.SnippetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SnippetViewHolder holder, int position) {
        Snippet snippet = snippets.get(position);
        holder.txtTitel.setText(snippet.getTitel());
    }

    @Override
    public int getItemCount() {
        if (snippets != null) {
            return snippets.size();
        }
        else {
            return 0;
        }

    }
    public void setSnippets(List<Snippet> snippets){
        this.snippets = snippets;
        notifyDataSetChanged();
    }

    public class SnippetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtTitel;

        public SnippetViewHolder(View itemView) {
            super(itemView);

            txtTitel = itemView.findViewById(R.id.snippet_view_titel);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {


            Snippet snippet = snippets.get(getAdapterPosition());
            long id = snippet.getSnippet_id();
            Fragment fram = new SnippetDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("id", id);
            fram.setArguments(bundle);


            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fram).addToBackStack(null).commit();

        }
    }
}
