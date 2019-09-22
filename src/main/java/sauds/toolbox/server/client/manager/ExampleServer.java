/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sauds.toolbox.server.client.manager;

import java.io.IOException;

/**
 *
 * @author saud
 */
public class ExampleServer {
    
    public static void main(String[] args) throws IOException {
        Server s = new Server("", 8888/*, new LoginCheckerInterface() {
            @Override
            public boolean isValid(String username, String password) {
                return username.equalsIgnoreCase("saud") && password.equals("1234");
            }
        }*/);
        s.start();
    }
    
}
