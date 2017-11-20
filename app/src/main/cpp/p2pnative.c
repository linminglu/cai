//
// Created by admin on 2015/12/16.
//

#include <stdlib.h>
#include "com_example_admin_caipiao33_utils_p2pnative.h"

#define TAG "p2pnative"
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, __VA_ARGS__)

JNIEXPORT jstring

JNICALL Java_com_example_admin_caipiao33_utils_P2PNative_decrypt(JNIEnv *env, jobject jobj,
                                                                 jstring jstr) {
//    LOGV("log form native  decrypt start!");
    if (jstr == NULL) {
        return NULL;
    }
    jstring key;
    jstring result;
    jclass AESencrypt;
    jmethodID mid;

    AESencrypt = (*env)->FindClass(env, "com/example/admin/caipiao33/encryption/MyAESencrypt");
    if (NULL == AESencrypt) {
        return NULL;
    }
    mid = (*env)->GetStaticMethodID(env, AESencrypt, "decrypt",
                                    "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
    if (NULL == mid) {
        (*env)->DeleteLocalRef(env, AESencrypt);
        return NULL;
    }
    key = (*env)->NewStringUTF(env, DES_KEY);
    result = (*env)->CallStaticObjectMethod(env, AESencrypt, mid, key, jstr);
    (*env)->DeleteLocalRef(env, AESencrypt);
    (*env)->DeleteLocalRef(env, key);
    return result;
}

JNIEXPORT jstring

JNICALL Java_com_example_admin_caipiao33_utils_P2PNative_encrypt(JNIEnv *env, jobject jobj,
                                                                 jstring jstr) {
//    LOGV("log form native  encrypt start!");
    if (jstr == NULL) {
        return NULL;
    }
    jstring key;
    jstring result;
    jclass AESencrypt;
    jmethodID mid;

    AESencrypt = (*env)->FindClass(env, "com/example/admin/caipiao33/encryption/MyAESencrypt");
    if (NULL == AESencrypt) {
        return NULL;
    }
    mid = (*env)->GetStaticMethodID(env, AESencrypt, "encrypt",
                                    "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;");
    if (NULL == mid) {
        (*env)->DeleteLocalRef(env, AESencrypt);
        return NULL;
    }
    key = (*env)->NewStringUTF(env, DES_KEY);
    result = (*env)->CallStaticObjectMethod(env, AESencrypt, mid, key, jstr);
    (*env)->DeleteLocalRef(env, AESencrypt);
    (*env)->DeleteLocalRef(env, key);
    return result;
}