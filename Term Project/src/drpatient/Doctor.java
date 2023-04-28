package drpatient;

//class to store doctor
//which contains a list of patients

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlRootElement(name = "doctor")
public class Doctor {
    protected String firstName;
	protected String lastName;
    protected List<Patient> patientList;
	protected int id;
    
    public Doctor() { }

   
    @Override
    public String toString() {
		String patientString = "\nPatients: " + this.patientList.toString();
		return lastName + ", " + firstName + ": " + id + patientString + "\n";
    }
    
    // properties
	@XmlElement
	public String getFirstName(){ return this.firstName; }
	public void setFirstName(String firstName){ this.firstName = firstName; }

	
	@XmlElement
	public String getLastName(){ return this.lastName; }
	public void setLastName(String lastName){ this.lastName = lastName; }
	

	@XmlElement
    public int getId() { return this.id; }
    public void setId(int id) { this.id=id;}

	@XmlElement
	public List<Patient> getPatientList(){
		return this.patientList;
	}
	public void setPatientList(List<Patient> patientList){
		if (this.patientList == null ){
				for (Patient p : patientList){
					if( p.getDoctorId == this.id){
						patientList.add(p);
					}
				}
		} else {
			this.patientList = patientList;
		}
	}
	
}