/*
 * @(#)cabinpanel.java   13/08/26
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
public class cabinpanel extends Cabin implements cabinlightsConst {
    static class Army_vechicleinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Army_vechicle_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Army_vechicle_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Army_vechicle_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Army_vechicle_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Army_vechicle_LLight_Gab", 3, false);
        }
    }


    static class Beverageinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Beverage_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Beverage_LLight_Gab", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Beverage_LLight_Back1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Beverage_LLight_Back2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Beverage_LLight_Stop1", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Beverage_LLight_Stop2", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Beverage_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Beverage_LLight_Right", 5, false);
        }
    }


    static class Car_carierinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Car_carier_Light_rot_01", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Car_carier_Light_rot_02", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Car_carier_Light_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Car_carier_Light_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Car_carier_Light_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Car_carier_Light_Gab", 1, false);
        }
    }


    static class Chevrolet_Avalancheinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Avalanche_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Avalanche_LLight_rot_01", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Avalanche_LLight_rot_02", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Avalanche_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Avalanche_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Avalanche_LLight_Stop", 8, false);
        }
    }


    static class Chevrolet_Cavalierinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Cavalier_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Cavalier_LLight_Left1", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Cavalier_LLight_Left2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Cavalier_LLight_Left3", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Cavalier_LLight_Left4", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Cavalier_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Cavalier_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Cavalier_LLight_Stop", 8, false);
        }
    }


    static class Chevrolet_Expressinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Express_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Express_LLight_Left1", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Express_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Express_LLight_Right1", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Express_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Express_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Express_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Express_LLight_Stop", 8, false);
        }
    }


    static class Chevrolet_Silveradoinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Silverado_LLight_Front_Far", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Silverado_LLight_rot_01", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Silverado_LLight_rot_02", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Silverado_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Silverado_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Silverado_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Chevy_Silverado_LLight_Gab", 3, false);
        }
    }


    static class Chrysler_Voyagerinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Chrysler_Voyager_LLight_Fronti", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Chrysler_Voyager_LLight_Left1", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Chrysler_Voyager_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Chrysler_Voyager_LLight_Right1", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Chrysler_Voyager_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Chrysler_Voyager_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Chrysler_Voyager_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Chrysler_Voyager_LLight_Stop", 8, false);
        }
    }


    static class Curtain_sideinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Curtain_side_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Curtain_side_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Curtain_side_LLight_Back1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Curtain_side_LLight_Back2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Curtain_side_LLight_Stop1", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Curtain_side_LLight_Stop2", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Curtain_side_LLight_Gab1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Curtain_side_LLight_Gab2", 1, false);
        }
    }


    static class Dodge_Grand_Caravaninit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Dodge_Gr_Cv_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Dodge_Gr_Cv_LLight_Left1", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Dodge_Gr_Cv_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Dodge_Gr_Cv_LLight_Right1", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Dodge_Gr_Cv_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Dodge_Gr_Cv_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Dodge_Gr_Cv_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Dodge_Gr_Cv_LLight_Stop", 8, false);
        }
    }


    static class Dodge_Intrepidinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Dodge_Intrepid_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Dodge_Intrepid_LLight_rot_01", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Dodge_Intrepid_LLight_rot_02", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Dodge_Intrepid_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Dodge_Intrepid_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Dodge_Intrepid_LLight_Stop", 8, false);
        }
    }


    static class DryVan_48d_01init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_48d_01_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_48d_01_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_48d_01_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_48d_01_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_48d_01_LLight_Gab", 1, false);
        }
    }


    static class DryVan_48d_Ochakovoinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_48d_Ochakovo_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_48d_Ochakovo_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_48d_Ochakovo_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_48d_Ochakovo_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_48d_Ochakovo_LLight_Gab", 1, false);
        }
    }


    static class DryVan_48d_Stimorolinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_48d_Stimorol_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_48d_Stimorol_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_48d_Stimorol_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_48d_Stimorol_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_48d_Stimorol_LLight_Gab", 1, false);
        }
    }


    static class DryVan_49d_01init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_DryVan_49d_01_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_DryVan_49d_01_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_DryVan_49d_01_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_DryVan_49d_01_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_DryVan_49d_01_LLight_Gab", 1, false);
        }
    }


    static class DryVan_Topoinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_DryVan_Topo_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_DryVan_Topo_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_DryVan_Topo_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_DryVan_Topo_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_DryVan_Topo_LLight_Gab", 1, false);
        }
    }


    static class Dry_Bulk_2init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Dry_Bulk_2_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Dry_Bulk_2_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Dry_Bulk_2_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Dry_Bulk_2_LLight_Gab", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Dry_Bulk_2_LLight_Stop", 8, false);
        }
    }


    static class Dry_Bulkinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Dry_Bulk_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Dry_Bulk_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Dry_Bulk_LLight_Back1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Dry_Bulk_LLight_Back2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Dry_Bulk_LLight_Stop1", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Dry_Bulk_LLight_Stop2", 8, false);
        }
    }


    static class Dump2init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Dump2_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Dump2_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Dump2_LLight_Back1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Dump2_LLight_Back2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Dump2_LLight_Stop1", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Dump2_LLight_Stop2", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Dump2_LLight_Gab1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Dump2_LLight_Gab2", 1, false);
        }
    }


    static class Dumpinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Dump_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Dump_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Dump_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Dump_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Dump_LLight_Gab", 1, false);
        }
    }


    static class FREIGHTLINER_ARGOSYinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Inside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_Gauges", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_Gauges_hnd_br", 9, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_Gauges_turner_left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_Gauges_turner_right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_Gauges_light", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_Gauges_fwd_br", 16, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_Gauges_antifriz", 12, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_Gauges_abs", 15, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_Gauges_oil_wh_dr", 14, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_Gauges_oil", 13, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_Gauges_av", 6, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_Gauges_podv", 17, true);

            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Argosy_GauSpeedSpace", 1, true);

            Cabin.TuneGAUGE(GAUGE, 5.0D, 85.0D, -120.0D, 120.0D, 2.237D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Argosy_GauRPMSpace", 2, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 3.0D, -135.0D, 135.0D, 0.06D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Argosy_GauFuelSpace", 3, true);
            Cabin.TuneGAUGE(GAUGE, -0.0D, 1.0D, -55.0D, 55.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Freight_Argosy_Gauges_lamp", 0.0D, 0.1D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Argosy_Gau001Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Argosy_Gau002Space", 5, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 250.0D, -135.0D, 135.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Freight_Argosy_Gauges_rear_br", 0.9300000000000001D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Argosy_Gau010Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Argosy_Gau006Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Argosy_Gau007Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Argosy_Gau008Space", 8, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Argosy_Gau004Space", 14, true);
            Cabin.TuneGAUGE(GAUGE, 10.0D, 18.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Argosy_Gau005Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Argosy_Gau003Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Argosy_Gau009Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Argosy_Gau011Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
        }

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Argosy_Wiper1Space", 9, false);

            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 90.5D, -9.5D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Argosy_Wiper2Space", 9, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 90.5D, -9.5D, 1.0D);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_LLight_Stop", 8, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_LLight_Park", 2, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_LLight_Front_Far_01", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_LLight_Front_01", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_LLight_Front_Far_02", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_LLight_Front_02", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_LLight_Left", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Argosy_LLight_Right", 5, true);
        }
    }


    static class FREIGHTLINER_CENTURYinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Inside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_Gauges", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_hnd_br", 9, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_turner_left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_turner_right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_light", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_fwd_br", 16, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_antifriz", 12, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_abs", 15, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_oil_wh_dr", 14, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_oil", 13, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_av", 6, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_podv", 17, true);

            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Century_GauSpeed_Space", 1, true);

            Cabin.TuneGAUGE(GAUGE, 5.0D, 85.0D, -120.0D, 120.0D, 2.237D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Century_GauRPM_Space", 2, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 3.0D, -135.0D, 135.0D, 0.06D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Century_GauFuel_Space", 3, true);
            Cabin.TuneGAUGE(GAUGE, -0.0D, 1.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Century_Gau001_Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Century_Gau002_Space", 5, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 250.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Century_Gau005_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Century_Gau006_Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Century_Gau007_Space", 8, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Century_Gau003_Space", 14, true);
            Cabin.TuneGAUGE(GAUGE, 10.0D, 18.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Century_Gau004_Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Century_Gau008_Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Century_Gau009_Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
        }

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Century_Wiper1Space", 9, false);

            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 91.0D, 0.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Century_Wiper2Space", 9, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 90.680000000000007D, 0.0D, 1.0D);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_LLight_Gab", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_LLight_FrontFar", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_LLight_rot_left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Century_LLight_rot_right", 5, false);
        }
    }


    static class FREIGHTLINER_CLASSIC_XLinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Inside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Freight_ClassicXL_Gauges", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_ClassicXL_hnd_br", 9, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_ClassicXL_turner_left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_ClassicXL_turner_right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_ClassicXL_light", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_ClassicXL_fwd_br", 16, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_ClassicXL_antifriz", 12, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_ClassicXL_abs", 15, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_ClassicXL_oil_wh_dr", 14, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_ClassicXL_oil", 13, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_ClassicXL_av", 6, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_ClassicXL_podv", 17, true);

            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_GauSpeed_Space", 1, true);

            Cabin.TuneGAUGE(GAUGE, 5.0D, 85.0D, -120.0D, 120.0D, 2.237D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_GauRPM_Space", 2, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 3.0D, -135.0D, 135.0D, 0.06D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_GauFuel_Space", 3, true);
            Cabin.TuneGAUGE(GAUGE, -0.0D, 1.0D, -55.0D, 55.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Freight_ClassicXL_lamp", 0.0D, 0.1D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_Gau001_Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 100.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_Gau002_Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 100.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_Gau003_Space", 5, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 250.0D, -135.0D, 135.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Freight_ClassicXL_rear_br", 0.9300000000000001D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_Gau007_Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_Gau008_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_Gau009_Space", 8, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_Gau013_Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_Gau014_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_Gau005_Space", 14, true);
            Cabin.TuneGAUGE(GAUGE, 10.0D, 18.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_Gau006_Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_Gau011_Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_Gau010_Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_Gau015_Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_Gau016_Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_Gau017_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_Gau018_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
        }

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_Wiper1Space", 9, false);

            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 70.5D, -29.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_ClassicXL_Wiper2Space", 9, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 70.0D, -20.0D, 1.0D);
            Cabin.CreateLight(cabin_np, "Dword_FCXL_LLight_Stop", 8, true);
            Cabin.CreateLight(cabin_np, "Dword_FCXL_LLight_Park", 2, true);
            Cabin.CreateLight(cabin_np, "Dword_FCXL_LLight_Back", 1, true);
            Cabin.CreateLight(cabin_np, "Switch_FCXL_LLight_FFL", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_FCXL_LLight_Far", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_FCXL_LLight", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_FCXL_LLight_Turn_Left", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_FCXL_LLight_Turn_Right", 5, true);
        }
    }


    static class FREIGHTLINER_CORONADOinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Inside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Freight_Cor_Gauges", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Cor_hnd_br", 9, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Cor_turner_left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Cor_turner_right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Cor_light", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Cor_fwd_br", 16, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Cor_antifriz", 12, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Cor_abs", 15, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Cor_oil_wh_dr", 14, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Cor_oil", 13, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Cor_av", 6, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Cor_podv", 17, true);

            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Cor_GauSpeed_Space", 1, true);

            Cabin.TuneGAUGE(GAUGE, 5.0D, 85.0D, -120.0D, 120.0D, 2.237D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Cor_GauRPM_Space", 2, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 3.0D, -135.0D, 135.0D, 0.06D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Cor_GauFuel_Space", 3, true);
            Cabin.TuneGAUGE(GAUGE, -0.0D, 1.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Cor_Gau002_Space", 5, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 250.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Cor_Gau003_Space", 14, true);
            Cabin.TuneGAUGE(GAUGE, 10.0D, 18.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Cor_Gau004_Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Cor_Gau012_Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 100.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Cor_Gau013_Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Cor_Gau005_Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Cor_Gau006_Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Cor_Gau001_Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Cor_Gau007_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Cor_Gau010_Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Cor_Gau011_Space", 8, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Cor_Gau008_Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Cor_Gau009_Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
        }

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Cor_Wiper1Space", 9, false);

            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 90.0D, -5.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Freight_Cor_Wiper2Space", 9, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 90.0D, -30.0D, 1.0D);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Cor_LLight_front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Cor_LLight_front_far", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Cor_LLight_gab", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Cor_LLight_left", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Cor_LLight_park", 2, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Cor_LLight_right", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Freight_Cor_LLight_stop", 8, true);
        }
    }


    static class FREIGHTLINER_FL60_2init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Freightliner_FL60_2_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Freightliner_FL60_2_LLight_Left1", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Freightliner_FL60_2_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Freightliner_FL60_2_LLight_Right1", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Freightliner_FL60_2_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Freightliner_FL60_2_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Freightliner_FL60_2_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Freightliner_FL60_2_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Freightliner_FL60_2_LLight_Gab", 3, false);
        }
    }


    static class FREIGHTLINER_FL60init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Freightliner_FL60_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Freightliner_FL60_LLight_Left1", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Freightliner_FL60_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Freightliner_FL60_LLight_Right1", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Freightliner_FL60_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Freightliner_FL60_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Freightliner_FL60_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Freightliner_FL60_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Freightliner_FL60_LLight_Gab", 3, false);
        }
    }


    static class Ford_CV_policeinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Police01_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Police01_LLight_Left1", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Police01_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Police01_LLight_Right1", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Police01_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Police01_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Police01_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Police01_LLight_Stop", 8, false);
        }
    }


    static class Ford_CV_taxi1init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_taxi1_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_taxi1_LLight_Left1", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_taxi1_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_taxi1_LLight_Right1", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_taxi1_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_taxi1_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_taxi1_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_taxi1_LLight_Stop", 8, false);
        }
    }


    static class Ford_CV_taxiinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_taxi_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_taxi_LLight_Left1", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_taxi_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_taxi_LLight_Right1", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_taxi_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_taxi_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_taxi_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_taxi_LLight_Stop", 8, false);
        }
    }


    static class Ford_CVinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_LLight_Left1", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_LLight_Right1", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_CV_LLight_Stop", 8, false);
        }
    }


    static class Ford_F350init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Ford_F_350_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_F_350_LLight_Left1", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_F_350_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_F_350_LLight_Right1", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_F_350_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_F_350_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_F_350_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_F_350_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Ford_F_350_LLight_Gab", 3, false);
        }
    }


    static class Freightliner_FL_65_Schoolbusinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Schoolbus_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Schoolbus_LLight_Left1", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Schoolbus_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Schoolbus_LLight_Right1", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Schoolbus_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Schoolbus_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Schoolbus_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Schoolbus_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Schoolbus_LLight_Gab", 3, false);
        }
    }


    static class GEPARDinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Inside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Gepard_Gauges", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_hnd_br", 9, true);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_turner_left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_turner_right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_light", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_fwd_br", 16, true);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_antifriz", 12, true);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_abs", 15, true);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_oil_wh_dr", 14, true);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_oil", 13, true);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_av", 6, false);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_podv", 17, true);

            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Gepard_GauSpeed_Space", 1, true);

            Cabin.TuneGAUGE(GAUGE, 0.0D, 115.0D, -90.0D, 90.0D, 2.237D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Gepard_GauRPM_Space", 2, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 3.0D, -90.0D, 90.0D, 0.06D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Gepard_GauFuel_Space", 3, true);
            Cabin.TuneGAUGE(GAUGE, -0.0D, 1.0D, -90.0D, 90.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Gepard_GauJetFuel_Space", 3, true);
            Cabin.TuneGAUGE(GAUGE, -0.0D, 1.0D, -90.0D, 90.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Gepard_Gau002_Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -90.0D, 90.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Gepard_Gau003_Space", 14, true);
            Cabin.TuneGAUGE(GAUGE, 10.0D, 18.0D, -90.0D, 90.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Gepard_Gau001_Space", 5, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 250.0D, -90.0D, 90.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Gepard_rear_br", 0.9300000000000001D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Gepard_Gau004_Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -90.0D, 90.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Gepard_Gau005_Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -90.0D, 90.0D, 1.0D);
        }

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Gepard_Wiper1Space", 9, false);

            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 75.450000000000003D, -14.33D, -1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Gepard_Wiper2Space", 9, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, -75.879999999999995D, 15.34D, 1.0D);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_LLight_Booth_Stop", 8, true);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_LLight_Booth_Park", 2, true);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_LLight_Booth_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_LLight_Bumper_Front1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_LLight_Bumper_Front2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_LLight_Capot_Front1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_LLight_Capot_Front2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_LLight_Capot_Rot_Left1", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_LLight_Capot_Rot_Left2", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_LLight_Booth_Left", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_LLight_Capot_Rot_Right1", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_LLight_Capot_Rot_Right2", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_LLight_Booth_Right", 5, true);
        }
    }


    static class GMC_TOPKICK4500_JOHN_TRAMPLINinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Tramp_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Tramp_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Tramp_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Tramp_LLight_Gab", 3, false);
        }
    }


    static class GMC_TOPKICK4500_JOHNinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick4500_John_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick4500_John_LLight_Left1", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick4500_John_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick4500_John_LLight_Right1", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick4500_John_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick4500_John_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick4500_John_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick4500_John_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick4500_John_LLight_Gab", 3, false);
        }
    }


    static class GMC_TOPKICK4500init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick4500_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick4500_LLight_Left1", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick4500_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick4500_LLight_Right1", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick4500_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick4500_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick4500_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick4500_LLight_Gab", 3, false);
        }
    }


    static class GMC_TOPKICK8500_CISTERNinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Cistern_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Cistern_LLight_Left1", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Cistern_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Cistern_LLight_Right1", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Cistern_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Cistern_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Cistern_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Cistern_LLight_Gab", 3, false);
        }
    }


    static class GMC_TOPKICK8500_DUMPinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Dump_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Dump_LLight_Left1", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Dump_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Dump_LLight_Right1", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Dump_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Dump_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Dump_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Dump_LLight_Gab", 3, false);
        }
    }


    static class GMC_TOPKICK8500_FLATinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Flat_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Flat_LLight_Left1", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Flat_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Flat_LLight_Right1", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Flat_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Flat_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Flat_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_Flat_LLight_Gab", 3, false);
        }
    }


    static class GMC_TOPKICK8500init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_LLight_Left1", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_LLight_Right1", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_GMC_TopKick8500_LLight_Gab", 3, false);
        }
    }


    static class Gas_tank_01init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Gas_tank_LightBack", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Gas_tank_LightStop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Gas_tank_LightPark", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Gas_tank_LightLeft", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Gas_tank_LightRight", 5, false);
        }
    }


    static class Gepard_Trailerinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Gepard_Trailer_LLight_Gab", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_Trailer_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_Trailer_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_Trailer_LLight_Back1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_Trailer_LLight_Back2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_Trailer_LLight_Stop1", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_Trailer_LLight_Stop2", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_Trailer_LLight_Park1", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Gepard_Trailer_LLight_Park2", 2, false);
        }
    }


    static class Hopperinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Hopper_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Hopper_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Hopper_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Hopper_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Hopper_LLight_Gab", 1, false);
        }
    }


    static class Horse_Trailerinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Horse_Trailer_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Horse_Trailer_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Horse_Trailer_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Horse_Trailer_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Horse_Trailer_LLight_Gab", 1, false);
        }
    }


    static class KENWORTH_T2000init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Inside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_KenworthT2000_Gauges", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_hnd_br", 9, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_turner_left", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_turner_right", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_light", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_fwd_br", 16, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_antifriz", 12, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_abs", 15, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_oil_wh_dr", 14, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_oil", 13, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_av", 6, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_podv", 17, true);

            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T2000_GauSpeed_Space", 1, false);

            Cabin.TuneGAUGE(GAUGE, 5.0D, 85.0D, -120.0D, 120.0D, 2.237D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T2000_GauRPM_Space", 2, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 3.0D, -135.0D, 135.0D, 0.06D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T2000_GauFuel_Space", 3, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, -55.0D, 55.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Kenworth_T2000_lamp", 0.0D, 0.1D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T2000_Gau001_Space", 5, false);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 250.0D, -135.0D, 135.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Kenworth_T2000_rear_br", 237.0D, 260.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T2000_Gau003_Space", 7, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T2000_Gau002_Space", 6, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 100.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T2000_Gau007_Space", 7, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T2000_Gau008_Space", 10, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T2000_Gau009_Space", 8, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T2000_Gau013_Space", 7, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T2000_Gau014_Space", 10, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T2000_Gau005_Space", 14, false);
            Cabin.TuneGAUGE(GAUGE, 10.0D, 18.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T2000_Gau006_Space", 4, false);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T2000_Gau011_Space", 4, false);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T2000_Gau010_Space", 12, false);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T2000_Gau012_Space", 11, false);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T2000_Gau015_Space", 11, false);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
        }

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T2000_Wiper1Space", 9, false);

            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 81.760000000000005D, -28.879999999999999D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T2000_Wiper2Space", 9, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 72.079999999999998D, -30.539999999999999D, 1.0D);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Bumper_Front1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Bumper_Front2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Bumper_Front3", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Bumper_Front4", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Bumper_Left", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Bumper_Right", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Case_Left", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_KenworthT2000_LLights_RamaLeft", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_KenworthT2000_LLights_RamaRight", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Case_Right", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Case_Gab0", 3, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Case_Gab1", 3, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Case_Gab2", 3, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Both_upgr3", 3, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Case_upgr2_Gab1", 3, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Case_upgr2_Gab2", 3, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Case_upgr3_Gab1", 3, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Case_upgr3_Gab2", 3, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Step02_upgr2_Gab1", 3, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Step02_upgr2_Gab2", 3, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Step02_upgr3_Gab1", 3, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T2000_LLight_Step02_upgr3_Gab2", 3, false);
            Cabin.CreateLight(cabin_np, "Dword_KenworthT2000_LLights_Park", 2, true);
            Cabin.CreateLight(cabin_np, "Dword_KenworthT2000_LLights_Stop", 8, true);
            Cabin.CreateLight(cabin_np, "Dword_KenworthT2000_LLights_Back", 1, false);
        }
    }


    static class KENWORTH_T600Winit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Inside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_Gauges", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_hnd_br", 9, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_turner_left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_turner_right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_light", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_fwd_br", 16, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_antifriz", 12, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_abs", 15, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_oil_wh_dr", 14, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_oil", 13, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_av", 6, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_podv", 17, true);

            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_GauSpeed_Space", 1, true);

            Cabin.TuneGAUGE(GAUGE, 5.0D, 85.0D, -120.0D, 120.0D, 2.237D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_GauRPM_Space", 2, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 3.0D, -135.0D, 135.0D, 0.06D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_GauFuel_Space", 3, true);
            Cabin.TuneGAUGE(GAUGE, -0.0D, 1.0D, -55.0D, 55.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Kenworth_T600_lamp", 0.0D, 0.1D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_Gau001_Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 100.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_Gau003_Space", 5, true);
            Cabin.TuneGAUGE(GAUGE, 80.0D, 280.0D, -55.0D, 55.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Kenworth_T600_rear_br", 0.9300000000000001D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_Gau002_Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 100.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_Gau007_Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_Gau008_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_Gau009_Space", 8, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_Gau013_Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_Gau014_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_Gau005_Space", 14, true);
            Cabin.TuneGAUGE(GAUGE, 10.0D, 18.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_Gau006_Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_Gau011_Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_Gau010_Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_Gau012_Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_Gau015_Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_Gau016_Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_Gau017_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_Gau018_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
        }

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_Wiper1Space", 9, false);

            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 1.23D, -80.730000000000004D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T600_Wiper2Space", 9, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, -0.41D, 87.829999999999998D, 1.0D);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_Front1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_Front2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_Gab", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_CapotLeft1", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_CapotLeft2", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_BumperLeft", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_RamaLeft", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_CapotRight1", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_CapotRight2", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_BumperRight", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_RamaRight", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_Upgrade1_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_Upgrade1_Left", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_Upgrade1_Right", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_Upgrade2_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_Upgrade2_Left", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_Upgrade2_Right", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_Park", 2, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T600_LLight_Stop", 8, true);
        }
    }


    static class KENWORTH_T800init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Inside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_Gauges", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_hnd_br", 9, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_turner_left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_turner_right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_light", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_fwd_br", 16, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_antifriz", 12, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_abs", 15, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_oil_wh_dr", 14, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_oil", 13, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_av", 6, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_podv", 17, true);

            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_GauSpeed_Space", 1, true);

            Cabin.TuneGAUGE(GAUGE, 5.0D, 85.0D, -120.0D, 120.0D, 2.237D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_GauRPM_Space", 2, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 3.0D, -135.0D, 135.0D, 0.06D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_GauFuel_Space", 3, true);
            Cabin.TuneGAUGE(GAUGE, -0.0D, 1.0D, -55.0D, 55.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Kenworth_T800_lamp", 0.0D, 0.1D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_Gau001_Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 100.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_Gau003_Space", 5, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 250.0D, -135.0D, 135.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Kenworth_T800_rear_br", 0.9300000000000001D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_Gau002_Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 100.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_Gau007_Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_Gau008_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_Gau009_Space", 8, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_Gau013_Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_Gau014_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_Gau005_Space", 14, true);
            Cabin.TuneGAUGE(GAUGE, 10.0D, 18.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_Gau006_Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_Gau011_Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_Gau010_Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_Gau012_Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_Gau015_Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_Gau016_Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_Gau017_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_Gau018_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
        }

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_Wiper1Space", 9, false);

            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 0.0D, -71.379999999999995D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_T800_Wiper2Space", 9, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 0.0D, 74.549999999999997D, 1.0D);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_LLightBack", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_LLightGab", 2, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_LLightStop", 8, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_LLightCaseBack01", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_LLightCaseGab01", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_LLightCaseBack02", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_LLightCaseGab02", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_LLightCapotLeft", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_LLightCapotRight", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_LLightCapotFront04", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_LLightCapotFront01", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_LLightCapotFront02", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_LLightCapotFront03", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_LLightBumper01", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_LLightBumper02", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_LLight_RamaLeft", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_LLight_RamaRight", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_LLightCaseGab", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_Upgrade_Lights_LLight_upgrade2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_Upgrade_Lights_LLight_upgrade3", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_T800_Upgrade_Lights_LLight_upgrade4", 1, false);
        }
    }


    static class KENWORTH_W900Linit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Inside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_Gauges", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_hnd_br", 9, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_turner_left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_turner_right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_light", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_fwd_br", 16, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_antifriz", 12, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_abs", 15, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_oil_wh_dr", 14, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_oil", 13, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_av", 6, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_podv", 17, true);

            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_GauSpeed_Space", 1, true);

            Cabin.TuneGAUGE(GAUGE, 5.0D, 85.0D, -120.0D, 120.0D, 2.237D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_GauRPM_Space", 2, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 3.0D, -135.0D, 135.0D, 0.06D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_GauFuel_Space", 3, true);
            Cabin.TuneGAUGE(GAUGE, -0.0D, 1.0D, -55.0D, 55.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Kenworth_W900_lamp", 0.0D, 0.1D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_Gau001_Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 100.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_Gau003_Space", 5, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 250.0D, -135.0D, 135.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Kenworth_W900_rear_br", 0.9300000000000001D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_Gau002_Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 100.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_Gau007_Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_Gau008_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_Gau009_Space", 8, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_Gau013_Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_Gau014_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_Gau005_Space", 14, true);
            Cabin.TuneGAUGE(GAUGE, 10.0D, 18.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_Gau006_Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_Gau011_Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_Gau010_Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_Gau012_Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_Gau015_Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_Gau016_Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_Gau017_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_Gau018_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
        }

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_Wiper1Space", 9, false);

            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 0.0D, -72.650000000000006D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Kenworth_W900_Wiper2Space", 9, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, -0.7D, 71.799999999999997D, 1.0D);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_LLight_Front0", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_LLight_Front1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_LLight_Gab", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_LLight_Spoiler_Kit_2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_LLight_Spoiler_Kit_3", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_LLight_Left", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_LLight_RamaLeft", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_LLight_Right", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_LLight_RamaRight", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_LLight_Park", 2, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_LLight_Upgrade_Kit_1_Bumper", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_LLight_Upgrade_Kit_2_Bumper", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_LLight_Stop", 8, true);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_LLight_Upgrade_Kit_1_Case_Left", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_LLight_Upgrade_Kit_1_Case_Right", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_LLight_Upgrade_Kit_2_Case_Left", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Kenworth_W900_LLight_Upgrade_Kit_2_Case_Right", 1, false);
        }
    }


    static class Lincoln_Town_Carinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_LincolnTownCar_LLight_Front_Far", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_LincolnTownCar_LLight_rot_01", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_LincolnTownCar_LLight_rot_02", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_LincolnTownCar_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_LincolnTownCar_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_LincolnTownCar_LLight_Stop", 8, false);
        }
    }


    static class Livestock_calvesinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Livestock_calves_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_calves_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_calves_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_calves_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_calves_LLight_Gab", 1, false);
        }
    }


    static class Livestock_cowinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Livestock_cow_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_cow_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_cow_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_cow_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_cow_LLight_Gab", 1, false);
        }
    }


    static class Livestock_hogsinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Livestock_hogs_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_hogs_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_hogs_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_hogs_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_hogs_LLight_Gab", 1, false);
        }
    }


    static class Livestock_lambsinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Livestock_lambs_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_lambs_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_lambs_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_lambs_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_lambs_LLight_Gab", 1, false);
        }
    }


    static class Livestock_piginit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Livestock_pig_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_pig_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_pig_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_pig_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_pig_LLight_Gab", 1, false);
        }
    }


    static class Livestock_sheepinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Livestock_sheep_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_sheep_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_sheep_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_sheep_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Livestock_sheep_LLight_Gab", 1, false);
        }
    }


    static class Log_Logsinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Log_Logs_LLightLeft", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Log_Logs_LLightRight", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Log_Logs_LLightBack1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Log_Logs_LLightBack2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Log_Logs_LLightStop1", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Log_Logs_LLightStop2", 8, false);
        }
    }


    static class Log_Pipesinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Log_Pipes_LLightLeft", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Log_Pipes_LLightRight", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Log_Pipes_LLightBack1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Log_Pipes_LLightBack2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Log_Pipes_LLightStop1", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Log_Pipes_LLightStop2", 8, false);
        }
    }


    static class Log_Timbersinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Log_Timber_LLightLeft", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Log_Timber_LLightRight", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Log_Timber_LLightBack1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Log_Timber_LLightBack2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Log_Timber_LLightStop1", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Log_Timber_LLightStop2", 8, false);
        }
    }


    static class Low_boy_Boilerinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Boiler_LLight_rot_01", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Boiler_LLight_rot_02", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Boiler_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Boiler_Light_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Boiler_LLight_Park", 2, false);
        }
    }


    static class Low_boy_Bulldozerinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Bulldozer_LLight_rot_01", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Bulldozer_LLight_rot_02", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Bulldozer_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Bulldozer_Light_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Bulldozer_LLight_Park", 2, false);
        }
    }


    static class Low_boy_Combaininit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Combain_LLight_rot_01", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Combain_LLight_rot_02", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Combain_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Combain_Light_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Combain_LLight_Park", 2, false);
        }
    }


    static class Low_boy_Gas_turbineinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Gas_turbine_LLight_rot_01", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Gas_turbine_LLight_rot_02", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Gas_turbine_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Gas_turbine_Light_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Gas_turbine_LLight_Park", 2, false);
        }
    }


    static class Low_boy_Katokinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Katok_LLight_rot_01", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Katok_LLight_rot_02", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Katok_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Katok_Light_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Katok_LLight_Park", 2, false);
        }
    }


    static class Low_boy_Traktorinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Traktor_LLight_rot_01", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Traktor_LLight_rot_02", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Traktor_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Traktor_Light_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Traktor_LLight_Park", 2, false);
        }
    }


    static class Low_boyinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_LLight_rot_01", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_LLight_rot_02", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_Light_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Low_boy_LLight_Park", 2, false);
        }
    }


    static class Oil_tankinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Oil_tank_LLight_rot_01", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Oil_tank_LLight_rot_02", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Oil_tank_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Oil_tank_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Oil_tank_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Oil_tank_LLight_Gab", 1, false);
        }
    }


    static class PETERBILT_378_CONTAINERinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_container_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_container_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_container_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_container_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_container_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_container_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_container_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_container_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_container_LLight_Gab", 3, false);
        }
    }


    static class PETERBILT_378_DRYVAN_2init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dryvan_2_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dryvan_2_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dryvan_2_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dryvan_2_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dryvan_2_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dryvan_2_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dryvan_2_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dryvan_2_LLight_Gab", 3, false);
        }
    }


    static class PETERBILT_378_DRYVANinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dryvan_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dryvan_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dryvan_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dryvan_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dryvan_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dryvan_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dryvan_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dryvan_LLight_Gab", 3, false);
        }
    }


    static class PETERBILT_378_DUMPinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dump_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dump_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dump_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dump_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dump_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dump_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dump_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_dump_LLight_Gab", 3, false);
        }
    }


    static class PETERBILT_378init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Inside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_Gauges", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_hnd_br", 9, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_turner_left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_turner_right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_light", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_fwd_br", 16, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_antifriz", 12, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_abs", 15, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_oil_wh_dr", 14, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_oil", 13, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_av", 6, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_podv", 17, true);

            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_GauSpeedSpace", 1, true);

            Cabin.TuneGAUGE(GAUGE, 5.0D, 85.0D, -120.0D, 120.0D, 2.237D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_GauRPMSpace", 2, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 3.0D, -135.0D, 135.0D, 0.06D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_GauFuelSpace", 3, true);
            Cabin.TuneGAUGE(GAUGE, -0.0D, 1.0D, -55.0D, 55.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Peterbilt_378_lamp", 0.0D, 0.1D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Gau001Space", 0, true);
            Cabin.TuneGAUGE(GAUGE, 8.0D, 18.0D, -45.0D, 45.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Gau002Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 100.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Gau004Space", 5, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 250.0D, -135.0D, 135.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Peterbilt_378_rear_br", 0.9300000000000001D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Gau003Space", 14, true);
            Cabin.TuneGAUGE(GAUGE, 10.0D, 18.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Gau005Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Gau006Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Gau007Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Gau008Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Gau009Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Gau010Space", 8, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Gau011Space", 3, true);
            Cabin.TuneGAUGE(GAUGE, -0.0D, 1.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Gau012Space", 5, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 250.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Gau013Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 100.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Gau014Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Gau015Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Gau016Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Gau017Space", 0, true);
            Cabin.TuneGAUGE(GAUGE, 8.0D, 18.0D, -45.0D, 45.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Gau018Space", 14, true);
            Cabin.TuneGAUGE(GAUGE, 10.0D, 18.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Gau019Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
        }

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Wiper1Space", 9, false);

            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 73.180000000000007D, -24.620000000000001D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_378_Wiper2Space", 9, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 66.700000000000003D, -25.289999999999999D, 1.0D);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_Capot_Front1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_Capot_Front2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_Capot_Left", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_RamaLeft", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_Capot_Right", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_RamaRight", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_Upgrade_1_1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_Upgrade_1_2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_Upgrade_2_1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_Upgrade_2_2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_Upgrade_2_3", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_Upgrade_2_4", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_Upgrade_3_1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_Upgrade_3_2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_Upgrade_3_3", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_Case_Gab", 3, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_Stop", 8, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_Park", 2, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_378_LLight_Case_Park", 2, true);
        }
    }


    static class PETERBILT_379_WRECKERinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Inside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_Gauges", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_hnd_br", 9, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_turner_left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_turner_right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_light", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_fwd_br", 16, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_antifriz", 12, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_abs", 15, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_oil_wh_dr", 14, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_oil", 13, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_av", 6, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_podv", 17, true);

            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_GauSpeedSpace", 1, true);

            Cabin.TuneGAUGE(GAUGE, 0.0D, 120.0D, -120.0D, 120.0D, 2.237D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_GauRPMSpace", 2, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 4.0D, -135.0D, 135.0D, 0.06D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_GauFuelSpace", 3, true);
            Cabin.TuneGAUGE(GAUGE, -0.0D, 1.0D, -45.0D, 45.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Peterbilt_379_Wrecker_lamp", 0.0D, 0.1D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_Gau011Space", 3, true);
            Cabin.TuneGAUGE(GAUGE, -0.25D, 1.0D, -45.0D, 45.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_Gau002Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 138.0D, 320.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_Gau004Space", 5, true);
            Cabin.TuneGAUGE(GAUGE, 138.0D, 320.0D, -55.0D, 55.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Peterbilt_379_Wrecker_rear_br", 0.9300000000000001D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_Gau012Space", 5, true);
            Cabin.TuneGAUGE(GAUGE, 80.0D, 280.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_Gau013Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, -10.0D, 110.0D, -45.0D, 45.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_Gau008Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, -10.0D, 110.0D, -45.0D, 45.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_Gau009Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, -10.0D, 110.0D, -45.0D, 45.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_Gau010Space", 8, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 120.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_Gau019Space", 8, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 120.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_Gau003Space", 14, true);
            Cabin.TuneGAUGE(GAUGE, 8.0D, 18.0D, -45.0D, 45.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_Gau018Space", 14, true);
            Cabin.TuneGAUGE(GAUGE, 8.0D, 18.0D, -45.0D, 45.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_Gau007Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 140.0D, 320.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_Gau014Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 140.0D, 320.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_Gau005Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 140.0D, 320.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_Gau016Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 140.0D, 320.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_Gau006Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 140.0D, 320.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_Gau015Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 140.0D, 320.0D, -55.0D, 55.0D, 1.0D);
        }

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_Wiper1Space", 9, false);

            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 80.0D, -19.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wrecker_Wiper2Space", 9, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 75.0D, -18.0D, 1.0D);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_LLight_Capot_Front1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_LLight_Capot_Front2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_LLight_Capot_Left", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_LLight_Capot_Right", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_LLight_Case_Gab", 3, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_LLight_Case_Gab1", 3, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_LLight_Case_Gab2", 3, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_LLight_Stop", 8, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_LLight_Park", 2, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Wrecker_LLight_Case_Park", 2, false);
        }
    }


    static class PETERBILT_379init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Inside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_Gauges", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_hnd_br", 9, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_turner_left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_turner_right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_light", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_fwd_br", 16, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_antifriz", 12, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_abs", 15, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_oil_wh_dr", 14, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_oil", 13, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_av", 6, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_podv", 17, true);

            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_GauSpeedSpace", 1, true);

            Cabin.TuneGAUGE(GAUGE, 5.0D, 85.0D, -120.0D, 120.0D, 2.237D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_GauRPMSpace", 2, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 3.0D, -135.0D, 135.0D, 0.06D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_GauFuelSpace", 3, true);
            Cabin.TuneGAUGE(GAUGE, -0.0D, 1.0D, -55.0D, 55.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Peterbilt_378_lamp", 0.0D, 0.1D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Gau001Space", 0, true);
            Cabin.TuneGAUGE(GAUGE, 8.0D, 18.0D, -45.0D, 45.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Gau002Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 100.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Gau004Space", 5, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 250.0D, -135.0D, 135.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Peterbilt_378_rear_br", 0.9300000000000001D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Gau003Space", 14, true);
            Cabin.TuneGAUGE(GAUGE, 10.0D, 18.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Gau005Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Gau006Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Gau007Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Gau008Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Gau009Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Gau010Space", 8, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Gau011Space", 3, true);
            Cabin.TuneGAUGE(GAUGE, -0.0D, 1.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Gau012Space", 5, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 250.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Gau013Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 100.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Gau014Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Gau015Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Gau016Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Gau017Space", 0, true);
            Cabin.TuneGAUGE(GAUGE, 8.0D, 18.0D, -45.0D, 45.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Gau018Space", 14, true);
            Cabin.TuneGAUGE(GAUGE, 10.0D, 18.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Gau019Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
        }

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wiper1Space", 9, false);

            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 80.480000000000004D, -16.920000000000002D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_379_Wiper2Space", 9, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 75.420000000000002D, -20.800000000000001D, 1.0D);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_LLight_Capot_Front1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_LLight_Capot_FrontFar1", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_LLight_Capot_Front2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_LLight_Capot_FrontFar2", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_LLight_Stop", 8, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_LLight_Park", 2, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_LLight_Capot_Left", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_LLight_RamaLeft", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_LLight_Capot_Right", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_LLight_RamaRight", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_LLight_Case_Gab", 3, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_LLight_Case_GabL", 3, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_LLight_Case_GabR", 3, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_LLight_Upgrade1_Left", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_LLight_Upgrade1_Right", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_LLight_Upgrade2_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_LLight_Upgrade2_Left", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_379_LLight_Upgrade2_Right", 1, false);
        }
    }


    static class PETERBILT_387init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Inside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_Gauges", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_hnd_br", 9, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_turner_left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_turner_right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_light", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_fwd_br", 16, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_antifriz", 12, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_abs", 15, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_oil_wh_dr", 14, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_oil", 13, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_av", 6, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_podv", 17, true);

            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_387_GauSpeedSpace", 1, true);

            Cabin.TuneGAUGE(GAUGE, 5.0D, 85.0D, -120.0D, 120.0D, 2.237D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_387_GauRPMSpace", 2, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 3.0D, -135.0D, 135.0D, 0.06D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_387_GauFuelSpace", 3, true);
            Cabin.TuneGAUGE(GAUGE, -0.0D, 1.0D, -55.0D, 55.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Peterbilt_387_lamp", 0.0D, 0.1D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_387_Gau001Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 100.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_387_Gau003Space", 5, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 250.0D, -135.0D, 135.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Peterbilt_387_rear_br", 0.9300000000000001D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_387_Gau008Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_387_Gau010Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_387_Gau012Space", 8, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_387_Gau007Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_387_Gau002Space", 14, true);
            Cabin.TuneGAUGE(GAUGE, 10.0D, 18.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_387_Gau004Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_387_Gau013Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_387_Gau005Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_387_Gau009Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_387_Gau011Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_387_Gau006Space", 0, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
        }

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_387_Wiper1Space", 9, false);

            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 74.930000000000007D, -39.049999999999997D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Peterbilt_387_Wiper2Space", 9, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 70.719999999999999D, -40.140000000000001D, 1.0D);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_LLight_Capot_Front1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_LLight_Capot_Front2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_LLight_Capot_FrontFar1", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_LLight_Capot_FrontFar2", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_LLight_Capot_Left", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_LLight_RamaLeft", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_LLight_Capot_Right", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_LLight_RamaRight", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_LLight_Case_Left", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_LLight_Case_Right", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_LLight_Case_Gab", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_LLight_Park", 2, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_LLight_Stop", 8, true);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_LLight_Upgrade1_1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_LLight_Upgrade1_2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_LLight_Upgrade2_1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Peterbilt_387_LLight_Upgrade2_2", 1, false);
        }
    }


    static class STERLING_9500init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Inside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_Gauges", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_hnd_br", 9, true);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_turner_left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_turner_right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_light", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_fwd_br", 16, true);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_antifriz", 12, true);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_abs", 15, true);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_oil_wh_dr", 14, true);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_oil", 13, true);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_av", 6, false);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_podv", 17, true);

            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Sterling_9500_GauSpeed_Space", 1, true);

            Cabin.TuneGAUGE(GAUGE, 5.0D, 85.0D, -120.0D, 120.0D, 2.237D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Sterling_9500_GauRPM_Space", 2, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 3.0D, -135.0D, 135.0D, 0.06D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Sterling_9500_GauFuel_Space", 3, true);
            Cabin.TuneGAUGE(GAUGE, -0.0D, 1.0D, -55.0D, 55.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Sterling_9500_lamp", 0.0D, 0.1D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Sterling_9500_Gau001_Space", 14, true);
            Cabin.TuneGAUGE(GAUGE, 10.0D, 18.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Sterling_9500_Gau002_Space", 5, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 250.0D, -135.0D, 135.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_Sterling_9500_rear_br", 0.9300000000000001D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Sterling_9500_Gau003_Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 100.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Sterling_9500_Gau004_Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Sterling_9500_Gau006_Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Sterling_9500_Gau005_Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Sterling_9500_Gau007_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Sterling_9500_Gau008_Space", 8, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Sterling_9500_Gau010_Space", 8, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Sterling_9500_Gau009_Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Sterling_9500_Gau011_Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
        }

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            long GAUGE = Cabin.CreateGAUGE(cabin_np, "Sterling_9500_Wiper1Space", 9, false);

            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 76.5D, -25.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "Sterling_9500_Wiper2Space", 9, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 76.0D, -32.5D, 1.0D);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_LLight_Park", 2, true);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_LLight_Stop", 8, true);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_LLight_Case_Gab1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_LLight_Case_Gab2", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_LLight_Case_Gab3", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_LLight", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_LLight_Capot_Left1", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_LLight_RamaLeft", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_LLight_Capot_Left2", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_LLight_Capot_Right1", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_LLight_RamaRight", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_LLight_Capot_Right2", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_LLight_Capot_Front1", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Sterling_9500_LLight_Capot_Front2", 1, false);
        }
    }


    static class Toyota_Camryinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Toyota_Camry_LLight_Front_Far", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Toyota_Camry_LLight_rot_01", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Toyota_Camry_LLight_rot_02", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Toyota_Camry_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Toyota_Camry_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Toyota_Camry_LLight_Stop", 8, false);
        }
    }


    static class VW_NewBeatleinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_VW_NewBeatle_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_VW_NewBeatle_LLight_rot_01", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_VW_NewBeatle_LLight_rot_02", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_VW_NewBeatle_LLight_rot_03", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_VW_NewBeatle_LLight_rot_04", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_VW_NewBeatle_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_VW_NewBeatle_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_VW_NewBeatle_LLight_Stop", 8, false);
        }
    }


    static class WESTERN_STAR4900_EXinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Inside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_Gauges", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_hnd_br", 9, true);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_turner_left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_turner_right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_light", 18, false);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_fwd_br", 16, true);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_antifriz", 12, true);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_abs", 15, true);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_oil_wh_dr", 14, true);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_oil", 13, true);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_av", 6, false);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_podv", 17, true);

            long GAUGE = Cabin.CreateGAUGE(cabin_np, "WesternStar_4900_GauSpeed_Space", 1, true);

            Cabin.TuneGAUGE(GAUGE, 5.0D, 85.0D, -120.0D, 120.0D, 2.237D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "WesternStar_4900_GauRPM_Space", 2, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 3.0D, -135.0D, 135.0D, 0.06D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "WesternStar_4900_GauFuel_Space", 3, true);
            Cabin.TuneGAUGE(GAUGE, -0.0D, 1.0D, -55.0D, 55.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "WesternStar_4900_lamp", 0.0D, 0.1D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "WesternStar_4900_Gau001_Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 100.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "WesternStar_4900_Gau002_Space", 6, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 100.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "WesternStar_4900_Gau003_Space", 5, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 250.0D, -135.0D, 135.0D, 1.0D);
            Cabin.AddControlLight(GAUGE, "Dword_WesternStar_4900_rear_br", 0.9300000000000001D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "WesternStar_4900_Gau006_Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "WesternStar_4900_Gau007_Space", 10, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "WesternStar_4900_Gau008_Space", 8, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "WesternStar_4900_Gau012_Space", 7, true);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 150.0D, -135.0D, 135.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "WesternStar_4900_Gau004_Space", 14, true);
            Cabin.TuneGAUGE(GAUGE, 10.0D, 18.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "WesternStar_4900_Gau005_Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "WesternStar_4900_Gau010_Space", 4, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "WesternStar_4900_Gau009_Space", 12, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "WesternStar_4900_Gau011_Space", 11, true);
            Cabin.TuneGAUGE(GAUGE, 100.0D, 300.0D, -55.0D, 55.0D, 1.0D);
        }

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            long GAUGE = Cabin.CreateGAUGE(cabin_np, "WesternStar_4900_Wiper1Space", 9, false);

            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 88.370000000000005D, 0.0D, 1.0D);
            GAUGE = Cabin.CreateGAUGE(cabin_np, "WesternStar_4900_Wiper2Space", 9, false);
            Cabin.TuneGAUGE(GAUGE, 0.0D, 1.0D, 90.230000000000004D, 4.4D, 1.0D);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_LLight_Stop", 8, true);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_LLight_CaseBack", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_LLight_Park", 2, true);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_LLight_RamaRight", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_LLight_Right", 5, true);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_LLight_Left", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_LLight_RamaLeft", 4, true);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_LLight_UpGab", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_LLight_Bumper", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_LLight_Front02", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_LLight_Front01", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_LLight_CaseGab01", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_LLight_AirFilter02", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_WesternStar_4900_LLight_AirFilter01", 1, false);
        }
    }


    static class WORK_TRUCKinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Work_truck_LLight_Front", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Work_truck_LLight_Left1", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Work_truck_LLight_Left2", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Work_truck_LLight_Right1", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Work_truck_LLight_Right2", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Work_truck_LLight_Park", 2, false);
            Cabin.CreateLight(cabin_np, "Dword_Work_truck_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Work_truck_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Work_truck_LLight_Gab", 3, false);
        }
    }


    static class flat_bed_cargo1init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_cargo1_LLight_rot_01", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_cargo1_LLight_rot_02", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_cargo1_LLight_Gab", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_cargo1_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_cargo1_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_cargo1_LLight_Park", 2, false);
        }
    }


    static class flat_bed_cargo2init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_cargo2_LLight_rot_01", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_cargo2_LLight_rot_02", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_cargo2_LLight_Gab", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_cargo2_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_cargo2_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_cargo2_LLight_Park", 2, false);
        }
    }


    static class flat_bed_cargo3init {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_cargo3_LLight_rot_01", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_cargo3_LLight_rot_02", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_cargo3_LLight_Gab", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_cargo3_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_cargo3_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_cargo3_LLight_Park", 2, false);
        }
    }


    static class flat_bed_hayinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_Hay_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_Hay_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_Hay_LLight_Gab", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_Hay_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_Hay_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_Hay_LLight_Park", 2, false);
        }
    }


    static class flat_bed_sheetsinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_Sheets_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_Sheets_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_Sheets_LLight_Gab", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_Sheets_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_Sheets_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_Sheets_LLight_Park", 2, false);
        }
    }


    static class flat_bed_wireinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_Wire_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_Wire_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_Wire_LLight_Gab", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_Wire_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_Wire_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Flat_bed_Wire_LLight_Park", 2, false);
        }
    }


    static class refrigeratorinit {

        /**
         * Method description
         *
         *
         * @param cabin_np
         */
        public static void Outside(long cabin_np) {
            Cabin.CreateLight(cabin_np, "Dword_Refrigerator_LLight_Left", 4, false);
            Cabin.CreateLight(cabin_np, "Dword_Refrigerator_LLight_Right", 5, false);
            Cabin.CreateLight(cabin_np, "Dword_Refrigerator_LLight_Back", 1, false);
            Cabin.CreateLight(cabin_np, "Dword_Refrigerator_LLight_Stop", 8, false);
            Cabin.CreateLight(cabin_np, "Dword_Refrigerator_LLight_Gab", 1, false);
        }
    }
}


//~ Formatted in DD Std on 13/08/26
