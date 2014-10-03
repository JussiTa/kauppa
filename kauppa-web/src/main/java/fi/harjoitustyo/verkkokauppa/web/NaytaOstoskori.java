package fi.harjoitustyo.verkkokauppa.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.wicket.PageParameters;


import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;



import org.apache.wicket.spring.injection.annot.SpringBean;

import fi.harjoitustyo.verkkokauppa.palvelut.Verkkokauppapalvelu;
import fi.harjoitustyo.verkkokauppa.palvelut.toteutus.Ostos;
import fi.harjoitustyo.verkkokauppa.tietorakenne.Tuote;



public class NaytaOstoskori extends Sivupohja {
	   
	@SuppressWarnings("unused")
	  private static final Log log =
	      LogFactory.getLog(Jaakiekko.class);
	  private static final long serialVersionUID = 1L;

	  @SpringBean(name = "verkkokauppaPalvelu")
	  private Verkkokauppapalvelu palvelu;
	  private Valinta valinta = new Valinta();
	  private Tuote tuote;
	  private Ostos ostos;
	  private List<Tuote> tuotteet = new ArrayList<Tuote>();
	  private double summa =0;
	  
	  
	  @SuppressWarnings("rawtypes")
	  private static final List INTEGERS = Arrays.asList(new Integer[] {1,2,3,4});
	   
	  public NaytaOstoskori(PageParameters sivunParametrit) {
		   super(sivunParametrit);
		 
		   //Asetetaan n‰kym‰‰n 1 valintaikkunaan
            valinta.setLkm(1);
           
            Iterator<Ostos> ostositer = getOstoskori().iterator();
            while(ostositer.hasNext()) {
 			   ostos=ostositer.next();
               tuote = palvelu.haePerusavaimella(ostos.getId());
			   tuotteet.add(tuote);
			   summa += ostos.getKpl()*tuote.getHinta();
			} 
			        

     	   final IModel ostoskoriModelli = new LoadableDetachableModel() {
             
			private static final long serialVersionUID = 1L;

			protected Object load() {
                           	  
                	 return tuotteet;

     			}
            };
         
            ListView ostoskori2	 = new ListView("ostoskori2", ostoskoriModelli) {
               
				private static final long serialVersionUID = 1L;

				protected void populateItem(final ListItem item) {
                   	   
                  	     
          	    Form form = new Form("form"){
        	       
					private static final long serialVersionUID = 1L;

					//T‰m‰ tuotteen v‰hent‰mist‰ varten
					protected void onSubmit(){
        	        	 //Haetaan modellilta oikea objekti
        	        	 Tuote c = (Tuote)item.getModelObject();
        	        	 int lkm =1;  
        	        	 //Haetaan valinnalta valintaikkunassa tehty valinta
        	             lkm = valinta.getLkm();
        	             //V‰hennet‰‰n summasta valinta lkm * tuotteen hinta      
      	                 summa -= getOstoskori().haeOstoksenLkm(c.getId())*c.getHinta();
      	                
      	                 getOstoskori().muutaOstoksenLkm(lkm,c.getId());
      	                 summa += lkm*c.getHinta();
      	                    
      	                 setResponsePage(NaytaOstoskori.class);
        	           }
        	        };
                   
                  	    form.add(new Label("id",
                             new PropertyModel(item.getModel(), "id")));
                         
                     
            			form.add(new Label("nimi",
                         new PropertyModel(item.getModel(), "nimi")));
                                 
            			form.add(new Label("hinta",
                             new PropertyModel(item.getModel(), "hinta")));
                                   
            			
            			Tuote c = (Tuote)item.getModelObject();
            			    int lkm= getOstoskori().haeOstoksenLkm(c.getId());
           	            form.add(new Label("kpl", String.valueOf(lkm)));
            			
            			 
                      
            			
            	      //Valintaikkuna, miss‰ tunniste "integer", valintaluokasta tehty modelli, mik‰ pit‰‰ sis‰ll‰‰n
            			// myˆs valintaluokan kent‰n lkm, Listasta tehty modelli, mik‰ muodostaa valintaikkunan valinnat
            	       DropDownChoice ddc = 
            	           new DropDownChoice("integer",
            	                   new PropertyModel(valinta, "lkm"),
            	                   new LoadableDetachableModel(){
            	                  
									private static final long serialVersionUID = 1L;

								@Override
            	                   protected Object load(){
            	        	         return INTEGERS;
            	                   }
            	                 }
            	          );
            	                 
            	                	              
            	    form.add(ddc);
            	    
            	    item.add(new Link("poista") {
      	                private static final long serialVersionUID = 1L;

      					public void onClick() {
      						Tuote c = (Tuote)item.getModelObject();
      						getOstoskori().poistaOstosId(c.getId());
      						setResponsePage(NaytaOstoskori.class);
      	                     
      	                 }
      	            });
                    
                      	    
                   item.add(form);
                    
      			  
         
                    
      	      }
              			
             };
                  
             add(ostoskori2);
                 
             add(new BookmarkablePageLink("teetilaus",
   		 	      TeeTilaus.class));  
             add(new Label("summa", String.valueOf(summa)));
             
	  	       
   } 
	   
	   
	   
	   
}        
 
	  
 
			

	
	  
		   
	 








