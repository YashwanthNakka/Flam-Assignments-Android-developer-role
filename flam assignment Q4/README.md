# Solar System Visualization

A 3D solar system visualization using OpenGL ES 3.0 that demonstrates the graphics pipeline, transformations, and shaders.

## Features

- Central Sun with a glowing effect
- Two planets orbiting at different speeds and distances
- One planet has a moon orbiting it
- Custom shaders for realistic lighting and effects
- Camera controls for viewing the scene from different angles
- Smooth animations and transformations

## Requirements

- CMake 3.10 or higher
- C++17 compatible compiler
- OpenGL ES 3.0
- GLFW3
- GLM

## Building the Project

1. Create a build directory:
```bash
mkdir build
cd build
```

2. Configure and build the project:
```bash
cmake ..
cmake --build .
```

## Running the Application

After building, run the executable:
```bash
./SolarSystem
```

## Controls

- Mouse movement: Rotate the camera view
- Mouse scroll: Zoom in/out
- ESC: Exit the application

## Project Structure

- `src/`: Source files
  - `main.cpp`: Main application entry point
  - `solar_system.cpp`: Solar system simulation implementation
  - `camera.cpp`: Camera controls implementation
  - `shader.cpp`: Shader management implementation
- `include/`: Header files
  - `solar_system.h`: Solar system class declaration
  - `camera.h`: Camera class declaration
  - `shader.h`: Shader class declaration
- `shaders/`: GLSL shader files
  - `vertex.glsl`: Common vertex shader
  - `sun_fragment.glsl`: Sun-specific fragment shader
  - `planet_fragment.glsl`: Planet and moon fragment shader 