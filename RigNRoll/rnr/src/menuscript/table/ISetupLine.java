/*
 * @(#)ISetupLine.java   13/08/26
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


package rnr.src.menuscript.table;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.Cmenu_TTI;
import rnr.src.menu.MENUEditBox;
import rnr.src.menu.MENUText_field;
import rnr.src.menu.MENUTruckview;
import rnr.src.menu.MENU_ranger;
import rnr.src.menu.MENUbutton_field;
import rnr.src.menu.MENUsimplebutton_field;

/**
 * Interface description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public abstract interface ISetupLine {

    /** Field description */
    public static final String METHOD = "SetupLineInTable";

    /**
     * Method description
     *
     *
     * @param paramMENUbutton_field
     * @param paramCmenu_TTI
     */
    public abstract void SetupLineInTable(MENUbutton_field paramMENUbutton_field, Cmenu_TTI paramCmenu_TTI);

    /**
     * Method description
     *
     *
     * @param paramMENUsimplebutton_field
     * @param paramCmenu_TTI
     */
    public abstract void SetupLineInTable(MENUsimplebutton_field paramMENUsimplebutton_field, Cmenu_TTI paramCmenu_TTI);

    /**
     * Method description
     *
     *
     * @param paramMENUText_field
     * @param paramCmenu_TTI
     */
    public abstract void SetupLineInTable(MENUText_field paramMENUText_field, Cmenu_TTI paramCmenu_TTI);

    /**
     * Method description
     *
     *
     * @param paramMENUTruckview
     * @param paramCmenu_TTI
     */
    public abstract void SetupLineInTable(MENUTruckview paramMENUTruckview, Cmenu_TTI paramCmenu_TTI);

    /**
     * Method description
     *
     *
     * @param paramMENUEditBox
     * @param paramCmenu_TTI
     */
    public abstract void SetupLineInTable(MENUEditBox paramMENUEditBox, Cmenu_TTI paramCmenu_TTI);

    /**
     * Method description
     *
     *
     * @param paramMENU_ranger
     * @param paramCmenu_TTI
     */
    public abstract void SetupLineInTable(MENU_ranger paramMENU_ranger, Cmenu_TTI paramCmenu_TTI);
}


//~ Formatted in DD Std on 13/08/26
