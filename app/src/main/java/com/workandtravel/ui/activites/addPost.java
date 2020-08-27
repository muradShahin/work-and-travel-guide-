package com.workandtravel.ui.activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.workandtravel.Classes.Posts;
import com.workandtravel.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class addPost extends AppCompatActivity {
    EditText name,country,state,content;
    ImageView image;
    Button publish,cancel;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference mStorageRef;
    private StorageTask storageTask;
    Uri imgUri;
    String downloadUri="https://firebasestorage.googleapis.com/v0/b/workandtravel-c7efb.appspot.com/o/default.png?alt=media&token=9c2447be-a012-41a8-8f9e-537c04d20b00";
    ProgressDialog pd;
    private RequestQueue requestQueue;
    private String url="https://fcm.googleapis.com/fcm/send";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        name=findViewById(R.id.name);
        country=findViewById(R.id.country);
        state=findViewById(R.id.state);
        content=findViewById(R.id.content);
        image=findViewById(R.id.image);
        publish=findViewById(R.id.publish);
        cancel=findViewById(R.id.cancel);

        mStorageRef= FirebaseStorage.getInstance().getReference("Images");
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("posts");
        requestQueue= Volley.newRequestQueue(this);

        FirebaseMessaging.getInstance().subscribeToTopic("posts");
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser();
            }
        });


        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag=true;
                if(TextUtils.isEmpty(name.getText().toString())){
                    name.setError("required");
                    flag=false;
                }
                if(TextUtils.isEmpty(country.getText().toString())){
                    country.setError("required");
                    flag=false;
                }
                if(TextUtils.isEmpty(state.getText().toString())){
                    state.setError("required");
                    flag=false;
                }
                if(TextUtils.isEmpty(content.getText().toString())){
                    content.setError("required");
                    flag=false;
                }
                if (flag) {

                    Posts post = new Posts();
                    post.setName(name.getText().toString());
                    post.setCountry(country.getText().toString());
                    post.setState(state.getText().toString());
                    post.setImage(downloadUri);
                    post.setContent(content.getText().toString());
                    addPost(post);
                }else{
                    Toast.makeText(addPost.this, "fill the required fields", Toast.LENGTH_SHORT).show();
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });




    }
    private void FileChooser(){
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,1);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== 1 && resultCode==RESULT_OK && data!=null){
            imgUri=data.getData();
            image.setImageURI(imgUri);
            FileUpload();


        }
    }
    private String getExtension(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    private void FileUpload() {
        pd=new ProgressDialog(this);
        pd.setMessage("Uploading..");
        pd.show();
        final int n=(int)(1+Math.random()*1000);
        StorageReference ref=mStorageRef.child(name.getText().toString()+country.getText().toString()+n+"."+getExtension(imgUri));


        storageTask= ref.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        getUri(n);
                        pd.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });


    }

    private void getUri(int n) {
        mStorageRef.child(name.getText().toString()+country.getText().toString()+n+"."+getExtension(imgUri)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                downloadUri=uri.toString();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(addPost.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void addPost(Posts post) {
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(addPost.this, "published successfully", Toast.LENGTH_SHORT).show();
                sendNotif();
                finish();
            }
        });
    }
    public void sendNotif(){
        JSONObject mainObj=new JSONObject();
        try {
            mainObj.put("to","/topics/"+"posts");
            JSONObject notifiObj=new JSONObject();
            notifiObj.put("tite","any title");
            notifiObj.put("body","a new post by"+name.getText().toString());
            mainObj.put("notification",notifiObj);

            JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url,
                    mainObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //codes here

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //error

                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header=new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AIzaSyBS947_Hq_a6dif6zooMCak9TXnRd9_uUs");
                    return header;
                }
            };
            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
