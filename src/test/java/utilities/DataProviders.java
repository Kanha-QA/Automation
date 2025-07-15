package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	//DataProvider 1
	
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		String path = ".\\testData\\Open_Cart Data.xlsx";		//taking Excel file from testData
		
		ExcelUtility xlutil = new ExcelUtility(path);	//creating an object for XLUtility
		
		int totalrows = xlutil.getRowCount("Sheet1"); // This returns lastRowNum (e.g., 5 for 6 rows incl. header)
		int totalcols = xlutil.getCellCount("Sheet1",1);
		
		String logindata[][] = new String[totalrows][totalcols];//created for two dimensional array which can store data
		
		for(int i=1; i<=totalrows; i++)	   //1  //read the data from Excel storing in two dimensional array
		{
			for(int j=0; j<totalcols; j++)		//0    i is rows j is col
			{
				logindata[i-1][j] = xlutil.getCelldata("Sheet1", i, j);
			}
		}
	return logindata; 	//returning two dimension array
	}

}
