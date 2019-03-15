# CSSE367 Boardgame Project
From the BVA definition of done, we will consider our program to be done when all the following tests are written and run without any errors. The tests for the cases are:

Tests:
8 outbreaks occur (players lose)
Tests:
1- error case: -1 - This should never be the count for the outbreaks since the game initializes with 0 outbreaks and should only be incremented from there
2- lower bound: 0 - This should be the base case to initialize this specific value so that the game can be started
3- upper bound: 8 - This should cause the game to end and is therefore the largest possible value that the outbreak counter can have
4- error case: 9 - This should never be the count since the game ends with an outbreak count of 8

Not enough player cards to draw (players lose)
Tests:
1- error case:   60 This should never be the count for the deck since the game initializes with 59 cards in the deck  and should only be decremented from there.
2- lower bound: 59 - This should be the base case to initialize this specific value so that the game can be started.
3- upper bound: 0 - This should cause the game to end if a player needs to draw and is therefore the smallest possible value that the deck counter can have.
4- error case: -1 - This should never be the count since the game ends with a card counter of 0 when draw is attempted.

Not enough disease cubes are left (all colors inclusive) (players lose) 
Tests:
1- error case: 97 - This should never be the count for the disease cubes since the maximum number of cubes in the game is 96
2- upper bound: 96 - This should be the base case since there are 96 disease cubes as part of the game
3- lower bound: 0 - This is the lowest number that the disease pile can reach with the game still continuing. If another disease cube was to be played, the game would automatically end, and the number would never decrease further
4- error case: -1 - This should never be the count since trying to place a cube when there is a disease cube count of 0 would end the game

Not enough disease cubes are left (one color) (players lose) 
Tests:
1- error case: 25 - This should never be the count for the disease cubes since the maximum number of cubes for every color in the game is 24
2- upper bound: 24 - This should be the base case since there are 24 disease cubes of every color as part of the game
3- lower bound: 0 - This is the lowest number that the disease cubes of any color can reach with the game still continuing. If another disease cube of the same color was to be played, the game would automatically end, and the number would never decrease further
4- error case: -1 - This should never be the count since trying to place a cube when there is a disease cube count of 0 would end the game

City
Tests for specific diseases:
Error case: -1 - This should never be the count for a specific disease, since a city can never have a negative number of disease cubes
Lower bound: 0 - This should be the default number of disease cubes per city
Upper bound: 3 - This is the maximum number of disease cubes that can be placed on a specific city
Error case: 4 - this can never be the count for a specific disease on a specific city

Tests for research station:
Error case: -1 - This should never be the count for a research station, since a city can never have a negative number of them
Lower bound: 0 - This should be the default number of research stations per city
Upper bound: 1 - This is the maximum number of research stations that can be placed on a specific city
Error case: 2 - this can never be the count for research stations on a specific city
Tests that trigger actions:
If there are already 3 disease cubes for a color, and another one has to be added, cause an outbreak to occur, and the disease cube count should stay the same for the current city
If the disease is treated, decrease the disease cube count by 1
If the cure for the disease has been discovered, remove all the disease cubes and set the count to 0

Hand Limit
Tests:
1- error case:   10 - This should never be the count for a hand since the game 7 is the normal max, and after drawing 2 the highest a player should get at any point is 9
2- upper bound all time: 9  - this is upper bound on cards for any point in time, if a player has 8 or 9 cards, they must immediately discard until they only have seven. 
3- upper bound normal: 7 - this is the upper bound for keeping cards in your hand. 
4- lower bound:  0 - you can’t have negative cards, so  zero would be the lower bound on a hand size.
5- error case: -1 - This should never be the count you can not play a card that you don't have.

Action count
Tests:
1- error case:   5 - This should never be the action count since the player can never take 5 actions in a turn
2- upper bound: 4  - this is upper bound on the number of turns that a player can take per turn
3- lower bound:  0 - this shows that the player has used up all his turns, and the player should change
4- error case: -1 - This should never be the count since a player could have never played more than four turns

All cures are discovered (players win)
Tests:
1-error case: 5-  This should never be the count since 4 cures will automatically makes the player win and the game ends.
2-upper bound: 4- This is the highest cures the game can have, when 4 cures are reached, the game automatically ends, and the players win the game.
3-lower bound: 0- This is initialized at the beginning of the game
4-error case: -1- This should never be the count since the number of cures are initialized to be 0 in the beginning of the game.     

Current Player
Tests:
1- error case: 0 - This can never be a player, since the first player will be player 1
2- lower bound: 1- This is the lowest possible value that this variable can have
3- upper bound: n - Here, n is the number of players who are playing the current game. The Current player value can never be higher than this since there can never be an n+1 player. N has to be between 2-4.
4- error case: n+1 - 

