package solarexplorer;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

import java.util.ArrayList;

public class CameraMovement{

    private final ArrayList<Vector3f> camPositions = new ArrayList<>();
    private final ArrayList<Vector3f> planetCoords;
    private final InputManager inputManager;
    private final Camera cam;
    private int index = 0;
    private Vector3f currentPos, targetPos;
    private Vector3f currentLookAt, targetLookAt;
    private float transitionProgress = 1f;
    private final float transitionSpeed = 1f; // Transition duration in seconds



    public CameraMovement (InputManager ipm, Camera cam, ArrayList<Vector3f> planetCoords){
        this.inputManager = ipm;
        this.cam = cam;
        this.planetCoords = planetCoords;
        addPositionsToList();
        initKeys();

    }

    private void addPositionsToList(){
        camPositions.add(new Vector3f(371.5833f, -23.170752f, 277.7154f)); // Positions that camera will move to when moving between planets
        camPositions.add(new Vector3f(118.742744f, -20.15924f, 2.8922782f));
        camPositions.add(new Vector3f(137.92656f, -20.198917f, 5.853851f));
        camPositions.add(new Vector3f(159.98541f, -20.008472f, 6.9954786f));
        camPositions.add(new Vector3f(178.43663f, -20.110064f, 3.970817f));
        camPositions.add(new Vector3f(190.72197f, -20.409294f, 32.774086f));
        camPositions.add(new Vector3f(217.33705f, -22.488308f, 35.756268f));
        camPositions.add(new Vector3f(275.9376f, -19.672451f, 19.24524f));
        camPositions.add(new Vector3f(301.94614f, -20.58642f, 9.968126f));
        camPositions.add(new Vector3f(178.56639f, -21.826445f, 142.91884f));
    }

    private void startTransition(int newIndex) {
        if (transitionProgress < 1f) return; // Prevent interrupting an ongoing transition

        index = newIndex;
        currentPos = cam.getLocation().clone();
        targetPos = camPositions.get(index);
        currentLookAt = cam.getDirection().clone().normalize();
        targetLookAt = planetCoords.get(index).subtract(targetPos).normalize();
        transitionProgress = 0f;
    }

    public void update(float tpf) {
        if (transitionProgress < 1f) {
            transitionProgress += tpf / transitionSpeed;
            if (transitionProgress > 1f) transitionProgress = 1f;

            Vector3f newPos = FastMath.interpolateLinear(transitionProgress, currentPos, targetPos);
            cam.setLocation(newPos);

            Vector3f newLookAt = FastMath.interpolateLinear(transitionProgress, currentLookAt, targetLookAt);
            cam.lookAt(newPos.add(newLookAt), Vector3f.UNIT_Y);
        }
    }

    private void initKeys() {
        inputManager.addMapping("Back", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Next", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(actionListener, "Back", "Next");
    }

    private final ActionListener actionListener = (name, isPressed, tpf) -> {
        if (!isPressed) return;

        if (name.equals("Back") && index > 0) {
            startTransition(index - 1);
        }
        if (name.equals("Next") && index < camPositions.size() - 1) {
            startTransition(index + 1);
        }
    };


}
