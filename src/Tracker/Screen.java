/**
 * @file Screen.java
 * @breif Establishes screen details for MoviePlayer.
 * @Author Leemarie Collet
 */
package Tracker;

public class Screen implements ScreenSpec {
    String resolution;
    int refreshrate;
    int responsetime;

    Screen(String reso, int refr, int resp) {
        resolution = reso;
        refreshrate = refr;
        responsetime = resp;
    }

    public String toString() {
        return "Resolution: " + resolution + "\nRefreshrate: " + refreshrate + "\nResponsetime: " + responsetime;
    }

    @Override
    public String getResolution() {
        return resolution;
    }

    @Override
    public int getRefreshRate() {
        return refreshrate;
    }

    @Override
    public int getResponseTime() {
        return responsetime;
    }
}