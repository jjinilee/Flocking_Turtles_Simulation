# Flocking_turtles_Simulation

This project is to design and implement a flocking simulation in Java, where each turtle will move around in random direction on the screen and interact with others to move in flocking behaviour by following a set of 3 main behaviours, of which the parameters can be varied by user. The 3 main behaviours are cohesion, separation, and alignment. Turtles will continue to move in random direction when cohesion / separation / alignment sliders
are set to zero.

# Design of the project

- ‘Add turtles’ button

When the button is clicked, randomly moving turtles will be added into the canvas at random positions. The distance between the centre of the obstacle (350, 200) and turtle will be calculated. If it is smaller than 75, turtles will appear in the obstacle where they are not supposed to. Therefore, the recursive addNewTurtle() method is designed to add turtles in another position

-  Cohesion Slider

Cohesion causes the neighbour turtles to move towards the centre of mass of those around them. Cohesion slider will adjust the value of cohesion behaviour of the turtles, also affecting the cohesion angle, required for the turtle to turn to face the centre of mass.

- Separation Slider

Separation is the opposite of cohesion, where it causes turtles to move away from the centre of mass around them. Separation slider will adjust the value of separation behaviour of the turtles, also affecting the separation angle required for the turtle to turn to face away the centre of mass.

- Alignment Slider

Alignment causes turtles to align its direction with those around them. Alignment slider will adjust the value of alignment behaviour of the turtles, also affecting the alignment angle required for turtle to turn to align with the average direction of all otherturtles within the flock area.

- Flock area Slider

Flock area is the radius of the area where the turtle will flock. When the flock area increases, the distance that other turtles are counted as neighbour turtles increases and vice versa. Turtles will not have any behaviours when flock area is set to 0.
