
import java.util.*; 
import java.lang.*;
import java.io.*; 

class Disk
{	
	static final int NUM_SECTORS = 1024;
	StringBuffer sectors[];
	int diskNumber;
	int freeSector;
	Disk(int dNum)
	{
		sectors = new StringBuffer[NUM_SECTORS];
		diskNumber = dNum;
		freeSector = 0;

	}
	void write(int sector, StringBuffer data)
	{
		sectors[sector] = data;
		++freeSector;	
	}  
	void read(int sector, StringBuffer data)
	{
		data.append(sectors[sector]);
	}
}
