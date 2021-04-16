# Mazes
###### by: Christopher Medlin & Will DeBernardi

In this project, we have implemented 4 maze generation algorithms, and 3 maze solving algorithms. Specifically,
for our generation algorithms, we have implemented: Aldous-Broder, Depth-First, Kruskal's, and Prim's. For our
solving algorithms, we implemented: Pledge, Random-Mouse, and Wall Follower.

##  Usage
This program can be run with the following command:

``java -jar mazesv1_Medlin+DeBernardi.jar <input text filename>.txt``

where the input text file is the settings text file which has the following parameters:

````
window size (in px)
cell size (in px)
generator
solver
framrate (60 is recommended)
````

The generator algorithms can be specified with the following keywords:
````
dfs (depth first generator)
kruskal
aldous
prim
````

The solver algorithms can be specified with the following keywords:
````
mouse
mouse_thread (multithreaded mouse)
wall
wall_thread (multithreaded wall follower)
pledge
````

## Bugs
None that we are currently aware of.