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
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Torus;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

import java.util.ArrayList;
import java.util.Vector;

/**
 * This is the Main Class of your Game. It should boot up your game and do initial initialisation
 * Move your Logic into AppStates or Controls or other java classes
 */
public class SolarExplorer extends SimpleApplication {

    CameraMovement cameraControl;

    public static void main(String[] args) {
        SolarExplorer app = new SolarExplorer();

        AppSettings settings = new AppSettings(true); // Modify settings for window
        settings.setResolution(1280, 700);
        settings.setWindowSize(1280, 700);

        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();


    }

    @Override
    public void simpleInitApp() {

        // Set cam location and disable the fly cam (no movement)
        cam.setLocation(new Vector3f(160, -20, 10));
        flyCam.setEnabled(false);
        setBackground();


        /// ================================== INITIALIZING ALL PLANET GEOMETRIES ONTO THE SCENE =================================
        // All planet sizes & rotation speeds will be calculated in reference to the Earth
        // SIZE:
        // Earth Radius = 3959 in miles & 1 will be earth's radius in this program

        // ROTATION SPEED:
        // Again, Earth's rotation will be 1 so we can easily calculate other rotations, we can do this by searching
        // how many earth days it takes a planet to rotate (earth day = 24 hours) OR how many hours it takes a planet to rotate,
        // compared to the 24 hours of the earth
        Geometry earth = preparePlanet("Earth", 1f, 1f, "Textures/earth_dmap.jpg");
        earth.setLocalTranslation(160, -20, 0);
        rootNode.attachChild(earth);

        // Mercury Radius = 2106 (miles)
        // Mercury's Radius calculated as a percentage of Earth's radius:
        // (1516 / 3959) * 100 = 38% , mercuries size will be 0.38 as it is in reference to earth's size which is 1.

        // 1 mercury day = 59 earth days
        // so mercury rotation = 1/59 = 0.01695
        Geometry mercury = preparePlanet("Mercury", 0.38f, 0.01695f,"Textures/mercury_dmap.jpg");
        mercury.setLocalTranslation(120, -20, 0); // I have set locations temporarily just so you can see all the planets
        rootNode.attachChild(mercury);

        // (3760 / 3959) * 100 = 94%
        // 1 venus day = 243 earth days
        // 1/243 = 0.004
        Geometry venus = preparePlanet("Venus", 0.94f, 0.004f,"Textures/venus_dmap.jpg");
        venus.setLocalTranslation(140, -20, 0);
        rootNode.attachChild(venus);

        // (2106 / 3959) * 100 = 53%
        // 1 mars day = 24.6 hours (24 / 24.6 = 0.96)
        Geometry mars = preparePlanet("Mars", 0.53f, 0.96f,"Textures/mars_dmap.jpg");
        mars.setLocalTranslation(180, -20, 0);
        rootNode.attachChild(mars);

        // (43,441 / 3959) * 100 = 1097%
        // 1 jupiter day = 10 hours (24 / 10 = 2.4)
        Geometry jupiter = preparePlanet("Jupiter",10.97f, 1.2f,"Textures/jupiter_dmap.jpg");
        jupiter.setLocalTranslation(210, -20, 0);
        rootNode.attachChild(jupiter);

        // (36,184 / 3959) * 100 = 913%
        // 1 saturn day = 10.75 hours (24 / 10.75 = 2.23)
        Geometry saturn = preparePlanet("Saturn", 9.13f, 2.23f,"Textures/saturn_dmap.jpg");
        saturn.setLocalTranslation(250, -20, 0);
        rootNode.attachChild(saturn);
        // Add saturns rings
        Geometry saturnRings = prepareRings(saturn, "Textures/saturn_ring.png",2.23f);
        rootNode.attachChild(saturnRings);

        // (15,759 / 3959) * 100 = 398%
        // 1 uranus day = 17 hours (24 / 17 = 1.4)
        Geometry uranus = preparePlanet("Uranus", 3.98f, 1f,"Textures/uranus_dmap.jpg");
        uranus.setLocalTranslation(290, -20, 0);
        rootNode.attachChild(uranus);

        // (15,299 / 3959) * 100 = 386%
        // 1 neptune day = 16 hours (24 / 16 = 1.5)
        Geometry neptune = preparePlanet("Neptune", 3.86f, 1.5f,"Textures/neptune_dmap.jpg");
        neptune.setLocalTranslation(320, -20, 0);
        rootNode.attachChild(neptune);

        Geometry sun = prepareSun(); // adds sun to scene
        // ===================================================================================================================================


        // Create a list of planet locations
        ArrayList<Vector3f> planetCoords = new ArrayList<>();
        planetCoords.add(sun.getLocalTranslation());
        planetCoords.add(mercury.getLocalTranslation());
        planetCoords.add(venus.getLocalTranslation());
        planetCoords.add(earth.getLocalTranslation());
        planetCoords.add(mars.getLocalTranslation());
        planetCoords.add(jupiter.getLocalTranslation());
        planetCoords.add(saturn.getLocalTranslation());
        planetCoords.add(uranus.getLocalTranslation());
        planetCoords.add(neptune.getLocalTranslation());

        // initialise the camera control class
        cameraControl = new CameraMovement(inputManager, cam, planetCoords);

    }

