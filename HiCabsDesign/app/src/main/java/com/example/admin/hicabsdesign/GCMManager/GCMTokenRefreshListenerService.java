package com.example.admin.hicabsdesign.GCMManager;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by admin on 16/05/2016.
 */
public class GCMTokenRefreshListenerService extends InstanceIDListenerService {
    /**
     * When token refresh, start service to get new token
     */

    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, GCMTokenRefreshListenerService.class);
        startService(intent);
    }
}
