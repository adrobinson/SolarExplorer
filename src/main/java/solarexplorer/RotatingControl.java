package solarexplorer;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class RotatingControl extends AbstractControl {
    private float speed;
    private Vector3f axis;

    public RotatingControl(float speed, Vector3f axis){
        this.speed = speed;
        this.axis = axis;
    }

    @Override
    protected void controlUpdate(float tpf) {
        Quaternion rotation = new Quaternion();
        rotation.fromAngleAxis(speed * tpf, axis);
        spatial.rotate(rotation); // spatial is assigned to any object that is assigned .addControl()
    }

    @Override
    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
