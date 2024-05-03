#Java Webservice Project

This is a locally hosted project that allows CRUD operations on doctor and patient information using JAXRS and a local database.  It uses deprecated versions of Java, Tomcat, and ANT and will not run unless the correct versions are available.

#Running:
To be able to compile and run the DrPatientService there are several versioning requirements:
Java 7: 
This version of Java can be downloaded from Oracle’s website here:
	 oracle.com/java/technologies/javase/javase7-archive-downloads.html	
Tomcat 8:
This Tomcat version can be downloaded from Apache’s website here:
	https://tomcat.apache.org/download-80.cgi
Ant 1.9
Ant can also be downloaded from Apache:
	https://ant.apache.org/bindownload.cgi

After these have been installed, you will need to update your PATH variables.  Add a new JAVA_HOME variable in whatever manner is appropriate for your OS.  You must also add the ant bin to your path as well as create an ANT_HOME variable pointed to where Ant is installed.  While you don’t need to set a variable for CATALINA_HOME (located within the Tomcat install directory, you will need to update the value for tomcat.home property within the build.xml file with this location.  It is located in the base directory for the project.

To compile the java files and start the service on the Tomcat server, it must be packaged using an ant script.  From the base of the service project directory (drpatient) run: 
	ant -Dwar.name=drpatient deploy
This command will compile and deploy the service to Tomcat.  Tomcat does not need to be running, but if it is it will automatically detect the changes to the service and deploy without the need to restart the server.

To start Tomcat, locate the bin directory within CATALINA_HOME and run startup.bat.

After the service has been deployed on your server, it is now available to be accessed via web browser or via the curl utility in a terminal.

#Accessing the API

Sample curl calls (These are also available in the curls.txt file):
Base:
  curl localhost:8080/drpatient/resources/

Defaults to listing doctors (xml & plain):
  curl localhost:8080/drpatient/resources/xml
  curl localhost:8080/drpatient/resources/plain

List only patients:
  curl localhost:8080/drpatient/resources/p
  curl localhost:8080/drpatient/resources/xml/p

List individual doctor by ID (xml & plain):
  curl --request GET localhost:8080/drpatient/resources/xml/2
  curl --request GET localhost:8080/drpatient/resources/plain/2

List individual patient by ID (xml & plain):
  curl --request GET localhost:8080/drpatient/resources/xml/p/2
  curl --request GET localhost:8080/drpatient/resources/plain/p/2

Add/create new doctor and add patients
  curl --request POST --data "firstName=Gee&lastName=Pea" localhost:8080/drpatient/resources/create/
  curl --request POST --data "firstName=Hai&lastName=Bludpresshar&insuranceNumber=XX698&doctorId=4" 
	localhost:8080/drpatient/resources/createp/
  curl --request POST --data "firstName=Hypoh&lastName=Kondriak&insuranceNumber=XX698&doctorId=4" 
	localhost:8080/drpatient/resources/createp/

Update doctor's name on existing list (give id and new name):
  curl --request PUT --data "id=1&firstName=Peety" localhost:8080/drpatient/resources/update/

Delete existing doctor without deleting patients on master list:
  curl --request DELETE localhost:8080/drpatient/resources/delete/2

Delete a patient:
  curl --request DELETE localhost:8080/drpatient/resources/deletep/7

Delete existing doctor AND all patients on master list:
  curl --request DELETE localhost:8080/drpatient/resources/deleteAll/3

Update a patient to a different doctor:
  curl --request PUT --data "id=5&doctorId=2" localhost:8080/drpatient/resources/updatep/



