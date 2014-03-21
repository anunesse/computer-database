package com.excilys.formation.projet.ws;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

//@org.springframework.stereotype.Service
public class MainTest {

	//	@WebServiceRef(wsdlLocation="http://localhost:8080/computer-db-webservice/hello?wsdl")
	//    static HelloWorldWS service;
		
	//	public static void main(String[] args) {
	//		System.out.println("Retrieving the port from the following service: " + service);
	//		if(service != null){
	//			System.out.println("Invoking the sayHello operation on the port : "+service.getHelloWorldAsString());
	//		}
	//		else{
	//			System.out.println("Cannot invoke webservice");
	//		}
	//		
	//	}
	//	@Autowired
	//	private static ComputerService computerService;
	
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://localhost:8090/computer-db-webservice/printcomp?wsdl");

		QName qname = new QName("http://ws.projet.formation.excilys.com/", "ComputerServiceService");
		
		Service service = Service.create(url, qname);

		ComputerWS hello = service.getPort(ComputerWS.class);

		for(String str : hello.readAll())
			System.out.print(str);

		
		//		System.out.println(hello.PrintComputerPage());
		//		for(ComputerDTO cdto : hello.PrintComputerPage()){
		//			System.out.print(cdto.toString());
		//		}
	}
}
