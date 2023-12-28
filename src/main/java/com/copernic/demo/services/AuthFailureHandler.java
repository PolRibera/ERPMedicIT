package com.copernic.demo.services;


import com.copernic.demo.dao.UsuariDAO;
import com.copernic.demo.domain.Usuari;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author PolRib pero basado en el del ruben xd
 */
@Component
@Slf4j
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler  {

    @Autowired
    private UsuariDAO userDao;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        log.info(username);
        Usuari user = userDao.findByUsername(username);

        if (user == null) { //Si no existeix l'usuari...

            //Llancem una excepció de tipus UsernameNotFoundException
            throw new UsernameNotFoundException(username);

        }else{
            if (user.getRol().getNom().equals("admin")) {
                exception = new LockedException("La seva contrasenya es incorrecta, pero com al ser admin no perd intents, aixi que no es bloqueja");
            } else {
                if (user.getIntents() > 0) {
                    int intents = user.getIntents() - 1;
                    user.setIntents(intents);
                    userDao.save(user);
                    exception = new LockedException("La seva contrasenya es incorrecta, ha perdut un intent et queden: " + user.getIntents());
                }
                else if(user.getIntents()== 0){
                    exception = new LockedException("El seu usuari s'ha quedat sin intents y esta bloquejat, contacti amb un administrador per desbloquejar-lo");
                }
            }

        }super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }

}
