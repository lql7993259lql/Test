LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := Bspatch
LOCAL_SRC_FILES := bspatch.c Test.c

include $(BUILD_SHARED_LIBRARY)
