package apt.tutorial;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;

class RestaurantHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "lunchlist.db";
	private static final int SCHEMA_VERSION = 4;

	private String dropTable = "DROP TABLE IF EXISTS " + DATABASE_NAME;
	private String createTable = "CREATE TABLE restaurants (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, type TEXT, notes TEXT, feed TEXT, lat REAL, lon REAL, phone TEXT);";
	private String getById = "SELECT _id, name, address, type, notes, feed, lat, lon, phone FROM restaurants WHERE _id=?";
	private String getAll = "SELECT _id, name, address, type, notes, lat, lon, phone FROM restaurants ORDER BY ";
	private String alterFeed = "ALTER TABLE restaurants ADD COLUMN feed TEXT";
	private String alterLat = "ALTER TABLE restaurants ADD COLUMN lat REAL";
	private String alterLon = "ALTER TABLE restaurants ADD COLUMN lon REAL";
	private String alterPhone = "ALTER TABLE restaurants ADD COLUMN phone TEXT";

	public RestaurantHelper( Context context ) {
		super( context, DATABASE_NAME, null, SCHEMA_VERSION );
	}

	@Override
	public void onCreate( SQLiteDatabase db ) {
		db.execSQL( dropTable );
		db.execSQL( createTable );
	}

	@Override
	public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
		switch ( oldVersion ) {
		case 0 :
		case 1 :
		case 2 :
			db.execSQL( alterFeed );
		case 3 :
			db.execSQL( alterLat );
			db.execSQL( alterLon );
		case 4 :
			db.execSQL( alterPhone );
		default :
			// do nothing
			break;
		}
	}

	public void insert( String name, String address, String type, String notes, String feed, String phone ) {
		ContentValues cv = new ContentValues();

		cv.put( "name", name );
		cv.put( "address", address );
		cv.put( "type", type );
		cv.put( "notes", notes );
		cv.put( "feed", feed );
		cv.put( "phone", phone );

		getWritableDatabase().insert( "restaurants", "name", cv );
	}

	public Cursor getAll( String orderBy ) {
		return getReadableDatabase().rawQuery( getAll + orderBy, null );
	}

	public Cursor getById( String id ) {
		String[] args = { id };
		return getReadableDatabase().rawQuery( getById, args );
	}

	public String getName( Cursor c ) {
		return c.getString( 1 );
	}

	public String getAddress( Cursor c ) {
		return c.getString( 2 );
	}

	public String getType( Cursor c ) {
		return c.getString( 3 );
	}

	public String getNotes( Cursor c ) {
		return c.getString( 4 );
	}

	public String getFeed( Cursor c ) {
		return c.getString( 5 );
	}

	public double getLatitude( Cursor c ) {
		return c.getDouble( 6 );
	}

	public double getLongitude( Cursor c ) {
		return c.getDouble( 7 );
	}

	public String getPhone( Cursor c ) {
		return c.getString( 8 );
	}

	public void updateLocation( String id, double lat, double lon ) {
		ContentValues cv = new ContentValues();
		String[] args = { id };
		cv.put( "lat", lat );
		cv.put( "lon", lon );
		getWritableDatabase().update( "restaurants", cv, "_ID=?", args );
	}

	public void update( String id, String name, String address, String type, String notes, String feed, String phone ) {
		ContentValues cv = new ContentValues();
		String[] args = { id };

		cv.put( "name", name );
		cv.put( "address", address );
		cv.put( "type", type );
		cv.put( "notes", notes );
		cv.put( "feed", feed );
		cv.put( "phone", phone );

		getWritableDatabase().update( "restaurants", cv, "_ID=?", args );
	}

}
