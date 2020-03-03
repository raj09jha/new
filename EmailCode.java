
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

public class EmailCode {

   public static void check(String host, String storeType, String user,
      String password) 
   {
      try {

    	//create properties field
          Properties properties = new Properties();

          properties.put("mail.pop3.host", host);
          properties.put("mail.pop3.port", "995");
          properties.put("mail.pop3.starttls.enable", "true");
          Session emailSession = Session.getDefaultInstance(properties);
      
          //create the POP3 store object and connect with the pop server
          Store store = emailSession.getStore("pop3s");

          store.connect(host, user, password);

          //create the folder object and open it
          Folder emailFolder = store.getFolder("INBOX");
          emailFolder.open(Folder.READ_ONLY);

          // retrieve the messages from the folder in an array and print it
          Message[] messages = emailFolder.getMessages();
          System.out.println("messages.length---" + messages.length);

          for (int i = 0, n = messages.length; i < n; i++) {
             Message message = messages[i];
             System.out.println("---------------------------------");
             System.out.println("Email Number " + (i + 1));
             System.out.println("Subject: " + message.getSubject());
             System.out.println("From: " + message.getFrom()[0]);
             System.out.println("Text: " + message.getContent().toString());

          }

          //close the store and folder objects
          emailFolder.close(false);
          store.close();

          } catch (NoSuchProviderException e) {
             e.printStackTrace();
          } catch (MessagingException e) {
             e.printStackTrace();
          } catch (Exception e) {
             e.printStackTrace();
          }
      }

   public static void main(String[] args) {

      String host = "mail.ad.crisil.com";// change accordingly
      String mailStoreType = "pop3";
      String username = "raj.jha@ext-crisil.com";// change accordingly
      String password = "2771998yo!";// change accordingly

      //check(host, mailStoreType, username, password);
      String protocol="imaps";
      Properties props = new Properties();
      props.setProperty("mail.store.protocol", protocol);
                       
      //extra codes required for reading OUTLOOK mails during IMAP-start
          props.setProperty("mail.imaps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
          props.setProperty("mail.imaps.socketFactory.fallback", "false");
          props.setProperty("mail.imaps.port", "993");
          props.setProperty("mail.imaps.socketFactory.port", "993");
      //extra codes required for reading OUTLOOK mails during IMAP-end
                       
          try {
        	  Session session = Session.getDefaultInstance(props, null);
              Store store = session.getStore(protocol);
              store.connect(host, username, password);
              Folder inbox = store.getFolder("INBOX");
              inbox.open(Folder.READ_WRITE);
               
              //search for all "unseen" messages
              Flags seen = new Flags(Flags.Flag.SEEN);
              FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
              Message messages[] = inbox.search(unseenFlagTerm);
              System.out.println(messages);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
     

   }

}