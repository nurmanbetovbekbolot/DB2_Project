package dbproject.db;//package db;
//
//public class DatabaseConnection {
//
//    DatabaseConnection databaseConnection = new DatabaseConnection();
//    Connection connectDB = databaseConnection.getConnection();
//    String verifyLogin = "SELECT count(1) FROM Account WHERE username = '" + usernameTextField.getText() + "' AND password = '" + enterPasswordField.getText() + "'";
//
//        try {
//        Statement statement = connectDB.createStatement();
//        ResultSet resultSet = statement.executeQuery(verifyLogin);
//
//        while (resultSet.next()) {
//            if (resultSet.getInt(1) == 1) {
//
//                loginInfoLabel.setText("Congratulations!");
//                mainApp.setUsername(usernameTextField.getText());
//                mainApp.setPassword(enterPasswordField.getText());
//
//                switch (usernameTextField.getText()) {
//                    case "acc1":
//                        mainApp.setRole(DbRole.READER);
//                        break;
//                    case "acc2":
//                        mainApp.setRole(DbRole.WRITER);
//                        break;
//                    case "admin":
//                        mainApp.setRole(DbRole.ADMIN);
//                        break;
//                }
//
//                mainApp.showDb();
//            } else {
//                loginInfoLabel.setText("Invalid login. Please try again!");
//            }
//        }
//        resultSet.close();
//        connectDB.close();
//    } catch (Exception e) {
//        e.printStackTrace();
//        e.getCause();
//    }
//}
