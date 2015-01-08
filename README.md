Table of Contents
=================

  * [Reactor](#reactor)
    * [Architecture overview](#architecture-overview)
    * [System usage concept](#system-usage-concept)
    * [Transport - Reactor interaction](#transport---reactor-interaction)
    * [Project structure](#project-structure)
    * [Building up and running](#building-up-and-running)
    * [Distribution folder structure](#distribution-folder-structure)
  * [System configuration](#system-configuration)

Reactor
=======
Reactor is a lightweight, system resource friendly, system integration and data retrieval micro kernel intended to be stupid simple making development process easy and painless. It was forged to make those possible:

- connect to any system you can imagine (just name it),
- get some interaction running,
- have results presented in the same manner no matter what system it was.

First version of Reactor (then called just ... IRCBot) have been limited only to [IRC](http://en.wikipedia.org/wiki/Internet_Relay_Chat) platform and was doing some vary basic logic back then.

Architecture overview
---------------------
![Reactor architecture](https://www.dropbox.com/s/govu4c4ekaazmx1/architecture.svg?dl=1)

Reactor system have been divided into two layers whereas each of them manages entities of two different kinds:

- [TransportController] - loads and stores list of objects ([ReactorMessageTransport]) providing access to system API through so called "Message Transports", whereas each object wraps and handles calls going through a given communication channel/protocol. It can be literally any communication channel existing out there:
  - IRC,
  - Telnet,
  - HTTP by REST or WebSockets,
  - Skype,
  - Microphone input together with voice recognition and synthesis,
  - Many many others ...
- [ReactorController] - loads and stores list of objects ([Reactor])) where each of them stands for a single unit of logic that is usually glued together with some external system like:
  - JIRA,
  - Jenkins,
  - Sonar,
  - etc.

Each entity type, be it [ReactorMessageTransport] or [Reactor], is being loaded from application classpath using JDK ServiceLoader factory object and glued together in system initialization phase.

System usage concept
--------------------
:hourglass: **IN PROGRESS**

Transport - Reactor interaction
-------------------------------
Because of generic nature of the system, there is only one interaction process suitable for any message transport or reactor that takes a part in a communication act. 

Having a given interaction diagram the process can be explained as following:

1. Request comes in through a [ReactorMessageTransport] instance,
2. Request object specific to given transport type is converted into [ReactorRequestInput] instance,
3. [ReactorRequestInput] is then sent into configured [ReactorRequestHandler] instance for further processing,
4. System asks [ReactorController] for a [Reactor] instance that can accept [ReactorRequestInput] by matching their argument values,
5. Then [ReactorRequestInput] is sent into [Reactor] instance for processing where [ReactorResponse] is produced as an outcome,
6. Provided [ReactorResponse] instance is going through a rendering process with one of available [ReactorResponseRenderer] implementations and goes back to a [ReactorMessageTransport] instance.

![Transport interaction with Reactor](https://www.dropbox.com/s/5v58lqeqo5nehz8/reactor-transport.svg?dl=1)

Project structure
=================

Project folder structure consist of several maven project modules located in following folders:

- [reactor-api](https://github.com/activey/reactor/tree/master/reactor-api) - provide whole API and basic implementations required to build Reactor instance,
- [reactor-bootstrap](https://github.com/activey/reactor/tree/master/reactor-bootstrap) - consists both of [TransportController] and [ReactorController] 
responsible for handline lifecycle of [Reactor] and [ReactorMessageTransport] instances. It glues together those two layers make it possible to interact 
with any [Reactor] by using any of available [ReactorMessageTransport] instance.
- [reactor-commons](https://github.com/activey/reactor/tree/master/reactor-commons) - right now it's just ... AbstractUnitTest :sweat_smile:,
- [reactor-lib](https://) - some of Reactor extensions, both reactor transports and reactor implementations theirself:
  - [reactor-transport-directinput](https://github.com/activey/reactor/tree/master/reactor-lib/reactor-transport-directinput) - transport implementation handles keyboard direct input passed through terminal where Reactor system was executed,
  - [reactor-transport-elastic-push](https://github.com/activey/reactor/tree/master/reactor-lib/reactor-transport-elastic-push) - first implementation of opposite direction message transport. Designed to push out [Reactor] response rendered as JSON documents to configured ElasticSearch instance,
  - [reactor-transport-http-jetty](https://github.com/activey/reactor/tree/master/reactor-lib/reactor-transport-http-jetty) - 
  - [reactor-transport-irc](https://github.com/activey/reactor/tree/master/reactor-lib/reactor-transport-irc) - transport implementation acquires IRC connection and awaits for messages on given IRC channels,
  - [reactor-transport-skype](https://github.com/activey/reactor/tree/master/reactor-lib/reactor-transport-skype) - plugs into running Skype instance and allows for interaction with a system by talking to an artificial Skype user,
  - [reactor-transport-telnet](https://github.com/activey/reactor/tree/master/reactor-lib/reactor-transport-telnet) - starts up a telnet server and allows for remote Reactor interaction using any of available telnet clients,
  - [reactor-transport-speech](https://github.com/activey/reactor/tree/master/reactor-lib/reactor-transport-speech) - experimental message transport implementation, listens to a microphone input and goes with system interaction by recognizing human voice,
  - [reactor-database](https://github.com/activey/reactor/tree/master/reactor-lib/reactor-database) - ,
  - [reactor-jenkins](https://github.com/activey/reactor/tree/master/reactor-lib/reactor-jenkins) - ,
  - [reactor-jira](https://github.com/activey/reactor/tree/master/reactor-lib/reactor-jira) - ,
  - [reactor-sonar](https://github.com/activey/reactor/tree/master/reactor-lib/reactor-sonar) - ,
  - [reactor-system](https://github.com/activey/reactor/tree/master/reactor-lib/reactor-system) -

:hourglass: **IN PROGRESS**
  
Building up and running
=======================
Whole build process is quite straightforward and required only maven to be available in system binaries path, then just navigate to downloaded source code folder and run:

```
mvn clean install
```

It will then compile just base system modules like reactor-api, reactor-bootstrap and so on. 
When you would like to build system with all extensions provided just use a dedicated switch for maven:

```
mvn clean install -DprovidedModules=true
```
This process will do the same as above but also will go through every extension that is located under **reactor-lib** module, compile it and build a JAR archive with all required dependencies included.

When you want to start up the system there are basically two options available:
- run script **run-cmd** that will pass command line parameters as an reactor input and print out results into terminal output,
- run script **run-reactor** which results in starting up all transports and reactors available in system classpath.

Those two scripts are located under **reactor-bootstrap/target/dist/bin** folder in two operating system flavors (windows and linux).

Distribution folder structure
-----------------------------
Structure of distribution package folder (that is **reactor-bootstrap/target/dist), is quite straightforward and it's naming follows common used conventions. Basically we can enlist three folders out there:
- /bin - consists of scripts for starting up the system, there should be couple of them each for a single dedicated operating system,
- /etc - one can find all configuration files used by a system and system extensions as well,
- /lib - contains basic libraries used by system mechanisms,
- /ext - put your system extensions here, both [Reactor] and [ReactorMessageTransport] implementations.

Installing system extensions
----------------------------
After building system source code with provided modules you can find the outcome of the process as a set of JAR files with all dependencies required by each of modules being built. Each of jar file is locater under corresponding module subfolder in **reactor-lib/[module-folder]/target**. Please note that JAR file generated for one module comes in two flavors. Get the one with **-jar-with-dependencies** suffix in file name, copy it over into **/ext** subfolder of distribution package and you're done :) Now start up the system and enjoy new extension.

Creating own extensions
=======================
:hourglass: **IN PROGRESS, follow source code of provided modules for now**

System configuration
====================
There are two configuration files provided for all message transports and reactors installed in the system located under **reactor-bootstrap/target/dist/etc** folder:

- reactor.properties - consists of setting entries for all reactors out there, where each entry has a dedicated prefix recognized by a given reactor implementation, eg: all entries starting with string **reactor.sonar** will be passed over to [Sonar Reactor](https://github.com/activey/reactor/tree/master/reactor-sonar) in system initialization phase,
- transport.properties - 

:hourglass: **IN PROGRESS**

[![Build Status](https://snap-ci.com/activey/reactor/branch/master/build_image)](https://snap-ci.com/activey/reactor/branch/master)

[Reactor]: https://github.com/activey/reactor/blob/master/reactor-api/src/main/java/org/reactor/Reactor.java
[ReactorRequestInput]: https://github.com/activey/reactor/blob/master/reactor-api/src/main/java/org/reactor/request/ReactorRequestInput.java
[ReactorRequestHandler]: https://github.com/activey/reactor/blob/master/reactor-api/src/main/java/org/reactor/transport/ReactorRequestHandler.java
[ReactorResponse]: https://github.com/activey/reactor/blob/master/reactor-api/src/main/java/org/reactor/response/ReactorResponse.java
[ReactorResponseRenderer]: https://github.com/activey/reactor/blob/master/reactor-api/src/main/java/org/reactor/response/renderer/ReactorResponseRenderer.java
[ReactorMessageTransport]: https://github.com/activey/reactor/blob/master/reactor-api/src/main/java/org/reactor/transport/ReactorMessageTransport.java
[ReactorController]: https://github.com/activey/reactor/blob/master/reactor-bootstrap/src/main/java/org/reactor/reactor/ReactorController.java
[TransportController]: https://github.com/activey/reactor/blob/master/reactor-bootstrap/src/main/java/org/reactor/transport/TransportController.java
