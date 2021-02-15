package it.unipi.dii.inginf.dsmt.fastdoku.fastdoku.persistence;

import it.unipi.dii.inginf.dsmt.fastdoku.fastdoku.Config;
import it.unipi.dii.inginf.dsmt.fastdoku.fastdoku.User;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.WriteBatch;
import java.io.File;
import java.io.IOException;
import static org.iq80.leveldb.impl.Iq80DBFactory.*;
import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;

/**
 * Class that contains the function used to interact with the Key-Value DB
 * Pattern of the keys: user:username:field
 */
public class LevelDBManager {
    private static volatile LevelDBManager instance; //Singleton instance
    private DB db;
    private String pathDatabase;
private LevelDBManager(){
   this("example");
}
private LevelDBManager(String pathDatabase){
    this.pathDatabase= pathDatabase;
  //  openDB();
}

    public static LevelDBManager getInstance() {
        if (instance == null)
        {
            synchronized (LevelDBManager.class)
            {
                if (instance == null)
                {
                    instance = new LevelDBManager();
                }
            }
        }
        return instance;
    }

    /*
     * open the connection with LevelDB
     */
    private void openDB()  {

        Options options = new Options();
        options.createIfMissing(true);
        try{
            db = factory.open(new File(pathDatabase), options);
            System.out.println("ok6");
        }
        catch
        (IOException ioe){
            ioe.printStackTrace();
            closeDB();
        }

    }

    /*
     * close the connection with LevelDB
     */
    public void closeDB() {
        try {
            if (db != null) {
                db.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void putValue (String key, String value)
    {
        db.put(bytes(key), bytes(value));
    }

    private String getValue (String key)
    {
        return asString(db.get(bytes(key)));
    }


    private void deleteValue (String key)
    {
        db.delete(bytes(key));
    }


    private User checkUsername (final String username)
    {

        User user = null;
        String password = getValue("user:" + username + ":password");
        // If this user is present in the DB
        if (password != null)
        {
            user = new User(username, password);
       /*     String value = getValue("user:" + username + ":ticTacToeWins");
            if (value != null)
                user.setTicTacToeWins(parseInt(value));
            value = getValue("user:" + username + ":connectFourWins");
            if (value != null)
                user.setConnectFourWins(parseInt(value));*/
        }

        return user;
    }


    public User login(final String username, final String password)
    {
        openDB();
        User user = checkUsername(username);
        // If doesn't exist a User registered with that username, or if the password doesn't match
        if (user == null || !user.getPassword().equals(password))
        {
            return null;
        }
        closeDB();
        return user;
    }


    public  boolean isRegistered(final String username)
    {
        openDB();
        boolean registered = false;
        String value = getValue("user:" + username + ":password");
        if (value != null)
            registered = true;
        closeDB();
        return registered;

    }


    public  void signin(final String username, final String password)
    {
openDB();
System.out.println("signin ok");
        putValue("user:" + username + ":password", password);
        System.out.println("put ok");

        closeDB();
    }

    /* public void ex1(){
        System.out.println("okok");
        openDB();
        try (WriteBatch batch = db.createWriteBatch()){
            System.out.println("ok");
            batch.put(bytes("ross"),bytes("ok"));
            batch.put(bytes("ros"),bytes("ok1"));
            db.write(batch);

        } catch (IOException ioe) {

            ioe.printStackTrace();
        }finally {closeDB();}
    }*/
   /* public static void main(String[] args) throws IOException {
        LevelDBManager a = new LevelDBManager();
        a.openDB();
        String x= a.getValue("ross");
        System.out.println(x);
        a.closeDB();


    }*/

}