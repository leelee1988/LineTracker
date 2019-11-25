/**
 * @file AudioPlayer.java
 * @breif  This is an extension for ItemType Audio.
 * @author Leemarie Collet
 */
package Tracker;

public class AudioPlayer extends Product implements MultimediaControl {
    private String audioSpecification;
    private String mediaType;

    AudioPlayer(String n, String m, String audioSpecification, String mediaType) {
        super(n, m, ItemType.AUDIO);
        this.audioSpecification = audioSpecification;
        this.mediaType = mediaType;
    }

    public void play() {
        System.out.println("Playing");
    }
    public void stop() {
        System.out.println("Stopping");
    }
    public void previous() {
        System.out.println("Previous");
    }
    public void next() {
        System.out.println("Next");
    }

    public String toString() {
        System.out.println(super.toString());
        return "Audio Formats: " + audioSpecification + "\nSupported Playlist Formats: " + mediaType;
    }
}