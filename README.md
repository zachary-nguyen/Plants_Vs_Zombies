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
        
  * UML Diagrams
  
  * Other Documentation

### Known Issues:

  * Cannot spawn a new sun without collecting the current sun generated on the map.
    That is, there cannot be more than one sun on the map at any time. 

  * A bullet cannot jump over another bullet in its path. 
    Thus, if two bullets collide, one of the bullets will be eliminated from the map. 

  * Currently, a plant can be placed where a bullet is on the map, eliminating the bullet. 
    In the finished version a plant will not be allowed to be placed on a square occupied by a bullet. 


### Roadmap:
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Our next steps regarding this project are to first fix the remaining issues with the game mechanics, such as those listed in the “known issues” section. Additionally, our next big step with this project will be to convert the text-based project we have now to a GUI-based version. This GUI version will have a grid of spaces with plant and zombie objects represented by their own distinct images. Additionally, we will also be creating test classes to test the game’s functionality. 
  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;In the future, the project will also have various features including a cancel feature for game moves, and an undo and redo feature to allow a player more control over their gameplay. Finally, the end milestones for the project as a whole include: have a refined working game design with no “smells”, having the implementation of a save and load feature, and a working implementation of a game level builder. 
  
