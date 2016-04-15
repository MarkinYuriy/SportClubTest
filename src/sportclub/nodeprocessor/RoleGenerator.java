package sportclub.nodeprocessor;
import java.util.*;

import javax.persistence.EntityManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sportclub.model.Role;

public class RoleGenerator {
	
	
	public static String JSONRoleGenerator(int n, EntityManager em) throws JsonProcessingException {
		//count of tree roles
		//int n=3;
		LinkedHashSet<String> roleSet= genListRole(n); 
		//System.out.println("list roles previous version\n" +roleSet);
		//LinkedHashSet<Node> nodeSet = new LinkedHashSet<Node>(); 
		ObjectMapper objectMapper = new ObjectMapper();
		String json="{}";
		
		
		for (String str:roleSet){
			Role role = new Role();
			json = objectMapper.writeValueAsString(node(str));
			System.out.println("role:" +json);
			role.setIdCode(json);
			role.setDescription("description for "+str);
			em.persist(role);
			
		}
				
		return json;
	}

/*	public static String Nodes2JSON(String str){
		String[] ar = str.split("\\.");
		Node node[] = new Node[ar.length];
		node[ar.length-1] = new Node();
		node[ar.length-1].id=Integer.parseInt(ar[ar.length-1]);
		for (int i=ar.length-2;i>=0;i--){
			node[i] = new Node();
			node[i].id=Integer.parseInt(ar[i]);
			node[i].node=node[i+1];
		}
		ObjectMapper objectMapper = new ObjectMapper();

		String json="{}";
		try {
			json = objectMapper.writeValueAsString(node[0]);
		} catch (JsonProcessingException e) {

		}

		return json;

	}*/

	public static Node node(String str){
		String[] ar = str.split("\\.");
		/*System.out.println(str);
		for(String s:ar){
			System.out.print(s+" ");
		}*/
		//System.out.println();
		Node node[] = new Node[ar.length];
		String regex = "\\d+";
		if(ar[ar.length-1].matches(regex)){
			
			node[ar.length-1] = new Node();
			node[ar.length-1].id=Integer.parseInt(ar[ar.length-1]);
		}else return null;
		for (int i=ar.length-2;i>=0;i--){
			node[i] = new Node();
			if(ar[i].matches(regex)){
			node[i].id=Integer.parseInt(ar[i]);
			node[i].node=node[i+1];
			}else return null;
		}
		return node[0];

	}

	private static LinkedHashSet<String> genListRole(int deep) {

		int n=deep;
		LinkedHashSet<String> roleSet = new LinkedHashSet<String>();
		for (int i=1;i<=n;i++){
			roleSet.add(((Integer)i).toString());
			roleSet=recursive(n, ((Integer)i).toString(),n,roleSet);
		}

		return roleSet;
	}

	private static LinkedHashSet<String> recursive(int n, String str,int level, LinkedHashSet<String> roleSet){
		LinkedHashSet<String> treeSet=roleSet;
		if(level>0){
			int k= (new Random().nextInt(n))+1;
			for (int i=1;i<k;i++){
				String strNew=str+"."+i;	
				int isRole= new Random().nextInt(100);
				if (isRole>49){
					roleSet.add(strNew);
				}
				treeSet=recursive(n, strNew,level-1,roleSet);
			}
		}
		return treeSet;
	}
}
