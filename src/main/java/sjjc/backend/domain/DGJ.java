package sjjc.backend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import org.neo4j.ogm.annotation.*;

@NoArgsConstructor
@Data
@NodeEntity
@Component
public class DGJ implements Entity, Serializable {
    @Id
    @GeneratedValue
    int id;
    @Property(name = "myId")
    String myId;
    @Property(name = "name")
    String name;

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString(){
        return "{id:" +id+
                "myId:" +myId+
                "name:" +name+
                "}";
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (obj instanceof DGJ) {
            return ((DGJ) obj).getId() == this.id;
        }
        return false;
    }

}
