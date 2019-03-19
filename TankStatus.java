package RestAPI;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Database.SaveData;
import org.json.simple.JSONObject;

public class TankStatus extends HttpServlet {
    
    SaveData saveData = new SaveData();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int waterLevel = saveData.getData();
        JSONObject waterObj = new JSONObject();
        waterObj.put("waterLevel", waterLevel);
        System.out.println("Level:"+waterLevel);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("waterObj", waterObj);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
    }
}
