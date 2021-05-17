package com.example.sapimarket.ui.addItem;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sapimarket.R;
import com.example.sapimarket.ui.constants.Constants;
import com.example.sapimarket.ui.home.HomeFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class AddItemFragment extends Fragment {

    private static int RESULT_LOAD_IMG = 1;

    EditText name, price, category, description;
    AppCompatButton addItemButton;
    TextView addPictureButton;
    ImageView itemImage;
    private Uri imageUri;

    DatabaseReference reference;

    StorageReference storageReference;

    private StorageTask uploadTask;

    public AddItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);
        Log.d("SAPISAPI","view Created");
        name = (EditText) view.findViewById(R.id.item_name);
        price = (EditText) view.findViewById(R.id.item_price);
        category = (EditText) view.findViewById(R.id.item_category);
        description = (EditText) view.findViewById(R.id.item_description);
        addItemButton = (AppCompatButton) view.findViewById(R.id.add_item_button);
        addPictureButton = (TextView) view.findViewById(R.id.add_picture_button);
        itemImage = (ImageView) view.findViewById(R.id.item_image);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        addPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_name = name.getText().toString();
                String txt_price = price.getText().toString();
                String txt_category = category.getText().toString();
                String txt_description = description.getText().toString();

                if (TextUtils.isEmpty(txt_category) || TextUtils.isEmpty(txt_name) || TextUtils.isEmpty(txt_price) || TextUtils.isEmpty(txt_description) || imageUri == null) {
                    Toast.makeText(getContext(), "All fields, and the image are required", Toast.LENGTH_SHORT).show();
                } else if (description.length() < 25) {
                    Toast.makeText(getContext(), "Description must be at least 25 characters long", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("SAPISAPI","Meghivas");
                    uploadItem(txt_name,txt_price,txt_category,txt_description);
                }
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {

                imageUri = data.getData();
                Glide.with(Objects.requireNonNull(getContext()))
                        .load(imageUri)
                        .into(itemImage);

            } else {
                Toast.makeText(getContext(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadItem(String txt_name,String txt_price,String txt_category,String txt_description) {
        Log.d("SAPISAPI","uploadItem");
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading");
        Log.d("SAPISAPI","before progress dialog");
        progressDialog.show();
        Log.d("SAPISAPI","after progress dialog");
        if (imageUri != null) {
            Log.d("SAPISAPI","IMAGE URI");
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));
            Log.d("SAPISAPI","Ide belep");
            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        reference = FirebaseDatabase.getInstance().getReference(Constants.ITEMS).child(new Date().toString());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL", mUri);
                        map.put("name",txt_name);
                        map.put("price",txt_price);
                        map.put("category",txt_category);
                        map.put("description",txt_description);
                        map.put("seller",Constants.getCurrentChild());
                        reference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.popBackStackImmediate();
                                    fragmentManager.beginTransaction()
                                            .addToBackStack(null)
                                            .replace(R.id.fragment_container, new HomeFragment())
                                            .commit();
                                }
                            }
                        });;
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("SAPISAPI",e.getMessage());
                    progressDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }
}