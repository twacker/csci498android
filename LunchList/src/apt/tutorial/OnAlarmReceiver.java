package apt.tutorial;

import android.app.*;
import android.content.*;
import android.preference.PreferenceManager;
import android.media.AudioManager;
import android.net.Uri;

public class OnAlarmReceiver extends BroadcastReceiver {

	private static final int NOTIFY_ME_ID = 1633;
	private String alert1 = "It's time for lunch!";
	private String alert2 = "It's time for lunch! Aren't you hungry?";

	@Override
	public void onReceive( Context ctxt, Intent intent ) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( ctxt );
		boolean useNotification = prefs.getBoolean( "use_notification", true );
		if ( useNotification ) {
			NotificationManager mgr = ( NotificationManager ) ctxt.getSystemService( Context.NOTIFICATION_SERVICE );
			Notification note = new Notification( R.drawable.stat_notify_chat, alert1, System.currentTimeMillis() );
			PendingIntent i = PendingIntent.getActivity( ctxt, 0, new Intent( ctxt, AlarmActivity.class ), 0 );
			note.setLatestEventInfo( ctxt, "LunchList", alert2, i );
			note.flags |= Notification.FLAG_AUTO_CANCEL;
			String sound = prefs.getString( "alarm_ringtone", null );
			if ( sound != null ) {
				note.sound = Uri.parse( sound );
				note.audioStreamType = AudioManager.STREAM_ALARM;
			}
			mgr.notify( NOTIFY_ME_ID, note );
		} else {
			Intent i = new Intent( ctxt, AlarmActivity.class );
			i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
			ctxt.startActivity( i );
		}
	}

}
