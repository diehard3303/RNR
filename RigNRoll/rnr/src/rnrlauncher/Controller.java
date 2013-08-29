/*
 * @(#)Controller.java   13/08/26
 * 
 * Copyright (c) 2013 DieHard Development
 *
 * All rights reserved.
Released under the FreeBSD  license 
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met: 

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer. 
2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution. 

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those
of the authors and should not be interpreted as representing official policies, 
either expressed or implied, of the FreeBSD Project.
 *
 *
 *
 */


package rnr.src.rnrlauncher;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrcore.eng;
import rnr.src.rnrlauncher.Controller.ResolutionResources;
import rnr.src.rnrlauncher.data.ColumnHeader;
import rnr.src.rnrlauncher.data.InfoText;
import rnr.src.rnrlauncher.data.LocalizedText;
import rnr.src.rnrlauncher.data.Resolution;
import rnr.src.rnrlauncher.data.SystemInfoDataRecord;
import rnr.src.rnrlauncher.widgets.DataTable;
import rnr.src.rnrlauncher.widgets.MainWindow;

//~--- JDK imports ------------------------------------------------------------

import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;

import java.awt.Color;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public final class Controller {
    private static final String FONT_NAME = "Tahoma";
    private static final String LOCALIZATION_NAMESPACE = "SYSTEM";
    private static final List<InfoText> textToDisplay = new ArrayList<InfoText>();
    private static final HashMap<Integer, ResolutionResources> resources = new HashMap<Integer, ResolutionResources>();
    private static final String ICONS_FOLDER = "..\\Data\\Menu\\Misc\\";
    private static final String LOGO_IMAGE_FILE = "..\\Data\\loc\\images\\rnr_launcher_logo.gif";
    private static final String WINDOW_ICON_IMAGE_FILE = "..\\Data\\Menu\\Misc\\rnr.png";
    private static String[] resolutionTextIndex = new String[5];
    private static final Color COLOR_CRASH = new Color(255, 170, 153, 255);
    private static final Color COLOR_BAD = new Color(230, 207, 161, 255);
    private static final Color COLOR_GOOD = new Color(161, 179, 168, 255);
    private static final String IMAGE_LIGHT_GREEN = "rnr_launcher_light_green.gif";
    private static final String IMAGE_LIGHT_YELLOW = "rnr_launcher_light_yellow.gif";
    private static final String IMAGE_LIGHT_RED = "rnr_launcher_light_red.gif";
    private static final String IMAGE_GRADIENT_GREEN = "rnr_launcher_back_green.gif";
    private static final String IMAGE_GRADIENT_YELLOW = "rnr_launcher_back_yellow.gif";
    private static final String IMAGE_GRADIENT_RED = "rnr_launcher_back_red.gif";
    private static ImageIcon LOGO_ICON;
    private static ImageIcon WINDOW_ICON;

    static void checkIcon(ImageIcon icon, String source) {
        if ((null != icon) && (8 == icon.getImageLoadStatus())) {
            return;
        }

        System.err.println("Warning: failed to load icon from: " + source);
    }

    /**
     * Method description
     *
     */
    public static void loadResourses() {
        try {
            UIManager.setLookAndFeel(new WindowsClassicLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.print("Warning: " + e.getMessage());
        }

        ImageIcon iconGood = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_light_green.gif");

        checkIcon(iconGood, "rnr_launcher_light_green.gif");

        ImageIcon iconBad = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_light_yellow.gif");

        checkIcon(iconBad, "rnr_launcher_light_yellow.gif");

        ImageIcon iconCrash = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_light_red.gif");

        checkIcon(iconCrash, "rnr_launcher_light_red.gif");

        ImageIcon gradientGood = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_back_green.gif");

        checkIcon(gradientGood, "rnr_launcher_back_green.gif");

        ImageIcon gradientBad = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_back_yellow.gif");

        checkIcon(gradientBad, "rnr_launcher_back_yellow.gif");

        ImageIcon gradientCrash = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_back_red.gif");

        checkIcon(gradientCrash, "rnr_launcher_back_red.gif");
        resources.put(Integer.valueOf(0),
                      new ResolutionResources(COLOR_CRASH, iconCrash, gradientCrash,
                          "LAUNCHER_COMPONENT_RESUME_CANT_RUN"));
        resources.put(Integer.valueOf(1),
                      new ResolutionResources(COLOR_CRASH, iconCrash, gradientCrash,
                          "LAUNCHER_COMPONENT_RESUME_UNDEFINED_COMPONENT"));
        resources.put(Integer.valueOf(2),
                      new ResolutionResources(COLOR_BAD, iconCrash, gradientCrash,
                          "LAUNCHER_COMPONENT_RESUME_WILL_RUN_WITH_ARTIFACTS"));
        resources.put(Integer.valueOf(3),
                      new ResolutionResources(COLOR_BAD, iconBad, gradientBad,
                          "LAUNCHER_COMPONENT_RESUME_FUNCTIONALITY_NOT_GOURANTED"));
        resources.put(Integer.valueOf(4),
                      new ResolutionResources(COLOR_GOOD, iconGood, gradientGood,
                          "LAUNCHER_COMPONENT_RESUME_MUST_RUN"));
        resolutionTextIndex[4] = "LAUNCHER_RESUME_MUST_RUN";
        resolutionTextIndex[3] = "LAUNCHER_RESUME_FUNCTIONALITY_NOT_GOURANTED";
        resolutionTextIndex[2] = "LAUNCHER_RESUME_WILL_RUN_WITH_ARTIFACTS";
        resolutionTextIndex[1] = "LAUNCHER_RESUME_UNDEFINED_COMPONENT";
        resolutionTextIndex[0] = "LAUNCHER_RESUME_CANT_RUN";
        LOGO_ICON = new ImageIcon("..\\Data\\loc\\images\\rnr_launcher_logo.gif");
        checkIcon(LOGO_ICON, "..\\Data\\loc\\images\\rnr_launcher_logo.gif");
        WINDOW_ICON = new ImageIcon("..\\Data\\Menu\\Misc\\rnr.png");
        checkIcon(WINDOW_ICON, "..\\Data\\Menu\\Misc\\rnr.png");
    }

    /**
     * Method description
     *
     */
    public static void prepare() {
        textToDisplay.clear();
    }

    /**
     * Method description
     *
     *
     * @param systemName
     * @param userSystem
     * @param requiredSystem
     * @param status
     */
    public static void consumeData(String systemName, String userSystem, String requiredSystem, int status) {
        System.err.println("Info: data came -> " + systemName + '|' + userSystem + '|' + requiredSystem + '|' + status);
        textToDisplay.add(new InfoText(userSystem, requiredSystem, null, status,
                                       eng.getStringRef("SYSTEM", systemName)));
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static boolean showLauncherWindow() {
        String componentNameString = eng.getStringRef("SYSTEM", "LAUNCHER_TABLE_COLUMN_COMPONENT");
        String userSystemInfoHeaderString = eng.getStringRef("SYSTEM", "LAUNCHER_TABLE_COLUMN_USER_WARE");
        String requiredSystemInfoHeaderString = eng.getStringRef("SYSTEM", "LAUNCHER_TABLE_COLUMN_MINIMAL_WARE");
        String analysisResultHeaderString = eng.getStringRef("SYSTEM", "LAUNCHER_TABLE_COLUMN_STATUS");
        ColumnHeader[] tableHeader = { new ColumnHeader(componentNameString, 0.3D),
                                       new ColumnHeader(userSystemInfoHeaderString, 0.15D),
                                       new ColumnHeader(requiredSystemInfoHeaderString, 0.25D),
                                       new ColumnHeader(analysisResultHeaderString, 0.3D) };
        int logoWidth = (0 < LOGO_ICON.getIconWidth())
                        ? LOGO_ICON.getIconWidth()
                        : 0;
        DataTable table = new DataTable(MainWindow.BACKGROUND_COLOR, "Tahoma", tableHeader,
                                        logoWidth + MainWindow.getBordersWidth());
        boolean unknownComponentExists = false;
        boolean undefinedComponentExists = false;
        boolean unsupportedComponentExists = false;
        boolean incompatibleComponentExists = false;

        for (InfoText infoText : textToDisplay) {
            SystemInfoDataRecord dataRecord = new SystemInfoDataRecord();
            ResolutionResources res = resources.get(Integer.valueOf(infoText.getStatus()));
            String resolutionText = eng.getStringRef("SYSTEM", res.getResolutionTextId());
            Resolution resulution = new Resolution(res.getImage(), res.getGradient(), resolutionText);

            switch (infoText.getStatus()) {
             case 0 :
                 incompatibleComponentExists = true;

                 break;

             case 3 :
                 unknownComponentExists = true;

                 break;

             case 2 :
                 unsupportedComponentExists = true;

                 break;

             case 1 :
                 undefinedComponentExists = true;

                 break;

             case 4 :
                 break;

             default :
                 System.err.println("Warning: unknown component status: " + infoText.getStatus());
            }

            dataRecord.putInfo(0, infoText.getName());
            dataRecord.putInfo(1, infoText.getUserSystemInfo());
            dataRecord.putInfo(2, infoText.getRequiredSystemInfo());
            dataRecord.putInfo(3, resulution);
            table.addInfoRecord(dataRecord);
        }

        LocalizedText text = new LocalizedText();
        String cancelButtonText = eng.getStringRef("SYSTEM", "LAUNCHER_BUTTON_CANCEL");
        String runAnywayButtonText = eng.getStringRef("SYSTEM", "LAUNCHER_BUTTON_RUN_ANYWAY");
        String headerText = eng.getStringRef("SYSTEM", "LAUNCHER_HEADER");
        String runButtonText = eng.getStringRef("SYSTEM", "LAUNCHER_BUTTON_OK");

        text.setCancelButtonText(cancelButtonText);
        text.setRunButtonText(runButtonText);
        text.setRunAnywayButtonText(runAnywayButtonText);
        text.setHeaderText(headerText);

        int status = 4;

        if (incompatibleComponentExists) {
            status = 0;
        } else if (undefinedComponentExists) {
            status = 1;
        } else if (unknownComponentExists) {
            status = 3;
        } else if (unsupportedComponentExists) {
            status = 2;
        }

        text.setFooterText(eng.getStringRef("SYSTEM", resolutionTextIndex[status]));
        table.constructGui();

        MainWindow window = new MainWindow(LOGO_ICON, WINDOW_ICON, table, "Tahoma", text, status);

        return window.show();
    }

    /**
     * Method description
     *
     *
     * @param rootPath
     */
    public static void redirectSystemErr(String rootPath) {
        try {
            System.setErr(new PrintStream(new FileOutputStream(rootPath + "warnings\\err.log", true)));
            System.err.println("INFO: redirected err from JVM");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Class description
     *
     *
     * @version        1.0, 13/08/26
     * @author         TJ    
     */
    public static final class ResolutionResources {
        private final Color color;
        private final ImageIcon image;
        private final ImageIcon gradient;
        private final String resolutionTextId;

        ResolutionResources(Color color, ImageIcon image, ImageIcon gradient, String resolutionTextId) {
            this.resolutionTextId = resolutionTextId;
            assert(null != color);
            assert(null != image);
            assert(null != gradient);
            this.color = color;
            this.image = image;
            this.gradient = gradient;
        }

        Color getColor() {
            return this.color;
        }

        ImageIcon getImage() {
            return this.image;
        }

        ImageIcon getGradient() {
            return this.gradient;
        }

        String getResolutionTextId() {
            return this.resolutionTextId;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
