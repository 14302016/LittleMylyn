package littlemylyn.controls;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import littlemylyn.data.Task;

public class TaskView
{	
	private Composite mMainContainer;
	
	private Label mTypeLabel;
	private Label mStatusLabel;
	
	private ExpandItem mItem;
	
	private Task mTask;
	
	private Set<Consumer<TaskView>> mOnActivateEvent = new HashSet<>();
	private Set<Consumer<TaskView>> mOnFinishEvent = new HashSet<>();
	private Set<Consumer<TaskView>> mOnAddRelatedClass = new HashSet<>();
	
	private Label mRelatedClassLabel;
	private List mRelatedClassesList;
	
	public TaskView(ExpandBar parent, Task task) 
	{
		mTask = task;
		
		mItem = new ExpandItem(parent, SWT.NONE);
		mItem.setText(task.getName());
		
		mMainContainer = new Composite(parent, SWT.NONE);
		mMainContainer.setLayout(new GridLayout(1, false));
		
		ToolBar toolBar = new ToolBar(mMainContainer, SWT.NONE);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, true));
		
		ToolItem activateBtn = new ToolItem(toolBar, SWT.NONE);
		activateBtn.setText("Activate");
		activateBtn.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event) 
			{
				callEvent(mOnActivateEvent);
			}
		});
		
		ToolItem finishBtn = new ToolItem(toolBar, SWT.NONE);
		finishBtn.setText("Finish");
		finishBtn.addListener(SWT.Selection,  new Listener()
		{
			@Override
			public void handleEvent(Event event) 
			{
				callEvent(mOnFinishEvent);
			}
		});
		ToolItem addClassBtn = new ToolItem(toolBar, SWT.NONE);
		addClassBtn.setText("Add Related Class");
		addClassBtn.addListener(SWT.Selection, new Listener() 
		{	
			@Override
			public void handleEvent(Event event) 
			{
				callEvent(mOnAddRelatedClass);		
			}
		});
		
		mTypeLabel = new Label(mMainContainer, SWT.NONE);
		mStatusLabel = new Label(mMainContainer, SWT.NONE);
		
		mRelatedClassLabel = new Label(mMainContainer, SWT.NONE);
		mRelatedClassesList = new List(mMainContainer, SWT.SINGLE);
		
		this.refresh();
	}
	
	private void callEvent(Set<Consumer<TaskView>> eventHandlers)
	{
		for (Consumer<TaskView> handler : eventHandlers) 
		{
			handler.accept(this);
		}
	}
	
	public Task getTask()
	{
		return mTask;
	}
	
	public void refresh()
	{
		mTypeLabel.setText("Type: " + mTask.getType());
		mTypeLabel.setSize(mTypeLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		mStatusLabel.setText("Status: " + mTask.getStatusText());
		mStatusLabel.setSize(mStatusLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		mRelatedClassLabel.setText("Related Classes (" + mTask.getRelatedClasses().size() + ")");
		
		mRelatedClassesList.removeAll();
		
		for (String relatedClass : mTask.getRelatedClasses()) 
		{
			mRelatedClassesList.add(relatedClass);
		}
		
		mItem.setControl(mMainContainer);
		mItem.setHeight(mMainContainer.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
	}
	
	public void addActivateHandler(Consumer<TaskView> handler)
	{
		mOnActivateEvent.add(handler);
	}
	
	public void addFinishHandler(Consumer<TaskView> handler)
	{
		mOnFinishEvent.add(handler);
	}
	
	public void addRelatedClassHandler(Consumer<TaskView> handler)
	{
		mOnAddRelatedClass.add(handler);
	}
}

