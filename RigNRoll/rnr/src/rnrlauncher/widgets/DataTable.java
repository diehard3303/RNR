/*
 * @(#)DataTable.java   13/08/26
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


package rnr.src.rnrlauncher.widgets;

//~--- non-JDK imports --------------------------------------------------------

import rnr.src.rnrlauncher.data.ColumnHeader;
import rnr.src.rnrlauncher.data.Resolution;
import rnr.src.rnrlauncher.data.SystemInfoDataRecord;

import sun.font.FontDesignMetrics;

//~--- JDK imports ------------------------------------------------------------

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.border.LineBorder;

/**
 * Class description
 *
 *
 * @version        1.0, 13/08/26
 * @author         TJ    
 */
public final class DataTable {
    private static final int MAX_SCREEN_WIDTH_BOUND = 1024;
    private static final int CELL_MARGIN = 2;
    private static final int GAP = 5;
    private static final int DEFAULT_ROW_HEIGHT = 10;
    private static final int LINE_HEIGHT = 11;
    private static final int LINE_CARRY = 4;
    private static final int HEADER_HEIGHT = 14;
    private static final int VERTICAL_TEXT_OFFSET = 2;
    private static final int FONT_SIZE = 12;
    private static final int DEFAULT_LINES_PER_CELL = 2;
    private static final int MAX_LINES_PER_CELL = 3;
    private static final int SCROLL_SIZE = 10;
    private static final int MAX_ROWS_WITH_EXTENDED_SIZE = 2;
    private static final Color TRANSPARENT;
    private static final Dimension VERTICAL_SCROLL_SIZE;
    private static final Dimension HORIZONTAL_SCROLL_SIZE;

    static {
        TRANSPARENT = new Color(0, 0, 0, 0);
        VERTICAL_SCROLL_SIZE = new Dimension(10, 0);
        HORIZONTAL_SCROLL_SIZE = new Dimension(0, 10);
    }

    private int rowsHeight = 0;
    private int columnsWidth = 0;
    private int rowsWithExtendedSize = 0;
    private final JPanel rootPanel = new JPanel();
    private final ArrayList<TableRecod> recordsToDisplay = new ArrayList();
    private boolean twoLinesOnHeader = false;
    private final Font headerFont;
    private final Font dataFont;
    private final Color tableBackgoundColor;
    private final int maxColumnsWidth;
    private int[] columnSize;
    private int[] widthBounds;
    private final ColumnHeader[] headers;

    /**
     * Constructs ...
     *
     *
     * @param backgoundColor
     * @param fontName
     * @param header
     * @param screenWidthRestReqired
     */
    public DataTable(Color backgoundColor, String fontName, ColumnHeader[] header, int screenWidthRestReqired) {
        assert(null != fontName);
        assert(null != header);
        assert(header.length == 4);
        assert(0 <= screenWidthRestReqired);
        assert(null != backgoundColor);

        double sum = 0.0D;

        for (ColumnHeader columnHeader : header) {
            sum += columnHeader.getWeight();
        }

        assert(Math.abs(1.0D - sum) <= 4.9E-324D);
        this.tableBackgoundColor = backgoundColor;
        this.headers = header;
        this.headerFont = new Font(fontName, 1, 12);
        this.dataFont = new Font(fontName, 0, 12);

        int maxTableWidth = Math.min(1024 - screenWidthRestReqired,
                                     Toolkit.getDefaultToolkit().getScreenSize().width - screenWidthRestReqired);

        this.maxColumnsWidth = (maxTableWidth - (2 * header.length));
        calculateColumnsWidthBounds();
        doGreedyHeaderLayout();
    }

    private void calculateColumnsWidthBounds() {
        this.widthBounds = new int[this.headers.length];

        for (int i = 0; i < this.widthBounds.length; ++i) {
            this.widthBounds[i] = (int) (this.maxColumnsWidth * this.headers[i].getWeight() + 0.5D);
        }
    }

