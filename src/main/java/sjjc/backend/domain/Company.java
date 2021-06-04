package sjjc.backend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
@Component
@NodeEntity
public class Company implements Entity, Serializable {
    @Id
    @GeneratedValue
    int id;
    @Property(name = "myId")
    String myId;
    @Property(name = "orgId")
    String orgId;
    @Property(name = "stockId")
    String stockId;
    @Property(name = "orgName")
    String name;
    @Property(name = "orgShortName")
    String orgShortName;
    @Property(name = "regAsset")
    String regAsset;
    @Property(name = "staffNum")
    String staffNum;
    @Property(name = "telephone")
    String telephone;
    @Property(name = "postcode")
    String postcode;
    @Property(name = "fax")
    String fax;
    @Property(name = "email")
    String email;
    @Property(name = "orgWebsite")
    String orgWebsite;
    @Property(name = "regAddressCn")
    String regAddressCn;
    @Property(name = "typeName")
    String typeName;
    @Property(name = "isStateOwned")
    String isStateOwned;
    @Property(name = "inDegree")
    String inDegree;
    @Property(name = "outDegree")
    String outDegree;
    @Property(name = "stateOwnedRatio")
    String stateOwnedRatio;

    double safety;

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "{ id:" + id +
                " name:" + name +
                " orgShortName:" + orgShortName +
                "}";
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (obj instanceof Company) {
            return ((Company) obj).getId() == this.id;
        }
        return false;
    }

}
