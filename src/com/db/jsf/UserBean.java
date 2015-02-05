package com.db.jsf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.primefaces.context.RequestContext;

@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean {

	private String userName;
	private String password;
	private String errMsg;
	private String FromCurrency;
	private String ToCurrency;
	private Date date;
	

	private List<String> currencyList = new ArrayList<String>();
	
	
	
	public String verifyUser() {
//		try {
//			sendMail("text","222","dreamzsoft@outlook.com","ankur.ggrwl1@gmail.com");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		String result = null;
		date = new Date();
		if(userName == null || userName == "" || userName == " "){
		FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Message Displayed Growl","Error Message Displayed Growl"));     	
			
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Error", "Personal Information Section contains errors.") );
		
		}
		if (userName.equals(password)) {
			result = "home";
			System.out.println("password match!!");
		} else {
			System.out.println("password doesn't match!!");
			result = "login";
			setErrMsg("Invalid User Name or Password");
		}
		return result;
	}
	
	
	
//		Properties props = new Properties();
//		props.put("mail.smtp.host", "smtp.gmail.com");
//		props.put("mail.smtp.socketFactory.port", "465");
//		props.put("mail.smtp.socketFactory.class",
//				"javax.net.ssl.SSLSocketFactory");
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.port", "587");
//		
////		props.put("mail.smtp.starttls.enable", "true"); 
//		Session session = Session.getDefaultInstance(props,
//			new javax.mail.Authenticator() {
//				protected PasswordAuthentication getPasswordAuthentication() {
//					return new PasswordAuthentication("ankur.ggrwl1@gmail.com","9810994067");
//				}
//			});
// 
//		try {
// 
//			Message message = new MimeMessage(session);
//			message.setFrom(new InternetAddress("ankur.ggrwl1@gmail.com"));
//			message.setRecipients(Message.RecipientType.TO,
//					InternetAddress.parse("ankur.ggrwl1@gmail.com"));
//			message.setSubject("Testing Subject");
//			message.setText("Dear Mail Crawler," +
//					"\n\n No spam to my email, please!");
// 
//			Transport.send(message);
// 
//			System.out.println("Done");
// 
//		} catch (MessagingException e) {
//			throw new RuntimeException(e);
//		}
		
//		String  d_email = "ankur.ggrwl1",
//	            d_uname = "ankur.ggrwl1",
//	            d_password = "9810994067",
//	            d_host = "smtp.gmail.com",
//	            d_port  = "465",
//	            m_to = "ankur.ggrwl1@gmail.com",
//	            m_subject = "Indoors Readable File: ",
//	            m_text = "This message is from Indoor Positioning App. Required file(s) are attached.";
//	    Properties props = new Properties();
//	    props.put("mail.smtp.user", d_email);
//	    props.put("mail.smtp.host", d_host);
//	    props.put("mail.smtp.port", d_port);
//	    props.put("mail.smtp.starttls.enable","true");
//	    props.put("mail.smtp.debug", "true");
//	    props.put("mail.smtp.auth", "true");
//	    props.put("mail.smtp.socketFactory.port", d_port);
//	    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//	    props.put("mail.smtp.socketFactory.fallback", "false");
//
//   
////	    Authenticator auth = new SMTPa
////	    Session session = Session.getInstance(props, auth);
//	    Session session = Session.getInstance(props);
//	    session.setDebug(true);
//
//	    MimeMessage msg = new MimeMessage(session);
//	    try {
//	        msg.setSubject(m_subject);
//	        msg.setFrom(new InternetAddress(d_email));
//	        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(m_to));
//
//	Transport transport = session.getTransport("smtps");
//	            transport.connect(d_host, Integer.valueOf(d_port), d_uname, d_password);
//	            transport.sendMessage(msg, msg.getAllRecipients());
//	            transport.close();
//
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
		
		 
	     
	     public void sendMail(String subject, String body, String sender, String recipients) 
	                                                                                                       throws Exception 
	     {     
	          
//	          Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	          String mailhost = "smtp-mail.outlook.com";
	          Properties props = new Properties();
	          props.setProperty("mail.transport.protocol", "smtp");
	          props.setProperty("mail.host", mailhost);
	          props.put("mail.smtp.auth", "true");
	          props.put("mail.smtp.port", "25");
//	          props.put("mail.smtp.socketFactory.port", "465");
	          props.put("mail.smtp.socketFactory.class",
	          "javax.net.ssl.SSLSocketFactory");
	          props.put("mail.smtp.socketFactory.fallback", "false");
	          props.setProperty("mail.smtp.quitwait", "false");

	          Session session = Session.getDefaultInstance(props,
	                    new javax.mail.Authenticator() 
	          {
	               protected PasswordAuthentication getPasswordAuthentication()
	               { return new PasswordAuthentication("dreamzsoft@outlook.com","Chhavi@123");     }
	          });          

	          MimeMessage message = new MimeMessage(session);
	          message.setSender(new InternetAddress(sender));
	          message.setSubject(subject);
	          message.setContent(body, "text/plain");
	          if (recipients.indexOf(',') > 0) 
	                         message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
	          else
	                         message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));

	          
	          Transport.send(message);
		
	}	
	public void showDialoge(){
		System.out.println("Calling Dialoge..");
		RequestContext.getCurrentInstance().execute("PF('dlg').show();");
	}

	public String gotoLogin() {
		setErrMsg("");
		return "login";
	}

	public void updateSelection(String updateSelection){
		System.out.println("Listner Called With "+updateSelection);
		if(updateSelection.contains("from")){
			RequestContext.getCurrentInstance().update("niitForm:tab:selectedFromCurrency");
		}else if(updateSelection.contains("to")){
			RequestContext.getCurrentInstance().update("niitForm:tab:selectedToCurrency");
		}
		
	}
	
	public String getUserName() {
		
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public List<String> getCurrencyList() {
		if(currencyList == null){
			currencyList = new ArrayList<String>();
		}
		if(currencyList != null && currencyList.size() == 0){
			currencyList.add("INR");
			currencyList.add("THB");
		}
		return currencyList;
	}

	public void setCurrencyList(List<String> currencyList) {
		this.currencyList = currencyList;
	}

	public String getFromCurrency() {
		return FromCurrency;
	}

	public void setFromCurrency(String fromCurrency) {
		FromCurrency = fromCurrency;
	}

	public String getToCurrency() {
		return ToCurrency;
	}

	public void setToCurrency(String toCurrency) {
		ToCurrency = toCurrency;
	}
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


}
