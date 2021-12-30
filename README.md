# JavaFX-Animations

### Rain Animation

<table>
  <tr>
    <th colspan = 3>Rain Animation Parameters</tr>
  </tr>
  <tr>
    <th>Rain</th>
    <th>Fall</th>
    <th>KickFloor</th>
  </tr>
  <tr>
    <td>Drop Itself</td>
    <td>Fall X Angle</td>
    <td>Direction Vector XZ</td>
  </tr>
  <tr>
    <td>Drop Amount</td>
    <td>Fall Z Angle</td>
    <td>Initial Y Velocity</td>
  </tr>
  <tr>
    <td>Drop Height</td>
    <td>Fall Speed</td>
    <td>Initial XZ Velocity</td>
  </tr>
  <tr>
    <td>Floor Height</td>
    <td>Time between Drops</td>
    <td>Y Velocity Divider Per Kick</td>
  </tr>
  <tr>
    <td>Sky Area</td>
    <td>Drop Rotation Speed</td>
    <td>XZ Velocity Divider Per Kick</td>
  </tr>
  <tr>
    <td></td>
    <td></td>
    <td>Maximum Kicks</td>
  </tr>
  <tr>
    <td></td>
    <td></td>
    <td>Kick Speed</td>
  </tr>
</table>

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
    // Add a new rain drop in rain instance using shape3D
    rain.addRainDrop(new RainDrop(shape));
}

// Define parameters as want and start rain animation
rain.start();
```

Animation:

https://user-images.githubusercontent.com/72703544/147706131-4139aafa-bb0a-49b8-a8e5-23a79d799d78.mp4

### Box Explosion Animation


<table>
  <tr>
    <th>Box Explosion Parameters</th>
  </tr>
  <tr>
    <td>Box Itself</td>
  </tr>
  <tr>
    <td>Scale</td>
  </tr>
  <tr>
    <td>If Optimized</td>
  </tr>
  <tr>
    <td>Fragment Displacement Length</td>
  </tr>
  <tr>
    <td>Space Runned To Start Disappear</td>
  </tr>
  <tr>
    <td>Gap Fragment Disappear</td>
  </tr>
  <tr>
    <td>Fragment Disappeared Percent To Stop</td>
  </tr>
  <tr>
    <td>Explosion Coordinate Source</td>
  </tr>
</table>

### How to implement

```
// Create a Box instance to explode
Box boxToExplode = new Box(0.3, 0.3, 0.3);
// Create ExplosionBoxAnimation instance, defining parameters and using the Box instance
ExplosionBoxAnimation explosionBoxAnimation = new ExplosionBoxAnimation(boxToExplode, 15, true, parentGroupNode);
// Start animation
explosionBoxAnimation.start();
```

Animation:

https://user-images.githubusercontent.com/72703544/147706150-7df8667d-3523-4b5d-9dbe-5f04ace38bfd.mp4


