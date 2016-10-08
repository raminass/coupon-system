package utils;

// method to compare String, this is needed in case both strings are Null
public class compareString {
	
	public static boolean compareStrings(String str1, String str2){

	    if(str1 == null && str2 == null) return true;

	    if(str1 != null && str2 != null){
	        if(str1.equals(str2))
	            return true;
	    }

	    return false;
	}

}
