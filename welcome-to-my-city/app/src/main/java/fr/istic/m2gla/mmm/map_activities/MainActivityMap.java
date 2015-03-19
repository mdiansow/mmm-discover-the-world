package fr.istic.m2gla.mmm.map_activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.istic.m2gla.mmm.R;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * MainActivity.
 * ²
 *
 */
public class MainActivityMap extends Activity implements LocationListener{

  private LocationManager locationManager;
  private GoogleMap gMap;
  private Marker marker;


  /**
  * {@inheritDoc}
  */

  protected void onCreate(final Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_map);

      gMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
      //marker = gMap.addMarker(new MarkerOptions().title("Vous êtes ici").position(new LatLng(0, 0)));


 /*     // latitude and longitude
      double latitude = 48.1119800 ;
      double longitude = -1.6742900;*/



  }

    // methode afficher positions
    public void createMarketOfPerson(double latitude, double longitude, Bitmap image) {


        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(50, 30, conf);
        Canvas canvas = new Canvas(bmp);

        Paint color = new Paint();
        color.setTextSize(6);
        color.setColor(Color.WHITE);


        canvas.drawBitmap(Bitmap.createScaledBitmap(image, 50, 30, true), 0, 0, color);

        gMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                .anchor(0.5f, 1)
                .title("Vous êtes ici")).showInfoWindow();
    }

  /**
  * {@inheritDoc}
  */

  public void onResume() {
      super.onResume();

      //Obtention de la référence du service
      locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

      //Si le GPS est disponible, on s'y abonne
      if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
          abonnementGPS();
      }
  }

  /**
  * {@inheritDoc}
  */

  public void onPause() {
      super.onPause();

      //On appelle la méthode pour se désabonner
      desabonnementGPS();
  }

  /**
  * Méthode permettant de s'abonner à la localisation par GPS.
  */
  public void abonnementGPS() {
      //On s'abonne
      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
  }

  /**
  * Méthode permettant de se désabonner de la localisation par GPS.
  */
  public void desabonnementGPS() {
      //Si le GPS est disponible, on s'y abonne
      locationManager.removeUpdates(this);
  }

  /**
  * {@inheritDoc}
  */

  public void onLocationChanged(final Location location) {
      //On affiche dans un Toat la nouvelle Localisation
      final StringBuilder msg = new StringBuilder("lat : ");
      msg.append(location.getLatitude());
      msg.append( "; lng : ");
      msg.append(location.getLongitude());

      Toast.makeText(this, msg.toString(), Toast.LENGTH_SHORT).show();

      //Mise à jour des coordonnées
      final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
      Bitmap bitmapImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.person), 100, 75, true);
      createMarketOfPerson(location.getLatitude(), location.getLongitude(), bitmapImg);
      gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
      //marker.setPosition(latLng);
  }

  /**
  * {@inheritDoc}
  */

  public void onProviderDisabled(final String provider) {
      //Si le GPS est désactivé on se désabonne
      if("gps".equals(provider)) {
          desabonnementGPS();
      }        
  }

  /**
  * {@inheritDoc}
  */

  public void onProviderEnabled(final String provider) {
      //Si le GPS est activé on s'abonne
      if("gps".equals(provider)) {
          abonnementGPS();
      }
  }

  /**
  * {@inheritDoc}
  */

  public void onStatusChanged(final String provider, final int status, final Bundle extras) { }


    private class HttpRequestTask extends AsyncTask<Void, Void, Contact[]> {

        @Override
        protected Contact[] doInBackground(Void... params) {
            try {

                final String url = "http://rest-service.guides.spring.io/greeting";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<Contact[]> listCoordibates = restTemplate.getForEntity(url, Contact[].class);
                Contact[] coordinates = listCoordibates.getBody();
                return coordinates;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Contact[] coordonnees) {
            for (Contact userCoordinates : coordonnees){
                Bitmap bitmapImg = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.person), 100, 75, true);
                createMarketOfPerson(userCoordinates.getLatitude(), userCoordinates.getLongitude(), bitmapImg);
            }
        }

    }


}

