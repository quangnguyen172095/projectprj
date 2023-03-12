/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import static Utils.DBUtils.makeConnection;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class TestDB {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        makeConnection();
    }
}
