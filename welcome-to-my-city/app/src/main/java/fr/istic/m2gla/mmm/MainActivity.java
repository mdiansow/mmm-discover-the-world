package fr.istic.m2gla.mmm;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Messenger;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mapquest.android.maps.DefaultItemizedOverlay;
import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.Overlay;
import com.mapquest.android.maps.OverlayItem;
import com.mapquest.android.maps.PolygonOverlay;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends MapActivity {

    protected MapView map;
    protected LinearLayout actions;
    protected ScrollView scroller;
    protected List<GeoPoint> polyData = new ArrayList();

    /** Messenger for communicating with service. */
    Messenger mService = null;
    /** Flag indicating whether we have called bind on the service. */
    boolean mIsBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        handleClickOnMap();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    protected void init() {
        this.setupMapView(new GeoPoint(48.12,-1.67), 12);
        this.actions=(LinearLayout)findViewById(R.id.actions);
        this.scroller=(ScrollView)findViewById(R.id.scroller);
    }

    /**
     * This will set up a basic MapQuest map with zoom controls
     */
    protected void setupMapView(GeoPoint pt, int zoom) {
        this.map = (MapView) findViewById(R.id.map);

        // set the zoom level
        map.getController().setZoom(zoom);

        // set the center point
        map.getController().setCenter(pt);

        // enable the zoom controls
        map.setBuiltInZoomControls(true);

    }

    /**
     * handle click on map
     * put marker where it is clicked
     * draw polyline when more than one marker
     */
    protected void handleClickOnMap(){

        final Paint paint = initLine();
        final DefaultItemizedOverlay markers = initMarkers();

        //final List<Overlay> mapOverlays = map.getOverlays();

        final Overlay overlay = new Overlay() {
            @Override
            public boolean onTap(GeoPoint p, MapView mapView) {
                OverlayItem marker = new OverlayItem(p,"","");
                markers.addItem(marker);
                polyData.add(marker.getPoint());

                if (polyData.size()==1) {
                    PolygonOverlay polygonOverlay = new PolygonOverlay(paint);
                    polygonOverlay.setData(polyData);
                    //map.getOverlays().add(polygonOverlay);
                }

                return super.onTap(p, mapView);
            }
        };

        map.getOverlays().add(markers);
        map.getOverlays().add(overlay);
    }

    protected Paint initLine(){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        return paint;
    }

    protected DefaultItemizedOverlay initMarkers(){
        Drawable icon = getResources().getDrawable(R.drawable.location_marker);
        final DefaultItemizedOverlay markers = new DefaultItemizedOverlay(icon);
        return markers;
    }



    private void addEventText(String text){
        TextView tv = new TextView(MainActivity.this);
        tv.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setText(text);
        actions.addView(tv);
        scroller.fullScroll(ScrollView.FOCUS_DOWN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scroller.fullScroll(ScrollView.FOCUS_DOWN);
            }
        },50);

    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    /**
     * Utility method for getting the text of an EditText, if no text was entered the hint is returned
     * @param editText
     * @return
     */
    public String getText(EditText editText){
        String s = editText.getText().toString();
        if("".equals(s)) s=editText.getHint().toString();
        return s;
    }

    /**
     * Hides the softkeyboard
     * @param v
     */
    public void hideSoftKeyboard(View v){
        //hides soft keyboard
        final InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }



}
