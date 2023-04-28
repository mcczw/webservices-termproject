package drpatient;

//class to store patient data
//contains insurance information for each patient

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "patient")
public class Patient {
    protected String firstName;
	protected String lastName;
    protected String insuranceNumber;
    
    public Patient() { }

    // overrides
    @Override
    public String toString() {
	return lastName + ", " + firstName + ": " + insuranceNumber;
    }
    
    // properties
    public void setFirstName(String firstName){ this.firstName = firstName; }
	public String getFirstName(){ return this.firstName; }
	
	public void setLastName(String lastName){ this.lastName = lastName; }
	public String getLastName(){ return this.lastName; }

    public void setInsuranceNumber(String insuranceNumber) { this.insuranceNumber = insuranceNumber;}
    public String getInsuranceNumber() { return this.insuranceNumber; }
}
