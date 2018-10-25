package csd;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		
		
	
		CorporateSolutionDirectory csd = new CorporateSolutionDirectory();
		
		/* La liste de tout les CSD (Id,Solution)
		 * {
  				"results": [{
    				"Id": 2,
    				"__metadata": {
      					"etag": "W\/\"120\"",
      					"type": "Microsoft.SharePoint.DataService.CAD_SolutionsItem",
      					"uri": "http:\/\/dialogue\/proj\/ITCD-RMTI\/APM-GPA\/_vti_bin\/listData.svc\/CAD_Solutions(2)"
    					},
    				"Solution": "AB Employment Insurance Mailout"
  				}, {...} ]}
		 * 
		 */
		try {
			csd.requestJSON("/CAD_Solutions(4)?$select=Id,Solution");
			
		} catch (IOException e) {

		} 
		
		//csd.requestJSON("/CAD_Solutions?$filter=Id%20eq%204&$select=Id,Solution"); // Avec un filtre sur Id
		//csd.requestJSON("/CAD_Solutions?$filter=Id%20gt%200&%20and%20Id%20leq%20100$select=Id,Solution");//Greater or equal 0 and lower than 100
		//csd.getSolution(4);
			

			
			
			

		

	}

}
