/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import java.util.Date;

/**
 *
 * @author hook
 */
public class Data {
    public static class dataS{
        private static Date data;

        public static Date getData() {
            return data;
        }

        public static void setData(Date data) {
            dataS.data = data;
        }

    
        
    }
    
}
