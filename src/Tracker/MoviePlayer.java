/** @file MoviePlayer.java
 * @breif Establishes what information MoviePlayer needs holds.
 * @Author Leemarie Collet
 */

package Tracker;

public class MoviePlayer extends Product implements MultimediaControl {
    private Screen screen;
    private MonitorType monitorType;


    public MoviePlayer(String n, String m, Screen s, MonitorType mon) {
        super(n, m, ItemType.VISUAL);
        monitorType = mon;
        screen = s;
    }

    public void play() {
        System.out.println("Play Movie");
    }

    public void stop() {
        System.out.println("Stop Movie");
    }

    public void previous() {
        System.out.println("Previous Movie");
    }

    public void next() {
        System.out.println("Next Movie");
    }

    public String toString() {
        System.out.println(super.toString());
        return "Screen\n" + screen.toString() + "\nMonitor Type: " + monitorType.toString();
    }
}