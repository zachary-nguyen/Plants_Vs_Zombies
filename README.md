## Plants_Vs_Zombies

SYSC 3110 Group Project.

Section: L1 

Group: 11

### Team Members:

- Zachary Nguyen

- Fareed Ahmad

- Mathew Smith

- Eric Cosoreanu (#101041744)

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
        
  * UML Diagrams
  
  * Other Documentation

### Known Issues:

- Wave logic is not fully implemented. Games are intended to have a maximum wave limit in order for the player to be able to win the game. 

- Need to fix end game logic, currently the game has an issue where zombies "overrunning" the playing field does not end the game. In future versions of the game, the game will be able to recognize when the zombies have beaten the player. 

### Roadmap:
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Our next steps regarding this project are to first fix the remaining issues with the game mechanics, such as those listed in the “known issues” section. Additionally, we will be focussing on adding features such as a drop-down menu for plant selections, introducing new GUI imagery for various events occuring during gameplay (I.E. different images for collisions), and introducing an option pane that lets the player know the current status of the game. We will also be focussing on including a cancel feature for game moves, and an undo and redo feature to allow a player more control over their gameplay. Finally, the end milestones for the project as a whole include: have a refined working game design with no “smells”, having the implementation of a save and load feature, and a working implementation of a game level builder. 
