#version 300 es
precision mediump float;

// Input vertex attributes
in vec3 aPosition;
in vec3 aNormal;
in vec2 aTexCoord;

// Output to fragment shader
out vec3 vNormal;
out vec2 vTexCoord;
out vec3 vFragPos;

// Uniforms for transformations
uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
    // Calculate vertex position in world space
    vec4 worldPos = model * vec4(aPosition, 1.0);
    vFragPos = worldPos.xyz;
    
    // Transform normal to world space
    vNormal = mat3(transpose(inverse(model))) * aNormal;
    
    // Pass texture coordinates
    vTexCoord = aTexCoord;
    
    // Calculate final position
    gl_Position = projection * view * worldPos;
} 