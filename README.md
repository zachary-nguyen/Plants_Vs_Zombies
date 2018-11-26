## Plants VS Zombies

SYSC 3110 Group Project

Section: L1 

Group: 11

### Team Members:

- Zachary Nguyen

- Fareed Ahmad

- Mathew Smith 

- Eric Cosoreanu 

### Contributions:

- Zachary: Implemented Undo/Redo, JUnit Test Development, General Refactoring, General Documentation.

- Fareed: In-Game Logic Refactoring, General Refactoring, Sequence Diagram Documentation. 

- Mathew: Optimized/Refactored In-Game Logic and Sprite Interactions, General Refactoring, UML Diagram Documentation.

- Eric: Implemented and Configured New Sprites, Implemented Additional GUI Elements, General Refactoring, README Project Documentation.

### Deliverables:

 * Source Code:
  
    - Controller: 
        - Game.java
 
    - Model:
        - AbstractPlant.java
        - AbstractZombie.java
        - Bullet.java
        - Peashooter.java
        - Sprite.java
        - Sunflower.java
        - Zombie.java
        - Backyard.java
        - ConeheadZombie.java
        - FlagZombie.java
        - Repeater.java
        - Wallnut.java
    
    - View:
        - Tile.java
        - View.java
    
    - images:
        - backyard.png
        - BULLET.png
        - PEASHOOTER.png
        - Sun.png
        - Sunflower.png
        - ZOMBIE.png
        - CONEHEAD.png
        - FLAGZOMBIE.png
        - REPEATER.png
        - WALLNUT.png
        
  * UML/Sequence Diagrams
  
  * Other Documentation

### Known Issues:

- General sprite spawn balancing issues. Sprites may spawn in such a way that is overwhelming to the player.

- If two sprite occupy the same tile backyard tile, only one of said sprites will be shown based on their priority level. Can cause confusion for the player. 


### Roadmap:
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Our next steps regarding this project are to first fix the remaining issues with the game mechanics, such as those listed in the “known issues” section. Additionally, we would like to experiment with an increased Zombie spawn complexity algorithm which will allow players to experience a more balanced and immersive experience while playing the game. We will also be focussing on including a cancel feature for game moves, and a save feature to allow a player to continue their gameplay at a later date. Finally, the end milestones for the project as a whole include: have a refined working game design with no “smells”, having the implementation of a save and load feature, and a working implementation of a game level builder. 
