package Tracker;

public class EmployeeInfo {
    /**
     * Object for storing information from Employee Table in DB
     *
     * @author Leemarie Collet
     * @file EmployeeInfo.java
     */
        private String name;
        private String username;
        private String email;
        private Boolean management;

        /**
         * @param name Full name of Employee
         * @param username Username of Employee
         * @param email Email of Employee
         * @param management Determines manager status
         */
        EmployeeInfo(String name, String username, String email, Boolean management) {
            this.name = name;
            this.username = username;
            this.email = email;
            this.management = management;
        }

        /** @return Employee name */
        public String getName() {
            return name;
        }

        /** @param name Sets Employee name */
        public void setName(String name) {
            this.name = name;
        }

        /** @return Employee username */
        public String getUsername() {
            return username;
        }

        /** @param username Sets Employee username */
        public void setUsername(String username) {
            this.username = username;
        }

        /** @return Employee email */
        public String getEmail() {
            return email;
        }

        /** @param email Sets Employee email */
        public void setEmail(String email) {
            this.email = email;
        }

        /** @return Management status */
        public Boolean getManagement() {
            return management;
        }

        /** @param management Sets Management status */
        public void setManagement(Boolean management) {
            this.management = management;
        }
    }

