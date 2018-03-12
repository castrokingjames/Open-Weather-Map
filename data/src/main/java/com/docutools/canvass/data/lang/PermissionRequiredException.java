package com.docutools.canvass.data.lang;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import java.util.List;

public class PermissionRequiredException
        extends Exception {

    private String permissions[];

    public PermissionRequiredException(List<String> permissions){
        this(permissions.toArray(new String[0]));
    }

    public PermissionRequiredException(String... permissions) {
        this("Permission Required", permissions);
    }

    public PermissionRequiredException(String message, String[] permissions) {
        super(message);
        this.permissions = permissions;
    }

    public void startResolutionForResult(Activity activity, int code) {
        ActivityCompat.requestPermissions(activity, permissions, code);
    }

    public void startResolutionForResult(Fragment fragment, int code) {
        fragment.requestPermissions(permissions, code);
    }
}