import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Merge 
{
	static ArrayList<String[]> attributes=new ArrayList<String[]>();
	static String output="";
	
	public static void main(String[] args) throws IOException
	{
    	String trainFile,users,words,testFile,trainOutputFile,testOutputFile;

    	if(args.length!=0)
    	{
    	trainFile=args[0];
		users=args[1];
		words=args[2];
		testFile=args[3];
		trainOutputFile=args[4];
		testOutputFile=args[5];
    	}
		
    	ArrayList<String[]> trainSet = new ArrayList<String[]>();
    	ArrayList<String[]> usersSet = new ArrayList<String[]>();
    	ArrayList<String[]> wordsSet = new ArrayList<String[]>();
    	//ArrayList<String[]> testSet = new ArrayList<String[]>();
    	//ArrayList<String[]> trainOutput = new ArrayList<String[]>();
    	//ArrayList<String[]> testOutput = new ArrayList<String[]>();
    	
    	trainFile="train_l.csv";
		read(trainFile,trainSet);
		
		users="users_l.csv";
		read(users,usersSet);
		
		words="words_l.csv";
		read(words,wordsSet);
		
		//testFile="test.csv";
		//read(testFile,testSet);
		//print(trainSet);
		//print(usersSet);
		//print(wordsSet);
		
		//System.out.print(output);
		
		trainOutputFile	="Result_l.csv";
		merge(trainSet,usersSet,wordsSet,trainOutputFile);
	}
	
	public static void read(String fileName, ArrayList<String[]> data)  throws IOException
	{
    	BufferedReader br = new BufferedReader(new FileReader(fileName));
    	String[] attribute=br.readLine().split(","); 
    	attributes.add(attribute);
    	String line;
		while ((line = br.readLine()) != null) 
		{
			String[] ins = line.split(",",-1);
			data.add(ins);
		}
		br.close();
	}
	
	public static void print(ArrayList<String[]> data)
	{
		for(int i=0;i<data.size();i++)
		{
			for(int j=0; j< data.get(i).length ;j++)
			{
				System.out.print(data.get(i)[j]+",");
			}
			System.out.println();
		}
		System.out.println(data.size());
	}
	
	public static void merge(ArrayList<String[]> data, ArrayList<String[]> users,ArrayList<String[]> words,String filename) throws IOException
	{
		PrintWriter writer = new PrintWriter(new FileWriter(filename));
		
		//print attributes	
		output+=attributes.get(0)[0]+","+
				attributes.get(0)[1]+","+
				attributes.get(0)[2];
		for(int x=1;x<attributes.get(1).length; x++)
		{
			output+=","+attributes.get(1)[x];
		}
		
		for(int y=2; y<attributes.get(2).length;y++)
		{
			output+=","+attributes.get(2)[y];
		}
		output+=","+attributes.get(0)[4]+","+attributes.get(0)[3];
		writer.println(output);
		
		//print content
		for(int i=0;i<data.size();i++)
		{	
			String userHeader="";
			//for users
			for(int k=0; k< users.size();k++)
			{
				//System.out.println(data.get(i)[2]+":"+users.get(k)[0]);
				if(convert(data.get(i)[2])==convert(users.get(k)[0]))
				{
					//System.out.println(data.get(i)[2]+":"+users.get(k)[0]);
					userHeader+=data.get(i)[0]+","+
							data.get(i)[1]+","+
							data.get(i)[2];
					for(int x=1;x<users.get(k).length; x++)
					{
						userHeader+=","+users.get(k)[x];
					}
				}
			}
			
			//for words
			for(int j=0; j< words.size();j++)
			{

				if(convert(data.get(i)[0])==convert(words.get(j)[0]) && convert(data.get(i)[2])==convert(words.get(j)[1]))
				{
					String output="";
					output=output+userHeader;
					for(int x=2; x<words.get(j).length;x++)
					{
						output+=","+words.get(j)[x];
					}
					output+=","+data.get(i)[4]+","+data.get(i)[3];
					//System.out.println(output);
		    		writer.println(output);
				}
			}
		}
		writer.close();
	}

	
	public static int convert(String str)
	{
		int a = Integer.parseInt(str);
		return a;
	}

	
}
