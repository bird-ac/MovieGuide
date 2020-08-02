package com.esoxjem.movieguide.safevault;

public class SafeVault {
    static {
        System.loadLibrary("SafeVault");
    }

    private static SafeVault myInstance = null;
    public native String getSslSha();
    public native String getApiKey();
    public native String[] getSignatureKeys();

    public static SafeVault getInstance() {
        if(myInstance == null) {
            myInstance = new SafeVault();
        }
        return myInstance;
    }
}
