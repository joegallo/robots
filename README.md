# Robots

Clojure Robots

## Installation

To install first extract `robocode.tar.gz`.

    $ tar xzvf robocode.tar.gz

Then install Robocode

    $ cd robocode
    $ java -jar robocode-1.7.0.2-setup.jar

and run the mvn install script

    $ cd mvn-setup
    $ ./mvn-install-robocode.sh

Now we can run lein deps from the root of the robots folder

    $ lein deps

Copy the downloaded clojure and clojure-contrib jar files into the libs folder
of the Robocode installation folder (~/robocode)

    $ cp ./lib/clojure-*.jar ~/robocode/libs/

Next edit `robocode.sh` to disable the security manager:

    java -Xmx512M -Dsun.io.useCanonCaches=false -DNOSECURITY=true -cp ./libs/*: robocode.Robocode $*

Note that this only works for Java6. Older versions require to specify
all jar files explicitly in the classpath.

Assuming `robotcode.sh` is executable, simply run

    $ ./robocode.sh

to verify the installation.

## Usage

## Build the Sample Robot

Use Leiningen to compile and build the jar

    $ lein compile
    $ lein jar

Lets check the jar file to make sure everything is included, esp. the robot
clojure source file and the properties files

    $ jar tf robots-1.0.0-SNAPSHOT.jar
    META-INF/MANIFEST.MF
    meta-inf/maven/robots/robots/pom.xml
    meta-inf/maven/robots/robots/pom.properties
    robots/fatrobot$_onScannedRobot.class
    robots/fatrobot$_run.class
    robots/fatrobot$loading__4410__auto__.class
    robots/fatrobot.class
    robots/fatrobot__init.class
    robots/fatrobot.clj
    robots/fatrobot.properties
    project.clj


## Deploy Robot

Run Robocode

    $ cd ~/robocode
    $ ./robocode.sh

and import your robots into robocode via the menu `Robot -> Import
downloaded robot` and select the jar file generated.

Start a new battle and select `robots.fatrobot` as one of the robot.

## Writing a Robot

Lets write out own robot now.

First copy the fatrobot code (courtesy of Jeff Foster) and properties files
into your own namespace, e.g. myrobot:

    (ns robots.myrobot
      (:gen-class :extends robocode.Robot))
    
    (defn -run
      [robot]
      "Infinite loop whilst robot is alive"
      (doto robot
        (.ahead 500)
        (.turnGunRight 360)
        (.back  500))
      (recur robot))
    
    (defn -onScannedRobot
      [robot event]
      (doto robot
        (.fire 1)))

and

    robot.description=My simple robot
    robot.webpage=
    robocode.version=1.1.2
    robot.java.source.included=false
    robot.author.name=Me
    robot.classname=robots.myrobot
    robot.name=My Robot

Adjust the Leiningen project.clj accordingly

    ...

    :manifest {"robots" "robots.fatrobot" "myrobots" "robots.myrobot"}
    :aot [robots.myrobot robots.myrobot])

Build

    $ lein jar

and import it into Robotcode as described above.


## License

Copyright (C) 2010 Pittburgh Clojure Group

Distributed under the Eclipse Public License, the same as Clojure.
