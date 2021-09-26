package servlet;

import database.DBManager;
import database.exception.DBException;
import entity.User;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginTest {
    private static final String path = "/login.jsp";
    @Mock
    private DBManager manager;

    public LoginTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenGetLoginReturnLoginPage() throws ServletException, IOException {
        final Login servlet = new Login();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(request, never()).getSession();
        verify(dispatcher).forward(request, response);
    }
    
}
