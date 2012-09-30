package apt.tutorial;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import java.util.*;

public class LunchList extends TabActivity {

	List< Restaurant > model = new ArrayList< Restaurant >();
	RestaurantAdapter adapter = null;

	EditText name = null;
	EditText address = null;
	EditText notes = null;
	RadioGroup types = null;
	Restaurant current = null;
	RestaurantHelper helper = null;

	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.main );

		Button save = ( Button ) findViewById( R.id.save );

		save.setOnClickListener( onSave );

		ListView list = ( ListView ) findViewById( R.id.restaurants );

		adapter = new RestaurantAdapter();
		list.setAdapter( adapter );

		// add the tabs to the view
		TabHost.TabSpec spec = getTabHost().newTabSpec( "tag1" );
		spec.setContent( R.id.restaurants );
		spec.setIndicator( "List", getResources().getDrawable( R.drawable.list ) );
		getTabHost().addTab( spec );

		spec = getTabHost().newTabSpec( "tag2" );
		spec.setContent( R.id.details );
		spec.setIndicator( "Details", getResources().getDrawable( R.drawable.restaurant ) );
		getTabHost().addTab( spec );

		getTabHost().setCurrentTab( 0 );

		list.setOnItemClickListener( onListClick );

		name = ( EditText ) findViewById( R.id.name );
		address = ( EditText ) findViewById( R.id.addr );
		notes = ( EditText ) findViewById( R.id.notes );
		types = ( RadioGroup ) findViewById( R.id.types );

		helper = new RestaurantHelper( this );

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		helper.close();
	}

	private View.OnClickListener onSave = new View.OnClickListener() {

		public void onClick( View v ) {
			String type=null;
			
			switch( types.getCheckedRadioButtonId() ) {
			case R.id.sit_down :
				current.setType( "sit_down" );
				break;

			case R.id.take_out :
				current.setType( "take_out" );
				break;

			case R.id.delivery :
				current.setType( "delivery" );
				break;
			}

			helper.insert( name.getText().toString(), address.getText().toString(), type, notes.getText().toString() );
		}
	};

	private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {

		public void onItemClick( AdapterView< ? > parent, View view, int position, long id ) {
			current = model.get( position );
			name.setText( current.getName() );
			address.setText( current.getAddress() );
			notes.setText( current.getNotes() );
			if( current.getType().equals( "sit down" ) ) {
				types.check( R.id.sit_down );
			} else if( current.getType().equals( "take_out" ) ) {
				types.check( R.id.take_out );
			} else {
				types.check( R.id.delivery );
			}
			getTabHost().setCurrentTab( 1 );
		}
	};

	class RestaurantAdapter extends ArrayAdapter< Restaurant > {

		RestaurantAdapter() {
			super( LunchList.this, R.layout.row, model );
		}

		public View getView( int position, View convertView, ViewGroup parent ) {
			View row = convertView;
			RestaurantHolder holder = null;

			if( row == null ) {
				LayoutInflater inflater = getLayoutInflater();

				row = inflater.inflate( R.layout.row, parent, false );
				holder = new RestaurantHolder( row );
				row.setTag( holder );
			} else {
				holder = ( RestaurantHolder ) row.getTag();
			}

			holder.populateFrom( model.get( position ) );

			return( row );
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

		void populateFrom( Restaurant r ) {
			name.setText( r.getName() );
			address.setText( r.getAddress() );

			if( r.getType().equals( "sit_down" ) ) {
				icon.setImageResource( R.drawable.ball_red );
			} else if( r.getType().equals( "take_out" ) ) {
				icon.setImageResource( R.drawable.ball_yellow );
			} else {
				icon.setImageResource( R.drawable.ball_green );
			}
		}
	}

}
