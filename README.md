# BlackJack
BlackJack game using standard rules as seen in rules.txt
## Getting Started

### Prerequisites
Java, Mavin Install

https://howtodoinjava.com/maven/how-to-install-maven-on-windows/

### Starting App

Clone this repository to a local folder.

Navigate to folder on command line.

Run ‘mvn install’ -> Maven needs to be configured for this step

When build complete , run 'java -jar target/blackjack-0.0.1-SNAPSHOT.jar' -> 
Java needs to be configured for this step.

####Using the application
****************************************************************
On Startup you will be asked if you'd like to start game. 
Start by pressing 1 and enter.
Console will then ask you to Hit (Press 1), or Stick(Any other numeral)
if the dealer or player does not have blackJack.
Continue hitting until you bust(Game ends) or stick where its the dealers turn.
Dealer will then hit until he wins, draws or busts.

 *************************************************************
 
## Running the tests
 In command line navigate to the project folder.
 Run 'mvn test'
 
 #####Alternately in IDE.
 Navigate to src/test/java/
 Right click com.blaisedev.blackjack
 Click run tests in clicked folder

## Authors
Blaise Devaney
 