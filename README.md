# Lightweight aspect-oriented method for feature management  

Lightweight method for fast product derivation (mainly from the existing applications) according to the configured variability by using annotations with simple expressions (possibly mapped to feature tree). The solution is demonstrated on the console Battleship game natively written in Java and refactored to AspectJ.   

An example of annotation with the expression:  
![Annotation with the expression](/images/annotationWithExpression.bmp)  



## Setup + launching configured solution/product  

- Pull repository content  
- Install Java and Eclipse (for AspectJ)  
- Optionally configure values in configuration file located in resources/battleshipConfig.json  
- Run the AspectJ program located in src/battleship/Battleship.java  


### AspectJ setup in Eclipse

- Download Eclipse 2021 - 03: -> https://www.eclipse.org/downloads/packages/release/2021-03/r -> Eclipse IDE for Java Developers 328 MB   
- If it is ZIP unpack it to directory: C:\Users\{profile}\eclipse\java-2022-03, otherwise follow instalation steps  
- Launch eclipse.exe positioned in the above directory  
- In menu Help -> Eclipse marketplace ->AJDT -> install     
- In menu File -> new Project -> Other -> AspectJ -> AspectJ Project  



## Product derivation  

Derive product/products with only necessary assets according to the provided configuration.  


### Single product derivation  

- Configuring the following values in the src/derivator/DerivatorBaseConfig.java file:  
  - Set the product name by changing the value of SINGLE_DERIVATION_NAME  
  - Set the folder where derivation should be stored by changing the value of NEW_DERIVATIONS_FOLDER_PATH  
  - Set the path to the application configuration by changing the value RESOURCES_CONFIG_PATH (already set by default as resources/battleshipConfig.json)   
  - Set the file system prefix to the used one - on which the project is located by changing the value USED_FILE_SYSTEM  
  - Set the absolute address to the pulled repository by changing the value BASE_PROJECT_SPL_PATH   
  ![Derivation configuration](/images/derivationConfig.bmp)  
- Set the configuration of the resulting product by changing values in resources/battleshipConfig.json  
![Derivation configuration](/images/appConfig.bmp)  
- Launch the derivation by running the program located in src/derivator/DerivationManager.java  
- Import the created project (Eclipse: File -> import -> Existing Projects Into Workspace -> browse -> select derived app -> Finish)
![Importing derivation](/images/selectingLocationOfDerivedApp.bmp)  
- Reimport the third-party libraries (Eclipse: Left click on project -> Properties -> Java Build Path -> Select libraries -> Remove -> Classpath -> Add External Jars -> Find them -> Open -> Apply and Close)  
![Finding properties](/images/reloadingLibraries.bmp)  
![Reimporting used libraries](/images/removingLibrary.bmp)  
- Run the AspectJ program located in the src/battleship/Battleship.java file of the derived application  
![Running the derived application](/images/runningTheApp.bmp)  


### All possible product derivations

- Configuring the following values in the src/derivator/DerivatorBaseConfig.java file:  
  - Set the folder where all derivations should be stored by changing the value of NEW_DERIVATIONS_FOLDER_PATH  
  - Set the path to the application configuration by changing the value RESOURCES_CONFIG_PATH (already set by default as resources/battleshipConfig.json)  
  - Set the path to the file with configuration of variable boundaries by changing the value VARIABLE_RESTRICTIONS_FILE_PATH (already set by default as resources/variableRestrictions.json") 
  - Set the file system prefix to the used one - on which the project is located by changing the value USED_FILE_SYSTEM  
  - Set the absolute address to the pulled repository by changing the value BASE_PROJECT_SPL_PATH   
  ![Derivation configuration](/images/derivationConfig.bmp)  
- Launch the derivation by running the program located in src/derivator/generator/GeneratorManager.java    
- Import the created project (Eclipse: File -> import -> Existing Projects Into Workspace -> Browse -> select derived app -> Finish)
![Importing derivation](/images/selectingLocationOfDerivedApp.bmp)  
- Reimport the third-party libraries (Eclipse: Left click on project -> Properties -> Java Build Path -> Select libraries -> Remove -> Classpath -> Add External Jars -> Find them -> Open -> Apply and Close)  
![Finding properties](/images/reloadingLibraries.bmp)  
![Reimporting used libraries](/images/removingLibrary.bmp)  
- Run the AspectJ program located in the src/battleship/Battleship.java file of the derived application  
![Running the derived application](/images/runningTheApp.bmp)  

![All derivations](/images/allCases.png) 



## Diagrams  

### Derivation process diagrams  

Structure of the derivator:  
![Class diagram](/images/generatorClassTight.png) 

Derivation process:  
![Sequence diagram](/images/derivationProcessTight.png) 


### Solution diagrams 
Feature model:   
![Mapped variables into the feature model](/images/derivationRules.png)

Refactored solution - class diagram:  
![Class diagram](/images/refactoredSchema.png)