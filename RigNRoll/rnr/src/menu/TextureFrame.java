/*
 * @(#)TextureFrame.java   13/08/25
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

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class TextureFrame {
    float m_maxx;
    float m_maxy;
    int x;
    int y;
    int texsx;
    int texsy;
    String name;

    /**
     * Method description
     *
     *
     * @param maxx
     * @param maxy
     */
    public void Init(int maxx, int maxy) {
        this.m_maxx = maxx;
        this.m_maxy = maxy;
    }

    /**
     * Method description
     *
     *
     * @param maxx
     * @param maxy
     * @param _texsx
     * @param _texsy
     */
    public void Init(int maxx, int maxy, int _texsx, int _texsy) {
        this.m_maxx = maxx;
        this.m_maxy = maxy;
        this.texsx = _texsx;
        this.texsy = _texsy;
    }

    /**
     * Method description
     *
     *
     * @param picture
     * @param _x
     * @param _y
     */
    public void ApplyToPicture(MENUText_field picture, int _x, int _y) {
        this.x = _x;
        this.y = _y;
        this.name = null;
        menues.CallMappingModifications(picture.nativePointer, this, "Mapping");
    }

    /**
     * Method description
     *
     *
     * @param pointer
     * @param _x
     * @param _y
     * @param _name
     */
    public void ApplyToPatch(long pointer, int _x, int _y, String _name) {
        this.x = _x;
        this.y = _y;
        this.name = _name;
        menues.CallMappingModifications(pointer, this, "Mapping");
    }

    void Mapping(int sizex, int sizey, menues.CMaterial_whithmapping[] stuff) {
        for (int i = 0; i < stuff.length; ++i) {
            if (this.name == null) {
                if (!(stuff[i].usepatch)) {
                    ;
                }
            } else {
                if (!(stuff[i].usepatch)) {
                    continue;
                }

                if (!(this.name.equals(stuff[i]._patch.tip))) {
                    continue;
                }

                menues.ctexcoord_multylayer layer = (menues.ctexcoord_multylayer) stuff[i].tex.get(0);

                if ((this.texsx != 0) && (this.texsy != 0)) {
                    layer.t0x = (this.x / this.m_maxx + 2.0F / this.texsx);
                    layer.t0y = (this.y / this.m_maxy + 2.0F / this.texsy);
                    layer.t1x = ((this.x + 1) / this.m_maxx - (2.0F / this.texsx));
                    layer.t1y = (this.y / this.m_maxy + 2.0F / this.texsy);
                    layer.t2x = ((this.x + 1) / this.m_maxx - (2.0F / this.texsx));
                    layer.t2y = ((this.y + 1) / this.m_maxy - (2.0F / this.texsy));
                    layer.t3x = (this.x / this.m_maxx + 2.0F / this.texsx);
                    layer.t3y = ((this.y + 1) / this.m_maxy - (2.0F / this.texsy));
                } else {
                    layer.t0x = (this.x / this.m_maxx);
                    layer.t0y = (this.y / this.m_maxy);
                    layer.t1x = ((this.x + 1) / this.m_maxx);
                    layer.t1y = (this.y / this.m_maxy);
                    layer.t2x = ((this.x + 1) / this.m_maxx);
                    layer.t2y = ((this.y + 1) / this.m_maxy);
                    layer.t3x = (this.x / this.m_maxx);
                    layer.t3y = ((this.y + 1) / this.m_maxy);
                }
            }
        }
    }
}


//~ Formatted in DD Std on 13/08/25
