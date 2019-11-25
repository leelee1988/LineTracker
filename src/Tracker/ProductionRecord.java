/**
 * @file ProductionRecord.java
 * @breif it is used to create Production Record based on Product Details.
 * @author Leemarie Collet
 */
package Tracker;

import java.util.Date;

public class ProductionRecord {
    private static int produced;
    private int prodNum;
    private int prodID;
    private String serialNum;
    private Date currentDate;

    ProductionRecord(int pon){
        prodNum = pon;
        prodID = 0;
        serialNum = "0";
        currentDate = new Date();
    }

    ProductionRecord(Product W){
        this.prodNum = W.getId();
        String manUpdate = W.getManufacturer().replaceAll(" ","_").toUpperCase().substring(0,3);
        serialNum = manUpdate + W.getTypes().item_type_abr + String.format("%05d", produced++);
        currentDate = new Date();
    }

    ProductionRecord(int prodNum, int prodID, String serialNum, Date currentDate){
        this.prodNum = prodNum;
        this.prodID = prodID;
        this.serialNum = serialNum;
        this.currentDate = currentDate;
    }

    public String toString(){
        return "Prod.Num: " + prodNum + " Product ID: " + prodID + " Serial Num: " + serialNum + " Date: " + currentDate;
    }

    public void setProductionNum(int prodNum){ this.prodNum = prodNum; }
    public void setProductID(int prodID){ this.prodID = prodID; }
    public void setSerialNum(String serialNum){ this.serialNum = serialNum; }
    public void setProdDate(Date currentDate){ this.currentDate = currentDate; }

    public int getProductionNum(){ return this.prodNum; }
    public int getProductID(){ return this.prodID; }

    public String getSerialNum(){ return this.serialNum; }
    public Date getProdDate(){ return this.currentDate; }
}