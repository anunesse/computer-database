package com.excilys.formation.projet.ws;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.excilys.formation.projet.dto.ComputerDTO;

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
	
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://localhost:8080/computer-db-webservice/printcomp?wsdl");
		//1st argument service URI, refer to wsdl document above
		//2nd argument is service name, refer to wsdl document above
		QName qname = new QName("http://impl.ws.projet.formation.excilys.com/", "PrintComputersWSImplService");
		
		Service service = Service.create(url, qname);
		 
		PrintComputersWS hello = service.getPort(PrintComputersWS.class);
		
		System.out.println(hello.PrintComputerPage());
		for(ComputerDTO cdto : hello.PrintComputerPage()){
			System.out.print(cdto.toString());
		}
	}
}
