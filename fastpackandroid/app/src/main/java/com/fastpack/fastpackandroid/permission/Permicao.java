package com.fastpack.fastpackandroid.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class Permicao {

    public static final int REQUEST_PERMISSION_FINE_LOCATION = 241;
    public static final int REQUEST_CAMERA_PERMISSION = 242;

    public static boolean isPermitedLocation(Activity c) {
        if (Build.VERSION.SDK_INT > 22) {
            ////////////////////Permissão para acessar localizacao
            if (ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(c, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //se por alguma vez ele negou a permissao ou colocou para nao ver mais (pelo menos assim esta na doc.)

                } else {
                    ActivityCompat.requestPermissions(c, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_FINE_LOCATION);
/*
                    if (ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return false;
                    }*/
                }
            } else return true;
        } else return true;

        return false;
    }

    public static boolean onRequestPermissionResult(int requestCode, String permissions[], int[] grantResults, int REQUEST_PERMISSIONS_CODE) {
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            return (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);
        }
        return false;
    }

    public static boolean onRequestPermissionResult(Activity ac,int requestCode) {
        if( REQUEST_CAMERA_PERMISSION  == requestCode ){
            return isPermitedCamera( ac );
        }
        throw new IllegalArgumentException("NOT IMPLEMENTED THIS REQUEST COD " + requestCode);
    }

    private static void callRequestPermission(Activity ac) {
        ActivityCompat.requestPermissions(ac, new String[]{Manifest.permission.CAMERA},
                REQUEST_CAMERA_PERMISSION);

    }

    public static boolean isPermitedCameraAndCallDialogIfNeeded(Activity ac) {
        if (Build.VERSION.SDK_INT <= 22) return true;

        if( !isPermitedCamera( ac ) ) {
            callRequestPermission( ac );
            return false;
        }

        return true;
    }
    private static boolean isPermitedCamera(Activity ac) {
        if (Build.VERSION.SDK_INT <= 22) return true;
        return (ContextCompat.checkSelfPermission(ac, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED);
    }


}

