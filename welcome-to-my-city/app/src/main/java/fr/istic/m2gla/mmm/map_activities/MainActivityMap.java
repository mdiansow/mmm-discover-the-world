package fr.istic.m2gla.mmm.map_activities;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.ListActivity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import fr.istic.m2gla.mmm.R;


/**
 * MainActivity.
 * @author Ludovic
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
      marker = gMap.addMarker(new MarkerOptions().title("Vous êtes ici").position(new LatLng(0, 0)));
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
      gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
      marker.setPosition(latLng);
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
}
