package com.cyberark.conjur.api;

import com.cyberark.conjur.util.Properties;

/**
 * Stores credentials for a Conjur identity.
 *
 * <p>A Conjur identity consists of a username and a password.  The password
 * may either be a user's password or the api key of a user or host.  These
 * cases are treated identically by the Conjur authentication service.</p>
 *
 * <p>Credentials supports hashCode and equals and can be used as keys, for
 * example for caching.</p>
 */
public class Credentials {
    private static final String CONJUR_AUTHN_LOGIN_PROPERTY = "CONJUR_AUTHN_LOGIN";
    private static final String CONJUR_AUTHN_API_KEY_PROPERTY = "CONJUR_AUTHN_API_KEY";
    private static final String CONJUR_AUTHN_URL_PROPERTY = "CONJUR_AUTHN_URL";
    private static final String CONJUR_APPLIANCE_URL_PROPERTY = "CONJUR_APPLIANCE_URL";

    private String username;
    private String password;
    private String authnUrl;

    /**
     * @param username the username/login for this Conjur identity
     * @param password the password or api key for this Conjur identity
     */
    public Credentials(String username, String password) {
        this(username, password, 
           Properties.getMandatoryProperty(CONJUR_AUTHN_URL_PROPERTY,
           Properties.getMandatoryProperty(CONJUR_APPLIANCE_URL_PROPERTY) + "/authn"));
    }

    /**
     * @param username the username/login for this Conjur identity
     * @param password the password or api key for this Conjur identity
     * @param authnUrl the conjur authentication url
     */
    public Credentials(String username, String password, String authnUrl) {
        this.username = username;
        this.password = password;
        this.authnUrl = authnUrl;
    }

    /**
     * Creates a Credentials instance from the system properties
     * {@link Credentials#CONJUR_AUTHN_LOGIN_PROPERTY} and
     * {@link Credentials#CONJUR_AUTHN_API_KEY_PROPERTY}
     * @return the credentials stored in the system property.
     */
    public static Credentials fromSystemProperties(){
        String login = Properties.getMandatoryProperty(CONJUR_AUTHN_LOGIN_PROPERTY);
        String apiKey = Properties.getMandatoryProperty(CONJUR_AUTHN_API_KEY_PROPERTY);
        String applianceUrl = Properties.getMandatoryProperty(CONJUR_APPLIANCE_URL_PROPERTY);
        String authnUrl = Properties.getMandatoryProperty(CONJUR_AUTHN_URL_PROPERTY, applianceUrl + "/authn");

        return new Credentials(login, apiKey, authnUrl);
    }

    /**
     * @return the username/login for this Conjur identity
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the api key or password for this Conjur identity
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the service id of this Conjur identity
     */
    public String getAuthnUrl() {
        return authnUrl;
    }

    public String toString(){
        return username + ":" + password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof com.cyberark.conjur.api.Credentials)) return false;

        com.cyberark.conjur.api.Credentials that = (com.cyberark.conjur.api.Credentials) o;

        if (!password.equals(that.getPassword())) return false;
        if (!username.equals(that.getUsername())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
