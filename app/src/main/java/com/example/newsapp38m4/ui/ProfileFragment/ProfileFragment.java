package com.example.newsapp38m4.ui.ProfileFragment;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.newsapp38m4.App;
import com.example.newsapp38m4.Prefs;
import com.example.newsapp38m4.R;
import com.example.newsapp38m4.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private static Uri avatarBuffer;
    private FirebaseAuth mAuth;

    private Prefs prefs;

    private String nameBuffer;
    private String surnameBuffer;
    static final int REQUEST_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        prefs = new Prefs(getContext());
        mAuth = FirebaseAuth.getInstance();
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        binding.ivAvatar.setImageURI(avatarBuffer);
        initProfile();

        binding.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        //

        binding.etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    prefs.saveProfileName(binding.etName.getText().toString());
                    Log.d("f_global", "profile: successfully saved name");
                }
            }
        });

        binding.etSurname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    prefs.saveProfileSurname(binding.etSurname.getText().toString());
                    Log.d("f_global", "profile: successfully saved surname");
                }
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Are you sure you want to log out?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        signOut();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
        });
    }

    private void initProfile() {
        Log.d("f_global", "profile: profile initializing");
        binding.etName.setText(prefs.loadProfileName());
        binding.etSurname.setText(prefs.loadProfileSurname());

        try {
            binding.ivAvatar.setImageURI(Uri.parse(prefs.loadProfileAvatar()));
        } catch (NullPointerException e) {
            Log.e("f_global", "profile: failed to load profile avatar!");
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap bitmap = null;

        switch(requestCode) {
            case REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    avatarBuffer = selectedImage;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.ivAvatar.setImageBitmap(bitmap);
                    prefs.saveProfileAvatar(selectedImage);
                }
        }
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.loginFragment);
        Log.d("f_global", "Logged out from account");
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        try {
            Glide.with(this).load(currentUser.getPhotoUrl()).into(binding.ivFirebaseAvatar);
        } catch (NullPointerException e) {
            Log.e("f_global", "Profile: failed to load avatar from Firebase!");
        }
        binding.ivFirebaseUsername.setText(currentUser.getDisplayName());
    }
}