package Profiling;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements ActionListener {
    JButton A,C,E,exit;
    public MainMenu(){
        setTitle("EzShop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(700,600);
        setLayout(null);
        setLocationRelativeTo(null);

        ImageIcon mainlogo=new ImageIcon("images\\mainlogo.png");
        setIconImage(mainlogo.getImage());

        JPanel head=new JPanel();
        head.setBounds(0,0,700,100);
        head.setBackground(new Color(44, 62, 80));
        head.setLayout(new GridLayout(1,0));
        JLabel heading= new JLabel("EzShop");
        heading.setFont(new Font("Bookman",Font.BOLD,40));
        heading.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));
        heading.setForeground(Color.white);
        head.add(heading);

        JPanel body=new JPanel();
        body.setBounds(0,100,700,400);
        body.setBackground(new Color(234, 250, 241 ));
        body.setLayout(null);
        A=new JButton("Administrator");
        A.setFocusable(false);
        A.setBounds(275,65,150,70);
        A.setFont(new Font("Comic sans",Font.BOLD,20));
        A.setBackground(new Color(40, 55, 71));
        A.setForeground(Color.white);
        A.setBorder(BorderFactory.createEtchedBorder());

        E=new JButton("Employee");
        E.setFocusable(false);
        E.setBounds(275,165,150,70);
        E.setFont(new Font("Comic sans",Font.BOLD,20));
        E.setBackground(new Color(40, 55, 71));
        E.setForeground(Color.white);
        E.setBorder(BorderFactory.createEtchedBorder());

        C=new JButton("Customer");
        C.setFocusable(false);
        C.setBounds(275,265,150,70);
        C.setFont(new Font("Comic sans",Font.BOLD,20));
        C.setBackground(new Color(40, 55, 71));
        C.setForeground(Color.white);
        C.setBorder(BorderFactory.createEtchedBorder());

        A.addActionListener(this);
        E.addActionListener(this);
        C.addActionListener(this);
        body.add(A);
        body.add(E);
        body.add(C);

        JPanel footer=new JPanel();
        footer.setBounds(0,500,700,100);
        footer.setBackground(new Color(233, 247, 239));
        footer.setLayout(null);

        exit=new JButton("exit");
        exit.setFocusable(false);
        exit.setBackground(new Color(203, 67, 53));
        exit.setFont(new Font("georgia", Font.BOLD,16));
        exit.setBounds(600,20,70,30);
        exit.setForeground(Color.white);
        exit.addActionListener(e -> System.exit(0));

        footer.add(exit);

        setVisible(true);
        add(head);
        add(body);
        add(footer);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==A){
           new Login(A.getActionCommand());
           dispose();
        }
        if(e.getSource()==E){
            new Login(E.getActionCommand());
            dispose();
        }
        if(e.getSource()==C){
            new Login(C.getActionCommand());
            dispose();
        }

    }
}
