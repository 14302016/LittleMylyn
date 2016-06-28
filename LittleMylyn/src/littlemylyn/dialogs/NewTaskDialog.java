package littlemylyn.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewTaskDialog extends Dialog 
{
	private String mNameField;
	private String mTypeField;
	
	public NewTaskDialog(Shell parentShell) 
	{
		super(parentShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		this.getShell().setText("New Task");
		
		Composite p = (Composite) super.createDialogArea(parent);
		
		Composite container = new Composite(p, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		container.setLayout(new GridLayout(2, false));
		
		Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText("Name");
		nameLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		
		Text nameInput = new Text(container, SWT.NONE);
		nameInput.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		nameInput.addModifyListener(new ModifyListener()
		{	
			@Override
			public void modifyText(ModifyEvent e) 
			{
				mNameField = nameInput.getText();
			}
		});
		
		Label typeLabel = new Label(container, SWT.NONE);
		typeLabel.setText("Type");
		typeLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		
		Text typeInput = new Text(container, SWT.NONE);
		typeInput.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		typeInput.addModifyListener(new ModifyListener() 
		{	
			@Override
			public void modifyText(ModifyEvent e) 
			{
				mTypeField = typeInput.getText();
			}
		});
		
		return container;
	}
	
	public String getNameField() { return mNameField; }
	public String getTypeField() { return mTypeField; }
}
