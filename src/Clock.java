
import java.awt.*;
import javax.swing.*;
import java.util.Calendar;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Month;

public class Clock extends JLabel implements Runnable {
    
    private JPanel timeCTN, dateCTN;
    private JLabel time, date;
    private JLabel timeIcon, dateIcon;
    
    public Clock() {
    }
    
    @Override
    public synchronized void run() {
        /* กำหนด format วันที่และเรียกใช้วัน ณ ปัจจุบัน */
        DateTimeFormatter day = DateTimeFormatter.ofPattern("dd");
        DateTimeFormatter month = DateTimeFormatter.ofPattern("MM");
        DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now = LocalDateTime.now();
        /* */
        
        /* กำหนด properties ของ Clock ทั้งหมด (จนถึงบรรทัด 53) */
        timeCTN = new JPanel();
        dateCTN = new JPanel();
        time = new JLabel();
        date = new JLabel();
        timeIcon = new JLabel(new ImageIcon("icon/clock.png"));
        dateIcon = new JLabel(new ImageIcon("icon/calendar.png"));
        
        time.setForeground(new Color(245, 245, 245));
        time.setFont(new Font("Sans Serif", Font.BOLD, 12));
        date.setForeground(new Color(245, 245, 245));
        date.setFont(new Font("Sans Serif", Font.BOLD, 12));
        
        timeCTN.setLayout(new BorderLayout(8, 8));
        timeCTN.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 24));
        timeCTN.setOpaque(false);
        timeCTN.add(timeIcon, BorderLayout.WEST);
        timeCTN.add(time, BorderLayout.CENTER);
        
        dateCTN.setLayout(new BorderLayout(8, 8));
        dateCTN.setOpaque(false);
        dateCTN.add(dateIcon, BorderLayout.WEST);
        dateCTN.add(date, BorderLayout.CENTER);
        
        this.setLayout(new GridBagLayout());
        this.add(timeCTN);
        this.add(dateCTN);
        /* */

        int monthNumber = Integer.parseInt(month.format(now));
        date.setText(day.format(now) + " " + Month.of(monthNumber).name() + " " + year.format(now));
        
        try { // การใช้ Thread เพื่อทำนาฬิกาแบบ real time
            while (true) {
                Calendar d = Calendar.getInstance(); // get เวลา ณ ปัจจุบัน
                int sec = d.get(Calendar.SECOND); // get วินาที ณ ปัจจุบัน
                int min = d.get(Calendar.MINUTE); // get นาที ณ ปัจจุบัน
                int hour = d.get(Calendar.HOUR_OF_DAY); // get ชั่วโมง ณ ปัจจุบัน
                
                String text = "";
                text += String.format("%02d", hour) + " : "; // format string
                text += String.format("%02d", min) + " : ";
                text += String.format("%02d", sec);
                
                time.setText(text);
                Thread.sleep(1000); // การพักการทำงานของ Thread 1 วินาที
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
