package apt.tutorial;

import android.app.*;
import android.content.Intent;
import android.os.*;
import android.util.*;
import org.mcsoxford.rss.*;

public class FeedService extends IntentService {

	public static final String EXTRA_URL = "apt.tutorial.EXTRA_URL";
	public static final String EXTRA_MESSENGER = "apt.tutorial.EXTRA_MESSENGER";

	public FeedService() {
		super( "FeedService" );
	}

	@Override
	public void onHandleIntent( Intent i ) {
		RSSReader reader = new RSSReader();
		Messenger messenger = ( Messenger ) i.getExtras().get( EXTRA_MESSENGER );
		Message msg = Message.obtain();
		try {
			RSSFeed result = reader.load( i.getStringExtra( EXTRA_URL ) );
			msg.arg1 = Activity.RESULT_OK;
			msg.obj = result;
		} catch ( Exception e ) {
			Log.e( "LunchList", "Exception parsing feed", e );
			msg.arg1 = Activity.RESULT_CANCELED;
			msg.obj = e;
		}
	}

}
