/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakingBad;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author rober
 */

public class GameSession {
    Game game;
    String resourceName;
    
    public GameSession(Game game){
        this.game = game;
        resourceName = "/Files/Session.json";
    }
    
    public void resumePastSave(){
        JSONObject sessionData;
        InputStream is = GameSession.class.getResourceAsStream(resourceName);
        if (is == null) {
            throw new NullPointerException("Cannot find resource file " + resourceName);
        }
        JSONTokener tokener = new JSONTokener(is);
        sessionData = new JSONObject(tokener);
        if(sessionData.isEmpty()) return;
        //Ball reset
        int bX, bY;
        double xVel, yVel, xDisp, yDisp;
        bX = sessionData.getJSONObject("Ball").getInt("x");
        bY = sessionData.getJSONObject("Ball").getInt("y");
        xVel = sessionData.getJSONObject("Ball").getDouble("xV");
        yVel = sessionData.getJSONObject("Ball").getDouble("yV");
        xDisp = sessionData.getJSONObject("Ball").getDouble("xD");
        yDisp = sessionData.getJSONObject("Ball").getDouble("yD");
        game.getBall().reset(bX, bY, xVel, yVel, xDisp, yDisp);
        //Bar reset
        int barX, barY;
        barX = sessionData.getJSONObject("Bar").getInt("x");
        barY = sessionData.getJSONObject("Bar").getInt("y");
        game.getPlayer().reset(barX, barY);
        
        //Birck reset
        ArrayList<Brick> newBricks = new ArrayList();
        for(int i = 0; i < sessionData.getJSONArray("Bricks").length(); i++){
            int x, y, potencia, width, height;
            x = ((JSONObject)sessionData.getJSONArray("Bricks").get(i)).getInt("x");
            y = ((JSONObject)sessionData.getJSONArray("Bricks").get(i)).getInt("y");
            width = ((JSONObject)sessionData.getJSONArray("Bricks").get(i)).getInt("w");
            height = ((JSONObject)sessionData.getJSONArray("Bricks").get(i)).getInt("h");
            potencia = ((JSONObject)sessionData.getJSONArray("Bricks").get(i)).getInt("p");
            newBricks.add(new Brick(x, y, width, height, potencia, game));
        }
        game.setBricks(newBricks);
    }
    
    public void save(){
        JSONObject fileObject = new JSONObject();
        
        JSONObject Ball = new JSONObject();
        //Ball save
        Ball.put("x", game.getBall().getX());
        Ball.put("y", game.getBall().getY());  
        Ball.put("xV", game.getBall().getxVelocity());
        Ball.put("yV", game.getBall().getyVelocity());
        Ball.put("xD", game.getBall().getxDisplacement());
        Ball.put("yD", game.getBall().getyDisplacement());
        
        JSONObject Bar = new JSONObject();
        //Bar save
        Bar.put("x", game.getPlayer().getX());
        Bar.put("y", game.getPlayer().getY());
        
        //Brick save
        JSONArray bricks = new JSONArray();
        ArrayList<Brick> originalBricks = game.getBricks();
        for(int i = 0; i < originalBricks.size(); i++){
            JSONObject br = new JSONObject();
            br.put("x", originalBricks.get(i).getX());
            br.put("y", originalBricks.get(i).getY());
            br.put("w", originalBricks.get(i).getWidth());
            br.put("h", originalBricks.get(i).getHeight());
            br.put("p", originalBricks.get(i).getPotencia());
            bricks.put(br);
        }
        
        fileObject.put("Bricks", bricks);
        fileObject.put("Ball", Ball);
        fileObject.put("Bar", Bar);
        
        FileWriter writer;  
        try {
            writer = new FileWriter(System.getProperty("user.dir") + "/src" + resourceName);
            BufferedWriter buffer = new BufferedWriter(writer);  
            buffer.write(fileObject.toString());
            buffer.close();  
        } catch (IOException ex) {
            Logger.getLogger(GameSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
