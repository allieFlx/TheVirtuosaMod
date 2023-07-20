#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoord;
varying vec4 v_color;

uniform sampler2D u_texture;

float softLight(float a, float b){
    return b < 0.5 ? (2.0 * a * b + a * a * (1.0 - 2.0 * b)) : (sqrt(a) * (2.0 * b - 1.0) + 2.0 * a * (1.0 - b));
}

void main() {
	vec4 color = texture2D(u_texture, v_texCoord);
	vec4 transparentColor = v_color * vec4(0.0, 0.75, 1.0, 0.3);

	gl_FragColor = color;

    gl_FragColor.x = softLight(color.x, transparentColor.x);
    gl_FragColor.y = softLight(color.y, transparentColor.y);
    gl_FragColor.z = softLight(color.z, transparentColor.z);

    float a = color.w;
    float b = transparentColor.w;
	gl_FragColor.w = a < 0.1 ? 0.0 : (a + b) / 2.0;
}