package drpatient;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlElementWrapper; 
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "staff")
public class Staff {
    private List<Doctor> doctors; 
    private AtomicInteger doctorId;

    public Staff() { 
		doctors = new CopyOnWriteArrayList<Doctor>(); 
		doctorId = new AtomicInteger();
    }

    @XmlElement 
    @XmlElementWrapper(name = "doctors") 
    public List<Doctor> getDoctors() { 
		return this.doctors;
    } 
    public void setDoctors(List<Doctor> doctors) { 
		this.doctors = doctors;
    }

    @Override
    public String toString() {
		String s = "";
		for (Doctor d : doctors) s += d.toString();
		return s;
    }

    public Doctor find(int id) {
		Doctor doctor = null;
	
		for (Doctor d : doctors) {
			if (d.getId() == id) {
				doctor = d;
			break;
			}
		}	
		return doctor;
    }

    public int add(String firstName, String lastName) {
		int id = doctorId.incrementAndGet();
		Doctor d = new Doctor();
		d.setFirstName(firstName);
		d.setLastName(lastName);
		d.setId(id);
		doctors.add(d);
		return id;
    }
}