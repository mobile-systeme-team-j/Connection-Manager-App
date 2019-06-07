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
import de.host.connectionmanagerapp.database.Job;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private List<Job> jobs;
    Application application;

    public JobAdapter(Application application, List<Job> jobs){
        this.jobs =jobs;
        this.application = application;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(application).inflate(R.layout.job_view, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = jobs.get(position);
        holder.txtTitel.setText(job.getTitel());

    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public class JobViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTitel;

        public JobViewHolder(View itemView){
            super(itemView);

            txtTitel = itemView.findViewById(R.id.job_view_titel);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Job job = jobs.get(getAdapterPosition());
        }
    }

}
