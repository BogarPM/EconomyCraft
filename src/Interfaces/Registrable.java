/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.sql.SQLException;

/**
 *
 * @author Bogar
 */
public interface Registrable {
    public void register() throws SQLException;
    public boolean isRegistered() throws SQLException;
    public void update(String field, Object value) throws SQLException;

}
