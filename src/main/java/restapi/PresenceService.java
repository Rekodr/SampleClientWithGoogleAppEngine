package restapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.*;
import org.restlet.representation.Representation;
import java.io.IOException;
import java.util.Vector;

/**
 * @author Recodeo Rekod.   Credit to: Jonathan Engelsma (http://themobilemontage.com)
 *
 */

public class PresenceService implements IPresenceService {
    //private static final String APPLICATION_URI = "http://localhost:8080"; //http://vast-lightning-224604.appspot.com
    private static final String APPLICATION_URI  = "http://vast-lightning-224604.appspot.com";

    public PresenceService() {
        super();
    }

    public boolean register(User user) {
        if (user == null) return false;

        Form form = new Form();
        form.add( "username", user.getUsername() );
        form.add( "port", Integer.toString( user.getPort() ) );
        form.add( "status", Boolean.toString( user.getStatus() ) );
        form.add( "host", user.getHost() );

        String usersRessourceURL = APPLICATION_URI + "/users";
        Request request = new Request( Method.POST, usersRessourceURL );

        request.setEntity( form.getWebRepresentation() );

        System.out.println( "Sending an HTTP POST to " + usersRessourceURL + "." );
        Response resp = new org.restlet.Client( Protocol.HTTP ).handle( request );

        if (resp.getStatus().equals( Status.SUCCESS_OK ))
            return true;
        else
            return false;
    }

    public boolean updateUser(User user) {
        if (user == null) {
            return false;
        }

        Form form = new Form();
        form.add( "port", Integer.toString( user.getPort() ) );
        form.add( "status", Boolean.toString( user.getStatus() ) );
        form.add( "host", user.getHost() );

        String usersRessourceURL = APPLICATION_URI + "/users/" + user.getUsername();
        Request request = new Request( Method.PUT, usersRessourceURL );

        request.setEntity( form.getWebRepresentation() );
        Response resp = new org.restlet.Client( Protocol.HTTP ).handle( request );

        if (resp.getStatus().equals( Status.SUCCESS_OK ))
            return true;
        else
            return false;
    }

    public void unregister(String username) {

        String usersRessourceURL = APPLICATION_URI + "/users/" + username;
        Request request = new Request( Method.DELETE, usersRessourceURL );

        request.getClientInfo().getAcceptedMediaTypes().
                add( new Preference( MediaType.APPLICATION_JSON ) );

        Response resp = new org.restlet.Client( Protocol.HTTP ).handle( request );

        if (resp.getStatus().equals( Status.SUCCESS_OK )) {
            System.out.println( "User deleted on server" );
        }
    }

    public User lookup(String name) {
        if (name == null) {
            return null;
        }
        User user = null;

        String usersRessourceURL = APPLICATION_URI + "/users/" + name;
        Request request = new Request( Method.GET, usersRessourceURL );

        request.getClientInfo().getAcceptedMediaTypes().
                add( new Preference( MediaType.APPLICATION_JSON ) );

        Response resp = new org.restlet.Client( Protocol.HTTP ).handle( request );
        Representation responseData = resp.getEntity();

        if (resp.getStatus().equals( Status.SUCCESS_OK )) {
            responseData = resp.getEntity();
            try {
                String jsonString = responseData.getText();
                JSONObject jsonObject = new JSONObject( jsonString );
                user = new User( jsonObject );
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException je) {
                je.printStackTrace();
            }
        }

        return user;
    }

    public Vector<User> listRegisteredUsers() {
        Vector<User> v = new Vector<User>();

        String usersRessourceURL = APPLICATION_URI + "/users";
        Request request = new Request( Method.GET, usersRessourceURL );

        request.getClientInfo().getAcceptedMediaTypes().
                add( new Preference( MediaType.APPLICATION_JSON ) );

        Response resp = new org.restlet.Client( Protocol.HTTP ).handle( request );
        Representation responseData = resp.getEntity();

        if (resp.getStatus().equals( Status.SUCCESS_OK )) {
            responseData = resp.getEntity();
            try {
                String jsonString = responseData.getText();
                JSONArray jsonArray = new JSONArray( jsonString );

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject( i );
                    User user = new User( jsonObject );
                    v.add( user );
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException je) {
                je.printStackTrace();
            }
        }

        return v;
    }
}

