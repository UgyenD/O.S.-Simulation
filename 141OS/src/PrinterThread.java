
import java.util.*; 
import java.lang.*;
import java.io.*; 
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.PrintWriter;  
import java.io.IOException;
import java.io.File; 
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.application.*;
import javafx.scene.image.*;


class PrinterThread
	extends Thread
{
	Disk[] disks;
	Printer[] printers;
	DirectoryManager directory;
	ResourceManager diskManager;
	ResourceManager printerManager;
	StringBuffer inputFile;
	long printTime = 2750;
	double speed;
	ImageView[] diskViews;
	Label[] diskStatusLabels;
	Label[] printLabels;
	ImageView[] printViews;

	PrinterThread(StringBuffer inFile, Disk[] d, Printer[] p, DirectoryManager dm, ResourceManager diskM, ResourceManager pM, ImageView[] dV, Label[] dS, double s, ImageView[] pV, Label[] prinLab)
	{
		disks = d;
		printers = p;
		directory = dm;
		diskManager = diskM;
		printerManager = pM;
		inputFile = inFile;
		diskViews = dV;
		diskStatusLabels = dS;
		speed = s;
		printViews = pV;
		printLabels = prinLab;
	}

	int freeMem(int diskNum)
	{
		return disks[diskNum].freeSector;
	}

	void diskRead(int diskNum) throws FileNotFoundException
	{
		String currentPath = new File("").getAbsolutePath();
        String basePath = new File(currentPath).getParent();
		Image diskWritten = new Image(new FileInputStream(basePath + "\\resources\\squidward2.gif"));
		Task<Void> task = new Task<Void>() {
                @Override 
                public Void call() throws Exception {
                	updateMessage("Being read from...");
                	Thread.sleep(100);
			        return null;        }};
		diskStatusLabels[diskNum].textProperty().bind(task.messageProperty());
		task.setOnSucceeded(e ->{
			diskStatusLabels[diskNum].textProperty().unbind();
			diskViews[diskNum].setImage(diskWritten);
			System.out.println("Disk being read from...");
		});

		Thread thrUpdateUser = new Thread(task);
		thrUpdateUser.setDaemon(true);
		thrUpdateUser.start();
	}

	void diskWriteDone(int diskNum) throws FileNotFoundException
	{
		String currentPath = new File("").getAbsolutePath();
        String basePath = new File(currentPath).getParent();
        Image idleImg = new Image(new FileInputStream(basePath + "\\resources\\spongebob.gif"));
		StringBuffer freeSpace = new StringBuffer("");
		int freeSpa = 1024 - freeMem(diskNum);
		freeSpace.append(freeSpa);
		freeSpace.append(" free sectors remains");
		Task<Void> task = new Task<Void>() {
                @Override 
                public Void call() throws Exception {
                	updateMessage(freeSpace.toString());
                	Thread.sleep(100);
			        return null;        }};
		diskStatusLabels[diskNum].textProperty().bind(task.messageProperty());
		task.setOnSucceeded(e ->{
			diskStatusLabels[diskNum].textProperty().unbind();
			diskViews[diskNum].setImage(idleImg);
			System.out.println("Done reading.");
		});

		Thread thrUpdateUser = new Thread(task);
		thrUpdateUser.setDaemon(true);
		thrUpdateUser.start();
	}

	void printingStatus(int printNum) throws FileNotFoundException
	{
		String currentPath = new File("").getAbsolutePath();
        String basePath = new File(currentPath).getParent();
		Image printing = new Image(new FileInputStream(basePath + "\\resources\\printer.gif"));
		Task<Void> task = new Task<Void>() {
                @Override 
                public Void call() throws Exception {
                	updateMessage("Printing...");
                	Thread.sleep(100);
			        return null;        }};
		printLabels[printNum].textProperty().bind(task.messageProperty());
		task.setOnSucceeded(e ->{
			printLabels[printNum].textProperty().unbind();
			printViews[printNum].setImage(printing);
			System.out.println("Disk being read from...");
		});

		Thread thrUpdateUser = new Thread(task);
		thrUpdateUser.setDaemon(true);
		thrUpdateUser.start();
	}

	void printedStatus(int printNum) throws FileNotFoundException
	{
		String currentPath = new File("").getAbsolutePath();
        String basePath = new File(currentPath).getParent();
		Image printing = new Image(new FileInputStream(basePath + "\\resources\\print.png"));
		Task<Void> task = new Task<Void>() {
                @Override 
                public Void call() throws Exception {
                	updateMessage("DONE");
                	Thread.sleep(100);
			        return null;        }};
		printLabels[printNum].textProperty().bind(task.messageProperty());
		task.setOnSucceeded(e ->{
			printLabels[printNum].textProperty().unbind();
			printViews[printNum].setImage(printing);
			System.out.println("Done printing");
		});

		Thread thrUpdateUser = new Thread(task);
		thrUpdateUser.setDaemon(true);
		thrUpdateUser.start();
	}

	public void run()
	{
		BufferedWriter bw;
		try
		{
			System.out.println("PrinterThread called");
			int freePrinter = printerManager.request();

			printingStatus(freePrinter);

			FileInfo fileInfo = directory.lookup(inputFile);
			StringBuffer fileName = new StringBuffer("PRINTER");
			fileName.append(freePrinter+1);
			fileName.append(".txt");
			System.out.println("PrinterThread A");
			String currentPath = new File("").getAbsolutePath();
        	String basePath = new File(currentPath).getParent();
			File file = new File(basePath + "\\outputs\\" + fileName.toString());
			System.out.println("PrinterThread B");
			if(!file.exists())
			{
				System.out.println("Creating new Printer File");
				System.out.println(file);
				file.createNewFile();
			}
			
			Disk readDisk = disks[fileInfo.diskNumber];

			diskRead(fileInfo.diskNumber);
			
			for(int i = fileInfo.startingSector; i < fileInfo.fileLength; ++i)
			{
				StringBuffer outLine = new StringBuffer("");
				readDisk.read(i, outLine);
				
				double doubWrite = printTime/speed;
				printTime = (long) doubWrite;
				Thread.sleep(printTime);
				
				printers[freePrinter].print(outLine);
			}

			diskWriteDone(fileInfo.diskNumber);

			printedStatus(freePrinter);
			
			printerManager.release(freePrinter);
		}

		catch(Exception e)
		{
			System.out.println("Something went wrong!");
			System.out.println(e);
		}
	}
}