varying vec2 v_texCoord;
varying vec4 v_color;

uniform sampler2D u_texture;

void main() {
	vec4 color = texture2D(u_texture, v_texCoord);
	vec4 transparentColor = vec4(0.0, 0.7, 1.0, 0.0);

	gl_FragColor = mix(color, transparentColor, 0.3);
}