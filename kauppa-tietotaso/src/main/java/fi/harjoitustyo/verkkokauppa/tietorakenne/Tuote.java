package fi.harjoitustyo.verkkokauppa.tietorakenne;



import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import org.hibernate.validator.NotNull;




@Entity
@Table(name = "Tuote")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public class Tuote extends Entiteetti {

  /**
	 * 
	 */
 private static final long serialVersionUID = 1L;

 @NotNull
 @Column(name = "tuoteryhma", nullable = true)
 private String tuoteryhma;
 
 
 @NotNull
 @Column(name = "nimi", nullable = false)
 private String nimi;

 
 @NotNull
 @Column(name = "hinta", nullable = true)
 private double hinta;
 
 @NotNull
 @Column(name = "alv", nullable = true)
 private int alv;
 
 @NotNull
 @Column(name = "kpl", nullable = false)
 private int kpl;
 
 
 

 
 
 
 public Tuote(){
	 
 }
 
 
 public Tuote(String nimi, int kpl){
	 this.nimi=nimi;
	 this.kpl=kpl;
	 
 }
 
 public void setNimi(String nimi){
	 this.nimi=nimi;
 }
 
 public String getNimi(){
	 return nimi;
 }
 
 public void setKpl(int kpl){
	 this.kpl=kpl;
 }
 
 public int getKpl(){
	 return kpl;
 }
 
 public void vahennakpl(int kpl){
	 this.kpl -=kpl;
 }
 
 public void setHinta(double hinta){
	 this.hinta=hinta;
 }
 
 public double getHinta(){
	 return hinta;
 }	 
 
 public void setAlv(int alv){
 	this.alv=alv;
 	}
 public int getAlv(){
	 return this.alv;
 	 
 }
 
 
 public void setTuoteryhma(String tuoteryhma){
	 	this.tuoteryhma=tuoteryhma;
	 	}
	 public String getTuoteryhma(){
		 return this.tuoteryhma;
	 	 
	 }


	
 
 
   



}