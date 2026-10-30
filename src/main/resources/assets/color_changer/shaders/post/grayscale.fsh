#version 150

uniform sampler2D In;
in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 color = texture(In, texCoord);
    float luminance = dot(color.rgb, vec3(0.2126, 0.7152, 0.0722));
    fragColor = vec4(vec3(luminance), color.a);
}