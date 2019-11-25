/**
 * @file Controller.java
 * @breif Controller class for fxml file.
 * @author Leemarie Collet
 */
package Tracker;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class Controller{

    @FXML
    private TableView<Product> choices;

    @FXML
    private ListView<Product> produce_list;

    @FXML
    private ComboBox<Integer> quantity_Combobox;

    @FXML
    private TextField manufacturer_info;

    @FXML
    private TextField productName_info;

    @FXML
    private ChoiceBox<String> Item_choice;

    @FXML
    private TableView<ProductionRecord> ProductionLog;


    private final String JDBC_DRIVER = "org.h2.Driver";
    private final String DB_URL = "jdbc:h2:./res/LineTracker";

    private Connection conn = null;
    private Statement stmt = null;

    public void initialize() {

        // Populates quantity_comboBox in the produce tab of GUI.
        for (int i = 1; i <= 10; i++) {
            quantity_Combobox.getItems().add(i);
        }
        // Adds Item Types to ChoiceBox
        for (ItemType items : ItemType.values()) {
            Item_choice.getItems().add(items.getType());
        }
        // Combobox properties
        quantity_Combobox.getSelectionModel().selectFirst();
        quantity_Combobox.setEditable(true);

        // Initialize Main Observable Lists
        ObservableList<Product> prodTableList = choices.getItems();
        ObservableList<Product> prodList = produce_list.getItems();
        ObservableList<ProductionRecord> productionRecord = ProductionLog.getItems();

        // Connect to Database
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();

            // Create Table Columns for Produce Tab in GUI
            TableColumn<Product, Integer> col_id = new TableColumn<>("ID");
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_id.setMinWidth(54);
            TableColumn<Product, String> col_name = new TableColumn<>("Name");
            col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            col_name.setMinWidth(100);
            TableColumn<Product, String> col_type = new TableColumn<>("Type");
            col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
            col_type.setMinWidth(100);
            TableColumn<Product, String> col_man = new TableColumn<>("Manufacturer");
            col_man.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
            col_man.setMinWidth(150);
            choices.getColumns().addAll(col_id, col_name, col_type, col_man);


            // Create Table Columns for Production Log in GUI
            TableColumn<ProductionRecord, Integer> col_prodNum = new TableColumn<>("Production Number");
            col_prodNum.setCellValueFactory(new PropertyValueFactory<>("ProductionNum"));
            TableColumn<ProductionRecord, Integer> col_prodID = new TableColumn<>("Product ID");
            col_prodID.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
            TableColumn<ProductionRecord, String> col_serialNum = new TableColumn<>("Serial Number");
            col_serialNum.setCellValueFactory(new PropertyValueFactory<>("SerialNum"));
            TableColumn<ProductionRecord, Timestamp> col_currentDate = new TableColumn<>("Date Produced");
            col_currentDate.setCellValueFactory(new PropertyValueFactory<>("ProdDate"));
            ProductionLog.getColumns().addAll(col_prodNum, col_prodID, col_serialNum, col_currentDate);

            String sql = "SELECT * FROM PRODUCT";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {

                prodTableList.add(
                        new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                prodList.add(
                        new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM PRODUCTIONRECORD");
            while (rs.next()) {
                productionRecord.add(new ProductionRecord(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getTimestamp(4)));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void addProduct() {
        String manufacturer_input = manufacturer_info.getText();
        String productName_input = productName_info.getText();
        String item_type = Item_choice.getValue();

        try {
            String sql = "INSERT INTO PRODUCT (NAME, TYPE , MANUFACTURER) VALUES ('"
                    + productName_input
                    + "', '"
                    + item_type
                    + "', '"
                    + manufacturer_input
                    + "')";
            try {
                stmt.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");

            while (rs.next()) {
                if (rs.last()) {
                    ObservableList<Product> prodList = produce_list.getItems();
                    ObservableList<Product> prodTableList = choices.getItems();
                    prodTableList.add(
                            new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                    prodList.add(
                            new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));

                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void record_production() {
        // pulls information from table row and places into object Product
        Product testing = choices.getItems().get(choices.getSelectionModel().getFocusedIndex());
        System.out.println(testing.getType());
        // change string to enum type
        ItemType typeSwitch = null;
        switch (testing.getType()) {
            case "AUDIO":
                typeSwitch = ItemType.AUDIO;
                break;
            case "VISUAL":
                typeSwitch = ItemType.VISUAL;
                break;
            case "AUDIO_MOBILE":
                typeSwitch = ItemType.AUDIO_MOBILE;
                break;
            case "VISUAL_MOBILE":
                typeSwitch = ItemType.VISUAL_MOBILE;
                break;
            default:
                System.out.println("Invalid String - Unable to Define ItemType");
        }
        Product productProduced = new Widget(testing.getName(), testing.getManufacturer(), typeSwitch);

        // gets value from combobox
        String quantityString = String.valueOf(quantity_Combobox.getValue());
        int quantity = Integer.parseInt(quantityString);

        int itemCount = 0; //create better counter
        for (int i = 0; i < quantity; i++) {
            ObservableList<ProductionRecord> productionRecord = ProductionLog.getItems();
            int productionNum = productionRecord.size();
            ProductionRecord productionRec = new ProductionRecord(productProduced);
            System.out.println(productionRec.getSerialNum());
            try {
                Timestamp ts = new Timestamp(productionRec.getProdDate().getTime());
                String sql = "INSERT INTO PRODUCTIONRECORD (PRODUCTION_NUM, PRODUCT_ID , SERIAL_NUM, DATE_PRODUCED) VALUES ('" + productionNum + "', '" + testing.getId() + "', '" + productionRec.getSerialNum() + "', '" + ts + "')";
                //Test Case
                System.out.println("THE SQL STATEMNT: " + sql);
                try {
                    stmt.execute(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
            try {
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                conn = DriverManager.getConnection(DB_URL);
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCTIONRECORD");
                while (rs.next()) {
                    if (rs.last()) {
                        productionRecord.add(
                                new ProductionRecord(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDate(4)));
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }


}
