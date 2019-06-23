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
import de.host.connectionmanagerapp.database.Job;
import de.host.connectionmanagerapp.helper.Job_id_holder;

public class JobAdapterSelection extends RecyclerView.Adapter<JobAdapterSelection.JobViewHolder> {

    private List<Job> jobs;
    private Context application;
    private final LayoutInflater mInflater;

    public JobAdapterSelection(Context application){

        mInflater = LayoutInflater.from(application);
    }

    @NonNull
    @Override
    public JobAdapterSelection.JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.identity_view, parent, false);
        return new JobAdapterSelection.JobViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JobAdapterSelection.JobViewHolder holder, int position) {
        if (jobs != null) {
            Job current = jobs.get(position);
            holder.textTitel.setText(current.getTitel());
        } else {
            // Covers the case of data not being ready yet.
            holder.textTitel.setText("No Word");
        }
    }

    @Override
    public int getItemCount() {
        if (jobs != null) {
            return jobs.size();
        }
        return 0;
    }
    public void setJobs(List<Job> jobs){
        this.jobs = jobs;
        notifyDataSetChanged();
    }

    public class JobViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textTitel;

        public JobViewHolder(View itemView) {
            super(itemView);
            textTitel = itemView.findViewById(R.id.job_view_titel);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick (View v){
            Job job = jobs.get(getAdapterPosition());
            String id = job.getTitel();

            Job_id_holder holder = new Job_id_holder();
            holder.id = id;

            Toast.makeText(itemView.getContext(),"Selected: " + job.getTitel(),Toast.LENGTH_SHORT).show();

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().popBackStack();

        }
    }
}
