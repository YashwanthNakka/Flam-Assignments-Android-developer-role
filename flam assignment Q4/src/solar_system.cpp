#include "solar_system.h"
#include "shader.h"
#include <cmath>
#include <vector>

SolarSystem::SolarSystem() : currentTime(0.0f) {
    // Initialize OpenGL objects
    glGenVertexArrays(1, &vao);
    glGenBuffers(1, &vbo);
    glGenBuffers(1, &ebo);
}

SolarSystem::~SolarSystem() {
    glDeleteVertexArrays(1, &vao);
    glDeleteBuffers(1, &vbo);
    glDeleteBuffers(1, &ebo);
}

void SolarSystem::init() {
    // Create sphere geometry
    createSphere(1.0f, 32, 32);

    // Initialize shaders
    sunShader = std::make_unique<Shader>();
    planetShader = std::make_unique<Shader>();

    sunShader->loadFromFiles("shaders/vertex.glsl", "shaders/sun_fragment.glsl");
    planetShader->loadFromFiles("shaders/vertex.glsl", "shaders/planet_fragment.glsl");

    // Initialize sun
    sun = {
        glm::vec3(0.0f),  // position
        glm::vec3(0.0f),  // rotation
        2.0f,             // scale
        0.0f,             // orbit radius
        0.0f,             // orbit speed
        0.5f,             // rotation speed
        false,            // has moon
        glm::vec3(0.0f),  // moon offset
        0.0f              // moon orbit speed
    };

    // Initialize planets
    CelestialBody planet1 = {
        glm::vec3(0.0f),  // position
        glm::vec3(0.0f),  // rotation
        0.5f,             // scale
        5.0f,             // orbit radius
        1.0f,             // orbit speed
        2.0f,             // rotation speed
        true,             // has moon
        glm::vec3(1.0f, 0.0f, 0.0f),  // moon offset
        3.0f              // moon orbit speed
    };

    CelestialBody planet2 = {
        glm::vec3(0.0f),  // position
        glm::vec3(0.0f),  // rotation
        0.7f,             // scale
        8.0f,             // orbit radius
        0.5f,             // orbit speed
        1.5f,             // rotation speed
        false,            // has moon
        glm::vec3(0.0f),  // moon offset
        0.0f              // moon orbit speed
    };

    planets.push_back(planet1);
    planets.push_back(planet2);

    // Enable depth testing
    glEnable(GL_DEPTH_TEST);
}

void SolarSystem::update(float deltaTime) {
    currentTime += deltaTime;
    updateOrbits(deltaTime);
}

void SolarSystem::render(const Camera& camera) {
    // Render sun
    renderCelestialBody(sun, camera, true);

    // Render planets
    for (const auto& planet : planets) {
        renderCelestialBody(planet, camera, false);
    }
}

void SolarSystem::createSphere(float radius, int sectors, int stacks) {
    std::vector<float> vertices;
    std::vector<unsigned int> indices;

    float sectorStep = 2 * M_PI / sectors;
    float stackStep = M_PI / stacks;

    for (int i = 0; i <= stacks; ++i) {
        float stackAngle = M_PI / 2 - i * stackStep;
        float xy = radius * cosf(stackAngle);
        float z = radius * sinf(stackAngle);

        for (int j = 0; j <= sectors; ++j) {
            float sectorAngle = j * sectorStep;

            float x = xy * cosf(sectorAngle);
            float y = xy * sinf(sectorAngle);

            // Position
            vertices.push_back(x);
            vertices.push_back(y);
            vertices.push_back(z);

            // Normal
            float nx = x / radius;
            float ny = y / radius;
            float nz = z / radius;
            vertices.push_back(nx);
            vertices.push_back(ny);
            vertices.push_back(nz);

            // Texture coordinates
            float s = (float)j / sectors;
            float t = (float)i / stacks;
            vertices.push_back(s);
            vertices.push_back(t);
        }
    }

    // Generate indices
    for (int i = 0; i < stacks; ++i) {
        int k1 = i * (sectors + 1);
        int k2 = k1 + sectors + 1;

        for (int j = 0; j < sectors; ++j, ++k1, ++k2) {
            if (i != 0) {
                indices.push_back(k1);
                indices.push_back(k2);
                indices.push_back(k1 + 1);
            }

            if (i != (stacks - 1)) {
                indices.push_back(k1 + 1);
                indices.push_back(k2);
                indices.push_back(k2 + 1);
            }
        }
    }

    // Bind VAO
    glBindVertexArray(vao);

    // Bind VBO and upload vertex data
    glBindBuffer(GL_ARRAY_BUFFER, vbo);
    glBufferData(GL_ARRAY_BUFFER, vertices.size() * sizeof(float), vertices.data(), GL_STATIC_DRAW);

    // Bind EBO and upload index data
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices.size() * sizeof(unsigned int), indices.data(), GL_STATIC_DRAW);

    // Set vertex attributes
    // Position
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)0);
    glEnableVertexAttribArray(0);
    // Normal
    glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)(3 * sizeof(float)));
    glEnableVertexAttribArray(1);
    // Texture coordinates
    glVertexAttribPointer(2, 2, GL_FLOAT, GL_FALSE, 8 * sizeof(float), (void*)(6 * sizeof(float)));
    glEnableVertexAttribArray(2);

    // Unbind
    glBindVertexArray(0);
}

void SolarSystem::updateOrbits(float deltaTime) {
    // Update planet positions
    for (auto& planet : planets) {
        float angle = currentTime * planet.orbitSpeed;
        planet.position.x = cos(angle) * planet.orbitRadius;
        planet.position.z = sin(angle) * planet.orbitRadius;
        planet.rotation.y += planet.rotationSpeed * deltaTime;

        if (planet.hasMoon) {
            float moonAngle = currentTime * planet.moonOrbitSpeed;
            planet.moonOffset.x = cos(moonAngle) * 1.0f;
            planet.moonOffset.z = sin(moonAngle) * 1.0f;
        }
    }
}

void SolarSystem::renderCelestialBody(const CelestialBody& body, const Camera& camera, bool isSun) {
    Shader& shader = isSun ? *sunShader : *planetShader;
    shader.use();

    // Set uniforms
    glm::mat4 model = glm::mat4(1.0f);
    model = glm::translate(model, body.position);
    model = glm::rotate(model, body.rotation.y, glm::vec3(0.0f, 1.0f, 0.0f));
    model = glm::scale(model, glm::vec3(body.scale));

    shader.setMat4("model", model);
    shader.setMat4("view", camera.getViewMatrix());
    shader.setMat4("projection", camera.getProjectionMatrix(800.0f / 600.0f));
    shader.setVec3("viewPos", camera.getPosition());

    if (isSun) {
        shader.setFloat("time", currentTime);
    } else {
        shader.setVec3("lightPos", sun.position);
    }

    // Draw sphere
    glBindVertexArray(vao);
    glDrawElements(GL_TRIANGLES, 32 * 32 * 6, GL_UNSIGNED_INT, 0);
    glBindVertexArray(0);

    // Render moon if the body has one
    if (body.hasMoon) {
        glm::mat4 moonModel = glm::mat4(1.0f);
        moonModel = glm::translate(moonModel, body.position + body.moonOffset);
        moonModel = glm::scale(moonModel, glm::vec3(0.2f));

        shader.setMat4("model", moonModel);
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, 32 * 32 * 6, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }
} 