package drpatient;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlElementWrapper; 
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "patientList")
public class PatientList {
    private List<Patient> patients; 
    private AtomicInteger patientId;

    public PatientList() { 
		patients = new CopyOnWriteArrayList<Patient>(); 
		patientId = new AtomicInteger();
    }

    @XmlElement 
    @XmlElementWrapper(name = "patients") 
    public List<Patient> getPatients() { 
		return this.patients;
    } 
    public void setPatients(List<Patient> patients) { 
		this.patients = patients;
    }

    @Override
    public String toString() {
		String s = "";
		for (Patient p : patients) s += p.toString();
		return s;
    }

    public Patient find(int id) {
		Patient patient = null;
	
		for (Patient p : patients) {
			if (p.getId() == id) {
				patient = p;
			break;
			}
		}	
		return patient;
    }

    public int add(String firstName, String lastName, String insuranceNum, int docNum) {
		int id = patientId.incrementAndGet();
		Patient p = new Patient();
		p.setFirstName(firstName);
		p.setLastName(lastName);
		p.setInsuranceNumber(insuranceNum);
		p.setId(id);
		p.setDoctorId(docNum);
		patients.add(p);
		return id;
    }
	
	public int add(Patient p){
		int id = patientId.incrementAndGet();
		patients.add(p);
		return id;
	}
}