package com.tmobile.thememanager.receiver;

import com.tmobile.thememanager.ThemeManager;
import com.tmobile.thememanager.provider.ThemeItem;
import com.tmobile.thememanager.utils.ThemeUtilities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class ChangeThemeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ThemeManager.DEBUG) {
            Log.d(ThemeManager.TAG, "SetThemeReceiver: intent=" + intent);
        }
        ThemeItem item = ThemeItem.getInstance(context, intent.getData());
        if (item == null) {
            Log.e(ThemeManager.TAG, "Could not retrieve theme item for uri=" + intent.getData());
            return;
        }
        try {
            Uri wallpaperUri = (Uri)intent.getParcelableExtra(ThemeManager.EXTRA_WALLPAPER_URI);
            Uri ringtoneUri = (Uri)intent.getParcelableExtra(ThemeManager.EXTRA_RINGTONE_URI);
            Uri notificationRingtoneUri =
                (Uri)intent.getParcelableExtra(ThemeManager.EXTRA_NOTIFICATION_RINGTONE_URI);
            ThemeUtilities.applyTheme(context, item, wallpaperUri, ringtoneUri,
                    notificationRingtoneUri);
        } finally {
            item.close();
        }

        setResult(Activity.RESULT_OK, null, null);
        abortBroadcast();
    }
}