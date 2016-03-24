package clean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Clean {

	static String[] attributes;
	static String output="";
	static int totalIns=0;
	static int[] change={4,31,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28};//115
	public static void main(String[] args) throws IOException 
	{
		String trainFile,outputFile;
		
    	ArrayList<String[]> trainSet = new ArrayList<String[]>();
		
	   	trainFile="Result_l.csv";
		read(trainFile,trainSet);
		totalIns=trainSet.size();
		
		
		for(int i=0;i<change.length;i++)
		{
			changeCN(change[i],trainSet,0,10);
		}
		changeCN(115,trainSet,0,20);
		
		changeList(8,trainSet);
		changeList(9,trainSet);
		
		changeCN(8,trainSet,0,4);
		changeCN(9,trainSet,0,4);

		
		outputFile="result_mnew.csv";
		filePrint(trainSet,outputFile);
		
		
		attributeN(trainSet);
	}
	
	public static void read(String fileName, ArrayList<String[]> data)  throws IOException
	{
    	BufferedReader br = new BufferedReader(new FileReader(fileName));
    	String line;
		while ((line = br.readLine()) != null) 
		{
			String[] ins = line.split(",",-1);
			data.add(ins);
		}
		br.close();
	}
	
	public static void changeCN(int position,ArrayList<String[]> data, int increse ,int rangeMax)
	{
		String range;
		int value;
		//System.out.println(data.get(0)[position]+":"+data.size());
		for(int x=1; x<data.size();x++)
		{	
			if(!data.get(x)[position].isEmpty())
			{
				value = Integer.parseInt(data.get(x)[position]); 
				range= Integer.toString(value/rangeMax)+"-"+Integer.toString((value+increse)/rangeMax);
				//System.out.println(x+":"+data.get(x)[position]+":"+range);
				data.get(x)[position]=range;
			}
			else
			{				
				data.get(x)[position]="0-0";

			}
			//System.out.println(x);
		}
	}
	
	public static void changeList(int position,ArrayList<String[]> data)
	{
		
		for(int x=1; x<data.size();x++)
		{	
			if(data.get(x)[position].indexOf("Less")!=-1)
			{
				data.get(x)[position]="0";
			}
			int check=data.get(x)[position].indexOf(" ");
			if(data.get(x)[position].indexOf("+")!=-1)
			{
				check--;
			}
			if(check!=-1)
			{
				data.get(x)[position]= data.get(x)[position].substring(0,check);
			}
		}

	}
	
	public static void filePrint(ArrayList<String[]> trainSet,String filename) throws IOException
	{
		PrintWriter writer= new PrintWriter(new FileWriter(filename));;
		for(int x=0;x<trainSet.size();x++)
		{
			output="";
			int length =trainSet.get(x).length-1;
			for(int y=0;y<length; y++)
			{
				output+= trainSet.get(x)[y]+",";
			}
			output += trainSet.get(x)[length];
			writer.println(output);
		}
		writer.close();
	}
	
	public static void attributeN(ArrayList<String[]> trainSet)
	{
		ArrayList<attributes> attributeN= new ArrayList<attributes> ();
		
		for(int y=0;y<trainSet.get(0).length;y++)
		{
			attributes instance =new attributes(trainSet.get(0)[y]);
			attributeN.add(instance);
		}
		
		for(int x=1;x<trainSet.size();x++)
		{
			for(int y=0;y<trainSet.get(x).length; y++)
			{
				attributeN.get(y).counter(trainSet.get(x)[y]);
			}
		}
		
		printAttVal(attributeN);
	}
	
	public static void printAttVal(ArrayList<attributes> print)
	{
		for(int x=0; x < print.size(); x++)
		{
			System.out.println("******************"+x+":"+print.get(x).attributeName+"************************");
			for(int y=0; y<print.get(x).values.size(); y++)
			{
				System.out.println(print.get(x).values.get(y).value
						+"\t"+print.get(x).values.get(y).counter+"\t"+format((double)(print.get(x).values.get(y).counter*100)/totalIns)+"%");
			}
		}
	}
	
    public static double format(double doubleF)
    {

    	DecimalFormat df = new DecimalFormat("#.##");
    	String result= df.format(doubleF);
    	
    	return (Double.parseDouble(result));
    }
		
}

class attributes
{
	String attributeName;
	ArrayList<mapping> values=new ArrayList<mapping>();
	
	public attributes(String name)
	{
		attributeName=name;
	}
	
	public void counter(String value)
	{
		boolean flag=false;
		for(int i=0;i<values.size();i++)
		{
			if(values.get(i).value.equals(value))
			{
				values.get(i).counter++;
				flag=true;
			}
		}
		if(!flag)
		{
			mapping instance=new mapping(value);
			values.add(instance);
		}
	}
}

class mapping
{
	String value="";
	int counter=0;
	public mapping(String str)
	{
		value=str;
		counter=1;
	}
}