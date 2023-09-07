import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Shutdown {
    private JFrame mainFrame;
    private JButton setShutdownButton;
    private JButton cancelShutdownButton;

    private JFrame setupFrame;
    private JTextField hoursTextField;
    private JTextField minutesTextField;
    private JTextField secondsTextField;
    private JButton confirmButton;
    private JButton cancelButton;

    public Shutdown() {
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("定时关机");
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setSize(300, 150);
        mainFrame.setLocationRelativeTo(null); // Center the frame on the screen

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBackground(Color.WHITE); // 设置背景颜色为白色

        setShutdownButton = new JButton("设置定时关机");
        setShutdownButton.setFocusable(false);
        setShutdownButton.setFont(new Font("楷体", 1, 13));
        setShutdownButton.setBackground(Color.WHITE);
        cancelShutdownButton = new JButton("取消定时关机");
        cancelShutdownButton.setFocusable(false);
        cancelShutdownButton.setFont(new Font("楷体", 1, 13));
        cancelShutdownButton.setBackground(Color.WHITE);

        setShutdownButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSetupFrame();
            }
        });

        cancelShutdownButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "shutdown", "-a");
                    Process process = processBuilder.start();
                    int exitCode = process.waitFor();
                    if (exitCode == 0) {
                        System.out.println("取消关机命令已执行");
                        JOptionPane.showMessageDialog(null, "已取消定时关机。", "提示", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        System.err.println("取消关机命令执行失败");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        int buttonSpacing = 20; // 你可以调整这个值来控制按钮之间的间隔
        buttonPanel.add(Box.createHorizontalGlue()); // 左边空白，使按钮居中
        buttonPanel.add(setShutdownButton);
        buttonPanel.add(Box.createHorizontalStrut(buttonSpacing)); // 添加间隔
        buttonPanel.add(cancelShutdownButton);
        buttonPanel.add(Box.createHorizontalGlue()); // 右边空白，使按钮居中

        mainFrame.add(buttonPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    private int parseTextField(JTextField textField) {
        String text = textField.getText().trim();
        if (text.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0; // 如果解析失败，也设置为0
        }
    }

    private void showSetupFrame() {
        setupFrame = new JFrame("设置定时关机");
        setupFrame.setLayout(new GridLayout(5, 2));
        setupFrame.setSize(300, 170);
        setupFrame.setLocationRelativeTo(null); // Center the frame on the screen
        setupFrame.getContentPane().setBackground(Color.WHITE);

        JPanel timePanel = new JPanel();
        timePanel.setBackground(Color.WHITE); // 设置背景颜色为白色
        timePanel.setLayout(new FlowLayout()); // 使用 FlowLayout 排列组件

        JPanel buttonPanel2 = new JPanel();
        buttonPanel2.setLayout(new BoxLayout(buttonPanel2, BoxLayout.LINE_AXIS));
        buttonPanel2.setBackground(Color.WHITE); // 设置背景颜色为白色

        hoursTextField = new JTextField();
        minutesTextField = new JTextField();
        secondsTextField = new JTextField();

        confirmButton = new JButton("确认");
        confirmButton.setFont(new Font("楷体", 1, 13));
        cancelButton = new JButton("取消");
        cancelButton.setFont(new Font("楷体", 1, 13));

        hoursTextField.setBackground(Color.WHITE); // 设置背景颜色为白色
        minutesTextField.setBackground(Color.WHITE); // 设置背景颜色为白色
        secondsTextField.setBackground(Color.WHITE); // 设置背景颜色为白色
        confirmButton.setBackground(Color.WHITE); // 设置背景颜色为白色
        cancelButton.setBackground(Color.WHITE); // 设置背景颜色为白色

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 在这里添加定时关机的逻辑
                int hours = parseTextField(hoursTextField);
                int minutes = parseTextField(minutesTextField);
                int seconds = parseTextField(secondsTextField);
                int totalSeconds = hours * 3600 + minutes * 60 + seconds;
                System.out.println("将在 " + totalSeconds + " 秒后关机");

                // 调用命令行执行 shutdown 命令
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder("shutdown", "-s", "-t", String.valueOf(totalSeconds));
                    Process process = processBuilder.start();
                    int exitCode = process.waitFor();
                    if (exitCode == 0) {
                        System.out.println("关机命令已执行");
                        JOptionPane.showMessageDialog(null, "已设置定时关机。", "提示", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        System.err.println("关机命令执行失败");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                setupFrame.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setupFrame.dispose();
            }
        });

        JLabel inputLabel = new JLabel("请输入定时时长：");
        JLabel hoursLabel = new JLabel("小时");
        JLabel minutesLabel = new JLabel("分钟");
        JLabel secondsLabel = new JLabel("秒");
        inputLabel.setFont(new Font("楷体", 1, 15));
        inputLabel.setBackground(Color.WHITE);
        hoursLabel.setFont(new Font("楷体", 1, 13));
        hoursLabel.setBackground(Color.WHITE);
        minutesLabel.setFont(new Font("楷体", 1, 13));
        minutesLabel.setBackground(Color.WHITE);
        secondsLabel.setFont(new Font("楷体", 1, 13));
        secondsLabel.setBackground(Color.WHITE);

        // 设置文本字段的列数，从而控制其宽度
        hoursTextField.setColumns(5); // 5 列，大致可以容纳 5 个字符的宽度
        minutesTextField.setColumns(5);
        secondsTextField.setColumns(5);

        timePanel.add(hoursTextField);
        timePanel.add(hoursLabel);
        timePanel.add(minutesTextField);
        timePanel.add(minutesLabel);
        timePanel.add(secondsTextField);
        timePanel.add(secondsLabel);

        int buttonSpacing2 = 30; // 你可以调整这个值来控制按钮之间的间隔
        buttonPanel2.add(Box.createHorizontalGlue()); // 左边空白，使按钮居中
        buttonPanel2.add(confirmButton);
        buttonPanel2.add(Box.createHorizontalStrut(buttonSpacing2)); // 添加间隔
        buttonPanel2.add(cancelButton);
        buttonPanel2.add(Box.createHorizontalGlue()); // 右边空白，使按钮居中

        setupFrame.add(inputLabel);
        setupFrame.add(timePanel);
        setupFrame.add(Box.createVerticalStrut(10)); // 添加间隔
        setupFrame.add(buttonPanel2);

        setupFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Shutdown();
            }
        });
    }
}
