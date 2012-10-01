package apt.tutorial;

import android.app.ListActivity;
import android.content.*;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import java.util.*;

public class LunchList extends ListActivity {

	Cursor model = null;
	RestaurantAdapter adapter = null;

	EditText name = null;
	EditText address = null;
	EditText notes = null;
	RadioGroup types = null;
	Restaurant current = null;
	RestaurantHelper helper = null;

	public final static String ID_EXTRA = "apt.tutorial._ID";

	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.main );

		helper = new RestaurantHelper( this );
		model = helper.getAll();
		startManagingCursor( model );
		adapter = new RestaurantAdapter( model );
		setListAdapter( adapter );

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		helper.close();
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		new MenuInflater( this ).inflate( R.menu.option, menu );
		return super.onCreateOptionsMenu( menu );
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		if( item.getItemId() == R.id.add ) {
			startActivity( new Intent( LunchList.this, DetailForm.class ) );
			return true;
		}
		return super.onContextItemSelected( item );
	}

	@Override
	public void onListItemClick( ListView list, View view, int position, long id ) {
		Intent i = new Intent( LunchList.this, DetailForm.class );
		i.putExtra( ID_EXTRA, String.valueOf( id ) );
		startActivity( i );
	}

	class RestaurantAdapter extends CursorAdapter {
		RestaurantAdapter( Cursor c ) {
			super( LunchList.this, c );
		}

		@Override
		public void bindView( View row, Context ctxt, Cursor c ) {
			RestaurantHolder holder = ( RestaurantHolder ) row.getTag();
			holder.populateFrom( c, helper );
		}

		@Override
		public View newView( Context ctxt, Cursor c, ViewGroup parent ) {
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate( R.layout.row, parent, false );
			RestaurantHolder holder = new RestaurantHolder( row );
			row.setTag( holder );
			return row;
		}
	}

	static class RestaurantHolder {

		private TextView name = null;
		private TextView address = null;
		private ImageView icon = null;

		RestaurantHolder( View row ) {
			name = ( TextView ) row.findViewById( R.id.title );
			address = ( TextView ) row.findViewById( R.id.address );
			icon = ( ImageView ) row.findViewById( R.id.icon );
		}

		void populateFrom( Cursor c, RestaurantHelper helper ) {
			name.setText( helper.getName( c ) );
			address.setText( helper.getAddress( c ) );

			if( helper.getType( c ).equals( "sit_down" ) ) {
				icon.setImageResource( R.drawable.ball_red );
			} else if( helper.getType( c ).equals( "take_out" ) ) {
				icon.setImageResource( R.drawable.ball_yellow );
			} else {
				icon.setImageResource( R.drawable.ball_green );
			}
		}
	}

}
