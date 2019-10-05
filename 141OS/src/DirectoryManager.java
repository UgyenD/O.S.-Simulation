
import java.util.*; 
import java.lang.*;
import java.io.*; 

class DirectoryManager
{
	Hashtable<String, FileInfo> T = new Hashtable<String, FileInfo>();
	void enter(StringBuffer key, FileInfo file)
	{
		String strKey = key.toString();
		T.put(strKey,file);
	}
	FileInfo lookup(StringBuffer key)
	{
		String strKey = key.toString();
		return T.get(strKey);
	}
}

class FileInfo
{
	int diskNumber;
	int startingSector;
	int fileLength;
	FileInfo(int d, int s, int f)
	{
		diskNumber = d;
		startingSector = s;
		fileLength = f; 
	}
}