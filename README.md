# Helidon Microstream Demo 

Sample Helidon  Microservices Demo with a Microsteram Persistence Layer.
The microservice  includes multiple REST operations.

## Build and run

With JDK11+
```bash
mvn package
java -jar target/helidon-microstream-demo.jar
```

## Native image 

### Generate the Reflexion config 
```shell script
$GRAALVM_HOME/bin/java -agentlib:native-image-agent=config-output-dir=./target/reflexion.generated  -jar  target/helidon-microstream-demo.jar
```

Copy the target/reflexion.generated/reflect-config.json content to META-INF/$GROUP_ID/$ARTIFACT_ID/


### Build the native Microservice with GraalVM
```
mvn clean  package -Pnative-image
```
## Exercise the application

```
Add Joe 

curl -X POST -H "Content-Type: application/json"  -d '{"firstname":"Joe","lastname":"Doe"}'  localhost:8080/employee

{"firstname":"Joe","lastname":"Doe","uid":"4ac482a8-20da-4290-9011-e8564dbc5d2c"}
                                                                                                                     

curl -X GET http://localhost:8080/employee

[{"firstname":"Zoomba","lastname":"Zoom","uid":"2a1cdac2-8e18-40ba-88de-63799d798103"},
{"firstname":"Zoomba","lastname":"Zoom","uid":"978b5730-7008-42f5-831d-f72baed14ad3"},
{"firstname":"GraalVM","lastname":"MicroStream","uid":"c1aa8d8e-6ddd-4070-8598-ba7de55fefcd"},
{"firstname":"Joe","lastname":"Doe","uid":"4ac482a8-20da-4290-9011-e8564dbc5d2c"}]

Find Employee with name Joe
curl -X GET http://localhost:8080/employee/Joe


Delete Joe 
curl -X DELETE  http://localhost:8080/employee/4ac482a8-20da-4290-9011-e8564dbc5d2c
true

```

## Try health and metrics

```
curl -s -X GET http://localhost:8080/health
{"outcome":"UP",...
. . .

# Prometheus Format
curl -s -X GET http://localhost:8080/metrics
# TYPE base:gc_g1_young_generation_count gauge
. . .

# JSON Format
curl -H 'Accept: application/json' -X GET http://localhost:8080/metrics
{"base":...
. . .

```

## Build the Docker Image

```
docker build -t helidon-microstream-demo .
```

## Start the application with Docker

```
docker run --rm -p 8080:8080 helidon-microstream-demo:latest
```

Exercise the application as described above

## Deploy the application to Kubernetes

```
kubectl cluster-info                         # Verify which cluster
kubectl get pods                             # Verify connectivity to cluster
kubectl create -f app.yaml                   # Deploy application
kubectl get pods                             # Wait for quickstart pod to be RUNNING
kubectl get service helidon-quickstart-mp    # Verify deployed service
```

Note the PORTs. You can now exercise the application as you did before but use the second
port number (the NodePort) instead of 8080.

After you’re done, cleanup.

```
kubectl delete -f app.yaml
```

## Build a native image with GraalVM

GraalVM allows you to compile your programs ahead-of-time into a native
 executable. See https://www.graalvm.org/docs/reference-manual/aot-compilation/
 for more information.

You can build a native executable in 2 different ways:
* With a local installation of GraalVM
* Using Docker

### Local build

Download Graal VM at https://www.graalvm.org/downloads, the version
 currently supported for Helidon is `20.1.0`.

```
# Setup the environment
export GRAALVM_HOME=/path
# build the native executable
mvn package -Pnative-image
```

You can also put the Graal VM `bin` directory in your PATH, or pass
 `-DgraalVMHome=/path` to the Maven command.

See https://github.com/oracle/helidon-build-tools/tree/master/helidon-maven-plugin#goal-native-image
 for more information.

Start the application:

```
./target/helidon-microstream-demo
```

### Multi-stage Docker build

Build the "native" Docker Image

```
docker build -t helidon-microstream-demo-native -f Dockerfile.native .
```

Start the application:

```
docker run --rm -p 8080:8080 helidon-microstream-demo-native:latest
```


## Build a Java Runtime Image using jlink

You can build a custom Java Runtime Image (JRI) containing the application jars and the JDK modules 
on which they depend. This image also:

* Enables Class Data Sharing by default to reduce startup time. 
* Contains a customized `start` script to simplify CDS usage and support debug and test modes. 
 
You can build a custom JRI in two different ways:
* Local
* Using Docker


### Local build

```
# build the JRI
mvn package -Pjlink-image
```

See https://github.com/oracle/helidon-build-tools/tree/master/helidon-maven-plugin#goal-jlink-image
 for more information.

Start the application:

```
./target/helidon-microstream-demo/bin/start
```

### Multi-stage Docker build

Build the "jlink" Docker Image

```
docker build -t helidon-microstream-demo-jlink -f Dockerfile.jlink .
```

Start the application:

```
docker run --rm -p 8080:8080 helidon-microstream-demo-jlink:latest
```

See the start script help:

```
docker run --rm helidon-microstream-demo-jlink:latest --help
```
