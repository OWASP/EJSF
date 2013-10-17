package ch.security4web.esapi.testing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *  * OWASP Enterprise Security API (ESAPI)
 * 
 * This file is part of the Open Web Application Security Project (OWASP)
 * Enterprise Security API (ESAPI) project. For details, please see
 * <a href="http://www.owasp.org/index.php/ESAPI">http://www.owasp.org/index.php/ESAPI</a>.
 *
 * Copyright (c) 2007 - The OWASP Foundation
 * 
 * The ESAPI is published by OWASP under the BSD license. You should read and accept the
 * LICENSE before you use, modify, and/or redistribute this software.
 * 
 * @author      Rakesh
 * @author      Dr.Prof.Emmanuel
 * @author 		Matthey Samuel
 * @version     2.0
 * @date		07/05/2013
 * @since       1.0
 */

public class CSRFSessionListener implements HttpSessionListener {

    private final static String CSRFTOKEN_NAME = "CSRFTOKEN_NAME_FOR_APP";

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        String randomId = generateRandomId();
        session.setAttribute(CSRFTOKEN_NAME, randomId);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        //nothing to do here
    }

    public String generateRandomId() {
//http://www.javapractices.com/topic/TopicAction.do?Id=56
        SecureRandom random = null;
        byte[] randomDigest = null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            randomDigest = sha.digest(new Integer(random.nextInt()).toString().getBytes());

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CSRFSessionListener.class.getName()).log(Level.SEVERE, "NoSuchAlgorithmException", ex);
        }
        Logger.getLogger(CSRFSessionListener.class.getName()).log(Level.FINEST, "Random Digest for session: {0}", hexEncode(randomDigest));


        return hexEncode(randomDigest);
    }

    /**
     * The byte[] returned by MessageDigest does not have a nice
     * textual representation, so some form of encoding is usually performed.
     *
     * This implementation follows the example of David Flanagan's book
     * "Java In A Nutshell", and converts a byte array into a String
     * of hex characters.
     *
     * Another popular alternative is to use a "Base64" encoding.
     */
    static private String hexEncode(byte[] aInput) {
        StringBuilder result = new StringBuilder();
        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (int idx = 0; idx < aInput.length; ++idx) {
            byte b = aInput[idx];
            result.append(digits[ (b & 0xf0) >> 4]);
            result.append(digits[ b & 0x0f]);
        }
        return result.toString();
    }
}
