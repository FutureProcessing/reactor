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

Transport - Reactor interaction
-------------------------------
Because of generic nature of the system, there is only one interaction process suitable for any message transport or reactor that takes a part in a communication act. 

Having a given flow diagram the process can be explained as following:

[IN PROGRESS]

 

[![Build Status](https://snap-ci.com/activey/reactor/branch/master/build_image)](https://snap-ci.com/activey/reactor/branch/master)
