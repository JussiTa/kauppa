package fi.harjoitustyo.verkkokauppa.tietorakenne.validaatio;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

/**
 * Annotaatio viitenumerojen validointia varten.
 * 
 * @author kuha
 */
@ValidatorClass(ViitenumeroValidaattori.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Viitenumero {
  String message() default "Viitenumeron merkkijono on virheellinen.";
}
