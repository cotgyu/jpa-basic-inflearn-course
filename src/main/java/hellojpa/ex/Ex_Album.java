package hellojpa.ex;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class Ex_Album extends Ex_Item{

    private String artist;
}
