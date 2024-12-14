import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

public class SystemInfoGUI extends JFrame {
    private JLabel cpuLabel;
    private JLabel memoryLabel;
    private Timer timer;

    public SystemInfoGUI() {
        super("システム情報");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new GridLayout(2, 1));
        
        cpuLabel = new JLabel("CPU 使用率: 取得中…");
        memoryLabel = new JLabel("メモリ使用量: 取得中…");
        add(cpuLabel);
        add(memoryLabel);
        
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double cpuLoad = osBean.getSystemCpuLoad() * 100;
                
                long totalMemory = Runtime.getRuntime().totalMemory();
                long freeMemory = Runtime.getRuntime().freeMemory();
                long usedMemory = totalMemory - freeMemory;
                
                cpuLabel.setText(String.format("CPU 使用率: %.2f%%", cpuLoad));
                // JVM内のメモリなのでデフォルトで使用するmax512MBで表示される
                memoryLabel.setText(String.format("メモリ使用量: %dMB / %dMB",
                        usedMemory / (1024 * 1024),
                        totalMemory / (1024 * 1024)));
            }
        });
        timer.start();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SystemInfoGUI());
    }
}
