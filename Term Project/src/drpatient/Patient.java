package drpatient;

//class to store patient data
//contains insurance information for each patient

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "patient")
public class Patient {
    protected String firstName;
	protected String lastName;
    protected String insuranceNumber;
	protected int doctorId = 0;
	protected int id;
    
	//might need to change patient id to pId or something to avoid
	// conflict in the xml/id call
	
    public Patient() { }

    // overrides
    @Override
    public String toString() {
	return  id + "- "+ lastName + ", " + firstName + ": " + insuranceNumber 
		+ " -- Doctor Id: " + doctorId + "\n";
    }
    
    // properties
	@XmlElement
	public String getFirstName(){ return this.firstName; }
    public void setFirstName(String firstName){ this.firstName = firstName; }
	
	@XmlElement
	public String getLastName(){ return this.lastName; }
	public void setLastName(String lastName){ this.lastName = lastName; }
	

	@XmlElement
	public String getInsuranceNumber() { return this.insuranceNumber; }
    public void setInsuranceNumber(String insuranceNumber) { this.insuranceNumber = insuranceNumber;}
	
	@XmlElement
	public int getDoctorId(){ return this.doctorId; }
	public void setDoctorId(int doctorId){ this.doctorId = doctorId; }
	
	@XmlElement
    public int getId() { return this.id; }
    public void setId(int id) { this.id=id;}
}
