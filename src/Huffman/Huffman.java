package Huffman;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

public class Huffman {

	public static HashMap<Integer, Integer> getHuffman(double[] probabilities) {
		
		//HashMap<Integer,Node> huffmanMap = new HashMap<Integer,Node>();
		LinkedList<Node> huffmanList = new LinkedList<Node>();
		for(Integer i=0; i<probabilities.length; i++) {
			if(probabilities[i]!=0){
				ChildNode node = new ChildNode((Integer)i,probabilities[i]);
				huffmanList.add(node);
			}
		}
		Collections.sort(huffmanList);
		
		while(huffmanList.size() !=1) {
			for(int i=0;i<huffmanList.size();i++)
				System.out.println(huffmanList.get(i).getName()+ " + "+ huffmanList.get(i).getProb());
			Node minor2 = huffmanList.remove(1);
			Node minor1 = huffmanList.remove(0);
			FatherNode n = new FatherNode(minor1,minor2);
			huffmanList.add(n);
			Collections.sort(huffmanList);
		}
		 
		return huffmanList.get(0).getCode();
		/*while(huffmanMap.size() != 1) {
			Node minor1,minor2 = null;
			Iterator it = huffmanMap.entrySet().iterator();
			Entry<Integer,Node> nodo = (Entry<Integer,Node>)it.next();
			minor1 = nodo.getValue();
			if(it.hasNext()) {
				nodo = (Entry<Integer,Node>)it.next();
				minor2 = nodo.getValue();
			}
			System.out.println("Nuevo ciclo");
			for(Entry<Integer, Node> n: huffmanMap.entrySet()) {
				if (minor2.getProb()>=n.getValue().getProb()) {
					if(minor1.getProb()>=n.getValue().getProb()) {
						minor2=minor1;
						minor1=n.getValue();
					}else {
						minor2=n.getValue();
					}
				}
				System.out.println(n.getValue().getName()+ " + "+ n.getValue().getProb());
			}
			FatherNode n = new FatherNode(minor1,minor2);
			huffmanMap.put(n.getName(), n);
			huffmanMap.remove(minor1.getName());
			huffmanMap.remove(minor2.getName());
		}
		
		Entry<Integer, Node> it = huffmanMap.entrySet().iterator().next();
		Node lastnode = it.getValue();
		HashMap<Integer, Integer> codesHash = lastnode.getCode();
*/
		
	}

}
