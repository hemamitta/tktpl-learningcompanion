#include <jni.h>
#include <string>
#include <android/log.h>
#include <string.h>

extern "C"
JNIEXPORT jdouble JNICALL
Java_id_ac_ui_cs_mobileprogramming_hemamittakalyani_learningcompanion_views_CourseProgressActivity_percentageFromJNI(
        JNIEnv* env,
        jobject, jint totalTime, jint targetSecond) {
    double result = static_cast<double>(totalTime) / static_cast<double>(targetSecond) * 100;
    return result;
}