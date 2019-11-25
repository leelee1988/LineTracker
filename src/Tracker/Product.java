package Tracker;

public class Product implements Item {
    private int id;
    private String name;
    private String type;
    private String manufacturer;
    private ItemType types;
    String abr;

    Product(int i, String n, String t, String m) {
        id = i;
        name = n;
        type = t;
        manufacturer = m;
    }

    Product(String n, String m, ItemType t) {
        name = n;
        manufacturer = m;
        types = t;
    }

    public String toString() {
        return "Product Id: " + id + "\nName: " + name + "\nManufacturer: " + manufacturer + "\nType: " + type;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ItemType getTypes(){
        return types;
    }

    public void setTypes(ItemType types){
        this.types = types;
    }

    public String getAbr(){
        return abr;
    }

    public void setAbr(String abr){
        this.abr = abr;
    }

}

class Widget extends Product {
    Widget(int i, String n, String m, String t) {
        super(i, n, m, t);
    }

    Widget(String n, String m, ItemType t) {
        super(n, m, t);
    }
}