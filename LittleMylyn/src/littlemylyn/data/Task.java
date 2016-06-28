package littlemylyn.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Task 
{
	private static final Map<Status, String> STATUS_TEXT = new HashMap<>();
	private static final Map<String, Status> TEXT_STATUS = new HashMap<>();
	static
	{
		STATUS_TEXT.put(Status.NEW, "New");
		STATUS_TEXT.put(Status.ACTIVATED, "Activated");
		STATUS_TEXT.put(Status.FINISHED, "Finished");
		
		for (Status s : STATUS_TEXT.keySet())
		{
			TEXT_STATUS.put(STATUS_TEXT.get(s), s);
		}
	}
	
	private Status mStatus;
	private String mName;
	private String mType;
	private Set<String> mRelatedClasses = new HashSet<>();
	
	public Task(String name, String type)
	{
		mStatus = Status.NEW;
		mName = name.replace(";", "");
		mType = type.replace(";", "");
	}
	
	public Status getStatus()
	{
		return mStatus;
	}
	
	public String getStatusText()
	{
		return STATUS_TEXT.get(this.getStatus());
	}
	
	public boolean setStatus(Status s)
	{
		if(s == Status.NEW)
		{
			return false;
		}
		
		mStatus = s;
		
		return true;
	}
	
	public String getName()
	{
		return mName;
	}
	
	public String getType()
	{
		return mType;
	}
	
	public boolean addRelatedClass(String className)
	{
		if(this.hasRelatedClass(className))
		{
			return false;
		}
		
		mRelatedClasses.add(className);
		return true;
	}
	
	public boolean hasRelatedClass(String className)
	{
		return mRelatedClasses.contains(className);
	}
	
	public Set<String> getRelatedClasses()
	{
		return mRelatedClasses;
	}
	
	public Task(String storedString)
	{
		String str[] = storedString.split(";");
		
		mName = str[0];
		mStatus = TEXT_STATUS.get(str[1]);
		mType = str[2];
		for(int i = 3; i < str.length; ++i)
		{
			mRelatedClasses.add(str[i]);
		}
	}
	
	public String store()
	{
		System.out.println(mRelatedClasses.size());
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(System.lineSeparator());
		sb.append(this.getName() + ";");
		sb.append(this.getStatusText() + ";");
		sb.append(this.getType() + ";");
		for (String className : mRelatedClasses) 
		{
			System.out.println("test");
			sb.append(className + ";");
		}
		sb.deleteCharAt(sb.length() - 1);
		
		return sb.toString();
	}
}
