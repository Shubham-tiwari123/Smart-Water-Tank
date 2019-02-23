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
public class ConnectionWithPi extends HttpServlet {

    SaveData saveData = new SaveData();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("hiiiiis");
        String waterLevel = null;
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = request.getReader();
        String line;
        while((line = reader.readLine())!= null)
            buffer.append(line);
        String data = buffer.toString();
        JSONParser parser = new JSONParser();
        System.out.println("dataOrder:-"+data);
        
        try {
            JSONObject jSONObject = (JSONObject) parser.parse(data);
            waterLevel = jSONObject.get("waterLevel").toString();
            int level = Integer.parseInt(waterLevel);
            saveData.updateData(level);
            System.out.println("text:-"+waterLevel);
        } catch (ParseException ex) {
            
        }
        
        /*JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("itemName", "shubham");
        //jSONObject3.put("itemPrice", priceObj);
        jSONObject3.put("itemQnt", "tiwari");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject3);*/
    }
}
