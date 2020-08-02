LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := SafeVault
LOCAL_SRC_FILES := SafeVault.c

include $(BUILD_SHARED_LIBRARY)