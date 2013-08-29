/*
 * @(#)matrixJ.java   13/08/26
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


package rnr.src.rnrcore;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class matrixJ implements Serializable {
    static final long serialVersionUID = 0L;

    /** Field description */
    public vectorJ v0;

    /** Field description */
    public vectorJ v1;

    /** Field description */
    public vectorJ v2;

    /**
     * Constructs ...
     *
     */
    public matrixJ() {
        this.v0 = new vectorJ(1.0D, 0.0D, 0.0D);
        this.v1 = new vectorJ(0.0D, 1.0D, 0.0D);
        this.v2 = new vectorJ(0.0D, 0.0D, 1.0D);
    }

    /**
     * Constructs ...
     *
     *
     * @param m
     */
    public matrixJ(matrixJ m) {
        this.v0 = new vectorJ(m.v0);
        this.v1 = new vectorJ(m.v1);
        this.v2 = new vectorJ(m.v2);
    }

    /**
     * Constructs ...
     *
     *
     * @param dir
     * @param nz
     */
    public matrixJ(vectorJ dir, vectorJ nz) {
        this.v2 = new vectorJ(nz);
        this.v1 = new vectorJ(dir);
        this.v0 = dir.oCross(nz);
    }

    /**
     * Method description
     *
     *
     * @param _x
     * @param _y
     * @param _z
     */
    public void Set0(double _x, double _y, double _z) {
        this.v0.x = _x;
        this.v0.y = _y;
        this.v0.z = _z;
    }

    /**
     * Method description
     *
     *
     * @param _x
     * @param _y
     * @param _z
     */
    public void Set1(double _x, double _y, double _z) {
        this.v1.x = _x;
        this.v1.y = _y;
        this.v1.z = _z;
    }

    /**
     * Method description
     *
     *
     * @param _x
     * @param _y
     * @param _z
     */
    public void Set2(double _x, double _y, double _z) {
        this.v2.x = _x;
        this.v2.y = _y;
        this.v2.z = _z;
    }

    /**
     * Method description
     *
     *
     * @param angle
     *
     * @return
     */
    public static matrixJ Mz(double angle) {
        matrixJ res = new matrixJ();
        double c = Math.cos(angle);
        double s = Math.sin(angle);

        res.Set0(c, s, 0.0D);
        res.Set1(-s, c, 0.0D);

        return res;
    }

    /**
     * Method description
     *
     *
     * @param angle
     *
     * @return
     */
    public static matrixJ Mx(double angle) {
        matrixJ res = new matrixJ();
        double c = Math.cos(angle);
        double s = Math.sin(angle);

        res.Set1(0.0D, c, s);
        res.Set2(0.0D, -s, c);

        return res;
    }

    /**
     * Method description
     *
     *
     * @param angle
     *
     * @return
     */
    public static matrixJ My(double angle) {
        matrixJ res = new matrixJ();
        double c = Math.cos(angle);
        double s = Math.sin(angle);

        res.Set0(c, 0.0D, -s);
        res.Set2(s, 0.0D, c);

        return res;
    }

    /**
     * Method description
     *
     *
     * @param M
     */
    public void mult(matrixJ M) {
        vectorJ Mc0 = new vectorJ(M.v0.x, M.v1.x, M.v2.x);
        vectorJ Mc1 = new vectorJ(M.v0.y, M.v1.y, M.v2.y);
        vectorJ Mc2 = new vectorJ(M.v0.z, M.v1.z, M.v2.z);

        Set0(this.v0.dot(Mc0), this.v0.dot(Mc1), this.v0.dot(Mc2));
        Set1(this.v1.dot(Mc0), this.v1.dot(Mc1), this.v1.dot(Mc2));
        Set2(this.v2.dot(Mc0), this.v2.dot(Mc1), this.v2.dot(Mc2));
    }

    /**
     * Method description
     *
     *
     * @param Mleft
     * @param M
     *
     * @return
     */
    public static matrixJ mult(matrixJ Mleft, matrixJ M) {
        matrixJ res = new matrixJ(Mleft);

        res.mult(M);

        return res;
    }

    /**
     * Method description
     *
     *
     * @param M
     * @param v
     *
     * @return
     */
    public static vectorJ mult(matrixJ M, vectorJ v) {
        vectorJ Mc0 = new vectorJ(M.v0.x, M.v1.x, M.v2.x);
        vectorJ Mc1 = new vectorJ(M.v0.y, M.v1.y, M.v2.y);
        vectorJ Mc2 = new vectorJ(M.v0.z, M.v1.z, M.v2.z);

        return new vectorJ(Mc0.dot(v), Mc1.dot(v), Mc2.dot(v));
    }
}


//~ Formatted in DD Std on 13/08/26
