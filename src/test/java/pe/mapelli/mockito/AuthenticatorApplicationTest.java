package pe.mapelli.mockito;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticatorApplicationTest {

    private static final String USERNAME = "jmapelli";
    private static final String PASSWORD = "@jmapelli20";

    @Mock
    private AuthenticatorInterface authenticatorMock;

    @InjectMocks
    private AuthenticatorApplication authenticator;

    @Before
    public void setUp() {
        //authenticatorMock = Mockito.mock(AuthenticatorInterface.class);
        //authenticator = new AuthenticatorApplication(authenticatorMock);
    }

    @Test
    public void testReturn() throws EmptyCredentialsException {
        when(this.authenticatorMock.authenticateUser(USERNAME, PASSWORD)).thenReturn(true);

        boolean resutl_false = authenticator.authenticate("user", "123");
        boolean resutl_true = authenticator.authenticate(USERNAME, PASSWORD);

        assertFalse(resutl_false);
        assertTrue(resutl_true);
    }

    @Test
    public void testCallAndParemeters() throws EmptyCredentialsException {
        authenticator.authenticate(USERNAME, PASSWORD);

        verify(authenticatorMock).authenticateUser(USERNAME, PASSWORD);
        //fail verify(authenticatorMock).authenticateUser(USERNAME, "error_password");
    }

    @Test
    public void testTimesCall() throws EmptyCredentialsException {
        authenticator.authenticate(USERNAME, PASSWORD);

        //una vez
        verify(authenticatorMock, times(1)).authenticateUser(USERNAME, PASSWORD);
        //Al menos una vez
        verify(authenticatorMock, atLeastOnce()).authenticateUser(USERNAME, PASSWORD);
        //Al menos x veces
        verify(authenticatorMock, atLeast(1)).authenticateUser(USERNAME, PASSWORD);
        //A lo mucho x veces
        verify(authenticatorMock, atMost(1)).authenticateUser(USERNAME, PASSWORD);
        //Nunca
        //fail verify(authenticatorMock, never()).authenticateUser(USERNAME, PASSWORD);
    }

    @Test
    public void testCallOrder() throws EmptyCredentialsException {
        authenticator.authenticate(USERNAME, PASSWORD);

        InOrder inOrder = inOrder(authenticatorMock);
        inOrder.verify(authenticatorMock).foo();
        inOrder.verify(authenticatorMock).authenticateUser(USERNAME, PASSWORD);

        /* FAIL
        inOrder.verify(authenticatorMock).authenticateUser(USERNAME, PASSWORD);
        inOrder.verify(authenticatorMock).foo();
        */
    }

    @Test
    public void testTimeout() throws EmptyCredentialsException {
        authenticator.authenticate(USERNAME, PASSWORD);

        verify(authenticatorMock, timeout(1)).authenticateUser(USERNAME, PASSWORD);
        verify(authenticatorMock, timeout(1).times(1)).authenticateUser(USERNAME, PASSWORD);
    }

    @Test(expected = EmptyCredentialsException.class)
    public void testException() throws EmptyCredentialsException {
        when(authenticatorMock.authenticateUser("", "")).thenThrow(new EmptyCredentialsException());
        authenticator.authenticate("", "");
    }
}