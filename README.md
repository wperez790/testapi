# Defender TI Graph API automation tests

Test Suite of DefenderTI API developed with JAVA, SerenityBDD and Rest Assured. This project tests API Responses, Schema validation and ensures that the API follows the Microsoft Guidelines.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

You need to have installed on your local machine

- [java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- [Maven](https://maven.apache.org/download.cgi?Preferred=ftp://ftp.osuosl.org/pub/apache/) 


### Installing

1. Import the project from https://msazure.visualstudio.com/RiskIQ/_git/defender-ti-graph-api-automation-tests to your IntelliJ IDEA version. 


2. Wait until all the dependencies are downloaded.


## Running the tests

To run the tests you have to add a configuration as a Shell Script to your Run configurations.
1. Add the shell script called run.sh to the script path of the configuration.
2. Then add the Script options: ppe Regression. // ppe is to run the tests on the ppe environment, and Regression is to run all the tests of the suite.
3. After that, you have to modify the run.sh script and add a valid token on the access-token variable.
   * To get a valid token you have to grab one from the web under the network tab and copy the value of the Authorization Header of one request package, then paste it in the access-token variable without the prefix Bearer.
   * For prod environment you have to get the token through az command then copy the value of the access-token:
     * az account  get-access-token --scope 478d8d1a-326f-49da-a58e-8f576faa4b5e/ThreatIntelligence.Read.All
4. Lastly, run the project through IntelliJ. 
5. Another option is to run directly from the command line: 
```
mvn clean verify -Denvironment=ppe -Dtags="Regression" -Daccess-token="{your_token}" -Pregression 
```

## Environments
- With the parameter environment, it can be configured the environment where the tests are running. the options are:
    - local, dev, ppe, or prod.


## Check the report

- After finished, you could check the report opening the auto-generated report under: /defender-ti-graph-api-automation-tests/target/site/serenity/index.html
- Another way is to execute the next command to generate the report:
```
mvn serenity:aggregate
```
## Contributing

Please read our [CONTRIBUTING.md](CONTRIBUTING.md) which outlines all of our policies, procedures, and requirements for contributing to this project.