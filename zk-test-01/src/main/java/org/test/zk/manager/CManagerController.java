package org.test.zk.manager;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Window;

public class CManagerController extends SelectorComposer<Component> {

	private static final long serialVersionUID = -1591648938821366036L;

	@Listen( "onClick=#buttonAdd" )
	public void onClickbuttonAdd( Event event ) {
		
		//Map arg = new HashMap();
		//arg.put("someName", someValue);
		Window win = (Window) Executions.createComponents( "/dialog.zul", null, null ); //attach to page as root if parent is null
		
		win.doModal();
		
	}

	@Listen( "onClick=#buttonModify" )
	public void onClickbuttonModify( Event event ) {
		
		
		
	}

	@Listen( "onClick=#buttonDelete" )
	public void onClickbuttonDelete( Event event ) {
		
		
		
	}
	
}
