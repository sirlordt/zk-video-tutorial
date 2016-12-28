package org.test.zk.datamodel;

import java.time.LocalDate;
import java.time.LocalTime;


public class CAuditableDataModel implements IAuditableDataModel {
    
    private static final long serialVersionUID = -469538587994589550L;

    protected String strCreatedBy = null;
    protected LocalDate createdAtDate = null;
    protected LocalTime createdAtTime = null;

    protected String strUpdatedBy = null;
    protected LocalDate updatedAtDate = null;
    protected LocalTime updatedAtTime = null;
    
    @Override
    public String getCreatedBy() {
        
        return strCreatedBy;
        
    }
    
    @Override
    public void setCreatedBy( String strCreatedBy ) {
        
        this.strCreatedBy = strCreatedBy;
        
    }
    
    @Override
    public LocalDate getCreatedAtDate() {
        
        return createdAtDate;
        
    }
    
    @Override
    public void setCreatedAtDate( LocalDate createdAtDate ) {
        
        this.createdAtDate = createdAtDate;
        
    }
    
    @Override
    public LocalTime getCreatedAtTime() {
        
        return createdAtTime;
        
    }
    
    @Override
    public void setCreatedAtTime( LocalTime createdAtTime ) {
        
        this.createdAtTime = createdAtTime;
        
    }
    
    @Override
    public String getUpdatedBy() {
        
        return strUpdatedBy;
        
    }
    
    @Override
    public void setUpdatedBy( String strUpdatedBy ) {
        
        this.strUpdatedBy = strUpdatedBy;
        
    }
    
    @Override
    public LocalDate getUpdatedAtDate() {
        
        return updatedAtDate;
        
    }
    
    @Override
    public void setUpdatedAtDate( LocalDate updatedAtDate ) {
        
        this.updatedAtDate = updatedAtDate;
        
    }
    
    @Override
    public LocalTime getUpdatedAtTime() {
        
        return updatedAtTime;
        
    }
    
    @Override
    public void setUpdatedAtTime( LocalTime updatedAtTime ) {
        
        this.updatedAtTime = updatedAtTime;
        
    }
    
}
