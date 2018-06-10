# route-finder
A simple CLI application which finds a route between two cities.

## Checkout project
Use this [link](https://github.com/ivannizhenko/route-finder/archive/master.zip) to download zip.
Use below command to clone repository:
```sh
git clone https://github.com/ivannizhenko/route-finder.git
```

## Run dependencies
* Java 8
* Text file with configured city connections

## Input file
Simple text file contains city name pairs, which represent connection between cities.
City pairs are separated with new line. Cities are separated by comma.
Here is content of example input file:
```sh
Washington,Baltimore
Baltimore,Philadelphia
Philadelphia,Pittsburgh
Philadelphia,Newark
Newark,New York
New York,Boston
```

## Run
Download or checkout project from GitHub.
In order to start application, run below example command:
```sh
java -jar "app/route-finder-1.0.0-SNAPSHOT.jar" -c1 "New York" -c2 "Washington" -f "cities.txt"
```
where
* -c1 argument is a first city
* -c2 argument is a second city
* -f argument is a path to a file which contains city connection pairs

## Output
Here is example output if cities are connected:
```sh
ROUTE: [New York, Newark, Philadelphia, Baltimore, Washington]
Is New York connected to Washington? - true
```
And here is example output if cities are not connected, or input file is empty:
```sh
ROUTE: []
Is New York connected to Tokyo? - false
```

## Build dependencies
* Maven 3

## Build
route-finder application is built with Maven.
In order to build application, run below command:
```sh
mvn clean package
```
You'll find a new uber executable jar in "target" directory.
The jar file contains all the dependencies and can be executed independently.