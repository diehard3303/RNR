/*
 * @(#)vectorJ.java   13/08/26
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

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class vectorJ {

    /** Field description */
    public double x;

    /** Field description */
    public double y;

    /** Field description */
    public double z;

    /**
     * Constructs ...
     *
     */
    public vectorJ() {
        this.x = 0.0D;
        this.y = 0.0D;
        this.z = 0.0D;
    }

    /**
     * Constructs ...
     *
     *
     * @param _copy
     */
    public vectorJ(vectorJ _copy) {
        this.x = _copy.x;
        this.y = _copy.y;
        this.z = _copy.z;
    }

    /**
     * Constructs ...
     *
     *
     * @param _x
     * @param _y
     * @param _z
     */
    public vectorJ(double _x, double _y, double _z) {
        this.x = _x;
        this.y = _y;
        this.z = _z;
    }

    /**
     * Method description
     *
     *
     * @param _x
     * @param _y
     * @param _z
     */
    public void Set(double _x, double _y, double _z) {
        this.x = _x;
        this.y = _y;
        this.z = _z;
    }

    /**
     * Method description
     *
     *
     * @param value
     */
    public void Set(vectorJ value) {
        this.x = value.x;
        this.y = value.y;
        this.z = value.z;
    }

    /**
     * Method description
     *
     *
     * @param pos2
     *
     * @return
     */
    public double len2(vectorJ pos2) {
        double px = this.x - pos2.x;
        double py = this.y - pos2.y;
        double pz = this.z - pos2.z;

        return (px * px + py * py + pz * pz);
    }

    /**
     * Method description
     *
     *
     * @param vec1
     * @param vec2
     *
     * @return
     */
    public static vectorJ oMinus(vectorJ vec1, vectorJ vec2) {
        return new vectorJ(vec1.x - vec2.x, vec1.y - vec2.y, vec1.z - vec2.z);
    }

    /**
     * Method description
     *
     *
     * @param vec2
     *
     * @return
     */
    public vectorJ oMinusN(vectorJ vec2) {
        return new vectorJ(this.x - vec2.x, this.y - vec2.y, this.z - vec2.z);
    }

    /**
     * Method description
     *
     *
     * @param vec2
     */
    public void oMinus(vectorJ vec2) {
        this.x -= vec2.x;
        this.y -= vec2.y;
        this.z -= vec2.z;
    }

    /**
     * Method description
     *
     *
     * @param vec1
     * @param vec2
     *
     * @return
     */
    public static vectorJ oPlus(vectorJ vec1, vectorJ vec2) {
        return new vectorJ(vec2.x + vec1.x, vec2.y + vec1.y, vec2.z + vec1.z);
    }

    /**
     * Method description
     *
     *
     * @param vec2
     *
     * @return
     */
    public vectorJ oPlusN(vectorJ vec2) {
        return new vectorJ(vec2.x + this.x, vec2.y + this.y, vec2.z + this.z);
    }

    /**
     * Method description
     *
     *
     * @param vec2
     */
    public void oPlus(vectorJ vec2) {
        this.x = (vec2.x + this.x);
        this.y = (vec2.y + this.y);
        this.z = (vec2.z + this.z);
    }

    /**
     * Method description
     *
     *
     * @param vec1
     * @param vec2
     *
     * @return
     */
    public static vectorJ oCross(vectorJ vec1, vectorJ vec2) {
        vectorJ res = new vectorJ();

        res.x = (vec1.y * vec2.z - (vec1.z * vec2.y));
        res.y = (vec1.z * vec2.x - (vec1.x * vec2.z));
        res.z = (vec1.x * vec2.y - (vec1.y * vec2.x));

        return res;
    }

    /**
     * Method description
     *
     *
     * @param vec1
     * @param vec2
     *
     * @return
     */
    public static double dot(vectorJ vec1, vectorJ vec2) {
        return (vec1.x * vec2.x + vec1.y * vec2.y + vec1.z * vec2.z);
    }

    /**
     * Method description
     *
     *
     * @param vec2
     *
     * @return
     */
    public vectorJ oCross(vectorJ vec2) {
        vectorJ res = new vectorJ();

        res.x = (this.y * vec2.z - (this.z * vec2.y));
        res.y = (this.z * vec2.x - (this.x * vec2.z));
        res.z = (this.x * vec2.y - (this.y * vec2.x));

        return res;
    }

    /**
     * Method description
     *
     *
     * @param vec2
     *
     * @return
     */
    public double dot(vectorJ vec2) {
        return (this.x * vec2.x + this.y * vec2.y + this.z * vec2.z);
    }

    /**
     * Method description
     *
     */
    public void norm() {
        double len_1 = length();

        if (len_1 != 0.0D) {
            double len__1 = 1.0D / len_1;

            this.x *= len__1;
            this.y *= len__1;
            this.z *= len__1;
        } else {
            this.y = 1.0D;
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public vectorJ normN() {
        vectorJ res = new vectorJ(this.x, this.y, this.z);
        double len_2 = dot(res);

        if (len_2 != 0.0D) {
            double len__1 = 1.0D / Math.sqrt(len_2);

            res.x *= len__1;
            res.y *= len__1;
            res.z *= len__1;
        } else {
            res.y = 1.0D;
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @param vec2
     *
     * @return
     */
    public static vectorJ norm(vectorJ vec2) {
        return vec2.normN();
    }

    /**
     * Method description
     *
     *
     * @param vec2
     */
    public static void normN(vectorJ vec2) {
        vec2.norm();
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public double length() {
        return Math.sqrt(dot(this));
    }

    /**
     * Method description
     *
     *
     * @param val
     */
    public void mult(double val) {
        this.x *= val;
        this.y *= val;
        this.z *= val;
    }

    /**
     * Method description
     *
     *
     * @param scale
     *
     * @return
     */
    public vectorJ getMultiplied(double scale) {
        return new vectorJ(this.x * scale, this.y * scale, this.z * scale);
    }
}


//~ Formatted in DD Std on 13/08/26
