//Mattis Uphoff
package de.host.connectionmanagerapp.adapter;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.host.connectionmanagerapp.R;
import de.host.connectionmanagerapp.activityFragments.JobDetailFragment;
import de.host.connectionmanagerapp.alarm.AlarmRepository;
import de.host.connectionmanagerapp.database.Job;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private List<Job> jobs;
    Application application;
    private final LayoutInflater mInflater;

    public JobAdapter(FragmentActivity application){
        mInflater = LayoutInflater.from(application);
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.job_view, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = jobs.get(position);
        holder.txtTitel.setText(job.getTitel());

    }

    @Override
    public int getItemCount() {
        if(jobs != null) {
            return jobs.size();
        }
        return 0;
    }

    public void setJobs(List<Job> jobs){
        this.jobs = jobs;
        notifyDataSetChanged();
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
            int id = job.getJob_id();
            Fragment fram = new JobDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", id);
            fram.setArguments(bundle);


            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fram).addToBackStack(null).commit();
        }
    }

}
