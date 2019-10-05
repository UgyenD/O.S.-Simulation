import java.util.*; 
import java.lang.*;
import java.io.*; 
import javafx.scene.control.*;
import javafx.scene.image.*;


public class mainClass
{
	public static void main (Label[] args1, ImageView[] args2, Label[] args3, double args4, ImageView[] args5, Label[] args6)
    {
    	int NUMBER_OF_USERS = 4;
    	int NUMBER_OF_DISKS = 2;
    	int NUMBER_OF_PRINTERS = 3;


        ImageView[] imgViews = args2;
        Label[] statusLabels = args1;
        Label[] diskStatusLabels = args3;
        double speed = args4;
        ImageView[] printViews = args5;
        Label[] printLabels = args6;

    	Printer thePrinters[] = new Printer[NUMBER_OF_PRINTERS];
    	Disk theDisks[] = new Disk[NUMBER_OF_DISKS];
    	UserThread theUsers[] = new UserThread[NUMBER_OF_USERS];

    	DirectoryManager mainManager = new DirectoryManager();

    	ResourceManager diskManager = new ResourceManager(NUMBER_OF_DISKS);
    	ResourceManager printerManager = new ResourceManager(NUMBER_OF_PRINTERS);
    	

    	for (int i = 0; i < NUMBER_OF_PRINTERS; ++i)
    	{
    		thePrinters[i] = new Printer(i);
    	}

    	for(int i = 0; i < NUMBER_OF_DISKS; ++i)
    	{
    		theDisks[i] = new Disk(i);
    	}

    	for(int i = 0; i < NUMBER_OF_USERS; ++i)
    	{
            String currentPath = new File("").getAbsolutePath();
            String basePath = new File(currentPath).getParent();
    		StringBuffer userFilepath = new StringBuffer("\\inputs\\USER");
    		int userNum = i+1;
    		String thePath = basePath + (userFilepath.append(userNum)).toString();
    		theUsers[i] = new UserThread(thePath, theDisks, thePrinters, mainManager, diskManager, printerManager,userNum, statusLabels[i],imgViews, diskStatusLabels, speed, printViews,printLabels);
    	}
    	
    	for(int i = 0; i < NUMBER_OF_USERS; ++i)
    	{
    		theUsers[i].start();
    	}
    	
    }
}