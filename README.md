Rosetta JVM
===========

Example project using GitHub data to create visualisations and integrating many many JVM languages in the same codebase.

Usage
=====

First start up the scraper service using:

```
$ gradle service -Dusername=<your-username> -Drsa.token=<a-one-time-rsa-token>
```

Then, execute the following at a command prompt:

```
$ gradle assets
```

and navigate to [http://localhost:3000/html/wheres-waldo.html](http://localhost:3000/html/wheres-waldo.html).
