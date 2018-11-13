/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Slaves;

import bean.Configuracao;
import bean.Fornecedor;
import bean.Pedido;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author hook
 */
public class EnviarEmail {

    private Properties props;
    private Session session;
    private Message message;
    private Address[] toUser;
    private Configuracao config;
    private Fornecedor fornecedor;
    
    public EnviarEmail(Configuracao c, Fornecedor d) throws MessagingException{
            
            props = new Properties();
        
            this.config = c;
            this.fornecedor = d;
        
            props.put("mail.smtp.host", config.getSmtp());
            props.put("mail.smtp.socketFactory.port", config.getPorta());
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", config.getPorta());
 
            session = Session.getDefaultInstance(props,
                        new javax.mail.Authenticator() {
                             protected PasswordAuthentication getPasswordAuthentication() 
                             {
                                   return new PasswordAuthentication(c.getEmail(), c.getSenha());
//                                   return new PasswordAuthentication("anjobruno92@gmail.com", "aygtjutnafgxgnpk");
                             }
                        });
             session.setDebug(true);
             
             message = new MimeMessage(session);
             message.setFrom(new InternetAddress(c.getEmail())); //Remetente
 
    
    }
    
    private void adicionaDestinatario(String email) throws AddressException, MessagingException{
        toUser = InternetAddress.parse(email);
        message.setRecipients(Message.RecipientType.TO, toUser);
    }
    
    private void preparaEmail(String assunto, String corpo) throws MessagingException{
                  message.setSubject(assunto);//Assunto
                  message.setText(corpo);
                
    }
    
    private void enviaEmail() throws MessagingException{
          /**Método para enviar a mensagem criada*/
          Transport.send(message);
    }
    
    public void enviarEmail(Pedido p){
        try {
            adicionaDestinatario(fornecedor.getEmail());
            preparaEmail(p.getAssunto(), p.getCorpo());
            enviaEmail();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}
