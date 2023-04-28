package drpatient;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.servlet.ServletContext;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/")
public class DrPatientRS {
    @Context 
    private ServletContext sctx;          	// dependency injection
    private static Staff staff; 			// set in populate()  
	private static PatientList patientList; //set in populate()

    public DrPatientRS() { }
	
	// GETs will return doctor & patient data by default
	//to get only patient data use additional /p/

    @GET
    @Path("/xml")
    @Produces({MediaType.APPLICATION_XML}) 
    public Response getXml() {
		checkContext();
		return Response.ok(staff, "application/xml").build();
    }
	
	@GET
    @Path("/xml/p")
    @Produces({MediaType.APPLICATION_XML}) 
    public Response getXml() {
		checkContext();
		return Response.ok(patientList, "application/xml").build();
    }

    @GET
    @Path("/xml/{id: \\d+}")
    @Produces({MediaType.APPLICATION_XML}) 
    public Response getXml(@PathParam("id") int id) {
		checkContext();
		return toRequestedType(id, "application/xml");
    }
	
	// patient xml by id
	@GET
    @Path("/xml/p/{id: \\d+}")
    @Produces({MediaType.APPLICATION_XML}) 
    public Response getXml(@PathParam("id") int id) {
		checkContext();
		return toRequestedTypeP(id, "application/xml");
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json")
    public Response getJson() {
		checkContext();
		return Response.ok(toJson(staff), "application/json").build();
    }

    @GET    
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/json/{id: \\d+}")
    public Response getJson(@PathParam("id") int id) {
		checkContext();
		return toRequestedType(id, "application/json");
    }

	@GET
	@Path("")
	@Produces({MediaType.TEXT_PLAIN})
	public String getNone() {
		checkContext();
		return staff.toString();
		
	}
	
    @GET
	@Path("/plain")
    @Produces({MediaType.TEXT_PLAIN}) 
	public String getPlain() {
		checkContext();
		return staff.toString();
    }

	// POST doctor
    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/create")
    public Response create(@FormParam("firstName") String firstName, 
			   @FormParam("lastName") String lastName) {
		checkContext();
		String msg = null;
		// Require both properties to create.
		if (firstName == null || lastName == null) {
			msg = "Property 'firstName' or 'lastName' is missing.\n";
			return Response.status(Response.Status.BAD_REQUEST).
		                                   entity(msg).
		                                   type(MediaType.TEXT_PLAIN).
		                                   build();
		}	    
	
		int id = addDoctor(firstName, lastName);
		msg = "Doctor " + id + " created: (" + firstName + " " + lastName + ").\n";
		return Response.ok(msg, "text/plain").build();
    }
	
	// POST patient
    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/createp")
    public Response create(@FormParam("firstName") String firstName, 
			   @FormParam("lastName") String lastName,
			   @FormParam("insuranceNumber") String insuranceNumber {
		checkContext();
		String msg = null;
		// Require both properties to create.
		if (firstName == null || lastName == null || insuranceNumber == null) {
			msg = "Property 'firstName', 'lastName', or 'insuranceNumber' is missing.\n";
			return Response.status(Response.Status.BAD_REQUEST).
		                                   entity(msg).
		                                   type(MediaType.TEXT_PLAIN).
		                                   build();
		}	    
	
		int id = addPatient(firstName, lastName, insuranceNumber);
		msg = "Patient " + id + " created: (" + firstName + " " + lastName + 
				"Insurance #: " + insuranceNumber + ").\n";
		return Response.ok(msg, "text/plain").build();
    }




	//PUT doctor
    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/update")
    public Response update(@FormParam("id") int id,
			   @FormParam("firstName") String firstName, 
			   @FormParam("lastName") String lastName) {
		checkContext();

		// Check that sufficient data are present to do an edit.
		String msg = null;
		if (firstName == null && lastName == null) 
			msg = "Neither first nor last name is given: nothing to edit.\n";

		Doctor d = staff.find(id);
		if (d == null)
			msg = "There is no doctor with ID " + id + "\n";

		if (msg != null)
			return Response.status(Response.Status.BAD_REQUEST).
		                                   entity(msg).
		                                   type(MediaType.TEXT_PLAIN).
		                                   build();
		// Update.
		if (firstName != null) d.setFirstName(firstName);
		if (lastName != null) d.setLastName(lastName);
		msg = "Doctor " + id + " has been updated.\n";
		return Response.ok(msg, "text/plain").build();
    }
	
	//PUT patient by id
	@PUT
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/updatep")
    public Response update(@FormParam("id") int id,
			   @FormParam("firstName") String firstName, 
			   @FormParam("lastName") String lastName,
			   @FormParam("insuranceNumber" String insuranceNumber) {
		checkContext();

		// Check that sufficient data are present to do an edit.
		String msg = null;
		if (firstName == null && lastName == null && insuranceNumber == null) 
			msg = "Neither first name, last name, or insurance number is given: nothing to edit.\n";

		Patient p = patientList.find(id);
		if (p == null)
			msg = "There is no patient with ID " + id + "\n";

		if (msg != null)
			return Response.status(Response.Status.BAD_REQUEST).
		                                   entity(msg).
		                                   type(MediaType.TEXT_PLAIN).
		                                   build();
		// Update.
		if (firstName != null) p.setFirstName(firstName);
		if (lastName != null) p.setLastName(lastName);
		if (insuranceNumber != null) p.setInsuranceNumber(insuranceNumber);
		msg = "Patient " + id + " has been updated.\n";
		return Response.ok(msg, "text/plain").build();
    }

	// Delete Doctor by ID
    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/delete/{id: \\d+}")
    public Response delete(@PathParam("id") int id) {
		checkContext();
		String msg = null;
		Doctor d = staff.find(id);
		if (d == null) {
			msg = "There is no doctor with ID " + id + ". Cannot delete.\n";
			return Response.status(Response.Status.BAD_REQUEST).
		                                   entity(msg).
		                                   type(MediaType.TEXT_PLAIN).
		                                   build();
		}
		staff.getDoctors().remove(d);
		msg = "Doctor " + id + " deleted.\n";

		return Response.ok(msg, "text/plain").build();
    }
	
	// Delete Patient by ID
    @DELETE
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/deletep/{id: \\d+}")
    public Response delete(@PathParam("id") int id) {
		checkContext();
		String msg = null;
		Patient p = patientList.find(id);
		if (p == null) {
			msg = "There is no patient with ID " + id + ". Cannot delete.\n";
			return Response.status(Response.Status.BAD_REQUEST).
		                                   entity(msg).
		                                   type(MediaType.TEXT_PLAIN).
		                                   build();
		}
		//access the list to use built in list remove()
		patientList.getPatients().remove(d);
		msg = "Doctor " + id + " deleted.\n";

		return Response.ok(msg, "text/plain").build();
    }

    //** utilities
    private void checkContext() {
	if (staff == null || patientList == null) populate();
    }

    private void populate() {
		if ( patientList == null ) {
			patientList = new PatientList();
			String filenamePatient = "/WEB-INF/data/patients.db";
			InputStream in = sctx.getResourceAsStream(firenamePatient);
			
			// Load Patient data		
			if (in != null){
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					int i = 0;
					String record = null;
					while ((record = read.readLine()) != null){
						String[] parts = record.split("!");
						addPatient(parts[0], parts[1], parts[2]);
					}
				} catch (Exception e){
					throw new RuntimeException("I/O failed: Patient data loading");
				}
			}
			
		}
		
		if ( staff == null ){
			staff = new Staff();
			String filenameDr = "/WEB-INF/data/drs.db";
			InputStream in = sctx.getResourceAsStream(filenameDr);
			
			if (in != null) {
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					int i = 0;
					int docId = 0;
					String record = null;
					while ((record = reader.readLine()) != null) {
						String[] parts = record.split("!");
						docId = addDoctor(parts[0], parts[1]);
						// finds patients with associated docId and adds them to that 
						// doctor's patientList
						addDoctorsPatients(staff, docId, patientList);
					}
				} catch (Exception e) { 
					throw new RuntimeException("I/O failed: Doctor data loading"); 
				}
			}	
		}
		
		
		
    } 

    // Add a new Doctor
    private int addDoctor(String firstName, String lastName) {
		int id = staff.add(firstName, lastName);
		return id;
    }
	
	private void addDoctorsPatients(Staff dList, int doctorId, PatientList patientList){
		doctor = dList.find(doctorId);
		
		if ( doctor.getPatientList() == null ){
			List<Patient> assignedPatients = new List<Patient>();
			for (Patient p : patientList) {
				if (p.getDoctorId() == doctorId ){
					assignedPatients.add(p);
				}
			}
			doctor.setPatientList(assignedPatients);
		}
	}
	
	private addPatient(String firstName, String lastName, String insuranceNum){
		int id = patientList.add(firstName, lastName, insuranceNum);
		return id;
	}

    // Prediction --> JSON document
    private String toJson(Doctor doctor) {
		String json = "If you see this, there's a problem.";
		try {
			json = new ObjectMapper().writeValueAsString(doctor);
		} catch (Exception e) { }
		return json;
    }
	
	private String toJson(Patient patient) {
		String json = "If you see this, there's a problem.";
		try {
			json = new ObjectMapper().writeValueAsString(patient);
		} catch (Exception e) { }
		return json;
    }

    // DoctorList --> JSON document
    private String toJson(Staff staff) {
		String json = "If you see this, there's a problem.";
		try {
			json = new ObjectMapper().writeValueAsString(staff);
		}
		catch(Exception e) { }
		return json;
    }
	
	// PatientList --> JSON document
    private String toJson(PatientList patientList) {
		String json = "If you see this, there's a problem.";
		try {
			json = new ObjectMapper().writeValueAsString(patientList);
		}
		catch(Exception e) { }
		return json;
    }
	
	

    // Generate an HTTP error response or typed OK response.
    private Response toRequestedType(int id, String type) {
	Doctor doctor = staff.find(id);
	if (doctor == null) {
	    String msg = id + " is a bad ID.\n";
	    return Response.status(Response.Status.BAD_REQUEST).
		                                   entity(msg).
		                                   type(MediaType.TEXT_PLAIN).
		                                   build();
	}
	else if (type.contains("json"))
	    return Response.ok(toJson(doctor), type).build();
	else
	    return Response.ok(doctor, type).build(); // toXml is automatic
    }
	
	private Response toRequestedTypeP(int id, String type) {
	Patient patient = patientList.find(id);
	if (patient == null) {
	    String msg = id + " is a bad ID.\n";
	    return Response.status(Response.Status.BAD_REQUEST).
		                                   entity(msg).
		                                   type(MediaType.TEXT_PLAIN).
		                                   build();
	}
	else if (type.contains("json"))
	    return Response.ok(toJson(patient), type).build();
	else
	    return Response.ok(patient, type).build(); // toXml is automatic
    }
}



