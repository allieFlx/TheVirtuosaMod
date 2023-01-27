#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoord;
varying vec4 v_color;

uniform sampler2D u_texture;

void main() {
	vec4 color = texture2D(u_texture, v_texCoord);
	vec4 transparentColor = v_color * vec4(0.0, 0.75, 1.0, 0.3);

    // b < 0.5 ? (2.0 * a * b) : (1.0 - 2.0 * (1.0 - a) * (1.0 - b))
	// gl_FragColor = mix(color, transparentColor, 0.3);
	gl_FragColor = color;

    float a = color.x;
    float b = transparentColor.x;
	gl_FragColor.x = b < 0.5 ? (2.0 * a * b) : (1.0 - 2.0 * (1.0 - a) * (1.0 - b));

    a = color.y;
    b = transparentColor.y;
	gl_FragColor.y = b < 0.5 ? (2.0 * a * b) : (1.0 - 2.0 * (1.0 - a) * (1.0 - b));

    a = color.z;
    b = transparentColor.z;
	gl_FragColor.z = b < 0.5 ? (2.0 * a * b) : (1.0 - 2.0 * (1.0 - a) * (1.0 - b));

    a = color.w;
    b = transparentColor.w;
	gl_FragColor.w = a < 0.1 ? 0.0 : (a + b) / 2.0;
}