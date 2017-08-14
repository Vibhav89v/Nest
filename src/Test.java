class Test 
{
    public static void main(String args[]) 
    {
      String test = "I am preparing for OCPJP";
      String[] tokens = test.split("\\S");
      System.out.println(tokens);
      System.out.println(tokens.length);
      for(String str:tokens)
    	  System.out.println("@"+str+"@");
    }
  }
