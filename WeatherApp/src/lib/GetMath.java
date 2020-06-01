package lib;

import java.util.ArrayList;

public class GetMath {
	
	public static String getMaxValueToCompare(String v1, String v2) {
		if(v1==null) v1="0";
		else if(v2==null) v2="0";
		
		double a1=Double.parseDouble(v1);
		double a2=Double.parseDouble(v2);
		
		if (a1>=a2) return v1;
		else return v2;
	}
	
	public static String getMinValueToCompare(String v1, String v2) {
		if(v1==null) v1="0";
		else if(v2==null) v2="0";
		
		double a1=Double.parseDouble(v1);
		double a2=Double.parseDouble(v2);
		
		if (a1<=a2) return v1;
		else return v2;
	}
	
	public static int getModeValue(ArrayList<Integer> list) {
		int[] index=new int[5];
		int result=100;
		int mode=0;
		for(int i=0;i<list.size();i++) {
			index[list.get(i)]++;
		}
		for(int i=0;i<index.length;i++) {
			if(index[i]>mode) {
				mode=index[i];
				result=i;
			}
		} 
		return result;
	}
}
