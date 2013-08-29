/*
 * @(#)WHOrderInfo.java   13/08/25
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


package rnr.src.gameobj;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/25
 * @author         TJ    
 */
public class WHOrderInfo {

    /** Field description */
    public static final int TYPE_DELIVERY = 0;

    /** Field description */
    public static final int TYPE_CONTEST = 1;

    /** Field description */
    public static final int TYPE_TENDER = 2;

    /** Field description */
    public static final int COOL_NONE = 0;

    /** Field description */
    public static final int COOL_GOLD = 1;

    /** Field description */
    public static final int COOL_SILVER = 2;

    /** Field description */
    public static final int COOL_BRONZE = 3;

    /** Field description */
    public static final int default_CT = 0;

    /** Field description */
    public static final int container_CT = 1;

    /** Field description */
    public static final int refrigerator_CT = 2;

    /** Field description */
    public static final int dryvan_CT = 3;

    /** Field description */
    public static final int carcarrier_CT = 4;

    /** Field description */
    public static final int cisternwater_CT = 5;

    /** Field description */
    public static final int cisternfuel_CT = 6;

    /** Field description */
    public static final int cisternchem_CT = 7;

    /** Field description */
    public static final int hopper_CT = 8;

    /** Field description */
    public static final int dump_CT = 9;

    /** Field description */
    public static final int live_calves_CT = 10;

    /** Field description */
    public static final int live_cattle_CT = 11;

    /** Field description */
    public static final int live_sheep_CT = 12;

    /** Field description */
    public static final int live_lambs_CT = 13;

    /** Field description */
    public static final int live_pigs_CT = 14;

    /** Field description */
    public static final int live_hogs_CT = 15;

    /** Field description */
    public static final int max_CT = 16;

    /** Field description */
    public static final String[] s_feebuttonnames = { "Max. Fee", "Gran prix", "Max. Fee" };

    /** Field description */
    public long nativep;

    /** Field description */
    public int WH_slot_ID;

    /** Field description */
    public String shipto;

    /** Field description */
    public String shipto_full;

    /** Field description */
    public String freight;

    /** Field description */
    public String shipper;

    /** Field description */
    public double weight;

    /** Field description */
    public double fragil;

    /** Field description */
    public int time_limit_min;

    /** Field description */
    public int time_limit_hour;

    /** Field description */
    public int charges;

    /** Field description */
    public int forfeit;

    /** Field description */
    public String cargotype;

    /** Field description */
    public int cargox;

    /** Field description */
    public int cargoy;

    /** Field description */
    public boolean competition;

    /** Field description */
    public double distance;

    /** Field description */
    public boolean accept;

    /** Field description */
    public String cargotypeid;

    /** Field description */
    public int arrowindex;

    /** Field description */
    public int order_type;

    /** Field description */
    public int order_coolness;
}


//~ Formatted in DD Std on 13/08/25
