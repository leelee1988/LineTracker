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
import javafx.event.ActionEvent;
import java.sql.*;

public class Controller {

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
    @FXML
    private Tab Accounts;
    @FXML
    private Label no_manager;
    @FXML
    private TableView<EmployeeInfo> employee_list;
    @FXML
    private TextField full_name;
    @FXML
    private PasswordField passW;
    @FXML
    private Label generated_user;
    @FXML
    private Label gen_email;
    @FXML
    private Label gen_username;
    @FXML
    private Label generated_email;
    @FXML
    private CheckBox manager_account;
    @FXML
    private Label not_correct;
    @FXML
    private Label add_error;
    @FXML
    private Label add_success;
    @FXML
    private Label not_selected;
    @FXML
    private Label production_recorded;
    @FXML
    private Label production_error;

    static LoggedEmployee log_emp;

    private final String JDBC_DRIVER = "org.h2.Driver";
    private final String DB_URL = "jdbc:h2:./res/LineTracker";

    private Connection conn = null;
    private Statement stmt = null;

    public void initialize() {

        gen_username.setVisible(false);
        gen_email.setVisible(false);
        generated_user.setVisible(false);
        generated_email.setVisible(false);
        Accounts.setDisable(true);
        no_manager.setVisible(false);
        not_correct.setVisible(false);
        add_error.setVisible(false);
        add_success.setVisible(false);
        production_recorded.setVisible(false);
        production_error.setVisible(false);
        not_selected.setVisible(false);

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
        ObservableList<EmployeeInfo> employeeList = employee_list.getItems();
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

            // Create Table Columns for Employees In Management tab of GUI
            TableColumn<EmployeeInfo, String> col_uname = new TableColumn<>("Employee Name");
            col_uname.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<EmployeeInfo, String> col_username = new TableColumn<>("Username");
            col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
            TableColumn<EmployeeInfo, String> col_email = new TableColumn<>("Email");
            col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
            TableColumn<EmployeeInfo, Boolean> col_isManagement = new TableColumn<>("Manager");
            col_isManagement.setCellValueFactory(new PropertyValueFactory<>("management"));
            employee_list.getColumns().addAll(col_uname, col_username, col_email, col_isManagement);


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
                productionRecord.add(new ProductionRecord(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDate(4), rs.getString(5)));
            }

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT NAME, USERNAME , EMAIL , MANAGEMENT FROM EMPLOYEE");
            while (rs.next()) {
                employeeList.add(
                        new EmployeeInfo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4)));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void generated_info(ActionEvent event) {
        String f_name = full_name.getText();
        String p_word = passW.getText();
        Boolean manager = manager_account.isSelected();
        Employee employee = new Employee(f_name, p_word, manager);
        if (employee.getUsername().equals("default") || employee.getPassword().equals("pw")) {
            not_correct.setVisible(true);
            gen_email.setVisible(false);
            generated_email.setVisible(false);
            gen_username.setVisible(false);
            generated_user.setVisible(false);
        } else {
            not_correct.setVisible(false);
            generated_user.setText(employee.getUsername());
            generated_user.setVisible(true);
            gen_username.setVisible(true);
            generated_email.setText(employee.getEmail());
            generated_email.setVisible(true);
            gen_email.setVisible(true);
            String usernameCheck = employee.getUsername();
            int nameCount = 0;

            try {
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                conn = DriverManager.getConnection(DB_URL);
                stmt = conn.createStatement();
                ResultSet rs =
                        stmt.executeQuery("SELECT NAME, USERNAME , EMAIL , MANAGEMENT FROM EMPLOYEE");
                while (rs.next()) {
                    if (rs.getString(2).contains(usernameCheck)) {
                        nameCount += 1;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                String user_n = employee.getUsername();
                if (nameCount != 0) {
                    user_n += Integer.toString(nameCount);
                }
                String tempPass = employee.getPassword();
                String sql =
                        "INSERT INTO EMPLOYEE (NAME, USERNAME , EMAIL , PASSWORD , MANAGEMENT) VALUES (?, ?, ?, ?, ?)";
                try {
                    PreparedStatement stmtp = conn.prepareStatement(sql);
                    stmtp.setString(1, employee.getName());
                    stmtp.setString(2, user_n);
                    stmtp.setString(3, employee.getEmail());
                    stmtp.setString(4, reverseString(tempPass));
                    stmtp.setBoolean(5, employee.getManagement());
                    stmtp.executeUpdate();
                    stmtp.close();
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
                ResultSet rs =
                        stmt.executeQuery("SELECT NAME, USERNAME , EMAIL , MANAGEMENT FROM EMPLOYEE");
                while (rs.next()) {
                    if (rs.last()) {
                        ObservableList<EmployeeInfo> employeeList = employee_list.getItems();
                        employeeList.add(
                                new EmployeeInfo(
                                        rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4)));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param id Inputted Password
     * @return Encrypted Password
     */
    private static String reverseString(String id) {
        if (id.isEmpty()) {
            return id;
        }
        // Calling Function Recursively
        return reverseString(id.substring(1)) + id.charAt(0);
    }

    /**
     * @param user_name Logged in Users Username
     * @param is_access Determines if Logged In user is Management
     */
    void sendUserInfo(String user_name, Boolean is_access) {
        log_emp = new LoggedEmployee(user_name, is_access);
    }

    @FXML
    void managementAccess(javafx.event.ActionEvent event) {
        if (log_emp.getManagement()) {
            Accounts.setDisable(false);
        } else {
            no_manager.setVisible(true);
        }
    }


    public void addProduct() {
        String manufacturer_input = manufacturer_info.getText();
        String productName_input = productName_info.getText();
        String item_type = Item_choice.getValue();

        if (manufacturer_input == null
                || productName_input == null
                || item_type == null
                || manufacturer_input.equals("")
                || productName_input.equals("")
                || item_type.equals("")) {
            add_error.setVisible(true);
            production_recorded.setVisible(false);
            production_error.setVisible(false);
            add_success.setVisible(false);
        } else {
            add_success.setVisible(true);
            add_error.setVisible(false);
            production_recorded.setVisible(false);
            production_error.setVisible(false);

            try {
                String sql = "INSERT INTO PRODUCT (NAME, TYPE , MANUFACTURER) VALUES (?, ? , ?)";
                try {
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, productName_input);
                    pstmt.setString(2, item_type);
                    pstmt.setString(3, manufacturer_input);
                    pstmt.executeUpdate();
                    pstmt.close();
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
    }
    public void record_prod() {
        // pulls information from table row and places into object Product
        Product testing =
                choices.getItems().get(choices.getSelectionModel().getFocusedIndex());
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
        String quantityString = String.valueOf(quantity_Combobox.getValue());
        ObservableList selectedItems = choices.getSelectionModel().getSelectedItems();
        not_selected.setVisible(false);
        production_error.setVisible(false);
        production_recorded.setVisible(false);
        add_error.setVisible(false);
        if (quantity_Combobox.getValue() == null
                || quantityString.equals("")
                || !quantityString.matches("[^a-zA-Z]")
                || selectedItems.get(0) == null) {
            if (selectedItems.get(0) == null) {
                not_selected.setVisible(true);
            }
            if (quantity_Combobox.getValue() == null
                    || quantityString.equals("")
                    || !quantityString.matches("[^a-zA-Z]")) {
                production_error.setVisible(true);
                production_recorded.setVisible(false);
                add_error.setVisible(false);
            }
        } else {
            production_error.setVisible(false);
            add_error.setVisible(false);
            not_selected.setVisible(false);
            // gets value from combobox
            int quantity = Integer.parseInt(quantityString);
            // Initiates count for Serial Number
            for (int i = 0; i < quantity; i++) {
                int itemCount = 0;
                ObservableList<ProductionRecord> productionRecord = ProductionLog.getItems();
                for (ProductionRecord record : productionRecord) {
                    assert typeSwitch != null;
                    String comparison = record.getSerialNum().substring(3, 5);
                    if (typeSwitch.item_type_abr.equals(comparison)) {
                        itemCount++;
                    }
                }
                int prodNum = productionRecord.size();
                // Check Product productProduced to find number of ItemType
                ProductionRecord prodRec = new ProductionRecord(productProduced, itemCount);
                try {
                    Timestamp ts = new Timestamp(prodRec.getProdDate().getTime());
                    String sql =
                            "INSERT INTO PRODUCTIONRECORD (PRODUCTION_NUM, PRODUCT_ID , SERIAL_NUM, DATE_PRODUCED, REC_EMPLOYEE ) VALUES (? , ? , ? , ? , ?)";
                    // Test Case
                    // System.out.println("THE SQL STATEMNT: " + sql);
                    production_recorded.setVisible(true);
                    try {
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setInt(1, prodNum);
                        stmt.setInt(2, testing.getId());
                        stmt.setString(3, prodRec.getSerialNum());
                        stmt.setTimestamp(4, ts);
                        stmt.setString(5, log_emp.getUserName());
                        stmt.executeUpdate();
                        stmt.close();
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
                                    new ProductionRecord(
                                            rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDate(4), rs.getString(5)));
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
