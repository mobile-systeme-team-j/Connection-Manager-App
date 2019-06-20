package de.host.connectionmanagerapp.activityFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.database.Snippet;
import de.host.connectionmanagerapp.viewmodels.ConnectionViewModel;

public class SnippetDetailFragment extends Fragment implements View.OnClickListener{

    private EditText editTextSnippetname;
    private EditText editTextSnippetContent;
    private Bundle arguments;
    private long id;
    private Snippet snippet;
    private ConnectionViewModel connectionViewModel;
    private FloatingActionButton delete;
    private FloatingActionButton save;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_snippet_detail, container, false);

        connectionViewModel= ViewModelProviders.of(getActivity()).get(ConnectionViewModel.class);
        editTextSnippetContent = view.findViewById(R.id.editTextSnippetContent);
        editTextSnippetname = view.findViewById(R.id.editTextConnectionName);


        delete = view.findViewById(R.id.fabDelete);
        save = view.findViewById(R.id.fabSave);
        delete.setOnClickListener(this);
        save.setOnClickListener(this);
        editTextSnippetname = view.findViewById(R.id.editTextSnippetname);


        arguments = getArguments();
        if (arguments != null) {
            id = arguments.getLong("id");
            snippet = connectionViewModel.getSnippets(id);

            getSnippet();
        }


        return view;
    }




    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fabDelete:
                if(arguments !=null) {
                    Toast.makeText(getContext(),"Snippet deleted",Toast.LENGTH_SHORT).show();

                }
                else{

                }
                break;
            case R.id.fabSave:
                if(arguments !=null) {
                    try{
                        connectionViewModel.updateSnippets(setSnippet());
                        Toast.makeText(getContext(),"Snippet saved", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Toast.makeText(getContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
                else{


                    snippet = new Snippet();
                    connectionViewModel.insertSnippet(setSnippet());

                    Toast.makeText(getContext(),"Snippet saved", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                }

                break;

        }

    }
        public void getSnippet () {
            editTextSnippetname.setText(snippet.getTitel());
            editTextSnippetContent.setText(snippet.getText());

        }
        public Snippet setSnippet(){

            snippet.setText(String.valueOf(editTextSnippetContent.getText()));
            snippet.setTitel(String.valueOf(editTextSnippetname.getText()));
            return snippet;
        }

}