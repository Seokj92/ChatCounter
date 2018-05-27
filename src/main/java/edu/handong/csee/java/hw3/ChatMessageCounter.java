package edu.handong.csee.java.hw3;


import java.io.BufferedReader; 
import java.io.FileNotFoundException; 
import java.io.FileReader; 
import java.io.IOException; 
import java.util.Scanner; 


public class ChatMessageCounter { 
   public static void main(String[] args) { 
      Scanner sc = new Scanner(System.in); 
      System.out.print("filepath : "); 
      String filePath = sc.nextLine(); 
      int count = 0; 
      BufferedReader br = null; 
      try { 
         br = new BufferedReader(new FileReader(filePath)); 
         String line = null; 
         String[] strArr = null; 
         int n = 0; 
         while((line = br.readLine()) != null) { 
            strArr = line.split(" "); 
            n = strArr.length; 
            for(String s:strArr) System.out.println(s); 
            count += n; 
         } 
         } catch (FileNotFoundException fnfe) { 
         fnfe.printStackTrace(); 
         } catch (IOException ioe) { 
         ioe.printStackTrace(); 

         }
   }
   
}
