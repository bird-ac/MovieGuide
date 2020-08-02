//
// Created by Bird.AC on 26/07/2020.
//
#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_esoxjem_movieguide_safevault_SafeVault_getSslSha(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "sha256/HkCBucsA3Tgiby96X7vjb/ojHaE1BrjvZ2+LRdJJd0E=");
}

JNIEXPORT jstring JNICALL
Java_com_esoxjem_movieguide_safevault_SafeVault_getApiKey(JNIEnv *env, jobject instance) {
    return (*env)->  NewStringUTF(env, "<TMDB_KEY_API>");
}

JNIEXPORT jobjectArray JNICALL
Java_com_esoxjem_movieguide_safevault_SafeVault_getSignatureKeys(JNIEnv *env, jobject thiz) {
    jobjectArray ret;
    int i;
    int maxElement = 3;
    char *data[3]= {"c3321433e970523c906b9dbd40933d75de9ac923",
                    "44b6c6365257b9d58938553d60995caff2d13845",
                    "8912aa73f6c7ff3f06d223d2ed9114a49d444003"};

    ret= (jobjectArray)(*env)->NewObjectArray(env, maxElement,(*env)->FindClass(env, "java/lang/String"),(*env)->NewStringUTF(env,""));

    for(i=0;i<maxElement;i++) (*env)->SetObjectArrayElement(env,ret,i,(*env)->NewStringUTF(env,data[i]));

    return(ret);
}