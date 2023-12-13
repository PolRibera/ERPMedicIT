package com.copernic.demo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Albert Grau
 */
public class EncriptadorContrasenya {

    /**
     * Mètode que encripta la contrasenya que passem per paràmetre
     * @param password
     * @return
     */

    public static String encriptarContrasenya(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
