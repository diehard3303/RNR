/*
 * @(#)UrgetAgent.java   13/08/26
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


package rnr.src.rnrorg;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class UrgetAgent {

    /**
     * Method description
     *
     *
     * @param element
     * @param secondsRest
     * @param mission_name
     *
     * @return
     */
    public static IStoreorgelement.Status changeStatus(IStoreorgelement element, int secondsRest, String mission_name) {
        if (rnr.src.rnrscr.Helper.getCurrentPosition() == null) {
            return element.getStatus();
        }

        switch (element.getStatus().ordinal()) {
         case 1 :
             double secondToRide = (element instanceof WarehouseOrder)
                                   ? rnrcore.Helper.getTimeToReachFinishPointIsSecondsWarehouseOrder()
                                   : rnrcore.Helper.getTimeToReachFinishPointIsSeconds(mission_name);

             return ((secondsRest < secondToRide * 0.7D)
                     ? IStoreorgelement.Status.urgentMission
                     : IStoreorgelement.Status.pendingMission);

         case 2 :
             double secondToRide = (element instanceof WarehouseOrder)
                                   ? rnrcore.Helper.getTimeToReachFinishPointIsSecondsWarehouseOrder()
                                   : rnrcore.Helper.getTimeToReachFinishPointIsSeconds(mission_name);

             return ((secondsRest > secondToRide * 0.8D)
                     ? IStoreorgelement.Status.pendingMission
                     : IStoreorgelement.Status.urgentMission);
        }

        return element.getStatus();
    }
}


//~ Formatted in DD Std on 13/08/26
