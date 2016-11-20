package org.test.zk.dialog;

import org.test.zk.dao.CPerson;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Selectbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


public class CDialogController extends SelectorComposer<Component> {

    private static final long serialVersionUID = -8977563222707532143L;
    
    protected ListModelList<String> dataModel = new ListModelList<String>();
    
    @Wire
    Window windowPerson;
    
    @Wire
    Label labelId;
    
    @Wire
    Textbox textboxId;
    
    @Wire
    Label labelFirstName;
    
    @Wire
    Textbox textboxFirstName;
    
    @Wire
    Label labelLastName;
    
    @Wire
    Textbox textboxLastName;
    
    @Wire
    Label labelGender;
    
    @Wire
    Selectbox selectboxGender;
    
    @Wire
    Label labelBirdDate;

    @Wire
    Datebox dateboxBirdDate;
    
    @Wire
    Label labelComment;

    @Wire
    Textbox textboxComment;
    
    @Override
    public void doAfterCompose( Component comp ) {
        
        try {
         
            super.doAfterCompose( comp );
            
            dateboxBirdDate.setFormat( "dd/MM/yyyy" );
            
            dataModel.add( "Femenino" );
            dataModel.add( "Masculino" );
            
            selectboxGender.setModel( dataModel );
            selectboxGender.setSelectedIndex( 0 );
            
            dataModel.addToSelection( "Femenino" );
            
            final Execution execution = Executions.getCurrent();

            CPerson personToModify = (CPerson) execution.getArg().get( "personToModify" );

            textboxId.setValue( personToModify.getId() );
            textboxFirstName.setValue( personToModify.getFirstName() );
            textboxLastName.setValue( personToModify.getLastName() );
            
            if ( personToModify.getGender() == 0 ) {
            
                dataModel.addToSelection( "Femenino" ); //Seleccionamos en el modelo el genero 
                        
            }
            else {
            	
                dataModel.addToSelection( "Masculino" ); //Seleccionamos en el modelo el genero 
            	
            }
            
            dateboxBirdDate.setValue(  java.sql.Date.valueOf( personToModify.getBirthDate() ) );
            textboxComment.setValue( personToModify.getComment() );
            
        }
        catch ( Exception e ) {
            
            e.printStackTrace();
            
        }
        
     }    
    
    @Listen( "onClick=#buttonAccept" )
    public void onClickButtonAceptar( Event event ) {
        
        //Messagebox.show( "Id=" + textboxId.getValue() + " firstName=" + textboxFirstName.getValue() + " lastName=" + textboxLastName.getValue() + " comment=" + textboxComment.getValue() , "Accept", Messagebox.OK, Messagebox.INFORMATION );
        
        //System.out.println( "Hello Accept" );
        
    	windowPerson.detach();
    	
    }

    @Listen( "onClick=#buttonCancel" )
    public void onClickButtonCancelar( Event event ) {
        
        //Messagebox.show( "Cancel", "Cancel", Messagebox.OK, Messagebox.EXCLAMATION );
        //System.out.println( "Hello Cancel" );
    	windowPerson.detach();
        
    }
    
}
