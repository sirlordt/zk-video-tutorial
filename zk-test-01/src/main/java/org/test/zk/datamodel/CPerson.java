package org.test.zk.datamodel;

import java.io.Serializable;
import java.time.LocalDate;

public class CPerson implements Serializable {
    
    private static final long serialVersionUID = -500698741423949696L;

    protected String strId;
    protected String strFirstName;
    protected String strLastName;
    protected int intGender; //0 = Famale 1 = Male
	protected LocalDate birthDate = null;
	protected String strComment;
    
    //Constructor
    public CPerson( String strId, String strFirstName, String strLastName, int intGender, LocalDate birthDate, String strComment ) {
        
        this.strId = strId;
        this.strFirstName = strFirstName;
        this.strLastName = strLastName;
        this.intGender = intGender;
        this.birthDate = birthDate;
        this.strComment = strComment;
        
    }
    
    public CPerson() {
        
        
        
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
    
    public int getGender() {
	
    	return intGender;
	
    }

	public void setGender( int intGender ) {
		
		this.intGender = intGender;
		
	}

	public LocalDate getBirthDate() {
		
		return birthDate;
		
	}

	public void setBirthDate( LocalDate birthDate ) {
		
		this.birthDate = birthDate;
		
	}

	public String getComment() {
		
		return strComment;
		
	}

	public void setComment( String strComment ) {
		
		this.strComment = strComment;
		
	}
    
    
}
