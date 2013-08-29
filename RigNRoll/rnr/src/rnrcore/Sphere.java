/*
 * @(#)Sphere.java   13/08/26
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
public final class Sphere {
    private static final double ERROR = 0.0001D;
    private final vectorJ center = new vectorJ();
    private double sqrRadius;
    private double sqrCenterLength;

    /**
     * Constructs ...
     *
     *
     * @param centerX
     * @param centerY
     * @param centerZ
     * @param radius
     */
    public Sphere(double centerX, double centerY, double centerZ, double radius) {
        this.sqrRadius = (radius * radius);
        this.center.x = centerX;
        this.center.y = centerY;
        this.center.z = centerZ;
        this.sqrCenterLength = this.center.dot(this.center);
    }

    vectorJ getCenter() {
        return this.center;
    }

    double getSqrRadius() {
        return this.sqrRadius;
    }

    double getSqrCenterLength() {
        return this.sqrCenterLength;
    }

    /**
     * Method description
     *
     *
     * @param radius
     */
    public void setRadius(double radius) {
        this.sqrRadius = (radius * radius);
    }

    /**
     * Method description
     *
     *
     * @param centerX
     * @param centerY
     * @param centerZ
     */
    public void setCenter(double centerX, double centerY, double centerZ) {
        this.center.x = centerX;
        this.center.y = centerY;
        this.center.z = centerZ;
        this.sqrCenterLength = this.center.dot(this.center);
    }

    private static boolean isParameterOnSegment(double value) {
        return ((0.0D <= value) && (1.0D >= value));
    }

    /**
     * Method description
     *
     *
     * @param start
     * @param finish
     *
     * @return
     */
    public boolean intersecs(vectorJ start, vectorJ finish) {
        double finishDotFinish = finish.dot(finish);
        double finishDotCenter = finish.dot(this.center);
        double a = start.len2(finish);
        double b = 2.0D * (-finishDotFinish - start.dot(this.center) + finishDotCenter + start.dot(finish));
        double c = this.sqrCenterLength + finishDotFinish - (2.0D * finishDotCenter) - this.sqrRadius;

        if (0.0001D > Math.abs(a)) {
            return (this.sqrRadius >= start.len2(this.center));
        }

        double discriminant = b * b - (4.0D * a * c);

        if (0.0D > discriminant) {
            return false;
        }

        if (0.0001D > discriminant) {
            return isParameterOnSegment(-b / 2.0D * a);
        }

        double denominator = 1.0D / 2.0D * a;
        double sqrtDiscriminant = Math.sqrt(discriminant);
        double leftParameter = (-b + sqrtDiscriminant) * denominator;
        double rightParameter = (-b - sqrtDiscriminant) * denominator;

        return (((0.0D <= leftParameter) && (1.0D >= leftParameter))
                || ((0.0D <= rightParameter) && (1.0D >= rightParameter))
                || ((0.0D >= leftParameter) && (1.0D <= rightParameter))
                || ((0.0D >= rightParameter) && (1.0D <= leftParameter)));
    }
}


//~ Formatted in DD Std on 13/08/26
