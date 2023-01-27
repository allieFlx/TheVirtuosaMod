#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoord;
varying vec4 v_color;

uniform sampler2D u_texture;

void main() {
	vec4 color = texture2D(u_texture, v_texCoord);

	// do stuff

	gl_FragColor = color;
}