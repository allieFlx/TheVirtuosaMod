#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoord;
varying vec4 v_color;

uniform sampler2D u_texture;

void main() {
	vec4 color = texture2D(u_texture, v_texCoord);
	vec4 transparentColor = v_color * vec4(0.0, 0.5, 0.7, 3.0);
	vec4 opaqueColor = v_color * vec4(1.0, 1.0, 1.0, 0.75);

    float grayscaleValue = dot(color.rgb, vec3(0.299, 0.587, 0.114));
    gl_FragColor = 0.5 + 1.5 * (mix(transparentColor, opaqueColor, grayscaleValue) - 0.5);

    float a = color.w;
    float b = transparentColor.w;
	gl_FragColor.w = a < 0.1 ? 0.0 : (a + b) / 2.0;
}