    // Function to create planets, requires the planet name, size of planet, rotation speed and path to its texture
    private Geometry preparePlanet(String name, float radius, float rotationSpeed, String texturePath){

        Sphere planetMesh = new Sphere(32, 32, radius); // creates a sphere mesh with the radius given
        planetMesh.setTextureMode(Sphere.TextureMode.Projected); // fixes texture stretching

        Geometry planet = new Geometry(name, planetMesh);

        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md"); // Uses Lighting so object materials interact with light
        Texture texture = assetManager.loadTexture(texturePath);
        material.setTexture("DiffuseMap", texture);

        planet.setMaterial(material);
        planet.addControl(new RotatingControl(rotationSpeed, Vector3f.UNIT_Z)); // When planet is first initialized, the south-pole is facing the camera, so we spin on the z-axis
        planet.setLocalRotation(new Quaternion().fromAngles(FastMath.PI / -3, 0, 0)); // rotates object to correct position

        return planet;

    }

    // Function to create the sun model
    private Geometry prepareSun(){

        // Calculate size of the Sun compared to Earth
        // (432,690 / 3959) * 100 = 10929%
        // Earth radius is size of '1' so sun's radius is '109.29'
        Sphere sunMesh = new Sphere(32, 32, (float) 109.29);
        sunMesh.setTextureMode(Sphere.TextureMode.Projected);

        Geometry sun = new Geometry("Sun", sunMesh);

        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"); // Unshaded so object does not need external light source to be visible
        Texture texture = assetManager.loadTexture("Textures/sun_dmap.jpg");
        material.setTexture("ColorMap", texture);
        sun.setMaterial(material);
        sun.setLocalTranslation(0, 0,0);
        rootNode.attachChild(sun);

        // we add a point light to the suns location, so that light will hit the surface of anything around it from the sun
        // basically, realistic lighting...
        PointLight sunlight = new PointLight();
        sunlight.setColor(ColorRGBA.White.mult(10)); // make it very bright
        sunlight.setRadius(10000f); // make light hit things far away
        sunlight.setPosition(sun.getLocalTranslation()); // set it at the suns position
        rootNode.addLight(sunlight); // add it to the root node

        return sun;
    }

    // Method to create rings for Saturn (and Uranus?)
    private Geometry prepareRings(Geometry planet,String texturePath, float rotationSpeed){

        // We use a torus geometry and then flatten it on the z-axis
        Torus ringMesh = new Torus(40, 40, 3f, 15f); // (circle samples, radial samples, inner radius, outer radius)
        Geometry rings = new Geometry("Ring", ringMesh);


        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md"); // Uses Lighting so object materials interact with light
        Texture texture = assetManager.loadTexture(texturePath);
        material.setTexture("DiffuseMap", texture);

        // Apply material and set properties of the rings
        rings.setMaterial(material);
        rings.setLocalScale(1,1,0.01f);
        rings.setLocalRotation(new Quaternion().fromAngles(-1f,-0.6f,0f)); // rotate rings so they appear on an angle
        rings.setLocalTranslation(planet.getLocalTranslation()); // Set the rings location to its planet

        // Make the rings spin
        rings.addControl(new RotatingControl(rotationSpeed, Vector3f.UNIT_Z));

        return rings;
    }

    // Method to set the skybox
    private void setBackground(){
        Texture spaceTexture = assetManager.loadTexture("Textures/space_background.jpg");
        Spatial sky = SkyFactory.createSky(assetManager, spaceTexture, SkyFactory.EnvMapType.EquirectMap);
        rootNode.attachChild(sky);
    }


    @Override //this method will be called every game tick and can be used to make updates
    public void simpleUpdate(float tpf) {
        cameraControl.update(tpf);
//        System.out.println(cam.getLocation());

    }

    @Override
    public void simpleRender(RenderManager rm) {
        //add render code here (if any)
    }
}
