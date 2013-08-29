/*
 * @(#)Helper.java   13/08/25
 * 
 * Copyright (c) 2013 DieHard Development
 *
 * All rights reserved.
   Released under the BSD 3 clause license
Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this 
    list of conditions and the following disclaimer. Redistributions in binary 
    form must reproduce the above copyright notice, this list of conditions and 
    the following disclaimer in the documentation and/or other materials 
    provided with the distribution. Neither the name of the DieHard Development 
    nor the names of its contributors may be used to endorse or promote products 
    derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR 
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 
 *
 *
 *
 */


package rnr.src.menu;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ
 */
public class Helper {
    private static Stack<Integer> animation_ids = null;
    private static int dimention_min = 0;
    private static int dimention_max = 2;
    private static int animation_hide_show_controls = -1;
    private static final ArrayList<ControlShow> toShowHide = new ArrayList<ControlShow>();
    private static final String METH_ANIMATE_HIDESHOW = "onHideShow";

    private static int numVisibleNodes_children(Cmenu_TTI menu) {
        if ((null == menu) || (!(menu.toshow))) {
            return 0;
        }

        int ch_show = 0;

        if (menu.showCH) {
            Iterator<?> iter = menu.children.iterator();

            while (iter.hasNext()) {
                ch_show += numVisibleNodes_children((Cmenu_TTI) iter.next());
            }
        }

        return (1 + ch_show);
    }

    /**
     * Method description
     *
     *
     * @param menu
     *
     * @return
     */
    public static int numVisibleNodes(Cmenu_TTI menu) {
        if ((null == menu) || (!(menu.toshow))) {
            return 0;
        }

        int ch_show = 0;
        Iterator<?> iter = menu.children.iterator();

        while (iter.hasNext()) {
            ch_show += numVisibleNodes_children((Cmenu_TTI) iter.next());
        }

        return ch_show;
    }

