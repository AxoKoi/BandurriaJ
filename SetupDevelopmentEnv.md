# Setup

* Java Version: JDK 11

First, you will need to clone this repository, all the developments are done
in the development branch, so you can check it. 

BandurriaJ is a maven project, so
most of the IDEs should be able to open the project base on the pom.xml.

## Changes to do to build the app
### Changes in pom xml
1. If you don't need to create a windows .exe executable, which is  probably the case,
you can just comment the whole <!--Launch4J--> plug in section.
   
### Changes in application.properties
1. If you plan to use a real CD Reader, you can can fill the full path to the discid
 library. For windows it would be the path to your discid.dll (You can find it, for instance,
   from the axokoi fork of JMBDiscId https://github.com/AxoKoi/JMBDiscId/tree/master/libdiscid)

## Running the app

### Spring profiles
We have 3 profiles:
1. noCD
2. noInternet
3. prod

The noCD profile will mock the reading of the CD and it will generate a valid discid, so it can be used 
to search for the disc using MusicBrainz.

The noInternet profile will mock the call to MusicBrainz service (not regularly maintained)

The prod profile will use the real CD reader (so you need indeed a cd driver in your pc) and will call
to musicbrainz.

### VM options
In order to run the application, you can launch the application with com.axokoi.bandurriaj.SpringBootMain
as the main class. YOu need to use the following vm options:
-Dspring.profiles.active=<your selected profile>
--module-path
<Path to the javafx sdk library> 
--add-modules
javafx.controls,javafx.fxml

The Path to the javafx sdk library could for instance be: C:\JavafxJDK\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib

You can download the javafx sdk from https://gluonhq.com/products/javafx/