A cure is discovered
Upon discovering a cure, do
For the disease associated with that cure, when one disease cube is to be removed from a city, instead remove all the disease cubes of that color.
If there are no disease cubes of that color on the board, none can be placed for the rest of the game by any means (the disease can be eradicated)
Form each players initial hands
Tests:
1-error case: 1- the number of cards is not enough to form an initial hand.
2-lower bound: 2- if this is a four-player game, then initial hand is limited to 2 cards.
3-middle bound : 3- if this is a three-player game, then initial hand is limited to 3 cards.
4-upper bound: 4- if this is a two-player game, then initial hand is limited to 4 cards.  
5-error case: 5- the number of cards is over the maximum limit for an initial hand.
Draw infection card
Upon drawing an infection card, verify that
Check if the color of disease is eradicated
If not, a cube of the correct color is put at that location on the card.
The card ends up in the collection of discarded infection cards.
The card is no longer in the infection deck. 

Draw event card
Upon drawing an event card, verify that
That card is in the hand of the player that was supposed to draw it.
The card is no longer in the infection deck.

Draw city card
Upon drawing a city card, verify that
That card is no longer in the player card deck.
That card is in the hand of the player that was supposed to draw it.

Draw epidemic card
Upon drawing a epidemic card, verify that
The infection rate marker is increased by one.
Do edge case of it increasing infection cards drawn, base case of the first epidemic and error cases of it going higher that possible (due to the limited number of epidemic cards).
The epidemic card is removed from the game and no longer exists in any collection.
The bottom card in the infection deck is revealed.
Special test case for deck being empty (this might be an error case as im pretty sure it is guaranteed that the player cards run out before infection cards resulting in a lose, however I’m not totally sure).
Three of the appropriate colored cubes are placed in the city of the revealed card.
Test case for empty city
Test case for city that has a cube of that color in it.
Should add until it gets to three then outbreak.
Test case for city that has a different color cube in it.
Should place 3 cube of standard color and have no outbreak.
The revealed card is removed from the deck.
The revealed card is in the infection card discard collection. 
The infection card’s discard is randomized.
The infection discard collection is empty.
The infection deck contains all of the cards that were previously in the infection discard, on top of all of the cards that were previously in the infection deck.

Drive/Ferry
Upon taking this action, verify that
The character is moved.
The destination of the move is valid for the starting space.
An action is consumed.
The only options available are adjacent cities. 

Direct Flight: 
Upon taking this action, verify that
A city card is is removed from the players hand.
The same card goes to the collection of discarded cards.
An action is consumed.
The character is moved to city with the same name as the city on the discarded card.


Charter Flight:
Upon taking this action, verify that
The city card a character is currently in is removed from the players hand
An action is consumed
A new city name is randomly generated from the set of cities
The player is moved to that new city

Charter Flight: Discard a city card with the city a character is currently in and consume one action. Move the character to any city.

Shuttle Flight:
Upon taking this action, verify that
The current city has a research station.
The destination city has a research station.
The character is moved from the current city to the destination city.
An action is consumed

Build a Research Station: Discard a city card with the city a character is currently in and consume one action. Place a research station in the current city.

Build Research station when there are already 6:  
Upon taking this action, verify that
The city card a character is currently in is removed from the players hand.
An action is consumed
A new city name is randomly generated from the set of cities that have research stations in it. 
The research station from the generated city is moved to the current city

Check if the cities are connected:
We are planning on implementing the city layout as a graph. To check the references, we are going to 
2 pointers from two adjacent cities can point to two different cities (objects) with the same contents.
2 pointers from two adjacent cities can point to the same city (object).
2 pointers from two adjacent cities refer to different cities whose non- reference contents are equal. Moreover, their pointer contents refer to cities that are recursively equal.

Treat Disease:
Upon taking this action, verify that
One disease cube is removed from the current city
All disease cubes are removed from the current city if the cure for the current disease has been discovered
Treat Disease: Consume an action to remove one disease cube from the current city. (if cured remove all cubes)

