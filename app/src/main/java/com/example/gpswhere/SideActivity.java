package com.example.gpswhere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.GeoObjectTapEvent;
import com.yandex.mapkit.layers.GeoObjectTapListener;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

public class SideActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth auth;
    FirebaseUser user;
    private MapKit mapKit;
    MapView mapView;

    private static final String API_KEY = "86b62060-f681-42c8-bbf8-7010ad40d4a6";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey(API_KEY);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_side);

        mapKit = MapKitFactory.getInstance();
        mapView = findViewById(R.id.mapview);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        auth = FirebaseAuth.getInstance();


        mapView.getMap().move(
                new CameraPosition(new Point(55.664827, 37.597756), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 2),
                null);

        mapView.getMap().addTapListener(new GeoObjectTapListener() {
            @Override
            public boolean onObjectTap(@NonNull GeoObjectTapEvent geoObjectTapEvent) {
                Log.d("Main",geoObjectTapEvent.getGeoObject().getName());
                return false;
            }
        });

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

                }
                else if (id == R.id.nav_joinedCircle)
                {

                }
                else if (id == R.id.nav_shareLoc)
                {
                    mapKit.createLocationManager().requestSingleUpdate(new LocationListener() {
                        @Override
                        public void onLocationUpdated(@NonNull Location location) {
                            mapView.getMap().move(
                                    new CameraPosition(location.getPosition(), 18.0f, 0.0f, 0.0f),
                                    new Animation(Animation.Type.SMOOTH, 2),
                                    null);
                            mapView.getMap().getMapObjects().addPlacemark(location.getPosition());

                        }

                        @Override
                        public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {
                        }
                    });

                }
                else if (id == R.id.nav_myCircle)
                {

                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }
    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}