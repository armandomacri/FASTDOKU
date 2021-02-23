package it.unipi.dii.inginf.dsmt.fastdoku.persistence;

import it.unipi.dii.inginf.dsmt.fastdoku.bean.User;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import java.io.File;
import java.io.IOException;
import static org.iq80.leveldb.impl.Iq80DBFactory.*;
import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;

/**
 * Class that contains the function used to interact with the Key-Value DB
 * Pattern of the keys: user:username:field
 */
public class LevelDBUser{
    private static final String DB_PATH = "usersDB"; //usare parametri configurazione
    private static volatile LevelDBUser instance; //Singleton instance
    private DB db;

    private LevelDBUser() {}

    public static LevelDBUser getInstance() {
        if (instance == null) {
            synchronized (LevelDBUser.class) {
                if (instance == null)
                    instance = new LevelDBUser();
            }
        }
        return instance;
    }

    /**
     * open the connection with LevelDB
     */
    private void openDB() {

        Options options = new Options();
        options.createIfMissing(true);
        try {
            db = factory.open(new File(DB_PATH), options);
        }
        catch (IOException ioe){
            ioe.printStackTrace();
            closeDB();
        }
    }

    /**
     * close the connection with LevelDB
     */
    private void closeDB() {
        try {
            if (db != null)
                db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param key
     * @param value
     */
    private void putValue (String key, String value) { db.put(bytes(key), bytes(value)); }

    /**
     *
     * @param key
     * @return
     */
    private String getValue (String key) { return asString(db.get(bytes(key))); }

    /**
     *
     * @param key
     */
    private void deleteValue (String key) { db.delete(bytes(key)); }

    private User checkUsername (final String username) {
        User user = null;
        String password = getValue("user:" + username + ":password");
        // If this user is present in the DB
        if (password != null)
            user = new User(username, password);
        return user;
    }

    public User login (final String username, final String password) {
        openDB();
        User user = checkUsername(username);
        // If doesn't exist a User registered with that username, or if the password doesn't match
        if (user == null || !user.getPassword().equals(password))
            return null;
        closeDB();
        return user;
    }


    public boolean isRegistered(final String username) {
        openDB();
        boolean registered = false;
        String value = getValue("user:" + username + ":password");
        if (value != null)
            registered = true;
        closeDB();
        return registered;
    }

    public void signin(final String username, final String password) {
        openDB();
        putValue("user:" + username + ":password", password);
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