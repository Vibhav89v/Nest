package generics;

import java.io.File;

public class DeleteFileFromFolder 

{
	public static void deleteFile(String filePath)
    {
    	try{

    		File file = new File(filePath);

    		if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}

    	}catch(Exception e){

    		e.printStackTrace();

    	}

    }

}
