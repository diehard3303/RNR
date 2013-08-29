/*
 * @(#)BalanceUpdater.java   13/08/28
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


package rnr.src.menu;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menuscript.Converts;
import rnr.src.rnrcore.EventsHolder;
import rnr.src.rnrcore.IEventListener;

//~--- JDK imports ------------------------------------------------------------

import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ
 */
public class BalanceUpdater implements IEventListener {
    static int m_iPlayerBalance = 0;
    static int m_iCompanyBalance = 0;
    private static BalanceUpdater singleton = null;
    Vector<balanceControl> controls = new Vector<balanceControl>();
    Vector<balanceControl> company_controls = new Vector<balanceControl>();

    private BalanceUpdater() {
        EventsHolder.addEventListenet(81, this);
    }

    private boolean remove(long control) {
        if (control == 0L) {
            return false;
        }

        for (int i = 0; i < this.controls.size(); ++i) {
            if (this.controls.elementAt(i).control == control) {
                this.controls.remove(i);

                return true;
            }
        }

        return false;
    }

    private boolean removeOffice(long control) {
        if (control == 0L) {
            return false;
        }

        for (int i = 0; i < this.company_controls.size(); ++i) {
            if (this.company_controls.elementAt(i).control == control) {
                this.company_controls.remove(i);

                return true;
            }
        }

        return false;
    }

    private boolean add(long control) {
        if (control == 0L) {
            return false;
        }

        for (int i = 0; i < this.controls.size(); ++i) {
            if (this.controls.elementAt(i).control == control) {
                return false;
            }
        }

        MENUText_field field = menues.ConvertTextFields(control);
        KeyPair[] keys = new KeyPair[2];

        keys[0] = new KeyPair("SIGN", (m_iPlayerBalance >= 0)
                                      ? ""
                                      : "-");
        keys[1] = new KeyPair("MONEY", "" + Converts.ConvertNumeric(Math.abs(m_iPlayerBalance)));

        balanceControl new_control = new balanceControl();

        new_control.text = field.text;
        new_control.control = control;
        field.text = MacroKit.Parse(field.text, keys);
        menues.UpdateField(field);
        this.controls.add(new_control);

        return true;
    }

    private boolean addOffice(long control) {
        if (control == 0L) {
            return false;
        }

        for (int i = 0; i < this.company_controls.size(); ++i) {
            if (this.company_controls.elementAt(i).control == control) {
                return false;
            }
        }

        MENUText_field field = menues.ConvertTextFields(control);
        KeyPair[] keys = new KeyPair[2];

        keys[0] = new KeyPair("SIGN", (m_iPlayerBalance >= 0)
                                      ? ""
                                      : "-");
        keys[1] = new KeyPair("MONEY", "" + Converts.ConvertNumeric(Math.abs(m_iPlayerBalance)));

        balanceControl new_control = new balanceControl();

        new_control.text = field.text;
        new_control.control = control;
        field.text = MacroKit.Parse(field.text, keys);
        menues.UpdateField(field);
        this.company_controls.add(new_control);

        return true;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    @Override
    public void on_event(int value) {
        for (int i = 0; i < this.controls.size(); ++i) {
            MENUText_field field = menues.ConvertTextFields(this.controls.elementAt(i).control);
            KeyPair[] keys = new KeyPair[2];

            keys[0] = new KeyPair("SIGN", (m_iPlayerBalance >= 0)
                                          ? ""
                                          : "-");
            keys[1] = new KeyPair("MONEY", "" + Converts.ConvertNumeric(Math.abs(m_iPlayerBalance)));
            field.text = MacroKit.Parse(this.controls.elementAt(i).text, keys);
            menues.UpdateField(field);
        }

        for (int i = 0; i < this.company_controls.size(); ++i) {
            MENUText_field field = menues.ConvertTextFields(this.company_controls.elementAt(i).control);
            KeyPair[] keys = new KeyPair[2];

            keys[0] = new KeyPair("SIGN", (m_iCompanyBalance >= 0)
                                          ? ""
                                          : "-");
            keys[1] = new KeyPair("MONEY", "" + Converts.ConvertNumeric(Math.abs(m_iCompanyBalance)));
            field.text = MacroKit.Parse(this.company_controls.elementAt(i).text, keys);
            menues.UpdateField(field);
        }
    }

    private int get() {
        return m_iPlayerBalance;
    }

    private static BalanceUpdater gHolder() {
        if (singleton == null) {
            singleton = new BalanceUpdater();
        }

        return singleton;
    }

    /**
     * Method description
     *
     *
     * @param control
     *
     * @return
     */
    public static boolean AddBalanceControl(long control) {
        return gHolder().add(control);
    }

    /**
     * Method description
     *
     *
     * @param control
     *
     * @return
     */
    public static boolean RemoveBalanceControl(long control) {
        return gHolder().remove(control);
    }

    /**
     * Method description
     *
     *
     * @param control
     *
     * @return
     */
    public static boolean AddCompanyBalanceControl(long control) {
        return gHolder().addOffice(control);
    }

    /**
     * Method description
     *
     *
     * @param control
     *
     * @return
     */
    public static boolean RemoveCompanyBalanceControl(long control) {
        return gHolder().removeOffice(control);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static int GetBalance() {
        return gHolder().get();
    }

    /**
     * Method description
     *
     *
     * @param v
     */
    public static void SetBalance(int v) {
        m_iPlayerBalance = v;
    }

    /**
     * Method description
     *
     *
     * @param v
     */
    public static void SetCompanyBalance(int v) {
        m_iCompanyBalance = v;
    }

    class balanceControl {
        long control;
        String text;

        balanceControl() {
            this.control = 0L;
            this.text = null;
        }
    }
}


//~ Formatted in DD Std on 13/08/28
