package org.test.zk;

import java.time.LocalDate;

import org.test.zk.datamodel.TBLPerson;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ItemRenderer;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Selectbox;
import org.zkoss.zul.Window;


public class CTest01Controller extends SelectorComposer<Component> implements ItemRenderer<TBLPerson> {

    private static final long serialVersionUID = -258902408111073465L;
    
    @Wire
    Button buttonTest01;
    
    @Wire( "#buttonMiSuperBotton" )
    Button buttonTest02;
    
    @Wire
    Selectbox selectbox01;

    @Wire
    Selectbox selectbox02;
    
    @Wire
    Window windowTest01;
    
    protected ListModelList<String> dataModel = new ListModelList<String>();

    protected ListModelList<TBLPerson> dataModelPerson = new ListModelList<TBLPerson>();
    
    @Listen( "onClick=#buttonTest01" )
    public void onClickButtonTest01( Event event ) {
        
        windowTest01.setTitle( "Click button test 01" );
        
        buttonTest02.setLabel( "¡Funciona!" );
        
        dataModel.add( "1" );
        dataModel.add( "2" );
        dataModel.add( "3" );
        dataModel.add( "4" );
        dataModel.add( "5" );
        
        dataModelPerson.add( new TBLPerson( "12045698", "Jose", "Romero", 1, LocalDate.parse( "1990-01-01" ), "Sin comentarios"  ) );
        dataModelPerson.add( new TBLPerson( "17045698", "Tomás", "Moreno", 1, LocalDate.parse( "1990-01-01" ), "Sin comentarios" ) );
        dataModelPerson.add( new TBLPerson( "12047698", "Loly", "Gómez", 1, LocalDate.parse( "1990-01-01" ), "Sin comentarios" ) );
        
        selectbox02.setModel( dataModelPerson );
        selectbox02.setItemRenderer( this );
        
        selectbox01.setModel( dataModel );
        dataModel.addToSelection( "1" );
        selectbox01.setSelectedIndex( 0 );
        
    }

    @Listen( "onClick=#buttonMiSuperBotton" )
    public void esteMetodoSeLlamaComoYoQuiera( Event event ) {
        
        buttonTest02.setLabel( "¡Evento!" );
        
    }

    @Listen( "onClick=#buttonTest03" )
    public void onClickButtonTest03( Event event ) {
        
        //buttonTest02.setLabel( "¡Evento!" );
        windowTest01.doModal();
        //buttonTest02.
        
    }
    
    @Listen( "onSelect=#selectbox01" )
    public void onSelectselectbox01( Event event ) {
        
        if ( selectbox01.getSelectedIndex() >= 0 ) {
            
            windowTest01.setTitle( dataModel.get( selectbox01.getSelectedIndex() ) );
            
        }
        
    }
    
    @Listen( "onSelect=#selectbox02" )
    public void onSelectselectbox02( Event event ) {
        
        if ( selectbox02.getSelectedIndex() >= 0 ) {
            
            TBLPerson personSelected = dataModelPerson.get( selectbox02.getSelectedIndex() );
            
            windowTest01.setTitle( personSelected.getId() );
            
        }
        
    }

    @Override
    public String render( Component arg0, TBLPerson arg1, int arg2 ) throws Exception {
        
        return arg1.getFirstName() + " " + arg1.getLastName();
        
    }
    
}
