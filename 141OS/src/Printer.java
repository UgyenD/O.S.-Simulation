
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.PrintWriter;  
import java.io.IOException;
import java.io.File; 

class Printer
{
	int printerNum;
	BufferedWriter bw;
	FileWriter fw;
	PrintWriter pw;
	File file;
	Printer(int pN)
	{
		printerNum = pN;
		String currentPath = new File("").getAbsolutePath();
        String basePath = new File(currentPath).getParent();
		StringBuffer fileName = new StringBuffer("\\outputs\\PRINTER");
		fileName.append(pN+1);
		fileName.append(".txt");
		String filePath = basePath + fileName;
		file = new File(filePath);
	}

	void print(StringBuffer data)
	{
		try
		{
			fw = new FileWriter(file, true);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			pw.println(data.toString());
			pw.flush();
			pw.close();
		}

		catch(Exception e)
		{
			System.out.println("Something went wrong");
			System.out.println(e);
		}
		
	}  
}