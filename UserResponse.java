package RestAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import Database.SaveData;

public class UserResponse extends HttpServlet {
    SaveData saveData = new SaveData();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userResponse;
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = request.getReader();
        String line;
        while((line = reader.readLine())!= null)
            buffer.append(line);
        String data = buffer.toString();
        JSONParser parser = new JSONParser();
        System.out.println("btnStatus:-"+data);
        try {
            JSONObject jSONObject = (JSONObject) parser.parse(data);
            userResponse = jSONObject.get("userResponse").toString();
            int btnStatus = Integer.parseInt(userResponse);
            saveData.updateBtnStatus(btnStatus);
            System.out.println("text:-"+userResponse);
        } catch (ParseException ex) {
            
        }
    }
}
