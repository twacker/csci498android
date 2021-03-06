package apt.tutorial;

import android.os.Bundle;
import com.google.android.maps.*;
import android.graphics.drawable.*;
import android.widget.*;

public class RestaurantMap extends MapActivity {

	private MapView map = null;

	public static final String EXTRA_LATITUDE = "apt.tutorial.EXTRA_LATITUDE";
	public static final String EXTRA_LONGITUDE = "apt.tutorial.EXTRA_LONGITUDE";
	public static final String EXTRA_NAME = "apt.tutorial.EXTRA_NAME";

	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.map );
		double lat = getIntent().getDoubleExtra( EXTRA_LATITUDE, 39.75 );
		double lon = getIntent().getDoubleExtra( EXTRA_LONGITUDE, 105.22 );

		map = ( MapView ) findViewById( R.id.map );
		map.getController().setZoom( 17 );
		GeoPoint status = new GeoPoint( ( int ) ( lat * 1000000 ), ( int ) ( lon * 1000000 ) );
		map.getController().setCenter( status );
		map.setBuiltInZoomControls( true );

		Drawable marker = getResources().getDrawable( R.drawable.marker );
		marker.setBounds( 0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight() );
		map.getOverlays().add( new RestaurantOverlay( marker, status, getIntent().getStringExtra( EXTRA_NAME ) ) );

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	private class RestaurantOverlay extends ItemizedOverlay< OverlayItem > {
		private OverlayItem item = null;

		public RestaurantOverlay( Drawable marker, GeoPoint point, String name ) {
			super( marker );
			boundCenterBottom( marker );
			item = new OverlayItem( point, name, name );
			populate();
		}

		@Override
		protected OverlayItem createItem( int i ) {
			return item;
		}

		@Override
		public int size() {
			return 1;
		}

		@Override
		protected boolean onTap( int i ) {
			Toast.makeText( RestaurantMap.this, item.getSnippet(), Toast.LENGTH_SHORT ).show();
			return true;
		}
	}

}
