package com.example.maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    GoogleMap a;
    LatLng n;
    ClusterManager<item> clusterManager;
    ArrayList<item> arrayList=new ArrayList<>();
    ArrayList<String> places=new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        //   MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        //  mapFragment.getMapAsync(this);
        AutoCompleteTextView autoCompleteTextView=findViewById(R.id.qwerty);
        ArrayAdapter p=new ArrayAdapter<String>(this,R.layout.placelayout,R.id.tvplace,places);
        autoCompleteTextView.setAdapter(p);
        autoCompleteTextView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView temp =(TextView) view.findViewById(R.id.tvplace);
                        String x=temp.getText().toString();
                        Toast.makeText(MainActivity.this,x,Toast.LENGTH_SHORT).show();


                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10); // Update location every second

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i("Location", location.toString());
        double lon = location.getLongitude();
        double lat = location.getLatitude();
        n=new LatLng(lat,lon);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        a = googleMap;
        clusterManager=new ClusterManager<item>(this,a);
        clusterManager.addItem(new item(28.749924, 77.127494,"dtu1"));
        clusterManager.addItem(new item(28.759925, 77.137495,"dtu2"));
        clusterManager.addItem(new item(28.769926, 77.147496,"dtu3"));
        clusterManager.addItem(new item(28.779927, 77.157497,"dtu4"));
        clusterManager.addItem(new item(28.789928, 77.167498,"dtu5"));
        clusterManager.setAnimation(true);
        a.setOnCameraIdleListener(clusterManager);
        a.setOnMarkerClickListener(clusterManager);
        Object[] avlMark= clusterManager.getAlgorithm().getItems().toArray();
        for (int i=0;i<avlMark.length;i++)
        {
            arrayList.add((item) avlMark[i]);
            places.add(arrayList.get(i).getTitle());
        }

        a.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(n).tilt(45).zoom(16).build()),2500,null);

    }

    public void a(View view) {
        a.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void b(View view) {
        a.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    public void c(View view) {
        a.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    @Override
    public void onConnectionSuspended(int i) { }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }

}