Share Knowledge:
Upon taking this action, verify that 
Check if chosen player is in the same city
Give or take a city card to/from selected player
The player who is given a card, the number of card in its hand increase by one
The player who is taken a card, the number of card in its hand decrease by one
Check hand limit for the player who takes a card  
An action is consumed.
Discover Cure:
Upon taking this action, verify that 
The player is at a research station
The player has five same color city cards (The scientist only need four same color city cards)
The player discards five city cards with same color
The number of cards in this player’s hand is decreased by one
The boolean for the cure corresponding to the disease becomes true
A cure is discovered
An action is consumed
Outbreaks:
Upon taking this action, verify that 
Increase the number of outbreaks marker by 1
Increase the number of cube by 1 to the connected cities
If the connected city already has outbreaks, do not place a 4th cube
If the connected city has a chain reaction outbreak, do not place a 4th cube
If the connected city already have 3 cubes of a disease color, do not place a 4th cube with this disease color. Instead, Outbreaks 
Turn End
When this happens, verify that 
The current player counter increases by 1
If the last player just finished his turn, the counter is reset to 1


Contingency Planner
If the current player is the Contingency Planner, verify that:
The player can take an Event card from anywhere in the Discard Pile (count as an action)
Place that event card on his role card.
Only have one event card can be on top of the role card.
The event card does not count against the hand limit.
When the event card is removed, it is removed completely from the game.

When the CP plays the Event card, it is removed from the game

Dispatcher
 If the current player is the Dispatcher, verify that:
If the pwns’ owners agree, can move any pawn to any city containing another pwn
If a pawn’s owner agrees, can move that pawn as if it were his own.
When moving a player’s pawn as if it were your own,  cards for Direct and Charter Flights are discarded from the player’s hand. The discarded Charter Flight card must match the city the pawn is moving from.

Medic
If the current player is the Medic, verify that:
Whenever a disease is cured, the medic removes all disease cubes of that color from the city.
When the medic removes the cubes, it needs to move to that city where the cubes are removed if it’s not currently there.
Cubes, outbreaks, and cured diseases cannot be placed at the city where it’s at
When the action “treat disease” occurs, the medic removes all cubes of the same color
The automatic cube removal should occur regardless of  being moved by Dispatcher or the Airlift Event. 


Operations Expert
If the current player is the Operations Expert, verify that:
The  player can build a research station in his current city 
Building the research station count as an action
Building the research station does not make the player discard or use a city card
Can move to any city when any city card is discarded
Researcher 
When sharing knowledge verify that.
The researcher can pass any card to any player in the same city as him/her. 
Verify with a 
The researcher may not take cards that don’t match the current city as him and the player who’s card he is attempting to take. 
Quarantine Specialist 
Anytime a cube is about to be placed verify that,
It is not in the same city as the quarantine specialist.
It is not in a city adjacent to the quarantine specialist.
In the case that either of these are requirements are not met, do not place the cube or resolve effects of placing the cube.
Test cases where the cube placed is a different color than the  quarantine specialist’s current city.
Test case where cube placement was via epidemic card rather than normal infection.
Test case where cube placement is via normal infection. 
Test cases where the cube is placed 2 away from the  quarantine specialist (cube should get placed). 
Test cases where an outbreak would have been caused.
Test cases where outbreak is the source of the cubes.
Scientist
If the current player is the scientist, verify that
He/She can discover a cure with 4 City cards of that color

Scientist only needs 4 City cards of the disease color to discover the cure

If you play an event card, verify that
The player playing the card has the card in their hand. 
The card end up in the player card discard.
The card played is removed from the players hand.
If the card name is “Event: Resilient Population”
Constraints on card.
Test that card cannot be played when infection discard pile is empty.
Allow the player who played the card to choose a card in the infection discard pile.
Remove the chosen card from the infection discard pile
The chosen card no longer exists in any collection.
If the card name is “Event: One Quiet Night”
The next infect Cites step passes without drawing any infection cards.
Test with all three possible infectivity rates (2,3,4) and error cases 1 and 5.
If the card name is “Event: Forecast:
The player who played the card gets to look at the the top 6 cards in the infection deck
Test normal case in which case the deck size is more than six, as well as a case in which the deck size is 6, and also less than 6, also test error cases in which the deck is empty. 
Allows the player move each card to the top (to rearrange the order)
This should not be limited on actions, however there should be a way to exit this game small loop.
When the loop is exited, the cards go out of the players control.
The cards on top of the deck should match the order that the player ordered them in step b.
If the card name is “Event: Government Grant”
Allow the player to choose a city. 
Test case that the city already contains a research station in which no station is placed.
Test case in which all six stations have already been placed in which case the player is given an additional choice about which station he wants to move to the destination.
A research station is in the chosen city.
If the card name is “Event: Airlift”
Allow the player who played the card a choice between all players in the game.
Test cases for each number of players in the game, as well as error cases for 1 and 5 players.
Allow the player who was chosen a choice of a city. 
The chosen player’s character is on the chosen city. 

