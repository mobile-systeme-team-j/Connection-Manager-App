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
import de.host.connectionmanagerapp.database.Snippet;

public class MultiAdapterSnippet extends RecyclerView.Adapter<MultiAdapterSnippet.MultiViewHolder> {

    private List<Snippet> snippets;
    private Context application;

    public MultiAdapterSnippet(Context application) {
            this.application = application;
            }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(application).inflate(R.layout.snippet_view, parent, false);
            return new MultiAdapterSnippet.MultiViewHolder(view);
            }


        @Override
    public void onBindViewHolder(MultiAdapterSnippet.MultiViewHolder holder, int position) {
            Snippet snippet = snippets.get(position);
            holder.textTitel.setText(snippet.getTitel());
            }

    @Override
    public int getItemCount() {
            if (snippets != null) {
            return snippets.size();
            }
            return 0;
            }

    public void setSnippets(List<Snippet> snippets) {
            this.snippets = snippets;
            notifyDataSetChanged();
            }

class MultiViewHolder extends RecyclerView.ViewHolder {
    TextView textTitel;
    CardView cardView;

    MultiViewHolder(View itemView) {
        super(itemView);
        textTitel = itemView.findViewById(R.id.snippet_view_titel);
        cardView = itemView.findViewById(R.id.conCard);
    }

    void bind(final Snippet snippet) {


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snippet.setChecked(!snippet.isChecked());
                cardView.setBackgroundColor(snippet.isChecked() ? Color.WHITE : Color.BLUE);
            }
        });
    }
}
    public List<Snippet> Selected(){
        List<Snippet> selected =new ArrayList<Snippet>();

        for(Snippet snippet : snippets){
            if(snippet.isChecked()){
                selected.add(snippet);
            }
        }

        return selected;
    }
}
