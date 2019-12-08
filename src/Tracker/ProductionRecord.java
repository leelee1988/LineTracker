package Tracker;

import java.util.Date;

/**
 * Used to create ProductionRecord based on Product Details.
 *
 * @file ProductionRecord.java
 * @author leemarie Collet
 */
public class ProductionRecord {
    private int produced;
    private int prodNum;
    private int prodID;
    private String serialNum;
    private Date currentDate;
    private String employee;

    /**
     * @param W Object Product
     * @param produced Number of each based on ItemType used to generate Serial Number
     */
    ProductionRecord(Product W, int produced) {
        this.produced = produced;
        this.prodID = W.getId();
        // create a better counting system for serial num
        String manUpdate = W.getManufacturer().replaceAll(" ", "_").toUpperCase().substring(0, 3);
        serialNum = manUpdate + W.getTypes().item_type_abr + String.format("%05d", this.produced);
        currentDate = new Date();
    }

    /**
     * @param prodNum Production Number
     * @param prodID Production ID
     * @param serialNum Product Serial Number
     * @param currentDate Product Current Date
     * @param string
     */
    ProductionRecord(int prodNum, int prodID, String serialNum, Date currentDate, String string) {
        this.prodNum = prodNum;
        this.prodID = prodID;
        this.serialNum = serialNum;
        this.currentDate = currentDate;
        this.employee = employee;
    }

    /** @return override to toString method for ProductionRecord */
    public String toString() {
        return "Prod. Num: "
                + prodNum
                + " Product ID: "
                + prodID
                + " Serial Num: "
                + serialNum
                + " Date: "
                + currentDate;
    }

    /** @param prodNum Production Number */
    public void setProductionNum(int prodNum) {
        this.prodNum = prodNum;
    }

    /** @param prodID Product ID */
    public void setProductID(int prodID) {
        this.prodID = prodID;
    }

    /** @param serialNum Serial Number */
    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    /** @param currentDate Product Current Date */
    public void setProdDate(Date currentDate) {
        this.currentDate = new Date(currentDate.getTime());
    }

    /** @return Production Number */
    public int getProductionNum() {
        return prodNum;
    }

    /** @return Product ID */
    public int getProductID() {
        return prodID;
    }

    /** @return Product Serial Number */
    public String getSerialNum() {
        return serialNum;
    }

    /** @return Product Current Date */
    public Date getProdDate() {
        return new Date(currentDate.getTime());
    }

    /** @return Logged in Employee */
    public String getEmployee() {
        return employee;
    }

    /** @param employee Logged in Employee Username */
    public void setEmployee(String employee) {
        this.employee = employee;
    }
}
