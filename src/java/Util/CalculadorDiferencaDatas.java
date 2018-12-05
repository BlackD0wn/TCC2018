/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.util.Date;

/**
 *
 * @author hook
 */
public class CalculadorDiferencaDatas {

    public int diferencaDeDatas(Date inicio, Date fim) {

        long dt = (inicio.getTime() - fim.getTime()); // 1 hora para compensar horário de verão
        int dias = (int) (dt / 86400000L); // passaram-se 67111 dias

        if (dias == 0) {
            dias = 1;
        }

        return dias;

    }

}
