package com.db.jsf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
	private String showClient="hide";
	private String errMsg;
	private String FromCurrency;
	private String ToCurrency;
	private Date date;
	private int activeIndex=0;
	private clientInfo clientInfoObj;
	private List<clientInfo> clientList;
	private boolean addClient;
	private boolean currencyClient;

	private List<String> currencyList = new ArrayList<String>();


	public void callAddClient(){
		addClient = true;
		currencyClient = false;
	}
	public void callCurrencyConvertor(int activeTab){
		activeIndex = activeTab;
		currencyClient = true;
		addClient = false;
	}
	
	public void showClient(){
		System.out.println("Modifying Status from "+showClient+" to ");
		if(showClient.contains("hide")){
			System.out.println("show");
			showClient="show";
		}else if(showClient.contains("show")){
			System.out.println("hide");
			showClient="hide";
		}
	}
	public String verifyUser() {
		String result = null;
		date = new Date();
		reset();

		if (userName != null && userName.equals(password)) {
			result = "home";
			System.out.println("password match!!");
		} else {
			System.out.println("password doesn't match!!");
			result = "login";
			setErrMsg("Invalid User Name or Password");
		}
		return result;
	}

	public void addClient(){
		System.out.println("Adding Client... "+clientInfoObj.Name);
		getClientList().add(clientInfoObj);
		clientInfoObj = null;
		getClientInfoObj();
	}
	

	public void sendMail(String subject, String body, String sender,
			String recipients) throws Exception {

		// Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		String mailhost = "smtp-mail.outlook.com";
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", mailhost);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "25");
		// props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.quitwait", "false");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								"dreamzsoft@outlook.com", "Chhavi@123");
					}
				});

		MimeMessage message = new MimeMessage(session);
		message.setSender(new InternetAddress(sender));
		message.setSubject(subject);
		message.setContent(body, "text/plain");
		if (recipients.indexOf(',') > 0)
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipients));
		else
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(
					recipients));

		Transport.send(message);

	}

	public void showDialoge() {
		System.out.println("Calling Dialoge..");
		RequestContext.getCurrentInstance().execute("PF('dlg').show();");
	}
	
	public void reset(){
		showClient="hide";
		activeIndex = 0;
		currencyClient = false;
		addClient = false;
		clientInfoObj = null;
		
	}
	
	public String handleCommand(String command, String[] params) {
        if(command.equals("greet")) {
            if(params.length > 0)
                return "Hello " + params[0];
            else
                return "Hello Stranger";
        }
        else if(command.equals("date"))
            return new Date().toString();
        else if(command.equals("user"))
            return userName;
        else if(command.equals("list")){
        	String clientName="";
        	for(clientInfo clients:getClientList()){
        		clientName = clientName + " "+clients.Name + ",";
        	}
        	int len = clientName.length();
        	clientName = clientName.substring(0, len-1);
        	clientName = clientName + ".";
            return clientName;
        }
        else
            return command + " not found";
    }

	public String gotoLogin() {
		setErrMsg("");
		System.out.println("Logout");
		return "login";
	}

	public void updateSelection(String updateSelection) {
		System.out.println("Listner Called With " + updateSelection);
		if (updateSelection.contains("from")) {
			RequestContext.getCurrentInstance().update(
					"niitForm:tab:selectedFromCurrency");
		} else if (updateSelection.contains("to")) {
			RequestContext.getCurrentInstance().update(
					"niitForm:tab:selectedToCurrency");
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
		if (currencyList == null) {
			currencyList = new ArrayList<String>();
		}
		if (currencyList != null && currencyList.size() == 0) {
			currencyList.add("AFA-Afghanistan Afghani");
			currencyList.add("ALL-Albanian Lek");
			currencyList.add("DZD-Algerian Dinar");
			currencyList.add("ARS-Argentine Peso");
			currencyList.add("AWG-Aruba Florin");
			currencyList.add("AUD-Australian Dollar");
			currencyList.add("BSD-Bahamian Dollar");
			currencyList.add("BHD-Bahraini Dinar");
			currencyList.add("BDT-Bangladesh Taka");
			currencyList.add("BBD-Barbados Dollar");
			currencyList.add("BZD-Belize Dollar");
			currencyList.add("BMD-Bermuda Dollar");
			currencyList.add("BTN-Bhutan Ngultrum");
			currencyList.add("BOB-Bolivian Boliviano");
			currencyList.add("BWP-Botswana Pula");
			currencyList.add("BRL-Brazilian Real");
			currencyList.add("GBP-British Pound");
			currencyList.add("BND-Brunei Dollar");
			currencyList.add("BIF-Burundi Franc");
			currencyList.add("XOF-CFA Franc (BCEAO)");
			currencyList.add("XAF-CFA Franc (BEAC)");
			currencyList.add("KHR-Cambodia Riel");
			currencyList.add("CAD-Canadian Dollar");
			currencyList.add("CVE-Cape Verde Escudo");
			currencyList.add("KYD-Cayman Islands Dollar");
			currencyList.add("CLP-Chilean Peso");
			currencyList.add("CNY-Chinese Yuan");
			currencyList.add("COP-Colombian Peso");
			currencyList.add("KMF-Comoros Franc");
			currencyList.add("CRC-Costa Rica Colon");
			currencyList.add("HRK-Croatian Kuna");
			currencyList.add("CUP-Cuban Peso");
			currencyList.add("CYP-Cyprus Pound");
			currencyList.add("CZK-Czech Koruna");
			currencyList.add("DKK-Danish Krone");
			currencyList.add("DJF-Dijibouti Franc");
			currencyList.add("DOP-Dominican Peso");
			currencyList.add("XCD-East Caribbean Dollar");
			currencyList.add("EGP-Egyptian Pound");
			currencyList.add("SVC-El Salvador Colon");
			currencyList.add("EEK-Estonian Kroon");
			currencyList.add("ETB-Ethiopian Birr");
			currencyList.add("EUR-Euro");
			currencyList.add("FKP-Falkland Islands Pound");
			currencyList.add("GMD-Gambian Dalasi");
			currencyList.add("GHC-Ghanian Cedi");
			currencyList.add("GIP-Gibraltar Pound");
			currencyList.add("XAU-Gold Ounces");
			currencyList.add("GTQ-Guatemala Quetzal");
			currencyList.add("GNF-Guinea Franc");
			currencyList.add("GYD-Guyana Dollar");
			currencyList.add("HTG-Haiti Gourde");
			currencyList.add("HNL-Honduras Lempira");
			currencyList.add("HKD-Hong Kong Dollar");
			currencyList.add("HUF-Hungarian Forint");
			currencyList.add("ISK-Iceland Krona");
			currencyList.add("INR-Indian Rupee");
			currencyList.add("IDR-Indonesian Rupiah");
			currencyList.add("IQD-Iraqi Dinar");
			currencyList.add("ILS-Israeli Shekel");
			currencyList.add("JMD-Jamaican Dollar");
			currencyList.add("JPY-Japanese Yen");
			currencyList.add("JOD-Jordanian Dinar");
			currencyList.add("KZT-Kazakhstan Tenge");
			currencyList.add("KES-Kenyan Shilling");
			currencyList.add("KRW-Korean Won");
			currencyList.add("KWD-Kuwaiti Dinar");
			currencyList.add("LAK-Lao Kip");
			currencyList.add("LVL-Latvian Lat");
			currencyList.add("LBP-Lebanese Pound");
			currencyList.add("LSL-Lesotho Loti");
			currencyList.add("LRD-Liberian Dollar");
			currencyList.add("LYD-Libyan Dinar");
			currencyList.add("LTL-Lithuanian Lita");
			currencyList.add("MOP-Macau Pataca");
			currencyList.add("MKD-Macedonian Denar");
			currencyList.add("MGF-Malagasy Franc");
			currencyList.add("MWK-Malawi Kwacha");
			currencyList.add("MYR-Malaysian Ringgit");
			currencyList.add("MVR-Maldives Rufiyaa");
			currencyList.add("MTL-Maltese Lira");
			currencyList.add("MRO-Mauritania Ougulya");
			currencyList.add("MUR-Mauritius Rupee");
			currencyList.add("MXN-Mexican Peso");
			currencyList.add("MDL-Moldovan Leu");
			currencyList.add("MNT-Mongolian Tugrik");
			currencyList.add("MAD-Moroccan Dirham");
			currencyList.add("MZM-Mozambique Metical");
			currencyList.add("MMK-Myanmar Kyat");
			currencyList.add("NAD-Namibian Dollar");
			currencyList.add("NPR-Nepalese Rupee");
			currencyList.add("ANG-Neth Antilles Guilder");
			currencyList.add("NZD-New Zealand Dollar");
			currencyList.add("NIO-Nicaragua Cordoba");
			currencyList.add("NGN-Nigerian Naira");
			currencyList.add("KPW-North Korean Won");
			currencyList.add("NOK-Norwegian Krone");
			currencyList.add("OMR-Omani Rial");
			currencyList.add("XPF-Pacific Franc");
			currencyList.add("PKR-Pakistani Rupee");
			currencyList.add("XPD-Palladium Ounces");
			currencyList.add("PAB-Panama Balboa");
			currencyList.add("PGK-Papua New Guinea Kina");
			currencyList.add("PYG-Paraguayan Guarani");
			currencyList.add("PEN-Peruvian Nuevo Sol");
			currencyList.add("PHP-Philippine Peso");
			currencyList.add("XPT-Platinum Ounces");
			currencyList.add("PLN-Polish Zloty");
			currencyList.add("QAR-Qatar Rial");
			currencyList.add("ROL-Romanian Leu");
			currencyList.add("RUB-Russian Rouble");
			currencyList.add("WST-Samoa Tala");
			currencyList.add("STD-Sao Tome Dobra");
			currencyList.add("SAR-Saudi Arabian Riyal");
			currencyList.add("SCR-Seychelles Rupee");
			currencyList.add("SLL-Sierra Leone Leone");
			currencyList.add("XAG-Silver Ounces");
			currencyList.add("SGD-Singapore Dollar");
			currencyList.add("SKK-Slovak Koruna");
			currencyList.add("SIT-Slovenian Tolar");
			currencyList.add("SBD-Solomon Islands Dollar");
			currencyList.add("SOS-Somali Shilling");
			currencyList.add("ZAR-South African Rand");
			currencyList.add("LKR-Sri Lanka Rupee");
			currencyList.add("SHP-St Helena Pound");
			currencyList.add("SDD-Sudanese Dinar");
			currencyList.add("SRG-Surinam Guilder");
			currencyList.add("SZL-Swaziland Lilageni");
			currencyList.add("SEK-Swedish Krona");
			currencyList.add("TRY-Turkey Lira");
			currencyList.add("CHF-Swiss Franc");
			currencyList.add("SYP-Syrian Pound");
			currencyList.add("TWD-Taiwan Dollar");
			currencyList.add("TZS-Tanzanian Shilling");
			currencyList.add("THB-Thai Baht");
			currencyList.add("TOP-Tonga Pa'anga");
			currencyList.add("TTD-Trinidad&amp;Tobago Dollar");
			currencyList.add("TND-Tunisian Dinar");
			currencyList.add("TRL-Turkish Lira");
			currencyList.add("USD-U.S. Dollar");
			currencyList.add("AED-UAE Dirham");
			currencyList.add("UGX-Ugandan Shilling");
			currencyList.add("UAH-Ukraine Hryvnia");
			currencyList.add("UYU-Uruguayan New Peso");
			currencyList.add("VUV-Vanuatu Vatu");
			currencyList.add("VEB-Venezuelan Bolivar");
			currencyList.add("VND-Vietnam Dong");
			currencyList.add("YER-Yemen Riyal");
			currencyList.add("YUM-Yugoslav Dinar");
			currencyList.add("ZMK-Zambian Kwacha");
			currencyList.add("ZWD-Zimbabwe Dollar");

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

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}
	
	public clientInfo getClientInfoObj() {
		if(clientInfoObj == null){
			clientInfoObj = new clientInfo();
		}
		return clientInfoObj;
	}

	public void setClientInfoObj(clientInfo clientInfoObj) {
		this.clientInfoObj = clientInfoObj;
	}

	public List<clientInfo> getClientList() {
		if(clientList == null){
			clientList = new ArrayList<clientInfo>();
		}
		return clientList;
	}

	public void setClientList(List<clientInfo> clientList) {
		this.clientList = clientList;
	}

	public String getShowClient() {
		return showClient;
	}

	public void setShowClient(String showClient) {
		this.showClient = showClient;
	}

	public boolean isAddClient() {
		return addClient;
	}
	public void setAddClient(boolean addClient) {
		this.addClient = addClient;
	}

	public boolean isCurrencyClient() {
		return currencyClient;
	}

	public void setCurrencyClient(boolean currencyClient) {
		this.currencyClient = currencyClient;
	}

	public class clientInfo{
		private String Name;
		private String City;
		private String phone;
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
		public String getCity() {
			return City;
		}
		public void setCity(String city) {
			City = city;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
	}

}
