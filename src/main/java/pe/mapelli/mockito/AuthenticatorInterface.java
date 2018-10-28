package pe.mapelli.mockito;

public interface AuthenticatorInterface {

    boolean authenticateUser(String username, String password) throws EmptyCredentialsException;

    void foo();

}
