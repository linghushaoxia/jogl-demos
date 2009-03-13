package demos.es1.cubefbo;

import java.nio.*;
import javax.media.nwi.*;
import javax.media.opengl.*;
import com.sun.javafx.newt.*;

public class Main implements MouseListener {

    public boolean quit = false;
    public GLWindow window = null;

    public void mouseClicked(MouseEvent e) {
        switch(e.getClickCount()) {
            case 1:
                if(null!=window) {
                    window.setFullscreen(!window.isFullscreen());
                }
                break;
            default: 
                quit=true;
                break;
        }
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }
    public void mouseMoved(MouseEvent e) {
    }
    public void mouseDragged(MouseEvent e) {
    }

    private void run(int type) {
        int width = 800;
        int height = 480;
        System.out.println("cubefbo.Main.run()");
        GLProfile.setProfileGLAny();
        try {
            // Hook this into EGL
            NWCapabilities caps = new NWCapabilities();
            // For emulation library, use 16 bpp
            caps.setRedBits(5);
            caps.setGreenBits(6);
            caps.setBlueBits(5);
            caps.setDepthBits(16);

            Window nWindow = null;
            if(0!=(type&USE_AWT)) {
                Display nDisplay = NewtFactory.createDisplay(NewtFactory.AWT, null); // local display
                Screen nScreen  = NewtFactory.createScreen(NewtFactory.AWT, nDisplay, 0); // screen 0
                nWindow = NewtFactory.createWindow(NewtFactory.AWT, nScreen, caps);
            }
            window = GLWindow.create(nWindow, caps);

            window.addMouseListener(this);

            window.enablePerfLog(true);
            window.setSize(width, height);
            window.setFullscreen(true);
            window.setVisible(true);

            GL gl = window.getGL();
            FBCubes cubes = new FBCubes();
            window.addGLEventListener(cubes);

            while ( !quit && window.getDuration() < 31000) {
                window.display();
            }

            // Shut things down cooperatively
            window.close();
            window.getFactory().shutdown();
            System.out.println("cubefbo.Main shut down cleanly.");
        } catch (GLException e) {
            e.printStackTrace();
        }
    }

    public static int USE_NEWT      = 0;
    public static int USE_AWT       = 1 << 0;

    public static void main(String[] args) {
        int type = USE_NEWT ;
        for(int i=args.length-1; i>=0; i--) {
            if(args[i].equals("-awt")) {
                type |= USE_AWT; 
            }
        }
        new Main().run(type);
        System.exit(0);
    }

}
