package org.test.zk.database.datamodel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class TBLOperator extends CAuditableDataModel implements Serializable {

    private static final long serialVersionUID = -460145854236713435L;
    
    protected String strId;
    protected String strName;
    protected String strRole;
    protected String strPassword;
    protected String strComment;
    
    protected String strDisabledBy;
    protected LocalDate disabledAtDate;
    protected LocalTime disabledAtTime;
    
    protected LocalDate lastLoginAtDate;
    protected LocalTime lastLoginAtTime;
    
    public String getId() {
        
        return strId;
        
    }
    
    public void setId( String strId ) {
        
        this.strId = strId;
        
    }
    
    public String getName() {
        
        return strName;
        
    }
    
    public void setName( String strName ) {
        
        this.strName = strName;
        
    }
    
    public String getRole() {
        
        return strRole;
        
    }
    
    public void setRole( String strRole ) {
        
        this.strRole = strRole;
        
    }
    
    public String getPassword() {
        
        return strPassword;
        
    }
    
    public void setPassword( String strPassword ) {
        
        this.strPassword = strPassword;
        
    }
    
    public String getComment() {
        
        return strComment;
        
    }
    
    public void setComment( String strComment ) {
        
        this.strComment = strComment;
        
    }
    
    public String getDisabledBy() {
        
        return strDisabledBy;
        
    }
    
    public void setDisabledBy( String strDisabledBy ) {
        
        this.strDisabledBy = strDisabledBy;
        
    }
    
    public LocalDate getDisabledAtDate() {
        
        return disabledAtDate;
        
    }
    
    public void setDisabledAtDate( LocalDate disabledAtDate ) {
        
        this.disabledAtDate = disabledAtDate;
        
    }
    
    public LocalTime getDisabledAtTime() {
        
        return disabledAtTime;
        
    }
    
    public void setDisabledAtTime( LocalTime disabledAtTime ) {
        
        this.disabledAtTime = disabledAtTime;
        
    }
    
    public LocalDate getLastLoginAtDate() {
        
        return lastLoginAtDate;
        
    }
    
    public void setLastLoginAtDate( LocalDate lastLoginAtDate ) {
        
        this.lastLoginAtDate = lastLoginAtDate;
        
    }
    
    public LocalTime getLastLoginAtTime() {
        
        return lastLoginAtTime;
        
    }
    
    public void setLastLoginAtTime( LocalTime lastLoginAtTime ) {
        
        this.lastLoginAtTime = lastLoginAtTime;
        
    }
    
}
