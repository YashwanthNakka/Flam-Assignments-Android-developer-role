cmake_minimum_required(VERSION 3.10)
project(SolarSystem)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Find OpenGL ES
find_package(OpenGL REQUIRED)

# Add GLFW for window management
find_package(glfw3 REQUIRED)

# Add GLM for mathematics
find_package(glm REQUIRED)

# Add source files
set(SOURCES
    src/main.cpp
    src/solar_system.cpp
    src/camera.cpp
    src/shader.cpp
)

# Add header files
set(HEADERS
    include/solar_system.h
    include/camera.h
    include/shader.h
)

# Create executable
add_executable(${PROJECT_NAME} ${SOURCES} ${HEADERS})

# Include directories
target_include_directories(${PROJECT_NAME} PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/include
    ${OPENGL_INCLUDE_DIR}
    ${GLFW_INCLUDE_DIRS}
    ${GLM_INCLUDE_DIRS}
)

# Link libraries
target_link_libraries(${PROJECT_NAME} PRIVATE
    ${OPENGL_LIBRARIES}
    glfw
    ${GLM_LIBRARIES}
) 