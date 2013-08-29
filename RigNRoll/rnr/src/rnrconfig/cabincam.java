/*
 * @(#)cabincam.java   13/08/26
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


package rnr.src.rnrconfig;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class cabincam {

    /**
     * Method description
     *
     *
     * @param cam
     */
    public void MACK_CH603cam(long cam) {
        Set_0_cam_Angles(cam, -0.0D, 14.0D, 0.0D);
        Set_course_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_tang_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_zoom_cam_inert(cam, 0.2D, 1.0D, 0.1D, 1.5D, 0.1D);
        Add_camera_cabin_point(cam, -0.492D, 1.374D, 2.5D, -89.0D, 89.0D, -89.0D, 89.0D);
        Add_camera_cabin_point(cam, 0.492D, 1.374D, 2.5D, -89.0D, 89.0D, -90.0D, 90.0D);
        Add_camera_cabin_point(cam, -0.0D, 0.255D, 2.5D, -89.0D, 89.0D, -90.0D, 90.0D);
    }

    /**
     * Method description
     *
     *
     * @param cam
     */
    public void PETERBILT_378cam(long cam) {
        Set_0_cam_Angles(cam, 0.0D, 4.5D, 0.0D);
        Set_course_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_tang_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_zoom_cam_inert(cam, 0.2D, 1.0D, 0.1D, 1.5D, 0.1D);
        Add_camera_cabin_point(cam, -0.414D, 1.137D, 2.377D, -70.000000999999997D, 50.000000999999997D,
                               -45.000000999999997D, 45.0D);
        Add_camera_cabin_point(cam, 0.414D, 1.137D, 2.377D, -90.000001999999995D, 60.000002000000002D,
                               -60.000002000000002D, 60.000002000000002D);
        Add_camera_cabin_point(cam, 0.0D, 0.025D, 2.377D, -140.00000199999999D, 140.0D, -89.000001999999995D,
                               89.000001999999995D);
    }

    /**
     * Method description
     *
     *
     * @param cam
     */
    public void PETERBILT_379cam(long cam) {
        Set_0_cam_Angles(cam, 0.0D, 4.5D, 0.0D);
        Set_course_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_tang_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_zoom_cam_inert(cam, 0.2D, 1.0D, 0.1D, 1.5D, 0.1D);
        Add_camera_cabin_point(cam, -0.423D, 1.53D, 2.4423D, -70.000000999999997D, 50.000000999999997D,
                               -45.000000999999997D, 45.0D);
        Add_camera_cabin_point(cam, 0.456D, 1.53D, 2.4423D, -90.000001999999995D, 60.000002000000002D,
                               -60.000002000000002D, 60.000002000000002D);
        Add_camera_cabin_point(cam, 0.0D, -0.14D, 2.4423D, -100.00000199999999D, 100.00000199999999D,
                               -89.000001999999995D, 89.000001999999995D);
    }

    /**
     * Method description
     *
     *
     * @param cam
     */
    public void PETERBILT_387cam(long cam) {
        Set_0_cam_Angles(cam, 0.3D, 5.0D, 0.0D);
        Set_course_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_tang_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_zoom_cam_inert(cam, 0.2D, 1.0D, 0.1D, 1.5D, 0.1D);
        Add_camera_cabin_point(cam, -0.6850000000000001D, 1.32D, 2.4323D, -70.000001999999995D, 50.000002000000002D,
                               -45.000002000000002D, 45.000002000000002D);
        Add_camera_cabin_point(cam, 0.456D, 1.32D, 2.4323D, -100.00000199999999D, 100.00000199999999D,
                               -89.000001999999995D, 89.000001999999995D);
        Add_camera_cabin_point(cam, 0.0D, -0.14D, 2.4323D, -100.00000199999999D, 100.00000199999999D,
                               -89.000001999999995D, 89.000001999999995D);
    }

    /**
     * Method description
     *
     *
     * @param cam
     */
    public void KENWORTH_T600Wcam(long cam) {
        Set_0_cam_Angles(cam, 0.0D, 10.0D, 0.0D);
        Set_course_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_tang_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_zoom_cam_inert(cam, 0.2D, 1.0D, 0.1D, 1.5D, 0.1D);
        Add_camera_cabin_point(cam, -0.425D, 1.5D, 2.663D, -70.000000999999997D, 50.000000999999997D,
                               -45.000000999999997D, 45.000000999999997D);
        Add_camera_cabin_point(cam, 0.485D, 1.5D, 2.663D, -100.00000199999999D, 100.00000199999999D,
                               -89.000001999999995D, 89.000001999999995D);
        Add_camera_cabin_point(cam, 0.0D, -0.1D, 2.5723D, -100.0D, 100.0D, -89.0D, 89.0D);
    }

    /**
     * Method description
     *
     *
     * @param cam
     */
    public void KENWORTH_T800cam(long cam) {
        Set_0_cam_Angles(cam, -3.0D, 7.0D, 0.0D);
        Set_course_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_tang_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_zoom_cam_inert(cam, 0.2D, 1.0D, 0.1D, 1.5D, 0.1D);
        Add_camera_cabin_point(cam, -0.407D, 1.65D, 2.584D, -60.0D, 60.0D, -45.0D, 45.0D);
        Add_camera_cabin_point(cam, 0.407D, 1.65D, 2.584D, -100.0D, 100.0D, -89.0D, 89.0D);
        Add_camera_cabin_point(cam, 0.0D, 0.01D, 2.584D, -100.0D, 100.0D, -120.0D, 120.0D);
    }

    /**
     * Method description
     *
     *
     * @param cam
     */
    public void KENWORTH_W900Lcam(long cam) {
        Set_0_cam_Angles(cam, 0.0D, 8.5D, 0.0D);
        Set_course_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_tang_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_zoom_cam_inert(cam, 0.2D, 1.0D, 0.1D, 1.5D, 0.1D);
        Add_camera_cabin_point(cam, -0.495D, 0.9D, 2.4723D, -50.0D, 30.0D, -45.0D, 45.0D);
        Add_camera_cabin_point(cam, 0.5D, 1.5D, 2.47233D, -100.0D, 100.0D, -89.0D, 89.0D);
        Add_camera_cabin_point(cam, 0.0D, -0.5D, 2.4723D, -100.0D, 100.0D, -89.0D, 89.0D);
    }

    /**
     * Method description
     *
     *
     * @param cam
     */
    public void KENWORTH_T2000cam(long cam) {
        Set_0_cam_Angles(cam, -3.0D, 3.0D, 0.0D);
        Set_course_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_tang_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_zoom_cam_inert(cam, 0.2D, 1.0D, 0.1D, 1.5D, 0.1D);
        Add_camera_cabin_point(cam, -0.707D, 1.3D, 2.5223D, -60.0D, 60.0D, -45.0D, 45.0D);
        Add_camera_cabin_point(cam, 0.655D, 1.3D, 2.5223D, -100.0D, 100.0D, -89.0D, 89.0D);
        Add_camera_cabin_point(cam, 0.0D, -0.2D, 2.52233D, -100.0D, 100.0D, -89.0D, 89.0D);
    }

    /**
     * Method description
     *
     *
     * @param cam
     */
    public void STERLING_9500cam(long cam) {
        Set_0_cam_Angles(cam, -3.0D, 3.0D, 0.0D);
        Set_course_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_tang_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_zoom_cam_inert(cam, 0.2D, 1.0D, 0.1D, 1.5D, 0.1D);
        Add_camera_cabin_point(cam, -0.461D, 1.275D, 2.563D, -89.0D, 89.0D, -89.0D, 89.0D);
        Add_camera_cabin_point(cam, 0.448D, 1.275D, 2.563D, -89.0D, 89.0D, -89.0D, 89.0D);
        Add_camera_cabin_point(cam, 0.0D, -0.206D, 2.8D, -89.0D, 89.0D, -89.0D, 89.0D);
    }

    /**
     * Method description
     *
     *
     * @param cam
     */
    public void FREIGHTLINER_CLASSIC_XLcam(long cam) {
        Set_0_cam_Angles(cam, -3.0D, 12.0D, 0.0D);
        Set_course_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_tang_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_zoom_cam_inert(cam, 0.2D, 1.0D, 0.1D, 1.5D, 0.1D);
        Add_camera_cabin_point(cam, -0.44D, 1.6D, 2.642D, -60.0D, 60.0D, -45.0D, 45.0D);
        Add_camera_cabin_point(cam, 0.44D, 1.6D, 2.642D, -100.0D, 100.0D, -89.0D, 89.0D);
        Add_camera_cabin_point(cam, 0.0D, 0.573D, 2.642D, -100.0D, 100.0D, -89.0D, 89.0D);
    }

    /**
     * Method description
     *
     *
     * @param cam
     */
    public void FREIGHTLINER_CORONADOcam(long cam) {
        Set_0_cam_Angles(cam, -3.0D, 11.0D, 0.0D);
        Set_course_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_tang_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_zoom_cam_inert(cam, 0.2D, 1.0D, 0.1D, 1.5D, 0.1D);
        Add_camera_cabin_point(cam, -0.485D, 0.9D, 2.489D, -60.0D, 60.0D, -45.0D, 45.0D);
        Add_camera_cabin_point(cam, 0.485D, 0.9D, 2.489D, -100.0D, 100.0D, -89.0D, 89.0D);
        Add_camera_cabin_point(cam, 0.0D, -0.1D, 2.489D, -100.0D, 100.0D, -89.0D, 89.0D);
    }

    /**
     * Method description
     *
     *
     * @param cam
     */
    public void FREIGHTLINER_CENTURYcam(long cam) {
        Set_0_cam_Angles(cam, -3.0D, 11.0D, 0.0D);
        Set_course_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_tang_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_zoom_cam_inert(cam, 0.2D, 1.0D, 0.1D, 1.5D, 0.1D);
        Add_camera_cabin_point(cam, -0.485D, 0.9D, 2.4893D, -60.0D, 60.0D, -45.0D, 45.0D);
        Add_camera_cabin_point(cam, 0.485D, 0.9D, 2.489D, -100.0D, 100.0D, -89.0D, 89.0D);
        Add_camera_cabin_point(cam, 0.0D, 0.9D, 2.489D, -100.0D, 100.0D, -89.0D, 89.0D);
    }

    /**
     * Method description
     *
     *
     * @param cam
     */
    public void FREIGHTLINER_ARGOSYcam(long cam) {
        Set_0_cam_Angles(cam, -3.0D, 15.0D, 0.0D);
        Set_course_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_tang_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_zoom_cam_inert(cam, 0.2D, 1.0D, 0.1D, 1.5D, 0.1D);
        Add_camera_cabin_point(cam, -0.674D, 2.85D, 2.589D, -70.0D, 70.0D, -45.0D, 45.0D);
        Add_camera_cabin_point(cam, 0.667D, 2.85D, 2.589D, -100.0D, 100.0D, -89.0D, 89.0D);
        Add_camera_cabin_point(cam, 0.0D, 1.3D, 2.589D, -100.0D, 100.0D, -89.0D, 89.0D);
    }

    /**
     * Method description
     *
     *
     * @param cam
     */
    public void GEPARDcam(long cam) {
        Set_0_cam_Angles(cam, -3.0D, 5.0D, 0.0D);
        Set_course_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_tang_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_zoom_cam_inert(cam, 0.2D, 1.0D, 0.1D, 1.5D, 0.1D);
        Add_camera_cabin_point(cam, -0.537D, 1.0D, 2.6D, -100.0D, 100.0D, -90.0D, 90.0D);
        Add_camera_cabin_point(cam, 0.537D, 1.0D, 2.6D, -100.0D, 100.0D, -89.0D, 89.0D);
        Add_camera_cabin_point(cam, 0.0D, 0.646D, 2.6D, -100.0D, 100.0D, -89.0D, 89.0D);
    }

    /**
     * Method description
     *
     *
     * @param cam
     */
    public void WESTERN_STAR4900_EXcam(long cam) {
        Set_0_cam_Angles(cam, -3.0D, 11.0D, 0.0D);
        Set_course_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_tang_cam_inert(cam, 0.1D, 0.5D, 0.5D, 3.0D, 0.1D);
        Set_zoom_cam_inert(cam, 0.2D, 1.0D, 0.1D, 1.5D, 0.1D);
        Add_camera_cabin_point(cam, -0.5530000000000001D, 0.33D, 2.55D, -100.0D, 100.0D, -90.0D, 90.0D);
        Add_camera_cabin_point(cam, 0.5530000000000001D, 0.33D, 2.55D, -100.0D, 100.0D, -89.0D, 89.0D);
        Add_camera_cabin_point(cam, 0.0D, -1.252D, 2.55D, -130.0D, 130.0D, -60.0D, 70.0D);
    }

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramDouble1
     * @param paramDouble2
     * @param paramDouble3
     */
    public static native void Set_0_cam_Angles(long paramLong, double paramDouble1, double paramDouble2,
            double paramDouble3);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramDouble1
     * @param paramDouble2
     * @param paramDouble3
     * @param paramDouble4
     * @param paramDouble5
     */
    public static native void Set_course_cam_inert(long paramLong, double paramDouble1, double paramDouble2,
            double paramDouble3, double paramDouble4, double paramDouble5);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramDouble1
     * @param paramDouble2
     * @param paramDouble3
     * @param paramDouble4
     * @param paramDouble5
     */
    public static native void Set_tang_cam_inert(long paramLong, double paramDouble1, double paramDouble2,
            double paramDouble3, double paramDouble4, double paramDouble5);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramDouble1
     * @param paramDouble2
     * @param paramDouble3
     * @param paramDouble4
     * @param paramDouble5
     */
    public static native void Set_zoom_cam_inert(long paramLong, double paramDouble1, double paramDouble2,
            double paramDouble3, double paramDouble4, double paramDouble5);

    /**
     * Method description
     *
     *
     * @param paramLong
     * @param paramDouble1
     * @param paramDouble2
     * @param paramDouble3
     * @param paramDouble4
     * @param paramDouble5
     * @param paramDouble6
     * @param paramDouble7
     */
    public static native void Add_camera_cabin_point(long paramLong, double paramDouble1, double paramDouble2,
            double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7);
}


//~ Formatted in DD Std on 13/08/26
