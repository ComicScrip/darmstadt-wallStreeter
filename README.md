# darmstadt-wallStreeter

This project consists in 3 exercises that were assigned during this course : https://www.fbi.h-da.de/organisation/personen/burchard-lars-olof/distributed-systems-cnam.html

(Each exercise has been implemented in its own package/folder)

HOW TO RUN :
(For all exercises, we require JDK Version 9)
=== EXERCICSE 1 ===


	1°) Run MainServer.java
	
	2°) Run TCPClient.java
	
	3°) Provide informations asked in the TCPClient console

=== EXERCISE 2 ===

	0°) Requirements :
		- Include following libraries in your project and change the CLASSPATH consequently :
			--> apache-xmlrpc-3.1.3 or higher (https://ws.apache.org/xmlrpc/xmlrpc2/download.html)
	
	1°) Run MainServer.java

	2°) This is possible to run TCPClient.java (in order to add some entry into the logfile.csv)

	3°) Adapt the IP address in PriceService.java if necessary (l.42) and run it

	4°) Provide informations asked in the TCPClient console

=== EXERCISE 3 ===

	0°) Requirements :
		- Include following libraries in your project and change the CLASSPATH consequently :
			--> apacha-activemq-5.15.3 (http://activemq.apache.org/download.html)
		- Include the following jars in your project and change the CLASSPATH consequently :
			--> javax.annotation.jar
			--> javax.ejb.jar
			--> javax.jms.jar
			--> javax.persistence.jar
			--> javax.resource.jar
			--> javax.servlet.jar
			--> javax.servlet.jsp.jar
			--> javax.servlet.jsp.jstl.jar
			--> javax.transaction.jar
	
	1°) First of all, run the ActiveMq service (through command line) :
		- <path-to-lib>/bin/activemq start
	
	2°) Once ActiveMq is running, run MainServer.java
	
	3°) Run NewsTraderPlatform.java
		- this will initiate multiple TCP clients. Some being "zero intelligence" traders (as in exercise 1), and other being Cyclic/Acyclic traders waiting for news.
	
	4°) Run Journalist.java
		- this will start publishig news to the message queue the Cyclic/acyclic traders are subscribed to.
		
	5°) Now you should see asks and bids being sent as good/bad news arrives. In the meantime, you can use the user interface of a "zero intelligence" trader to build your ask/bids as you wish.