    private static boolean setNumVisibleNodeOnTop_children(Cmenu_TTI menu, int depth) {
        if ((null == menu) || (!(menu.toshow))) {
            return false;
        }

        if (depth == 0) {
            menu.ontop = true;

            return true;
        }

        menu.ontop = false;

        if (menu.showCH) {
            Iterator<?> iter = menu.children.iterator();

            while (iter.hasNext()) {
                if (setNumVisibleNodeOnTop_children((Cmenu_TTI) iter.next(), --depth)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @param menu
     * @param depth
     */
    public static void setNumVisibleNodeOnTop(Cmenu_TTI menu, int depth) {
        if ((null == menu) || (!(menu.toshow))) {
            return;
        }

        Iterator<?> iter = menu.children.iterator();

        while (iter.hasNext()) {
            if (setNumVisibleNodeOnTop_children((Cmenu_TTI) iter.next(), --depth)) {
                return;
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param menu
     * @param current_depth
     *
     * @return
     */
    public static boolean tell0Line_children(Cmenu_TTI menu, Integer current_depth) {
        if ((null == menu) || (!(menu.toshow))) {
            return false;
        }

        if (menu.ontop) {
            return true;
        }

        if (menu.showCH) {
            Iterator<?> iter = menu.children.iterator();

            while (iter.hasNext()) {
                current_depth = new Integer(current_depth.intValue() + 1);

                if (tell0Line_children((Cmenu_TTI) iter.next(), current_depth)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @param menu
     *
     * @return
     */
    public static int tell0Line(Cmenu_TTI menu) {
        if ((null == menu) || (!(menu.toshow))) {
            return 0;
        }

        Integer i = new Integer(0);
        Iterator<?> iter = menu.children.iterator();

        while (iter.hasNext()) {
            if (tell0Line_children((Cmenu_TTI) iter.next(), i)) {
                return i.intValue();
            }

            i = new Integer(i.intValue() + 1);
        }

        return 0;
    }

    /**
     * Method description
     *
     *
     * @param menu
     * @param item
     * @param current_depth
     *
     * @return
     */
    public static boolean tellItemLine_children(Cmenu_TTI menu, Cmenu_TTI item, Integer current_depth) {
        if ((null == menu) || (!(menu.toshow))) {
            return false;
        }

        if (menu.equals(item)) {
            return true;
        }

        if (menu.showCH) {
            Iterator<?> iter = menu.children.iterator();

            while (iter.hasNext()) {
                current_depth = new Integer(current_depth.intValue() + 1);

                if (tellItemLine_children((Cmenu_TTI) iter.next(), item, current_depth)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Method description
     *
     *
     * @param root
     * @param item
     *
     * @return
     */
    public static int tellItemLine(Cmenu_TTI root, Cmenu_TTI item) {
        Integer i = new Integer(0);
        Iterator<?> iter = root.children.iterator();

        while (iter.hasNext()) {
            if (tellItemLine_children((Cmenu_TTI) iter.next(), item, i)) {
                return i.intValue();
            }

            i = new Integer(i.intValue() + 1);
        }

        return i.intValue();
    }

    /**
     * Method description
     *
     *
     * @param root
     * @param item
     *
     * @return
     */
    public static int tellLine(Cmenu_TTI root, Cmenu_TTI item) {
        return (tellItemLine(root, item) - tell0Line(root));
    }

    /**
     * Method description
     *
     *
     * @param root
     * @param obj
     *
     * @return
     */
    public static Cmenu_TTI findInTree(Cmenu_TTI root, Object obj) {
        if ((root == null) || (obj == null)) {
            return null;
        }

        if ((root.item != null) && (root.item.equals(obj))) {
            return root;
        }

        Iterator<?> iter = root.children.iterator();

        while (iter.hasNext()) {
            Cmenu_TTI next_one = (Cmenu_TTI) iter.next();
            Cmenu_TTI res = findInTree(next_one, obj);

            if (res != null) {
                return res;
            }
        }

        return null;
    }

    /**
     * Method description
     *
     *
     * @param root
     * @param visitor
     */
    public static void traverseTree(Cmenu_TTI root, ITableNodeVisitor visitor) {
        if ((root == null) || (visitor == null)) {
            return;
        }

        visitor.visitNode(root);

        Iterator<?> iter = root.children.iterator();

        while (iter.hasNext()) {
            traverseTree((Cmenu_TTI) iter.next(), visitor);
        }
    }

    private static boolean traverseTree_if(Cmenu_TTI root, Cmenu_TTI begin, Cmenu_TTI end, ITableNodeVisitor visitor,
            BoolProxy started) {
        if ((!(started.get())) && (root.equals(begin))) {
            started.set(true);
        }

        if (started.get()) {
            visitor.visitNode(root);
        }

        if (root.equals(end)) {
            return false;
        }

        Iterator<?> iter = root.children.iterator();

        while (iter.hasNext()) {
            if (!(traverseTree_if((Cmenu_TTI) iter.next(), begin, end, visitor, started))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Method description
     *
     *
     * @param root
     * @param begin
     * @param end
     * @param visitor
     */
    public static void traverseTree(Cmenu_TTI root, Cmenu_TTI begin, Cmenu_TTI end, ITableNodeVisitor visitor) {
        if ((root == null) || (begin == null) || (visitor == null)) {
            return;
        }

        traverseTree_if(root, begin, end, visitor, new BoolProxy(false));
    }

    /**
     * Method description
     *
     *
     * @param summ
     *
     * @return
     */
    public static String convertMoney(int summ) {
        if (summ == 0) {
            return "0";
        }

        if (summ < 0) {
            summ *= -1;
        }

        int count_spasec = (int) Math.floor(Math.floor(Math.log10(summ)) / 3.0D);
        String res = "";

        if (count_spasec == 0) {
            res = "" + summ;

            return res;
        }

        boolean first_time = true;

        while (count_spasec >= 0) {
            double rem = summ / 1000.0D - Math.floor(summ / 1000.0D);

            rem *= 1000.0D;

            long irem = Math.round(rem);

            if (irem == 0L) {
                irem = 1000L;
            }

            String medres = "";

            if (irem >= 1000L) {
                if (count_spasec == 0) {
                    medres = (first_time)
                             ? "0"
                             : res;
                } else {
                    medres = "000 " + res;
                }
            } else if (irem >= 100L) {
                medres = irem + " " + res;
            } else if ((irem < 100L) && (irem >= 10L)) {
                if (count_spasec == 0) {
                    medres = irem + " " + res;
                } else {
                    medres = "0" + irem + " " + res;
                }
            } else if (irem < 10L) {
                if (count_spasec == 0) {
                    medres = irem + " " + res;
                } else {
                    medres = "00" + irem + " " + res;
                }
            }

            res = medres;
            first_time = false;
            summ = (int) Math.floor(summ / 1000.0D);
            --count_spasec;
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public static final int getUniqueAnimationID() {
        if (animation_ids == null) {
            animation_ids = new Stack<Integer>();

            for (int i = 1 << dimention_min; i < 1 << dimention_max; ++i) {
                animation_ids.push(Integer.valueOf(i));
            }
        } else if (animation_ids.isEmpty()) {
            dimention_min = dimention_max;
            dimention_max += 2;

            for (int i = 1 << dimention_min; i < 1 << dimention_max; ++i) {
                animation_ids.push(Integer.valueOf(i));
            }
        }

        return animation_ids.pop().intValue();
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public static final void returnUniqueAnimationID(int value) {
        for (Iterator<Integer> i$ = animation_ids.iterator(); i$.hasNext(); ) {
            int i = i$.next().intValue();

            if (i == value) {
                return;
            }
        }

        animation_ids.push(Integer.valueOf(value));
    }

    /**
     * Method description
     *
     *
     * @param control
     * @param show
     */
    public static void setControlShow(long control, boolean show) {
        toShowHide.add(new ControlShow(control, show));

        if (-1 == animation_hide_show_controls) {
            animation_hide_show_controls = getUniqueAnimationID();
            menues.SetScriptObjectAnimation(0L, animation_hide_show_controls, new Helper(), "onHideShow");
        }
    }

    /**
     * Method description
     *
     *
     * @param control
     * @param time
     */
    public void onHideShow(long control, double time) {
        for (ControlShow item : toShowHide) {
            menues.SetShowField(item.control, item.show);
        }

        toShowHide.clear();
        menues.StopScriptAnimation(animation_hide_show_controls);
        animation_hide_show_controls = -1;
    }

    /**
     * @return the methAnimateHideshow
     */
    public static String getMethAnimateHideshow() {
        return METH_ANIMATE_HIDESHOW;
    }

    private static class BoolProxy {
        private boolean val;

        BoolProxy(boolean value) {
            set(value);
        }

        void set(boolean value) {
            this.val = value;
        }

        boolean get() {
            return this.val;
        }
    }


    static class ControlShow {
        long control;
        boolean show;

        ControlShow(long control, boolean show) {
            this.control = control;
            this.show = show;
        }
    }
}


//~ Formatted in DD Std on 13/08/25
