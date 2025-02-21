package solarexplorer;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

/**
 * This is the Main Class of your Game. It should boot up your game and do initial initialisation
 * Move your Logic into AppStates or Controls or other java classes
 */
public class SolarExplorer extends SimpleApplication {

    public static void main(String[] args) {
        SolarExplorer app = new SolarExplorer();

        AppSettings settings = new AppSettings(true);
        settings.setResolution(1280, 700);
        settings.setWindowSize(1280, 700);

        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }

    private Geometry preparePlanet(float radius, String texturePath){ // Function to create planets, arguments required are radius & file path to the planets texture

        Sphere planetMesh = new Sphere(32, 32, radius); // creates a sphere mesh with the radius given
        planetMesh.setTextureMode(Sphere.TextureMode.Projected); // fixes texture stretching

        Geometry planet = new Geometry("Sphere", planetMesh);

        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md"); // Uses Lighting so object materials interact with light
        Texture texture = assetManager.loadTexture(texturePath);
        material.setTexture("DiffuseMap", texture);

        planet.setMaterial(material);
        planet.setLocalRotation(new Quaternion().fromAngles(FastMath.PI / -3, 0, 0)); // rotates object to correct position

        return planet;

    }

    private void prepareSun(){

        // Calculate size of the Sun compared to Earth
        // (432,690 / 3959) * 100 = 10929%
        // Earth radius is size of '1' so sun's radius is '109.29'
        Sphere sunMesh = new Sphere(32, 32, (float) 109.29);
        sunMesh.setTextureMode(Sphere.TextureMode.Projected);

        Geometry sun = new Geometry("Sphere", sunMesh);

        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"); // Unshaded so object does not need external light source to be visible
        Texture texture = assetManager.loadTexture("Textures/sun_dmap.jpg");
         material.setTexture("ColorMap", texture);
        sun.setMaterial(material);
        sun.setLocalTranslation(0, 0,0);
        rootNode.attachChild(sun);

        PointLight sunlight = new PointLight();
        sunlight.setColor(ColorRGBA.White.mult(10));
        sunlight.setRadius(10000f);
        sunlight.setPosition(sun.getLocalTranslation());
        rootNode.addLight(sunlight);

    }

    private void setBackground(){
        Texture spaceTexture = assetManager.loadTexture("Textures/space_background.jpg");
        Spatial sky = SkyFactory.createSky(assetManager, spaceTexture, SkyFactory.EnvMapType.EquirectMap);
        rootNode.attachChild(sky);
    }

    @Override
    public void simpleInitApp() {

        cam.setLocation(new Vector3f(150, 0, 150));
        flyCam.setMoveSpeed(50);
        setBackground();

        // All planet sizes will be calculated in reference to the Earth
        // Earth Radius = 3959 (miles)
        Geometry earth = preparePlanet(1f, "Textures/earth_dmap.jpg");
        earth.setLocalTranslation(160, -20, 0);
        rootNode.attachChild(earth);

        // Mercury Radius = 2106 (miles)
        // Mercury's Radius calculated as a percentage of Earth's radius:
        // (1516 / 3959) * 100 = 38% (0.38/1)
        Geometry mercury = preparePlanet(0.38f, "Textures/mercury_dmap.jpg");
        mercury.setLocalTranslation(120, -20, 0); // I have set locations temporarily just so you can see all the planets
        rootNode.attachChild(mercury);

        // (3760 / 3959) * 100 = 94%
        Geometry venus = preparePlanet(0.94f, "Textures/venus_dmap.jpg");
        venus.setLocalTranslation(140, -20, 0);
        rootNode.attachChild(venus);

        // (2106 / 3959) * 100 = 53%
        Geometry mars = preparePlanet(0.53f, "Textures/mars_dmap.jpg");
        mars.setLocalTranslation(180, -20, 0);
        rootNode.attachChild(mars);

        // (43,441 / 3959) * 100 = 1097%
        Geometry jupiter = preparePlanet(10.97f, "Textures/jupiter_dmap.jpg");
        jupiter.setLocalTranslation(210, -20, 0);
        rootNode.attachChild(jupiter);

        // (36,184 / 3959) * 100 = 913%
        Geometry saturn = preparePlanet(9.13f, "Textures/jupiter_dmap.jpg");
        saturn.setLocalTranslation(250, -20, 0);
        rootNode.attachChild(saturn);

        // (15,759 / 3959) * 100 = 398%
        Geometry uranus = preparePlanet(3.98f, "Textures/uranus_dmap.jpg");
        uranus.setLocalTranslation(290, -20, 0);
        rootNode.attachChild(uranus);

        // (15,299 / 3959) * 100 = 386%
        Geometry neptune = preparePlanet(3.86f, "Textures/neptune_dmap.jpg");
        neptune.setLocalTranslation(320, -20, 0);
        rootNode.attachChild(neptune);

        prepareSun();

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
