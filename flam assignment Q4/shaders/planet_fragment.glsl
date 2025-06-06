#version 300 es
precision mediump float;

in vec3 vNormal;
in vec3 vFragPos;
in vec2 vTexCoord;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform vec3 viewPos;
uniform vec3 lightPos; // Sun position
uniform bool useTexture;

void main() {
    // Ambient light
    float ambientStrength = 0.2;
    vec3 ambient = ambientStrength * vec3(1.0);
    
    // Diffuse light
    vec3 norm = normalize(vNormal);
    vec3 lightDir = normalize(lightPos - vFragPos);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * vec3(1.0);
    
    // Specular light
    float specularStrength = 0.5;
    vec3 viewDir = normalize(viewPos - vFragPos);
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32.0);
    vec3 specular = specularStrength * spec * vec3(1.0);
    
    // Combine lighting
    vec3 result = ambient + diffuse + specular;
    
    // Apply texture or color
    vec4 texColor;
    if (useTexture) {
        texColor = texture(textureSampler, vTexCoord);
    } else {
        // Default color if no texture
        texColor = vec4(0.5, 0.5, 0.5, 1.0);
    }
    
    fragColor = vec4(result * texColor.rgb, texColor.a);
} 