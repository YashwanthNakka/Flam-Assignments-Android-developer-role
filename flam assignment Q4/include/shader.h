#pragma once

#include <GLES3/gl3.h>
#include <glm/glm.hpp>
#include <string>
#include <unordered_map>

class Shader {
public:
    Shader();
    ~Shader();

    // Load and compile shaders
    bool loadFromFiles(const std::string& vertexPath, const std::string& fragmentPath);
    bool loadFromStrings(const std::string& vertexSource, const std::string& fragmentSource);

    // Use/activate the shader
    void use() const;

    // Uniform setters
    void setBool(const std::string& name, bool value) const;
    void setInt(const std::string& name, int value) const;
    void setFloat(const std::string& name, float value) const;
    void setVec2(const std::string& name, const glm::vec2& value) const;
    void setVec3(const std::string& name, const glm::vec3& value) const;
    void setVec4(const std::string& name, const glm::vec4& value) const;
    void setMat2(const std::string& name, const glm::mat2& mat) const;
    void setMat3(const std::string& name, const glm::mat3& mat) const;
    void setMat4(const std::string& name, const glm::mat4& mat) const;

private:
    GLuint programID;
    std::unordered_map<std::string, GLint> uniformLocationCache;

    // Helper methods
    bool compileShader(GLuint& shader, GLenum type, const std::string& source);
    bool linkProgram();
    GLint getUniformLocation(const std::string& name) const;
}; 