package hellojpa.ex;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Ex_Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    public Ex_Period() {
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    private void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    private void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
