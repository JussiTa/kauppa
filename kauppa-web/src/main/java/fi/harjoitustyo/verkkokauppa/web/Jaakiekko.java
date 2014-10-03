package fi.harjoitustyo.verkkokauppa.web;

import java.util.Arrays;
import java.util.Collection;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.wicket.PageParameters;


import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;


import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;


import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;


import fi.harjoitustyo.verkkokauppa.palvelut.Verkkokauppapalvelu;
import fi.harjoitustyo.verkkokauppa.palvelut.toteutus.Ostos;

import fi.harjoitustyo.verkkokauppa.tietorakenne.Tuote;

public class Jaakiekko extends Sivupohja  {
	

	  @SuppressWarnings("unused")
	  private static final Log log =
	      LogFactory.getLog(Jaakiekko.class);
	  private static final long serialVersionUID = 1L;

	  @SpringBean(name = "verkkokauppaPalvelu")
	  private Verkkokauppapalvelu palvelu;
	
	  //Lista dropDownChoiseen
	  @SuppressWarnings("rawtypes")
	  private static final List INTEGERS = Arrays.asList(new Integer[] {1,2,3,4});
     
      private Valinta valinta = new Valinta();
      
		  
      
	  /**
	   * Konstruktori, jokaa hakee halutun tuoteryhm‰n palvelun avulla
	   * ja muuntaa tulokset Wicketin web-malliksi.
	   * 
	   * @param sivunParametrit
	   */
	  @SuppressWarnings({ "serial" })
	  public Jaakiekko(
	      final PageParameters sivunParametrit) {
	      super(sivunParametrit);
	            
	      /**
		    * K‰ytet‰‰n modelleja kohdistamaan toiminnot oikeaan tuotteeseen 
		    * J‰‰kiekko tuoteryhm‰st‰ luodaan modelli.
		    */ 
		 	      
	      valinta.setLkm(1);
	   
	   final IModel jaakiekkoModelli = new LoadableDetachableModel() {
         protected Object load() {
             Collection<Tuote> jaakiekko =
       	     palvelu.haetuoteRyhmanMukaan("j‰‰kiekko");
           	 return jaakiekko;

			}
       };
    
       /**
        * Tehd‰‰n j‰‰kiekko modellista list-view, johon lis‰t‰‰n itemit.
        * Modelli huolehtii, ett‰ jokainen olio k‰yd‰‰n l‰pi ja osaa myˆs kohdistaa
        * nappulan toiminnon oikeaan olioon
        */ 
             
        
       ListView jaakiekko2	 = new ListView("jaakiekko2", jaakiekkoModelli) {
          protected void populateItem(final ListItem item) {
             	   
        	  Form form = new Form("form"){
     	           protected void onSubmit(){
     	        	 //Haetaan modellilta oikea objekti
     	        	 Tuote c = (Tuote)item.getModelObject();
     	        	 int lkm =1;  
     	        	 //Haetaan valinnalta valintaikkunassa tehty valinta
     	             lkm = valinta.getLkm();
     	                   
   	                 if(getOstoskori().samaTuote(c.getId())){
   	                    int Id= getOstoskori().haeSamanOstoksenId();
   	                    getOstoskori().lisaaOstoksenLkm(lkm,Id);
   	                     
   	                 }
   	                 else
   	                    getOstoskori().lisaaOstos(new Ostos(c.getId(),lkm));
   	                 //T‰m‰n avulla valintaikunassa n‰kyy 1 taas koriin lis‰‰missen j‰lkeen
   	                 valinta.setLkm(1);          
     	             }
     	       };
                
                              
                
                
             
            	form.add(new Label("id",
                       new PropertyModel(item.getModel(), "id")));
                   
               
      			form.add(new Label("nimi",
                   new PropertyModel(item.getModel(), "nimi")));
                           
      			form.add(new Label("hinta",
                       new PropertyModel(item.getModel(), "hinta")));
                             
      			form.add(new Label("kpl",
	                    new PropertyModel(item.getModel(), "kpl")));
               
          
      			
      	      //Valintaikkuna, miss‰ tunniste "integer", valintaluokasta tehty modelli, mik‰ pit‰‰ sis‰ll‰‰n
      			// myˆs valintaluokan kent‰n lkm, Listasta tehty modelli, mik‰ muodostaa valintaikkunan valinnat
      	       DropDownChoice ddc = 
      	           new DropDownChoice("integer",
      	                   new PropertyModel(valinta, "lkm"),
      	                   new LoadableDetachableModel(){
      	                   @Override
      	                   protected Object load(){
      	        	         return INTEGERS;
      	                   }
      	                 }
      	          );
      	                 
      	          form.add(ddc);
      	          item.add(form);
      	                
      	               
      	              
      	      
              
                	    
             
      	             
              
	      }
        			
       };
            
       add(jaakiekko2);
       

  
	  }
	  	  	
	
   }

	
	  
	     

	 
	    	       

  



 
                       
 
	
	  
 	   
