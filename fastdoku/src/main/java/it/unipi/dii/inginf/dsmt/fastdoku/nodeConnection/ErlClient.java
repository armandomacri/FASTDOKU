package it.unipi.dii.inginf.dsmt.fastdoku.nodeConnection;

import com.ericsson.otp.erlang.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ErlClient {
    //node name
    private final OtpNode node;
    //register name
    private final OtpMbox mbox;
    private final String sendTo = "fastdoku_server";
    private final String nodeName;


    public ErlClient() throws IOException{
        this("javaFasdokuNode", "", "");
    }


    /**
     * Constructor
     * @param nodeName represent the node name
     * @param mboxName represente the register node name
     * @param cookie not necessary!
     * @throws IOException
     **/
    public ErlClient(String nodeName, String mboxName, String cookie) throws IOException {
        this.nodeName = nodeName;
        if (cookie.equals(""))
            this.node = new OtpNode(nodeName+"@localhost");
        else
            this.node = new OtpNode(nodeName, cookie);
        this.mbox = this.node.createMbox(mboxName+"_server");
    }

    /**
     * This method add the current user to the list of online users
     * @return return false if the operation fail, true otherwise
     */
    public boolean addAmongOnlineUser() {
        boolean result = true;
        try {
            //add user
            OtpErlangAtom request = new OtpErlangAtom("add");
            OtpErlangString username = new OtpErlangString(this.nodeName);
            OtpErlangTuple outMsg = new OtpErlangTuple(new OtpErlangObject[]{this.mbox.self(), new OtpErlangTuple(new OtpErlangObject[]{request, username})});

            //procRegisterName - NodeName - Message
            this.mbox.send(sendTo, "fastdoku@localhost", outMsg);
            System.out.println("Reply to " + sendTo + " : " + outMsg.toString());
            OtpErlangObject msg = this.mbox.receive();
            OtpErlangAtom ok = (OtpErlangAtom)msg;
            if (!ok.equals(new OtpErlangAtom("ok")))
                result = false;

        } catch (Exception e) {
            result = false;
            //aggiungere logger
            e.printStackTrace();
        }
        return result;
    }

    /**
     * This method remove the current user from the list of online users
     * @return return false if the operation fail, true otherwise
     */
    public boolean removeUser() {
        boolean result = true;
        try {
            //remove user
            OtpErlangAtom request = new OtpErlangAtom("remove");
            OtpErlangString username = new OtpErlangString(this.nodeName);
            OtpErlangTuple outMsg = new OtpErlangTuple(new OtpErlangObject[]{this.mbox.self(), new OtpErlangTuple(new OtpErlangObject[]{request, username})});

            //procRegisterName - NodeName - Message
            this.mbox.send(sendTo, "fastdoku@localhost", outMsg);
            System.out.println("Reply to " + sendTo + " : " + outMsg.toString());
            OtpErlangObject msg = this.mbox.receive(5000);
            OtpErlangAtom ok = (OtpErlangAtom)msg;
            if (!ok.equals(new OtpErlangAtom("ok")))
                result = false;

        } catch (Exception e) {
            result = false;
            //aggiungere logger
            e.printStackTrace();
        }
        return result;
    }

    /**
     * This method send a request in orther to get the online users list
     * @return return an hashmap<Pid, Username>
     */
    public Map<String, String> getOnlineUser(){
        //blocking receive operation (online user list)
        HashMap<String, String> onlineUserList;
        try {
            OtpErlangAtom request = new OtpErlangAtom("get");
            OtpErlangTuple outMsg = new OtpErlangTuple(new OtpErlangObject[]{this.mbox.self(), request});
            this.mbox.send(sendTo, "fastdoku@localhost", outMsg);
            System.out.println("Request to " + sendTo + " : " + outMsg.toString());
            OtpErlangObject msg = this.mbox.receive();
            OtpErlangList erlangList = new OtpErlangList((OtpErlangObject) msg);
            onlineUserList = convertErlangList(erlangList);
            System.out.println("Online user list: " + onlineUserList);
        } catch (Exception e){
            e.printStackTrace();
            onlineUserList = null;
        }
        return onlineUserList;
    }

    /**
     * Support method: convert an erlang list of tuple in an hasmap
     * @param list : list of erlang tuple
     * @return : haspmap<Pid,Username>
     */
    private HashMap<String, String> convertErlangList(OtpErlangList list){
        HashMap<String, String> onlineUser = new HashMap<>();
        int i = 0;
        for (OtpErlangObject l : (OtpErlangList)list.elementAt(0)){
            String username = ((OtpErlangString)l).stringValue();
            onlineUser.put(username, Integer.toString(i));
            i++;
        }
        return onlineUser;
    }


    public static void main(String[] args) throws IOException {
        ErlClient erlClient = new ErlClient();
        //erlClient.addAmongOnlineUser();
        erlClient.getOnlineUser();
    }


}
