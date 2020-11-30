# ErrorPrinter
This is a fun little project born out of boredom during one of my lectures. The basic concept is that any error generated by your code is printed on paper and not in the console.

## How to use
Just download the code and open it with your favourite IDE. I have used IntelliJ but it should work everywhere.
Additionally you should change the standard printer.

## Basic function
If an error or exception is thrown in the code the printError() function is called. It will print the provided String out on the defined printer, otherwise it will try and print it on the Microsoft PDF Printer.
If the printer is not available a window is opened so you can choose a printer yourself.

## Additional Notes
This is neither a program you should use without understanding the code, nor is it fully implemented code. This is just a fun side project of myself.
