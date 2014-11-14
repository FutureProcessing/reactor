Reactor
=======
Reactor is a lightweight, system resource friendly, system integration and data retrieval micro kernel intended to be stupid simple making development process easy and painless. It was forged to make those possible:

- connect to any system you can immagine (just name it),
- get some interaction running,
- have results presented in the same manner no matter what system it was.

First version of Reactor (then called just ... IRCBot) have been limited only to [IRC](http://en.wikipedia.org/wiki/Internet_Relay_Chat) platform and was doing some vary basic logic back then.

Architecture overview
---------------------
![Reactor architecture](https://www.dropbox.com/s/govu4c4ekaazmx1/architecture.svg?dl=1)

Reactor system have been divided into two layers whereas each of them manages entities of two different kinds:

- [TransportController](https://github.com/activey/reactor/blob/master/reactor-bootstrap/src/main/java/org/reactor/transport/TransportController.java) - loads and stores list of objects ([ReactorMessageTransport](https://github.com/activey/reactor/blob/master/reactor-api/src/main/java/org/reactor/transport/ReactorMessageTransport.java)) providing access to system API through so called "Message Transports", whereas each object wraps and handles calls going through a given communication channel/protocol. It can be literally any communication channel one can immagine:
  - IRC,
  - Telnet,
  - HTTP by REST or WebSockets,
  - Skype,
  - Microphone input together with voice recognition and synthesis,
  - Many many others ...
- [ReactorController](https://github.com/activey/reactor/blob/master/reactor-bootstrap/src/main/java/org/reactor/reactor/ReactorController.java) - loads and stores list of objects ([Reactor](https://github.com/activey/reactor/blob/master/reactor-api/src/main/java/org/reactor/Reactor.java)) where each of them stands for a single unit of logic that is usually glued together with some external system like:
  - JIRA,
  - Jenkins,
  - Sonar,
  - etc.

Each entity type, be it [ReactorMessageTransport](https://github.com/activey/reactor/blob/master/reactor-api/src/main/java/org/reactor/transport/ReactorMessageTransport.java) or [Reactor](https://github.com/activey/reactor/blob/master/reactor-api/src/main/java/org/reactor/Reactor.java), is being loaded from application classpath using JDK ServiceLoader factory object and glued together in system initialization phase.

System usage concept
--------------------

:hourglass: **IN PROGRESS**

Transport - Reactor interaction
-------------------------------
Because of generic nature of the system, there is only one interaction process suitable for any message transport or reactor that takes a part in a communication act. 

Having a given flow diagram the process can be explained as following:

:hourglass: **IN PROGRESS**

Project structure
-----------------

Project folder structure consist of several maven project modules located in following folders:

- [reactor-api](https://github.com/activey/reactor/tree/master/reactor-api) - provide whole API and basic implementations required to build reactor instance,
- [reactor-commons](https://github.com/activey/reactor/tree/master/reactor-commons) - right now it's just ... AbstractUnitTest :sweat_smile:,
- [reactor-bootstrap](https://github.com/activey/reactor/tree/master/reactor-bootstrap) - 
- [reactor-transport-directinput](https://github.com/activey/reactor/tree/master/reactor-transport-directinput) -
- [reactor-transport-http-jetty](https://github.com/activey/reactor/tree/master/reactor-transport-http-jetty) - 
- [reactor-transport-irc](https://github.com/activey/reactor/tree/master/reactor-transport-irc) -
- [reactor-transport-skype](https://github.com/activey/reactor/tree/master/reactor-transport-skype) -
- [reactor-transport-telnet](https://github.com/activey/reactor/tree/master/reactor-transport-telnet) -
- [reactor-transport-speech](https://github.com/activey/reactor/tree/master/reactor-transport-speech) -

:hourglass: **IN PROGRESS**

Building up and running
-----------------------
Whole build process is quite straightforward and required only maven to be available in system binaries path, then just navigate to downloaded source code folder and run:

```
mvn clean install
```

It will then process every module, compile it and build binary package for each of them that will be a jar archive located under **module_folder/target/module_name.jar** file. When you want to start up the system there are basically two options available:
- run script **run-cmd** that will pass command line parameters as an reactor input and print out results into terminal output,
- run script **run-reactor** which results in starting up all transports and reactors available in system classpath.

Those two scripts are located under **reactor-bootstrap/target/dist/bin** folder in two operating system flavors (windows and linux).

System configuration
====================
There are two configuration files provided for all message transports and reactors installed in the system located under **reactor-bootstrap/target/dist/etc** folder:

- reactor.properties - consists of setting entries for all reactors out there, where each entry has a dedicated prefix recognized by a given reactor implementation, eg: all entries starting with string **reactor.sonar** will be passed over to [Sonar Reactor](https://github.com/activey/reactor/tree/master/reactor-sonar) in system initialization phase,
- transport.properties - 

:hourglass: **IN PROGRESS**

[![Build Status](https://snap-ci.com/activey/reactor/branch/master/build_image)](https://snap-ci.com/activey/reactor/branch/master)
