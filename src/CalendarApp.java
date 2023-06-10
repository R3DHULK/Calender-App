import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;
import java.util.Calendar;

public class CalendarApp extends JFrame {
    private JLabel monthLabel;
    private JButton prevButton;
    private JButton nextButton;
    private JTable calendarTable;
    private Calendar calendar;

    public CalendarApp() {
        setTitle("Calendar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));

        // Create the month label
        monthLabel = new JLabel("", JLabel.CENTER);

        // Create the buttons
        prevButton = new JButton("<<");
        nextButton = new JButton(">>");

        // Create the calendar table
        calendarTable = new JTable(6, 7);
        calendarTable.setRowHeight(40);

        // Add the components to the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(monthLabel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(calendarTable), BorderLayout.CENTER);
        mainPanel.add(prevButton, BorderLayout.WEST);
        mainPanel.add(nextButton, BorderLayout.EAST);

        // Add the main panel to the frame
        getContentPane().add(mainPanel);

        pack();
        setLocationRelativeTo(null);

        // Set up the calendar
        calendar = Calendar.getInstance();
        updateCalendar();

        // Add action listeners to the buttons
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });
    }

    private void updateCalendar() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        // Update the month label
        DateFormatSymbols dfs = new DateFormatSymbols();
        String monthName = dfs.getMonths()[month];
        monthLabel.setText(monthName + " " + year);

        // Get the number of days in the month
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Get the day of the week for the first day of the month
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int startDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Set the table headers
        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < headers.length; i++) {
            calendarTable.setValueAt(headers[i], 0, i);
        }

        // Clear the table cells
        for (int i = 1; i < calendarTable.getRowCount(); i++) {
            for (int j = 0; j < calendarTable.getColumnCount(); j++) {
                calendarTable.setValueAt(null, i, j);
            }
        }

        // Set the table cells with the days of the month
        int row = 1;
        int column = startDayOfWeek - 1;
        for (int day = 1; day <= daysInMonth; day++) {
            calendarTable.setValueAt(day, row, column);
            if (column == 6) {
                row++;
                column = 0;
            } else {
                column++;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CalendarApp calendarApp = new CalendarApp();
                calendarApp.setVisible(true);
            }
        });
    }
}
