package com.qoobico.remindme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;

public class MapActivity extends AppCompatActivity {


    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private double Latitude;
    private double Longitude;
    private LocationManager locationManager;
    private Context cont;
    private Button buttonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        cont = this;
        buttonSearch = (Button)findViewById(R.id.buttonSearch);
        setUpMapIfNeeded();
        locationManager  = (LocationManager)getSystemService(LOCATION_SERVICE);
    }


    public void onAddPlace(View v){
        startActivity(new Intent(getApplicationContext(), PlacePickerActivity.class));
    }

    public void onSearch(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(cont);
        builder.setTitle(R.string.enter_lat_and_lon);

// Set up the input
        final EditText inputLatLon = new EditText(cont);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        inputLatLon.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        inputLatLon.setHint("Enter Latitude and Longitude Using after /  ....");
        builder.setView(inputLatLon);



// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strLatLon = null;
                String strLat = null;
                String strLon = null;

                strLatLon = inputLatLon.getEditableText().toString();
                if (!strLatLon.contains("/")) {
                    Toast.makeText(cont, "You have written wrong coordinates,repeat again...", Toast.LENGTH_LONG).show();
                } else {
                    String[] parts = strLatLon.split("/");
                    strLat = parts[0];
                    strLon = parts[1];


                    Double doubleLat = Double.parseDouble(strLat);
                    Double doubleLon = Double.parseDouble(strLon);

                    Toast.makeText(cont, "Entered Latitude = " + strLat + "Entered Longitude =" + strLon, Toast.LENGTH_LONG).show();

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(doubleLat, doubleLon), 8));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(doubleLat, doubleLon)).title("The Place you searching"));
                }


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100 * 10, 20, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100 * 10, 20, locationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private LocationListener locationListener = new LocationListener() {/*The Lisneter realizes interface LocationListener
                                                                        Providers turns ON and OFF in system settings.
                                                                        */



        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if(provider.equals(LocationManager.GPS_PROVIDER)){
                Toast.makeText(getApplicationContext(),"Status : "+String.valueOf(status),Toast.LENGTH_SHORT).show();
            }
            else if(provider.equals(LocationManager.NETWORK_PROVIDER)){
                Toast.makeText(getApplicationContext(),"Status : "+String.valueOf(status),Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();
            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }
    };
    private void showLocation(Location location){
        if(location==null)
            return;
        if(location.getProvider().equals(LocationManager.GPS_PROVIDER)){
            // Toast.makeText(getApplicationContext(),formatLocation(location),Toast.LENGTH_SHORT).show();
            Latitude = location.getLatitude();
            Longitude = location.getLongitude();
        }
        if(location.getProvider().equals(LocationManager.NETWORK_PROVIDER)){
            // Toast.makeText(getApplicationContext(),formatLocation(location),Toast.LENGTH_SHORT).show();
            Latitude = location.getLatitude();
            Longitude = location.getLongitude();
        }
    }

    private  String formatLocation(Location location){
        if(location==null)
            return  "";
        return String.format("Coordinates: lat = %1$.4f, lon = %2$.4f,time = %3$tF %3$tT",location.getLatitude(),location.getLongitude(),new Date(location.getTime()));
    }


    private void checkEnabled(){

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getApplicationContext(), "GPS is Enabled", Toast.LENGTH_SHORT).show();
        }
        else if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(getApplicationContext(), "GPS is Disabled", Toast.LENGTH_SHORT).show();
        }
        else if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            Toast.makeText(getApplicationContext(), "Network is Enabled", Toast.LENGTH_SHORT).show();
        }
        else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            Toast.makeText(getApplicationContext(), "Network is Disabled ", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This is where we can a   dd markers or lines, add listeners or move the camera. In this case, we
     * just add a marker in the place ,where i am.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */

    private void setUpMap() {

        mMap.setMyLocationEnabled(true); // Show indicator of my location

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.mapItem){
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            return true;
        }
        else if(id == R.id.satelliteItem){
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            return true;
        }
        else if(id == R.id.hybridItem){
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            return true;
        }
        else if(id == R.id.settings){
            Intent call_GPS_Wi_Fi_Setting_Intent = new Intent(Settings.ACTION_SETTINGS);
            startActivity(call_GPS_Wi_Fi_Setting_Intent);
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }
}
