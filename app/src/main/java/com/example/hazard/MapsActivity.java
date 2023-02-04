package com.example.hazard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.hazard.databinding.ActivityMapsBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Vector;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    MarkerOptions marker;
    LatLng centerlocation;

    Vector<MarkerOptions> markerOptions;

    private String URL ="http://192.168.49.151/maklumat/all.php";
    RequestQueue requestQueue;
    Gson gson;
    Maklumat[] maklumats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

 gson = new GsonBuilder().create();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

       centerlocation = new LatLng(3.0,101);

       markerOptions = new Vector<>();


        //markerOptions.add(new MarkerOptions().title("Cawangan Alor Setar")
                //.position(new LatLng(6.12,100.37))
                //.snippet("Open during MCO : 8am - 6pm")

       //);
        //markerOptions.add(new MarkerOptions().title("Cawangan Bayan Lepas")
                //.position(new LatLng(5.29,100.259))
                //.snippet("Open during MCO : 8am - 6pm")
       // );
        //markerOptions.add(new MarkerOptions().title("Cawangan Ipoh")
                //.position(new LatLng(4.61,101.08))
                //.snippet("Open during MCO : 8am - 6pm")

       //);
        //markerOptions.add(new MarkerOptions().title("Cawangan Gua Musang")
                //.position(new LatLng(4.8721,108.96))
                //.snippet("Open during MCO : 8am - 6pm")
        //);
        //markerOptions.add(new MarkerOptions().title("Cawangan Arau")
                //.position(new LatLng(6.45,100.29))
                //.snippet("Open during MCO : 8am - 6pm")
        //);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        for (MarkerOptions mark : markerOptions) {
            mMap.addMarker(mark);
        }

        enableMyLocation();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerlocation,7));
        sendRequest();



    }


    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            String perms[] = {"android.permission.ACCESS_FINE_LOCATION"};
            // Permission to access the location is missing. Show rationale and request permission
            ActivityCompat.requestPermissions(this, perms,200);
        }
    }

    public void sendRequest(){

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL,onSuccess,onError);
        requestQueue.add(stringRequest);

    }
    public Response.Listener<String> onSuccess = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            maklumats = gson.fromJson(response, Maklumat[].class);

            Log.d("Maklumat", "Number of Maklumat Data Point : " +maklumats.length);

            if(maklumats.length <1 ){

                Toast.makeText(getApplicationContext(), "Problem retrieving JSON data", Toast.LENGTH_LONG).show();
                return;
            }

            for (Maklumat info: maklumats){

                Double lat = Double.parseDouble(info.lat);
                Double lng = Double.parseDouble(info.lng);
                String title = info.name;
                String snippet = info.date;



                MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng))
                        .title(title)
                        .snippet(snippet)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                mMap.addMarker(marker);

            }


        }

    };


    public Response.ErrorListener onError = new Response.ErrorListener(){


        @Override
        public void onErrorResponse(VolleyError error) {

            Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();

        }


    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.abouts:{
                Intent intent = new Intent(this, about.class);
                startActivity(intent);
            }break;

            case R.id.home:{
                Intent intent = new Intent(this, Homepage.class);
                startActivity(intent);
            }break;

            case R.id.mapst:{
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
            }break;
        }
        return super.onOptionsItemSelected(item);
    }


}