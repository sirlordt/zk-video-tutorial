package org.test.zk.manager;

import java.util.Set;

import org.test.zk.dao.CPerson;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class CManagerController extends SelectorComposer<Component> {

	private static final long serialVersionUID = -1591648938821366036L;

	protected ListModelList<CPerson> dataModel = new ListModelList<CPerson>(); 
	
	public class rendererHelper implements ListitemRenderer<CPerson> {
	    
	    /*
	    public void render(Listitem listitem, Object data, int index) {
	        Listcell cell = new Listcell();
	        listitem.appendChild(cell);
	        if (data instanceof String[]){
	            cell.appendChild(new Label(((String[])data)[0].toString()));
	        } else if (data instanceof String){
	            cell.appendChild(new Label(data.toString()));
	        } else {
	            cell.appendChild(new Label("UNKNOW:"+data.toString()));
	        }
	    }
*/

        @Override
        public void render( Listitem listitem, CPerson person, int intIndex ) throws Exception {
            
            try {
                
                Listcell cell = new Listcell();
                
                cell.setLabel( person.getId() );
                
                listitem.appendChild( cell );
                
                cell = new Listcell();
                
                cell.setLabel( person.getFirstName() );
                
                listitem.appendChild( cell );

                cell = new Listcell();
                
                cell.setLabel( person.getLastName() );
                
                listitem.appendChild( cell );
                
            }
            catch ( Exception ex ) {
                
                ex.printStackTrace();
                
            }
            
        }
	    
	}	
	
	@Wire
	Listbox listboxPersons;
	
    @Override
    public void doAfterCompose( Component comp ) {
        
        try {
         
            super.doAfterCompose( comp );
            
            CPerson person01 = new CPerson( "1111", "Juan", "Rojas" );
            CPerson person02 = new CPerson( "2222", "Jose", "Gonzales" );
            CPerson person03 = new CPerson( "3333", "Jose", "Rodriguez" );
            CPerson person04 = new CPerson( "4444", "Tomás", "Moreno" );
            CPerson person05 = new CPerson( "5555", "Loly", "Gómez" );
            
            dataModel.add( person01 );
            dataModel.add( person02 );
            dataModel.add( person03 );
            dataModel.add( person04 );
            dataModel.add( person05 );
            
            //Activa la seleccion multiple de elementos util para operacion de borrado de multiples elementos a la vez
            dataModel.setMultiple( true );
            
            listboxPersons.setModel( dataModel );      
            
            
            listboxPersons.setItemRenderer( new rendererHelper() ); //Aqui lo asociamos al listbox
            
        }
        catch ( Exception e ) {
            
            e.printStackTrace();
            
        }
        
     }    
	
	
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

	@SuppressWarnings( { "rawtypes", "unchecked" } )
    @Listen( "onClick=#buttonDelete" )
	public void onClickbuttonDelete( Event event ) {

        Set<CPerson> selectedItems = dataModel.getSelection();
	    
		if ( selectedItems != null && selectedItems.size() > 0 ) {

		    //Obtenemos el primero de la lista es una lista por que puedes tener seleccion multiple
	        
		    String strBuffer = null;
		    
		    for ( CPerson person : selectedItems ) {
		    
		        if ( strBuffer == null ) {
		        
		            strBuffer = person.getId() + " " + person.getFirstName() + " " + person.getLastName();
		            
		        }
		        else {
	             
		            strBuffer = strBuffer + "\n" + person.getId() + " " + person.getFirstName() + " " + person.getLastName();
	               
		        }   
		       
		    }
		    
		    Messagebox.show( "¿Seguro que desea eliminar los " + Integer.toString( selectedItems.size() ) + " registros?\n" + strBuffer, "Eliminar", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
		        
		        public void onEvent(Event evt) throws InterruptedException {
		        
		            if ( evt.getName().equals( "onOK" ) ) {

                        //Eliminar los registros seleccionados
		                while ( selectedItems.iterator().hasNext() ) {

		                    CPerson person = selectedItems.iterator().next();
		                    
		                    //selectedItems.iterator().remove();
		                    
		                    dataModel.remove( person );
		                    
		                }    
		                
		            } 
		            
		        }
		        
		    });		    
		    
            //Messagebox.show( strBuffer );
		     
		   
		}
		else {
		    
		    Messagebox.show( "No hay seleccion" );
		    
		}
		
	}
	
}
