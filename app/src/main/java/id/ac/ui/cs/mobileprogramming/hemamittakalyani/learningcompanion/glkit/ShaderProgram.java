package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.glkit;

import android.opengl.GLES20;
import android.renderscript.Matrix4f;

import java.nio.IntBuffer;
import java.util.HashMap;

public class ShaderProgram {

    private int vertexShaderHandle;
    private int fragmentShaderHandle;
    private int programHandle;
    private String log = "";
    private boolean isCompiled;

    private HashMap<String, Integer> uniforms = new HashMap<>();
    private HashMap<String, Integer> attributes = new HashMap<>();


    public ShaderProgram(String vertexShader, String fragmentShader) {
        if(vertexShader == null) throw new IllegalArgumentException("vertex shader must not be null");
        if(fragmentShader == null) throw new IllegalArgumentException("fragment shader must not be null");

        compileShaders(vertexShader, fragmentShader);

        if(isCompiled()) {
            fetchAttributes();
            fetchUniforms();
        }
    }

    public void begin() {
        GLES20.glUseProgram(programHandle);
    }

    public void end() {
        GLES20.glUseProgram(0);
    }

    private void compileShaders(String vertexShader, String fragmentShader) {
        vertexShaderHandle = loadShader(GLES20.GL_VERTEX_SHADER, vertexShader);
        fragmentShaderHandle = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);

        if(vertexShaderHandle == -1 || fragmentShaderHandle == -1) {
            isCompiled = false;
            return;
        }

        programHandle = linkProgram(createProgram());
        if(programHandle == -1) {
            isCompiled = false;
            return;
        }

        isCompiled = true;
    }

    private int createProgram() {
        int program = GLES20.glCreateProgram();
        return program != 0 ? program : -1;
    }

    private int linkProgram(int program) {
        if(program == -1)
            return -1;

        GLES20.glAttachShader(program, vertexShaderHandle);
        GLES20.glAttachShader(program, fragmentShaderHandle);
        GLES20.glLinkProgram(program);

        int[] linked = new int[1];
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linked, 0);
        if(linked[0] == GLES20.GL_FALSE) {
            String infoLog = GLES20.glGetProgramInfoLog(program);
            log += infoLog;
            GLES20.glDeleteProgram(program);
            return  -1;
        }
        return program;
    }

    private int loadShader(int shaderType, String shaderCode) {
        int shader = GLES20.glCreateShader(shaderType);
        if(shader == 0)
            return -1;

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        int[] compiled = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);

        if(compiled[0] == GLES20.GL_FALSE) {
            String infoLog = GLES20.glGetShaderInfoLog(shader);
            log += infoLog;
            GLES20.glDeleteShader(shader);
            return -1;
        }
        return shader;
    }


    private boolean isCompiled() {
        return isCompiled;
    }

    private int fetchAttributeLocation(String name) {

        int location = attributes.get(name);
        if(location == -1) {
            location = GLES20.glGetAttribLocation(programHandle, name);
            if(location != -1) {
                attributes.put(name, location);
            }
        }
        return location;
    }

    private int fetchUniformLocation(String name) {
        int location = uniforms.get(name);
        if(location == -1) {
            location = GLES20.glGetUniformLocation(programHandle, name);
            if(location != -1) {
                uniforms.put(name, location);
            }
        }
        return location;
    }


    private void fetchAttributes() {

        attributes.clear();

        IntBuffer params = IntBuffer.allocate(1);
        IntBuffer type = IntBuffer.allocate(1);
        GLES20.glGetProgramiv(programHandle, GLES20.GL_ACTIVE_ATTRIBUTES, params);
        int numAttributes = params.get(0);

        for(int i=0; i<numAttributes; i++) {
            params.compact();
            params.put(0, 1);
            type.clear();
            String name = GLES20.glGetActiveAttrib(programHandle, i, params, type);
            int location = GLES20.glGetAttribLocation(programHandle, name);
            attributes.put(name, location);
        }
    }

    private void fetchUniforms() {

        uniforms.clear();

        IntBuffer params = IntBuffer.allocate(1);
        IntBuffer type = IntBuffer.allocate(1);
        GLES20.glGetProgramiv(programHandle, GLES20.GL_ACTIVE_UNIFORMS, params);
        int numUniform = params.get(0);

        for(int i=0; i<numUniform; i++) {
            params.compact();
            params.put(0, 1);
            type.clear();
            String name = GLES20.glGetActiveUniform(programHandle, i, params, type);
            int location = GLES20.glGetUniformLocation(programHandle, name);
            uniforms.put(name, location);
        }
    }

    public void setUniformi(String name, int value) {
        int location = fetchUniformLocation(name);
        if(location == -1) return;
        GLES20.glUniform1i(location, value);
    }

    public void setUniformMatrix (String name, Matrix4f matrix) {
        setUniformMatrix(name, matrix, false);
    }

    public void setUniformMatrix (String name, Matrix4f matrix, boolean transpose) {
        setUniformMatrix(fetchUniformLocation(name), matrix, transpose);
    }

    public void setUniformMatrix (int location, Matrix4f matrix, boolean transpose) {
        if(location == -1) return;
        GLES20.glUniformMatrix4fv(location, 1, transpose, matrix.getArray(), 0);
    }

    public void setVertexAttribute (String name, int size, int type, boolean normalize, int stride, int offset) {
        int location = fetchAttributeLocation(name);
        if (location == -1)
            return;
        GLES20.glVertexAttribPointer(location, size, type, normalize, stride, offset);
    }

    public void disableVertexAttribute (String name) {
        int location = fetchAttributeLocation(name);
        if (location == -1) return;
        GLES20.glDisableVertexAttribArray(location);
    }

    public void enableVertexAttribute (String name) {
        int location = fetchAttributeLocation(name);
        if (location == -1) return;
        GLES20.glEnableVertexAttribArray(location);
    }

}