    private void doGreedyHeaderLayout() {
        this.columnSize = new int[this.headers.length];

        for (int i = 0; i < this.headers.length; ++i) {
            int width = new FontDesignMetrics(this.headerFont).stringWidth(this.headers[i].getTitle());

            if (width > this.widthBounds[i]) {
                this.twoLinesOnHeader = true;
                width = this.widthBounds[i];
            }

            this.columnsWidth += width;
            this.columnSize[i] = width;
        }
    }

    private int adjustCellSize(int columnIndex, int[] widthBounds, String text) {
        assert(null != widthBounds);
        assert((0 <= columnIndex) && (widthBounds.length > columnIndex));

        int maxWidth = widthBounds[columnIndex];
        int requiredWidth = new FontDesignMetrics(this.headerFont).stringWidth(text) + 5 + 5;

        if (requiredWidth < this.columnSize[columnIndex]) {
            return 1;
        }

        if (this.columnsWidth - this.columnSize[columnIndex] + requiredWidth < this.maxColumnsWidth) {
            requiredWidth = Math.min(requiredWidth, maxWidth);
            this.columnsWidth += requiredWidth - this.columnSize[columnIndex];
            this.columnSize[columnIndex] = requiredWidth;

            return 1;
        }

        int linesCount = 2;

        requiredWidth /= 2;

        if (requiredWidth > this.columnSize[columnIndex]) {
            int newColumnsWidth = this.columnsWidth - this.columnSize[columnIndex] + requiredWidth;

            if ((newColumnsWidth > this.maxColumnsWidth) || (requiredWidth > maxWidth)) {
                requiredWidth -= Math.max(newColumnsWidth - this.maxColumnsWidth, requiredWidth - maxWidth);
                ++linesCount;
            }

            this.columnsWidth += requiredWidth - this.columnSize[columnIndex];
            this.columnSize[columnIndex] = requiredWidth;
        }

        return linesCount;
    }

    /**
     * Method description
     *
     *
     * @param infoRecord
     */
    public void addInfoRecord(SystemInfoDataRecord infoRecord) {
        assert(null != infoRecord);

        TableRecod record = new TableRecod(infoRecord, null);
        int linesRequired = 1;

        linesRequired = Math.max(linesRequired,
                                 adjustCellSize(3, this.widthBounds,
                                     ((Resolution) infoRecord.getInfo(3)).getDescription()));
        linesRequired = Math.max(linesRequired, adjustCellSize(0, this.widthBounds, (String) infoRecord.getInfo(0)));
        linesRequired = Math.max(linesRequired, adjustCellSize(1, this.widthBounds, (String) infoRecord.getInfo(1)));
        linesRequired = Math.max(linesRequired, adjustCellSize(2, this.widthBounds, (String) infoRecord.getInfo(2)));

        if (3 == linesRequired) {
            if (2 > this.rowsWithExtendedSize) {
                this.rowsWithExtendedSize += 1;
            } else {
                --linesRequired;
            }
        }

        int cellHeight = 10 + linesRequired * 11 + (linesRequired - 1) * 4;

        record.setHeight(cellHeight);
        this.rowsHeight += cellHeight;
        this.recordsToDisplay.add(record);
    }

    private static JPanel createFooter(Resolution backgrountInfo) {
        Image gradient = backgrountInfo.getBaseImage().getImage();

        return new JPanel(gradient) {
            @Override
            public void paint(Graphics g) {
                Rectangle area = getBounds();
                Graphics2D canvas = (Graphics2D) g;

                canvas.drawImage(this.val$gradient, 0, 0, area.width, area.height, null);
                super.paintChildren(g);
            }
        };
    }

