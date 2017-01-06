package org.test.zk.database.datamodel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public interface IAuditableDataModel extends Serializable {
    
    public String getCreatedBy();
    public void setCreatedBy( final String strCreatedBy );

    public LocalDate getCreatedAtDate();
    public void setCreatedAtDate( final LocalDate createdAtDate );

    public LocalTime getCreatedAtTime();
    public void setCreatedAtTime( final LocalTime createdAtTime );
    
    public String getUpdatedBy();
    public void setUpdatedBy( final String strUpdatedBy );

    public LocalDate getUpdatedAtDate();
    public void setUpdatedAtDate( final LocalDate updatedAtDate );

    public LocalTime getUpdatedAtTime();
    public void setUpdatedAtTime( final LocalTime updatedAtTime );
    
}
