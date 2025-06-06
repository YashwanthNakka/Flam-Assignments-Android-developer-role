#pragma once

#include <GLES3/gl3.h>
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <vector>
#include <memory>

class Shader;
class Camera;

struct CelestialBody {
    glm::vec3 position;
    glm::vec3 rotation;
    float scale;
    float orbitRadius;
    float orbitSpeed;
    float rotationSpeed;
    bool hasMoon;
    glm::vec3 moonOffset;
    float moonOrbitSpeed;
};

class SolarSystem {
public:
    SolarSystem();
    ~SolarSystem();

    void init();
    void update(float deltaTime);
    void render(const Camera& camera);
    void handleInput(float deltaTime);

private:
    // OpenGL objects
    GLuint vao;
    GLuint vbo;
    GLuint ebo;
    
    // Shaders
    std::unique_ptr<Shader> sunShader;
    std::unique_ptr<Shader> planetShader;
    
    // Celestial bodies
    CelestialBody sun;
    std::vector<CelestialBody> planets;
    
    // Time tracking
    float currentTime;
    
    // Helper methods
    void createSphere(float radius, int sectors, int stacks);
    void updateOrbits(float deltaTime);
    void renderCelestialBody(const CelestialBody& body, const Camera& camera, bool isSun);
}; 