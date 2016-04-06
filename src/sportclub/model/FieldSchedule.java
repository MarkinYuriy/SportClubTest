package sportclub.model;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class FieldSchedule {
	@Id @GeneratedValue
	int id;
	@ManyToOne(cascade=CascadeType.ALL) 
   	Slot slot;
	@ManyToOne(cascade=CascadeType.ALL)  
	Court court;
	
	int fieldPartitionType;
	
	@OneToOne
	Event fieldPart1;
	@OneToOne
	Event fieldPart2;
	@Override
	public String toString() {
		return "FieldSchedule [id=" + id + ", slot=" + slot + ", field=" + court + ", fieldPartitionType="
				+ fieldPartitionType + ", fieldPart1=" + fieldPart1 + ", fieldPart2=" + fieldPart2 + ", fieldPart3="
				+ fieldPart3 + "]";
	}
	@OneToOne
	Event fieldPart3;
	
	public Slot getSlot() {
		return slot;
	}
	public void setSlot(Slot slot) {
		this.slot = slot;
	}
	public Court getCourt() {
		return court;
	}
	public void setCourt(Court court) {
		this.court = court;
	}
	public int getFieldPartitionType() {
		return fieldPartitionType;
	}
	public void setFieldPartitionType(int fieldPartitionType) {
		this.fieldPartitionType = fieldPartitionType;
	}
	public Event getFieldPart1() {
		return fieldPart1;
	}
	public void setFieldPart1(Event fieldPart1) {
		this.fieldPart1 = fieldPart1;
	}
	public Event getFieldPart2() {
		return fieldPart2;
	}
	public void setFieldPart2(Event fieldPart2) {
		this.fieldPart2 = fieldPart2;
	}
	public Event getFieldPart3() {
		return fieldPart3;
	}
	public void setFieldPart3(Event fieldPart3) {
		this.fieldPart3 = fieldPart3;
	}
	public int getId() {
		return id;
	}
	
	
}
