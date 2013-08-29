/*
 * @(#)LauncherTest.java   13/08/26
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

import rnr.src.rnrlauncher.data.ColumnHeader;
import rnr.src.rnrlauncher.data.LocalizedText;
import rnr.src.rnrlauncher.data.Resolution;
import rnr.src.rnrlauncher.data.SystemInfoDataRecord;
import rnr.src.rnrlauncher.widgets.DataTable;
import rnr.src.rnrlauncher.widgets.MainWindow;

//~--- JDK imports ------------------------------------------------------------

import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;

import java.io.PrintStream;

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
public final class LauncherTest {
    private static final String FONT_NAME = "Tahoma";
    private static final String ICONS_FOLDER = "..\\Data\\Menu\\Misc\\";

    /**
     * Method description
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new WindowsClassicLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
        }

        ColumnHeader[] tableHeader = { new ColumnHeader(" ÓÏÔÓÌÂÌÚ‡", 0.3D), new ColumnHeader("¬‡¯ ÍÓÏÔ¸˛ÚÂ", 0.15D),
                                       new ColumnHeader("ÃËÌËÏ‡Î¸Ì˚Â ÚÂ·Ó‚‡ÌËˇ", 0.25D),
                                       new ColumnHeader("–ÂÁÛÎ¸Ú‡Ú ‡Ì‡ÎËÁ‡", 0.3D) };
        ImageIcon logoIcon = new ImageIcon("..\\Data\\loc\\images\\rnr_launcher_logo.gif");
        DataTable table = new DataTable(MainWindow.BACKGROUND_COLOR, "Tahoma", tableHeader,
                                        logoIcon.getIconWidth() + MainWindow.getBordersWidth());
        ImageIcon iconGood = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_light_green.gif");
        ImageIcon iconBad = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_light_yellow.gif");
        ImageIcon iconCrash = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_light_red.gif");
        ImageIcon gradientGood = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_back_green.gif");
        ImageIcon gradientBad = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_back_yellow.gif");
        ImageIcon gradientCrash = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_back_red.gif");
        Object res1 = new Resolution(iconGood, gradientGood, "Ok");
        Object res2 = new Resolution(iconCrash, gradientCrash, "Will crash! You PC SUCKS!!!! Go spend money");
        Object res3 = new Resolution(iconBad, gradientBad, "God only knows");
        SystemInfoDataRecord osDataRecord = new SystemInfoDataRecord();

        osDataRecord.putInfo(0, "Operating System");
        osDataRecord.putInfo(
            1, "Windows Vista, Windows Vista, Windows Vista, Windows Vista, Windows Vista, Windows Vista, Windows Vista, Windows Vista, Windows Vista, ");
        osDataRecord.putInfo(2, "Windows XP, Windows 2000, Windows Vista");
        osDataRecord.putInfo(3, res1);

        SystemInfoDataRecord videoCardDataRecord = new SystemInfoDataRecord();

        videoCardDataRecord.putInfo(0, "Videocard");
        videoCardDataRecord.putInfo(1, "GeForce 8800 GTX ULTA");
        videoCardDataRecord.putInfo(2, "Voodoo 3, Voodoo 4, Voodoo 5");
        videoCardDataRecord.putInfo(3, res2);

        SystemInfoDataRecord videoMemoryDataRecord = new SystemInfoDataRecord();

        videoMemoryDataRecord.putInfo(0, "Video memory");
        videoMemoryDataRecord.putInfo(1, "128 Mb");
        videoMemoryDataRecord.putInfo(2, "2048 Mb");
        videoMemoryDataRecord.putInfo(3, res3);

        SystemInfoDataRecord directxVersionDataRecord = new SystemInfoDataRecord();

        directxVersionDataRecord.putInfo(0, "DirectX version");
        directxVersionDataRecord.putInfo(1, "8.0");
        directxVersionDataRecord.putInfo(2, "9.0c");
        directxVersionDataRecord.putInfo(3, res1);

        SystemInfoDataRecord audioCardDataRecord = new SystemInfoDataRecord();

        audioCardDataRecord.putInfo(0, "Audio Card");
        audioCardDataRecord.putInfo(1, "???");
        audioCardDataRecord.putInfo(2, "Sound Blaster");
        audioCardDataRecord.putInfo(3, res3);

        SystemInfoDataRecord memoryDataRecord = new SystemInfoDataRecord();

        memoryDataRecord.putInfo(0, "System RAM");
        memoryDataRecord.putInfo(1, "1024");
        memoryDataRecord.putInfo(
            2, "GeForce1, GeForce2, GeForce3, GeForce4, GeForce5, GeForce6, GeForce7, GeForce8, GeForce9");
        memoryDataRecord.putInfo(3, res2);

        SystemInfoDataRecord pagefileDataRecord = new SystemInfoDataRecord();

        pagefileDataRecord.putInfo(0, "Page file");
        pagefileDataRecord.putInfo(1, "1024");
        pagefileDataRecord.putInfo(2, "100 “≈––¿ √≈ “¿–¿!");
        pagefileDataRecord.putInfo(3, res2);

        SystemInfoDataRecord cpuDataRecord = new SystemInfoDataRecord();

        cpuDataRecord.putInfo(0, "CPU");
        cpuDataRecord.putInfo(1, "486");
        cpuDataRecord.putInfo(2, "œ≈Õ“»”Ã 100!");
        cpuDataRecord.putInfo(3, res2);
        table.addInfoRecord(osDataRecord);
        table.addInfoRecord(videoCardDataRecord);
        table.addInfoRecord(videoMemoryDataRecord);
        table.addInfoRecord(directxVersionDataRecord);
        table.addInfoRecord(audioCardDataRecord);
        table.addInfoRecord(memoryDataRecord);
        table.addInfoRecord(pagefileDataRecord);
        table.addInfoRecord(cpuDataRecord);

        LocalizedText text = new LocalizedText();

        text.setCancelButtonText("Exit");
        text.setRunButtonText("Run game");
        text.setRunAnywayButtonText("Run game anyway");
        text.setFooterText(
            "QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH ");
        text.setHeaderText("¿Õ¿À»«  ŒÕ‘»√”–¿÷»»  ŒÃœ‹ﬁ“≈–¿");
        table.constructGui();

        MainWindow window = new MainWindow(logoIcon, new ImageIcon("..\\Data\\Menu\\Misc\\rnr.png"), table, "Tahoma",
                                           text, 2);

        window.show();
    }
}


//~ Formatted in DD Std on 13/08/26
