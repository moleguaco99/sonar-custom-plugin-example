SonarQube Custom Plugin Example [![Build Status](https://travis-ci.org/SonarSource/sonar-custom-plugin-example.svg?branch=7.x)](https://travis-ci.org/SonarSource/sonar-custom-plugin-example)
==========

An example SonarQube plugin compatible with SonarQube 8.x.

Back-end
--------

Todo...

### Building

To build the plugin JAR file, call:

```
mvn clean package
```

The JAR will be deployed to `target/sonar-example-plugin-VERSION.jar`. Copy this to your SonarQube's `extensions/plugins/` directory, and re-start SonarQube.
