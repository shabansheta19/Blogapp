package com.example.shaban.blogapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shaban.blogapp.R;
import com.example.shaban.blogapp.data.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shaban on 6/19/2018.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private List<Post> posts;
    private Context context;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.title.setText(post.getTitle());
        holder.desc.setText(post.getDescription());
        holder.date.setText(post.getTimeStamp());

        //load image using picasso
        Picasso.with(context).load(post.getImg()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        TextView desc;
        TextView date;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.postImageView);
            title = (TextView)itemView.findViewById(R.id.postTitleTextView);
            desc = (TextView)itemView.findViewById(R.id.postDescTextView);
            date = (TextView)itemView.findViewById(R.id.postDateTextView);
        }
    }
}
