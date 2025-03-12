package solarexplorer;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

import java.util.ArrayList;

public class CameraMovement{

    ArrayList<Vector3f> camPositions = new ArrayList<>();
    ArrayList<Vector3f> planetCoords;
    private static CameraMovement cameraMovement;
    private InputManager inputManager;
    private Camera cam;
    private int index = 0;



    public CameraMovement(InputManager ipm, Camera cam, ArrayList<Vector3f> planetCoords){
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
    }

    private void moveToPos(int index){
        cam.setLocation(camPositions.get(index));
        cam.lookAt(planetCoords.get(index), Vector3f.UNIT_Y);
    }

    private void initKeys(){
        inputManager.addMapping("Back", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addListener(actionListener, "Back");

        inputManager.addMapping("Next", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(actionListener, "Next");
    }

    private ActionListener actionListener = (name, isPressed, tpf) -> {
        if (name.equals("Back") && isPressed) {
            if (index != 0){
                System.out.println("A PRESSED");
                index--;
                System.out.println(index);
                moveToPos(index);
            }
        }

        if (name.equals("Next") && isPressed) {
            if (index != camPositions.size() - 1){
                System.out.println("D PRESSED");
                index++;
                System.out.println(index);
                moveToPos(index);
            }
        }
    };


}
