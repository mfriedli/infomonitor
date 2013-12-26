package ch.friedli.infosystem.message.event.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy; 
import java.lang.annotation.Target;
import javax.inject.Qualifier;

/**
 * This is the Totomat event classifier
 * 
 * @author  Michael Friedli
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
public @interface WBTotomatEvent {
    
}
