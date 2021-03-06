/*
 * @(#)CSRGUIMouseAdapter.java 1.0 04/04/01
 *
 * Copyright (c) 2004 Bodgitt and Scarper, LLC.
 * All rights reserved.
 */


package suncertify.app;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;

/**
 * The <code>CSRGUIMouseAdapter</code> class extends
 * <code>MouseAdapter</code>, to support column sorting.
 */
public final class CSRGUIMouseAdapter extends MouseAdapter {

    /** The <code>CSRGUI</code> object. */
    private CSRGUI csrgui;

    /** Index of the last column clicked. */
    private int lastColumnClicked;

    /** Toggle between ascending and descending. */
    private boolean isAscending;

    /**
     * Hide the no-argument constructor.
     */
    private CSRGUIMouseAdapter() {

        super();
    }

    /**
     * Constructor.
     *
     * @param o
     * the <code>CSRGUI</code> object.
     */
    CSRGUIMouseAdapter(final CSRGUI o) {

        super();

        if (null == o) {
            throw new NullPointerException();
        }

        // keep the CSRGUI reference
        csrgui = o;

        // we always start off by sorting on column 0
        lastColumnClicked = 0;
        isAscending = true;
    }

    /**
     * Process a <code>MouseEvent</code> on a JTable column
     * header.
     *
     * If the mouse is clicked an a header, sort the table on
     * the values in that column.
     *
     * @param event
     * the <code>MouseEvent</code>.
     */
    public void mouseClicked(final MouseEvent event) {

        assert null != event;

        /*
         * number of pixels between headers, where double
         * arrow is displayed for column resize.
         */
        final int pixelsBetweenHeaders = 3;

        /*
         * we know the event was generated by the JTable, but
         * this is the proper way to get the source of the
         * event
         */
        final JTable table = ((JTableHeader) event.getSource())
            .getTable();

        // get the column index
        final int column = table
            .getColumnModel()
            .getColumnIndexAtX(
                event.getX());

        // ignore event if not clicked on a column header
        if (column == -1) {
            return;
        }

        /*
         * When the mouse is positioned between columns, a
         * double arrow is displayed for column resizing.
         *
         * If the mouse was clicked between columns in this
         * mode, we will simply ignore the event.
         */
        final Rectangle rectangle = table
            .getTableHeader()
            .getHeaderRect(
                column);

        if (column == 0) {
            rectangle.width -= pixelsBetweenHeaders;
        } else {
            rectangle.grow(
                -pixelsBetweenHeaders,
                0);
        }

        if (rectangle.contains(
            event.getX(),
            event.getY())) {

            // toggle between ascending and descending
            if (column == lastColumnClicked) {
                isAscending = !isAscending;
            } else {
                isAscending = true;
            }

            lastColumnClicked = column;

            // update the table
            csrgui.updateTable(
                column,
                isAscending);
        }
    }
}

