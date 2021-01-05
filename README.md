RushHour
========

Programmed by: Luke Janik-Jones

Note: This project is still under development, as such it is not yet in a polished state.

This Java program attempts to solve a user-inputted state of the sliding block logic game 'Rush Hour®'.
It accepts input through a JSON file whose file path is dictated by the main function in WindowUI.java.

I have included a build.gradle file to run the program using the main method I have supplied.
I have also included three example JSON files which are configured to demonstrate different capabilities of the program.

An up-to-date version of gradle allows you to run this program with the following commands in your terminal:

1. gradle build
2. java -jar build/libs/RushHour.jar

Examples
--------

Example 1 demonstrates the customizability of the program, example 2 demonstrates the ability to quickly solve puzzles of moderate difficulty, and example 3 shows a puzzle that is too complex for my code to handle.

To change which example the program uses, simply modify the filename variable in the main method in WindowUI.java.

Capabilities
------------

A great deal is able to be customized in the JSON files. The length and width of the board and the number, position, direction, and length of any blocks are all subject to change.

There are some restrictions to the rules:
* Blocks must be of at least length two.
* A valid board state must be provided, that being a state without out-of-bounds blocks or overlaps.
* There must be exactly one primary block declared in the JSON, that being the block which must be removed from the play area by reaching the 'exit'.
* The exit will always be on the right or bottom wall of the play area, depending on the primary blocks orientation.

There are also complexity restrictions, but this results mostly from out-of-memory errors as the heap becomes full.
Generally, this program performs significantly better on tightly-packed puzzles with complex solutions rather than open puzzles with less complex solutions.

This is because open puzzles have a larger amount of potential states than tighter puzzles, and the main bottleneck of this program is memory-space.

Future Additions
----------------

I have several plans for the future of this project. The first and highest priority is to create a cleaner user interface that looks cleaner, clearer, and allows for additional functionality.

Otherwise, the most important additions are changes to the algorithm I'm using in order to save storage space, and other efficiency improvements.