    private JComponent createTableRecord(TableRecod record) {
        JPanel recordContainer = new JPanel();

        recordContainer.setLayout(new BoxLayout(recordContainer, 0));
        recordContainer.setBackground(this.tableBackgoundColor);

        SystemInfoDataRecord data = record.getData();

        assert(data.getInfo(3) instanceof Resolution);

        Resolution resulutionDescription = (Resolution) data.getInfo(3);

        for (int i = 0; i < 3; ++i) {
            String dataText = (String) data.getInfo(i);
            JTextArea textRenderer = new JTextArea(dataText);
            JScrollPane scroller = new JScrollPane(textRenderer);
            JComponent textRendererBase = createFooter(resulutionDescription);

            textRendererBase.setLayout(new BorderLayout());
            textRenderer.setFont((0 == i)
                                 ? this.headerFont
                                 : this.dataFont);
            textRenderer.setOpaque(false);
            textRenderer.setBackground(TRANSPARENT);
            textRenderer.setSelectionColor(TRANSPARENT);
            textRenderer.setSelectedTextColor(Color.BLACK);
            textRenderer.setEditable(false);
            textRenderer.setWrapStyleWord(true);
            textRenderer.setLineWrap(0 != i);
            scroller.setOpaque(false);
            scroller.setBackground(TRANSPARENT);
            scroller.setForeground(TRANSPARENT);
            scroller.getViewport().setOpaque(false);
            scroller.getViewport().setBackground(TRANSPARENT);
            scroller.getVerticalScrollBar().setPreferredSize(VERTICAL_SCROLL_SIZE);
            scroller.getHorizontalScrollBar().setPreferredSize(HORIZONTAL_SCROLL_SIZE);
            scroller.setBorder(new LineBorder(TRANSPARENT, 5));

            JComponent verticalTextShift = new JPanel();

            verticalTextShift.setPreferredSize(new Dimension(this.columnSize[i], 2));
            verticalTextShift.setBackground(TRANSPARENT);
            textRendererBase.add(verticalTextShift, "North");
            textRendererBase.add(scroller, "Center");

            Dimension size = new Dimension(this.columnSize[i], record.getHeight());

            textRendererBase.setPreferredSize(size);
            scroller.setPreferredSize(size);
            scroller.getViewport().setPreferredSize(size);

            Component strut = Box.createHorizontalStrut(2);

            strut.setBackground(this.tableBackgoundColor);
            recordContainer.add(textRendererBase);
            recordContainer.add(strut);
        }

        JPanel iconTextBase = createFooter(resulutionDescription);

        iconTextBase.setLayout(new BorderLayout(5, 0));

        JLabel resolutionIconRenderer = new JLabel(resulutionDescription.getIcon());

        resolutionIconRenderer.setOpaque(false);
        resolutionIconRenderer.setVerticalAlignment(1);

        int iconWithTextWidth = this.columnSize[3];
        JTextArea iconText = new JTextArea(resulutionDescription.getDescription());

        iconText.setFont(this.dataFont);
        iconText.setLineWrap(true);
        iconText.setWrapStyleWord(true);
        iconText.setEditable(false);
        iconText.setOpaque(false);
        iconText.setBackground(TRANSPARENT);
        iconText.setSelectionColor(TRANSPARENT);
        iconText.setSelectedTextColor(Color.BLACK);

        JPanel iconBase = new JPanel(new BorderLayout());
        JPanel textBase = new JPanel(new BorderLayout());
        JScrollPane textScroll = new JScrollPane(iconText);

        textScroll.setBorder(null);
        textScroll.getVerticalScrollBar().setPreferredSize(VERTICAL_SCROLL_SIZE);
        textScroll.getHorizontalScrollBar().setPreferredSize(HORIZONTAL_SCROLL_SIZE);
        textScroll.getViewport().setBackground(TRANSPARENT);
        textScroll.setBackground(TRANSPARENT);
        textScroll.setOpaque(false);
        textScroll.getViewport().setOpaque(false);
        iconBase.setBackground(TRANSPARENT);
        textBase.setBackground(TRANSPARENT);
        iconBase.setOpaque(false);
        textBase.setOpaque(false);
        textBase.setBorder(new LineBorder(TRANSPARENT, 5));
        iconBase.setBorder(new LineBorder(TRANSPARENT, 4));
        iconBase.add(resolutionIconRenderer, "Center");
        textBase.add(textScroll, "Center");
        iconTextBase.add(iconBase, "West");
        iconTextBase.add(textBase, "Center");
        textScroll.setPreferredSize(new Dimension(this.columnSize[3], record.getHeight()));
        iconTextBase.setPreferredSize(new Dimension(iconWithTextWidth, record.getHeight()));
        iconTextBase.setMinimumSize(new Dimension(iconWithTextWidth, record.getHeight()));
        recordContainer.add(iconTextBase);

        return recordContainer;
    }

