package com.example.admin.jerne.UserFragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.admin.jerne.CustomAutoComplete.PlaceJSONParser;
import com.example.admin.jerne.GPSService.GPSService;
import com.example.admin.jerne.GoogleMapDirection.DirectionFinder;
import com.example.admin.jerne.GoogleMapDirection.DirectionFinderListener;
import com.example.admin.jerne.GoogleMapDirection.Route;
import com.example.admin.jerne.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 17/06/2016.
 */
public class UserHome extends Fragment implements OnMapReadyCallback, DirectionFinderListener {

    Button btn_user_direction;
    ImageView img_walk_mode, img_bicycle_mode, img_car_mode, img_bus_mode, img_train_mode;
    EditText edit_source_add, edit_drop_add, edit_time;


    private int mYear, mMonth, mDay, mHour, mMinute;

    private GoogleMap mMap;
    private Button btnFindPath;
    private EditText etOrigin;
    private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;

    //private DirectionFinderListener interface2;

    public static String travel_mode;

    AutoCompleteTextView atvPlaces, btvPlaces;
    PlacesTask placesTask;
    ParserTask parserTask;

    public static String current_address;
    public static double current_lat, current_long;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        v.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);


        atvPlaces = (AutoCompleteTextView) v.findViewById(R.id.edit_source_add);
        atvPlaces.setThreshold(1);

        btvPlaces = (AutoCompleteTextView) v.findViewById(R.id.edit_destination_add);
        btvPlaces.setThreshold(1);


        if (isNetworkAvailable()) {

            /*********************** For Current Location ************************************/

            String address = "";
            GPSService mGPSService = new GPSService(getActivity());
            mGPSService.getLocation();

            if (mGPSService.isLocationAvailable == false) {

                // Here you can ask the user to try again, using return; for that
                Toast.makeText(getActivity(), "Your location is not available, please try again.", Toast.LENGTH_SHORT).show();


                // Or you can continue without getting the location, remove the return; above and uncomment the line given below
                // address = "Location not available";
            } else {

                // Getting location co-ordinates
                double latitude = mGPSService.getLatitude();
                double longitude = mGPSService.getLongitude();
                //Toast.makeText(getActivity(), "Latitude:" + latitude + " | Longitude: " + longitude, Toast.LENGTH_LONG).show();

                address = mGPSService.getLocationAddress();

                //tvLocation.setText("Latitude: " + latitude + " \nLongitude: " + longitude);
                //tvAddress.setText("Address: " + address);

                current_lat = latitude;
                current_long = longitude;
                current_address = address;

                if(current_address.equals("")){

                }else{
                    atvPlaces.setText(current_address);
                }

            }

            //Toast.makeText(getActivity(), "Your address is: " + address, Toast.LENGTH_SHORT).show();

            // make sure you close the gps after using it. Save user's battery power
            //mGPSService.closeGPS();
            /*********************************************************************************/

        } else {
            Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT).show();
        }

        atvPlaces.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                placesTask = new PlacesTask();
                placesTask.execute(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });


        btvPlaces.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                placesTask = new PlacesTask();
                placesTask.execute(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });





        edit_time = (EditText) v.findViewById(R.id.edit_time);
        img_walk_mode = (ImageView) v.findViewById(R.id.img_walk_mode);
        img_bicycle_mode = (ImageView) v.findViewById(R.id.img_cycle_mode);
        img_car_mode = (ImageView) v.findViewById(R.id.img_car_mode);
        img_bus_mode = (ImageView) v.findViewById(R.id.img_bus_mode);
        img_train_mode = (ImageView) v.findViewById(R.id.img_train_mode);
        //edit_source_add = (EditText) v.findViewById(R.id.edit_source_add);
        //edit_drop_add = (EditText) v.findViewById(R.id.edit_destination_add);

        edit_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        //edit_user_booking_time.setText(hourOfDay + ":" + minute);
                        edit_time.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        img_walk_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable()) {
                    travel_mode = "walking";
                    sendRequest();
                } else {
                    Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_LONG);
                }


            }
        });

        img_bicycle_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable()) {
                    travel_mode = "bicycling";
                    sendRequest();
                } else {
                    Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT);
                }
            }
        });

        img_car_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable()) {
                    travel_mode = "driving";
                    sendRequest();
                } else {
                    Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT);
                }
            }
        });

        img_bus_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable()) {
                    travel_mode = "transit&transit_mode=bus";
                    sendRequest();
                } else {
                    Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT);
                }
            }
        });

        img_train_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable()) {
                    travel_mode = "transit&transit_mode=train|tram|subway|rail";
                    sendRequest();
                } else {
                    Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT);
                }
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.user_map);
        mapFragment.getMapAsync(this);
    }


    private void sendRequest() {
        String origin = atvPlaces.getText().toString();
        String destination = btvPlaces.getText().toString();
        if (origin.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter source address!", Toast.LENGTH_SHORT).show();
            return;
        } else if (destination.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        } else {

            try {
                hideKeyboard();
                //Toast.makeText(getActivity(), origin, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), destination, Toast.LENGTH_SHORT).show();
                new DirectionFinder(this, origin, destination, travel_mode).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng hcmus;

        if (current_lat == 0 || current_long == 0) {
            hcmus = new LatLng(40.730610, -73.935242);
        } else {
            hcmus = new LatLng(current_lat, current_long);
        }

        //mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 14));
        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .title(current_address)
                .position(hcmus)));

        

    }

    @Override
    public void onDirectionFinderStart() {
        /*
        progressDialog = ProgressDialog.show(getActivity(), "Please wait.",
                "Finding direction..!", true);*/

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        progressDialog.show();

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }

    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 14));
            //((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            //((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.jerne_source_marker))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.jerne_destination_marker))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.RED).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }


    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            //Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches all places from GooglePlaces AutoComplete Web Service
    private class PlacesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... place) {
            // For storing data from web service
            String data = "";

            // Obtain browser key from https://code.google.com/apis/console
            String key = "key=AIzaSyBFjK8UInAeNGfhx8attCH8UNY6xzNjuwU";

            String input="";

            try {
                input = "input=" + URLEncoder.encode(place[0], "utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }


            // place type to be searched
            String types = "types=geocode";

            // Sensor enabled
            String sensor = "sensor=false";

            // Building the parameters to the web service
            String parameters = input+"&"+types+"&"+sensor+"&"+key;

            // Output format
            String output = "json";

            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"+output+"?"+parameters;

            try{
                // Fetching the data from web service in background
                data = downloadUrl(url);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Creating ParserTask
            parserTask = new ParserTask();

            // Starting Parsing the JSON string returned by Web Service
            parserTask.execute(result);
        }
    }


    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>> {

        JSONObject jObject;

        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;

            PlaceJSONParser placeJsonParser = new PlaceJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);

                // Getting the parsed data as a List construct
                places = placeJsonParser.parse(jObject);

            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return places;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {

            String[] from = new String[] { "description"};
            int[] to = new int[] { android.R.id.text1 };

            // Creating a SimpleAdapter for the AutoCompleteTextView
            SimpleAdapter adapter = new SimpleAdapter(getActivity(), result, android.R.layout.simple_list_item_1, from, to);

            // Setting the adapter
            atvPlaces.setAdapter(adapter);
            btvPlaces.setAdapter(adapter);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /** Hide keyboard function **/
    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }





}
