# Rock Paper Scissors Using Concurrency 

## Introduction

The Rock Paper Scissors application that simulates a number of players (NPLAYERS) taking turns to play. 
Each player thread will take NTURNS turns. 

Thus, if NPLAYERS=10 and NTURNS=100 then each player will play 100 times and 10 players take part. 
The games will be overseen by a referee thread that will select two players at a time and then arbitrate as they play the game.

This Main class should set up the referee and NPLAYERS players and set them running as concurrent threads. 
Once all of the players have finished the main program should signal to the referee that it is time to shut itself down.

## Screenshots

![Referee Queue][referee_queue]

[referee_queue]: /img/YlRe2FO.png

