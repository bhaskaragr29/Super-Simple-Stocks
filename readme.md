# Super Simple Stocks

###  Project structure
com.jpmorgan.supersimplestocks.model - Contains Model for Stock and Trade
com.jpmorgan.supersimplestocks.Service - Contains services for Trade, Formula, SimpleStocks, TraderLogger

### Running the project
Since the implementation was built with maven, just execute in the project root folder:

```
mvn package
java -jar target/super-simple-stocks-0.0.1.jar
```
 Project using the following data
  
##### Global Beverage Corporation Exchange

Stock Symbol  | Type | Last Dividend | Fixed Dividend | Par Value
------------- | ---- | ------------: | :------------: | --------: 
TEA           | Common    | 0  |    | 100
POP           | Common    | 8  |    | 100
ALE           | Common    | 23 |    | 60
GIN           | Preferred | 8  | 2% | 100
JOE           | Common    | 13 |    | 250

- Project contains the doc folder for inline documentation for Services class.