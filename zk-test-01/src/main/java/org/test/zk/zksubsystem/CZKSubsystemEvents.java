package org.test.zk.zksubsystem;

import java.util.List;

import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.DesktopCleanup;
import org.zkoss.zk.ui.util.DesktopInit;
import org.zkoss.zk.ui.util.ExecutionCleanup;
import org.zkoss.zk.ui.util.ExecutionInit;
import org.zkoss.zk.ui.util.SessionCleanup;
import org.zkoss.zk.ui.util.SessionInit;
import org.zkoss.zk.ui.util.WebAppCleanup;
import org.zkoss.zk.ui.util.WebAppInit;

public class CZKSubsystemEvents implements DesktopInit, DesktopCleanup, SessionInit, SessionCleanup, WebAppInit, WebAppCleanup, ExecutionInit, ExecutionCleanup {

    @Override
    public void cleanup( Execution execution0, Execution execution1, List<Throwable> arg2 ) throws Exception {
        
        System.out.println( "Execution cleanup" );
        
    }

    @Override
    public void init( Execution execution0, Execution execution1 ) throws Exception {
        
        System.out.println( "Execution cleanup" );
        
    }

    @Override
    public void cleanup( WebApp webApp ) throws Exception {
        
        System.out.println( "Web app cleanup" ); //Este evento se llama cuando el servidor de aplicaciones termina (Civilizadamente) el jetty en nuestro caso
        
    }

    @Override
    public void init( WebApp webApp ) throws Exception {
        
        System.out.println( "Web app init" ); //Este evento se llama cuando el servidor de aplicaciones inicia el jetty en nuestro caso
        
    }

    @Override
    public void cleanup( Session session ) throws Exception {
        
        System.out.println( "Session cleanup" );
        
    }

    @Override
    public void init( Session session, Object object ) throws Exception {
        
        System.out.println( "Session init" );
        
    }

    @Override
    public void cleanup( Desktop desktop ) throws Exception {
        
        System.out.println( "Desktop cleanup" );
        
    }

    @Override
    public void init( Desktop desktop, Object object ) throws Exception {
        
        System.out.println( "Desktop init" );
        
    }
    
}
