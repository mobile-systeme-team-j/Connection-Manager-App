package de.host.connectionmanagerapp.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.CheckedTextView;

import androidx.recyclerview.widget.RecyclerView;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.viewmodels.SelectableSnippets;

class MultiViewHolderSnippet extends RecyclerView.ViewHolder {


    CheckedTextView textView;
    SelectableSnippets mItem;



    public MultiViewHolderSnippet(View view) {
        super(view);
        textView = (CheckedTextView) view.findViewById(R.id.snippet_view_titel_checked);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (mItem.isSelected()) {
                    setChecked(false);
                } else {
                    setChecked(true);
                }

            }
        });
    }

    public void setChecked(boolean value) {
        if (value) {
            textView.setBackgroundColor(Color.LTGRAY);
        } else {
            textView.setBackground(null);
        }
        mItem.setSelected(value);
        textView.setChecked(value);
    }


}
