/*
 * @(#)ControlGroup.java   13/08/26
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


package rnr.src.menu.resource;

class ControlGroup implements IMenuResources {
    private String filename;
    private String groupname;

    /**
     * Constructs ...
     *
     *
     * @param filename
     * @param groupname
     */
    public ControlGroup(String filename, String groupname) {
        this.setFilename(filename);
        this.setGroupname(groupname);
    }

    /**
     * Method description
     *
     *
     * @param menuDescriptor
     *
     * @return
     */
    @Override
    public long[] loadResource(long menuDescriptor) {
        return null;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the groupname
     */
    public String getGroupname() {
        return groupname;
    }

    /**
     * @param groupname the groupname to set
     */
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}


//~ Formatted in DD Std on 13/08/26