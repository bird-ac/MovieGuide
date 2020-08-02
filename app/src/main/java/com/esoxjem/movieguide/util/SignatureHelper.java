package com.esoxjem.movieguide.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.util.Log;
import com.esoxjem.movieguide.safevault.SafeVault;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;

public class SignatureHelper {
    public static int CODE_SIGNATURE_MATCHING_NOT_VALID = -1;
    public static int CODE_SIGNATURE_MATCHING_VALID = 1;

    private Signature[] getSignatures(Context context, String applicationId) throws PackageManager.NameNotFoundException {
        int flag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P ?
                PackageManager.GET_SIGNING_CERTIFICATES : PackageManager.GET_SIGNATURES;

        final PackageInfo info = context.getPackageManager().getPackageInfo(applicationId, flag);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            if(info.signingInfo.hasMultipleSigners()){
                return info.signingInfo.getApkContentsSigners();
            } else {
                return info.signingInfo.getSigningCertificateHistory();
            }
        } else {
            return info.signatures;
        }
    }

    private HashSet<String> getAllSignatureString(Context context) {
        HashSet<String> keySignatures = new HashSet<>();
        try {
            String pkgName = context.getPackageName();
            Signature[] signatures = getSignatures(context, pkgName);
            for (Signature signature : signatures) {
                final MessageDigest md = MessageDigest.getInstance("sha1");
                md.update(signature.toByteArray());
                final byte[] digest = md.digest();
                final StringBuilder toRet = new StringBuilder();
                for (byte value : digest) {
                    int b = value & 0xff;
                    String hex = Integer.toHexString(b);
                    if (hex.length() == 1) toRet.append("0");
                    toRet.append(hex);
                }
                String keySigned = toRet.toString();
                keySignatures.add(keySigned);
            }
            return keySignatures;
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return keySignatures;
    }

    public int isSignatureMatched(Context context) {
        String[] keys = SafeVault.getInstance().getSignatureKeys();
        HashSet<String> keySet = new HashSet<>(Arrays.asList(keys));
        HashSet<String> signingKeys = getAllSignatureString(context);

        if(getAllSignatureString(context).size() == 0) {
            Log.e("ApkNotSigned","Cannot retrieve signing certificate");
            return CODE_SIGNATURE_MATCHING_NOT_VALID;
        }

        
        for(String signingKey:signingKeys){
            if(keySet.contains(signingKey)){
                Log.e("UnknownCertificate", signingKey);
                return CODE_SIGNATURE_MATCHING_VALID;
            }
        }
        return CODE_SIGNATURE_MATCHING_NOT_VALID;
    }
}
