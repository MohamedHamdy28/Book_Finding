# Problem statment 
According to the bestseller of Joanne Rowling called Harry Potter and Philosopher’s Stone, the main hero was looking for a book about Nicholas Flamel, the only known creator of a philosopher’s stone. However, the book was located in the restricted section of a library. Therefore, Harry has decided to use his invisibility cloak to hide from unexpected guests and go to the library during the night. However, he has lost his invisibility cloak somewhere in the library while looking for a book. At that time, Argus Filch and his cat Mrs Norris began inspecting the library. Harry has to find the book and leave the library without being caught.
## Actor
You start from bottom left. Your goal is to reach door in as minimum number of steps as
possible. Your ability to perceive inspectors is defined in the “variants section” below.
Your algorithms will work on both variants. The actor can move one step per turn and can
move horizontally, vertically and diagonally.
## Inspectors
1. Mrs Norris: Cat’s perception is only in consecutive cells (Moore neighborhood), shown in
figure below.
![image](https://user-images.githubusercontent.com/71794972/163438506-3d967b91-40c2-45fa-94cf-3728bbf29465.png)
2. Argus Filch: His perception is Moore neighborhood of range 2 as he has a candle, shown in
figure below
![image](https://user-images.githubusercontent.com/71794972/163438554-081a5bde-918a-4c60-80cf-3518249ff4e4.png)

Both of them are generated randomly on the map. You do not want to face inspectors as it
ends the game. You can decrease the perception zone of inspectors to 1 cell (their location)
by finding the invisibility cloak. Technically you will be invisible to them until you collide with
them

## Exit
The exit door from the library is randomly generated on the map except inside the
inspectors’ cells and except the cell where the book is allocated. You know the location of
the exit door.

## Book
The book is generated randomly and is not in the inspectors’ zone. You do not know the
location of the book. You can perceive the book only when you are inside the book cell.

## Invisibility Cloak
The invisibility cloak is generated randomly and is not in the inspectors’ zone. You do not
know its location. You can perceive the invisibility cloak only when you are inside its cell. If
you found it, inspectors’ perception decreases till the cell, where they are located.

# Algorithms
- A backtracking search
- A* search

# Input
The algorithms input is a 9*9 square lattice. The map has a single actor, Argus Filch, Mrs
Norris, the book, the invisibility cloak and an exit door. The input file would be a sequence
of positions for each agent in the abovementioned order followed by the actor’s perception
scenario. Possible positions are including [0,0] [0,1] [0,2]....[8,6] [8,7] [8,8]. Possible
scenarios are 1 and 2.
It should be possible:
1. to generate the map and manually insert perception scenario
2. to insert the positions of agents and perception scenario manually.
While generation guarantees valid inputs, manual typing may lead to errors. Any incorrect
inputs violating conditions of the assignment should lead to the error message and request
for valid data. Input should be written in 2 strings. For example,
[0,0] [4,2] [2,7] [7,4] [0,8] [1,4]
1
describes the positions of all actors in the first figure:
• [0,0] – Harry
• [4,2] – Argus Filch
• [2,7] – Mrs Norris
• [7,4] – Book
• [0,8] – Invisibility Cloak
• [1,4] – Exit
and defines that the first scenario of actor’s perception is chosen.

# Output
The output comprises of
1. Name of the algorithm
2. Outcome - Win or Lose
3. The number of steps algorithm took to reach exit door
4. The path on the map. Path can be displayed as, for example, [0,0] [1,1] [1,2]...
5. It would be better if the path is highlighted on the map. You can use console for it
6. Time taken by the algorithm to reach the exit door
There should be 2 output sequences for each input – one for each algorithm.

# Statistical Analysis (MohamedHamdy.pdf)
Comparison of algorithms through statistical arguments based upon test maps generated.
Statistical analysis is required for both variants (described above). As an example , for each
test map, comparison would be:
• Backtracking (variant 1) compared to 2nd algorithm (variant 1)
• Backtracking (variant 2) compared to 2nd algorithm (variant 2)
• Backtracking (variant 1) compared to Backtracking (variant 2)
• 2nd algorithm (variant 1) compared to 2nd algorithm (variant 2)

# Sample of the output
- **This is the initial map:**


![image](https://user-images.githubusercontent.com/71794972/163440014-09f31df5-8fdd-44d7-9f99-23895278e304.png)
- **This is the result of the backtracking algotrithm**
![image](https://user-images.githubusercontent.com/71794972/163440133-268bd770-e1d1-4cc9-9cec-a23e0722ce66.png)
- **This is the result of the A start algorithm**
![image](https://user-images.githubusercontent.com/71794972/163440230-219b04ec-fcd9-4462-b9a4-1f81b11ea571.png)


