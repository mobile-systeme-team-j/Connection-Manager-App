package de.host.connectionmanagerapp.viewmodels;

import de.host.connectionmanagerapp.database.Snippet;

public class SelectableSnippets extends Snippet {
    private boolean isSelected = false;


    public SelectableSnippets(Snippet item,boolean isSelected) {
        super(item.getTitel());
        this.isSelected = isSelected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
