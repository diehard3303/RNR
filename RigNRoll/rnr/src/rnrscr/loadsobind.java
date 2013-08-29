/*
 * @(#)loadsobind.java   13/08/28
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
 * Class description
 *
 *
 * @version        1.0, 13/08/28
 * @author         TJ    
 */
public class loadsobind {
    static final int BARBIND = 1;
    static final int STOBIND = 2;
    static final int MOTELBIND = 3;
    static final int OFICEBIND = 4;
    static final int PPBIND = 5;
    static final int POLICEBIND = 6;
    Object bind;
    int bindtype;

    loadsobind() {
        this.bind = null;
        this.bindtype = 0;
    }

    void BindBar(cSpecObjects bar) {
        this.bind = new cBindToSpecObject_Bar(bar);
        this.bindtype = 1;
    }

    void BindOffice(cSpecObjects office) {
        this.bind = new cBindToSpecObject_Office(office);
        this.bindtype = 4;
    }

    void BindMotel(cSpecObjects motel) {
        this.bind = new cBindToSpecObject_Motel(motel);
        this.bindtype = 3;
    }

    void BindPolice(cSpecObjects police) {
        this.bind = new cBindToSpecObject_Police(police);
        this.bindtype = 6;
    }

    void BindSTO(cSpecObjects police) {
        this.bind = new cBindToSpecObject_STO(police);
        this.bindtype = 2;
    }

    void BindPP(cSpecObjects police) {
        this.bind = new cBindToSpecObject_ParkingPlace(police);
        this.bindtype = 5;
    }

    boolean Unloaded(specobjects so) {
        switch (this.bindtype) {
         case 1 :
             return (!(((cBindToSpecObject_Bar) this.bind).Loaded(so)));

         case 2 :
             return (!(((cBindToSpecObject_STO) this.bind).Loaded(so)));

         case 3 :
             return (!(((cBindToSpecObject_Motel) this.bind).Loaded(so)));

         case 4 :
             return (!(((cBindToSpecObject_Office) this.bind).Loaded(so)));

         case 5 :
             return (!(((cBindToSpecObject_ParkingPlace) this.bind).Loaded(so)));

         case 6 :
             return (!(((cBindToSpecObject_Police) this.bind).Loaded(so)));
        }

        return false;
    }

    class cBindToSpecObject_Bar {
        cSpecObjects object;

        cBindToSpecObject_Bar(cSpecObjects paramcSpecObjects) {
            this.object = paramcSpecObjects;
        }

        boolean Loaded(specobjects so) {
            return so.ifLoadedBar(this.object.name);
        }
    }


    class cBindToSpecObject_Motel {
        cSpecObjects object;

        cBindToSpecObject_Motel(cSpecObjects paramcSpecObjects) {
            this.object = paramcSpecObjects;
        }

        boolean Loaded(specobjects so) {
            return so.ifLoadedMotel(this.object.name);
        }
    }


    class cBindToSpecObject_Office {
        cSpecObjects object;

        cBindToSpecObject_Office(cSpecObjects paramcSpecObjects) {
            this.object = paramcSpecObjects;
        }

        boolean Loaded(specobjects so) {
            return so.ifLoadedOffice(this.object.name);
        }
    }


    class cBindToSpecObject_ParkingPlace {
        cSpecObjects object;

        cBindToSpecObject_ParkingPlace(cSpecObjects paramcSpecObjects) {
            this.object = paramcSpecObjects;
        }

        boolean Loaded(specobjects so) {
            return so.ifLoadedParkingPlaces(this.object.name);
        }
    }


    class cBindToSpecObject_Police {
        cSpecObjects object;

        cBindToSpecObject_Police(cSpecObjects paramcSpecObjects) {
            this.object = paramcSpecObjects;
        }

        boolean Loaded(specobjects so) {
            return so.ifLoadedPolice(this.object.name);
        }
    }


    class cBindToSpecObject_STO {
        cSpecObjects object;

        cBindToSpecObject_STO(cSpecObjects paramcSpecObjects) {
            this.object = paramcSpecObjects;
        }

        boolean Loaded(specobjects so) {
            return so.ifLoadedRepair(this.object.name);
        }
    }
}


//~ Formatted in DD Std on 13/08/28
