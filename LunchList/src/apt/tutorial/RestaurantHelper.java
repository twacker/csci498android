package apt.tutorial;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;

class RestaurantHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "@string/dbname";
	private static final int SCHEMA_VERSION = 1;

	public RestaurantHelper( Context context ) {
		super( context, DATABASE_NAME, null, SCHEMA_VERSION );
	}

	@Override
	public void onCreate( SQLiteDatabase db ) {
		db.execSQL( "@string/createtable" );
	}

	@Override
	public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
		// no-op until we have another version of our schema
	}

	public void insert( String name, String address, String type, String notes ) {
		ContentValues cv = new ContentValues();

		cv.put( "@string/namecol", name );
		cv.put( "@string/addrcol", address );
		cv.put( "@string/typecol", type );
		cv.put( "@string/notecol", notes );

		getWritableDatabase().insert( "@string/db", "name", cv );
	}

	public Cursor getAll(String orderBy) {
		// DO NOT end the query string with ; (looked up on docs)
		return getReadableDatabase().rawQuery( "@string/getall" + orderBy, null );
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

	public Cursor getById( String id ) {
		String[] args = { id };
		return getReadableDatabase().rawQuery( "@string/getbyid", args );
	}

	public void update( String id, String name, String address, String type, String notes ) {
		ContentValues cv = new ContentValues();
		String[] args = { id };

		cv.put( "@string/namecol", name );
		cv.put( "@string/addrcol", address );
		cv.put( "@string/typecol", type );
		cv.put( "@string/notecol", notes );

		getWritableDatabase().update( "@string/db", cv, "@string/condition", args );

	}

}
