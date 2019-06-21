package de.host.connectionmanagerapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.database.Snippet;
import de.host.connectionmanagerapp.viewmodels.SelectableSnippets;

public class MultiAdapterSnippet extends RecyclerView.Adapter<MultiViewHolderSnippet> {

    private List<SelectableSnippets> mValues;
    private List<Snippet> snippets;
    private Context application;

    public MultiAdapterSnippet(Context application) {
            this.application = application;

            }

    @NonNull
    @Override
    public MultiViewHolderSnippet onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(application).inflate(R.layout.snippet_multible_view, parent, false);
            return new MultiViewHolderSnippet(view);
            }


        @Override
    public void onBindViewHolder(MultiViewHolderSnippet holder, int position) {
            Snippet snippet = snippets.get(position);
            holder.textView.setText(snippet.getTitel());
            SelectableSnippets selectableItem = mValues.get(position);

            TypedValue value = new TypedValue();
            holder.textView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorMultiple, value, true);
            int checkMarkDrawableResId = value.resourceId;
            holder.textView.setCheckMarkDrawable(checkMarkDrawableResId);
            holder.mItem = selectableItem;
            holder.setChecked(holder.mItem.isSelected());
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
        mValues = new ArrayList<>();
        for (Snippet snippet: snippets) {
            mValues.add(new SelectableSnippets(snippet, false));
        }
            notifyDataSetChanged();
            }
    public List<Snippet> getSelectedItems() {

        List<Snippet> selectedItems = new ArrayList<>();
        for (SelectableSnippets selectableSnippet : mValues) {
            if (selectableSnippet.isSelected()) {
                selectedItems.add(selectableSnippet);
            }
        }
        return selectedItems;
    }
}