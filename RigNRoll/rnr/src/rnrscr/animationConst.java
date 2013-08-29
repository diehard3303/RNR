/*
 * @(#)animationConst.java   13/08/28
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


package rnr.src.rnrscr;

/**
 * Interface description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public abstract interface animationConst {

    /** Field description */
    public static final int MOVETO = 1;

    /** Field description */
    public static final int PLAYANIM = 2;

    /** Field description */
    public static final int ANIMREPLACE = 3;

    /** Field description */
    public static final int DIRECTANIMATION_CHAIN = 5;

    /** Field description */
    public static final int CYCLEANIMATION = 1;

    /** Field description */
    public static final int UPTOENDANIMATION = 2;

    /** Field description */
    public static final int FADEANDREFOCUS = 3;

    /** Field description */
    public static final int FADEANDRESTOREORIGINAL = 4;

    /** Field description */
    public static final int CYCLEWAY = 5;

    /** Field description */
    public static final int ROTATEEVENT = 6;

    /** Field description */
    public static final int BORNDIEWAY = 7;

    /** Field description */
    public static final int ACCESS_CANNTWRIGHT = 100;

    /** Field description */
    public static final int SENDERDIED = 200;

    /** Field description */
    public static final int SPACEEVENTS = 1;

    /** Field description */
    public static final int KEYBOARDEVENTS = 2;

    /** Field description */
    public static final int BORNKILLEVENTS = 3;

    /** Field description */
    public static final int ONENDEVENT = 4;

    /** Field description */
    public static final int WAYTURNSEVENTS = 5;

    /** Field description */
    public static final int AROUNDCAR = 6;

    /** Field description */
    public static final int JUMPFROMCAR = 7;

    /** Field description */
    public static final int PREDEFINE = 8;

    /** Field description */
    public static final int WALKFIVWTYPE = 1;

    /** Field description */
    public static final int JUMPFIVWTYPE = 2;

    /** Field description */
    public static final int DOWNFIVWTYPE = 3;

    /** Field description */
    public static final int RUNFIVWTYPE = 4;

    /** Field description */
    public static final int CURRENTFIVWTYPE = 5;

    /** Field description */
    public static final int STAYFIVWTYPE = 6;

    /** Field description */
    public static final int BODY = 1;

    /** Field description */
    public static final int LEGS = 2;

    /** Field description */
    public static final int HEAD = 3;

    /** Field description */
    public static final int RHAND = 4;

    /** Field description */
    public static final int LHAND = 5;

    /** Field description */
    public static final int JUMPS_ANIMGROP = 1;

    /** Field description */
    public static final int WALL_ANIMGROP = 2;

    /** Field description */
    public static final int WALK_ANIMGROP = 3;

    /** Field description */
    public static final int RUN_ANIMGROP = 4;

    /** Field description */
    public static final int STAY_ANIMGROP = 5;

    /** Field description */
    public static final int ASINHRON = 6;

    /** Field description */
    public static final int CYCLEANIM = 700;

    /** Field description */
    public static final int NONCYCLEANIM = 800;

    /** Field description */
    public static final int FORWARD_JUMP_GT = 1;

    /** Field description */
    public static final int BACK_JUMP_GT = 2;

    /** Field description */
    public static final int LEFT_JUMP_GT = 3;

    /** Field description */
    public static final int RIGHT_JUMP_GT = 4;

    /** Field description */
    public static final int FALLRIGHT_JUMP_GT = 5;

    /** Field description */
    public static final int FALLLEFT_JUMP_GT = 6;

    /** Field description */
    public static final int FALLFRONT_GT = 7;

    /** Field description */
    public static final int FALLBACK_GT = 8;

    /** Field description */
    public static final int STANDINGUPBACK_GT = 9;

    /** Field description */
    public static final int STANDINGUPFRONT_GT = 10;

    /** Field description */
    public static final int LEFT_LIFT_GT = 11;

    /** Field description */
    public static final int RIGHT_LIFT_GT = 12;

    /** Field description */
    public static final int FLIGHTOVER_CHEST_GT = 13;

    /** Field description */
    public static final int FLIGHTOVER_BACK_GT = 14;

    /** Field description */
    public static final int ORDINARY_WALK_GT = 1;

    /** Field description */
    public static final int FAST_WALK_GT = 2;

    /** Field description */
    public static final int BACKTOWALL_GT = 1;

    /** Field description */
    public static final int FRONTTOWALL_GT = 2;

    /** Field description */
    public static final int FREE_GT = 1;

    /** Field description */
    public static final int LAINGONBACK_GT = 2;

    /** Field description */
    public static final int LAINGONCHEST_GT = 3;

    /** Field description */
    public static final int RUN_GT = 1;

    /** Field description */
    public static final int ASKUNDERCAR = 6;

    /** Field description */
    public static final int ASKBLOCKEDBYCAR = 7;
}


//~ Formatted in DD Std on 13/08/28
