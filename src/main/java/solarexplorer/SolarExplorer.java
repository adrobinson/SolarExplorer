package solarexplorer;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

/**
 * This is the Main Class of your Game. It should boot up your game and do initial initialisation
 * Move your Logic into AppStates or Controls or other java classes
 */
public class SolarExplorer extends SimpleApplication {

    public static void main(String[] args) {
        SolarExplorer app = new SolarExplorer();
        app.start();
    }

    private Geometry preparePlanet(float radius, String texturePath){ // Function to create planets, arguments required are radius & file path to the planets texture

        Sphere planetMesh = new Sphere(32, 32, radius); // creates a sphere mesh with the radius given
        planetMesh.setTextureMode(Sphere.TextureMode.Projected); // fixes texture stretching

        Geometry planet = new Geometry("Sphere", planetMesh);

        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture texture = assetManager.loadTexture(texturePath);
        material.setTexture("ColorMap", texture);

        planet.setMaterial(material);
        planet.setLocalRotation(new Quaternion().fromAngles(FastMath.PI / -3, 0, 0)); // rotates object to correct position

        return planet;

    }

    @Override
    public void simpleInitApp() {

        // All planet sizes will be calculated in reference to the Earth
        // Earth Radius = 3959 (miles)
        Geometry earth = preparePlanet(1f, "Textures/earth_dmap.jpg");
        rootNode.attachChild(earth);

        // Mercury Radius = 2106 (miles)
        // Mercury's Radius calculated as a percentage of Earth's radius:
        // (1516 / 3959) * 100 = 38% (0.38/1)
        Geometry mercury = preparePlanet(0.38f, "Textures/mercury_dmap.jpg");
        mercury.setLocalTranslation(-5, 0, 0); // I have set locations temporarily just so you can see all the planets
        rootNode.attachChild(mercury);

        // (3760 / 3959) * 100 = 94%
        Geometry venus = preparePlanet(0.94f, "Textures/venus_dmap.jpg");
        venus.setLocalTranslation(-3, 0, 0);
        rootNode.attachChild(venus);

        // (2106 / 3959) * 100 = 53%
        Geometry mars = preparePlanet(0.53f, "Textures/mars_dmap.jpg");
        mars.setLocalTranslation(3, 0, 0);
        rootNode.attachChild(mars);

        // (43,441 / 3959) * 100 = 1097%
        Geometry jupiter = preparePlanet(10.97f, "Textures/jupiter_dmap.jpg");
        jupiter.setLocalTranslation(0, 0, -30);
        rootNode.attachChild(jupiter);

        // (36,184 / 3959) * 100 = 913%
        Geometry saturn = preparePlanet(9.13f, "Textures/jupiter_dmap.jpg");
        saturn.setLocalTranslation(20, 0, -20);
        rootNode.attachChild(saturn);

        // (15,759 / 3959) * 100 = 398%
        Geometry uranus = preparePlanet(3.98f, "Textures/uranus_dmap.jpg");
        uranus.setLocalTranslation(7, 0, -5);
        rootNode.attachChild(uranus);

        // (15,299 / 3959) * 100 = 386%11
        Geometry neptune = preparePlanet(3.86f, "Textures/neptune_dmap.jpg");
        neptune.setLocalTranslation(15, 0, -5);
        rootNode.attachChild(neptune);



        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(10f, 10f, -1f).normalizeLocal());
        rootNode.addLight(sun);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //this method will be called every game tick and can be used to make updates
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //add render code here (if any)
    }
}
