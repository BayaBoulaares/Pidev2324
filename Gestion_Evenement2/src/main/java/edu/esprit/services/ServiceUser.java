package edu.esprit.services;

import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceUser {

    public User getUserById(int userId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish a database connection (modify connection details accordingly)
            connection = DataSource.getInstance().getCnx();

            // Prepare the SQL statement to fetch the user by ID
            String sql = "SELECT * FROM utilisateurs WHERE idu = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId );

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Check if the user was found
            if (resultSet.next()) {
                // Retrieve user data from the result set
                int idu = resultSet.getInt("idu");
                String nom = resultSet.getString("nom");
                // Retrieve other user attributes as needed

                // Create and return the User object
                return new User(idu, nom /* other attributes */);
            } else {
                // Handle the case where the user is not found
                throw new SQLException("User not found with ID: " + userId);
            }
        } finally {
            // Close the database resources in a finally block to ensure they are closed even if an exception occurs
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } finally {

            }
        }
    }
}
