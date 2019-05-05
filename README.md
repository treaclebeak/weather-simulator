## Weather Simulator

## Run

##### Prerequisite

[Java 1.8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) must be installed and if you want to build from source  [maven](https://maven.apache.org/) must be installed

##### The jar file

Use the precompiled jar in the `bin` folder othwerwise build the application from source with maven

`mvn clean package`

##### Program options

1. Mandatory: The name of a file  containing city data. Use `cities.csv` (a dataset of ~10K cities) or `selected_cities.csv` (smaller dataset selected cities)
2. Optional: An integer n that will add n months to the current date. By default the calculations use the current date.

##### Execute the jar file with java 

If using the precompiled jar file  

`java -jar bin/weather-1.0-SNAPSHOT.jar <file name>` 

if building from source

`java -jar target/weather-1.0-SNAPSHOT.jar <file name>`

## The Problem Space

A toy simulation of the environment (taking into account things like atmosphere, topography, geography, oceanography, or similar) that evolves over time. 
The program outputs data for 10 random cities in the following sample format

```
    Sydney|-33.86,151.21,39|2015-12-23T05:02:12Z|Rain|+12.5|1004.3|97
    Melbourne|-37.83,144.98,7|2015-12-24T15:30:55Z|Snow|-5.3|998.4|55
    Adelaide|-34.92,138.62,48|2016-01-03T12:35:37Z|Sunny|+39.4|1114.1|12
```

where

* Location is an optional label describing one or more positions,

* Position is a comma-separated triple containing latitude, longitude, and elevation in metres above sea level,
* Local time is an ISO8601 date time,
* Conditions is either Snow, Rain, Sunny,
* Temperature is in °C,
* Pressure is in hPa, and
* Relative humidity is a %.


## The Solution Space

The program needs to calculate the following parameters,

1. Temperature
2. Pressure
3. Humidity
4. Conditions(Rain/Snow/Sunny).

The solution options are

1. Code the solution using equations/algorithms to directly produce the values based on the inputs.
2. Code the solution using machine learning approaches to predict the values based on models - in this case i think supervised learning models and classifiers.

I do not have a background in machine learning and after spending some time researching options like WEKA and Spark decided that this was not a viable approach for me within the time constraints, so I have used option 1.

#### Temperature

Calculated from the date and the location on earth (including elevation and distance from the sea) according to the formulas in this document 

http://www-das.uwyo.edu/~geerts/cwx/notes/chap16/geo_clim.html


#### Pressure

Calculated from the elevation of the location and the temperature according to the barometric formula described in https://en.wikipedia.org/wiki/Atmospheric_pressure

#### Humidity

Naive humidity calculation with random dew point based on https://en.wikipedia.org/wiki/Dew_point

#### Conditions

Extremely simple hard coded heuristics to determine weather condition from temperature, humidity and air pressure