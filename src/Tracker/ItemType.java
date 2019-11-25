/**
 * @Author Leemarie Collet
 */
package Tracker;

public enum ItemType {
    AUDIO("AUDIO", "AU"),
    VISUAL("VISUAL", "VI"),
    AUDIO_MOBILE("AUDIO_MOBILE", "AM"),
    VISUAL_MOBILE("VISUAL_MOBILE", "VM");

    public final String item_type;
    public final String item_type_abr;

    ItemType(String item_type, String item_type_abr) {
        this.item_type = item_type;
        this.item_type_abr = item_type_abr;
    }

    public String getType() {
        return item_type;
    }

    public String getAbr() {
        return item_type_abr;
    }
}