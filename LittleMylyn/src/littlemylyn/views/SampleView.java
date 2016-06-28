package littlemylyn.views;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.part.ViewPart;

import littlemylyn.controls.TaskView;
import littlemylyn.data.Task;
import littlemylyn.data.TaskList;
import littlemylyn.dialogs.NewTaskDialog;

public class SampleView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "littlemylyn.views.SampleView";
	private static final String DATA_FILE_LOCATION = ".\\data";
	private TaskList mTaskList;
	private Map<String, TaskView> mTaskViews;

/**
	 * The constructor.
	 */
	public SampleView() 
	{
	}

	public void createPartControl(Composite parent) 
	{
		mTaskList = new TaskList(DATA_FILE_LOCATION);
		mTaskViews = new HashMap<>();

		GridLayout gridLayout = new GridLayout(1, true);
		parent.setLayout(gridLayout);
		
		ToolBar toolBar = new ToolBar(parent, SWT.FLAT | SWT.WRAP | SWT.HORIZONTAL);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		ToolItem addTaskItem = new ToolItem(toolBar, SWT.NONE);
		addTaskItem.setText("Add Task...");
		
		ExpandBar bar = new ExpandBar(parent, SWT.V_SCROLL);
		bar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		addTaskItem.addListener(SWT.Selection, new Listener()
		{	
			@Override
			public void handleEvent(Event event) 
			{
				NewTaskDialog dialog = new NewTaskDialog(getSite().getShell());
				
				if(dialog.open() == Dialog.OK)
				{
					Task task = new Task(dialog.getNameField(), dialog.getTypeField());
					
					if(mTaskList.hasTask(task.getName()))
					{
						MessageBox mbox = new MessageBox(getSite().getShell(), SWT.ICON_ERROR);
						mbox.setMessage("Task already exists.");
						mbox.open();
					}
					else
					{
						addTaskView(task, bar);	
					}	
				}
			}
		});
		
		//Add stored tasks
		Iterator<String> taskIterator = mTaskList.nameIterator();
		while(taskIterator.hasNext())
		{
			String name = taskIterator.next();
			Task task = mTaskList.getTask(name);
			
			addTaskView(task, bar);
		}
	}
	
	private void addTaskView(Task task, ExpandBar bar)
	{
		mTaskList.addTask(task);
		
		TaskView taskView = new TaskView(bar, task);
		mTaskViews.put(task.getName(), taskView);
		taskView.addActivateHandler(new Consumer<TaskView>() 
		{
			@Override
			public void accept(TaskView t) 
			{
				Task prev = mTaskList.setActiveTask(t.getTask().getName());
				
				if(prev != null)
				{
					mTaskViews.get(prev.getName()).refresh();
				}
				t.refresh();
			}
		});
		taskView.addFinishHandler(new Consumer<TaskView>() 
		{
			@Override
			public void accept(TaskView t)
			{
				Task task = t.getTask();
				mTaskList.finishTask(task.getName());
				t.refresh();
			}
		});
		taskView.addRelatedClassHandler(new Consumer<TaskView>() 
		{
			@Override
			public void accept(TaskView t) 
			{
				System.out.println("test");
				InputDialog dlg = new InputDialog(getSite().getShell(), "Add Related Class", "Class Name", "", null);
				if(dlg.open() == InputDialog.OK)
				{
					t.getTask().addRelatedClass(dlg.getValue().replace(";", ""));
					t.refresh();
				}
			}
		});
	}
	
	public void setFocus() 
	{
		
	}
}
