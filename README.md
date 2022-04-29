# graphql-java-app

This repository contains my credit project for [Java Programming](https://d3s.mff.cuni.cz/teaching/nprg013/). 
This app provides a GraphQL provider that aggregates regional population change data from the Czech Statistical Office,
which are available [here](https://data.gov.cz/datov%C3%A9-sady?kl%C3%AD%C4%8Dov%C3%A1-slova=pohyb%20obyvatel).
The app relies on [GraphQL Java](https://www.graphql-java.com/) to serve the endpoint.


## Setup
Download the contents of this repository and compile using maven
```
mvn compile
```
run using maven
```
mvn exec:java
```
to use options with maven, use `-Dexec.args="..."`.


## Use
Running the command line app will read the data from the disc and read a query from the standard input.
It will then execute the query on the GraphQL endpoint and print the result. For more advanced use, use options.

`-f filename` will read the file provided and execute it as a query. You can use multiple `-f` options and all files will be executed in succession.

`-p pipename` will read from the named pipe. To use a named pipe, use `mkfifo pipename`. For more on FIFOs see `man mkfifo`. For example use,
see [pipe-query.sh](./pipe-query.sh).

`-c` will run continuously, allowing you to enter multiple queries successively and get query results after each one. When paired with `-f`, will 
continuously read from pipe. Otherwise, will read from stdin.


## Data
The data used for the endpoint include the following:
  - Codebooks for [towns](https://data.gov.cz/datov%C3%A1-sada?iri=https%3A%2F%2Fdata.gov.cz%2Fzdroj%2Fdatov%C3%A9-sady%2F00025593%2F3243574de944b881e835b53611efcea7),
[districts](https://data.gov.cz/datov%C3%A1-sada?iri=https%3A%2F%2Fdata.gov.cz%2Fzdroj%2Fdatov%C3%A9-sady%2F00025593%2Fdc2cb2c062fd30feb608c2b0848db0e7)
and [regions](https://data.gov.cz/datov%C3%A1-sada?iri=https%3A%2F%2Fdata.gov.cz%2Fzdroj%2Fdatov%C3%A9-sady%2F00025593%2F27abc7725c1b2d01531aa633b06ba4db)
  - Codebook bindings [town-district](https://data.gov.cz/datov%C3%A1-sada?iri=https%3A%2F%2Fdata.gov.cz%2Fzdroj%2Fdatov%C3%A9-sady%2F00025593%2F470d256f844e150a74307bf2d6ee3d91)
, [town-region](https://data.gov.cz/datov%C3%A1-sada?iri=https%3A%2F%2Fdata.gov.cz%2Fzdroj%2Fdatov%C3%A9-sady%2F00025593%2F08ec32b320bcae842bb90bafc448e38b)
and [district-region](https://data.gov.cz/datov%C3%A1-sada?iri=https%3A%2F%2Fdata.gov.cz%2Fzdroj%2Fdatov%C3%A9-sady%2F00025593%2Fc92832d196d01f0f06f39ec6f1661e5f)
  - [Yearly population data](https://data.gov.cz/datov%C3%A9-sady?kl%C3%AD%C4%8Dov%C3%A1-slova=pohyb%20obyvatel)
 
To add new years of data to the endpoint, simply place the new dataset in the `/src/main/resources/` folder.