    private JComponent createTableHeader() {
        JPanel header = new JPanel();

        header.setLayout(new BoxLayout(header, 0));
        header.setBackground(this.tableBackgoundColor);

        if (this.twoLinesOnHeader) {
            int extendedHeaderSize = new FontDesignMetrics(this.headerFont).getHeight() * 2 + 4;

            this.rowsHeight += extendedHeaderSize;

            for (int i = 0; i < this.headers.length; ++i) {
                JPanel columnHeaderBase = new JPanel();
                JTextArea columnHeader = new JTextArea(this.headers[i].getTitle());

                columnHeader.setFont(this.headerFont);
                columnHeader.setEditable(false);
                columnHeader.setSelectionColor(this.tableBackgoundColor);
                columnHeader.setBackground(this.tableBackgoundColor);
                columnHeader.setLineWrap(true);
                columnHeader.setWrapStyleWord(true);
                columnHeader.setSelectedTextColor(Color.WHITE);
                columnHeader.setForeground(Color.WHITE);

                Dimension size = new Dimension(this.columnSize[i], extendedHeaderSize);

                columnHeader.setPreferredSize(size);
                columnHeaderBase.setPreferredSize(size);
                columnHeaderBase.add(columnHeader);
                columnHeaderBase.setBackground(this.tableBackgoundColor);
                header.add(columnHeaderBase);
            }
        } else {
            this.rowsHeight += 14;

            for (int i = 0; i < this.headers.length; ++i) {
                JPanel columnHeaderBase = new JPanel(new BorderLayout());
                JLabel columnHeader = new JLabel(this.headers[i].getTitle(), 0);

                columnHeader.setFont(this.headerFont);
                columnHeader.setForeground(Color.WHITE);
                columnHeader.setVerticalAlignment(0);
                columnHeaderBase.setPreferredSize(new Dimension(this.columnSize[i], 14));
                columnHeaderBase.add(columnHeader, "Center");
                columnHeaderBase.setBackground(this.tableBackgoundColor);
                header.add(columnHeaderBase);
            }
        }

        return header;
    }

    /**
     * Method description
     *
     */
    public void constructGui() {
        this.rootPanel.setBackground(this.tableBackgoundColor);
        this.rootPanel.setLayout(new BoxLayout(this.rootPanel, 1));

        int tableWidth = this.columnsWidth + 2 * this.headers.length;
        int tableHeight = this.rowsHeight + 13 * this.recordsToDisplay.size();

        this.rootPanel.setPreferredSize(new Dimension(tableWidth, tableHeight));
        this.rootPanel.add(createTableHeader());

        for (TableRecod tableRecod : this.recordsToDisplay) {
            this.rootPanel.add(createTableRecord(tableRecod));

            Component strut = Box.createVerticalStrut(2);

            strut.setBackground(this.tableBackgoundColor);
            this.rootPanel.add(strut);
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    public JComponent getTable() {
        return this.rootPanel;
    }

    private static final class TableRecod {
        private final SystemInfoDataRecord data;
        private int height;

        private TableRecod(SystemInfoDataRecord data) {
            this.data = data;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public SystemInfoDataRecord getData() {
            return this.data;
        }

        /**
         * Method description
         *
         *
         * @return
         */
        public int getHeight() {
            return this.height;
        }

        /**
         * Method description
         *
         *
         * @param height
         */
        public void setHeight(int height) {
            this.height = height;
        }
    }
}


//~ Formatted in DD Std on 13/08/26
