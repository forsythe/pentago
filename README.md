### Summary
This is a text based version of the board game [Pentago](https://en.wikipedia.org/wiki/Pentago). I made this project as an opportunity to implement a [minimax](https://en.wikipedia.org/wiki/Minimax) algorithm with [alpha beta pruning](https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning) for the AI player.

### Rules for Pentago
Pentago is a game for two players. The board is a 6x6 square, divided into four quadrants.
In each turn, a player places one marble of their color anywhere on the board, and rotates any quadrant by 90 degrees (clockwise or counterclockwise). The first player to reach 5 marbles of their color in a row (vertically, horizontally, or diagonally) wins. If a rotation of a quadrant results in a 5-in-a-row pattern for both colors, or if the board is full without anyone reaching 5-in-a-row, the game is a tie.

![pentago](https://upload.wikimedia.org/wikipedia/commons/thumb/b/be/Pentago-Game-Winning-Position.jpg/330px-Pentago-Game-Winning-Position.jpg)

### How the AI works
The `Board` object stores the current state of the board inside a `long[] board` of size 2. `board[0]` holds a `long` binary number which represents the locations of the white marbles, and `board[1]` holds a `long` binary number which represents the location of the black marbles. For example, `board[0] = 0b110000_000001_000000_000000_000000_000100L` would mean that white marbles are in the following positions:
```
|W|W|_||_|_|_|
|_|_|_||_|_|W|
|_|_|_||_|_|_|
==============
|_|_|_||_|_|_|
|_|_|_||_|_|_|
|_|_|_||W|_|_|
```
Similarly, `board[1] = 0b000000_110000_001000_000000_001000_000000L` would mean that the black marbles are in the following positions:
```
|_|_|_||_|_|_|
|B|B|_||_|_|_|
|_|_|B||_|_|_|
==============
|_|_|_||_|_|_|
|_|_|B||_|_|_|
|_|_|_||_|_|_|
```
I chose to use two binary numbers instead of a single array because this simplifies the task of checking for 3-in-a-row, 4-in-a-row, and 5-in-a-row conditions (for calculating heuristic values of the board); I can simply test `(mask & board[0]) == mask` to determine if the pattern has occurred, where `mask` is a `long` binary number which represents one gamestate where 5 marbles are in a row. 
For example, one of the masks might be `0b010000_010000_010000_010000_010000_000000L`. For this value, I would be checking whether or not the white player has achieved the following winning state:
```
|_|W|_||_|_|_|
|_|W|_||_|_|_|
|_|W|_||_|_|_|
==============
|_|W|_||_|_|_|
|_|W|_||_|_|_|
|_|_|_||_|_|_|
```
I calculate the heuristic value for the minimax algorithm by summing the number of times the following occur: 5-in-a-row, 4-in-a-row, 3-in-a-row, and center-of-quadrant marbles. The weights for each occurence are `1_000_000`, `1000`, `100`, and `5` respectively. Again, the masks make it much easier to calculate the heuristic value: simply iterating through a list of predefined masks is easier than writing a bunch of for loops to check all the possiblities.

For the function `alphaBeta()`, instead of returning just the score of the `board`, I return a `ScoreObject`. This object not only holds the `score` of the board, but also the `board` itself. Further contained within the `board` object are these three variables: `int movePos`, `int quadrant`, and `boolean moveClockwise`. This data makes it easier for the `PentagoAI` to determine what move to exectute, instead of having to deduce what move was made by comparing the new `board` state with the old. 

### Included gamemodes
`GameManager`'s `play()` function accepts two parameters, which specify who the white and black players are, respectively. The 4 options are: human v. human, human v. computer, computer v. human, computer v. computer, where the first player is white and the second black. `SEARCH_DEPTH_MAX` can also be customized to create different `PentagoAI` objects with varying difficulties. The higher the value of `SEARCH_DEPTH_MAX`, the deeper it will explore the game tree, and the more difficult it will be to win against it. Above values of `SEARCH_DEPTH_MAX = 5`, the search takes up to several minutes. But that doesn't matter for me, since I can barely beat it at `SEARCH_DEPTH_MAX = 2`. =(
