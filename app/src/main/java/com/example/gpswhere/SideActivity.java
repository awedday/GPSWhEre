package com.example.gpswhere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.GeoObjectTapEvent;
import com.yandex.mapkit.layers.GeoObjectTapListener;
import com.yandex.mapkit.location.FilteringMode;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationManager;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

import de.hdodenhof.circleimageview.CircleImageView;

public class SideActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth auth;
    FirebaseUser user;

    private MapKit mapKit;
    MapView mapView;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final double DESIRED_ACCURACY = 0;
    private static final long MINIMAL_TIME = 1000;
    private static final double MINIMAL_DISTANCE = 1;
    private static final boolean USE_IN_BACKGROUND = false;
    public static final int COMFORTABLE_ZOOM_LEVEL = 18;


    private LocationManager locationManager;
    private LocationListener myLocationListener;
    private Point myLocation;

    DatabaseReference databaseReference;

    String current_user_name;
    String current_user_code;
    String current_user_imageUrl;

    TextView textViewName, textViewCode;
    CircleImageView imageViewImage;


    private static final String MAPKIT_API_KEY = "86b62060-f681-42c8-bbf8-7010ad40d4a6";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_side);

        mapKit = MapKitFactory.getInstance();
        mapView = findViewById(R.id.mapview);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        View header = navigationView.getHeaderView(0);
        textViewName = header.findViewById(R.id.textViewName);
        textViewCode = header.findViewById(R.id.textViewCode);
        imageViewImage = header.findViewById(R.id.imageViewPhoto);
        locationManager = MapKitFactory.getInstance().createLocationManager();
        myLocationListener = new LocationListener() {
            @Override
            public void onLocationUpdated(Location location) {
                if (myLocation == null) {
                    moveCamera(location.getPosition(), COMFORTABLE_ZOOM_LEVEL);
                }
                myLocation = location.getPosition(); //this user point
                Log.w(TAG, "my location - " + myLocation.getLatitude() + "," + myLocation.getLongitude());
                FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lng").setValue(myLocation.getLongitude());
                FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lat").setValue(myLocation.getLatitude());
                subscribeToLocationUpdate();
            }

            @Override
            public void onLocationStatusUpdated(LocationStatus locationStatus) {
                if (locationStatus == LocationStatus.NOT_AVAILABLE) {
                    System.out.println("sdncvoadsjv");
                }
            }
        };

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.nav_signOut){
                    FirebaseUser user = auth.getCurrentUser();
                    if(user !=null) {
                        auth.signOut();

                        Intent intent = new Intent(SideActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else if (id == R.id.nav_inviteMembers)
                {

                }
                else if (id == R.id.nav_joinCircle)
                {
                    Intent myIntent = new Intent(SideActivity.this,JoinCircleActivity.class);
                    startActivity(myIntent);
                    finish();
                }
                else if (id == R.id.nav_joinedCircle)
                {

                }
                else if (id == R.id.nav_shareLoc)
                {
//                    mapKit.createLocationManager().requestSingleUpdate(new LocationListener() {
//                        @Override
//                        public void onLocationUpdated(@NonNull Location location) {
//                            mapView.getMap().move(
//                                    new CameraPosition(location.getPosition(), 18.0f, 0.0f, 0.0f),
//                                    new Animation(Animation.Type.SMOOTH, 2),
//                                    null);
//                            mapView.getMap().getMapObjects().addPlacemark(location.getPosition());
//
//                        }
//
//                        @Override
//                        public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {
//                        }
//                    });

                }
                else if (id == R.id.nav_myCircle)
                {
                    Intent myIntent = new Intent(SideActivity.this,MyCircleActivity.class);
                    startActivity(myIntent);
                    finish();
                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                current_user_name = snapshot.child(user.getUid()).child("name").getValue(String.class);
                current_user_code = snapshot.child(user.getUid()).child("code").getValue(String.class);
                current_user_imageUrl = snapshot.child(user.getUid()).child("imageUrl").getValue(String.class);


                textViewName.setText(current_user_name);
                textViewCode.setText(current_user_code);
                Picasso.get().load(current_user_imageUrl).into(imageViewImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        locationManager.unsubscribe(myLocationListener);
        StorageReference storRef = FirebaseStorage.getInstance().getReference("profile")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(String.valueOf(R.drawable.red_offline));
        super.onStop();
    }

    public void onFabCurrentLocationClick(View view) {
        if (myLocation == null) {
            return;
        }

        moveCamera(myLocation, COMFORTABLE_ZOOM_LEVEL);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
//        Online();
        subscribeToLocationUpdate();
    }

    private void subscribeToLocationUpdate() {
        if (locationManager != null && myLocationListener != null) {
            locationManager.subscribeForLocationUpdates(DESIRED_ACCURACY, MINIMAL_TIME, MINIMAL_DISTANCE, USE_IN_BACKGROUND, FilteringMode.OFF, myLocationListener);
        }
    }

    private void moveCamera(Point point, float zoom) {
        mapView.getMap().move(
                new CameraPosition(point, zoom, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 1),
                null);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    public void Online(){
//        StorageReference storRef = FirebaseStorage.getInstance().getReference("profile")
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .child(String.valueOf(R.drawable.green_online));
//        storRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Uri> task) {
//                        storeData(task.getResult());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e("err2", e.getMessage());
//                    }
//                });
//    }
//
//    private void storeData(Uri uri){
//        User newUser = new User();
//        newUser.setUserId(uri.toString());
//        FirebaseDatabase.getInstance().getReference("Users")
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
//                .setValue(newUser);
//    }
}