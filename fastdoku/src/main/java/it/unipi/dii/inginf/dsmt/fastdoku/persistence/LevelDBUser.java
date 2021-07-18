package it.unipi.dii.inginf.dsmt.fastdoku.persistence;

import it.unipi.dii.inginf.dsmt.fastdoku.Utils;
import it.unipi.dii.inginf.dsmt.fastdoku.bean.User;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.WriteBatch;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.StreamSupport;

import static java.lang.Integer.parseInt;
import static org.iq80.leveldb.impl.Iq80DBFactory.*;
import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;

/**
 * Class that contains the function used to interact with the Key-Value DB
 * Pattern of the keys: user:username:field
 */
public class LevelDBUser implements AutoCloseable {
    private static String DB_PATH; //database name psth
    private static volatile LevelDBUser instance; //Singleton instance
    private static DB db;

    /**
     * Private constructor
     */
    private LevelDBUser() {this("usersDB");}
    private LevelDBUser(final String path) {
        this.DB_PATH = path;
        this.openDB();
    }

    /**
     * Singleton pattern
     * @return return the unique istance 
     */
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
        catch (IOException ioe) {
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
     * store the value 'value' corresponding to the key 'key'
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
        String points = getValue("user:" + username + ":points");

        //controllo momentaneo per aggiungerlo agli utenti già iscritti
        if (points == null){
            putValue("user:" + username + ":points", "0");
            points = "0";
        }
        // If this user is present in the DB
        if (password != null)
            user = new User(username, password, parseInt(points));
        return user;
    }

    public User login (final String username, final String password) {
        User user = checkUsername(username);
        // If doesn't exist a User registered with that username, or if the password doesn't match
        if (user == null || !user.getPassword().equals(password))
            return null;

        return user;
    }


    public boolean isRegistered(final String username) {

        boolean registered = false;
        String value = getValue("user:" + username + ":password");
        if (value != null)
            registered = true;

        return registered;
    }

    public void signin (final String username, final String password) {
        WriteBatch batch = db.createWriteBatch();
        try {
            batch.put(bytes("user:" + username + ":password"), bytes(password));
            batch.put(bytes("user:" + username + ":points"), bytes("0"));
            db.write(batch);
            batch.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void updateScore (final String username, final int points) {
        putValue("user:" + username + ":points", Integer.toString(points));
    }


    /**
     *
     *R-Function that returns the rank for the user passed ad parameter
     * R- User id user to get the ranking of
     * @param limit number of records to be returned
     * @return a sorted HashMap where the key is the username and the value is rank
     */

    public HashMap<String, Integer> getRanking(int limit) {

        HashMap<String, Integer> userRank = new HashMap<>();

        //Iterate DB Content
        try (DBIterator iterator = db.iterator()) {
            StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(iterator, Spliterator.NONNULL), false) //NONNULL: the entry are all non-null
                    .parallel() // Parallel computation
                    .forEach(entry -> {
                        String key = asString(entry.getKey());
                        // Check if it is the game wins record
                        // user:'username':'game'Wins" -> parts[1] = username
                        System.out.println(key);
                        if (key.contains("points")) {
                            String userWins = asString(entry.getValue());
                            // user:'username':'game'Wins" -> parts[1] = username
                            //user:'username': rank
                            String[] parts = key.split(":");
                            final String username = parts[1];
                            userRank.put(username, parseInt(userWins));
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Utils.sortHashMap(userRank, limit);
    }

    public void close(){
        this.closeDB();
    }

}