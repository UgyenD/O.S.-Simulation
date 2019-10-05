import java.util.*; 
import java.lang.*;
import java.io.*; 
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;  
import java.io.IOException;
import java.io.File;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.application.*;
import javafx.scene.image.*;

class UserThread 
	extends Thread
{
	File file;
	DirectoryManager directoryManager;
	
	Disk[] disks;
	ResourceManager diskManager;

	Printer[] printers;
	ResourceManager printerManager;

	long writeTime = 200;
	double speed;
	int userNum;

	Label status;
	ImageView[] diskViews;
	Label[] diskStatusLabels;
	ImageView[] printViews;
	Label[] printLabels;

	UserThread(String path, Disk[] d,Printer[] p , DirectoryManager dM, ResourceManager diskM, ResourceManager pM, int uN, Label stat, ImageView[] dImg, Label[] dS, double s, ImageView[] pV, Label[] prinLab)
	{
		file = new File(path);
		disks = d;
		printers = p;
		diskManager = diskM;
		printerManager = pM;
		directoryManager = dM;
		userNum = uN;
		status = stat;
		diskViews = dImg;
		diskStatusLabels = dS;
		speed = s;
		printViews = pV;
		printLabels = prinLab;
	}

	int freeMem(int diskNum)
	{
		return disks[diskNum].freeSector;
	}

	void updateUserStatusRead()
	{
		Task<Void> task = new Task<Void>() {
                @Override 
                public Void call() throws Exception {
                	updateMessage("Reading file...");
                	Thread.sleep(100);
			        return null;        }};
		status.textProperty().bind(task.messageProperty());
		task.setOnSucceeded(e ->{
			status.textProperty().unbind();
			System.out.println("File is being read");
		});

		Thread thrUpdateUser = new Thread(task);
		thrUpdateUser.setDaemon(true);
		thrUpdateUser.start();
	}

	void updateUserStatusWrite(int diskNum)
	{
		StringBuffer theDisk = new StringBuffer("DISK ");
		theDisk.append(diskNum);

		Task<Void> task = new Task<Void>() {
                @Override 
                public Void call() throws Exception {
                	updateMessage("Saving to "+theDisk.toString());
                	Thread.sleep(100);
			        return null;        }};
		status.textProperty().bind(task.messageProperty());
		task.setOnSucceeded(e ->{
			status.textProperty().unbind();
			System.out.println("Writing to disk...");
		});

		Thread thrUpdateUser = new Thread(task);
		thrUpdateUser.setDaemon(true);
		thrUpdateUser.start();
	}

	void updateDiskWrite(int diskNum) throws FileNotFoundException
	{
		String currentPath = new File("").getAbsolutePath();
        String basePath = new File(currentPath).getParent();
		Image diskWritten = new Image(new FileInputStream(basePath + "\\resources\\squidward2.gif"));
		Task<Void> task = new Task<Void>() {
                @Override 
                public Void call() throws Exception {
                	updateMessage("Being written to...");
                	Thread.sleep(100);
			        return null;        }};
		diskStatusLabels[diskNum].textProperty().bind(task.messageProperty());
		task.setOnSucceeded(e ->{
			diskStatusLabels[diskNum].textProperty().unbind();
			diskViews[diskNum].setImage(diskWritten);
			System.out.println("Disk being written to...");
		});

		Thread thrUpdateUser = new Thread(task);
		thrUpdateUser.setDaemon(true);
		thrUpdateUser.start();
	}


	void statusWriteDone()
	{
		Task<Void> task = new Task<Void>() {
                @Override 
                public Void call() throws Exception {
                	updateMessage("DONE");
                	Thread.sleep(100);
			        return null;        }};
		status.textProperty().bind(task.messageProperty());
		task.setOnSucceeded(e ->{
			status.textProperty().unbind();
			System.out.println("Done saving.");
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
			System.out.println("Done saving.");
		});

		Thread thrUpdateUser = new Thread(task);
		thrUpdateUser.setDaemon(true);
		thrUpdateUser.start();
	}

	void diskWriteDone1(int diskNum) throws FileNotFoundException
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
			System.out.println("Done saving.");
		});

		Thread thrUpdateUser = new Thread(task);
		thrUpdateUser.setDaemon(true);
		thrUpdateUser.start();
	}

	public void run()
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			String[] output;

			updateUserStatusRead();

			while((line = br.readLine()) != null)
			{
				output = line.split(" ", 3);
				if(output[0].equals(".save"))
				{
					//NEED TO CHECK WHETHER DISK IS FREE SO YOU CAN WRTIE TO IT
					int freeDisk = diskManager.request();
					StringBuffer fileName = new StringBuffer(output[1]);

					updateUserStatusWrite(freeDisk+1);
					updateDiskWrite(freeDisk);

					int startSector = disks[freeDisk].freeSector;
					System.out.println("Writing to Disk: ");
					System.out.println(freeDisk);
					while(!((line = br.readLine()).equals(".end")))
					{
						output = line.split(". ");
						StringBuffer strBuf = new StringBuffer(output[1]);
						
						double doubWrite = writeTime/speed;
						writeTime = (long) doubWrite;
						
						Thread.sleep(writeTime);
						disks[freeDisk].write(disks[freeDisk].freeSector, strBuf);
						System.out.println(output[1]);
						//NEED TO RELEASE THE DISK
					}

					diskWriteDone(freeDisk);

					System.out.println("Done writing to: ");
					System.out.println(freeDisk);
					int endSector = disks[freeDisk].freeSector;
					FileInfo fileInfo = new FileInfo(disks[freeDisk].diskNumber,startSector,endSector);

					statusWriteDone();

					diskManager.release(freeDisk);
					directoryManager.enter(fileName, fileInfo);
				}
				
				if(output[0].equals(".print"))
				{
					StringBuffer fileName = new StringBuffer(output[1]);
					PrinterThread printJob = new PrinterThread(fileName, disks, printers, directoryManager, diskManager, printerManager, diskViews, diskStatusLabels, speed, printViews, printLabels);
					printJob.start();
				}
			}
			diskWriteDone1(0);
			diskWriteDone1(1);

		}
		catch(Exception e)
		{
			System.out.println("Something went wrong");
			System.out.println(e);
		}
	}
}