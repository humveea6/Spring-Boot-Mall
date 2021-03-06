package com.imooc.mall.utils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created on 2020-11-09
 */
public class SmtpAuthenticator extends Authenticator {
    public SmtpAuthenticator() {
        super();
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        String username = "";
        String password = "";
        if ((username != null) && (username.length() > 0) && (password != null)
                && (password.length() > 0)) {

            return new PasswordAuthentication(username, password);
        }

        return null;
    }
}