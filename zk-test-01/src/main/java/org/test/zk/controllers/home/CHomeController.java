package org.test.zk.controllers.home;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;


public class CHomeController extends SelectorComposer<Component> {

    private static final long serialVersionUID = 1040032932048818844L;

    @Listen( "onClick = #includeNorthContent #buttonLogout" )  
    public void onClickbuttonLogout( Event event ) {
        
        System.out.println( "Logout" );
        
    }
    
}
