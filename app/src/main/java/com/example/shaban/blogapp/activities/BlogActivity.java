package com.example.shaban.blogapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shaban.blogapp.MainActivity;
import com.example.shaban.blogapp.R;
import com.example.shaban.blogapp.adapters.PostsAdapter;
import com.example.shaban.blogapp.data.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class BlogActivity extends AppCompatActivity {

    private List<Post> posts;
    private RecyclerView recyclerView;
    private PostsAdapter postsAdapter;
    private FirebaseDatabase blogDatabase;
    private DatabaseReference postsReference;
    private FirebaseAuth blogAuth;
    private FirebaseUser curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        blogDatabase = FirebaseDatabase.getInstance();
        blogAuth = FirebaseAuth.getInstance();
        curUser = blogAuth.getCurrentUser();

        postsReference = blogDatabase.getReference().child("posts");
        postsReference.keepSynced(true);

        posts = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.postsRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add_post :
                addPost();
                break;
            case R.id.action_sign_out :
                signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        if(curUser != null && blogAuth != null) {
            blogAuth.signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void addPost() {
        if(curUser != null && blogAuth != null) {
            startActivity(new Intent(this,AddPostActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.blog_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        postsReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Post post = dataSnapshot.getValue(Post.class);
                posts.add(post);
                postsAdapter = new PostsAdapter(BlogActivity.this,posts);
                recyclerView.setAdapter(postsAdapter);
                postsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
