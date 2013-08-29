/*
 * @(#)JournalPane.java   13/08/26
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


package rnr.src.menuscript.org;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.menu.MENUBase_Line;
import rnr.src.menu.MENU_ranger;
import rnr.src.menu.MENUbutton_field;
import rnr.src.menu.MENUsimplebutton_field;
import rnr.src.menu.menues;
import rnr.src.menuscript.Converts;
import rnr.src.menuscript.IYesNoCancelMenuListener;
import rnr.src.menuscript.YesNoCancelMenu;
import rnr.src.menuscript.table.IRangerListener;
import rnr.src.menuscript.table.Table;
import rnr.src.menuscript.tablewrapper.TableData;
import rnr.src.menuscript.tablewrapper.TableLine;
import rnr.src.menuscript.tablewrapper.TableWrapped;
import rnr.src.rnrcore.CoreTime;
import rnr.src.rnrorg.journable;
import rnr.src.rnrorg.journal;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public class JournalPane implements IOrgTab {
    private static final String XML = "..\\data\\config\\menu\\menu_com.xml";
    private static final String TABLE = "TABLEGROUP JOURNAL - 6 112";
    private static final String RANGER = "Tableranger - JOURNAL - list";
    private static final String LINE = "Tablegroup - ELEMENTS - JOURNAL";
    private static final String[] LINE_ELEMENTS = {
        "Journal - MessageDate READ", "Journal - Message READ", "Journal - Message READ - pic",
        "Journal - MessageDate UNREAD", "Journal - Message UNREAD", "Journal - Message UNREAD - pic",
        "Tableranger - Journal - Message - PIC FieldBlack", "Tableranger - Journal - Message - PIC BorderDark01",
        "Tableranger - Journal - Message - PIC BorderDark02", "Tableranger - Journal - Message - PIC BorderDark03",
        "Tableranger - Journal - Message"
    };
    private static final boolean[] LINE_ELEMENTS_RADIO = {
        true, true, true, true, true, true, false, false, false, false, false
    };
    private static final int LINE_READ_DATE = 0;
    private static final int LINE_READ_MESSAGE = 1;
    private static final int LINE_READ_PIC = 2;
    private static final int LINE_UNREAD_DATE = 3;
    private static final int LINE_UNREAD_MESSAGE = 4;
    private static final int LINE_UNREAD_PIC = 5;
    private static final int LINE_RANGER_GROUP0 = 6;
    private static final int LINE_RANGER_GROUP1 = 7;
    private static final int LINE_RANGER_GROUP2 = 8;
    private static final int LINE_RANGER_GROUP3 = 9;
    private static final int LINE_RANGER = 10;
    private static final String CALENDAR_TABLE = "TABLEGROUP JOURNAL DATE - 21 72";
    private static final String CALENDAR_LINE = "Tablegroup - ELEMENTS - JOURNAL DATE";
    private static final String[] CALENDAR_LINE_ELEMENTS = {
        "button - JOURNAL DATE - Messages NO", "button - JOURNAL DATE - Messages READ",
        "button - JOURNAL DATE - Messages UNREAD", "button - JOURNAL DATE - TODAY Messages NO",
        "button - JOURNAL DATE - TODAY Messages READ", "button - JOURNAL DATE - TODAY Messages UNREAD"
    };
    private static final int CALENDAR_LINE_NOMESSAGES = 0;
    private static final int CALENDAR_LINE_MESS_READ = 1;
    private static final int CALENDAR_LINE_MESS_UNREAD = 2;
    private static final int CALENDAR_LINE_TODAY_NO = 3;
    private static final int CALENDAR_LINE_TODAY_READ = 4;
    private static final int CALENDAR_LINE_TODAY_UNREAD = 5;
    private static final String[] MONTHES = { "Month01", "Month02", "Month03" };
    private static final String[] ACTIONS_BUTTONS = { "JOURNAL DATE - Button - GO TO TODAY",
            "JOURNAL DATE - Button - LEFT", "JOURNAL DATE - Button - RIGHT" };
    private static final String[] ACTIONS_METHODS = { "onCurrent", "onLeft", "onRight" };
    private static final String[] POINTERS = { "Messages UNREAD - Pointer UP", "Messages UNREAD - Pointer DOWN" };
    private static final int POINTER_UP = 0;
    private static final int POINTER_DOWN = 1;
    private static final String QUESTION_GROUP = "MESSAGE - JOURNAL - Add to organizer";
    private static final String QUESTION_WINDOW = "MESSAGE - JOURNAL - Add to organizer";
    private final boolean DEBUG = OrganiserMenu.DEBUG;
    OrganiserMenu parent = null;
    private final int[] decades = { 0, 7, 14 };
    private final boolean is_empty_table = true;
    private final CoreTime startTime = new CoreTime();
    private CoreTime selectedTime = new CoreTime();
    private final CoreTime currentTime = new CoreTime();
    private final boolean can_select = true;
    private long[] pointers = null;
    private boolean f_onEnteringFocus = false;
    private boolean f_onFocus = false;
    private final JournalTable journal_table;
    private final Calendar calendar_table;
    private final YesNoCancelMenu question;
    private JournalLine underQuestion;
    int linescreen_unread;
    int line_height_unread;
    int linescreen_read;
    int line_height_read;
    MENU_ranger[] rangers;
    long[] id_rangers_control_group;
    long[] id_textFieldUnread;
    MENUBase_Line baseline_unread;
    long[] id_textFieldRead;
    MENUBase_Line baseline_read;
    long _menu;

    /**
     * Constructs ...
     *
     *
     * @param _menu
     * @param parent
     */
    public JournalPane(long _menu, OrganiserMenu parent) {
        this.parent = parent;
        this.journal_table = new JournalTable(_menu);
        this.calendar_table = new Calendar(_menu);

        for (int i = 0; i < ACTIONS_BUTTONS.length; ++i) {
            long button = menues.FindFieldInMenu(_menu, ACTIONS_BUTTONS[i]);

            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(button), this, ACTIONS_METHODS[i], 4L);
        }

        this.pointers = new long[POINTERS.length];

        for (int i = 0; i < POINTERS.length; ++i) {
            this.pointers[i] = menues.FindFieldInMenu(_menu, POINTERS[i]);
        }

        this.question = new YesNoCancelMenu(_menu, "..\\data\\config\\menu\\menu_com.xml",
                "MESSAGE - JOURNAL - Add to organizer", "MESSAGE - JOURNAL - Add to organizer");
        this.question.addListener(new ListenQuestionAnswer());

        long control = menues.FindFieldInMenu(_menu, "CALL COMMUNICATOR HELP - JOURNAL");

        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "ShowHelp", 4L);
    }

    private boolean canShowQuestion() {
        return ((!(this.f_onEnteringFocus)) && (this.f_onFocus));
    }

    /**
     * Method description
     *
     */
    @Override
    public void afterInit() {
        this.question.afterInit();

        for (long p : this.pointers) {
            menues.SetShowField(p, false);
        }

        this.journal_table.afterInit();
        this.calendar_table.afterInit();
        this.calendar_table.updateMonthes();
    }

    /**
     * Method description
     *
     */
    @Override
    public void exitMenu() {
        this.journal_table.deinit();
        this.calendar_table.deinit();
    }

    /**
     * Method description
     *
     */
    @Override
    public void enterFocus() {
        this.f_onEnteringFocus = true;
        this.journal_table.update();
        this.calendar_table.update();
        this.f_onEnteringFocus = false;
        this.f_onFocus = true;
    }

    /**
     * Method description
     *
     */
    @Override
    public void leaveFocus() {
        this.f_onFocus = false;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onCurrent(long _menu, MENUsimplebutton_field button) {
        this.selectedTime = this.currentTime;
        this.journal_table.selectDate();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onLeft(long _menu, MENUsimplebutton_field button) {
        this.calendar_table.moveCurrentOnDays(-7);
        this.calendar_table.update();
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void onRight(long _menu, MENUsimplebutton_field button) {
        this.calendar_table.moveCurrentOnDays(7);
        this.calendar_table.update();
    }

    private ArrayList<JournalLine> getAllLines() {
        ArrayList res = new ArrayList();
        int size = journal.getInstance().journalSize();

        for (int i = 0; i < size; ++i) {
            journable jou = journal.getInstance().get(i);
            JournalLine elem = new JournalLine();

            elem.jou = jou;
            elem.message = jou.description();
            elem.date = jou.getTime();
            elem.is_read = (!(jou.isQuestion()));
            elem.text_high = 0;

            if (elem.is_read) {
                elem.text_high = (((this.id_textFieldRead != null) && (this.id_textFieldRead[0] != 0L))
                                  ? Converts.HeightToLines(menues.GetTextHeight(this.id_textFieldRead[0],
                                  elem.message), this.baseline_read.GetMinBaseLine(), this.line_height_read)
                                  : 0);
            } else {
                elem.text_high = (((this.id_textFieldUnread != null) && (this.id_textFieldUnread[0] != 0L))
                                  ? Converts.HeightToLines(menues.GetTextHeight(this.id_textFieldUnread[0],
                                  elem.message), this.baseline_unread.GetMinBaseLine(), this.line_height_unread)
                                  : 0);
            }

            elem.ranger_cur_value = 0;
            res.add(elem);
        }

        return res;
    }

    private ArrayList<CalendarLine> getCalendar() {
        ArrayList res = new ArrayList();

        for (int i = 0; i < 21; ++i) {
            CalendarLine line = new CalendarLine();

            line.date = new CoreTime(this.startTime);
            line.date.plus_days(i);

            JournalCalendarFilling data_fill = this.journal_table.findCalendarFilling(line.date);

            line.has_messages = (data_fill != null);
            line.is_read = (!(data_fill.has_unread_messages));
            line.is_today = CoreTime.isSameDate(this.currentTime, line.date);
            res.add(line);
        }

        return res;
    }

    /**
     * Method description
     *
     *
     * @param _menu
     * @param button
     */
    public void ShowHelp(long _menu, MENUsimplebutton_field button) {
        if (this.parent != null) {
            this.parent.ShowTabHelp(1);
        }
    }

    class Calendar extends TableWrapped {
        private long[] decadeFields = null;
        private String[] decadeFields_text = null;
        private JournalPane.CalendarLine selected_line = null;
        private CoreTime[] monthes;
        private ArrayList<JournalPane.CalendarLine> data_calendar;

        Calendar(long _menu) {
            super(_menu, 1, 0, false, "..\\data\\config\\menu\\menu_com.xml", "TABLEGROUP JOURNAL DATE - 21 72", null,
                  "Tablegroup - ELEMENTS - JOURNAL DATE", JournalPane.CALENDAR_LINE_ELEMENTS, null);
            this.decadeFields = new long[JournalPane.MONTHES.length];
            this.decadeFields_text = new String[JournalPane.MONTHES.length];

            for (int i = 0; i < this.decadeFields.length; ++i) {
                this.decadeFields[i] = menues.FindFieldInMenu(_menu, JournalPane.access$1800()[i]);
                this.decadeFields_text[i] = null;
            }

            if (null == this.monthes) {
                this.monthes = new CoreTime[JournalPane.this.decades.length];
            }
        }

        private void updateMonth(int num_field, CoreTime date) {
            if (this.decadeFields_text[num_field] == null) {
                this.decadeFields_text[num_field] = menues.GetFieldText(this.decadeFields[num_field]);
            }

            menues.SetFieldText(this.decadeFields[num_field],
                                Converts.ConvertDate(this.decadeFields_text[num_field], date.gMonth(), date.gYear()));
            menues.UpdateMenuField(menues.ConvertMenuFields(this.decadeFields[num_field]));
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onSort(long _menu, MENUsimplebutton_field button) {}

        @Override
        protected void reciveTableData() {
            this.data_calendar = JournalPane.this.getCalendar();
            this.selected_line = null;

            for (JournalPane.CalendarLine line : this.data_calendar) {
                if (CoreTime.isSameDate(line.date, JournalPane.this.selectedTime)) {
                    this.selected_line = line;
                }
            }

            if (null == this.monthes) {
                this.monthes = new CoreTime[JournalPane.this.decades.length];
            }

            for (int i = 0; i < JournalPane.this.decades.length; ++i) {
                this.monthes[i] = ((JournalPane.CalendarLine) this.data_calendar.get(
                    JournalPane.access$1900(JournalPane.this)[i])).date;
            }

            this.TABLE_DATA.all_lines.addAll(this.data_calendar);
        }

        /**
         * Method description
         *
         *
         * @param button
         * @param position
         * @param table_node
         */
        @Override
        public void SetupLineInTable(long button, int position, TableLine table_node) {
            JournalPane.CalendarLine line = (JournalPane.CalendarLine) table_node;

            switch (position) {
             case 0 :
                 menues.SetFieldText(button, "" + line.date.gDate());
                 menues.SetShowField(button, (!(line.is_today)) && (!(line.has_messages)));

                 break;

             case 1 :
                 menues.SetShowField(button, (!(line.is_today)) && (line.has_messages) && (line.is_read));
                 menues.SetFieldText(button, "" + line.date.gDate());

                 break;

             case 2 :
                 menues.SetShowField(button, (!(line.is_today)) && (line.has_messages) && (!(line.is_read)));
                 menues.SetFieldText(button, "" + line.date.gDate());

                 break;

             case 3 :
                 menues.SetShowField(button, (line.is_today) && (!(line.has_messages)));
                 menues.SetFieldText(button, "" + line.date.gDate());

                 break;

             case 4 :
                 menues.SetShowField(button, (line.is_today) && (line.has_messages) && (line.is_read));
                 menues.SetFieldText(button, "" + line.date.gDate());

                 break;

             case 5 :
                 menues.SetShowField(button, (line.is_today) && (line.has_messages) && (!(line.is_read)));
                 menues.SetFieldText(button, "" + line.date.gDate());
            }

            menues.UpdateMenuField(menues.ConvertMenuFields(button));
        }

        /**
         * Method description
         *
         *
         * @param linedata
         */
        @Override
        public void updateSelectedInfo(TableLine linedata) {
            if (!(JournalPane.this.can_select)) {
                return;
            }

            JournalPane.access$1302(JournalPane.this, false);

            JournalPane.CalendarLine line = (JournalPane.CalendarLine) linedata;
            JournalPane.JournalCalendarFilling data_fill =
                JournalPane.JournalTable.access$400(JournalPane.this.journal_table, line.date);

            if ((null == data_fill) || (null == data_fill.line)) {
                JournalPane.access$1302(JournalPane.this, true);

                return;
            }

            JournalPane.JournalTable.access$2100(JournalPane.this.journal_table, data_fill.line);
            JournalPane.access$1302(JournalPane.this, true);
        }

        /**
         * Method description
         *
         *
         * @param table
         */
        public void enterFocus(Table table) {}

        /**
         * Method description
         *
         *
         * @param table
         */
        public void leaveFocus(Table table) {}

        /**
         * Method description
         *
         */
        public void updateMonthes() {
            for (int i = 0; i < this.monthes.length; ++i) {
                updateMonth(i, this.monthes[i]);
            }
        }

        /**
         * Method description
         *
         *
         * @param days
         */
        public void moveCurrentOnDays(int days) {
            if (days > 0) {
                JournalPane.this.startTime.plus_days(days);
            } else {
                JournalPane.this.startTime.minusDays(-days);
            }
        }

        /**
         * Method description
         *
         */
        public void moveToSelectedTime() {
            if (JournalPane.this.startTime.moreThan(JournalPane.this.selectedTime) == 0) {
                return;
            }

            if (JournalPane.this.startTime.moreThan(JournalPane.this.selectedTime) > 0) {
                moveCurrentOnDays(-7);
                moveToSelectedTime();
            } else if (CoreTime.CompareByDays(JournalPane.this.selectedTime, JournalPane.this.startTime) >= 21) {
                moveCurrentOnDays(7);
                moveToSelectedTime();
            }
        }

        /**
         * Method description
         *
         */
        public void update() {
            updateTable();
            updateMonthes();

            if (null != this.selected_line) {
                this.table.select_line_by_data(this.selected_line);
            } else {
                this.table.deselectAll();
            }
        }

        /**
         * Method description
         *
         */
        @Override
        public void redrawTable() {
            for (JournalPane.CalendarLine line : this.data_calendar) {
                JournalPane.JournalCalendarFilling data_fill =
                    JournalPane.JournalTable.access$400(JournalPane.this.journal_table, line.date);

                line.has_messages = (data_fill != null);
                line.is_read = (!(data_fill.has_unread_messages));
            }

            super.redrawTable();
        }

        /**
         * Method description
         *
         */
        public void deinit() {
            this.table.deinit();
        }
    }


    static class CalendarLine extends TableLine {
        boolean has_messages;
        boolean is_read;
        boolean is_today;
        CoreTime date;

        CalendarLine() {
            this.has_messages = false;
            this.is_read = false;
            this.is_today = false;
            this.date = null;
        }
    }


    static class JournalCalendarDateFilling {
        HashMap<Integer, JournalPane.JournalCalendarFilling> fill;

        JournalCalendarDateFilling() {
            this.fill = new HashMap();
        }

        JournalPane.JournalCalendarFilling find(CoreTime time) {
            if (!(this.fill.containsKey(Integer.valueOf(time.gDate())))) {
                return null;
            }

            JournalPane.JournalCalendarFilling data = this.fill.get(Integer.valueOf(time.gDate()));

            return data;
        }

        void add(JournalPane.JournalLine data) {
            JournalPane.JournalCalendarFilling date = null;

            if (!(this.fill.containsKey(Integer.valueOf(data.date.gDate())))) {
                date = new JournalPane.JournalCalendarFilling();
                this.fill.put(Integer.valueOf(data.date.gDate()), date);
            } else {
                date = this.fill.get(Integer.valueOf(data.date.gDate()));
            }

            date.add(data);
        }
    }


    static class JournalCalendarFilling {
        boolean has_unread_messages;
        JournalPane.JournalLine line;
        ArrayList<JournalPane.JournalLine> all_data;

        JournalCalendarFilling() {
            this.has_unread_messages = false;
            this.all_data = new ArrayList();
        }

        void add(JournalPane.JournalLine data) {
            this.all_data.add(data);
            this.has_unread_messages = ((this.has_unread_messages) || (!(data.is_read)));

            if (this.line == null) {
                this.line = data;
            } else if (this.line.date.moreThan(data.date) > 0) {
                this.line = data;
            }
        }

        private void refresh() {
            this.has_unread_messages = false;

            for (JournalPane.JournalLine line : this.all_data) {
                if (!(line.is_read)) {
                    this.has_unread_messages = true;

                    return;
                }
            }
        }
    }


    static class JournalCalendarMonthFilling {
        HashMap<Integer, JournalPane.JournalCalendarDateFilling> fill;

        JournalCalendarMonthFilling() {
            this.fill = new HashMap();
        }

        JournalPane.JournalCalendarFilling find(CoreTime time) {
            if (!(this.fill.containsKey(Integer.valueOf(time.gMonth())))) {
                return null;
            }

            JournalPane.JournalCalendarDateFilling date = this.fill.get(Integer.valueOf(time.gMonth()));

            return date.find(time);
        }

        void add(JournalPane.JournalLine data) {
            JournalPane.JournalCalendarDateFilling date = null;

            if (!(this.fill.containsKey(Integer.valueOf(data.date.gMonth())))) {
                date = new JournalPane.JournalCalendarDateFilling();
                this.fill.put(Integer.valueOf(data.date.gMonth()), date);
            } else {
                date = this.fill.get(Integer.valueOf(data.date.gMonth()));
            }

            date.add(data);
        }
    }


    static class JournalCalendarYearFilling {
        HashMap<Integer, JournalPane.JournalCalendarMonthFilling> fill = new HashMap();

        JournalCalendarYearFilling(ArrayList<JournalPane.JournalLine> lines) {
            if (null == lines) {
                return;
            }

            for (JournalPane.JournalLine line : lines) {
                add(line);
            }
        }

        JournalPane.JournalCalendarFilling find(CoreTime time) {
            if (!(this.fill.containsKey(Integer.valueOf(time.gYear())))) {
                return null;
            }

            JournalPane.JournalCalendarMonthFilling month = this.fill.get(Integer.valueOf(time.gYear()));

            return month.find(time);
        }

        void add(JournalPane.JournalLine data) {
            JournalPane.JournalCalendarMonthFilling month = null;

            if (!(this.fill.containsKey(Integer.valueOf(data.date.gYear())))) {
                month = new JournalPane.JournalCalendarMonthFilling();
                this.fill.put(Integer.valueOf(data.date.gYear()), month);
            } else {
                month = this.fill.get(Integer.valueOf(data.date.gYear()));
            }

            month.add(data);
        }
    }


    static class JournalLine extends TableLine {
        static int serial_number_next = 0;
        int num = 0;
        boolean is_read = false;
        CoreTime date = null;
        String message = null;
        journable jou = null;
        int ranger_cur_value = 0;
        int text_high = 0;

        JournalLine() {
            this.num = (serial_number_next++);
        }

        private void SayYes() {
            this.is_read = true;

            if (this.jou != null) {
                this.jou.answerYES();
            }
        }

        private void SayNo() {
            this.is_read = true;

            if (this.jou != null) {
                this.jou.answerNO();
            }
        }
    }


    class JournalPointers {
        int num_first = -1;
        int num_last = -1;

        void add(int num) {
            if (-1 == this.num_first) {
                this.num_first = num;
            }

            this.num_last = num;
        }

        boolean hasUpperPointer() {
            if ((this.num_first == -1) || (JournalPane.this.is_empty_table)) {
                return false;
            }

            int num_current = ((JournalPane.JournalLine) JournalPane.this.journal_table.getTop()).num;

            return (num_current > this.num_first);
        }

        boolean hasDownPointer() {
            if ((this.num_first == -1) || (JournalPane.this.is_empty_table)) {
                return false;
            }

            int num_current = ((JournalPane.JournalLine) JournalPane.this.journal_table.getTop()).num
                              + JournalPane.this.journal_table.getNumRows() - 1;

            return (num_current < this.num_last);
        }
    }


    class JournalTable extends TableWrapped implements IRangerListener {
        String LINE_READ_DATE_text = null;
        String LINE_UNREAD_DATE_text = null;
        private JournalPane.JournalCalendarYearFilling calendarFilling;
        private JournalPane.JournalPointers make_pointers;

        JournalTable(long __menu) {
            super(__menu, 1, false, "..\\data\\config\\menu\\menu_com.xml", "TABLEGROUP JOURNAL - 6 112",
                  "Tableranger - JOURNAL - list", "Tablegroup - ELEMENTS - JOURNAL", JournalPane.LINE_ELEMENTS,
                  JournalPane.LINE_ELEMENTS_RADIO, null);
            JournalPane.this._menu = __menu;
            this.table.addRangerListener(this);

            long[] id_rangers = this.table.getLineStatistics_controls("Tableranger - Journal - Message");

            JournalPane.this.rangers = new MENU_ranger[id_rangers.length];

            for (int i = 0; i < id_rangers.length; ++i) {
                JournalPane.this.rangers[i] = ((MENU_ranger) menues.ConvertMenuFields(id_rangers[i]));
                JournalPane.this.rangers[i].userid = i;
                menues.SetScriptOnControl(JournalPane.this._menu, JournalPane.this.rangers[i], this, "OnRanger", 1L);
                menues.UpdateMenuField(JournalPane.this.rangers[i]);
            }
        }

        private void make_sync_group() {
            long[] ids0 = this.table.getLineStatistics_controls(JournalPane.LINE_ELEMENTS[0]);
            long[] ids1 = this.table.getLineStatistics_controls(JournalPane.LINE_ELEMENTS[1]);
            long[] ids2 = this.table.getLineStatistics_controls(JournalPane.LINE_ELEMENTS[2]);
            long[] ids3 = this.table.getLineStatistics_controls(JournalPane.LINE_ELEMENTS[3]);
            long[] ids4 = this.table.getLineStatistics_controls(JournalPane.LINE_ELEMENTS[4]);
            long[] ids5 = this.table.getLineStatistics_controls(JournalPane.LINE_ELEMENTS[5]);
            int size = Math.min(ids0.length, ids1.length);

            size = Math.min(ids2.length, size);
            size = Math.min(ids3.length, size);
            size = Math.min(ids4.length, size);
            size = Math.min(ids5.length, size);

            for (int i = 0; i < size; ++i) {
                menues.SetSyncControlActive(JournalPane.this._menu, i, ids0[i]);
                menues.SetSyncControlState(JournalPane.this._menu, i, ids0[i]);
                menues.SetSyncControlActive(JournalPane.this._menu, i, ids1[i]);
                menues.SetSyncControlState(JournalPane.this._menu, i, ids1[i]);
                menues.SetSyncControlActive(JournalPane.this._menu, i, ids2[i]);
                menues.SetSyncControlState(JournalPane.this._menu, i, ids2[i]);
                menues.SetSyncControlActive(JournalPane.this._menu, i, ids3[i]);
                menues.SetSyncControlState(JournalPane.this._menu, i, ids3[i]);
                menues.SetSyncControlActive(JournalPane.this._menu, i, ids4[i]);
                menues.SetSyncControlState(JournalPane.this._menu, i, ids4[i]);
                menues.SetSyncControlActive(JournalPane.this._menu, i, ids5[i]);
                menues.SetSyncControlState(JournalPane.this._menu, i, ids5[i]);
            }
        }

        /**
         * Method description
         *
         *
         * @param _menu
         * @param button
         */
        public void onSort(long _menu, MENUsimplebutton_field button) {}

        private int getNumRows() {
            return this.table.getNumRows();
        }

        void OnRanger(long _menu, MENU_ranger ranger) {
            if ((ranger.userid < this.TABLE_DATA.all_lines.size()) && (!(getLineItem(ranger.userid).wheather_show))) {
                return;
            }

            JournalPane.JournalLine line = (JournalPane.JournalLine) getLineItem(ranger.userid);

            if (line.is_read) {
                if ((JournalPane.this.id_textFieldRead != null)
                        && (JournalPane.this.id_textFieldRead[ranger.userid] != 0L)) {
                    line.ranger_cur_value = ranger.current_value;
                    JournalPane.this.baseline_read.MoveBaseLine(JournalPane.this.id_textFieldRead[ranger.userid],
                            -ranger.current_value * JournalPane.this.line_height_read);
                }
            } else if ((JournalPane.this.id_textFieldUnread != null)
                       && (JournalPane.this.id_textFieldUnread[ranger.userid] != 0L)) {
                line.ranger_cur_value = ranger.current_value;
                JournalPane.this.baseline_unread.MoveBaseLine(JournalPane.this.id_textFieldUnread[ranger.userid],
                        -ranger.current_value * JournalPane.this.line_height_unread);
            }
        }

        @Override
        protected void reciveTableData() {
            ArrayList all = JournalPane.this.getAllLines();

            this.calendarFilling = new JournalPane.JournalCalendarYearFilling(all);
            this.make_pointers = new JournalPane.JournalPointers(JournalPane.this);

            for (JournalPane.JournalLine line : all) {
                if (!(line.is_read)) {
                    this.make_pointers.add(line.num);
                }
            }

            JournalPane.access$002(JournalPane.this, (all == null) || (all.isEmpty()));
            this.TABLE_DATA.all_lines.addAll(all);
        }

        /**
         * Method description
         *
         *
         * @param button
         * @param position
         * @param table_node
         */
        @Override
        public void SetupLineInTable(long button, int position, TableLine table_node) {
            JournalPane.JournalLine line = (JournalPane.JournalLine) table_node;

            if ((position == 10) || (position == 6) || (position == 7) || (position == 8) || (position == 9)) {
                if (line.is_read) {
                    if (line.text_high <= JournalPane.this.linescreen_read) {
                        menues.SetShowField(button, false);
                    } else {
                        menues.SetShowField(button, true);
                    }
                } else if (line.text_high <= JournalPane.this.linescreen_unread) {
                    menues.SetShowField(button, false);
                } else {
                    menues.SetShowField(button, true);
                }
            }

            switch (position) {
             case 0 :
                 if (line.is_read) {
                     if (this.LINE_READ_DATE_text == null) {
                         this.LINE_READ_DATE_text = menues.GetFieldText(button);
                     }

                     menues.SetFieldText(button,
                                         Converts.ConvertDateAbsolute(this.LINE_READ_DATE_text, line.date.gMonth(),
                                             line.date.gDate(), line.date.gYear(), line.date.gHour(),
                                             line.date.gMinute()));
                     menues.SetShowField(button, true);
                 } else {
                     menues.SetShowField(button, false);
                 }

                 break;

             case 1 :
                 if (line.is_read) {
                     menues.SetFieldText(button, line.message);
                     menues.SetShowField(button, true);

                     if (JournalPane.this.baseline_unread != null) {
                         JournalPane.this.baseline_unread.MoveBaseLine(button,
                                 -line.ranger_cur_value * JournalPane.this.line_height_unread);
                     }
                 } else {
                     menues.SetShowField(button, false);
                 }

                 break;

             case 2 :
                 if (line.is_read) {
                     menues.SetShowField(button, true);
                 } else {
                     menues.SetShowField(button, false);
                 }

                 break;

             case 3 :
                 if (line.is_read) {
                     menues.SetShowField(button, false);
                 } else {
                     if (this.LINE_UNREAD_DATE_text == null) {
                         this.LINE_UNREAD_DATE_text = menues.GetFieldText(button);
                     }

                     menues.SetFieldText(button,
                                         Converts.ConvertDateAbsolute(this.LINE_UNREAD_DATE_text, line.date.gMonth(),
                                             line.date.gDate(), line.date.gYear(), line.date.gHour(),
                                             line.date.gMinute()));
                     menues.SetShowField(button, true);
                 }

                 break;

             case 4 :
                 if (line.is_read) {
                     menues.SetShowField(button, false);
                 } else {
                     menues.SetFieldText(button, line.message);
                     menues.SetShowField(button, true);

                     if (JournalPane.this.baseline_unread != null) {
                         JournalPane.this.baseline_unread.MoveBaseLine(button,
                                 -line.ranger_cur_value * JournalPane.this.line_height_unread);
                     }
                 }

                 break;

             case 5 :
                 if (line.is_read) {
                     menues.SetShowField(button, false);
                 } else {
                     menues.SetShowField(button, true);
                 }

                 break;

             case 10 :
                 MENU_ranger ranger = menues.ConvertRanger(button);

                 if (line.is_read) {
                     if (line.text_high > JournalPane.this.linescreen_read) {
                         ranger.min_value = 0;
                         ranger.max_value = (line.text_high - JournalPane.this.linescreen_read);
                         ranger.page = JournalPane.this.linescreen_read;
                         ranger.current_value = line.ranger_cur_value;
                     } else {
                         ranger.min_value = 0;
                         ranger.current_value = 0;
                         ranger.max_value = 0;
                         ranger.page = JournalPane.this.linescreen_read;
                     }
                 } else if (line.text_high > JournalPane.this.linescreen_unread) {
                     ranger.min_value = 0;
                     ranger.max_value = (line.text_high - JournalPane.this.linescreen_unread);
                     ranger.page = JournalPane.this.linescreen_unread;
                     ranger.current_value = line.ranger_cur_value;
                 } else {
                     ranger.min_value = 0;
                     ranger.current_value = 0;
                     ranger.max_value = 0;
                     ranger.page = JournalPane.this.linescreen_unread;
                 }

                 menues.UpdateMenuField(ranger);
             case 6 :
             case 7 :
             case 8 :
             case 9 :
            }

            menues.UpdateMenuField(menues.ConvertMenuFields(button));
        }

        /**
         * Method description
         *
         *
         * @param linedata
         */
        @Override
        public void updateSelectedInfo(TableLine linedata) {
            if (JournalPane.this.is_empty_table) {
                return;
            }

            JournalPane.JournalLine line = (JournalPane.JournalLine) linedata;

            JournalPane.access$1202(JournalPane.this, line.date);

            if (!(JournalPane.this.can_select)) {
                return;
            }

            if ((JournalPane.this.canShowQuestion() != 0) && (line != null) && (!(line.is_read))) {
                JournalPane.access$302(JournalPane.this, line);

                journable jou = line.jou;

                if (jou != null) {
                    if (jou.needMenu()) {
                        JournalPane.this.question.show();
                    } else {
                        JournalPane.this.question.callonYesClose();
                    }
                }
            }

            JournalPane.access$1302(JournalPane.this, false);
            selectDate();
            JournalPane.access$1302(JournalPane.this, true);
        }

        /**
         * Method description
         *
         *
         * @param table
         */
        public void enterFocus(Table table) {}

        /**
         * Method description
         *
         *
         * @param table
         */
        public void leaveFocus(Table table) {}

        /**
         * Method description
         *
         */
        public void deinit() {
            this.table.deinit();
        }

        /**
         * Method description
         *
         */
        public void selectDate() {
            JournalPane.this.calendar_table.moveToSelectedTime();
            JournalPane.this.calendar_table.update();
        }

        private JournalPane.JournalCalendarFilling findCalendarFilling(CoreTime time) {
            if (null == this.calendarFilling) {
                return null;
            }

            return this.calendarFilling.find(time);
        }

        private void selectLine(JournalPane.JournalLine line) {
            this.table.select_line_by_data(line);
        }

        private void updatePointers() {
            if (this.make_pointers == null) {
                return;
            }

            menues.SetShowField(JournalPane.this.pointers[0], this.make_pointers.hasUpperPointer());
            menues.SetShowField(JournalPane.this.pointers[1], this.make_pointers.hasDownPointer());
        }

        /**
         * Method description
         *
         */
        public void update() {
            updateTable();
            updatePointers();
        }

        /**
         * Method description
         *
         */
        @Override
        public void rangerMoved() {
            updatePointers();
        }

        /**
         * Method description
         *
         */
        @Override
        public void afterInit() {
            super.afterInit();
            JournalPane.this.id_textFieldUnread = this.table.getLineStatistics_controls("Journal - Message UNREAD");
            JournalPane.this.id_textFieldRead = this.table.getLineStatistics_controls("Journal - Message READ");

            MENUbutton_field unread_text =
                (MENUbutton_field) menues.ConvertMenuFields(JournalPane.this.id_textFieldUnread[0]);
            MENUbutton_field read_text =
                (MENUbutton_field) menues.ConvertMenuFields(JournalPane.this.id_textFieldRead[0]);

            JournalPane.this.line_height_unread = menues.GetTextLineHeight(JournalPane.this.id_textFieldUnread[0]);
            JournalPane.this.line_height_read = menues.GetTextLineHeight(JournalPane.this.id_textFieldRead[0]);
            JournalPane.this.baseline_unread = new MENUBase_Line(JournalPane.this.id_textFieldUnread[0]);
            JournalPane.this.baseline_read = new MENUBase_Line(JournalPane.this.id_textFieldRead[0]);
            JournalPane.this.linescreen_unread = Converts.HeightToLines(unread_text.leny,
                    JournalPane.this.baseline_unread.GetMinBaseLine(), JournalPane.this.line_height_unread);
            JournalPane.this.linescreen_read = Converts.HeightToLines(read_text.leny,
                    JournalPane.this.baseline_read.GetMinBaseLine(), JournalPane.this.line_height_read);
            JournalPane.this.id_rangers_control_group =
                this.table.getLineStatistics_controls("Journal - Message UNREAD");

            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                if (this.TABLE_DATA.all_lines.elementAt(i).wheather_show) {
                    JournalPane.JournalLine elem = (JournalPane.JournalLine) this.TABLE_DATA.all_lines.elementAt(i);

                    if (elem.is_read) {
                        elem.text_high = (((JournalPane.this.id_textFieldRead != null)
                                           && (JournalPane.this.id_textFieldRead[0] != 0L))
                                          ? Converts.HeightToLines(
                                              menues.GetTextHeight(JournalPane.this.id_textFieldRead[0], elem.message),
                                              JournalPane.this.baseline_read.GetMinBaseLine(),
                                              JournalPane.this.line_height_read)
                                          : 0);
                    } else {
                        elem.text_high = (((JournalPane.this.id_textFieldUnread != null)
                                           && (JournalPane.this.id_textFieldUnread[0] != 0L))
                                          ? Converts.HeightToLines(
                                              menues.GetTextHeight(
                                                  JournalPane.this.id_textFieldUnread[0],
                                                  elem.message), JournalPane.this.baseline_unread.GetMinBaseLine(),
                                                      JournalPane.this.line_height_unread)
                                          : 0);
                    }
                }
            }

            make_sync_group();
        }
    }


    class ListenQuestionAnswer implements IYesNoCancelMenuListener {
        private void cleanAnswered() {
            JournalPane.JournalCalendarFilling fill_data =
                JournalPane.this.journal_table.findCalendarFilling(JournalPane.this.underQuestion.date);

            JournalPane.JournalCalendarFilling.access$500(fill_data);
            JournalPane.access$302(JournalPane.this, null);
            JournalPane.this.calendar_table.redrawTable();
            journal.getInstance().updateActiveNotes();
        }

        /**
         * Method description
         *
         */
        @Override
        public void onCancelClose() {
            JournalPane.access$302(JournalPane.this, null);
        }

        /**
         * Method description
         *
         */
        @Override
        public void onClose() {
            JournalPane.access$302(JournalPane.this, null);
        }

        /**
         * Method description
         *
         */
        @Override
        public void onNoClose() {
            JournalPane.this.underQuestion.SayNo();
            JournalPane.this.journal_table.update();
            JournalPane.this.calendar_table.update();
            JournalPane.this.journal_table.redrawTable();
            cleanAnswered();
        }

        /**
         * Method description
         *
         */
        @Override
        public void onOpen() {}

        /**
         * Method description
         *
         */
        @Override
        public void onYesClose() {
            JournalPane.this.underQuestion.SayYes();
            JournalPane.this.journal_table.update();
            JournalPane.this.calendar_table.update();
            JournalPane.this.journal_table.redrawTable();
            cleanAnswered();
        }
    }
}


//~ Formatted in DD Std on 13/08/26
