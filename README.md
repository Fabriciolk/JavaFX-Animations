# JavaFX-Rain-Simulation
Rain Simulation in 3D

#### Rain Parameters:

<ul>
  <li>Drop Itself</li>
  <li>Drop Amount</li>
  <li>Sky Height</li>
  <li>Floor Height</li>
  <li>Sky Area</li>
</ul>
  
#### Fall Parameters:

<ul>
  <li>Fall X Angle</li>
  <li>Fall Z Angle</li>
  <li>Fall Speed</li>
  <li>Time between Drops</li>
  <li>Drop Rotation Speed</li>
</ul>

#### KickFloor Parameters:

<ul>
  <li>Direction Vector XZ</li>
  <li>Initial Y Velocity</li>
  <li>Initial XZ Velocity</li>
  <li>Y Velocity Divider Per Kick</li>
  <li>XZ Velocity Divider Per Kick</li>
  <li>Maximum Kicks</li>
  <li>Kick Speed</li>
</ul>

#### How to implement rain WITH drop rotation:

```
// Create a rain instance
Rain rain = new Rain();

// Fill rain with Shape3D rain drops (boxes in this example)
for (int i = 0; i < rainDropAmount; i++)
{
    Shape3D shape = new Box(0.03, 0.03, 0.03);
    // Create xyz rotations
    Rotate xR = new Rotate(0, Rotate.X_AXIS);
    Rotate yR = new Rotate(0, Rotate.Y_AXIS);
    Rotate zR = new Rotate(0, Rotate.Z_AXIS);
    // Attach rotations in Shape3D
    shape.getTransforms().addAll(xR, yR, zR);
    // Attach each Shape3D in parent Group node
    parentGroupNode.getChildren().add(shape);
    // Add a new rain drop in rain instance using shape3D and rotations
    rain.addRainDrop(new RainDrop(shape, xR, yR, zR));
}

// Define parameters as want and start rain animation
rain.start();
```

#### How to implement rain WITHOUT drop rotation:

```
// Create a rain instance
Rain rain = new Rain();

// Fill rain with Shape3D rain drops (boxes in this example)
for (int i = 0; i < rainDropAmount; i++)
{
    Shape3D shape = new Box(0.03, 0.03, 0.03);
    // Attach each Shape3D in parent Group node
    parentGroupNode.getChildren().add(shape);
    // Add a new rain drop in rain instance using shape3D and rotations
    rain.addRainDrop(new RainDrop(shape, xR, yR, zR));
}

// Define parameters as want and start rain animation
rain.start();
```

Simulation:

https://user-images.githubusercontent.com/72703544/147505734-9cd50c4d-566b-4f51-8597-183c842dacc4.mp4

