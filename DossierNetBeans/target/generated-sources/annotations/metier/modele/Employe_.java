package metier.modele;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-11T21:40:53")
@StaticMetamodel(Employe.class)
public class Employe_ extends Personne_ {

    public static volatile SingularAttribute<Employe, Integer> horaireEntree;
    public static volatile SingularAttribute<Employe, Integer> horaireSortie;
    public static volatile SingularAttribute<Employe, Boolean> dispo;

}