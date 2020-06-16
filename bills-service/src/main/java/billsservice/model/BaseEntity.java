package billsservice.model;

import lombok.Data;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;


import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data

public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userUuid;

    private Date createDate;

    private Boolean deleted;

    @PrePersist
    public void onPersist() {
        final DateTime nowDt = new DateTime(DateTimeZone.UTC);
        final Date current = new Date(nowDt.getMillis());
        setCreateDate(current);
    }
}
