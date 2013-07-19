package owasp.ejsf.unifreiburg.util;

import java.io.*;
import java.util.*;

public class Property
{
	private String str ;
	private String key;
  
	public static void main(String[] args) 
	{
		Property r = new Property();
	}
	
	public Property()
	{
		try
		{
		    File f = new File("\\esapi\\unifreiburg\\util\\JSFESAPI.properties");
		    if(f.exists())
		    {
		    	Properties pro = new Properties();
			    FileInputStream in = new FileInputStream(f);
			    pro.load(in);
			    System.out.println("All key are given: " + pro.keySet());
				System.out.print("Enter Key : ");
	            String p = pro.getProperty("ESAPI.AutoSecurity");
	            System.out.println(key + " : " + p);
		    }
		    else
		    {
		    	System.out.println("File not found!");
		    }
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
}
