package littlemylyn.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class TaskList 
{
	private Map<String, Task> mTasks = new HashMap<>();
	private File mDataFile;
	private Task mCurrentActivatedTask;
	
	public TaskList(String fileLocation)
	{
		//Load stored tasks
		mDataFile = new File(fileLocation);
		if(mDataFile.exists() && mDataFile.isFile())
		{
			try 
			{
				FileInputStream fis = new FileInputStream(mDataFile);
				Scanner s = new Scanner(fis);
				
				while(s.hasNextLine())
				{
					String line = s.nextLine();
					if(!line.trim().equals(""))
					{
						Task t = new Task(line);
						mTasks.put(t.getName(), t);
					}
				}
				
				s.close();
			}
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			try 
			{
				PrintWriter fileWriter = new PrintWriter(mDataFile);
				fileWriter.write("");
				fileWriter.close();
			}
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public boolean addTask(Task t)
	{
		if(this.hasTask(t.toString()))
		{
			return false;
		}
		
		try 
		{
			FileWriter fWriter = new FileWriter(mDataFile);
			BufferedWriter bWriter = new BufferedWriter(fWriter);
			bWriter.append(t.store());
			bWriter.close();
			fWriter.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		mTasks.put(t.getName(), t);
		return true;
	}
	
	public Task getCurrentActivatedTask()	
	{
		return mCurrentActivatedTask;
	}
	
	public boolean hasTask(String t)
	{
		return mTasks.containsKey(t);
	}
	
	public Task getTask(String name)
	{
		return mTasks.get(name);
	}

	public Iterator<String> nameIterator()
	{
		return mTasks.keySet().iterator();
	}
	
	/**
	 * Sets the active task and set previously active task to finished state 
	 *
	 * @param name Name of the task
	 * 
	 * @return The task that was previously active
	 */
	public Task setActiveTask(String name)
	{
		if(!mTasks.containsKey(name))
		{
			return null;
		}
		
		mTasks.get(name).setStatus(Status.ACTIVATED);
		
		if(mCurrentActivatedTask != null)
		{
			mCurrentActivatedTask.setStatus(Status.FINISHED);	
		}
		
		
		return mCurrentActivatedTask;
	}
	
	public void finishTask(String name)
	{
		Task t = mTasks.get(name);
		
		if(t != null)
		{
			if(t == mCurrentActivatedTask)
			{
				mCurrentActivatedTask = null;
			}
			
			t.setStatus(Status.FINISHED);
		}
	}
}
