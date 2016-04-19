package sportclub.nodeprocessor;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)

public class Node{
	@JsonInclude(Include.NON_EMPTY)
	int id;
	@JsonInclude(Include.NON_EMPTY)
	Node node;//=new Node();

	//@JsonInclude(Include.NON_EMPTY)
	public Node() {
		//this.node=new Node();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
	@Override
	public String toString() {
		return id + ":" + node;
	}
	

}
