# Admin Feature Documentation
## Introduction
 - This documentation is for system admins and maintainers.
 - This documentation covers how to adjust game by changing game.properties
## game.properties
The game.properties file is a property file that the game uses. By changing it, you can easily adjust the game without having to recompile the game. 
#### IMAGE_PATH
This value is for specifying the path to the image folder. The default is `images/`
#### DATA_PATH
This value is for specifying the data folder, which contains city data. The default is `data/`
#### CITY_DATA
This value is for specifying the name of the city data file inside the data folder. The default is `citydata.dat`
#### MAX_DISEASE_CUBE_PER_COLOR
This specifies how many cubes are in the game. The default is `24`. Higher value will make the game easier since you lose when you run out of cubes.
#### HAND_LIMIT
Specifies how many card can a player hold. Default is `7`
#### INFECTION_RATES
Specifies the infection rates, separated by commas. The rates should be in non-descreasing order for the game rule to make sense, but you can change it to anything. Having more values in the array makes the game easier, since you lose when you reach the highest infection. The default value is `2,2,2,3,3,4,4`, which is the same as the original game.
#### ACTION_PER_TURN
Number of actions a player can take per turn. Default is `4`
#### DRAW_CARDS_PER_TURN
Number of cards a player can draw at the end of the turn. Default is `2`
#### DISCOVER_CURE_CARDS
Number of cards a player (non-scientist) need to discover cure. Default is `5`.
#### DISCOVER_CURE_CARDS_SCIENTIST
Number of cards a scientist need to discover cure. Default is `4`
#### FORECAST_CARD_NUMBER
Number of cards players can arrange when playing the Forecast Event Card. Default is `6`.
#### MAX_STATION_COUNT
Number of stations that can be on the map. Default is `6`. 
#### LANGUAGES
Languages supported by the game, separated by comma. When adding to this value, you also need to provide corresponding languages files.
