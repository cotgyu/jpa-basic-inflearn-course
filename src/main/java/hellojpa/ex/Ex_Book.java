package hellojpa.ex;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
public class Ex_Book extends Ex_Item{

    private String autor;

    private String isbn;
}
