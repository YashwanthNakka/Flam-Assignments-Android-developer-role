#version 300 es
precision mediump float;

in vec3 vNormal;
in vec3 vFragPos;
in vec2 vTexCoord;

out vec4 fragColor;

uniform float time;
uniform vec3 viewPos;

void main() {
    // Base sun color (yellow-orange)
    vec3 baseColor = vec3(1.0, 0.7, 0.3);
    
    // Calculate glow effect
    float glow = sin(time * 2.0) * 0.1 + 0.9; // Pulsing effect
    
    // Calculate rim lighting for edge glow
    vec3 viewDir = normalize(viewPos - vFragPos);
    float rim = 1.0 - max(dot(viewDir, vNormal), 0.0);
    rim = pow(rim, 3.0);
    
    // Combine effects
    vec3 finalColor = baseColor * (1.0 + rim * glow);
    
    // Add some variation based on normal
    float normalVariation = dot(vNormal, vec3(0.0, 1.0, 0.0)) * 0.2 + 0.8;
    finalColor *= normalVariation;
    
    fragColor = vec4(finalColor, 1.0);
} 