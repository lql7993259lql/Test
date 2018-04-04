#include <jni.h>

#include "com_example_test_Ndk.h"


JNIEXPORT void JNICALL Java_com_example_test_Ndk_getFun
  (JNIEnv *env, jobject jobj){

}




JNIEXPORT jstring JNICALL Java_com_example_test_Ndk_getHandle
  (JNIEnv *env, jobject jobj){
		return (*env)->NewStringUTF(env,"hello");
}
