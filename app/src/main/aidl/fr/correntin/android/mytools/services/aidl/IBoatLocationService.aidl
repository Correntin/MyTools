// IBoatLocationService.aidl
package fr.correntin.android.mytools.services.aidl;

// Declare any non-default types here with import statements

interface IBoatLocationService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString);
    void startTracking();
    void stopTracking();
    boolean isTrackingRunning();
    void setPhoneNumber(String phoneNumber);
}
