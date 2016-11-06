package org.test.zk.dao;

import java.io.Serializable;

public class CPerson implements Serializable {
    
    private static final long serialVersionUID = -500698741423949696L;

    protected String strId;
    protected String strFirstName;
    protected String strLastName;
    
    //Constructor
    public CPerson( String strId, String strFirstName, String strLastName ) {
        
        this.strId = strId;
        this.strFirstName = strFirstName;
        this.strLastName = strLastName;
        
    }
    
    public String getId() {
        
        return strId;
        
    }
    
    public void setId( String strId ) {
        
        this.strId = strId;
        
    }
    
    public String getFirstName() {
        
        return strFirstName;
        
    }
    
    public void setFirstName( String strFirstName ) {
        
        this.strFirstName = strFirstName;
        
    }
    
    public String getLastName() {
        
        return strLastName;
        
    }
    
    public void setLastName( String strLastName ) {
        
        this.strLastName = strLastName;
        
    }
    
    
}
