package com.example.shaban.blogapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shaban.blogapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AddPostActivity extends AppCompatActivity {

    private ImageView postImageView;
    private EditText postTitleEditText;
    private EditText postDescEditText;
    private ProgressDialog progressDialog;
    private FirebaseDatabase blogDatabase;
    private DatabaseReference postsReference;
    private StorageReference blogStorage;
    private FirebaseUser curUser;
    private FirebaseAuth blogAuth;

    private Uri imageUri;
    private static final int GALLERY_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        postImageView = (ImageView) findViewById(R.id.addPostImageView);
        postTitleEditText = (EditText)findViewById(R.id.postTitleEditText);
        postDescEditText = (EditText)findViewById(R.id.postDescEditText);

        progressDialog = new ProgressDialog(this);

        blogDatabase = FirebaseDatabase.getInstance();
        blogAuth = FirebaseAuth.getInstance();
        curUser = blogAuth.getCurrentUser();
        postsReference = blogDatabase.getReference().child("posts");
        blogStorage = FirebaseStorage.getInstance().getReference();

        postImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && requestCode == RESULT_OK) {
            imageUri = data.getData();
            postImageView.setImageURI(imageUri);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_ok_post) {
            String title = postTitleEditText.getText().toString();
            String description = postDescEditText.getText().toString();
            if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description) && imageUri != null) {
                startPosting(title,description);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void startPosting(final String title, final String description) {
        progressDialog.setMessage("posting to blog....");
        progressDialog.show();

        StorageReference imageRef = blogStorage.child("posts_images").child(imageUri.getLastPathSegment());
        imageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        addPost(title,description,downloadUrl);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(AddPostActivity.this,"posting failed, try again",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });

    }

    private void addPost(String title, String description ,Uri downloadUrl) {
        DatabaseReference newPostRef = postsReference.push();

        Map<String,String> post = new HashMap<>();
        post.put("title",title);
        post.put("description",description);
        post.put("image_uri",downloadUrl.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(System.currentTimeMillis());
        post.put("timeStamp",date);
        post.put("user_id",curUser.getUid());
        newPostRef.setValue(post);
        progressDialog.dismiss();
        startActivity(new Intent(AddPostActivity.this,BlogActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
