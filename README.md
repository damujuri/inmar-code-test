# inmar-code-test
Steps to run

1. Check out the code from IDE (Intelli J or Eclipse)
2. Open run configuration for the main java class file (PersonImporter) and provide input from command line argument. i.e ./files/input.csv or input.json or input.txt as per your choice
3. Finnaly run java main class


Positive scenario

As per the file data list of persons information will be displayed in console. 
Example

FirstName : Deva, Last Nme:  Amujuri
FirstName : Chandara, Last Nme:  Muvvala
FirstName : Siva, Last Nme:  Prasad

Error scenarios
1. If you are not privided any input from the command line argument it will throw an custom exception with message FileNotFound Exception 
2. If you are file format other than JSON, CSV, TXT it wil throw an exception with message invalid file format.
