package com.srinug.testapp.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.srinug.testapp.R;
import com.srinug.testapp.api.model.GitCommitsData;

import java.util.ArrayList;
import java.util.List;

public class CommitsAdapter extends RecyclerView.Adapter<CommitsAdapter.ViewHolder> {
    private List<GitCommitsData> gitCommitsData;

    public CommitsAdapter(List<GitCommitsData> gitCommitsData) {
        this.gitCommitsData = gitCommitsData;
    }

    @Override
    public CommitsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommitsAdapter.ViewHolder viewHolder, int i) {

        if (gitCommitsData != null) {
            viewHolder.commitAuthorName.setText(gitCommitsData.get(i).getCommit().getAuthor().getName());
            viewHolder.commitHash.setText("Commit: "+gitCommitsData.get(i).getSha());
            viewHolder.commitMessage.setText("Message:\n "+gitCommitsData.get(i).getCommit().getMessage().trim());
        }

    }

    @Override
    public int getItemCount() {
        return gitCommitsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView commitAuthorName, commitHash, commitMessage;
        public ViewHolder(View view) {
            super(view);

            commitAuthorName = (TextView)view.findViewById(R.id.commit_author_name);
            commitHash = (TextView)view.findViewById(R.id.commit_hash);
            commitMessage = (TextView)view.findViewById(R.id.commit_message);

        }
    }

}
