
package Database;

import com.mysql.jdbc.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveData extends DbConnection{
    
    public void updateData(int waterLevel){
        try {
            initializeDbConnection();
            smt.executeUpdate("UPDATE RaspberryData SET waterLevel="+waterLevel+" WHERE srno=1");
        } catch (SQLException ex) {
            Logger.getLogger(SaveData.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeDbConnection(connection);
        }
    }
    
    public void updateBtnStatus(int btnStatus){
        try {
            initializeDbConnection();
            smt.executeUpdate("UPDATE RaspberryData SET userResponse="+btnStatus+" WHERE srno=1");
        } catch (SQLException ex) {
            Logger.getLogger(SaveData.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeDbConnection(connection);
        }
    }
    
    public int getBtnStatus(){
        int btnResponse=0;
        try {
            initializeDbConnection();
            resultSet = (ResultSet) smt.executeQuery("SELECT * FROM RaspberryData");
            while (resultSet.next()) {                
                btnResponse = resultSet.getInt("userResponse");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SaveData.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeDbConnection(connection);
        }
        return btnResponse;
    }
    
    public int getData(){
        int waterLevel = 0;
        try {
            initializeDbConnection();
            resultSet = (ResultSet) smt.executeQuery("SELECT * FROM RaspberryData");
            while (resultSet.next()) {                
                waterLevel = resultSet.getInt("waterLevel");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SaveData.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeDbConnection(connection);
        }
        return  waterLevel;
    }
}
